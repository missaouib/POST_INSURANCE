package com.gdpost.web.entity.insurance;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.gdpost.web.entity.Idable;


/**
 * TStasticsArea entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="t_stastics_area")

public class StasticsArea  implements Idable<Long>,Serializable {


    // Fields    

     /**
	 * 
	 */
	private static final long serialVersionUID = -3631897688876870887L;
	private Long id;
     private String organCode;
     private String organName;
     private String mth;
     private String qyhglValue;
     private Double qyhglScore;
     private String kfhfcglValue;
     private Double kfhfcglScore;
     private String wtjValue;
     private Double wtjScore;
     private String xq13jValue;
     private Double xq13jScore;
     private String xq25jValue;
     private Double xq25jScore;
     private String qjxdtblValue;
     private Double qjxdtblScore;
     private Double billvalueTotalScore;
     private String uwValue;
     private Double uwScore;
     private String lppayValue;
     private Double lppayScore;
     private String invalidValue;
     private Double invalidScore;
     private Double kfTotalScore;
     private String inputValue;
     private Double inputScore;
     private String gwtjValue;
     private Double gwtjScore;
     private String lpjyxValue;
     private Double lpjyxScore;
     private Double jobTotalScore;
     private String bqjyjkValue;
     private Double bqjyjkScore;
     private String truthValue;
     private Double truthScore;
     private Double riskTotalScore;
     private Double totalScore;
     private Integer citySort;
     private Long operateId;
     private Date operateTime;
     private String operateName;
     
     private String xq3ylostValue;
     private Double xq3ylostScore;
     private String xqissueValue;
     private Double xqissueScore;
     private String issuetimeValue;
     private Double issuetimeScore;
     private String riskValue;
     private Double riskScore;

     @Transient
     private String mthYear;
     @Transient
     private String mthMonth;
     
    // Constructors
    @Transient
    public String getMthYear() {
		return mthYear;
	}
    @Transient
	public void setMthYear(String mthYear) {
		this.mthYear = mthYear;
	}
    @Transient
	public String getMthMonth() {
		return mthMonth;
	}
    @Transient
	public void setMthMonth(String mthMonth) {
		this.mthMonth = mthMonth;
	}
    
    // Constructors

    /** default constructor */
    public StasticsArea() {
    }

    
    /** full constructor */
    public StasticsArea(String organCode, String organName, String mth, String qyhglValue, Double qyhglScore, String kfhfcglValue, Double kfhfcglScore, String wtjValue, Double wtjScore, String xq13jValue, Double xq13jScore, String xq25jValue, Double xq25jScore, String qjxdtblValue, Double qjxdtblScore, Double billvalueTotalScore, String uwValue, Double uwScore, String lppayValue, Double lppayScore, String invalidValue, Double invalidScore, Double kfTotalScore, String inputValue, Double inputScore, String gwtjValue, Double gwtjScore, String lpjyxValue, Double lpjyxScore, Double jobTotalScore, String bqjyjkValue, Double bqjyjkScore, String truthValue, Double truthScore, Double riskTotalScore, Double totalScore, Integer citySort, Long operateId, Date operateTime, String operateName) {
        this.organCode = organCode;
        this.organName = organName;
        this.mth = mth;
        this.qyhglValue = qyhglValue;
        this.qyhglScore = qyhglScore;
        this.kfhfcglValue = kfhfcglValue;
        this.kfhfcglScore = kfhfcglScore;
        this.wtjValue = wtjValue;
        this.wtjScore = wtjScore;
        this.xq13jValue = xq13jValue;
        this.xq13jScore = xq13jScore;
        this.xq25jValue = xq25jValue;
        this.xq25jScore = xq25jScore;
        this.qjxdtblValue = qjxdtblValue;
        this.qjxdtblScore = qjxdtblScore;
        this.billvalueTotalScore = billvalueTotalScore;
        this.uwValue = uwValue;
        this.uwScore = uwScore;
        this.lppayValue = lppayValue;
        this.lppayScore = lppayScore;
        this.invalidValue = invalidValue;
        this.invalidScore = invalidScore;
        this.kfTotalScore = kfTotalScore;
        this.inputValue = inputValue;
        this.inputScore = inputScore;
        this.gwtjValue = gwtjValue;
        this.gwtjScore = gwtjScore;
        this.lpjyxValue = lpjyxValue;
        this.lpjyxScore = lpjyxScore;
        this.jobTotalScore = jobTotalScore;
        this.bqjyjkValue = bqjyjkValue;
        this.bqjyjkScore = bqjyjkScore;
        this.truthValue = truthValue;
        this.truthScore = truthScore;
        this.riskTotalScore = riskTotalScore;
        this.totalScore = totalScore;
        this.citySort = citySort;
        this.operateId = operateId;
        this.operateTime = operateTime;
        this.operateName = operateName;
    }

   
    // Property accessors
    @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id", unique=true, nullable=false)

    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    @Column(name="organ_code", length=20)

    public String getOrganCode() {
        return this.organCode;
    }
    
    public void setOrganCode(String organCode) {
        this.organCode = organCode;
    }
    
    @Column(name="organ_name", length=60)

    public String getOrganName() {
        return this.organName;
    }
    
    public void setOrganName(String organName) {
        this.organName = organName;
    }
    
    @Column(name="mth", nullable=false)

    public String getMth() {
        return this.mth;
    }
    
    public void setMth(String mth) {
        this.mth = mth;
    }
    
    @Column(name="qyhgl_value", length=7)

    public String getQyhglValue() {
        return this.qyhglValue;
    }
    
    public void setQyhglValue(String qyhglValue) {
        this.qyhglValue = qyhglValue;
    }
    
    @Column(name="qyhgl_score", precision=22, scale=0)

    public Double getQyhglScore() {
        return this.qyhglScore;
    }
    
    public void setQyhglScore(Double qyhglScore) {
        this.qyhglScore = qyhglScore;
    }
    
    @Column(name="kfhfcgl_value", length=7)

    public String getKfhfcglValue() {
        return this.kfhfcglValue;
    }
    
    public void setKfhfcglValue(String kfhfcglValue) {
        this.kfhfcglValue = kfhfcglValue;
    }
    
    @Column(name="kfhfcgl_score", precision=22, scale=0)

    public Double getKfhfcglScore() {
        return this.kfhfcglScore;
    }
    
    public void setKfhfcglScore(Double kfhfcglScore) {
        this.kfhfcglScore = kfhfcglScore;
    }
    
    @Column(name="wtj_value", length=7)

    public String getWtjValue() {
        return this.wtjValue;
    }
    
    public void setWtjValue(String wtjValue) {
        this.wtjValue = wtjValue;
    }
    
    @Column(name="wtj_score", precision=22, scale=0)

    public Double getWtjScore() {
        return this.wtjScore;
    }
    
    public void setWtjScore(Double wtjScore) {
        this.wtjScore = wtjScore;
    }
    
    @Column(name="xq13j_value", length=7)

    public String getXq13jValue() {
        return this.xq13jValue;
    }
    
    public void setXq13jValue(String xq13jValue) {
        this.xq13jValue = xq13jValue;
    }
    
    @Column(name="xq13j_score", precision=22, scale=0)

    public Double getXq13jScore() {
        return this.xq13jScore;
    }
    
    public void setXq13jScore(Double xq13jScore) {
        this.xq13jScore = xq13jScore;
    }
    
    @Column(name="xq25j_value", length=7)

    public String getXq25jValue() {
        return this.xq25jValue;
    }
    
    public void setXq25jValue(String xq25jValue) {
        this.xq25jValue = xq25jValue;
    }
    
    @Column(name="xq25j_score", precision=22, scale=0)

    public Double getXq25jScore() {
        return this.xq25jScore;
    }
    
    public void setXq25jScore(Double xq25jScore) {
        this.xq25jScore = xq25jScore;
    }
    
    @Column(name="qjxdtbl_value", length=7)

    public String getQjxdtblValue() {
        return this.qjxdtblValue;
    }
    
    public void setQjxdtblValue(String qjxdtblValue) {
        this.qjxdtblValue = qjxdtblValue;
    }
    
    @Column(name="qjxdtbl_score", precision=22, scale=0)

    public Double getQjxdtblScore() {
        return this.qjxdtblScore;
    }
    
    public void setQjxdtblScore(Double qjxdtblScore) {
        this.qjxdtblScore = qjxdtblScore;
    }
    
    @Column(name="billvalue_total_score", precision=22, scale=0)

    public Double getBillvalueTotalScore() {
        return this.billvalueTotalScore;
    }
    
    public void setBillvalueTotalScore(Double billvalueTotalScore) {
        this.billvalueTotalScore = billvalueTotalScore;
    }
    
    @Column(name="uw_value", length=7)

    public String getUwValue() {
        return this.uwValue;
    }
    
    public void setUwValue(String uwValue) {
        this.uwValue = uwValue;
    }
    
    @Column(name="uw_score", precision=22, scale=0)

    public Double getUwScore() {
        return this.uwScore;
    }
    
    public void setUwScore(Double uwScore) {
        this.uwScore = uwScore;
    }
    
    @Column(name="lppay_value", length=7)

    public String getLppayValue() {
        return this.lppayValue;
    }
    
    public void setLppayValue(String lppayValue) {
        this.lppayValue = lppayValue;
    }
    
    @Column(name="lppay_score", precision=22, scale=0)

    public Double getLppayScore() {
        return this.lppayScore;
    }
    
    public void setLppayScore(Double lppayScore) {
        this.lppayScore = lppayScore;
    }
    
    @Column(name="invalid_value", length=7)

    public String getInvalidValue() {
        return this.invalidValue;
    }
    
    public void setInvalidValue(String invalidValue) {
        this.invalidValue = invalidValue;
    }
    
    @Column(name="invalid_score", precision=22, scale=0)

    public Double getInvalidScore() {
        return this.invalidScore;
    }
    
    public void setInvalidScore(Double invalidScore) {
        this.invalidScore = invalidScore;
    }
    
    @Column(name="kf_total_score", precision=22, scale=0)

    public Double getKfTotalScore() {
        return this.kfTotalScore;
    }
    
    public void setKfTotalScore(Double kfTotalScore) {
        this.kfTotalScore = kfTotalScore;
    }
    
    @Column(name="input_value", length=7)

    public String getInputValue() {
        return this.inputValue;
    }
    
    public void setInputValue(String inputValue) {
        this.inputValue = inputValue;
    }
    
    @Column(name="input_score", precision=22, scale=0)

    public Double getInputScore() {
        return this.inputScore;
    }
    
    public void setInputScore(Double inputScore) {
        this.inputScore = inputScore;
    }
    
    @Column(name="gwtj_value", length=7)

    public String getGwtjValue() {
        return this.gwtjValue;
    }
    
    public void setGwtjValue(String gwtjValue) {
        this.gwtjValue = gwtjValue;
    }
    
    @Column(name="gwtj_score", precision=22, scale=0)

    public Double getGwtjScore() {
        return this.gwtjScore;
    }
    
    public void setGwtjScore(Double gwtjScore) {
        this.gwtjScore = gwtjScore;
    }
    
    @Column(name="lpjyx_value", length=7)

    public String getLpjyxValue() {
        return this.lpjyxValue;
    }
    
    public void setLpjyxValue(String lpjyxValue) {
        this.lpjyxValue = lpjyxValue;
    }
    
    @Column(name="lpjyx_score", precision=22, scale=0)

    public Double getLpjyxScore() {
        return this.lpjyxScore;
    }
    
    public void setLpjyxScore(Double lpjyxScore) {
        this.lpjyxScore = lpjyxScore;
    }
    
    @Column(name="job_total_score", precision=22, scale=0)

    public Double getJobTotalScore() {
        return this.jobTotalScore;
    }
    
    public void setJobTotalScore(Double jobTotalScore) {
        this.jobTotalScore = jobTotalScore;
    }
    
    @Column(name="bqjyjk_value", length=7)

    public String getBqjyjkValue() {
        return this.bqjyjkValue;
    }
    
    public void setBqjyjkValue(String bqjyjkValue) {
        this.bqjyjkValue = bqjyjkValue;
    }
    
    @Column(name="bqjyjk_score", precision=22, scale=0)

    public Double getBqjyjkScore() {
        return this.bqjyjkScore;
    }
    
    public void setBqjyjkScore(Double bqjyjkScore) {
        this.bqjyjkScore = bqjyjkScore;
    }
    
    @Column(name="truth_value", length=7)

    public String getTruthValue() {
        return this.truthValue;
    }
    
    public void setTruthValue(String truthValue) {
        this.truthValue = truthValue;
    }
    
    @Column(name="truth_score", precision=22, scale=0)

    public Double getTruthScore() {
        return this.truthScore;
    }
    
    public void setTruthScore(Double truthScore) {
        this.truthScore = truthScore;
    }
    
    @Column(name="risk_total_score", precision=22, scale=0)

    public Double getRiskTotalScore() {
        return this.riskTotalScore;
    }
    
    public void setRiskTotalScore(Double riskTotalScore) {
        this.riskTotalScore = riskTotalScore;
    }
    
    @Column(name="total_score", precision=22, scale=0)

    public Double getTotalScore() {
        return this.totalScore;
    }
    
    public void setTotalScore(Double totalScore) {
        this.totalScore = totalScore;
    }
    
    @Column(name="city_sort")

    public Integer getCitySort() {
        return this.citySort;
    }
    
    public void setCitySort(Integer citySort) {
        this.citySort = citySort;
    }
    
    @Column(name="operate_id")

    public Long getOperateId() {
        return this.operateId;
    }
    
    public void setOperateId(Long operateId) {
        this.operateId = operateId;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="operate_time", length=10)

    public Date getOperateTime() {
        return this.operateTime;
    }
    
    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }
    
    @Column(name="operate_name", length=32)

    public String getOperateName() {
        return this.operateName;
    }
    
    public void setOperateName(String operateName) {
        this.operateName = operateName;
    }
    
    @Column(name="xq3ylost_value")
	public String getXq3ylostValue() {
		return xq3ylostValue;
	}
	public void setXq3ylostValue(String xq3ylostValue) {
		this.xq3ylostValue = xq3ylostValue;
	}
	
	@Column(name="xq3ylost_score")
	public Double getXq3ylostScore() {
		return xq3ylostScore;
	}
	public void setXq3ylostScore(Double xq3ylostScore) {
		this.xq3ylostScore = xq3ylostScore;
	}
	
	@Column(name="xqissue_value")
	public String getXqissueValue() {
		return xqissueValue;
	}
	public void setXqissueValue(String xqissueValue) {
		this.xqissueValue = xqissueValue;
	}
	
	@Column(name="xqissue_score")
	public Double getXqissueScore() {
		return xqissueScore;
	}
	public void setXqissueScore(Double xqissueScore) {
		this.xqissueScore = xqissueScore;
	}
	
	@Column(name="issuetime_value")
	public String getIssuetimeValue() {
		return issuetimeValue;
	}
	public void setIssuetimeValue(String issuetimeValue) {
		this.issuetimeValue = issuetimeValue;
	}
	
	@Column(name="issuetime_score")
	public Double getIssuetimeScore() {
		return issuetimeScore;
	}
	public void setIssuetimeScore(Double issuetimeScore) {
		this.issuetimeScore = issuetimeScore;
	}
	
	@Column(name="risk_value")
	public String getRiskValue() {
		return riskValue;
	}
	public void setRiskValue(String riskValue) {
		this.riskValue = riskValue;
	}
	
	@Column(name="risk_score")
	public Double getRiskScore() {
		return riskScore;
	}
	public void setRiskScore(Double riskScore) {
		this.riskScore = riskScore;
	}
    
    
}