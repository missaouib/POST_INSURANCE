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
 * TStasticsCity entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="t_stastics_city")

public class StasticsCity implements Idable<Long>,Serializable {


    // Fields    

     /**
	 * 
	 */
	private static final long serialVersionUID = -3592923876742033732L;
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
     private String lp7dValue;
     private Double lp7dScore;
     private String invalidValue;
     private Double invalidScore;
     private Double kfTotalScore;
     private String inputValue;
     private Double inputScore;
     private String checkValue;
     private Double checkScore;
     private String gwtjValue;
     private Double gwtjScore;
     private String lpjyxValue;
     private Double lpjyxScore;
     private Double jobTotalScore;
     private String bqjyjkValue;
     private Double bqjyjkScore;
     private String dzzkValue;
     private Double dzzkScore;
     private String truthValue;
     private Double truthScore;
     private Double riskTotalScore;
     private String bqmqriskValue;
     private Double bqmqriskScore;
     private String bqmqjobValue;
     private Double bqmqjobScore;
     private String bqxtValue;
     private Double bqxtScore;
     private String bqmqValue;
     private Double bqmqScore;
     private String qyprintValue;
     private Double qyprintScore;
     private String checkdealValue;
     private Double checkdealScore;
     private Double importantjobTotalScore;
     private Double totalScore;
     private Integer citySort;
     private Long operateId;
     private Date operateTime;
     private String operateName;
     
     private String xq3ylostValue;
     private Double xq3ylostScore;
     private String lp7dnumValue;
     private Double lp7dnumScore;
     private String lpcallValue;
     private Double lpcallScore;
     private String xqissueValue;
     private Double xqissueScore;
     private String dasubmitValue;
     private Double dasubmitScore;
     private String riskValue;
     private Double riskScore;
     private String bqmqsxValue;
     private Double bqmqsxScore;
     private String wxcallValue;
     private Double wxcallScore;
     
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

	/** default constructor */
    public StasticsCity() {
    }

    /** full constructor */
    public StasticsCity(String organCode, String organName, String mth, String qyhglValue, Double qyhglScore, String kfhfcglValue, Double kfhfcglScore, String wtjValue, Double wtjScore, String xq13jValue, Double xq13jScore, String xq25jValue, Double xq25jScore, String qjxdtblValue, Double qjxdtblScore, Double billvalueTotalScore, String uwValue, Double uwScore, String lppayValue, Double lppayScore, String lp7dValue, Double lp7dScore, String invalidValue, Double invalidScore, Double kfTotalScore, String inputValue, Double inputScore, String checkValue, Double checkScore, String gwtjValue, Double gwtjScore, String lpjyxValue, Double lpjyxScore, Double jobTotalScore, String bqjyjkValue, Double bqjyjkScore, String dzzkValue, Double dzzkScore, String truthValue, Double truthScore, Double riskTotalScore, String bqmqriskValue, Double bqmqriskScore, String bqmqjobValue, Double bqmqjobScore, String bqxtValue, Double bqxtScore, String bqmqValue, Double bqmqScore, String qyprintValue, Double qyprintScore, Double importantjobTotalScore, Double totalScore, Integer citySort, Long operateId, Date operateTime, String operateName) {
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
        this.lp7dValue = lp7dValue;
        this.lp7dScore = lp7dScore;
        this.invalidValue = invalidValue;
        this.invalidScore = invalidScore;
        this.kfTotalScore = kfTotalScore;
        this.inputValue = inputValue;
        this.inputScore = inputScore;
        this.checkValue = checkValue;
        this.checkScore = checkScore;
        this.gwtjValue = gwtjValue;
        this.gwtjScore = gwtjScore;
        this.lpjyxValue = lpjyxValue;
        this.lpjyxScore = lpjyxScore;
        this.jobTotalScore = jobTotalScore;
        this.bqjyjkValue = bqjyjkValue;
        this.bqjyjkScore = bqjyjkScore;
        this.dzzkValue = dzzkValue;
        this.dzzkScore = dzzkScore;
        this.truthValue = truthValue;
        this.truthScore = truthScore;
        this.riskTotalScore = riskTotalScore;
        this.bqmqriskValue = bqmqriskValue;
        this.bqmqriskScore = bqmqriskScore;
        this.bqmqjobValue = bqmqjobValue;
        this.bqmqjobScore = bqmqjobScore;
        this.bqxtValue = bqxtValue;
        this.bqxtScore = bqxtScore;
        this.bqmqValue = bqmqValue;
        this.bqmqScore = bqmqScore;
        this.qyprintValue = qyprintValue;
        this.qyprintScore = qyprintScore;
        this.importantjobTotalScore = importantjobTotalScore;
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
    
    @Column(name="qyhgl_value", length=9)

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
    
    @Column(name="kfhfcgl_value", length=9)

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
    
    @Column(name="wtj_value", length=9)

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
    
    @Column(name="xq13j_value", length=9)

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
    
    @Column(name="xq25j_value", length=9)

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
    
    @Column(name="qjxdtbl_value", length=9)

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
    
    @Column(name="uw_value", length=9)

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
    
    @Column(name="lppay_value", length=9)

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
    
    @Column(name="lp7d_value", length=9)

    public String getLp7dValue() {
        return this.lp7dValue;
    }
    
    public void setLp7dValue(String lp7dValue) {
        this.lp7dValue = lp7dValue;
    }
    
    @Column(name="lp7d_score", precision=22, scale=0)

    public Double getLp7dScore() {
        return this.lp7dScore;
    }
    
    public void setLp7dScore(Double lp7dScore) {
        this.lp7dScore = lp7dScore;
    }
    
    @Column(name="invalid_value", length=9)

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
    
    @Column(name="input_value", length=9)

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
    
    @Column(name="check_value", length=9)

    public String getCheckValue() {
        return this.checkValue;
    }
    
    public void setCheckValue(String checkValue) {
        this.checkValue = checkValue;
    }
    
    @Column(name="check_score", precision=22, scale=0)

    public Double getCheckScore() {
        return this.checkScore;
    }
    
    public void setCheckScore(Double checkScore) {
        this.checkScore = checkScore;
    }
    
    @Column(name="gwtj_value", length=9)

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
    
    @Column(name="lpjyx_value", length=9)

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
    
    @Column(name="bqjyjk_value", length=9)

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
    
    @Column(name="dzzk_value", length=9)

    public String getDzzkValue() {
        return this.dzzkValue;
    }
    
    public void setDzzkValue(String dzzkValue) {
        this.dzzkValue = dzzkValue;
    }
    
    @Column(name="dzzk_score", precision=22, scale=0)

    public Double getDzzkScore() {
        return this.dzzkScore;
    }
    
    public void setDzzkScore(Double dzzkScore) {
        this.dzzkScore = dzzkScore;
    }
    
    @Column(name="truth_value", length=9)

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
    
    @Column(name="bqmqrisk_value", length=9)

    public String getBqmqriskValue() {
        return this.bqmqriskValue;
    }
    
    public void setBqmqriskValue(String bqmqriskValue) {
        this.bqmqriskValue = bqmqriskValue;
    }
    
    @Column(name="bqmqrisk_score", precision=22, scale=0)

    public Double getBqmqriskScore() {
        return this.bqmqriskScore;
    }
    
    public void setBqmqriskScore(Double bqmqriskScore) {
        this.bqmqriskScore = bqmqriskScore;
    }
    
    @Column(name="bqmqjob_value", length=9)

    public String getBqmqjobValue() {
        return this.bqmqjobValue;
    }
    
    public void setBqmqjobValue(String bqmqjobValue) {
        this.bqmqjobValue = bqmqjobValue;
    }
    
    @Column(name="bqmqjob_score", precision=22, scale=0)

    public Double getBqmqjobScore() {
        return this.bqmqjobScore;
    }
    
    public void setBqmqjobScore(Double bqmqjobScore) {
        this.bqmqjobScore = bqmqjobScore;
    }
    
    @Column(name="bqxt_value", length=9)

    public String getBqxtValue() {
        return this.bqxtValue;
    }
    
    public void setBqxtValue(String bqxtValue) {
        this.bqxtValue = bqxtValue;
    }
    
    @Column(name="bqxt_score", precision=22, scale=0)

    public Double getBqxtScore() {
        return this.bqxtScore;
    }
    
    public void setBqxtScore(Double bqxtScore) {
        this.bqxtScore = bqxtScore;
    }
    
    @Column(name="bqmq_value", length=9)

    public String getBqmqValue() {
        return this.bqmqValue;
    }
    
    public void setBqmqValue(String bqmqValue) {
        this.bqmqValue = bqmqValue;
    }
    
    @Column(name="bqmq_score", precision=22, scale=0)

    public Double getBqmqScore() {
        return this.bqmqScore;
    }
    
    public void setBqmqScore(Double bqmqScore) {
        this.bqmqScore = bqmqScore;
    }
    
    @Column(name="qyprint_value", length=9)

    public String getQyprintValue() {
        return this.qyprintValue;
    }
    
    public void setQyprintValue(String qyprintValue) {
        this.qyprintValue = qyprintValue;
    }
    
    @Column(name="qyprint_score", precision=22, scale=0)
    public Double getQyprintScore() {
        return this.qyprintScore;
    }
    
    public void setQyprintScore(Double qyprintScore) {
        this.qyprintScore = qyprintScore;
    }
    
    @Column(name="checkdeal_value")
    public String getCheckdealValue() {
		return checkdealValue;
	}
	public void setCheckdealValue(String checkdealValue) {
		this.checkdealValue = checkdealValue;
	}
	@Column(name="checkdeal_score", precision=22, scale=0)
	public Double getCheckdealScore() {
		return checkdealScore;
	}
	public void setCheckdealScore(Double checkdealScore) {
		this.checkdealScore = checkdealScore;
	}
	@Column(name="importantjob_total_score", precision=22, scale=0)
    public Double getImportantjobTotalScore() {
        return this.importantjobTotalScore;
    }
    
    public void setImportantjobTotalScore(Double importantjobTotalScore) {
        this.importantjobTotalScore = importantjobTotalScore;
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
	
	@Column(name="lp7dnum_value")
	public String getLp7dnumValue() {
		return lp7dnumValue;
	}
	public void setLp7dnumValue(String lp7dnumValue) {
		this.lp7dnumValue = lp7dnumValue;
	}
	
	@Column(name="lp7dnum_score")
	public Double getLp7dnumScore() {
		return lp7dnumScore;
	}
	public void setLp7dnumScore(Double lp7dnumScore) {
		this.lp7dnumScore = lp7dnumScore;
	}
	
	@Column(name="lpcall_value")
	public String getLpcallValue() {
		return lpcallValue;
	}
	public void setLpcallValue(String lpcallValue) {
		this.lpcallValue = lpcallValue;
	}
	
	@Column(name="lpcall_score")
	public Double getLpcallScore() {
		return lpcallScore;
	}
	public void setLpcallScore(Double lpcallScore) {
		this.lpcallScore = lpcallScore;
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
	
	@Column(name="dasubmit_value")
	public String getDasubmitValue() {
		return dasubmitValue;
	}
	public void setDasubmitValue(String dasubmitValue) {
		this.dasubmitValue = dasubmitValue;
	}
	
	@Column(name="dasubmit_score")
	public Double getDasubmitScore() {
		return dasubmitScore;
	}
	public void setDasubmitScore(Double dasubmitScore) {
		this.dasubmitScore = dasubmitScore;
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
	
	@Column(name="bqmqsx_value")
	public String getBqmqsxValue() {
		return bqmqsxValue;
	}
	public void setBqmqsxValue(String bqmqsxValue) {
		this.bqmqsxValue = bqmqsxValue;
	}
	
	@Column(name="bqmqsx_score")
	public Double getBqmqsxScore() {
		return bqmqsxScore;
	}
	public void setBqmqsxScore(Double bqmqsxScore) {
		this.bqmqsxScore = bqmqsxScore;
	}
	
	@Column(name="wxcall_value")
	public String getWxcallValue() {
		return wxcallValue;
	}
	public void setWxcallValue(String wxcallValue) {
		this.wxcallValue = wxcallValue;
	}
	
	@Column(name="wxcall_score")
	public Double getWxcallScore() {
		return wxcallScore;
	}
	public void setWxcallScore(Double wxcallScore) {
		this.wxcallScore = wxcallScore;
	}
    
}