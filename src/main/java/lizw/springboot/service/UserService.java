package lizw.springboot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import lizw.springboot.entity.User;
import lizw.springboot.mapper.UserMapper;

import java.util.List;

@Service
@CacheConfig(cacheNames="userService")
public class UserService{

	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserMapper userMapper;

	@Autowired
	private RedisTemplate redisTemplate;

	@Cacheable //开启缓存
	public User findById(String id) {
		// 从缓存中获取信息
		String key = "user-" + id;
		ValueOperations<String, User> operations = redisTemplate.opsForValue();

		// 缓存存在
		boolean hasKey = redisTemplate.hasKey(key);
		if (hasKey) {
			User user = operations.get(key);
			LOGGER.info("从缓存中获取了用户 >> " + "id: " + user.getId() + ", username: " + user.getUsername() + ",sex: " + user.getSex());
			return user;
		}
		return userMapper.findById(id);
	}

	/**
	 * @Cacheable: condition属性：这个参数将指明方法的返回结果是否被缓。eg:condition = "#id < 3"
	 *				key属性：当然你可以重写key，自定义key可以使用SpEL表达式。eg:key = "#id"
	 *				value属性：value指明了缓存将被存到什么地方。eg:value="user"
	 */
	@Cacheable(value="user",key = "#id",condition = "#id < 5")
	public User find(Integer id){
		return userMapper.findByIdLizw(id.toString());
	}

	public List<User> findByPage(){
		return userMapper.findAll();
	}

}
