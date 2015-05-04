/**
 * 
 */
package com.gdpost.web.service.member;

import java.util.List;

import com.gdpost.web.entity.basedata.Area;
import com.gdpost.web.entity.basedata.City;
import com.gdpost.web.entity.basedata.Province;

public interface AddrService {
	Province getProvince(String code);
	
	City getCity(String code);
	
	Area getArea(String code);
	
	List<Province> getAllProvince();
	
	List<City> getAllCityByProvinceCode(String provinceCode);
	
	List<Area> getAllAreaByCityCode(String cityCode);
}
