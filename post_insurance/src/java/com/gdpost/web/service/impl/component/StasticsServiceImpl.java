package com.gdpost.web.service.impl.component;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdpost.web.dao.component.StaffModelDAO;
import com.gdpost.web.dao.component.TuiBaoModelDAO;
import com.gdpost.web.entity.component.StaffModel;
import com.gdpost.web.entity.component.TuiBaoModel;
import com.gdpost.web.service.component.StasticsService;

@Service
@Transactional
public class StasticsServiceImpl implements StasticsService {
	//private static final Logger logger = LoggerFactory.getLogger(QyglServiceImpl.class);
	
	@Autowired
	private TuiBaoModelDAO cmDAO;
	
	@Autowired
	private StaffModelDAO smDAO;

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
		return cmDAO.getProvTuiBaoWarningWithPolicyDateAndCsDateNoBankCode(organCode, d1, d2, d3, d4, prdCode, toPerm, staffFlag);
	}
	
	@Override
	public List<TuiBaoModel> getProvTuiBaoWarnningWithPolicyDateAndCsDate(String organCode, String d1, String d2, String d3, String d4, String flag, String prdCode, String toPerm, String staffFlag) {
		return cmDAO.getProvTuiBaoWarningWithPolicyDateAndCsDate(organCode, d1, d2, d3, d4, flag, prdCode, toPerm, staffFlag);
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
	
}
