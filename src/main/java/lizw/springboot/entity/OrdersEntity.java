package lizw.springboot.entity;

import java.math.BigInteger;

import net.sf.jsqlparser.expression.DateTimeLiteralExpression.DateTime;

public class OrdersEntity {
	
	private Integer id;
	private BusinessesEntity businessesEntity;
	private MemberEntity memberEntity;
	private Integer peopleNumber;
	private Integer commentState;
	private BigInteger price;
	private DateTime createTime;
	
	public OrdersEntity() {
		
	}
	
    public OrdersEntity(Integer id,BusinessesEntity businessesEntity,MemberEntity memberEntity,Integer peopleNumber,Integer commentState,BigInteger price,DateTime createTime){
		this.id = id;
		this.businessesEntity = businessesEntity;
		this.memberEntity = memberEntity;
		this.peopleNumber = peopleNumber;
		this.commentState = commentState;
		this.price = price;
		this.createTime = createTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BusinessesEntity getBusinessesEntity() {
		return businessesEntity;
	}

	public void setBusinessesEntity(BusinessesEntity businessesEntity) {
		this.businessesEntity = businessesEntity;
	}

	public MemberEntity getMemberEntity() {
		return memberEntity;
	}

	public void setMemberEntity(MemberEntity memberEntity) {
		this.memberEntity = memberEntity;
	}

	public Integer getPeopleNumber() {
		return peopleNumber;
	}

	public void setPeopleNumber(Integer peopleNumber) {
		this.peopleNumber = peopleNumber;
	}

	public Integer getCommentState() {
		return commentState;
	}

	public void setCommentState(Integer commentState) {
		this.commentState = commentState;
	}

	public BigInteger getPrice() {
		return price;
	}

	public void setPrice(BigInteger price) {
		this.price = price;
	}

	public DateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(DateTime createTime) {
		this.createTime = createTime;
	}

	

}
