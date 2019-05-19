package lizw.springboot.controller;

import lizw.springboot.utils.AnalysisOID;
import lizw.springboot.utils.HttpUtil;
import lizw.springboot.utils.HttpUtilLizw;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lizhengwei
 * @date 2019年1月14日 下午5:09:09
 * 根据提供oid对返回的HTML前端代码标签进行解析，对XML标签中的值进行提取
 */
@Controller
@RequestMapping(value = "/OIDSelectController")
public class OIDSelectController {

    private String action = "display";
    private String URL = "http://www.oid-info.com/cgi-bin/display";

    @ResponseBody
    @RequestMapping(value = "/OIDSelect")
    public Map<String, Object> OIDSelect(HttpServletRequest request){
        String oid = request.getParameter("oid");
        String url = URL + "?oid=" + oid + "&action=" + action ;//拼接url
        Map<String,String> resultMap = new HashMap<String,String>();
        Map<String,Object> map = new HashMap<String,Object>();
        String result = null;
        try {
            result = HttpUtilLizw.sendGet(url, "UTF-8");//获取oid页面代码
            if (!"FIALED".equals(result)){//判断输入的oid的是否能查到
                resultMap = AnalysisOID.analysisOID(result);//对oid页面代码进行解析
                map.put("map",resultMap);
            }else{
                map.put("map","FIALED");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
