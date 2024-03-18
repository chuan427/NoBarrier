package com.quo.model;

import java.sql.Date;
import java.time.LocalDate;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.order.model.OrderVO;
import com.reqorder.model.ReqOrderVO;
import com.user.model.UserVO;

@Entity
@Table(name = "quotation")
public class QuoVO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	
	private Integer quoNum;//PK 報價單Id
	private Date quoDate = Date.valueOf(LocalDate.now());//報價單建立時間
	private String quoProdname;//產品名稱
	private String quoUnitname;//單位名稱
	private Integer quoProdqty;//產品數量
	private Integer quoUnitprice;//產品單價
	private Integer quoTotalprice;//產品總價
	private String quoInfo;//文字敘述
	private ReqOrderVO reqOrderVO;//需求單的VO
	private UserVO userVO;//賣家VO
	private Integer quoIsValid;//啟用狀態
	private List<OrderVO> orders;

	
	public QuoVO() {
	}

	@Id
	@Column(name = "quoNum")
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment的@
	public Integer getQuoNum() {
		return quoNum;
	}

	public void setQuoNum(Integer quoNum) {
		this.quoNum = quoNum;
	}

	@Column(name = "quoDate",columnDefinition = "DATE DEFAULT CURRENT_DATE" )
	public Date getQuoDate() {
		return quoDate;
	}

	public void setQuoDate(Date quoDate) {
		this.quoDate = quoDate;
	}

	@Column(name = "quoProdname")
	public String getQuoProdname() {
		return quoProdname;
	}

	public void setQuoProdname(String quoProdname) {
		this.quoProdname = quoProdname;
	}

	@Column(name = "quoUnitname")
	public String getQuoUnitname() {
		return quoUnitname;
	}

	public void setQuoUnitname(String quoUnitname) {
		this.quoUnitname = quoUnitname;
	}

	@Column(name = "quoProdqty")
	public Integer getQuoProdqty() {
		return quoProdqty;
	}

	public void setQuoProdqty(Integer quoProdqty) {
		this.quoProdqty = quoProdqty;
	}

	@Column(name = "quoUnitprice")
	public Integer getQuoUnitprice() {
		return quoUnitprice;
	}

	public void setQuoUnitprice(Integer quoUnitprice) {
		this.quoUnitprice = quoUnitprice;
	}

	@Column(name = "quoTotalprice")
	public Integer getQuoTotalprice() {
		return quoTotalprice;
	}

	public void setQuoTotalprice(Integer quoTotalprice) {
		this.quoTotalprice = quoTotalprice;
	}

	@Column(name = "quoInfo", columnDefinition = "text")
	public String getQuoInfo() {
		return quoInfo;
	}

	public void setQuoInfo(String quoInfo) {
		this.quoInfo = quoInfo;
	}

	@ManyToOne
	@JoinColumn(name = "quoReqnum", referencedColumnName = "reqNum")
	public ReqOrderVO getReqOrderVO() {
		return reqOrderVO;
	}

	public void setReqOrderVO(ReqOrderVO reqOrderVO) {
		this.reqOrderVO = reqOrderVO;
	}

	@ManyToOne
	@JoinColumn(name = "quoUserid", referencedColumnName = "userid")
	public UserVO getUserVO() {
		return userVO;
	}

	public void setUserVO(UserVO userVO) {
		this.userVO = userVO;
	}

	@Column(name = "quoIsValid")
	public Integer getQuoIsValid() {
		return quoIsValid;
	}

	public void setQuoIsValid(Integer quoIsValid) {
		this.quoIsValid = quoIsValid;
	}

	@OneToMany(mappedBy = "quoVO", cascade = CascadeType.ALL)
	public List<OrderVO> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderVO> orders) {
        this.orders = orders;
    }
	
    

	@Override
	public String toString() {
		return "QuoVO [quoNum=" + quoNum + ", quoDate=" + quoDate + ", quoProdname=" + quoProdname + ", quoUnitname="
				+ quoUnitname + ", quoProdqty=" + quoProdqty + ", quoUnitprice=" + quoUnitprice + ", quoTotalprice="
				+ quoTotalprice + ", quoInfo=" + quoInfo + ", reqOrderVO=" + reqOrderVO + ", userVO=" + userVO
				+ ", quoIsValid=" + quoIsValid + "]";
	}

	

	
	
	
}
