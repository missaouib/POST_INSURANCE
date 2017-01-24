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
	public List<CommonModel> getTuiBaoWarnningWithPolicyDate(String organCode, String d1, String d2) {
		return cmDAO.getTuiBaoWarningWithPolicyDate(organCode, d1, d2);
	}

	@Override
	public List<CommonModel> getTuiBaoWarnningWithCsDate(String organCode, String d1, String d2) {
		return cmDAO.getTuiBaoWarningWithCsDate(organCode, d1, d2);
	}

	@Override
	public List<CommonModel> getTuiBaoWarnningWithPolicyDateAndCsDate(String organCode, String d1, String d2, String d3, String d4) {
		return cmDAO.getTuiBaoWarningWithPolicyDateAndCsDate(organCode, d1, d2, d3, d4);
	}
		
}
