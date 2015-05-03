package com.gdpost.web.entity.basedata;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.gdpost.web.entity.Idable;
import com.gdpost.web.entity.main.CallFail;

/**
 * TCallDealType entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_call_deal_type", catalog = "postinsurance")
public class CallDealType implements Idable<Long> {

	// Fields

	private Long id;
	private String typeName;
	private String typeDesc;
	private List<CallFail> callFails = new ArrayList<CallFail>(0);

	// Constructors

	/** default constructor */
	public CallDealType() {
	}

	/** full constructor */
	public CallDealType(String typeName, String typeDesc, List<CallFail> TCallFails) {
		this.typeName = typeName;
		this.typeDesc = typeDesc;
		this.callFails = TCallFails;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "type_name", length = 32)
	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Column(name = "type_desc")
	public String getTypeDesc() {
		return this.typeDesc;
	}

	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}

	@OneToMany(mappedBy="callDealType", cascade={CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval=true)
	public List<CallFail> getCallFails() {
		return callFails;
	}

	public void setCallFails(List<CallFail> callFails) {
		this.callFails = callFails;
	}
}