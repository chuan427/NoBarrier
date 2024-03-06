package com.newsmodel;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;



/*
 * 註1: classpath必須有javax.persistence-api-x.x.jar 
 * 註2: Annotation可以添加在屬性上，也可以添加在getXxx()方法之上
 */


@Entity  //要加上@Entity才能成為JPA的一個Entity類別
@Table(name = "news") //代表這個class是對應到資料庫的實體table，目前對應的table是emp3
public class NewsVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer newsNum; // 消息編號
	private Date newsDate;  //發布日期
	private String newsTitle; // 消息標題
	private String newsContent; // 消息內容
	private Integer newsIsValid = 1;// 消息是否有效

	public NewsVO() { //必需有一個不傳參數建構子(JavaBean基本知識)
	}

	@Id //@Id代表這個屬性是這個Entity的唯一識別屬性，並且對映到Table的主鍵 
	@Column(name = "newsNum")  //@Column指這個屬性是對應到資料庫Table的哪一個欄位   //【非必要，但當欄位名稱與屬性名稱不同時則一定要用】
	@GeneratedValue(strategy = GenerationType.IDENTITY) //@GeneratedValue的generator屬性指定要用哪個generator //【strategy的GenerationType, 有四種值: AUTO, IDENTITY, SEQUENCE, TABLE】 
	public Integer getNewsNum() {
		return this.newsNum;
	}

	public void setNewsNum(Integer newsNum) {
		this.newsNum = newsNum;
	}

	@Column(name = "newsDate", columnDefinition = "DATE DEFAULT CURRENT_DATE")
//	@NotNull(message="消息日期: 請勿空白")	
//	@Future(message="日期必須是在今日(不含)之後")
//	@Past(message="日期必須是在今日(含)之前")
//	@DateTimeFormat(pattern="yyyy-MM-dd") 
//	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") 
	public Date getNewsDate() {
		return this.newsDate;
	}

	public void setNewsDate(Date newsDate) {
		this.newsDate = newsDate;
	}
	
	@Column(name = "newsTitle")
	@NotEmpty(message="消息標題: 請勿空白")
	public String getNewsTitle() {
		return this.newsTitle;
	}

	public void setNewsTitle(String newsTitle) {
		this.newsTitle = newsTitle;
	}

	@Column(name = "newsContent")
	@NotEmpty(message="消息內容: 請勿空白")
	public String getNewsContent() {
		return this.newsContent;
	}

	public void setNewsContent(String newsContent) {
		this.newsContent = newsContent;
	}

	@Column(name = "newsIsValid")
	@NotNull(message="消息狀態: 請勿空白,預設為1啟用中")
	@DecimalMin(value = "0", inclusive = true, message = "值必須等於0或1,0是已停用,1是啟用中")
    @DecimalMax(value = "1", inclusive = true, message = "值必須等於0或1,0是已停用,1是啟用中")
	public Integer getNewsIsValid() {
		return this.newsIsValid;
	}

	public void setNewsIsValid(Integer newsIsValid) {
		this.newsIsValid = newsIsValid;
	}
}
	

