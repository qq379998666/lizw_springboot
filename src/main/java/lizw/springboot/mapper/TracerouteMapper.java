package lizw.springboot.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TracerouteMapper {
	
	//查询一个IP
	public List<Map<String,String>> selectOneIP(String date,String ip);
	
	//查询两个IP
	public List<Map<String,String>> selectTwoIP(String date,String ip_A,String ip_B);
	
	//全部
	public List<Map<String,String>> selectCity(String date,String operator);
	
	//省间
	public List<Map<String,String>> selectProvince(String date,String operator);
    
	//多线程查询
	public List<Map<String, String>> selectTwoIPFast(String date, String ip_A, String ip_B, int first, int last);

}
