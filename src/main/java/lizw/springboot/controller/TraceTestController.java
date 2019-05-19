package lizw.springboot.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;
import org.springframework.beans.factory.annotation.Autowired;

import lizw.springboot.entity.TraceAnalysisEntity;
import lizw.springboot.mapper.TraceAnalysisMapper;
import lizw.springboot.traceSourceMain.IP;
import lizw.springboot.utils.PagingHandler;


public class TraceTestController extends DefaultHandler{
	
	@Autowired
	private static TraceAnalysisMapper traceAnalysisMapper;

	public static void main(String[] args) throws FileNotFoundException, IOException, OpenXML4JException, SAXException {
		/*
		File excelFile = new File("D:\\test\\trace20181012v1.xlsx");
		Workbook sheets = new XSSFWorkbook(new FileInputStream(excelFile));
		Sheet sheet = sheets.getSheetAt(0);
        */
        
		File excelFile = new File("D:\\test\\trace20181012v1.xlsx");
		
		OPCPackage oPCPackage = OPCPackage.open(excelFile);
		XSSFReader xSSFReader = new XSSFReader(oPCPackage);
		SharedStringsTable sharedStringsTable = xSSFReader.getSharedStringsTable();
		
		XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
		
		ContentHandler handler = new PagingHandler(sharedStringsTable);
		parser.setContentHandler(handler);
		
		/*
		int firstRowIndex = sheet.getFirstRowNum();// 获取sheet的第一行
		int lastRowIndex = sheet.getLastRowNum();// 获取sheet的最后一行

		TraceAnalysisEntity traceAnalysisEntity = new TraceAnalysisEntity();
		
        
		if (firstRowIndex != lastRowIndex - 1 && lastRowIndex != 0) {// 判断第一行和最后一行不是同一行
			for (int rowIndex = firstRowIndex + 1; rowIndex <= lastRowIndex - 1; rowIndex++) {
				// 获取当前行
				Row currentRow = sheet.getRow(rowIndex);

				int firstColumnIndex = currentRow.getFirstCellNum(); // 获取第一列
				int lastColumnIndex = currentRow.getLastCellNum();// 获取最后一列

				//List<String> rowList = new ArrayList<String>();

				for (int columnIndex = firstColumnIndex; columnIndex < lastColumnIndex; columnIndex++) {

					// 当前单元格
					Cell currentCell = currentRow.getCell(columnIndex);
					
					//设置单元格数据类型
					currentCell.setCellType(CellType.STRING);

					// 当前单元格的值
					String currentCellValue = currentCell.getStringCellValue();//ex.getCellValue(currentCell, true);
					System.out.print("currentCellValue =="+currentCellValue);

					// 当单元格里的值为空停止
					if (StringUtils.isBlank(currentCellValue)) {
						break;
					}
					
					//traceAnalysisEntity.setId(1);

					if (columnIndex == firstColumnIndex) {
						traceAnalysisEntity.setHop(Integer.valueOf(currentCellValue));
					} else if (columnIndex == firstColumnIndex + 1) {
						String ip = currentCellValue;
						traceAnalysisEntity.setA_ip(ip);

						IP.load("D:\\workspace\\lizw_springboot\\src\\main\\resources\\IpData\\ip.dat");// 引入的文件
						String[] ipArray = IP.find(ip);
						for (int i = 0; i < ipArray.length; i++) {
							if (i == 0) {
								traceAnalysisEntity.setA_country(ipArray[i].toString());
							} else if (i == 1) {
								traceAnalysisEntity.setA_province(ipArray[i].toString());
							} else if (i == 2) {
								traceAnalysisEntity.setA_city(ipArray[i].toString());
							} else if (i == 3) {
								
							} else if (i == 4) {
								traceAnalysisEntity.setA_operator(ipArray[i].toString());
							}
						}
					} else if (columnIndex == firstColumnIndex + 2) {
						traceAnalysisEntity.setA_p(currentCellValue.toString());
					}
				}

				// 获取当前行的下一行
				Row secondCurrentRow = sheet.getRow(rowIndex + 1);

				int secondRowFirstColumnIndex = secondCurrentRow.getFirstCellNum(); // 获取当前行的下一行第一列
				int secondLastColumnIndex = secondCurrentRow.getLastCellNum();// 获取当前行的下一行最后一列

				for (int secondRowColumnIndex = secondRowFirstColumnIndex; secondRowColumnIndex < secondLastColumnIndex; secondRowColumnIndex++) {

					// 当前单元格
					Cell secondCurrentCell = secondCurrentRow.getCell(secondRowColumnIndex);
					
					//设置单元格数据类型
					secondCurrentCell.setCellType(CellType.STRING);

					// 当前单元格的值
					String secondCurrentCellValue = secondCurrentCell.getStringCellValue();//ex.getCellValue(secondCurrentCell, true);

					// 当单元格里的值为空停止
					if (StringUtils.isBlank(secondCurrentCellValue)) {
						break;
					}

					if (secondRowColumnIndex == secondRowFirstColumnIndex) {

					} else if (secondRowColumnIndex == secondRowFirstColumnIndex + 1) {
				        
						String ip = secondCurrentCellValue;
						traceAnalysisEntity.setB_ip(ip);

						IP.load("D:\\workspace\\lizw_springboot\\src\\main\\resources\\IpData\\ip.dat");// 引入的文件
						String[] ipArray = IP.find(ip);
						for (int i = 0; i < ipArray.length; i++) {
							if (i == 0) {
								traceAnalysisEntity.setB_country(ipArray[i].toString());
							} else if (i == 1) {
								traceAnalysisEntity.setB_province(ipArray[i].toString());
							} else if (i == 2) {
								traceAnalysisEntity.setB_city(ipArray[i].toString());
							} else if (i == 3) {
								
							} else if (i == 4) {
								traceAnalysisEntity.setB_operator(ipArray[i].toString());
							}
						}

					} else if (secondRowColumnIndex == secondRowFirstColumnIndex + 2) {
						traceAnalysisEntity.setB_p(secondCurrentCellValue.toString());
						break;
					}
				}

				System.out.println("  ");
				
				System.out.println("getId=="+traceAnalysisEntity.getId());
				System.out.println("getHop=="+traceAnalysisEntity.getHop());
				System.out.println("getA_country=="+traceAnalysisEntity.getA_country());
				System.out.println("getA_province=="+traceAnalysisEntity.getA_province());
				System.out.println("getA_city=="+traceAnalysisEntity.getA_city());
				System.out.println("getA_ip=="+traceAnalysisEntity.getA_ip());
				System.out.println("getA_operator=="+traceAnalysisEntity.getA_operator());
				System.out.println("getA_p=="+traceAnalysisEntity.getA_p());
				System.out.println("getB_country=="+traceAnalysisEntity.getB_country());
				System.out.println("getB_province=="+traceAnalysisEntity.getB_province());
				System.out.println("getB_city=="+traceAnalysisEntity.getB_city());
				System.out.println("getB_ip=="+traceAnalysisEntity.getB_ip());
				System.out.println("getB_operator=="+traceAnalysisEntity.getB_operator());
				System.out.println("getB_p=="+traceAnalysisEntity.getB_p());
				TraceAnalysisService traceAnalysisService = new TraceAnalysisService();
//				int count = 0;
				int resultInt = 0;
				//resultInt += traceAnalysisMapper.traceAnalysisMapper(traceAnalysisEntity);
				resultInt += traceAnalysisService.traceAnalysisExcel(traceAnalysisEntity);
				System.out.println("resultInt====="+resultInt);
				System.gc();
//				count++;
//				if(resultInt == 0) {
//					System.out.println("----------------------------------------------------------------------------------------------"+count);
				}
				
			}
			*/

	}
	

}
