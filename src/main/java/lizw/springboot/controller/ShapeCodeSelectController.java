package lizw.springboot.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lizw.springboot.utils.HttpUtil;
import lizw.springboot.utils.StringToJson;

/**
 * @author lizhengwei
 * @date 2019年1月14日 下午6:01:06
 * 条形码解析接口
 */
@Controller
@RequestMapping(value = "/ShapeCodeSelectController")
public class ShapeCodeSelectController {

    //public static final String APPKEY = "e14d2c39f3a46574";// 你的appkey
    //public static final String URL = "http://api.jisuapi.com/barcode2/query";
    //public static final String barcode = "6917878036526";

    private String APPKEY = "e14d2c39f3a46574";// 你的appkey
    private String URL = "http://api.jisuapi.com/barcode2/query";
//    private String barcode = "6917878036526";

    @ResponseBody
    @RequestMapping(value = "/shapeCodeSelect")
    public Map<String, Object> shapeCodeSelect(HttpServletRequest request){
        String code = request.getParameter("code");
        String url = URL + "?appkey=" + APPKEY + "&barcode=" + code;
        System.out.println("拼接url的路径:  "+url);
        String result = null;
        Map<String,String> stringToJsonMap = new HashMap<String,String>();
        try {
            result = HttpUtil.sendGet(url, "UTF-8");
            //result = HttpUtil.sendGet("http://www.oid-info.com/cgi-bin/display?oid=1.2.250.1&action=display", "UTF-8");
            System.out.println(result);
            stringToJsonMap  = StringToJson.stringToJson(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("map",stringToJsonMap);
        return map;
    }
}
