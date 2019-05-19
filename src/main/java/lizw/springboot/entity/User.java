package lizw.springboot.entity;

import java.io.Serializable;
import java.math.BigInteger;

public class User implements Serializable {
	
    private	Integer id;
	private String username;
	private Integer sex;
	private BigInteger phone;
	private String infoA;
	private String infoB;
	private String infoC;

	public User() {
	}

	public User(Integer id, String username, Integer sex, BigInteger phone, String infoA, String infoB, String infoC) {
		this.id = id;
		this.username = username;
		this.sex = sex;
		this.phone = phone;
		this.infoA = infoA;
		this.infoB = infoB;
		this.infoC = infoC;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public BigInteger getPhone() {
		return phone;
	}
	public void setPhone(BigInteger phone) {
		this.phone = phone;
	}
	public String getInfoA() {
		return infoA;
	}
	public void setInfoA(String infoA) {
		this.infoA = infoA;
	}
	public String getInfoB() {
		return infoB;
	}
	public void setInfoB(String infoB) {
		this.infoB = infoB;
	}
	public String getInfoC() {
		return infoC;
	}
	public void setInfoC(String infoC) {
		this.infoC = infoC;
	}

}
