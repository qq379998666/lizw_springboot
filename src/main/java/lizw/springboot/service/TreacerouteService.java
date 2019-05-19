
package lizw.springboot.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lizw.springboot.callableImpl.CallableImpl;
import lizw.springboot.mapper.TracerouteMapper;

@Service
public class TreacerouteService {
	
	@Autowired
	private TracerouteMapper tracerouteMapper;
	
	@Autowired
	private SqlSessionFactory sqlSessionFactory;
		
	public List<Map<String,String>> treacerouteService(String date,String ip) {
		List<Map<String,String>> map = tracerouteMapper.selectOneIP(date, ip);
		return map;
	}
	
	public List<Map<String,String>> treacerouteSelectTwoIPService(String date,String ip_A,String ip_B) {
		List<Map<String,String>> map = tracerouteMapper.selectTwoIP(date, ip_A, ip_B);
		return map;
	}
	
	public List<Map<String,String>> treacerouteSelectCityService(String date,String operator) {
		List<Map<String,String>> map = tracerouteMapper.selectCity(date, operator);
		return map;
	}
	
	public List<Map<String,String>> treacerouteSelectProvinceService(String date,String operator) {
		List<Map<String,String>> map = tracerouteMapper.selectProvince(date, operator);
		return map;
	}
	
	/*
	 * 设置两条线程：第一线程负责结束请求，第二线程负责后台查询生成Excel到服务器
	 */
	public List<Map<String, String>> treacerouteTwoThreadSelectService(String date,String ip_A,String ip_B) throws Exception {
		
		SqlSession session = sqlSessionFactory.openSession();

    	//存放参数的Map集合
    	Map<String, Object> paramsMap = new HashMap<String,Object>();
    	//把查询出的数据放到List中
    	List<Map<String,String>> resultList= new ArrayList<Map<String,String>>();
					
		// 把SQL需要的参数放到Map中
	    paramsMap.put("date",date);
	    paramsMap.put("ip_A",ip_A);
	    paramsMap.put("ip_B",ip_B);
	    
		session.select("lizw.springboot.mapper.TracerouteMapper.selectTwoIP",paramsMap, new ResultHandler() {
				
            @Override
            public void handleResult(ResultContext resultContext) {
            	
            	Map<String,String> map = (Map<String, String>) resultContext.getResultObject();
            	resultList.add(map);
            }

        });
		
		return resultList;
//		return twoThreadSelectUtil.tracerouteMapper.selectTwoIP(date, ip_A, ip_B);
  
	}
	 
	public List<Map<String, String>> treacerouteFastSelectTwoIPService(String date,String ip_A,String ip_B) throws Exception {
		
		// 定义固定长度的线程池 
		ExecutorService executorService = Executors.newFixedThreadPool(50);
		
		// 所有线程产生结果
        List<Callable<List<Map<String,String>>>> allCallableResult = new ArrayList<Callable<List<Map<String,String>>>>();
        //返回结果
        List<Map<String,String>> resultList = new ArrayList<Map<String,String>>();
  
        for (int i = 1; i <= 50; i++) {
        	int sign = i;
        	Callable<List<Map<String,String>>> callableList = new CallableImpl(date,ip_A,ip_B,sign);
        	allCallableResult.add(callableList);
        }        
		
		//Future用于获取结果
        List<Future<List<Map<String, String>>>> futureList = executorService.invokeAll(allCallableResult);
        
        //处理线程返回结果
        for (Future<List<Map<String, String>>> future:futureList){
        	
        	if(future.get().size() > 0) {
        		System.out.println(" future.get() = "+future.get().size());
        		resultList.addAll(future.get());
        		
        	}
        	
        }
        
        System.out.println(" finally ");
        //关闭线程连接池
        executorService.shutdownNow();
		return resultList;
	}

}
