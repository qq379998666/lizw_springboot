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

import lizw.springboot.entity.OrdersEntity;
import lizw.springboot.service.OrdersService;


/**
 * @author lizhengwei
 * @date 2019年1月14日 下午5:14:27
 * 订单的查询接口
 */
@Controller
@RequestMapping(value="/OrdersController")
public class OrdersController {
	
	@Autowired
	private OrdersService ordersService;
	
	@ResponseBody
	@RequestMapping(value="/OrdersSelect" ,method= RequestMethod.POST)
	public Map<String,Object> OrdersSelect(HttpServletRequest request){
		
		String peopleNumber = request.getParameter("peopleNumber");
		
		List<Map<String,String>> list = ordersService.ordersSelectService(peopleNumber);
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("list", list);
		
		return resultMap;
		
	}
	
	@ResponseBody
	@RequestMapping(value="/OrdersInsert" ,method= RequestMethod.POST)
	public Map<String,Object> OrdersInsert(@RequestBody OrdersEntity ordersEntity){
		
		/**
		 * @RequestBody:用来处理content-type不是默认的application/x-www-form-urlcoded编码的内容，application/json或者是application/xml等。
		 *    			一般情况下来说常用其来处理application/json类型。
		 *    			@RequestBody OrdersEntity ordersEntity，这种形式会将JSON字符串中的值赋予OrdersEntity中对应的属性上需要注意的是JSON字符串中的key必须对应user中的属性名，否则是请求不过去的。
		 *    			
		 */
		
		int count = ordersService.ordersInsertService(ordersEntity);
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("count", count);
		
		return resultMap;
		
	}

}
