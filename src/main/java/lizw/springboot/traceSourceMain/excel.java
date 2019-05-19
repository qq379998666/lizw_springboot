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

/**
 * 读取EXCEL内容
 *
 * @author lxr
 */
public class excel {
    public final String EXTENSION_XLS = "xls";
    public final String EXTENSION_XLSX = "xlsx";


    /**
     * 判断EXCEL版本
     *
     * @param in
     * @param filename
     * @return
     * @throws IOException
     */
    public Workbook getWorkbook(InputStream in, String filename) throws IOException {
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
    public void preReadCheck(String filePath) throws FileNotFoundException,
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
     * 取单元格的值
     *
     * @param cell       单元格对象
     * @param treatAsStr 为true时，当做文本来取值 (取到的是文本，不会把“1”取成“1.0”)
     * @return
     */
    public String getCellValue(Cell cell, boolean treatAsStr) {
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
                    DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
