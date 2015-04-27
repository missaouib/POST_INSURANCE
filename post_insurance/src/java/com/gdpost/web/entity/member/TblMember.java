package com.gdpost.web.entity.member;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.ColumnTransformer;

import com.gdpost.web.entity.Idable;

/**
 * TblMember entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tbl_member")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.gdpost.web.entity.member.TblMember")
public class TblMember implements Comparable<TblMember>, Idable<Long> {

	// Fields

	private Long id;
	private TblMember parent;
	private String memberName;
	private String description;
	private String contact;
	private String phone;
	private String address;
	private String email;
	private String deliver;
	private String deliverAddress;
	private String deliverEmail;
	private String deliverPhone;
	private String payee;
	private String account;
	private Integer status;
	private Integer priority;
	private String provinceCode;
	private String cityCode;
	private String areaCode;
	private String deliverProvinceCode;
	private String deliverCityCode;
	private String deliverAreaCode;
	private String alertMsg;
	private Integer score = 0;
	private List<TblMemberData> tblMemberDatas = new ArrayList<TblMemberData>(0);
	//private List<TblMemberCategory> tblMemberCategories = new ArrayList<TblMemberCategory>(0);
	//private List<TblMemberShop> tblMemberShops = new ArrayList<TblMemberShop>(0);
	private List<TblMemberDataTemplate> tblMemberDataTemplates = new ArrayList<TblMemberDataTemplate>(0);
	private List<TblMemberUser> tblMemberUsers = new ArrayList<TblMemberUser>(0);
	private List<TblMemberDataStatus> tblMemberDataStatuses = new ArrayList<TblMemberDataStatus>(0);
	private List<TblMemberResource> tblMemberResources = new ArrayList<TblMemberResource>(0);
	private List<TblMember> children = new ArrayList<TblMember>(0);

	// Constructors

	/** default constructor */
	public TblMember() {
	}

	/** minimal constructor */
	public TblMember(String memberName) {
		this.memberName = memberName;
	}

	/** full constructor */
	public TblMember(TblMember tblMember, String memberName, String description, String contact, String phone, String address, String email, String deliver,
			String deliverAddress, String deliverEmail, String deliverPhone, String payee, String account, Integer status, Integer priority,
			/*List<TblMemberData> tblMemberDatas, List<TblMemberCategory> tblMemberCategories, List<TblMemberShop> tblMemberShops,
			List<TblMemberDataTemplate> tblMemberDataTemplates, List<TblMemberUser> tblMemberUsers, List<TblMemberDataStatus> tblMemberDataStatuses,
			List<TblMemberResource> tblMemberResources,*/ List<TblMember> tblMembers) {
		this.parent = tblMember;
		this.memberName = memberName;
		this.description = description;
		this.contact = contact;
		this.phone = phone;
		this.address = address;
		this.email = email;
		this.deliver = deliver;
		this.deliverAddress = deliverAddress;
		this.deliverEmail = deliverEmail;
		this.deliverPhone = deliverPhone;
		this.payee = payee;
		this.account = account;
		this.status = status;
		this.priority = priority;
		//this.tblMemberDatas = tblMemberDatas;
		//this.tblMemberCategories = tblMemberCategories;
		//this.tblMemberShops = tblMemberShops;
		//this.tblMemberDataTemplates = tblMemberDataTemplates;
		//this.tblMemberUsers = tblMemberUsers;
		//this.tblMemberDataStatuses = tblMemberDataStatuses;
		//this.tblMemberResources = tblMemberResources;
		this.children = tblMembers;
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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "parent_id")
	public TblMember getParent() {
		return this.parent;
	}

	public void setParent(TblMember tblMember) {
		this.parent = tblMember;
	}

	@Column(name = "member_name", unique = true, nullable = false)
	@ColumnTransformer(
			forColumn="member_name",
			read="cast(aes_decrypt(unhex(member_name), '" + com.gdpost.web.MySQLAESKey.AESKey + "') as char(100))", 
			write="hex(aes_encrypt(?,'" + com.gdpost.web.MySQLAESKey.AESKey + "'))")
	public String getMemberName() {
		return this.memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	@Column(name = "description", length = 200)
	@ColumnTransformer(
			forColumn="description",
			read="cast(aes_decrypt(unhex(description), '" + com.gdpost.web.MySQLAESKey.AESKey + "') as char(500))", 
			write="hex(aes_encrypt(?,'" + com.gdpost.web.MySQLAESKey.AESKey + "'))")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "contact", length = 20)
	@ColumnTransformer(
			forColumn="contact",
			read="cast(aes_decrypt(unhex(contact), '" + com.gdpost.web.MySQLAESKey.AESKey + "') as char(100))", 
			write="hex(aes_encrypt(?,'" + com.gdpost.web.MySQLAESKey.AESKey + "'))")
	public String getContact() {
		return this.contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	@Column(name = "phone", length = 20)
	@ColumnTransformer(
			forColumn="phone",
			read="cast(aes_decrypt(unhex(phone), '" + com.gdpost.web.MySQLAESKey.AESKey + "') as char(100))", 
			write="hex(aes_encrypt(?,'" + com.gdpost.web.MySQLAESKey.AESKey + "'))")
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "address", length = 200)
	@ColumnTransformer(
			forColumn="address",
			read="cast(aes_decrypt(unhex(address), '" + com.gdpost.web.MySQLAESKey.AESKey + "') as char(500))", 
			write="hex(aes_encrypt(?,'" + com.gdpost.web.MySQLAESKey.AESKey + "'))")
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "email", length = 50)
	@ColumnTransformer(
			forColumn="email",
			read="cast(aes_decrypt(unhex(email), '" + com.gdpost.web.MySQLAESKey.AESKey + "') as char(200))", 
			write="hex(aes_encrypt(?,'" + com.gdpost.web.MySQLAESKey.AESKey + "'))")
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "deliver", length = 20)
	@ColumnTransformer(
			forColumn="deliver",
			read="cast(aes_decrypt(unhex(deliver), '" + com.gdpost.web.MySQLAESKey.AESKey + "') as char(100))", 
			write="hex(aes_encrypt(?,'" + com.gdpost.web.MySQLAESKey.AESKey + "'))")
	public String getDeliver() {
		return this.deliver;
	}

	public void setDeliver(String deliver) {
		this.deliver = deliver;
	}

	@Column(name = "deliver_address", length = 200)
	@ColumnTransformer(
			forColumn="deliver_address",
			read="cast(aes_decrypt(unhex(deliver_address), '" + com.gdpost.web.MySQLAESKey.AESKey + "') as char(500))", 
			write="hex(aes_encrypt(?,'" + com.gdpost.web.MySQLAESKey.AESKey + "'))")
	public String getDeliverAddress() {
		return this.deliverAddress;
	}

	public void setDeliverAddress(String deliverAddress) {
		this.deliverAddress = deliverAddress;
	}

	@Column(name = "deliver_email", length = 50)
	@ColumnTransformer(
			forColumn="deliver_email",
			read="cast(aes_decrypt(unhex(deliver_email), '" + com.gdpost.web.MySQLAESKey.AESKey + "') as char(200))", 
			write="hex(aes_encrypt(?,'" + com.gdpost.web.MySQLAESKey.AESKey + "'))")
	public String getDeliverEmail() {
		return this.deliverEmail;
	}

	public void setDeliverEmail(String deliverEmail) {
		this.deliverEmail = deliverEmail;
	}

	@Column(name = "deliver_phone", length = 20)
	@ColumnTransformer(
			forColumn="deliver_phone",
			read="cast(aes_decrypt(unhex(deliver_phone), '" + com.gdpost.web.MySQLAESKey.AESKey + "') as char(100))", 
			write="hex(aes_encrypt(?,'" + com.gdpost.web.MySQLAESKey.AESKey + "'))")
	public String getDeliverPhone() {
		return this.deliverPhone;
	}

	public void setDeliverPhone(String deliverPhone) {
		this.deliverPhone = deliverPhone;
	}

	@Column(name = "payee", length = 20)
	@ColumnTransformer(
			forColumn="payee",
			read="cast(aes_decrypt(unhex(payee), '" + com.gdpost.web.MySQLAESKey.AESKey + "') as char(100))", 
			write="hex(aes_encrypt(?,'" + com.gdpost.web.MySQLAESKey.AESKey + "'))")
	public String getPayee() {
		return this.payee;
	}

	public void setPayee(String payee) {
		this.payee = payee;
	}

	@Column(name = "account", length = 50)
	@ColumnTransformer(
			forColumn="account",
			read="cast(aes_decrypt(unhex(account), '" + com.gdpost.web.MySQLAESKey.AESKey + "') as char(200))", 
			write="hex(aes_encrypt(?,'" + com.gdpost.web.MySQLAESKey.AESKey + "'))")
	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "priority")
	public Integer getPriority() {
		return this.priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	@Column(name = "province_code")
	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	@Column(name = "city_code")
	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	@Column(name = "area_code")
	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	@Column(name = "deliver_province_code")
	public String getDeliverProvinceCode() {
		return deliverProvinceCode;
	}

	public void setDeliverProvinceCode(String deliverProvinceCode) {
		this.deliverProvinceCode = deliverProvinceCode;
	}

	@Column(name = "deliver_city_code")
	public String getDeliverCityCode() {
		return deliverCityCode;
	}

	public void setDeliverCityCode(String deliverCityCode) {
		this.deliverCityCode = deliverCityCode;
	}

	@Column(name = "deliver_area_code")
	public String getDeliverAreaCode() {
		return deliverAreaCode;
	}

	public void setDeliverAreaCode(String deliverAreaCode) {
		this.deliverAreaCode = deliverAreaCode;
	}


	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY, mappedBy = "tblMember", orphanRemoval=true)
	public List<TblMemberData> getTblMemberDatas() {
		return this.tblMemberDatas;
	}

	public void setTblMemberDatas(List<TblMemberData> tblMemberDatas) {
		this.tblMemberDatas = tblMemberDatas;
	}
	/*
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tblMember")
	public List<TblMemberCategory> getTblMemberCategories() {
		return this.tblMemberCategories;
	}

	public void setTblMemberCategories(List<TblMemberCategory> tblMemberCategories) {
		this.tblMemberCategories = tblMemberCategories;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tblMember")
	public List<TblMemberShop> getTblMemberShops() {
		return this.tblMemberShops;
	}

	public void setTblMemberShops(List<TblMemberShop> tblMemberShops) {
		this.tblMemberShops = tblMemberShops;
	}
	*/

	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY, mappedBy = "tblMember", orphanRemoval=true)
	public List<TblMemberDataTemplate> getTblMemberDataTemplates() {
		return this.tblMemberDataTemplates;
	}

	public void setTblMemberDataTemplates(List<TblMemberDataTemplate> tblMemberDataTemplates) {
		this.tblMemberDataTemplates = tblMemberDataTemplates;
	}

	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY, mappedBy = "tblMember", orphanRemoval=true)
	public List<TblMemberUser> getTblMemberUsers() {
		return this.tblMemberUsers;
	}

	public void setTblMemberUsers(List<TblMemberUser> tblMemberUsers) {
		this.tblMemberUsers = tblMemberUsers;
	}

	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY, mappedBy = "tblMember", orphanRemoval=true)
	public List<TblMemberDataStatus> getTblMemberDataStatuses() {
		return this.tblMemberDataStatuses;
	}

	public void setTblMemberDataStatuses(List<TblMemberDataStatus> tblMemberDataStatuses) {
		this.tblMemberDataStatuses = tblMemberDataStatuses;
	}

	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY, mappedBy = "tblMember", orphanRemoval=true)
	public List<TblMemberResource> getTblMemberResources() {
		return this.tblMemberResources;
	}

	public void setTblMemberResources(List<TblMemberResource> tblMemberResources) {
		this.tblMemberResources = tblMemberResources;
	}
	
	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY, mappedBy = "parent")
	public List<TblMember> getChildren() {
		return this.children;
	}

	public void setChildren(List<TblMember> tblMembers) {
		//this.children.clear();
		//this.children.addAll(tblMembers);
		this.children = tblMembers;
	}

	/*
	 * 
	 */
	@Override
	public int compareTo(TblMember org) {
		if (org == null) {
			return -1;
		} else if (org == this) {
			return 0;
		} else if (this.priority < org.getPriority()) {
			return -1;
		} else if (this.priority > org.getPriority()) {
			return 1;
		}

		return 0;	
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Column(name = "alert_msg")
	public String getAlertMsg() {
		return alertMsg;
	}

	public void setAlertMsg(String alertMsg) {
		this.alertMsg = alertMsg;
	}
	
	@Transient
	private String provinceName;
	@Transient
	private String cityName;
	@Transient
	private String areaName;

	@Transient
	public String getProvinceName() {
		return provinceName;
	}

	@Transient
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	@Transient
	public String getCityName() {
		return cityName;
	}

	@Transient
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	@Transient
	public String getAreaName() {
		return areaName;
	}

	@Transient
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	@Column(name = "score")
	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}
}