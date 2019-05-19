package lizw.springboot.callableUtils;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lizw.springboot.mapper.TracerouteMapper;

@Component
public class SelectTwoIPFastUtil {
	
	@Autowired
	private TracerouteMapper tracerouteMapper;
	
	public static SelectTwoIPFastUtil selectTwoIPFastUntil;
	
	@PostConstruct
	public void init() {
		selectTwoIPFastUntil = this;
		selectTwoIPFastUntil.tracerouteMapper = this.tracerouteMapper;
	}
	
	//多线程查询数据
	public List<Map<String,String>> SelectTwoIPFastU(String date,String ip_A,String ip_B,int first,int last) {
		return selectTwoIPFastUntil.tracerouteMapper.selectTwoIPFast(date, ip_A, ip_B, first, last);
	}

}
