/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, 
 * Filename:		com.gdpost.web.entity.main.LogEntity.java
 * Class:			LogEntity
 * Date:			2013-5-3
 * Author:			Aming
 * Version          2.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.gdpost.web.entity.main;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.gdpost.web.entity.Idable;
import com.gdpost.web.log.LogLevel;

/** 
 * 	
 * @author 	Aming
 * Version  2.1.0
 * @since   2013-5-3 下午4:43:44 
 */
@Entity
@Table(name="t_log_info")
public class LogInfo implements Idable<Long> {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@Column(name="username", length=32)
	private String username;

	@Column(name="message", length=256)
	private String message;
	
	@Column(name="ip_address", length=16)
	private String ipAddress;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time", updatable=false)
	private Date createTime;
	
	@Column(name="log_level", length=16)
	@Enumerated(EnumType.STRING)
	private LogLevel logLevel;
	
	@Column(name="module", length=32)
	private String module;
	
	@Transient
	private String search_LIKE_module;
	
	@Transient
	public String getSearch_LIKE_module() {
		return search_LIKE_module;
	}
	@Transient
	public void setSearch_LIKE_module(String search_LIKE_module) {
		this.search_LIKE_module = search_LIKE_module;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public LogLevel getLogLevel() {
		return logLevel;
	}

	public void setLogLevel(LogLevel logLevel) {
		this.logLevel = logLevel;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}
	
}
