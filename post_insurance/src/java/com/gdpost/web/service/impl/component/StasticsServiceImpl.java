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
	public List<CommonModel> getTuiBaoWarnning() {
		List<CommonModel> test = cmDAO.getTuiBaoWarning();
		return test;
	}
		
		
}
