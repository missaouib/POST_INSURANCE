/**
 * 
 */
package com.gdpost.web.controller.member;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdpost.web.entity.basedata.Area;
import com.gdpost.web.entity.basedata.City;
import com.gdpost.web.service.member.AddrService;

@Controller
@RequestMapping("/members/addr")
public class AddrController {
	//private static final Logger LOG = LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	private AddrService addrService;

	@RequestMapping(value="/city/{provinceCode}", method=RequestMethod.POST)
	public @ResponseBody String getCityListByProvinceCode(@PathVariable String provinceCode, Map<String, Object> map) {
		if(provinceCode == null || provinceCode.trim().length() <= 0 || provinceCode.equals("0")) {
			return "[[\"0\",\" — — \"]]";
		}
		List<City> list = addrService.getAllCityByProvinceCode(provinceCode);
		StringBuffer strbuf = new StringBuffer("[");
		for(City city : list) {
			strbuf.append("[\"" + city.getCityCode() + "\", \"" + city.getCityName() + "\"],");
		}
		if(strbuf.toString().endsWith(",")) {
			strbuf.deleteCharAt(strbuf.length()-1);
		}
		strbuf.append("]");
		return strbuf.toString();
	}
	
	@RequestMapping(value="/area/{cityCode}", method=RequestMethod.POST)
	public @ResponseBody String getAreaListByCityCode(@PathVariable String cityCode, Map<String, Object> map) {
		if(cityCode == null || cityCode.trim().length() <= 0 || cityCode.equals("0")) {
			return "[[\"0\",\" — — \"]]";
		}
		List<Area> list = addrService.getAllAreaByCityCode(cityCode);
		StringBuffer strbuf = new StringBuffer("[");
		for(Area area : list) {
			strbuf.append("[\"" + area.getAreaCode() + "\", \"" + area.getAreaName() + "\"],");
		}
		if(strbuf.toString().endsWith(",")) {
			strbuf.deleteCharAt(strbuf.length()-1);
		}
		strbuf.append("]");
		return strbuf.toString();
	}
}
