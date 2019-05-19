package lizw.springboot.controller;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.MultipartConfigElement;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import lizw.springboot.service.TraceAnalysisService;
import lizw.springboot.traceSourceMain.FileUtil;
import lizw.springboot.traceSourceMain.XxlsPrint;
import lizw.springboot.utils.PagingHandler;

@Controller
@RequestMapping(value = "/TraceAnalysisController")
public class TraceAnalysisController {
	
    
    
    @Autowired
    private TraceAnalysisService traceAnalysisService;

    @ResponseBody
    @RequestMapping(value = "/TraceAnalysis")
    public int traceAnalysis(MultipartHttpServletRequest request) throws IOException{
    	
    	int sign = 0;
    	
    	//getFileNames() 获取文件名字
    	Iterator<String> iter = request.getFileNames();
    	while (iter.hasNext()){
    		
    		//获取一个文件
    		String upLoadedFile = iter.next();
    		System.out.println("---------------------");
    		
    		//System.out.println("request.getFile()：" + request.getFile(upLoadedFile));
    		//获得上传excel
    	    MultipartFile multipartFile = request.getFile(upLoadedFile);
    	    
    	    System.out.println("multipartFile.getOriginalFilename()：" + multipartFile.getOriginalFilename());
    	    String filename = multipartFile.getOriginalFilename();//获取上传文件名
            String path = saveFile(multipartFile,filename);
            int  resultInt = traceAnalysisService.readTraceAnalysisExcel(path, filename);//读取Excel文件sheet1
            
            if(resultInt > 0) {
            	sign = 1;
            }else if(resultInt == 0) {
            	sign = 0;
            }
    	
    	}
		return sign;
    	   	
    }
    
    private String saveFile(MultipartFile multipartFile,String name){
        //获取当前时间
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String currentTime = dateFormat.format(date);
        //设置全路径
        String fileFullPath = "D:/test/"+currentTime+"/";
        //File fileDirectory = new File(fileFullPath);//查找该路径下是否有这个文件
        try {
            if(FileUtil.saveFile(fileFullPath,name,multipartFile)){//判断该路径下文件夹是否为空
                return fileFullPath + name;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
    
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //单个文件最大
        factory.setMaxFileSize("50MB");
        /// 设置总上传数据总大小
        factory.setMaxRequestSize("100MB");
        return factory.createMultipartConfig();
    }
    
    
    
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////    
    
  
    @ResponseBody
    @RequestMapping(value = "/traceAnalysisController")
    public int traceAnalysisController() throws IOException, OpenXML4JException, SAXException{
        String filepath = "D:\\test\\www.xlsx";
		
		OPCPackage pkg = OPCPackage.open(filepath);
		XSSFReader r = new XSSFReader(pkg);
		SharedStringsTable sst = r.getSharedStringsTable();
		
		XMLReader parser = fetchSheetParser(sst);
		
		InputStream sheet1 = r.getSheet("rId1");
		
		InputSource sheetSource = new InputSource(sheet1);
		System.out.println("================");
		parser.parse(sheetSource);
		System.out.println("---------------");
		sheet1.close();
    	return 0;
    }
    
    private static XMLReader fetchSheetParser(SharedStringsTable sst) throws SAXException {
		XMLReader parser = XMLReaderFactory.createXMLReader();
		ContentHandler handler = new PagingHandler(sst);
		
		parser.setContentHandler(handler);
		
		return parser;
	}
    
    

}
