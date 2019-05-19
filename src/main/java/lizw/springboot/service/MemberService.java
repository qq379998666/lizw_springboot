package lizw.springboot.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lizw.springboot.entity.MemberEntity;
import lizw.springboot.mapper.MemberMapper;

@Service
public class MemberService {
	
	@Autowired
	private MemberMapper memberMapper;
	
	public Map<String,String> memberService(MemberEntity member){
		Map<String,String> map = memberMapper.memberLogin(member);
		return map;
	}

}
