package lizw.springboot.traceSourceMain;

import java.util.HashMap;
import java.util.Map;

import lizw.springboot.utils.HttpUtil;

public class IPSelectUtil {
	
	private static String URL = "http://freeapi.ipip.net/";
	
	public static Map<String, String> ipSelectUtil(String ip){
		
		//拼接查询IP链接
        String url = URL + ip;
        System.out.println("拼接url的路径:  "+url);
        
        String result = null;
        //Map<String,String> stringToJsonMap = new HashMap<String,String>();
        try {
            result = HttpUtil.sendGet(url, "UTF-8");
            System.out.println(result);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
		
				
	}

}
