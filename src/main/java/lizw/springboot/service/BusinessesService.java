package lizw.springboot.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lizw.springboot.entity.BusinessesEntity;
import lizw.springboot.mapper.BusinessesMapper;

@Service
public class BusinessesService {
	
	@Autowired
	private BusinessesMapper businessesMapper;
	
	public int businessesDeleteService(String id){
		System.out.println("id值："+id);
		int result = businessesMapper.businessesDeleteMapper(id);
		return result;
	}
	
	public int businessesUpdateService(BusinessesEntity businessesEntity){
		int result = businessesMapper.businessesUpdateMapper(businessesEntity);
		return result;
	}
	
	public int businesseInsertService(BusinessesEntity businessesEntity){
		int result = businessesMapper.businessesInsertMapper(businessesEntity);
		return result;
	}
	
	public List<Map<String,String>> businesseSelectService(String id){
		List<Map<String,String>> resultMap = businessesMapper.businessesSelectMapper(id);
		return resultMap;
	}

}
