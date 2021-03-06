/**
 * 
 */
package com.gdpost.web.shiro;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.gdpost.web.entity.main.DataControl;
import com.gdpost.web.entity.main.Module;
import com.gdpost.web.entity.main.User;

/**
 * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
 */
public class ShiroUser implements Serializable {

	private static final long serialVersionUID = -1748602382963711884L;
	private Long id;
	private String loginName;
	private String ipAddress;
	private User user;
	//private TblMemberUser memberUser;
	private String userType = "admin";
	private String flag = "";
	
	private Map<String, DataControl> hasDataControls = new HashMap<String, DataControl>();
	//private Map<String, TblMemberDataControl> hasTblMemberDataControls = new HashMap<String, TblMemberDataControl>();
	private Map<String, Module> hasModules = new HashMap<String, Module>();
	//private Map<String, TblMemberModule> hasTblMemberModules = new HashMap<String, TblMemberModule>();
	
	/**
	 * 加入更多的自定义参数
	 */
	private Map<String, Object> attribute = new HashMap<String, Object>();
	
	public ShiroUser() {
		
	}
	
	public ShiroUser(String loginName) {
		this.loginName = loginName;
	}
	
	public ShiroUser(Long id, String loginName) {
		this.id = id;
		this.loginName = loginName;
	}
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the loginName
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * @param loginName the loginName to set
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * @return the ipAddress
	 */
	public String getIpAddress() {
		return ipAddress;
	}

	/**
	 * @param ipAddress the ipAddress to set
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
//	public TblMemberUser getMemberUser() {
//		return memberUser;
//	}
//
//	public void setMemberUser(TblMemberUser memberUser) {
//		this.memberUser = memberUser;
//	}

	/**
	 * @return the hasDataControls
	 */
	public Map<String, DataControl> getHasDataControls() {
		return hasDataControls;
	}

	/**
	 * @param hasDataControls the hasDataControls to set
	 */
	public void setHasDataControls(Map<String, DataControl> hasDataControls) {
		this.hasDataControls = hasDataControls;
	}

//	public Map<String, TblMemberDataControl> getHasTblMemberDataControls() {
//		return hasTblMemberDataControls;
//	}
//
//	public void setHasTblMemberDataControls(Map<String, TblMemberDataControl> hasTblMemberDataControls) {
//		this.hasTblMemberDataControls = hasTblMemberDataControls;
//	}

	/**
	 * @return the hasModules
	 */
	public Map<String, Module> getHasModules() {
		return hasModules;
	}

	/**
	 * @param hasModules the hasModules to set
	 */
	public void setHasModules(Map<String, Module> hasModules) {
		this.hasModules = hasModules;
	}
	
//	public Map<String, TblMemberModule> getHasTblMemberModules() {
//		return hasTblMemberModules;
//	}
//
//	public void setHasTblMemberModules(Map<String, TblMemberModule> hasTblMemberModules) {
//		this.hasTblMemberModules = hasTblMemberModules;
//	}

	public void setAttribute(String name, Object value) {
		attribute.put(name, value);
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	public Object getAttribute(String name) {
		return attribute.get(name);
	}
	
	public Object removeAttribute(String name) {
		return attribute.remove(name);
	}
	
	public Map<String, Object> getAttributes() {
		return this.attribute;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * 本函数输出将作为默认的<shiro:principal/>输出.
	 */
	@Override
	public String toString() {
		return loginName;
	}
	
	public String toMyString () {
		return "ShiroUser [id=" + id + ", loginName=" + loginName + ", ipAddress=" + ipAddress + ", user=" + user 
				+ ", userType=" + userType + ", hasDataControls=" + hasDataControls + ", hasModules=" + hasModules + ", attribute=" + attribute + "]";
	}
}