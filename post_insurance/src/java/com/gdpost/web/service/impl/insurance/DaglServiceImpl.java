/**
 * 
 */
package	com.gdpost.web.service.impl.insurance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdpost.web.dao.model.DocStatModelDAO;
import com.gdpost.web.entity.component.DocStatModel;
import com.gdpost.web.service.insurance.DaglService;

@Service
@Transactional
public class DaglServiceImpl implements DaglService {
	@Autowired
	DocStatModelDAO dsm;
	
	@Override
	public List<DocStatModel> getDocNotScanStat(String d1, String d2) {
		return dsm.getDocNotScanStat(d1, d2);
	}

	@Override
	public List<DocStatModel> getSubDocNotScanStat(String organName, String d1, String d2) {
		return dsm.getSubDocNotScanStat(organName, d1, d2);
	}

	@Override
	public List<DocStatModel> getSumDocStat(String organName, String d1, String d2) {
		return dsm.getDocSumStat(organName, d1, d2);
	}
	
}
