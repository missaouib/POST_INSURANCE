/**
 * 
 */
package	com.gdpost.web.service.impl.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdpost.web.dao.member.AreaDAO;
import com.gdpost.web.dao.member.CityDAO;
import com.gdpost.web.dao.member.ProvinceDAO;
import com.gdpost.web.entity.basedata.Area;
import com.gdpost.web.entity.basedata.City;
import com.gdpost.web.entity.basedata.Province;
import com.gdpost.web.service.member.AddrService;

@Service
@Transactional
public class AddrServiceImpl implements AddrService {
	
	//private static final Logger LOG = LoggerFactory.getLogger(AddrServiceImpl.class);
	
	@Autowired
	private ProvinceDAO provinceDAO;
	
	@Autowired
	private CityDAO cityDAO;
	
	@Autowired
	private AreaDAO areaDAO;

	@Override
	public Province getProvince(String code) {
		List<Province> provinces = provinceDAO.findDistinctProvinceByProvinceCode(code);
		if(provinces == null || provinces.isEmpty()) {
			return null;
		}
		return provinces.get(0);
	}

	@Override
	public City getCity(String code) {
		List<City> citys = cityDAO.findDistinctCityBycityCode(code);
		if(citys == null || citys.isEmpty()) {
			return null;
		}
		return citys.get(0);
	}

	@Override
	public Area getArea(String code) {
		List<Area> areas = areaDAO.findDistinctAreaByAreaCode(code);
		if(areas == null || areas.isEmpty()) {
			return null;
		}
		return areas.get(0);
	}

	@Override
	public List<Province> getAllProvince() {
		return provinceDAO.findAllWithCache();
	}

	@Override
	public List<City> getAllCityByProvinceCode(String provinceCode) {
		return cityDAO.findByProvinceCodeWithCache(provinceCode);
	}

	@Override
	public List<Area> getAllAreaByCityCode(String cityCode) {
		return areaDAO.findByCityCodeWithCache(cityCode);
	}
}
