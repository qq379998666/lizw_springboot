package lizw.springboot.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lizw.springboot.entity.OrdersEntity;
import lizw.springboot.mapper.OrdersMapper;

@Service
public class OrdersService {
	
	@Autowired
    private OrdersMapper ordersMapper;
	
	public List<Map<String,String>> ordersSelectService(String peopleNumber){
		List<Map<String,String>> resultMap = ordersMapper.ordersSelect(peopleNumber);
		return resultMap;
	}
	
	public int ordersInsertService(OrdersEntity ordersEntity){
		int count = ordersMapper.ordersInsert(ordersEntity);
		return count;
	}

}
