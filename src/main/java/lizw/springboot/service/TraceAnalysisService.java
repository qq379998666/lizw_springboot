package lizw.springboot.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lizw.springboot.entity.TraceAnalysisEntity;
import lizw.springboot.mapper.TraceAnalysisMapper;
import lizw.springboot.traceSourceMain.IP;
import lizw.springboot.traceSourceMain.excel;

@Service
public class TraceAnalysisService {

	@Autowired
	private TraceAnalysisMapper traceAnalysisMapper;

	public int readTraceAnalysisExcel(String filePath, String filename) throws IOException {

		excel ex = new excel();
		ex.preReadCheck(filePath);// 检查

		// 获取输入流的文件
		InputStream inputStream = new FileInputStream(filePath);

		// 检查 Excel 版本
		Workbook workbook = null;
		workbook = ex.getWorkbook(inputStream, filePath);

		// 获取第一个sheet
		Sheet sheet = workbook.getSheetAt(0);

		int firstRowIndex = sheet.getFirstRowNum();// 获取sheet的第一行
		int lastRowIndex = sheet.getLastRowNum();// 获取sheet的最后一行

		TraceAnalysisEntity traceAnalysisEntity = new TraceAnalysisEntity();
		int resultInt = 0;

		if (firstRowIndex != lastRowIndex - 1 && lastRowIndex != 0) {// 判断第一行和最后一行不是同一行
			for (int rowIndex = firstRowIndex + 1; rowIndex <= lastRowIndex - 1; rowIndex++) {
				// 获取当前行
				Row currentRow = sheet.getRow(rowIndex);

				int firstColumnIndex = currentRow.getFirstCellNum(); // 获取第一列
				int lastColumnIndex = currentRow.getLastCellNum();// 获取最后一列

				List<String> rowList = new ArrayList<String>();

				for (int columnIndex = firstColumnIndex; columnIndex < lastColumnIndex; columnIndex++) {

					// 当前单元格
					Cell currentCell = currentRow.getCell(columnIndex);

					// 当前单元格的值
					String currentCellValue = ex.getCellValue(currentCell, true);

					// 当单元格里的值为空停止
					if (StringUtils.isBlank(currentCellValue)) {
						break;
					}

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

					// 当前单元格的值
					String secondCurrentCellValue = ex.getCellValue(secondCurrentCell, true);

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

				for (int i = 0; i < rowList.size(); i++) {
					System.out.print(rowList.get(i) + ",");
				}
				System.out.println("  ");

//				int count = 0;
				resultInt += traceAnalysisMapper.traceAnalysisMapper(traceAnalysisEntity);
				System.gc();
//				count++;
//				if(resultInt == 0) {
//					System.out.println("------------"+count);
//				}

			}

		}

		return resultInt;
	}
	
	public int writeData(TraceAnalysisEntity traceAnalysisEntity) {
		System.out.println("---");
		int resultInt =traceAnalysisMapper.traceAnalysisMapper(traceAnalysisEntity);
		return resultInt;		
	}
	


}
