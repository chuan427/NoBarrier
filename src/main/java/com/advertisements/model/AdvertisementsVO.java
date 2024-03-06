package com.advertisements.model;


import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import com.ad.model.AdVO;

@Entity
@Table(name = "advertisements")
public class AdvertisementsVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private Integer adsWhen;
	private Date adsDays;
	private Integer adsIsValid=1;

	private AdVO adVO;

	public AdvertisementsVO() {
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ADSORDERNUM")
	public AdVO getAdVO() {
		return this.adVO;
	}

	public void setAdVO(AdVO adVO) {
		this.adVO = adVO;
	}

	@Id
	@Column(name = "ADSWHEN")
	@GeneratedValue(strategy = GenerationType.IDENTITY) // @GeneratedValue的generator屬性指定要用哪個generator
														// //【strategy的GenerationType, 有四種值: AUTO, IDENTITY, SEQUENCE,
														// TABLE】
	public Integer getAdsWhen() {
		return adsWhen;
	}

	public void setAdsWhen(Integer adsWhen) {
		this.adsWhen = adsWhen;
	}

	@Column(name = "adsDays", nullable = false)
	@NotNull(message = "雇用日期: 請勿空白")
//	@Future(message = "日期必須是在今日(不含)之後")
//	@Past(message="日期必須是在今日(含)之前")
//	@DateTimeFormat(pattern="yyyy-MM-dd") 
//	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public Date getAdsDays() {
		return adsDays;
	}

	public void setAdsDays(Date adsDays) {
		this.adsDays = adsDays;
	}

	@Column(name = "ADSISVALID")
	public Integer getAdsIsValid() {
		return adsIsValid;
	}

	public void setAdsIsValid(Integer adsIsValid) {
		this.adsIsValid = adsIsValid;

	}

}
