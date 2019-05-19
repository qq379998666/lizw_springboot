package lizw.springboot.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import lizw.springboot.entity.BusinessesEntity;

@Mapper
public interface BusinessesMapper {
	
	public int businessesDeleteMapper(String id);//删除成功返回1，删除失败返回0
	
	public int businessesUpdateMapper(BusinessesEntity businessesEntity);//删除成功返回1，删除失败返回0
	
	public int businessesInsertMapper(BusinessesEntity businessesEntity);//删除成功返回1，删除失败返回0
	
	public List<Map<String,String>> businessesSelectMapper(String id);

}
