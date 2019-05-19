package lizw.springboot.callableImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import lizw.springboot.callableUtils.SelectTwoIPFastUtil;


public class CallableImpl implements Callable<List<Map<String,String>>> {
		
	volatile static int count = 0;
	
	private String date;
	private String ip_A;
	private String ip_B;
	private int sign;
	
	//每条线程查询到的数据赋值给list
	List<Map<String,String>> list = new ArrayList<Map<String,String>>();
	
	public CallableImpl(String date,String ip_A,String ip_B,int sign) {
		this.date = date;
		this.ip_A = ip_A;
		this.ip_B = ip_B;
		this.sign = sign;
	}
		
	@Override
	public List<Map<String,String>> call() throws Exception {
		
		return execute();
		
	}
	
	public List<Map<String,String>> execute(){
		
	    int first;
		int last;
		
		if(sign >= 0) {
			first = (sign-1)*2000000;
			last = sign*2000000;
			
			SelectTwoIPFastUtil selectTwoIPFastUntil = new SelectTwoIPFastUtil();
			list = selectTwoIPFastUntil.SelectTwoIPFastU(date, ip_A, ip_B, first, last);
		}
		
		return list;
		
	}

}
