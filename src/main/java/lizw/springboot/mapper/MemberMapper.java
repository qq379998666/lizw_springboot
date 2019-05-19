package lizw.springboot.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import lizw.springboot.entity.MemberEntity;

@Mapper
public interface MemberMapper {
	
	public Map<String,String> memberLogin(MemberEntity member);

}
