package com.gdpost.web.service.impl.component;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdpost.web.dao.component.CommonModelDAO;
import com.gdpost.web.entity.component.CommonModel;
import com.gdpost.web.service.component.StasticsService;

@Service
@Transactional
public class StasticsServiceImpl implements StasticsService {
	//private static final Logger logger = LoggerFactory.getLogger(QyglServiceImpl.class);
	
	@Autowired
	private CommonModelDAO cmDAO;

	@Override
	public List<CommonModel> getTuiBaoWarnningWithPolicyDateAndCsDateNoBankCode(String organCode, String d1, String d2, String d3, String d4, String prdCode) {
		return cmDAO.getTuiBaoWarningWithPolicyDateAndCsDateNoBankCode(organCode, d1, d2, d3, d4, prdCode);
	}
	
	@Override
	public List<CommonModel> getTuiBaoWarnningWithPolicyDateAndCsDate(String organCode, String d1, String d2, String d3, String d4, String flag, String prdCode) {
		return cmDAO.getTuiBaoWarningWithPolicyDateAndCsDate(organCode, d1, d2, d3, d4, flag, prdCode);
	}
	
	@Override
	public List<CommonModel> getProvTuiBaoWarnningWithPolicyDateAndCsDateNoBankCode(String organCode, String d1, String d2, String d3, String d4, String prdCode) {
		return cmDAO.getProvTuiBaoWarningWithPolicyDateAndCsDateNoBankCode(organCode, d1, d2, d3, d4, prdCode);
	}
	
	@Override
	public List<CommonModel> getProvTuiBaoWarnningWithPolicyDateAndCsDate(String organCode, String d1, String d2, String d3, String d4, String flag, String prdCode) {
		return cmDAO.getProvTuiBaoWarningWithPolicyDateAndCsDate(organCode, d1, d2, d3, d4, flag, prdCode);
	}
}
