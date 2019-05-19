package lizw.springboot.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.mybatis.spring.batch.MyBatisCursorItemReader;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lizw.springboot.callableImpl.TwoThreadSelectCallableImpl;
import lizw.springboot.service.TreacerouteService;

/*
 * traceroute提供的外部接口
 * 包括：单IP查询，双IP查询，查询数据在50万以内的导出Excel，查询数据在100万以内的导出Excel，省间查询，城市查询
 */

@Controller
@RequestMapping(value="/TracerouteController")
public class TracerouteController {
	
	@Autowired
	private TreacerouteService treacerouteService;
	
	@Autowired
	SqlSessionFactory sqlSessionFactory;
	
	@ResponseBody
	@RequestMapping(value="/treacerouteSelectOneIPController")
	public void treacerouteSelectOneIPController(HttpServletRequest requset,HttpServletResponse response) {
		
		/*
		 * 接口：查询数据在100万以内的导出Excel
		 * 
		 * 该接口利用Mysql的：流式查询，springboot+Mybatis
		 * 流式查询：当查询的数据超过50万条的时候，需要注意内存溢出，流式查询可以解决这样的问题
		 * 内存溢出的原因：某一个线程里面执行数据库查询时候，其实这个请求首先会调用mysql驱动程序
		 *             MySQL驱动内则会把符合条件的数据缓存到驱动内，等服务器返回了所有符合条件的数据后，在一下子把缓存里面的数据返回给调用sql的应用程序。
		 */
		
		
		String date = "traceroute201809";
		String ip_A = "219.158.98.237";
		String ip_B = "219.158.8.98";
		/*
		String date = requset.getParameter("date");
		String ip = requset.getParameter("ip");

		List<Map<String, String>> map = treacerouteService.treacerouteService(date, ip);
		
		Map<String,Object> resultMap =new HashMap<String,Object>();
		resultMap.put("resultMap", map);
		return resultMap;
		*/
		
		// 开始时间
		long  startTime = System.currentTimeMillis();	
		System.out.println(" 开始时间 : " + startTime);
	    
//		String date = requset.getParameter("date");
//		String ip_A = requset.getParameter("ipA");
//		String ip_B = requset.getParameter("ipB");
			
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

//                System.out.println(resultContext.getResultCount());
            	resultList.add(map);

            }

        });
		
		// 最重要的就是使用SXSSFWorkbook，表示流的方式进行操作
		// 在内存中保持100行，超过100行将被刷新到磁盘
		SXSSFWorkbook wb = new SXSSFWorkbook(100);
		
		// 创建sheet页
		Sheet sheet = wb.createSheet("查询结果"); 
		
		//定义表头
		Row Row = sheet.createRow(0);
	    Row.createCell(0).setCellValue("源IP");  
	    Row.createCell(1).setCellValue("源IP省份");  
	    Row.createCell(2).setCellValue("源IP运营商");  
	    Row.createCell(3).setCellValue("目的IP");
	    Row.createCell(4).setCellValue("目的IP省份");
	    Row.createCell(5).setCellValue("目的IP运营商");
	    Row.createCell(6).setCellValue("跳数");  
	    Row.createCell(7).setCellValue("当前IP");  
	    Row.createCell(8).setCellValue("当前IP国家");  
	    Row.createCell(9).setCellValue("当前IP省份");
	    Row.createCell(10).setCellValue("当前IP城市");
	    Row.createCell(11).setCellValue("时延");

		for (int i = 1; i <= resultList.size(); i++) {
			Row nRow = sheet.createRow(i);
			nRow.createCell(0).setCellValue(resultList.get(i-1).get("F_ip"));
			nRow.createCell(1).setCellValue(resultList.get(i-1).get("F_province"));
			nRow.createCell(2).setCellValue(resultList.get(i-1).get("F_operator"));
			nRow.createCell(3).setCellValue(resultList.get(i-1).get("L_ip"));
			nRow.createCell(4).setCellValue(resultList.get(i-1).get("L_province"));
			nRow.createCell(5).setCellValue(resultList.get(i-1).get("L_operator"));
			nRow.createCell(6).setCellValue(String.valueOf(resultList.get(i-1).get("hop")));
			nRow.createCell(7).setCellValue(resultList.get(i-1).get("B_ip"));
			nRow.createCell(8).setCellValue(resultList.get(i-1).get("B_country"));
			nRow.createCell(9).setCellValue(resultList.get(i-1).get("B_province"));
			nRow.createCell(10).setCellValue(resultList.get(i-1).get("B_city"));
			nRow.createCell(11).setCellValue(resultList.get(i-1).get("hop_time"));
		}
		
//		String flag = null;// 设置返回值标志
//		Map<String,String> flagMap = new HashMap<String, String>();
//		FileOutputStream fileOutputStream;
//		try {
//			fileOutputStream = new FileOutputStream("D:/lizhengwei.xlsx");
//			wb.write(fileOutputStream); //将数据写入Excel
//			fileOutputStream.flush(); // 刷新缓冲区
//			fileOutputStream.close();
//			flag = "1";
//		} catch (FileNotFoundException e) {
//			flag = "0";
//			e.printStackTrace();
//		} catch (IOException e) {
//			flag = "0";
//			e.printStackTrace();
//		}finally {
//			flagMap.put("flagMap", flag);
//		}
		
		response.setHeader("Content-Disposition", "attachment; filename=appointmentUser.xls");
        response.setContentType("application/vnd.ms-excel; charset=utf-8");
		
        OutputStream out = null;
        try {
        	out = response.getOutputStream();
        	wb.write(out);
            out.flush();
			out.close();
        }catch (Exception e) {
        	e.printStackTrace();
        }
		
		// 处理完成时间		
		long finishedTime = System.currentTimeMillis();
		System.out.println("数据读取完成耗时 : " + (finishedTime - startTime) / 1000 + "m");
		
	}
	

	@ResponseBody
	@RequestMapping(value="/treacerouteFiftyExportExcelController")
	public Map<String, String> treacerouteFiftyExportExcelController(HttpServletRequest requset,HttpServletResponse response) throws IOException {
		
		/*
		 * 查询数据在50万以内的导出Excel
		 */
		
		// 开始时间
		long  startTime = System.currentTimeMillis();	
		System.out.println(" 开始时间 : " + startTime);
		
//		String date = requset.getParameter("date");
//		String ip_A = requset.getParameter("ipA");
//		String ip_B = requset.getParameter("ipB");
		
		String date = "traceroute201810";
		String ip_A = "221.183.12.181";
		String ip_B = "221.176.18.42";
		
		List<Map<String, String>> resultMap = treacerouteService.treacerouteSelectTwoIPService(date, ip_A, ip_B);
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		// 最重要的就是使用SXSSFWorkbook，表示流的方式进行操作
		// 在内存中保持100行，超过100行将被刷新到磁盘
		SXSSFWorkbook wb = new SXSSFWorkbook(100);
		
		// 创建sheet页
		Sheet sheet = wb.createSheet("查询结果"); 
		
		//定义表头
		Row Row = sheet.createRow(0);
	    Row.createCell(0).setCellValue("源IP");  
	    Row.createCell(1).setCellValue("源IP省份");  
	    Row.createCell(2).setCellValue("源IP运营商");  
	    Row.createCell(3).setCellValue("目的IP");
	    Row.createCell(4).setCellValue("目的IP省份");
	    Row.createCell(5).setCellValue("目的IP运营商");
	    Row.createCell(6).setCellValue("跳数");  
	    Row.createCell(7).setCellValue("当前IP");  
	    Row.createCell(8).setCellValue("当前IP国家");  
	    Row.createCell(9).setCellValue("当前IP省份");
	    Row.createCell(10).setCellValue("当前IP城市");
	    Row.createCell(11).setCellValue("时延");

		for (int i = 1; i <= resultMap.size(); i++) {
			Row nRow = sheet.createRow(i);
			nRow.createCell(0).setCellValue(resultMap.get(i-1).get("F_ip"));
			nRow.createCell(1).setCellValue(resultMap.get(i-1).get("F_province"));
			nRow.createCell(2).setCellValue(resultMap.get(i-1).get("F_operator"));
			nRow.createCell(3).setCellValue(resultMap.get(i-1).get("L_ip"));
			nRow.createCell(4).setCellValue(resultMap.get(i-1).get("L_province"));
			nRow.createCell(5).setCellValue(resultMap.get(i-1).get("L_operator"));
			nRow.createCell(6).setCellValue(String.valueOf(resultMap.get(i-1).get("hop")));
			nRow.createCell(7).setCellValue(resultMap.get(i-1).get("B_ip"));
			nRow.createCell(8).setCellValue(resultMap.get(i-1).get("B_country"));
			nRow.createCell(9).setCellValue(resultMap.get(i-1).get("B_province"));
			nRow.createCell(10).setCellValue(resultMap.get(i-1).get("B_city"));
			nRow.createCell(11).setCellValue(resultMap.get(i-1).get("hop_time"));
		}
		
		String flag = null;// 设置返回值标志
		Map<String,String> flagMap = new HashMap<String, String>();
		FileOutputStream fileOutputStream;
		try {
			fileOutputStream = new FileOutputStream("D:/lizhengwei.xlsx");
			wb.write(fileOutputStream); //将数据写入Excel
			fileOutputStream.flush(); // 刷新缓冲区
			fileOutputStream.close();
			flag = "1";
		} catch (FileNotFoundException e) {
			flag = "0";
			e.printStackTrace();
		} catch (IOException e) {
			flag = "0";
			e.printStackTrace();
		}finally {
			flagMap.put("flagMap", flag);
		}
		
		
		long stopTime = System.currentTimeMillis(); // 写文件时间
		System.out.println("数据写入Excel表格中耗时 : " + (stopTime - startTime) / 1000 + "m");
		
		return flagMap;
	}
	@ResponseBody
	@RequestMapping(value="/treacerouteSixExportExcelController")
	public Map<String, String> treacerouteSixExportExcelController(HttpServletRequest requset,HttpServletResponse response) throws IOException {
		
		/*
		 * 查询数据在6万以内的导出Excel
		 * HSSFWorkbook:是操作Excel2003以前（包括2003）的版本，扩展名是.xls,超出65536条后系统就报错
		 * XSSFWorkbook:是操作Excel2007的版本，扩展名是.xlsx,最多可以导出104万行
		 */
		
		// 开始时间
		long  start = System.currentTimeMillis();	
		System.out.println(" 开始时间 : " + start);
		
//		String date = requset.getParameter("date");
//		String ip_A = requset.getParameter("ipA");
//		String ip_B = requset.getParameter("ipB");
		
		String date = "traceroute201810";
		String ip_A = "221.183.12.181";
		String ip_B = "221.176.18.42";
		
		List<Map<String, String>> resultList = treacerouteService.treacerouteSelectTwoIPService(date, ip_A, ip_B);
				
		//第一步,创建一个workbook,对应一个Excel文件
		HSSFWorkbook workbook = new HSSFWorkbook();
		
		//第二步,创建一个sheet
		HSSFSheet sheet = workbook.createSheet("查询结果 ");
		
		//第三步,在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  		
		HSSFRow firstRow = sheet.createRow(0);  
		
		// 第四步，创建单元格，并设置值表头 设置表头居中  
		//HSSFCellStyle style = workbook.createCellStyle();        
		
		//设置行的标题头
		firstRow.createCell(0).setCellValue("源IP");
		firstRow.createCell(1).setCellValue("源省份");
		firstRow.createCell(2).setCellValue("源运营商");
		firstRow.createCell(3).setCellValue("目的IP");
		firstRow.createCell(4).setCellValue("目的省份");
		firstRow.createCell(5).setCellValue("目的运营商");
		firstRow.createCell(6).setCellValue("跳数");
//		firstRow.createCell(7).setCellValue("A_ip");
//		firstRow.createCell(8).setCellValue("A_province");
//		firstRow.createCell(9).setCellValue("A_city");
//		firstRow.createCell(10).setCellValue("A_operator");
		firstRow.createCell(11).setCellValue("当前IP");
		firstRow.createCell(12).setCellValue("当前IP省份");
		firstRow.createCell(13).setCellValue("当前IP城市");
		firstRow.createCell(14).setCellValue("当前IP运营商");
		firstRow.createCell(15).setCellValue("时延");
		
		//设置查询到的值到表格
		for (int i = 0; i < resultList.size(); i++) {
			HSSFRow row = sheet.createRow(i+1);
			
			row.createCell(0).setCellValue(resultList.get(i).get("F_ip"));
			row.createCell(1).setCellValue(resultList.get(i).get("F_province"));
			row.createCell(2).setCellValue(resultList.get(i).get("F_operator"));
			row.createCell(3).setCellValue(resultList.get(i).get("L_ip"));
			row.createCell(4).setCellValue(resultList.get(i).get("L_province"));
			row.createCell(5).setCellValue(resultList.get(i).get("L_operator"));
			row.createCell(6).setCellValue(String.valueOf(resultList.get(i).get("hop")));
//			row.createCell(7).setCellValue(map.get(i).get("A_ip"));
//			row.createCell(8).setCellValue(map.get(i).get("A_province"));
//			row.createCell(9).setCellValue(map.get(i).get("A_city"));
//			row.createCell(10).setCellValue(map.get(i).get("A_operator"));
			row.createCell(11).setCellValue(resultList.get(i).get("B_ip"));
			row.createCell(12).setCellValue(resultList.get(i).get("B_province"));
			row.createCell(13).setCellValue(resultList.get(i).get("B_city"));
			row.createCell(14).setCellValue(resultList.get(i).get("B_operator"));
			row.createCell(15).setCellValue(resultList.get(i).get("hop_time"));
		}
		
		response.setHeader("Content-Disposition", "attachment; filename=appointmentUser.xls");
        response.setContentType("application/vnd.ms-excel; charset=utf-8");
        OutputStream out = response.getOutputStream();
		
        //设置返回集合
        Map<String,String> flagMap = new HashMap<String, String>();
        String flag = null;
        try {            
            workbook.write(out);
            flag = "导出成功";
        }catch (Exception e) {
        	e.printStackTrace();
        	flag = "导出失败";
        }finally {
        	flagMap.put("flag", flag);
        	out.flush();
            out.close();
        }
		
		//终止时间
		long end = System.currentTimeMillis();
        System.out.println(" 线程查询数据用时 :"+(end-start)+"ms");
        
		return flagMap;
		
	}
	
	@ResponseBody
	@RequestMapping(value="/TwoThreadSelectController")
	public Map<String, String> TwoThreadSelectController(HttpServletRequest requset,HttpServletResponse response) throws Exception {
		
		/*
		 * 两个IP多线程查询 部署在项目上的查询接口
		 */
		
		//开始时间
		long start = System.currentTimeMillis();
		
		String date = requset.getParameter("date");
		String ip_A = requset.getParameter("ipA");
		String ip_B = requset.getParameter("ipB");
		 	
    	TwoThreadSelectCallableImpl twoThreadSelectCallableImpl1 = new TwoThreadSelectCallableImpl(date,ip_A,ip_B,"t1");
    	TwoThreadSelectCallableImpl twoThreadSelectCallableImpl2 = new TwoThreadSelectCallableImpl(date,ip_A,ip_B,"t2");
    	Thread t1 = new Thread(twoThreadSelectCallableImpl1);
    	Thread t2 = new Thread(twoThreadSelectCallableImpl2);
    	t1.start();
    	t2.start();
        
    	//返回结果
        Map<String,String> resultMap = new HashMap<String,String>();
        resultMap.put("resultMap", "正在导出");
		
		//终止时间
		long end = System.currentTimeMillis();
        System.out.println(" 线程查询数据用时 :"+(end-start)/1000+"ms");
		
		return resultMap;
	}
	
	@ResponseBody
	@RequestMapping(value="/UploadFileController")
	public void UploadFileController(HttpServletRequest requset,HttpServletResponse response) throws Exception {
		
		/*
		 * 两个IP多线程查询 部署在项目上的查询接口
		 */
		
		String path = "D:\\IPinfo.xlsx";
		File file = new File(path);
		//防止乱码
		String fileName = new String("IPinfo.xlsx".getBytes("UTF-8"), "iso-8859-1");
		
		response.reset();
        response.setHeader("Accept-Ranges", "bytes");
        response.setHeader("Content-disposition", String.format("attachment; filename=\"%s\" ", fileName));//设置文件下载是以附件的形式下载
        response.addHeader("Content-Length", String.valueOf(file.length()));//设置文件
        
		Map<String,String> map = new HashMap<String, String>(); //返回值集合
        int sign ;
        FileInputStream in = null;
        OutputStream out = null;
        try {
			//读取要下载的文件,保存到文件输入流
			in = new FileInputStream(path);
			
			//创建输出流
			out = response.getOutputStream();
			
			//缓存区
			byte buffer[] = new byte[1024];
			
			//写入输出流中
//			int bufferLength = 0;
			while(in.read(buffer) > 0) {
//				bufferLength = in.read(buffer);
//				out.write(buffer, 0, bufferLength);
				out.write(buffer);
			}
			sign = 0;
		} catch (Exception e) {
			sign = 1;
			e.printStackTrace();
		}finally {
			in.close(); //关闭
			out.close();
			if(file.exists()) {
				file.delete();
			}
		}
        
        
	}
	
	@ResponseBody
	@RequestMapping(value="/treacerouteFastSelectTwoIPController")
	public Map<String, Object> treacerouteFastSelectTwoIPController(HttpServletRequest requset,HttpServletResponse response) throws Exception {
		
		/*
		 * 两个IP多线程查询
		 */
		
		//开始时间
		long start = System.currentTimeMillis();
		
		String date = requset.getParameter("date");
		String ip_A = requset.getParameter("ipA");
		String ip_B = requset.getParameter("ipB");
		
		List<Map<String, String>> list = treacerouteService.treacerouteFastSelectTwoIPService(date, ip_A, ip_B);
		
		System.out.println(" list.size = "+list.size());
				
		Map<String,Object> resultMap =new HashMap<String,Object>();
		resultMap.put("list", list);

		//终止时间
		long end = System.currentTimeMillis();
        System.out.println(" 线程查询数据用时 :"+(end-start)/1000+"ms");
		return null;
		
	}
	
	@ResponseBody
	@RequestMapping(value="/treacerouteSelectTwoIPController")
	public Map<String, Object> treacerouteSelectTwoIPController(HttpServletRequest requset) {
		
		/*
		 * 两个IP查询接口
		 */
		
		//开始时间
		long start = System.currentTimeMillis();
		
		String date = requset.getParameter("date");
		String ip_A = requset.getParameter("ipA");
		String ip_B = requset.getParameter("ipB");
		
		List<Map<String, String>> map = treacerouteService.treacerouteSelectTwoIPService(date, ip_A, ip_B);
		
		Map<String,Object> resultMap =new HashMap<String,Object>();
		resultMap.put("resultMap", map);
		
		//终止时间
		long end = System.currentTimeMillis();
        System.out.println("线程查询数据用时:"+(end-start) / 1000+"s");
		
		return null;
	}
	

	@ResponseBody
	@RequestMapping(value="/treacerouteSelectCityController")
	public Map<String, Object> treacerouteSelectCityController(HttpServletRequest requset) {
		
		String date = requset.getParameter("date");
		String operator ="nnn"; //requset.getParameter("operator");
		
		List<Map<String, String>> map = treacerouteService.treacerouteSelectCityService(date, operator);
		
		Map<String,Object> resultMap =new HashMap<String,Object>();
		resultMap.put("resultMap", map);
		return resultMap;
	}
	
	@ResponseBody
	@RequestMapping(value="/treacerouteSelectProvinceController")
	public Map<String, Object> treacerouteSelectProvinceController(HttpServletRequest requset) {
		
		String date = requset.getParameter("date");
		String operator = "nnn"; //requset.getParameter("operator");
		
		List<Map<String, String>> map = treacerouteService.treacerouteSelectProvinceService(date, operator);
		
		Map<String,Object> resultMap =new HashMap<String,Object>();
		resultMap.put("resultMap", map);
		return resultMap;
	}
	
	
	

}
