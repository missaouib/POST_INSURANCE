package com.gdpost.web.service.impl.component;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdpost.web.dao.component.PolicyDataDAO;
import com.gdpost.web.dao.component.StaffDtlModelDAO;
import com.gdpost.web.dao.component.StaffModelDAO;
import com.gdpost.web.dao.component.TuiBaoDtlModelDAO;
import com.gdpost.web.dao.component.TuiBaoModelDAO;
import com.gdpost.web.dao.component.UwDtlModelDAO;
import com.gdpost.web.dao.component.UwModelDAO;
import com.gdpost.web.entity.component.PolicyStatModel;
import com.gdpost.web.entity.component.StaffDtlModel;
import com.gdpost.web.entity.component.StaffModel;
import com.gdpost.web.entity.component.TuiBaoDtlModel;
import com.gdpost.web.entity.component.TuiBaoModel;
import com.gdpost.web.entity.component.UwDtlModel;
import com.gdpost.web.entity.component.UwModel;
import com.gdpost.web.service.component.StasticsService;

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
	private PolicyDataDAO policyDAO;

	@Override
	public List<TuiBaoModel> getTuiBaoWarnningWithPolicyDateAndCsDateNoBankCode(String organCode, String d1, String d2, String d3, String d4, String prdCode, String toPerm, String staffFlag) {
		return cmDAO.getTuiBaoWarningWithPolicyDateAndCsDateNoBankCode(organCode, d1, d2, d3, d4, prdCode, toPerm, staffFlag);
	}
	
	@Override
	public List<TuiBaoModel> getTuiBaoWarnningWithPolicyDateAndCsDate(String organCode, String d1, String d2, String d3, String d4, String flag, String prdCode, String toPerm, String staffFlag) {
		return cmDAO.getTuiBaoWarningWithPolicyDateAndCsDate(organCode, d1, d2, d3, d4, flag, prdCode, toPerm, staffFlag);
	}
	
	@Override
	public List<TuiBaoModel> getNetTuiBaoWarnningWithPolicyDateAndCsDate(String organCode, String d1, String d2, String d3, String d4, String prdCode, String toPerm, String staffFlag, String bankNaem) {
		return cmDAO.getNetTuiBaoWarningWithPolicyDateAndCsDate(organCode, d1, d2, d3, d4, prdCode, toPerm, staffFlag, bankNaem);
	}
	
	@Override
	public List<TuiBaoModel> getNetTuiBaoWarnningWithPolicyDateAndCsDate(String organCode, String d1, String d2, String d3, String d4, String flag, String prdCode, String toPerm, String staffFlag, String bankNaem) {
		return cmDAO.getNetTuiBaoWarningWithPolicyDateAndCsDate(organCode, d1, d2, d3, d4, flag, prdCode, toPerm, staffFlag, bankNaem);
	}
	
	@Override
	public List<TuiBaoModel> getProvTuiBaoWarnningWithPolicyDateAndCsDateNoBankCode(String organCode, String d1, String d2, String d3, String d4, String prdCode, String toPerm, String staffFlag) {
		return cmDAO.getProvAllCityTuiBaoWarning(organCode, d1, d2, d3, d4, prdCode, toPerm, staffFlag);
	}
	
	@Override
	public List<TuiBaoModel> getProvTuiBaoWarnningWithPolicyDateAndCsDate(String organCode, String d1, String d2, String d3, String d4, String flag, String prdCode, String toPerm, String staffFlag) {
		return cmDAO.getProvAllCityTuiBaoWarningWithBankCode(organCode, d1, d2, d3, d4, flag, prdCode, toPerm, staffFlag);
	}
	
	@Override
	public List<TuiBaoDtlModel> getTuiBaoWarnningDetailWithBankCode(String organCode, String d1, String d2, String d3, String d4, String flag, String prdCode, String toPerm, String staffFlag, String bankName) {
		return tbdDAO.getAllTuiBaoWarningDetailWithBankCode(organCode, d1, d2, d3, d4, flag, prdCode, toPerm, staffFlag, bankName);
	}
	
	/*
	 * =========================================
	 * staff
	 * =========================================
	 */
	@Override
	public List<StaffModel> getStaffCountWithPolicyDateNoBankCode(String organCode, String d1, String d2, String prdCode, String toPerm) {
		return smDAO.getStaffCountWithPolicyDateNoBankCode(organCode, d1, d2, prdCode, toPerm);
	}
	
	@Override
	public List<StaffModel> getStaffCountWithPolicyDate(String organCode, String d1, String d2, String flag, String prdCode, String toPerm) {
		return smDAO.getStaffCountWithPolicyDate(organCode, d1, d2, flag, prdCode, toPerm);
	}
	
	@Override
	public List<StaffModel> getProvStaffCountWithPolicyDateNoBankCode(String organCode, String d1, String d2, String prdCode, String toPerm) {
		return smDAO.getProvStaffCountWithPolicyDateNoBankCode(organCode, d1, d2, prdCode, toPerm);
	}
	
	@Override
	public List<StaffModel> getProvStaffCountWithPolicyDate(String organCode, String d1, String d2, String flag, String prdCode, String toPerm) {
		return smDAO.getProvStaffCountWithPolicyDate(organCode, d1, d2, flag, prdCode, toPerm);
	}

	@Override
	public List<StaffDtlModel> getStaffDetailWithPolicyDate(String organCode, String d1, String d2, String flag, String prdCode, String toPerm) {
		return sdDAO.getProvAllStaffDetailWithBankCode(organCode, d1, d2, flag, prdCode, toPerm);
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
			String toPerm, String staffFlag) {
		return policyDAO.getPolicyDateProdStat(organCode, d1, d2, prdCode, toPerm, staffFlag);
	}

	@Override
	public List<PolicyStatModel> getPolicyProdStasticsWithBankCode(String organCode, String d1, String d2, String flag,
			String prdCode, String toPerm, String staffFlag, String bankName) {
		return policyDAO.getPolicyDateProdStatWithBankCode(organCode, d1, d2, flag, prdCode, toPerm, staffFlag, bankName);
	}

	@Override
	public List<PolicyStatModel> getProvPolicyOrganStastics(String organCode, String d1, String d2, String prdCode,
			String toPerm, String staffFlag) {
		return policyDAO.getProvPolicyDateOrganStat(organCode, d1, d2, prdCode, toPerm, staffFlag);
	}
	
	@Override
	public List<PolicyStatModel> getPolicyOrganStastics(String organCode, String d1, String d2, String prdCode,
			String toPerm, String staffFlag) {
		return policyDAO.getPolicyDateOrganStat(organCode, d1, d2, prdCode, toPerm, staffFlag);
	}
	
	@Override
	public List<PolicyStatModel> getPolicyOrganNetStastics(String organCode, String d1, String d2, String prdCode,
			String toPerm, String staffFlag, String bankName) {
		return policyDAO.getPolicyDateOrganNetStat(organCode, d1, d2, prdCode, toPerm, staffFlag, bankName);
	}

	@Override
	public List<PolicyStatModel> getProvPolicyOrganStasticsWithBankCode(String organCode, String d1, String d2, String flag,
			String prdCode, String toPerm, String staffFlag, String bankName) {
		return policyDAO.getProvPolicyDateOrganStatWithBankCode(organCode, d1, d2, flag, prdCode, toPerm, staffFlag, bankName);
	}
	
	@Override
	public List<PolicyStatModel> getPolicyOrganStasticsWithBankCode(String organCode, String d1, String d2, String flag,
			String prdCode, String toPerm, String staffFlag, String bankName) {
		return policyDAO.getPolicyDateOrganStatWithBankCode(organCode, d1, d2, flag, prdCode, toPerm, staffFlag, bankName);
	}
	
	@Override
	public List<PolicyStatModel> getPolicyOrganNetStasticsWithBankCode(String organCode, String d1, String d2, String flag,
			String prdCode, String toPerm, String staffFlag, String bankName) {
		return policyDAO.getPolicyDateOrganNetStatWithBankCode(organCode, d1, d2, flag, prdCode, toPerm, staffFlag, bankName);
	}

	@Override
	public List<PolicyStatModel> getPolicyFeeFrequencyStastics(String organCode, String d1, String d2, String prdCode,
			String toPerm, String staffFlag) {
		return policyDAO.getPolicyDateFeeTypeStat(organCode, d1, d2, prdCode, toPerm, staffFlag);
	}

	@Override
	public List<PolicyStatModel> getPolicyFeeFrequencyStasticsWithBankCode(String organCode, String d1, String d2,
			String flag, String prdCode, String toPerm, String staffFlag, String bankName) {
		return policyDAO.getPolicyDateFeeTypeStatWithBankCode(organCode, d1, d2, flag, prdCode, toPerm, staffFlag, bankName);
	}
}
