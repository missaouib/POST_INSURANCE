package com.gdpost.web.entity.insurance;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.JoinColumnOrFormula;
import org.hibernate.annotations.JoinColumnsOrFormulas;
import org.hibernate.annotations.JoinFormula;

import com.gdpost.web.entity.Idable;


/**
 * TRenewedFollow entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="t_renewed_follow")
public class RenewedFollow  implements Idable<Long>,java.io.Serializable {


    // Fields    

     /**
	 * 
	 */
	private static final long serialVersionUID = 8714070396177623654L;
	private Long id;
	private Policy policy;
     private String job;
     private String company;
     private Double income;
     private Double homeIncome;
     private Date objectives;
     private Date bankInfo;
     private Date reporteDate;
     private Date followDate;
     private String status;
     private String remark;
     private Date operateTime;


     @Transient
     private String followStatus;
     
    // Constructors
     @Transient
    public String getFollowStatus() {
		return followStatus;
	}
     @Transient
	public void setFollowStatus(String followStatus) {
		this.followStatus = followStatus;
	}

	/** default constructor */
    public RenewedFollow() {
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
    
    @ManyToOne(cascade = CascadeType.DETACH, targetEntity = Policy.class)
	@JoinColumnsOrFormulas(value={
	@JoinColumnOrFormula(column=@JoinColumn(name ="policy_no", referencedColumnName ="policy_no", insertable =false, updatable = false)),
	@JoinColumnOrFormula(formula=@JoinFormula(value="0", referencedColumnName = "attached_flag"))
	})
	public Policy getPolicy() {
		return policy;
	}

	public void setPolicy(Policy policy) {
		this.policy = policy;
	}
    
    @Column(name="job", length=24)

    public String getJob() {
        return this.job;
    }
    
    public void setJob(String job) {
        this.job = job;
    }
    
    @Column(name="company", length=24)

    public String getCompany() {
        return this.company;
    }
    
    public void setCompany(String company) {
        this.company = company;
    }
    
    @Column(name="income", precision=22, scale=0)

    public Double getIncome() {
        return this.income;
    }
    
    public void setIncome(Double income) {
        this.income = income;
    }
    
    @Column(name="home_income", precision=22, scale=0)

    public Double getHomeIncome() {
        return this.homeIncome;
    }
    
    public void setHomeIncome(Double homeIncome) {
        this.homeIncome = homeIncome;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="objectives", length=10)

    public Date getObjectives() {
        return this.objectives;
    }
    
    public void setObjectives(Date objectives) {
        this.objectives = objectives;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="bank_info", length=10)

    public Date getBankInfo() {
        return this.bankInfo;
    }
    
    public void setBankInfo(Date bankInfo) {
        this.bankInfo = bankInfo;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="reporte_date", length=10)

    public Date getReporteDate() {
        return this.reporteDate;
    }
    
    public void setReporteDate(Date reporteDate) {
        this.reporteDate = reporteDate;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="follow_date", length=10)

    public Date getFollowDate() {
        return this.followDate;
    }
    
    public void setFollowDate(Date followDate) {
        this.followDate = followDate;
    }
    
    @Column(name="status", length=9)

    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    @Column(name="remark", length=119)

    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    @Column(name="operate_time", length=26)

    public Date getOperateTime() {
        return this.operateTime;
    }
    
    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }
   








}