package com.gdpost.web.service.impl.component;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdpost.web.dao.StasticsAreaDAO;
import com.gdpost.web.dao.StasticsCityDAO;
import com.gdpost.web.dao.model.CheckDtlDAO;
import com.gdpost.web.dao.model.CheckStasticsDAO;
import com.gdpost.web.dao.model.PolicyStatDAO;
import com.gdpost.web.dao.model.QyStatDAO;
import com.gdpost.web.dao.model.StaffDtlModelDAO;
import com.gdpost.web.dao.model.StaffModelDAO;
import com.gdpost.web.dao.model.TuiBaoDtlModelDAO;
import com.gdpost.web.dao.model.TuiBaoModelDAO;
import com.gdpost.web.dao.model.UwDtlModelDAO;
import com.gdpost.web.dao.model.UwModelDAO;
import com.gdpost.web.entity.component.CheckModel;
import com.gdpost.web.entity.component.PolicyStatModel;
import com.gdpost.web.entity.component.QyCheckModel;
import com.gdpost.web.entity.component.QyStatModel;
import com.gdpost.web.entity.component.StaffDtlModel;
import com.gdpost.web.entity.component.StaffModel;
import com.gdpost.web.entity.component.TuiBaoDtlModel;
import com.gdpost.web.entity.component.TuiBaoModel;
import com.gdpost.web.entity.component.UwDtlModel;
import com.gdpost.web.entity.component.UwModel;
import com.gdpost.web.entity.insurance.StasticsArea;
import com.gdpost.web.entity.insurance.StasticsCity;
import com.gdpost.web.service.component.StasticsService;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.dwz.PageUtils;

@Service
@Transactional
public class StasticsServiceImpl implements StasticsService {
	//private static final Logger logger = LoggerFactory.getLogger(QyglServiceImpl.class);
	
	@Autowired
	private TuiBaoModelDAO cmDAO;
	
	@Autowired
	private TuiBaoDtlModelDAO tbdDAO;
	
	@Autowired
	private StaffModelDAO smDAO;
	
	@Autowired
	private StaffDtlModelDAO sdDAO;
	
	@Autowired
	private UwModelDAO umDAO;
	
	@Autowired
	private UwDtlModelDAO udDAO;
	
	@Autowired
	private PolicyStatDAO policyDAO;
	
	@Autowired
	private QyStatDAO qyStatDAO;
	
	@Autowired
	private CheckStasticsDAO checkDAO;
	
	@Autowired
	private CheckDtlDAO checDtlkDAO;

	@Autowired
	private StasticsCityDAO cityStatDAO;

	@Autowired
	private StasticsAreaDAO areaStatDAO;

	@Override
	public List<TuiBaoModel> getTuiBaoWarnningWithPolicyDateAndCsDateNoBankCode(String organCode, String d1, String d2, String d3, String d4, String prdCode, String toPerm, String staffFlag, Integer duration) {
		return cmDAO.getTuiBaoWarningWithPolicyDateAndCsDateNoBankCode(organCode, d1, d2, d3, d4, prdCode, toPerm, staffFlag, duration);
	}
	
	@Override
	public List<TuiBaoModel> getTuiBaoWarnningWithPolicyDateAndCsDate(String organCode, String d1, String d2, String d3, String d4, String flag, String prdCode, String toPerm, String staffFlag, Integer duration) {
		return cmDAO.getTuiBaoWarningWithPolicyDateAndCsDate(organCode, d1, d2, d3, d4, flag, prdCode, toPerm, staffFlag, duration);
	}
	
	@Override
	public List<TuiBaoModel> getNetTuiBaoWarnningWithPolicyDateAndCsDate(String organCode, String d1, String d2, String d3, String d4, String prdCode, String toPerm, String staffFlag, String bankNaem, Integer duration) {
		return cmDAO.getNetTuiBaoWarningWithPolicyDateAndCsDate(organCode, d1, d2, d3, d4, prdCode, toPerm, staffFlag, bankNaem, duration);
	}
	
	@Override
	public List<TuiBaoModel> getNetTuiBaoWarnningWithPolicyDateAndCsDate(String organCode, String d1, String d2, String d3, String d4, String flag, String prdCode, String toPerm, String staffFlag, String bankNaem, Integer duration) {
		return cmDAO.getNetTuiBaoWarningWithPolicyDateAndCsDate(organCode, d1, d2, d3, d4, flag, prdCode, toPerm, staffFlag, bankNaem, duration);
	}
	
	@Override
	public List<TuiBaoModel> getProvTuiBaoWarnningWithPolicyDateAndCsDateNoBankCode(String organCode, String d1, String d2, String d3, String d4, String prdCode, String toPerm, String staffFlag, Integer duration) {
		return cmDAO.getProvAllCityTuiBaoWarning(organCode, d1, d2, d3, d4, prdCode, toPerm, staffFlag, duration);
	}
	
	@Override
	public List<TuiBaoModel> getProvTuiBaoWarnningWithPolicyDateAndCsDate(String organCode, String d1, String d2, String d3, String d4, String flag, String prdCode, String toPerm, String staffFlag, Integer duration) {
		return cmDAO.getProvAllCityTuiBaoWarningWithBankCode(organCode, d1, d2, d3, d4, flag, prdCode, toPerm, staffFlag, duration);
	}
	
	@Override
	public List<TuiBaoDtlModel> getTuiBaoWarnningDetailWithBankCode(String organCode, String d1, String d2, String d3, String d4, String flag, String prdCode, String toPerm, String staffFlag, String bankName, Integer duration) {
		return tbdDAO.getAllTuiBaoWarningDetailWithBankCode(organCode, d1, d2, d3, d4, flag, prdCode, toPerm, staffFlag, bankName, duration);
	}
	
	@Override
	public List<TuiBaoDtlModel> getTuiBaoCsDetailWithBankCode(String organCode, String d1, String d2, String d3, String d4, String flag, String prdCode, String toPerm, String staffFlag, String bankName, Integer duration) {
		return tbdDAO.getAllTuiBaoCsDetailWithBankCode(organCode, d1, d2, d3, d4, flag, prdCode, toPerm, staffFlag, bankName, duration);
	}
	
	/*
	 * =========================================
	 * staff
	 * =========================================
	 */
	@Override
	public List<StaffModel> getStaffCountWithPolicyDateNoBankCode(String organCode, String d1, String d2, String prdCode, String toPerm, Integer duration) {
		return smDAO.getStaffCountWithPolicyDateNoBankCode(organCode, d1, d2, prdCode, toPerm, duration);
	}
	
	@Override
	public List<StaffModel> getStaffCountWithPolicyDate(String organCode, String d1, String d2, String flag, String prdCode, String toPerm, Integer duration) {
		return smDAO.getStaffCountWithPolicyDate(organCode, d1, d2, flag, prdCode, toPerm, duration);
	}
	
	@Override
	public List<StaffModel> getProvStaffCountWithPolicyDateNoBankCode(String organCode, String d1, String d2, String prdCode, String toPerm, Integer duration) {
		return smDAO.getProvStaffCountWithPolicyDateNoBankCode(organCode, d1, d2, prdCode, toPerm, duration);
	}
	
	@Override
	public List<StaffModel> getProvStaffCountWithPolicyDate(String organCode, String d1, String d2, String flag, String prdCode, String toPerm, Integer duration) {
		return smDAO.getProvStaffCountWithPolicyDate(organCode, d1, d2, flag, prdCode, toPerm, duration);
	}

	@Override
	public List<StaffDtlModel> getStaffDetailWithPolicyDate(String organCode, String d1, String d2, String flag, String prdCode, String toPerm, Integer duration) {
		return sdDAO.getProvAllStaffDetailWithBankCode(organCode, d1, d2, flag, prdCode, toPerm, duration);
	}

	/*
	 * =========================================
	 * under write
	 * =========================================
	 */
	@Override
	public List<UwModel> getProvUwStastics(String organCode, String d1, String d2) {
		return umDAO.getProvUwStastic(organCode, d1, d2);
	}

	@Override
	public List<UwModel> getCityUwStastics(String organCode, String d1, String d2) {
		return umDAO.getCityUwStastic(organCode, d1, d2);
	}
	
	@Override
	public List<UwModel> getNetUwStastics(String organCode, String d1, String d2) {
		return umDAO.getNetUwStastic(organCode, d1, d2);
	}

	@Override
	public List<UwDtlModel> getUwDtlStastics(String organCode, String d1, String d2) {
		return udDAO.getUwDtlStastic(organCode, d1, d2);
	}
	
	@Override
	public List<UwModel> getProvLongUwStastics(String organCode, String d1, String d2) {
		return umDAO.getProvLongUwStastic(organCode, d1, d2);
	}

	@Override
	public List<UwModel> getCityLongUwStastics(String organCode, String d1, String d2) {
		return umDAO.getCityLongUwStastic(organCode, d1, d2);
	}
	
	@Override
	public List<UwModel> getNetLongUwStastics(String organCode, String d1, String d2) {
		return umDAO.getNetLongUwStastic(organCode, d1, d2);
	}

	@Override
	public List<UwDtlModel> getLongUwDtlStastics(String organCode, String d1, String d2) {
		return udDAO.getLongUwDtlStastic(organCode, d1, d2);
	}
	
	/*
	 * =================
	 * policy data
	 * =================
	 */
	@Override
	public List<PolicyStatModel> getPolicyProdStastics(String organCode, String d1, String d2, String prdCode,
			String toPerm, String staffFlag, String csFlag, String saleType, String status, Integer duration, Integer policyFee) {
		return policyDAO.getPolicyDateProdStat(organCode, d1, d2, prdCode, toPerm, staffFlag, csFlag, saleType, status, duration, policyFee);
	}

	@Override
	public List<PolicyStatModel> getPolicyProdStasticsWithBankCode(String organCode, String d1, String d2, String flag,
			String prdCode, String toPerm, String staffFlag, String bankName, String csFlag, String saleType, String status, Integer duration, Integer policyFee) {
		return policyDAO.getPolicyDateProdStatWithBankCode(organCode, d1, d2, flag, prdCode, toPerm, staffFlag, bankName, csFlag, saleType, status, duration, policyFee);
	}

	@Override
	public List<PolicyStatModel> getProvPolicyOrganStastics(String organCode, String d1, String d2, String prdCode,
			String toPerm, String staffFlag, String csFlag, String saleType, String status, Integer duration, Integer policyFee) {
		return policyDAO.getProvPolicyDateOrganStat(organCode, d1, d2, prdCode, toPerm, staffFlag, csFlag, saleType, status, duration, policyFee);
	}
	
	@Override
	public List<PolicyStatModel> getPolicyOrganStastics(String organCode, String d1, String d2, String prdCode,
			String toPerm, String staffFlag, String csFlag, String saleType, String status, Integer duration, Integer policyFee) {
		return policyDAO.getPolicyDateOrganStat(organCode, d1, d2, prdCode, toPerm, staffFlag, csFlag, saleType, status, duration, policyFee);
	}
	
	@Override
	public List<PolicyStatModel> getPolicyOrganNetStastics(String organCode, String d1, String d2, String prdCode,
			String toPerm, String staffFlag, String bankName, String csFlag, String saleType, String status, Integer duration, Integer policyFee) {
		return policyDAO.getPolicyDateOrganNetStat(organCode, d1, d2, prdCode, toPerm, staffFlag, bankName, csFlag, saleType, status, duration, policyFee);
	}

	@Override
	public List<PolicyStatModel> getProvPolicyOrganStasticsWithBankCode(String organCode, String d1, String d2, String flag,
			String prdCode, String toPerm, String staffFlag, String bankName, String csFlag, String saleType, String status, Integer duration, Integer policyFee) {
		return policyDAO.getProvPolicyDateOrganStatWithBankCode(organCode, d1, d2, flag, prdCode, toPerm, staffFlag, bankName, csFlag, saleType, status, duration, policyFee);
	}
	
	@Override
	public List<PolicyStatModel> getPolicyOrganStasticsWithBankCode(String organCode, String d1, String d2, String flag,
			String prdCode, String toPerm, String staffFlag, String bankName, String csFlag, String saleType, String status, Integer duration, Integer policyFee) {
		return policyDAO.getPolicyDateOrganStatWithBankCode(organCode, d1, d2, flag, prdCode, toPerm, staffFlag, bankName, csFlag, saleType, status, duration, policyFee);
	}
	
	@Override
	public List<PolicyStatModel> getPolicyOrganNetStasticsWithBankCode(String organCode, String d1, String d2, String flag,
			String prdCode, String toPerm, String staffFlag, String bankName, String csFlag, String saleType, String status, Integer duration, Integer policyFee) {
		return policyDAO.getPolicyDateOrganNetStatWithBankCode(organCode, d1, d2, flag, prdCode, toPerm, staffFlag, bankName, csFlag, saleType, status, duration, policyFee);
	}

	@Override
	public List<PolicyStatModel> getPolicyFeeFrequencyStastics(String organCode, String d1, String d2, String prdCode,
			String toPerm, String staffFlag, String csFlag, String saleType, String status, Integer duration, Integer policyFee) {
		return policyDAO.getPolicyDateFeeTypeStat(organCode, d1, d2, prdCode, toPerm, staffFlag, csFlag, saleType, status, duration, policyFee);
	}

	@Override
	public List<PolicyStatModel> getPolicyFeeFrequencyStasticsWithBankCode(String organCode, String d1, String d2,
			String flag, String prdCode, String toPerm, String staffFlag, String bankName, String csFlag, String saleType, String status, Integer duration, Integer policyFee) {
		return policyDAO.getPolicyDateFeeTypeStatWithBankCode(organCode, d1, d2, flag, prdCode, toPerm, staffFlag, bankName, csFlag, saleType, status, duration, policyFee);
	}

	/*
	 * =========================================
	 * Check Write , Check Record statsics
	 * =========================================
	 */
	@Override
	public List<QyCheckModel> getCheckWriteCityStastics(String d1, String d2, Integer duration, String toPerm) {
		return checkDAO.getCheckWriteCityStat(d1, d2, duration, toPerm);
	}

	@Override
	public List<QyCheckModel> getCheckRecordCityStastics(String d1, String d2, Integer duration, String toPerm) {
		return checkDAO.getCheckRecordCityStat(d1, d2, duration, toPerm);
	}

	@Override
	public List<QyCheckModel> getCheckWriteAreaStastics(String organCode, String d1, String d2, Integer duration, String toPerm) {
		return checkDAO.getCheckWriteAreaStat(organCode, d1, d2, duration, toPerm);
	}

	@Override
	public List<QyCheckModel> getCheckRecordAreaStastics(String organCode, String d1, String d2, Integer duration, String toPerm) {
		return checkDAO.getCheckRecordAreaStat(organCode, d1, d2, duration, toPerm);
	}

	@Override
	public List<QyCheckModel> getCheckTruthCityStastics(String d1, String d2, Integer duration, String toPerm, String netFlag) {
		return checkDAO.getCheckTruthCityStat(d1, d2, duration, toPerm, netFlag);
	}

	@Override
	public List<QyCheckModel> getCheckTruthAreaStastics(String organCode, String d1, String d2, Integer duration, String toPerm, String netFlag) {
		return checkDAO.getCheckTruthAreaStat(organCode, d1, d2, duration, toPerm, netFlag);
	}

	@Override
	public List<QyCheckModel> getMultipleCheckTruthCityStastics(String d1, String d2, Integer duration, String toPerm, String netFlag) {
		return checkDAO.getMultipleCheckTruthCityStat(d1, d2, duration, toPerm, netFlag);
	}

	@Override
	public List<QyCheckModel> getMultipleCheckTruthAreaStastics(String organCode, String d1, String d2, Integer duration, String toPerm, String netFlag) {
		return checkDAO.getMultipleCheckTruthAreaStat(organCode, d1, d2, duration, toPerm, netFlag);
	}

	@Override
	public List<QyCheckModel> getPrintCityStastics(String d1, String d2) {
		return checkDAO.getPrintCityStat(d1, d2);
	}

	@Override
	public List<QyCheckModel> getPrintAreaStastics(String organCode, String d1, String d2) {
		return checkDAO.getPrintAreaStat(organCode, d1, d2);
	}

	@Override
	public List<CheckModel> getCheckWritetasticsDtl(String orgCode, String d1, String d2, Integer duration, String toPerm) {
		return checDtlkDAO.getCheckModelWriteStatDtl(orgCode, d1, d2, duration, toPerm);
	}

	@Override
	public List<CheckModel> getCheckRecordtasticsDtl(String orgCode, String d1, String d2, Integer duration, String toPerm) {
		return checDtlkDAO.getCheckModelRecordStatDtl(orgCode, d1, d2, duration, toPerm);
	}

	@Override
	public List<CheckModel> getCheckTruthStasticsDtl(String organCode, String d1, String d2, Integer duration, String toPerm, String netFlag) {
		return checDtlkDAO.getCheckModelTruthStatDtl(organCode, d1, d2, duration, toPerm, netFlag);
	}
	
	@Override
	public List<StasticsCity> findByCityStatByExample(
			Specification<StasticsCity> specification, Page page) {
		org.springframework.data.domain.Page<StasticsCity> springDataPage = cityStatDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	@Override
	public List<StasticsArea> findByAreaStatByExample(
			Specification<StasticsArea> specification, Page page) {
		org.springframework.data.domain.Page<StasticsArea> springDataPage = areaStatDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	@Override
	public List<QyCheckModel> getStatusCheckWriteCityStastics(String d1, String d2, String fixStatus, String netFlag) {
		return checkDAO.getStatusCheckWriteCityStat(d1, d2, fixStatus, netFlag);
	}
	
	@Override
	public List<QyCheckModel> getStatusCheckWriteAreaStastics(String organCode, String d1, String d2, String fixStatus, String netFlag) {
		return checkDAO.getStatusCheckWriteAreaStat(organCode, d1, d2, fixStatus, netFlag);
	}
	
	@Override
	public List<QyCheckModel> getHfCityStastics(String d1, String d2, String netFlag) {
		return checkDAO.getHfCityStat(d1, d2, netFlag);
	}
	
	@Override
	public List<QyCheckModel> getHfAreaStastics(String organCode, String d1, String d2, String netFlag) {
		return checkDAO.getHfAreaStat(organCode, d1, d2, netFlag);
	}

	@Override
	public List<TuiBaoModel> getAGWarnningWithPolicyDateAndCsDateNoBankCode(String organCode, String d1, String d2, String e1, String e2, String c1, String c2, String prdCode, String toPerm, String staffFlag, Integer duration) {
		return cmDAO.getAGWarningWithPolicyDateAndCsDateNoBankCode(organCode, d1, d2, e1, e2, c1, c2, prdCode, toPerm, staffFlag, duration);
	}

	@Override
	public List<TuiBaoModel> getAGWarnningWithPolicyDateAndCsDate(String organCode, String d1, String d2, String e1, String e2, String c1, String c2, String flag, String prdCode, String toPerm, String staffFlag, Integer duration) {
		return cmDAO.getAGWarningWithPolicyDateAndCsDate(organCode, d1, d2, e1, e2, c1, c2, flag, prdCode, toPerm, staffFlag, duration);
	}

	@Override
	public List<TuiBaoModel> getNetAGWarnningWithPolicyDateAndCsDate(String organCode, String d1, String d2, String e1, String e2, String c1, String c2, String prdCode, String toPerm, String staffFlag, String bankNaem, Integer duration) {
		return cmDAO.getNetAGWarningWithPolicyDateAndCsDate(organCode, d1, d2, e1, e2, c1, c2, prdCode, toPerm, staffFlag, bankNaem, duration);
	}

	@Override
	public List<TuiBaoModel> getNetAGWarnningWithPolicyDateAndCsDate(String organCode, String d1, String d2, String e1, String e2, String c1, String c2, String flag, String prdCode, String toPerm, String staffFlag, String bankNaem, Integer duration) {
		return cmDAO.getNetAGWarningWithPolicyDateAndCsDate(organCode, d1, d2, e1, e2, c1, c2, flag, prdCode, toPerm, staffFlag, bankNaem, duration);
	}

	@Override
	public List<TuiBaoModel> getProvAGWarnningWithPolicyDateAndCsDateNoBankCode(String organCode, String d1, String d2, String e1, String e2, String c1, String c2, String prdCode, String toPerm, String staffFlag, Integer duration) {
		return cmDAO.getProvAllCityAGWarning(organCode, d1, d2, e1, e2, c1, c2, prdCode, toPerm, staffFlag, duration);
	}

	@Override
	public List<TuiBaoModel> getProvAGWarnningWithPolicyDateAndCsDate(String organCode, String d1, String d2, String e1, String e2, String c1, String c2, String flag, String prdCode, String toPerm, String staffFlag, Integer duration) {
		return cmDAO.getProvAllCityAGWarningWithBankCode(organCode, d1, d2, e1, e2, c1, c2, flag, prdCode, toPerm, staffFlag, duration);
	}

	
	//QYHB
	@Override
	public List<QyStatModel> getQyStatHbPrdRatio(String organCode, String d1, String d2, String prdCode, String toPerm,
			String staffFlag, String csFlag, String saleType, String status, Integer duration) {
		return qyStatDAO.getQyStatHbPrdRatio(organCode, d1, d2, prdCode, toPerm, staffFlag, csFlag, saleType, status, duration);
	}

	@Override
	public List<QyStatModel> getQYHBProdStatWithBankCode(String organCode, String d1, String d2, String flag,
			String prdCode, String toPerm, String staffFlag, String bankName, String csFlag, String saleType,
			String status, Integer duration) {
		return qyStatDAO.getQYHBProdStatWithBankCode(organCode, d1, d2, flag, prdCode, toPerm, staffFlag, bankName, csFlag, saleType, status, duration);
	}

	@Override
	public List<QyStatModel> getProvQYHBOrganStat(String organCode, String d1, String d2, String prdCode, String toPerm,
			String staffFlag, String csFlag, String saleType, String status, Integer duration) {
		return qyStatDAO.getProvQYHBOrganStat(organCode, d1, d2, prdCode, toPerm, staffFlag, csFlag, saleType, status, duration);
	}

	@Override
	public List<QyStatModel> getQYHBOrganStat(String organCode, String d1, String d2, String prdCode, String toPerm,
			String staffFlag, String csFlag, String saleType, String status, Integer duration) {
		return qyStatDAO.getQYHBOrganStat(organCode, d1, d2, prdCode, toPerm, staffFlag, csFlag, saleType, status, duration);
	}

	@Override
	public List<QyStatModel> getQYHBOrganNetStat(String organCode, String d1, String d2, String prdCode, String toPerm,
			String staffFlag, String bankName, String csFlag, String saleType, String status, Integer duration) {
		return qyStatDAO.getQYHBOrganNetStat(organCode, d1, d2, prdCode, toPerm, staffFlag, bankName, csFlag, saleType, status, duration);
	}

	@Override
	public List<QyStatModel> getProvQYHBOrganStatWithBankCode(String organCode, String d1, String d2, String flag,
			String prdCode, String toPerm, String staffFlag, String bankName, String csFlag, String saleType,
			String status, Integer duration) {
		return qyStatDAO.getProvQYHBOrganStatWithBankCode(organCode, d1, d2, flag, prdCode, toPerm, staffFlag, bankName, csFlag, saleType, status, duration);
	}

	@Override
	public List<QyStatModel> getQYHBOrganStatWithBankCode(String organCode, String d1, String d2, String flag,
			String prdCode, String toPerm, String staffFlag, String bankName, String csFlag, String saleType,
			String status, Integer duration) {
		return qyStatDAO.getQYHBOrganStatWithBankCode(organCode, d1, d2, flag, prdCode, toPerm, staffFlag, bankName, csFlag, saleType, status, duration);
	}

	@Override
	public List<QyStatModel> getQYHBOrganNetStatWithBankCode(String organCode, String d1, String d2, String flag,
			String prdCode, String toPerm, String staffFlag, String bankName, String csFlag, String saleType,
			String status, Integer duration) {
		return qyStatDAO.getQYHBOrganNetStatWithBankCode(organCode, d1, d2, flag, prdCode, toPerm, staffFlag, bankName, csFlag, saleType, status, duration);
	}

	@Override
	public List<QyStatModel> getQYHBFeeTypeStat(String organCode, String d1, String d2, String prdCode, String toPerm,
			String staffFlag, String csFlag, String saleType, String status, Integer duration) {
		return qyStatDAO.getQYHBFeeTypeStat(organCode, d1, d2, prdCode, toPerm, staffFlag, csFlag, saleType, status, duration);
	}

	@Override
	public List<QyStatModel> getQYHBFeeTypeStatWithBankCode(String organCode, String d1, String d2, String flag, String prdCode,
			String toPerm, String staffFlag, String bankName, String csFlag, String saleType, String status,
			Integer duration) {
		return qyStatDAO.getQYHBFeeTypeStatWithBankCode(organCode, d1, d2, flag, prdCode, toPerm, staffFlag, bankName, csFlag, saleType, status, duration);
	}
	
}
