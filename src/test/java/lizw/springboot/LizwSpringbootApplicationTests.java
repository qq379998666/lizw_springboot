package lizw.springboot;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lizw.springboot.config.TestConfig;
import lizw.springboot.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lizw.springboot.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LizwSpringbootApplicationTests {

	private static final Logger LOGGER = LoggerFactory.getLogger(LizwSpringbootApplicationTests.class);

	@Autowired
	private RedisConnectionFactory factory;

	@Autowired
	private UserService userService;
	
	@Test
	public void contextLoads() {

		LOGGER.info("----------------------Start---------------------------");
		PageHelper.startPage(1,2);

		List<User> list = userService.findByPage();

		//得到分页的结果对象
		PageInfo<User> pageInfo = new PageInfo<>(list);

		List<User> page = pageInfo.getList();

		for (User user:page){
			System.out.println("user="+user);
		}
		LOGGER.info("----------------------Over---------------------------");
	}



	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Test
	public void stringRedisTest(){
		//字符串类型(string)：stringRedisTemplate.opsForValue()
		stringRedisTemplate.opsForValue().set("test","test");

	}

	@Autowired
	private RedisTemplate<Object,Object> redisTemplate;

	@Test
	public void redisTest(){
		//散列（hashes)：
		Map<String,String> map = new HashMap<String,String>();
		map.put("li","li");
		map.put("zheng","zheng");
		map.put("wei","wei");
		redisTemplate.opsForHash().put("lizhengwei","hashkey",map);

	}

	@Test
	public void importTest(){
		ApplicationContext ctx = new AnnotationConfigApplicationContext(TestConfig.class);
		String[] beanDefinitionNames = ctx.getBeanDefinitionNames();
		for (String name : beanDefinitionNames) {
			System.out.println(name);
		}
	}

}
