package lizw.springboot.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class BusinessesEntity implements Serializable {
	//序列化
	private static final long serialVersionUID = 1L;
	
	//私有化属性
	private Integer id ; 
	private String imgFileName; 
	private String title; 
	private String subtitle; 
	private BigDecimal price; 
	private Integer distance; 
	private Integer number; 
	private String desc; 
	private String city; 
	private String category; 
	private Integer starTotalNum; 
	private Integer commentTotalNum; 
	
	//无参的构造器
    public BusinessesEntity(){
    	
    }
    
  //有参的构造器
    public BusinessesEntity(Integer id,String imgFileName,String title,String subtitle,BigDecimal price,Integer distance,Integer number,String desc,String city,String category,Integer starTotalNum,Integer commentTotalNum){
    	this.id = id;
    	this.imgFileName = imgFileName;
    	this.title = title;
    	this.subtitle = subtitle;
    	this.price = price;
    	this.distance = distance;
    	this.number = number;
    	this.desc = desc;
    	this.city = city;
    	this.category = category;
    	this.starTotalNum = starTotalNum;
    	this.commentTotalNum = commentTotalNum;
    }
	
    //创建的setter和getter方法
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getImgFileName() {
		return imgFileName;
	}
	public void setImgFileName(String imgFileName) {
		this.imgFileName = imgFileName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubtitle() {
		return subtitle;
	}
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Integer getDistance() {
		return distance;
	}
	public void setDistance(Integer distance) {
		this.distance = distance;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Integer getStarTotalNum() {
		return starTotalNum;
	}
	public void setStarTotalNum(Integer starTotalNum) {
		this.starTotalNum = starTotalNum;
	}
	public Integer getCommentTotalNum() {
		return commentTotalNum;
	}
	public void setCommentTotalNum(Integer commentTotalNum) {
		this.commentTotalNum = commentTotalNum;
	}
	

}
