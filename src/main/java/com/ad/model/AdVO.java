package com.ad.model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.advertisements.model.AdvertisementsVO;

/*
 * 註1: classpath必須有javax.persistence-api-x.x.jar 
 * 註2: Annotation可以添加在屬性上，也可以添加在getXxx()方法之上
 */

@Entity // 要加上@Entity才能成為JPA的一個Entity類別
@Table(name = "ad") // 代表這個class是對應到資料庫的實體table，目前對應的table是EMP2
public class AdVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private Integer adOrdernum;
	private byte[] adImage;
	private Integer adPrice;
	private Integer adStat = 1;
	private Date adDate = Date.valueOf(LocalDate.now());
	private Integer adUserid=1;
	private Integer adDuration;
	private Integer adIsValid = 1;

	private List<AdvertisementsVO> advertisements = new ArrayList<AdvertisementsVO>();

	public AdVO() { // 必需有一個不傳參數建構子(JavaBean基本知識)
	}

	@Id // @Id代表這個屬性是這個Entity的唯一識別屬性，並且對映到Table的主鍵
	@Column(name = "ADORDERNUM") // @Column指這個屬性是對應到資料庫Table的哪一個欄位 //【非必要，但當欄位名稱與屬性名稱不同時則一定要用】
	@GeneratedValue(strategy = GenerationType.IDENTITY) // @GeneratedValue的generator屬性指定要用哪個generator
														// //【strategy的GenerationType, 有四種值: AUTO, IDENTITY, SEQUENCE,
														// TABLE】

	public Integer getAdOrdernum() {
		return adOrdernum;
	}

	public void setAdOrdernum(Integer adOrdernum) {
		this.adOrdernum = adOrdernum;
	}

	@Column(name = "ADIMAGE")
//	@NotEmpty(message="員工照片: 請上傳照片") --> 由EmpController.java 第60行處理錯誤信息
	public byte[] getAdImage() {
		return adImage;
	}

	public void setAdImage(byte[] adImage) {
		this.adImage = adImage;
	}

	@Column(name = "ADPRICE")
	@NotNull(message = "廣告金額: 請勿空白")
	public Integer getAdPrice() {
		return adPrice;
	}

	public void setAdPrice(Integer adPrice) {
		this.adPrice = adPrice;
	}

	@Column(name = "ADSTAT")
	public Integer getAdStat() {
		return adStat;
	}

	public void setAdStat(Integer adStat) {
		this.adStat = adStat;
	}

	@Column(name = "ADDATE", columnDefinition = "DATE DEFAULT CURRENT_DATE")
//	@NotNull(message="雇用日期: 請勿空白")	
//	@Future(message="日期必須是在今日(不含)之後")
//	@Past(message="日期必須是在今日(含)之前")
//	@DateTimeFormat(pattern="yyyy-MM-dd") 
//	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public Date getAdDate() {
		return adDate;
	}

	public void setAdDate(Date adDate) {

		this.adDate = adDate;
	}

	@Column(name = "ADUSERID")
	@NotNull(message = "廣告使用者: 請勿空白")
	public Integer getAdUserid() {
		return adUserid;
	}

	public void setAdUserid(Integer adUserid) {
		this.adUserid = adUserid;
	}

	@Column(name = "ADDURATION")
//	@NotNull(message = "廣告使用者: 請勿空白")
	public Integer getAdDuration() {
		return adDuration;
	}

	public void setAdDuration(Integer adDuration) {
		this.adDuration = adDuration;
	}

	@Column(name = "ADISVALID")
	public Integer getAdIsValid() {
		return adIsValid;
	}

	public void setAdIsValid(Integer adIsValid) {
		this.adIsValid = adIsValid;
	}

	@OneToMany(mappedBy = "adVO", cascade = CascadeType.ALL)
	@OrderBy("ADSWHEN asc")
	public List<AdvertisementsVO> getAdvertisements() {
		return this.advertisements;
	}

	public void setAdvertisements(List<AdvertisementsVO> advertisements) {
		this.advertisements = advertisements;
	}

}
