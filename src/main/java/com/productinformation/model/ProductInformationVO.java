package com.productinformation.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.user.model.UserVO;

@Entity // 要加上@Entity才能成為JPA的一個Entity類別
@Table(name = "productInformation")
public class ProductInformationVO implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer pinfoNum;
	private UserVO userVO;
//	private Integer pinfoUserid;
	private byte[] pinfoImage;
	private String pinfoDes;
	private Integer pinfoIsValid =1;
	

	public ProductInformationVO() {
		
	}


	@Id
	@Column(name = "pinfoNum")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getPinfoNum() {
		return this.pinfoNum;
	}


	public void setPinfoNum(Integer pinfoNum) {
		this.pinfoNum = pinfoNum;
	}
	
	@ManyToOne
	@JoinColumn(name = "pinfoUserid", referencedColumnName = "userid")   // 指定用來join table的column
	public UserVO getUserVO() {
		return this.userVO;
	}

	public void setUserVO(UserVO userVO) {
		this.userVO = userVO;
	}

//	@Column(name = "pinfoUserid", insertable=false, updatable=false)
//	public Integer getPinfoUserid() {
//		return this.pinfoUserid;
//	}
//
//
//	public void setPinfoUserid(Integer pinfoUserid) {
//		this.pinfoUserid = pinfoUserid;
//	}

	
	@Column(name = "pinfoImage")
	public byte[] getPinfoImage() {
		return this.pinfoImage;
	}


	public void setPinfoImage(byte[] pinfoImage) {
		this.pinfoImage = pinfoImage;
	}

	@NotEmpty(message="產品資訊請勿空白")
	@Column(name = "pinfoDes")
	public String getPinfoDes() {
		return this.pinfoDes;
	}


	public void setPinfoDes(String pinfoDes) {
		this.pinfoDes = pinfoDes;
	}

	@Column(name = "pinfoIsValid")
	public Integer getPinfoIsValid() {
		return pinfoIsValid;
	}


	public void setPinfoIsValid(Integer pinfoIsValid) {
		this.pinfoIsValid = pinfoIsValid;
	}
	
	
}
