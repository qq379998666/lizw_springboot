package lizw.springboot.traceSourceMain;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.piccolo.io.FileFormatException;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtil {
    private static final String EXTENSION_XLS = "xls";
    private static final String EXTENSION_XLSX = "xlsx";


    /**
     * 判断EXCEL版本
     *
     * @param in
     * @param filename
     * @return
     * @throws IOException
     */
    public static Workbook getWorkbook(InputStream in, String filename) throws IOException {
        Workbook wb = null;
        if (filename.endsWith(EXTENSION_XLS)) {
            wb = new HSSFWorkbook(in);//Excel 2003
        } else if (filename.endsWith(EXTENSION_XLSX)) {
            wb = new XSSFWorkbook(in);//Excel 2007
        }
        return wb;
    }


    /**
     * 文件校验是否是excel
     *
     * @param filePath
     * @throws FileNotFoundException
     * @throws FileFormatException
     */
    private static void preReadCheck(String filePath) throws FileNotFoundException,
            FileFormatException {
        // 常规检查
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException("传入的文件不存在：" + filePath);
        }

        if (!(filePath.endsWith(EXTENSION_XLS) || filePath
                .endsWith(EXTENSION_XLSX))) {
            throw new FileFormatException("传入的文件不是excel");
        }
    }


    /**
     * 读取EXCEL
     *
     * @param filePath
     * @throws FileNotFoundException
     * @throws FileFormatException
     */
    public static List<List<String>> readExcel(String filePath) throws FileNotFoundException, FileFormatException {
        // 检查
        preReadCheck(filePath);
        // 获取workbook对象
        Workbook workbook = null;
        InputStream is = new FileInputStream(filePath);
        List<List<String>> result = new ArrayList<List<String>>();
        try {
            workbook = getWorkbook(is, filePath);
            // workbook = WorkbookFactory.create(is);

            int sheetCount = workbook.getNumberOfSheets();  //Sheet的数量
            // 读文件 一个sheet一个sheet地读取
            for (int numSheet = 0; numSheet < sheetCount; numSheet++) {
                Sheet sheet = workbook.getSheetAt(numSheet);
                if (sheet == null) {
                    continue;
                }

                int firstRowIndex = sheet.getFirstRowNum();
                int lastRowIndex = sheet.getLastRowNum();

                if (firstRowIndex != lastRowIndex && lastRowIndex != 0) {

                    // 读取数据行
                    for (int rowIndex = firstRowIndex+1; rowIndex <= lastRowIndex; rowIndex++) {
                        Row currentRow = sheet.getRow(rowIndex);// 当前行
                        int firstColumnIndex = currentRow.getFirstCellNum(); // 首列
                        int lastColumnIndex = currentRow.getLastCellNum();// 最后一列
                        List<String> rowList = new ArrayList<String>();
                        for (int columnIndex = firstColumnIndex; columnIndex < lastColumnIndex; columnIndex++) {
                            Cell currentCell = currentRow.getCell(columnIndex);// 当前单元格

                            String currentCellValue = getCellValue(currentCell, true);// 当前单元格的值
                            rowList.add(currentCellValue);
                        }
                        result.add(rowList);
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return result;
    }

    /**
     * 取单元格的值
     *
     * @param cell       单元格对象
     * @param treatAsStr 为true时，当做文本来取值 (取到的是文本，不会把“1”取成“1.0”)
     * @return
     */
    private static String getCellValue(Cell cell, boolean treatAsStr) {
        if (cell == null) {
            return "";
        }

       /* if (treatAsStr) {
            // 虽然excel中设置的都是文本，但是数字文本还被读错，如“1”取成“1.0”
            // 加上下面这句，临时把它当做文本来读取
            cell.setCellType(Cell.CELL_TYPE_STRING);
        }*/
        //SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

        String cellValue = null;
        int cellType = cell.getCellType();
        switch (cellType) {
            case Cell.CELL_TYPE_STRING: // 文本
                cellValue = cell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_NUMERIC: // 数字、日期
               /* if (DateUtil.isCellDateFormatted(cell)) {
                    DateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
                    cellValue = sdf.format(cell.getDateCellValue()); // 日期型
                } else {
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    cellValue = String.valueOf(cell.getNumericCellValue()); // 数字
                }
                break;*/
                if (DateUtil.isCellDateFormatted(cell)) {
                    DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    return sdf.format(cell.getDateCellValue());
                }
                cell.setCellType(Cell.CELL_TYPE_STRING);
                return cell.getStringCellValue();

            case Cell.CELL_TYPE_BOOLEAN: // 布尔型
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_BLANK: // 空白
                cellValue = cell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_ERROR: // 错误
                cellValue = "错误";
                break;
            case Cell.CELL_TYPE_FORMULA: // 公式
               /* try {
                    cellValue = cell.getStringCellValue();
                } catch (IllegalStateException e) {
                    cellValue = String.valueOf(cell.getNumericCellValue());
                }*/
                try {
                    cellValue = String.valueOf(cell.getNumericCellValue());
                } catch (IllegalStateException e) {
                    cellValue = String.valueOf(cell.getRichStringCellValue());
                }
                break;
            default:
                cellValue = "错误";
        }
        return cellValue;
    }
}

