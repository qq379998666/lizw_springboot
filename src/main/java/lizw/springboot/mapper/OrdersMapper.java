package lizw.springboot.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import lizw.springboot.entity.OrdersEntity;

@Mapper
public interface OrdersMapper {
	
	public int ordersInsert(OrdersEntity ordersEntity);
	
	public List<Map<String,String>> ordersSelect(String peopleNumber);

}
