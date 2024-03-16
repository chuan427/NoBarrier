package com.administrator.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Entity // 要加上@Entity才能成為JPA的一個Entity類別
@Table(name = "administrator")
public class AdministratorVO implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer adminNum;
	private String adminName;
	private String adminAccount;
	private String adminPassword;
	private Integer adminAuth;
	private Integer adminIsValid;
	
	public AdministratorVO() {
		
	}

	@Id
	@Column(name = "adminNum")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getAdminNum() {
		return this.adminNum;
	}

	public void setAdminNum(Integer adminNum) {
		this.adminNum = adminNum;
	}

	@Column(name = "adminName")
	@NotEmpty(message="管理者名稱: 請勿空白")
	@Pattern(regexp = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$", message = "管理者名稱: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間")
	public String getAdminName() {
		return this.adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	@Column(name = "adminAccount")
	@NotEmpty(message="管理者帳號: 請勿空白")
	@Pattern(regexp = "^[(a-zA-Z0-9)]{2,10}$", message = "管理者帳號: 只能是英文字母和數字 , 且長度必需在2到10之間")
	public String getAdminAccount() {
		return this.adminAccount;
	}

	public void setAdminAccount(String adminAccount) {
		this.adminAccount = adminAccount;
	}

	@Column(name = "adminPassword")
	@NotEmpty(message="管理者密碼: 請勿空白")
	@Pattern(regexp = "^[(a-zA-Z0-9)]{2,10}$", message = "管理者密碼: 只能是英文字母和數字 , 且長度必需在2到10之間")
	public String getAdminPassword() {
		return this.adminPassword;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

	@Column(name = "adminAuth")
	public Integer getAdminAuth() {
		return this.adminAuth;
	}

	public void setAdminAuth(Integer adminAuth) {
		this.adminAuth = adminAuth;
	}

	@Column(name = "adminIsValid")
	public Integer getAdminIsValid() {
		return this.adminIsValid;
	}

	public void setAdminIsValid(Integer adminIsValid) {
		this.adminIsValid = adminIsValid;
	}
	
	

}
