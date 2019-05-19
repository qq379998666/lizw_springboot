package lizw.springboot.entity;

public class TracerouteEntity {
	
	private Integer id;
	private Integer hop;
	private String A_ip;
	private String A_country;
	private String A_province;
	private String A_city;
	private String A_operator;
	private String B_ip;
	private String B_country;
	private String B_province;
	private String B_city;
	private String B_operator;
	private String hop_time;
	private Integer hop_count;
	private String F_ip;
	private String F_province;
	private String F_operator;
	private String L_ip;
	private String L_province;
	private String L_operator;
	private Integer count_hop;
	private String flag;
	
	public TracerouteEntity() {
		
	}
	
    public TracerouteEntity(Integer id,Integer hop,String A_ip,String A_country,String A_province,String A_city,String A_operator,String B_ip,String B_country,String B_province,String B_city,String B_operator,String hop_time,Integer hop_count,String F_ip,String F_province,String F_operator,String L_ip,String L_province,String L_operator,Integer count_hop,String flag){
		this.id = id;
		this.hop = hop;
		this.A_ip = A_ip;
		this.A_country = A_country;
		this.A_province = A_province;
		this.A_city = A_city;
		this.A_operator = A_operator;
		this.B_ip = B_ip;
		this.B_country = B_country;
		this.B_province = B_province;
		this.B_city = B_city;
		this.B_operator = B_operator;
		this.hop_time = hop_time;
		this.hop_count = hop_count;
		this.F_ip = F_ip;
		this.F_province = F_province;
		this.F_operator = F_operator;
		this.L_ip = L_ip;
		this.L_province = L_province;
		this.L_operator = L_operator;
		this.count_hop = count_hop;
		this.flag = flag;
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
	public String getHop_time() {
		return hop_time;
	}
	public void setHop_time(String hop_time) {
		this.hop_time = hop_time;
	}
	public Integer getHop_count() {
		return hop_count;
	}
	public void setHop_count(Integer hop_count) {
		this.hop_count = hop_count;
	}
	public String getF_ip() {
		return F_ip;
	}
	public void setF_ip(String f_ip) {
		F_ip = f_ip;
	}
	public String getF_province() {
		return F_province;
	}
	public void setF_province(String f_province) {
		F_province = f_province;
	}
	public String getF_operator() {
		return F_operator;
	}
	public void setF_operator(String f_operator) {
		F_operator = f_operator;
	}
	public String getL_ip() {
		return L_ip;
	}
	public void setL_ip(String l_ip) {
		L_ip = l_ip;
	}
	public String getL_province() {
		return L_province;
	}
	public void setL_province(String l_province) {
		L_province = l_province;
	}
	public String getL_operator() {
		return L_operator;
	}
	public void setL_operator(String l_operator) {
		L_operator = l_operator;
	}
	public Integer getCount_hop() {
		return count_hop;
	}

	public void setCount_hop(Integer count_hop) {
		this.count_hop = count_hop;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	

}
