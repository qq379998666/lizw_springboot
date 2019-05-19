package lizw.springboot.utils;

import net.sf.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author lizhengwei
 * @date 2019年1月14日 下午5:44:43
 * 为条形码查询出的返回得数据进行解析
 */
public class  StringToJson {

    public static Map<String, String> stringToJson(String string){
        JSONObject data = JSONObject.fromObject(string);//解析第一层数据
        int status = data.optInt("status");
        String msg = data.optString("msg");
        String result = data.optString("result");
        Map<String, String> map = new HashMap<String, String>();
        if(status == 0) {
            JSONObject secondData = JSONObject.fromObject(result);//解析第二层数据
            String barcode = secondData.optString("barcode");
            String name = secondData.optString("name");
            String ename = secondData.optString("ename");
            String unspsc = secondData.optString("unspsc");
            String brand = secondData.optString("brand");
            String type = secondData.optString("type");
            String width = secondData.optString("width");
            String height = secondData.optString("height");
            String depth = secondData.optString("depth");
            String origincountry = secondData.optString("origincountry");
            String originplace = secondData.optString("originplace");
            String assemblycountry = secondData.optString("assemblycountry");
            String barcodetype = secondData.optString("barcodetype");
            String catena = secondData.optString("catena");
            String isbasicunit = secondData.optString("isbasicunit");
            String packagetype = secondData.optString("packagetype");
            String grossweight = secondData.optString("grossweight");
            String netweight = secondData.optString("netweight");
            String description = secondData.optString("description");
            String keyword = secondData.optString("keyword");
            String pic = secondData.optString("pic");
            String price = secondData.optString("price");
            String licensenum = secondData.optString("licensenum");
            String healthpermitnum = secondData.optString("healthpermitnum");
            String netcontent = secondData.optString("netcontent");
            String company = secondData.optString("company");
            String expirationdate = secondData.optString("expirationdate");
            map.put("barcode", barcode);
            map.put("name", name);
            map.put("ename", ename);
            map.put("unspsc", unspsc);
            map.put("brand", brand);
            map.put("type", type);
            map.put("width", width);
            map.put("height", height);
            map.put("depth", depth);
            map.put("origincountry", origincountry);
            map.put("originplace", originplace);
            map.put("assemblycountry", assemblycountry);
            map.put("barcodetype", barcodetype);
            map.put("catena", catena);
            map.put("isbasicunit", isbasicunit);
            map.put("packagetype", packagetype);
            map.put("grossweight", grossweight);
            map.put("netweight", netweight);
            map.put("description", description);
            map.put("keyword", keyword);
            map.put("pic", pic);
            map.put("price", price);
            map.put("licensenum", licensenum);
            map.put("healthpermitnum", healthpermitnum);
            map.put("netcontent", netcontent);
            map.put("company", company);
            map.put("expirationdate", expirationdate);

            System.out.println("  barcode:" + barcode + "  name:" + name + "  ename:" + ename + "  unspsc:" + unspsc);
            return map;
        }else{
            return map;
        }
    }
}
