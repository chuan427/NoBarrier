package com.questionList.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
//import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.user.model.UserVO;

/*
 * 註1: classpath必須有javax.persistence-api-x.x.jar 
 * 註2: Annotation可以添加在屬性上，也可以添加在getXxx()方法之上
 */


@Entity  //要加上@Entity才能成為JPA的一個Entity類別
@Table(name = "questionList") //代表這個class是對應到資料庫的實體table，目前對應的table是emp3
public class QueListVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer queNum;
	private Timestamp queNotitime;
	private String queDes;
	private byte[] queImage;
	private UserVO userVO;
	private Integer queStat  = 1;
	private Integer queIsValid  = 1;
	

	public QueListVO() { //必需有一個不傳參數建構子(JavaBean基本知識)
	}

	@Id //@Id代表這個屬性是這個Entity的唯一識別屬性，並且對映到Table的主鍵 
	@Column(name = "queNum")  //@Column指這個屬性是對應到資料庫Table的哪一個欄位   //【非必要，但當欄位名稱與屬性名稱不同時則一定要用】
	@GeneratedValue(strategy = GenerationType.IDENTITY) //@GeneratedValue的generator屬性指定要用哪個generator //【strategy的GenerationType, 有四種值: AUTO, IDENTITY, SEQUENCE, TABLE】 
	public Integer getQueNum() {
		return this.queNum;
	}

	public void setQueNum(Integer queNum) {
		this.queNum = queNum;
	}
	
	@Column(name = "queNotitime", columnDefinition = "DATE DEFAULT CURRENT_DATE")
//	@NotNull(message="消息日期: 請勿空白")	
//	@Future(message="日期必須是在今日(不含)之後")
//	@Past(message="日期必須是在今日(含)之前")
//	@DateTimeFormat(pattern="yyyy-MM-dd") 
//	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") 
	public Timestamp getQueNotitime() {
		return this.queNotitime;
	}

	public void setQueNotitime(Timestamp queNotitime) {
		this.queNotitime = queNotitime;
	}
	
	@Column(name = "queDes")
	@NotEmpty(message="問題描述: 請勿空白")
	public String getQueDes() {
		return this.queDes;
	}

	public void setQueDes(String queDes) {
		this.queDes = queDes;
	}
	
	@Column(name = "queImage")
//	@NotEmpty(message="員工照片: 請上傳照片") --> 由EmpController.java 第60行處理錯誤信息
	public byte[] getQueImage() {
		return queImage;
	}
	public void setQueImage(byte[] queImage) {
		this.queImage = queImage;
	}
	

	// @ManyToOne  (雙向多對一/一對多) 的多對一
	//【此處預設為 @ManyToOne(fetch=FetchType.EAGER) --> 是指 lazy="false"之意】【注意: 此處的預設值與XML版 (p.127及p.132) 的預設值相反】
	//【如果修改為 @ManyToOne(fetch=FetchType.LAZY)  --> 則指 lazy="true" 之意】
	@ManyToOne
	@JoinColumn(name = "queUserid", referencedColumnName = "userId")
	public UserVO getUserVO() {
		return this.userVO;
	}

	public void setUserVO(UserVO userVO) {
		this.userVO = userVO;
	}

	@Column(name = "queStat")
	@NotNull(message="問題處理狀態: 請勿空白,預設為1未處理")
	@DecimalMin(value = "0", inclusive = true, message = "值必須等於0或1,0是已處理,1是未處理")
    @DecimalMax(value = "1", inclusive = true, message = "值必須等於0或1,0是已處理,1是未處理")
	public Integer getQueStat() {
		return this.queStat;
	}

	public void setQueStat(Integer queStat) {
		this.queStat = queStat;
	}
	
	@Column(name = "queIsValid")
	@NotNull(message="問題列表狀態: 請勿空白,預設為1啟用中")
	@DecimalMin(value = "0", inclusive = true, message = "值必須等於0或1,0是已停用,1是啟用中")
    @DecimalMax(value = "1", inclusive = true, message = "值必須等於0或1,0是已停用,1是啟用中")
	public Integer getQueIsValid() {
		return this.queIsValid;
	}

	public void setQueIsValid(Integer queIsValid) {
		this.queIsValid = queIsValid;
	}
	
	
}
