package lizw.springboot.traceSourceMain;

import java.io.InputStream;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import lizw.springboot.utils.PagingHandler;
import lizw.springboot.utils.PagingHandler_1;



public class XxlsPrint {

	public static void main(String[] args) throws Exception {
				
		String filepath = "D:\\test\\50w.xlsx";
		//String filepath = "D:\\test\\trace20181012v1.xlsx";
		//String filepath = "D:\\test\\trace20181012v2.xlsx";
		//String filepath = "D:\\test\\trace20181012v3.xlsx";
		//String filepath = "D:\\test\\trace20181012v4.xlsx";
		
		OPCPackage pkg = OPCPackage.open(filepath);
		XSSFReader r = new XSSFReader(pkg);
		SharedStringsTable sst = r.getSharedStringsTable();
		
		XMLReader parser = fetchSheetParser(sst);
		
		InputStream sheet1 = r.getSheet("rId1");
		
		InputSource sheetSource = new InputSource(sheet1);
		
		parser.parse(sheetSource);
		
		sheet1.close();
		
	}

	private static XMLReader fetchSheetParser(SharedStringsTable sst) throws SAXException {
		XMLReader parser = XMLReaderFactory.createXMLReader();
		ContentHandler handler = new PagingHandler(sst);
		
		parser.setContentHandler(handler);
		
		return parser;
	}
	
	

}
