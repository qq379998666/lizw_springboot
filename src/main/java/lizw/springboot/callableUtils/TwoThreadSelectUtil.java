package lizw.springboot.callableUtils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lizw.springboot.mapper.TracerouteMapper;
import lizw.springboot.service.TreacerouteService;

@Component
public class TwoThreadSelectUtil {
	
	@Autowired
	private TreacerouteService treacerouteService;
	
	public static TwoThreadSelectUtil twoThreadSelectUtil;
	
	@PostConstruct
	public void init() {
		twoThreadSelectUtil = this;
		twoThreadSelectUtil.treacerouteService = this.treacerouteService;
	}

	//第二条线程查询数据
	public List<Map<String,String>> twoThreadSelectU(String date,String ip_A,String ip_B) throws Exception {
		return treacerouteService.treacerouteTwoThreadSelectService(date, ip_A, ip_B);
	}
	
	public void exportExcelU(List<Map<String,String>> resultList) {
		
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
		
		System.out.println(System.getProperty("java.class.path")); 
		
		//获取当前时间
//		long time = System.currentTimeMillis();
//		String name = String.valueOf(time);
		String name = "IPinfo";
				
		String flag = null;// 设置返回值标志
//		Map<String,String> flagMap = new HashMap<String, String>();
		FileOutputStream fileOutputStream;
		try {
			fileOutputStream = new FileOutputStream("D:/"+name+".xlsx");
			wb.write(fileOutputStream); //将数据写入Excel
			fileOutputStream.flush(); // 刷新缓冲区
			fileOutputStream.close();
			flag = "导出成功";
		} catch (FileNotFoundException e) {
			flag = "导出失败";
			e.printStackTrace();
		} catch (IOException e) {
			flag = "导出失败";
			e.printStackTrace();
		}finally {
//			flagMap.put("flagMap", flag);
			System.out.println(flag);
		}

	}

}
