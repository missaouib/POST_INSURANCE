/**
 * 
 */
package com.sendtend.web.service.member;

import java.util.List;

import com.sendtend.web.entity.member.Area;
import com.sendtend.web.entity.member.City;
import com.sendtend.web.entity.member.Province;

public interface AddrService {
	Province getProvince(String code);
	
	City getCity(String code);
	
	Area getArea(String code);
	
	List<Province> getAllProvince();
	
	List<City> getAllCityByProvinceCode(String provinceCode);
	
	List<Area> getAllAreaByCityCode(String cityCode);
}
