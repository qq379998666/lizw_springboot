package lizw.springboot.entity;

public class TraceAnalysisEntity {
	
	private Integer id;
	private Integer hop;
	private String A_ip;
	private String A_country;
	private String A_province;
	private String A_city;
	private String A_operator;
	private String A_p;
	private String B_ip;
	private String B_country;
	private String B_province;
	private String B_city;
	private String B_operator;
	private String B_p;
	private Integer hop_sign;
	
    

	public TraceAnalysisEntity() {
		
	}
	
    public TraceAnalysisEntity(Integer id,Integer hop,String A_ip,String A_country,String A_province,String A_city,String A_operator,String A_p,String B_ip,String B_country,String B_province,String B_city,String B_operator,String B_p,Integer hop_sign){
		this.id = id;
		this.hop = hop;
		this.A_ip = A_ip;
		this.A_country = A_country;
		this.A_province = A_province;
		this.A_city = A_city;
		this.A_operator = A_operator;
		this.A_p = A_p;
		this.B_ip = B_ip;
		this.B_country = B_country;
		this.B_province = B_province;
		this.B_city = B_city;
		this.B_operator = B_operator;
		this.B_p = B_p;
		this.hop_sign = hop_sign;
	}
	
    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getHop() {
		return hop;
	}

	public void setHop(Integer hop) {
		this.hop = hop;
	}

	public String getA_ip() {
		return A_ip;
	}

	public void setA_ip(String a_ip) {
		A_ip = a_ip;
	}

	public String getA_country() {
		return A_country;
	}

	public void setA_country(String a_country) {
		A_country = a_country;
	}

	public String getA_province() {
		return A_province;
	}

	public void setA_province(String a_province) {
		A_province = a_province;
	}

	public String getA_city() {
		return A_city;
	}

	public void setA_city(String a_city) {
		A_city = a_city;
	}

	public String getA_operator() {
		return A_operator;
	}

	public void setA_operator(String a_operator) {
		A_operator = a_operator;
	}

	public String getA_p() {
		return A_p;
	}

	public void setA_p(String a_p) {
		A_p = a_p;
	}

	public String getB_ip() {
		return B_ip;
	}

	public void setB_ip(String b_ip) {
		B_ip = b_ip;
	}

	public String getB_country() {
		return B_country;
	}

	public void setB_country(String b_country) {
		B_country = b_country;
	}

	public String getB_province() {
		return B_province;
	}

	public void setB_province(String b_province) {
		B_province = b_province;
	}

	public String getB_city() {
		return B_city;
	}

	public void setB_city(String b_city) {
		B_city = b_city;
	}

	public String getB_operator() {
		return B_operator;
	}

	public void setB_operator(String b_operator) {
		B_operator = b_operator;
	}

	public String getB_p() {
		return B_p;
	}

	public void setB_p(String b_p) {
		B_p = b_p;
	}
	
	public Integer getHop_sign() {
		return hop_sign;
	}

	public void setHop_sign(Integer hop_sign) {
		this.hop_sign = hop_sign;
	}

}
