package com.gdpost.web.entity.member;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.ColumnTransformer;

import com.gdpost.web.entity.Idable;

/**
 * TblMemberData entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tbl_member_data")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.gdpost.web.entity.member.TblMemberData")
public class TblMemberData implements Idable<Long> {

	// Fields

	private Long id;
	private TblMember tblMember;
	private Integer ny;
	private String dm;
	private String pl;
	private String pm;
	private String cj;
	private String gg;
	private Double dj;
	private Double cbj;
	private Double mll;
	private Double xsl;
	private Double xse;
	private Double yckc;
	private Double ymkc;
	private Double jhl;
	private Timestamp createDate;
	private String ls;
	private String cs;
	private String tm;
	
	private String pldl;
	private String plzl;
	private String yb;
	private String otc;
	private String ydbm;
	private String ypbm;
	private String dw;

	// Constructors

	/** default constructor */
	public TblMemberData() {
	}

	/** full constructor */
	public TblMemberData(TblMember tblMember, Integer ny, String dm, String pl, String pm,
			String cj, String gg, Double dj, Double cbj, Double mll, Double xsl, Double xse, Double yckc, Double ymkc, Double jhl, Timestamp createDate) {
		this.tblMember = tblMember;
		this.ny = ny;
		this.dm = dm;
		this.pl = pl;
		this.pm = pm;
		this.cj = cj;
		this.gg = gg;
		this.dj = dj;
		this.cbj = cbj;
		this.mll = mll;
		this.xsl = xsl;
		this.xse = xse;
		this.setYckc(yckc);
		this.setYmkc(ymkc);
		this.setJhl(jhl);
		this.createDate = createDate;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	public TblMember getTblMember() {
		return this.tblMember;
	}

	public void setTblMember(TblMember tblMember) {
		this.tblMember = tblMember;
	}

	@Column(name = "ny")
	public Integer getNy() {
		return this.ny;
	}

	public void setNy(Integer ny) {
		this.ny = ny;
	}

	@Column(name = "dm", length = 300)
	@ColumnTransformer(
			forColumn="dm",
			read="cast(aes_decrypt(unhex(dm), '" + com.gdpost.web.MySQLAESKey.AESKey + "') as char(100))", 
			write="aes_encrypt(?,'" + com.gdpost.web.MySQLAESKey.AESKey + "')")
	public String getDm() {
		return this.dm;
	}

	public void setDm(String dm) {
		this.dm = dm;
	}

	@Column(name = "pl", length = 300)
	@ColumnTransformer(
			forColumn="pl",
			read="cast(aes_decrypt(unhex(pl), '" + com.gdpost.web.MySQLAESKey.AESKey + "') as char(100))", 
			write="aes_encrypt(?,'" + com.gdpost.web.MySQLAESKey.AESKey + "')")
	public String getPl() {
		return this.pl;
	}

	public void setPl(String pl) {
		this.pl = pl;
	}

	@Column(name = "pm", length = 300)
	@ColumnTransformer(
			forColumn="pm",
			read="cast(aes_decrypt(unhex(pm), '" + com.gdpost.web.MySQLAESKey.AESKey + "') as char(100))", 
			write="aes_encrypt(?,'" + com.gdpost.web.MySQLAESKey.AESKey + "')")
	public String getPm() {
		return this.pm;
	}

	public void setPm(String pm) {
		this.pm = pm;
	}

	@Column(name = "cj", length = 300)
	@ColumnTransformer(
			forColumn="cj",
			read="cast(aes_decrypt(unhex(cj), '" + com.gdpost.web.MySQLAESKey.AESKey + "') as char(100))", 
			write="aes_encrypt(?,'" + com.gdpost.web.MySQLAESKey.AESKey + "')")
	public String getCj() {
		return this.cj;
	}

	public void setCj(String cj) {
		this.cj = cj;
	}

	@Column(name = "gg", length = 300)
	@ColumnTransformer(
			forColumn="gg",
			read="cast(aes_decrypt(unhex(gg), '" + com.gdpost.web.MySQLAESKey.AESKey + "') as char(100))", 
			write="aes_encrypt(?,'" + com.gdpost.web.MySQLAESKey.AESKey + "')")
	public String getGg() {
		return this.gg;
	}

	public void setGg(String gg) {
		this.gg = gg;
	}

	@Column(name = "dj", precision = 12, scale = 4)
	public Double getDj() {
		return this.dj;
	}

	public void setDj(Double dj) {
		this.dj = dj;
	}

	@Column(name = "cbj", precision = 12, scale = 4)
	public Double getCbj() {
		return this.cbj;
	}

	public void setCbj(Double cbj) {
		this.cbj = cbj;
	}

	@Column(name = "mll", precision = 12, scale = 4)
	public Double getMll() {
		return this.mll;
	}

	public void setMll(Double mll) {
		this.mll = mll;
	}

	@Column(name = "xsl", precision = 12, scale = 4)
	public Double getXsl() {
		return this.xsl;
	}

	public void setXsl(Double xsl) {
		this.xsl = xsl;
	}

	@Column(name = "xse", precision = 12, scale = 4)
	public Double getXse() {
		return this.xse;
	}

	public void setXse(Double xse) {
		this.xse = xse;
	}

	@Column(name = "yckc", precision = 12, scale = 4)
	public Double getYckc() {
		return yckc;
	}

	public void setYckc(Double yckc) {
		this.yckc = yckc;
	}

	@Column(name = "ymkc", precision = 12, scale = 4)
	public Double getYmkc() {
		return ymkc;
	}

	public void setYmkc(Double ymkc) {
		this.ymkc = ymkc;
	}

	@Column(name = "jhl", precision = 12, scale = 4)
	public Double getJhl() {
		return jhl;
	}

	public void setJhl(Double jhl) {
		this.jhl = jhl;
	}

	@Column(name = "create_date", length = 19)
	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	@Column(name = "ls", length = 300)
	@ColumnTransformer(
			forColumn="ls",
			read="cast(aes_decrypt(unhex(ls), '" + com.gdpost.web.MySQLAESKey.AESKey + "') as char(100))", 
			write="aes_encrypt(?,'" + com.gdpost.web.MySQLAESKey.AESKey + "')")
	public String getLs() {
		return ls;
	}

	public void setLs(String ls) {
		this.ls = ls;
	}

	@Column(name = "cs", length = 300)
	@ColumnTransformer(
			forColumn="cs",
			read="cast(aes_decrypt(unhex(cs), '" + com.gdpost.web.MySQLAESKey.AESKey + "') as char(100))", 
			write="aes_encrypt(?,'" + com.gdpost.web.MySQLAESKey.AESKey + "')")
	public String getCs() {
		return cs;
	}

	public void setCs(String cs) {
		this.cs = cs;
	}

	@Column(name = "tm", length = 300)
	@ColumnTransformer(
			forColumn="tm",
			read="cast(aes_decrypt(unhex(tm), '" + com.gdpost.web.MySQLAESKey.AESKey + "') as char(100))", 
			write="aes_encrypt(?,'" + com.gdpost.web.MySQLAESKey.AESKey + "')")
	public String getTm() {
		return tm;
	}

	public void setTm(String tm) {
		this.tm = tm;
	}

	@Column(name = "pldl", length = 300)
	@ColumnTransformer(
			forColumn="pldl",
			read="cast(aes_decrypt(unhex(pldl), '" + com.gdpost.web.MySQLAESKey.AESKey + "') as char(100))", 
			write="aes_encrypt(?,'" + com.gdpost.web.MySQLAESKey.AESKey + "')")
	public String getPldl() {
		return pldl;
	}

	public void setPldl(String pldl) {
		this.pldl = pldl;
	}

	@Column(name = "plzl", length = 300)
	@ColumnTransformer(
			forColumn="plzl",
			read="cast(aes_decrypt(unhex(plzl), '" + com.gdpost.web.MySQLAESKey.AESKey + "') as char(100))", 
			write="aes_encrypt(?,'" + com.gdpost.web.MySQLAESKey.AESKey + "')")
	public String getPlzl() {
		return plzl;
	}

	public void setPlzl(String plzl) {
		this.plzl = plzl;
	}

	@Column(name = "yb", length = 100)
	@ColumnTransformer(
			forColumn="yb",
			read="cast(aes_decrypt(unhex(yb), '" + com.gdpost.web.MySQLAESKey.AESKey + "') as char(100))", 
			write="aes_encrypt(?,'" + com.gdpost.web.MySQLAESKey.AESKey + "')")
	public String getYb() {
		return yb;
	}

	public void setYb(String yb) {
		this.yb = yb;
	}

	@Column(name = "otc", length = 100)
	@ColumnTransformer(
			forColumn="otc",
			read="cast(aes_decrypt(unhex(otc), '" + com.gdpost.web.MySQLAESKey.AESKey + "') as char(100))", 
			write="aes_encrypt(?,'" + com.gdpost.web.MySQLAESKey.AESKey + "')")
	public String getOtc() {
		return otc;
	}

	public void setOtc(String otc) {
		this.otc = otc;
	}

	@Column(name = "ydbm", length = 300)
	@ColumnTransformer(
			forColumn="ydbm",
			read="cast(aes_decrypt(unhex(ydbm), '" + com.gdpost.web.MySQLAESKey.AESKey + "') as char(100))", 
			write="aes_encrypt(?,'" + com.gdpost.web.MySQLAESKey.AESKey + "')")
	public String getYdbm() {
		return ydbm;
	}

	public void setYdbm(String ydbm) {
		this.ydbm = ydbm;
	}

	@Column(name = "ypbm", length = 300)
	@ColumnTransformer(
			forColumn="ypbm",
			read="cast(aes_decrypt(unhex(ypbm), '" + com.gdpost.web.MySQLAESKey.AESKey + "') as char(100))", 
			write="aes_encrypt(?,'" + com.gdpost.web.MySQLAESKey.AESKey + "')")
	public String getYpbm() {
		return ypbm;
	}

	public void setYpbm(String ypbm) {
		this.ypbm = ypbm;
	}

	@Column(name = "dw", length = 300)
	@ColumnTransformer(
			forColumn="dw",
			read="cast(aes_decrypt(unhex(dw), '" + com.gdpost.web.MySQLAESKey.AESKey + "') as char(100))", 
			write="aes_encrypt(?,'" + com.gdpost.web.MySQLAESKey.AESKey + "')")
	public String getDw() {
		return dw;
	}

	public void setDw(String dw) {
		this.dw = dw;
	}


}