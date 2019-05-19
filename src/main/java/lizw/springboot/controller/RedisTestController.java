package lizw.springboot.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.sun.media.jfxmedia.logging.Logger;
import lizw.springboot.entity.User;
import lizw.springboot.service.RedisService;
import lizw.springboot.service.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lizhengwei
 * @Description
 * @Date 20:17 2019/3/31
 **/
@RestController
@RequestMapping(value = "/redis")
public class RedisTestController{

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Autowired
	private RedisTemplate redisTemplate;

	/*
		redisTemplate.opsForValue();//操作字符串
		redisTemplate.opsForHash();//操作hash
		redisTemplate.opsForList();//操作list
		redisTemplate.opsForSet();//操作set
		redisTemplate.opsForZSet();//操作有序set
	 */

	//String
    @RequestMapping(value = "/opsforvalue")
    public Map<String, Object> redisTest(HttpServletRequest request){

		String redisString = request.getParameter("redisString");

		redisTemplate.opsForValue().set("redisString",redisString);
		redisTemplate.opsForValue().get("redisString");

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("redisString",redisString);
		return map;
	}

	//Set
	@RequestMapping(value = "/opsforset")
	public Map<String, Object> redisLizw(HttpServletRequest request){
		Set<String> set = new HashSet<String>();
		set.add("opsforset1");
		set.add("opsforset2");
		set.add("opsforset3");
		redisTemplate.opsForSet().add("set",set);
		Set<String> resultSet = redisTemplate.opsForSet().members(set);
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("resultSet",resultSet);
		return resultMap;
	}

	//ZSet
	@RequestMapping(value = "/opsforzset")
	public Map<String, Object> redisZSet(HttpServletRequest request){

    	String opsforset1 = request.getParameter("opsforset1");
		String opsforset2 = request.getParameter("opsforset2");
		String opsforset3 = request.getParameter("opsforset3");

		Set<String> set = new HashSet<String>();
		set.add(opsforset1);
		set.add(opsforset2);
		set.add(opsforset3);
		redisTemplate.opsForSet().add("zset",set);

		Set<String> resultSet = redisTemplate.opsForSet().members("zset");
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("resultSet",resultSet);
		return resultMap;

	}

	//
	@RequestMapping(value = "/opsforzlist")
	public Map<String, Object> redisList(HttpServletRequest request){

		List<String> list1=new ArrayList<String>();
		list1.add("a1");
		list1.add("a2");
		list1.add("a3");

		List<String> list2=new ArrayList<String>();
		list2.add("b1");
		list2.add("b2");
		list2.add("b3");

		redisTemplate.boundListOps("namelist1").leftPush(list1);
		redisTemplate.boundListOps("namelist2").rightPush(list2);

		List<String> resultLista=(List<String>)redisTemplate.boundListOps(list1);
		List<String> resultListb=(List<String>)redisTemplate.boundListOps(list2);

		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("resultList",resultLista);
		return resultMap;

	}

}
