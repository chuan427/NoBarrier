package com.limitsale.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.limitsale.model.LimitSaleVO;

import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import com.order.model.OrderVO;



	@Entity // 要加上@Entity才能成為JPA的一個Entity類別
	@Table(name = "limitSale") // 代表這個class是對應到資料庫的實體table，目前對應的table是userInformation
	public class LimitSaleVO implements java.io.Serializable {
		private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "limNum")
		private Integer limNum;
		@Column(name = "limOrdernum")
		private Integer limOrdernum;
		@Column(name = "limSellerid")
		private Integer limSellerid;
		@Column(name = "limProdname")
		private String limProdname;
		@Column(name = "limQty")
		private Integer limQty;
		@Column(name = "limPrice")
		private Integer limPrice;
		@Column(name = "limImage")
		private byte[] limImage;
		@Column(name = "limDes")
		private String limDes;
		@Column(name = "limUnitname")
		private String limUnitname;
		
		@OneToOne
		@MapsId 
		@JoinColumn(name="limNum", referencedColumnName = "ordNum")
		private OrderVO orderVO;
		

		
//		public LimitSaleVO(Integer limNum, Integer limOrdernum, String limProdname, String limDes, Integer limQty,
//				String limUnitname, Integer limPrice, byte[] limImage, Integer limSellerid) {
//
//			this.limNum = limNum;
//			this.limOrdernum = limOrdernum;
//			this.limProdname = limProdname;
//			this.limDes = limDes;
//			this.limQty = limQty;
//			this.limUnitname = limUnitname;
//			this.limPrice = limPrice;
//			this.limImage = limImage;
//			this.limSellerid = limSellerid;
//		}
		public LimitSaleVO() {
			
		}
		
		
		public OrderVO getOrderVO() {
			return orderVO;
		}

		public void setOrderVO(OrderVO orderVO) {
			this.orderVO = orderVO;
		}
		
		
//		@Id
//		@GeneratedValue(strategy = GenerationType.IDENTITY)
		public Integer getLimNum() {
			return limNum;
		}
		public void setLimNum(Integer limNum) {
			this.limNum = limNum;
		}
		
		
		public Integer getLimOrdernum() {
			return limOrdernum;
		}
		public void setLimOrdernum(Integer limOrdernum) {
			this.limOrdernum = limOrdernum;
		}
		public String getLimProdname() {
			return limProdname;
		}
		public void setLimProdname(String limProdname) {
			this.limProdname = limProdname;
		}
		public String getLimDes() {
			return limDes;
		}
		public void setLimDes(String limDes) {
			this.limDes = limDes;
		}
		public Integer getLimQty() {
			return limQty;
		}
		public void setLimQty(Integer limQty) {
			this.limQty = limQty;
		}
		public String getLimUnitname() {
			return limUnitname;
		}
		public void setLimUnitname(String limUnitname) {
			this.limUnitname = limUnitname;
		}
		public Integer getLimPrice() {
			return limPrice;
		}
		public void setLimPrice(Integer limPrice) {
			this.limPrice = limPrice;
		}
		public byte[] getLimImage() {
			return limImage;
		}
		public void setLimImage(byte[] limImage) {
			this.limImage = limImage;
		}
		public Integer getLimSellerid() {
			return limSellerid;
		}
		public void setLimSellerid(Integer limSellerid) {
			this.limSellerid = limSellerid;
		}

		
		
	}
