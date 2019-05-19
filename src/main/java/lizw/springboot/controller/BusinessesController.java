package lizw.springboot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lizw.springboot.entity.BusinessesEntity;
import lizw.springboot.service.BusinessesService;

/**
 * @author lizhengwei
 * @date 2019年1月14日 下午5:02:41
 * 基于Springboot项目的 增删改查
 */
@Controller
@RequestMapping(value="/BusinessesController")
public class BusinessesController {
	
	@Autowired
	private BusinessesService businessesService;
	
	@ResponseBody
	@RequestMapping(value="/BusinessesDelete",method= RequestMethod.DELETE)//DELETE是用来删除某一个资源的，该请求就像数据库的delete操作。
	public Map<String,Integer> businessesDelete(HttpServletRequest request) {
		//请求路径：http://localhost:8080/BusinessesController/BusinessesDelete?id=3
		String id = request.getParameter("id");
		int result = businessesService.businessesDeleteService(id);

		Map<String,Integer> resultMap = new HashMap<String,Integer>();
		resultMap.put("resultMap", result);
		return resultMap;
	}
	
	@ResponseBody
	@RequestMapping(value="/BusinessesUpdate",method= RequestMethod.POST,produces="application/json;charset=UTF-8")//DELETE是用来删除某一个资源的，该请求就像数据库的delete操作。
	public Map<String,Integer> businessesUpdate(@RequestBody BusinessesEntity businessesEntity) {
		//请求路径：http://localhost:8080/BusinessesController/BusinessesUpdate
		//请求参数格式：{"id":1,"price":1,"distance":1,"number":1,"desc":1,"city":1,"category":1,"starTotalNum":1,"commentTotalNum":1 }
		int result = businessesService.businessesUpdateService(businessesEntity);

		Map<String,Integer> resultMap = new HashMap<String,Integer>();
		resultMap.put("resultMap",result);
		return resultMap;
	}
	
	@ResponseBody
	@RequestMapping(value="/BusinessesInsert",method= RequestMethod.POST,produces="application/json;charset=UTF-8")//DELETE是用来删除某一个资源的，该请求就像数据库的delete操作。
	public Map<String,Integer> businessesInsert(@RequestBody BusinessesEntity businessesEntity) {
		//请求路径：http://localhost:8080/BusinessesController/BusinessesUpdate
		//请求参数格式：{"id":1,"price":1,"distance":1,"number":1,"desc":1,"city":1,"category":1,"starTotalNum":1,"commentTotalNum":1 }
		int result = businessesService.businesseInsertService(businessesEntity);

		Map<String,Integer> resultMap = new HashMap<String,Integer>();
		resultMap.put("resultMap",result);
		return resultMap;
	}
	
	@ResponseBody
	@RequestMapping(value="/BusinessesSelect",method= RequestMethod.POST)
	public Map<String,Object> businessesSelect(String id) {
		//请求路径：http://localhost:8080/BusinessesController/BusinessesSelect
		//Integer integer = Integer.parseInt(id);
		List<Map<String,String>> list = businessesService.businesseSelectService(id);
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("resultMap",list);
		return resultMap;
	}

}
