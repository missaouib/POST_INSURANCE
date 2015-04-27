package com.gdpost.web.entity.member;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.Date;
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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.validator.constraints.NotBlank;

import com.gdpost.web.entity.Idable;

/**
 * TblMemberUser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tbl_member_user")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.gdpost.web.entity.member.TblMemberUser")
public class TblMemberUser implements Comparable<TblMemberUser>, Idable<Long> {

	// Fields
	public static final String STATUS_DISABLED = "disabled";
	public static final String STATUS_ENABLED = "enabled";

	private Long id;
	private TblMember tblMember;
	private String userName;
	private String realName;
	private String password;
	private String salt;
	private String phone;
	private String email;
	private String status = STATUS_ENABLED;
	private String description;
	private Integer isAdmin;
	private Date createDate;
	private Long createdBy;
	private List<TblMemberMessage> tblMemberMessages = new ArrayList<TblMemberMessage>(0);
	private List<TblMemberUserRole> tblMemberUserRoles = new ArrayList<TblMemberUserRole>(0);

	/** default constructor */
	public TblMemberUser() {
	}

	/** minimal constructor */
	public TblMemberUser(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	/** full constructor */
	public TblMemberUser(TblMember tblMember, String userName, String realName, String password, String salt, String phone, String email, String status,
			String description, Integer isAdmin, Date createDate, Long createdBy, List<TblMemberMessage> tblMemberMessages,
			List<TblMemberUserRole> tblMemberUserRoles) {
		this.tblMember = tblMember;
		this.userName = userName;
		this.realName = realName;
		this.password = password;
		this.salt = salt;
		this.phone = phone;
		this.email = email;
		this.status = status;
		this.description = description;
		this.isAdmin = isAdmin;
		this.createDate = createDate;
		this.createdBy = createdBy;
		this.tblMemberMessages = tblMemberMessages;
		this.tblMemberUserRoles = tblMemberUserRoles;
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
	@JoinColumn(name = "member_id")
	public TblMember getTblMember() {
		return this.tblMember;
	}

	public void setTblMember(TblMember tblMember) {
		this.tblMember = tblMember;
	}

	@Column(name = "user_name", nullable = false, length = 20)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "real_name", length = 20)
	@ColumnTransformer(
			forColumn="real_name",
			read="cast(aes_decrypt(unhex(real_name), '" + com.gdpost.web.MySQLAESKey.AESKey + "') as char(100))", 
			write="hex(aes_encrypt(?,'" + com.gdpost.web.MySQLAESKey.AESKey + "'))")
	public String getRealName() {
		return this.realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	@Column(name = "password", nullable = false, length = 50)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Transient
	private String plainPassword;

	@Transient
	public String getPlainPassword() {
		return plainPassword;
	}

	@Transient
	public void setPlainPassword(String plainPassword) {
		this.plainPassword = plainPassword;
	}

	@Column(name = "salt", length = 50)
	public String getSalt() {
		return this.salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
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

	@NotBlank
	@Column(name = "status")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	@Column(name = "is_admin")
	public Integer getIsAdmin() {
		return this.isAdmin;
	}

	public void setIsAdmin(Integer isAdmin) {
		this.isAdmin = isAdmin;
	}

	@Column(name = "create_date", length = 19)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "created_by")
	public Long getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY, mappedBy = "tblMemberUser", orphanRemoval=true)
	public List<TblMemberMessage> getTblMemberMessages() {
		return this.tblMemberMessages;
	}

	public void setTblMemberMessages(List<TblMemberMessage> tblMemberMessages) {
		this.tblMemberMessages = tblMemberMessages;
	}

	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY, mappedBy = "tblMemberUser", orphanRemoval=true)
	public List<TblMemberUserRole> getTblMemberUserRoles() {
		return this.tblMemberUserRoles;
	}

	public void setTblMemberUserRoles(List<TblMemberUserRole> tblMemberUserRoles) {
		this.tblMemberUserRoles = tblMemberUserRoles;
	}

	@Override
	public String toString() {
		return "TblMemberUser [id=" + id + ", tblMember=" + tblMember + ", userName=" + userName + ", realName=" + realName + ", password=" + password
				+ ", salt=" + salt + ", phone=" + phone + ", email=" + email + ", status=" + status + ", description=" + description + ", isAdmin=" + isAdmin
				+ ", createDate=" + createDate + ", createdBy=" + createdBy + ", tblMemberMessages=" + tblMemberMessages + ", tblMemberUserRoles="
				+ tblMemberUserRoles + ", plainPassword=" + plainPassword + "]";
	}

	@Override
	public int compareTo(TblMemberUser arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
}