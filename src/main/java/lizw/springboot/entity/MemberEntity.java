package lizw.springboot.entity;

import org.springframework.beans.factory.annotation.Value;

import java.math.BigInteger;

public class MemberEntity {

	private Integer id;
	private BigInteger phone;
	private String name;
	private String password;
	
	public MemberEntity() {
		
	}
	
    public MemberEntity(Integer id,BigInteger phone,String name,String password){
		this.id = id;
		this.phone = phone;
		this.name = name;
		this.password = password;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public BigInteger getPhone() {
		return phone;
	}
	public void setPhone(BigInteger phone) {
		this.phone = phone;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
