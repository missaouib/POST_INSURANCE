package com.gdpost.web.service.impl.component;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdpost.web.dao.component.StaffDtlModelDAO;
import com.gdpost.web.dao.component.StaffModelDAO;
import com.gdpost.web.dao.component.TuiBaoDtlModelDAO;
import com.gdpost.web.dao.component.TuiBaoModelDAO;
import com.gdpost.web.dao.component.UwDtlModelDAO;
import com.gdpost.web.dao.component.UwModelDAO;
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

	@Override
	public List<TuiBaoModel> getTuiBaoWarnningWithPolicyDateAndCsDateNoBankCode(String organCode, String d1, String d2, String d3, String d4, String prdCode, String toPerm, String staffFlag) {
		return cmDAO.getTuiBaoWarningWithPolicyDateAndCsDateNoBankCode(organCode, d1, d2, d3, d4, prdCode, toPerm, staffFlag);
	}
	
	@Override
	public List<TuiBaoModel> getTuiBaoWarnningWithPolicyDateAndCsDate(String organCode, String d1, String d2, String d3, String d4, String flag, String prdCode, String toPerm, String staffFlag) {
		return cmDAO.getTuiBaoWarningWithPolicyDateAndCsDate(organCode, d1, d2, d3, d4, flag, prdCode, toPerm, staffFlag);
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
	public List<TuiBaoDtlModel> getTuiBaoWarnningDetailWithBankCode(String organCode, String d1, String d2, String d3, String d4, String flag, String prdCode, String toPerm, String staffFlag) {
		return tbdDAO.getAllTuiBaoWarningDetailWithBankCode(organCode, d1, d2, d3, d4, flag, prdCode, toPerm, staffFlag);
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
	public List<UwDtlModel> getUwDtlStastics(String organCode, String d1, String d2) {
		return udDAO.getUwDtlStastic(organCode, d1, d2);
	}
}
