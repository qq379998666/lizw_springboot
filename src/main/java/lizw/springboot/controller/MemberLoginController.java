package lizw.springboot.controller;

import java.math.BigInteger;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lizw.springboot.entity.MemberEntity;
import lizw.springboot.service.MemberService;

/**
 * @author lizhengwei
 * @date 创建时间：2019年1月10日 下午7:33:07
 * 简单的登录功能的实现：1-成功登录，2-登录失败
 */
@Controller
@RequestMapping(value="/MemberLoginController")
public class MemberLoginController { 
	
	@Autowired
	private MemberService memberService;
	
	
	@ResponseBody
	@RequestMapping(value="/MemberLogin",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	public String memberLogin(HttpServletRequest request){
		
		String phone = request.getParameter("phone");
		String password = request.getParameter("password");
		
		String result = null;
		if(!"".equals(phone) && phone != null) {
			if(phone.length() == 11) {
				MemberEntity memeberEntity = new MemberEntity();
				memeberEntity.setPhone(new BigInteger(phone));
				Map<String,String> phoneMap = memberService.memberService(memeberEntity);
				System.out.println();
				if(phoneMap.get("password").equals(password)) {
					result = "1";
				}else {
					result = "0";
				}
			
			}else {
				result = "0";
			}
		}else {
			result = "0";
		}
		return result;
		
	}

}
