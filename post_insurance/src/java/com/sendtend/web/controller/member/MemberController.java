/**
 * 
 */
package com.sendtend.web.controller.member;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sendtend.utils.DataControlFilter;
import com.sendtend.utils.SecurityUtils;
import com.sendtend.web.dao.member.MUserDAO;
import com.sendtend.web.entity.member.Area;
import com.sendtend.web.entity.member.City;
import com.sendtend.web.entity.member.Province;
import com.sendtend.web.entity.member.TblMember;
import com.sendtend.web.entity.member.TblMemberRole;
import com.sendtend.web.entity.member.TblMemberUser;
import com.sendtend.web.entity.member.TblMemberUserRole;
import com.sendtend.web.exception.ServiceException;
import com.sendtend.web.log.Log;
import com.sendtend.web.log.LogMessageObject;
import com.sendtend.web.log.impl.LogUitls;
import com.sendtend.web.service.member.AddrService;
import com.sendtend.web.service.member.MRoleService;
import com.sendtend.web.service.member.MUserRoleService;
import com.sendtend.web.service.member.MUserService;
import com.sendtend.web.service.member.MemberRoleService;
import com.sendtend.web.service.member.MemberService;
import com.sendtend.web.shiro.ShiroDbRealm;
import com.sendtend.web.shiro.ShiroDbRealm.HashPassword;
import com.sendtend.web.shiro.ShiroUser;
import com.sendtend.web.util.dwz.AjaxObject;
import com.sendtend.web.util.dwz.Page;
import com.sendtend.web.util.persistence.DynamicSpecifications;
import com.sendtend.web.util.persistence.SearchFilter;
import com.sendtend.web.util.persistence.SearchFilter.Operator;

@Controller
@RequestMapping("/members/member")
public class MemberController {
	private static final Logger LOG = LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private AddrService addrService;
	
	@Autowired
	private MUserDAO userDAO;
	
	@Autowired
	private MUserService userService;

	@Autowired
	private MUserRoleService userRoleService;
	
	@Autowired
	private ShiroDbRealm shiroRealm;
	
	@Autowired
	private MemberRoleService memberRoleService;
	
	@Autowired
	private MRoleService roleService;
	
	private static final String CREATE = "members/member/create";
	private static final String UPDATE = "members/member/update";
	private static final String WEBCREATE = "members/member/webcreate";
	private static final String WEBUPDATE = "members/member/webupdate";
	private static final String LIST = "members/member/list";
	private static final String TREE = "members/member/tree";
	private static final String TREE_LIST = "members/member/tree_list";
	private static final String LOOKUP_PARENT = "members/member/lookup_parent";
	private static final String REGISTER = "members/member/register";
	private static final String WEBLIST = "members/member/weblist";
	
	@RequiresPermissions("TblMember:save")
	@RequestMapping(value="/create/{parentMemberId}", method=RequestMethod.GET)
	public String preCreate(@PathVariable Long parentMemberId, Map<String, Object> map) {
		map.put("parentMemberId", parentMemberId);
		map.put("provinceCode", "provinceCode");
		map.put("deliverProvinceCode", "deliverProvinceCode");
		List<Province> list = addrService.getAllProvince();
		Province p = new Province();
		p.setProvinceCode("0");
		p.setProvinceName(" — — ");
		list.add(0, p);
		map.put("provinces", list);
		return CREATE;
	}
	
	@RequiresPermissions("TblMember:save")
	@RequestMapping(value="/webCreate/{parentMemberId}", method=RequestMethod.GET)
	public String preWebCreate(@PathVariable Long parentMemberId, Map<String, Object> map) {
		map.put("parentMemberId", parentMemberId);
		map.put("provinceCode", "provinceCode");
		map.put("deliverProvinceCode", "deliverProvinceCode");
		List<Province> list = addrService.getAllProvince();
		Province p = new Province();
		p.setProvinceCode("0");
		p.setProvinceName(" — — ");
		list.add(0, p);
		map.put("provinces", list);
		return WEBCREATE;
	}
	
	@Log(message="添加了{0}会员。")
	@RequiresPermissions("TblMember:save")
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public @ResponseBody String create(@Valid TblMember member) {
		try {
			memberService.saveOrUpdate(member);
			//memberService.add(member, MySQLAESKey.AESKey);
		} catch (ServiceException e) {
			return AjaxObject.newError("添加会员失败：" + e.getMessage()).toString();
		}
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{member.getMemberName()}));
		return AjaxObject.newOk("添加会员成功！").toString();
	}
	
	@ModelAttribute("preloadMember")
	public TblMember preload(@RequestParam(value = "id", required = false) Long id) {
		if (id != null) {
			TblMember member = memberService.get(id);
			member.setParent(null);
			return member;
		}
		return null;
	}
	
	@RequiresPermissions("TblMember:edit")
	@RequestMapping(value="/update/{id}", method=RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
		TblMember member = memberService.get(id);
		
		map.put("member", member);
		
		List<Province> list = addrService.getAllProvince();
		Province p = new Province();
		p.setProvinceCode("0");
		p.setProvinceName(" — — ");
		list.add(0, p);
		map.put("provinces", list);
		
		City c = new City();
		c.setCityCode("0");
		c.setCityName(" — — ");
		Area a = new Area();
		a.setAreaCode("0");
		a.setAreaName(" — — ");
		//已选省的市列表
		List<City> cityList = addrService.getAllCityByProvinceCode(member.getProvinceCode());
		cityList.add(0, c);
		map.put("cityList", cityList);
		//已选市的县区列表
		List<Area> areaList = addrService.getAllAreaByCityCode(member.getCityCode());
		areaList.add(0, a);
		map.put("areaList", areaList);
		//已选投递省的市列表
		List<City> dcityList = addrService.getAllCityByProvinceCode(member.getDeliverProvinceCode());
		dcityList.add(0, c);
		map.put("dcityList", dcityList);
		//已选投递市的去列表
		List<Area> dareaList = addrService.getAllAreaByCityCode(member.getDeliverCityCode());
		dareaList.add(0, a);
		map.put("dareaList", dareaList);
		
		return UPDATE;
	}
	
	@RequiresPermissions("TblMember:edit")
	@RequestMapping(value="/webUpdate/{id}", method=RequestMethod.GET)
	public String preWebUpdate(@PathVariable Long id, Map<String, Object> map) {
		TblMember member = memberService.get(id);
		
		map.put("member", member);
		
		List<Province> list = addrService.getAllProvince();
		Province p = new Province();
		p.setProvinceCode("0");
		p.setProvinceName(" — — ");
		list.add(0, p);
		map.put("provinces", list);
		
		City c = new City();
		c.setCityCode("0");
		c.setCityName(" — — ");
		Area a = new Area();
		a.setAreaCode("0");
		a.setAreaName(" — — ");
		//已选省的市列表
		List<City> cityList = addrService.getAllCityByProvinceCode(member.getProvinceCode());
		cityList.add(0, c);
		map.put("cityList", cityList);
		//已选市的县区列表
		List<Area> areaList = addrService.getAllAreaByCityCode(member.getCityCode());
		areaList.add(0, a);
		map.put("areaList", areaList);
		//已选投递省的市列表
		List<City> dcityList = addrService.getAllCityByProvinceCode(member.getDeliverProvinceCode());
		dcityList.add(0, c);
		map.put("dcityList", dcityList);
		//已选投递市的去列表
		List<Area> dareaList = addrService.getAllAreaByCityCode(member.getDeliverCityCode());
		dareaList.add(0, a);
		map.put("dareaList", dareaList);
		
		return WEBUPDATE;
	}
	
	@Log(message="修改了{0}会员的信息。")
	@RequiresPermissions("TblMember:edit")
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public @ResponseBody String update(@Valid @ModelAttribute("preloadMember")TblMember member) {
		memberService.saveOrUpdate(member);
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{member.getMemberName()}));
		return AjaxObject.newOk("修改会员成功！").toString();
	}
	
	@Log(message="删除了{0}会员。")
	@RequiresPermissions("TblMember:delete")
	@RequestMapping(value="/delete/{id}", method=RequestMethod.POST)
	public @ResponseBody String delete(@PathVariable Long id) {
		TblMember member = memberService.get(id);
		try {
			memberService.delete(id);
		} catch (ServiceException e) {
			return AjaxObject.newError("删除会员失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{member.getMemberName()}));
		return AjaxObject.newOk("删除会员成功！").setCallbackType("").toString();
	}
	
	@RequiresPermissions("TblMember:view")
	@RequestMapping(value="/tree_list", method={RequestMethod.GET, RequestMethod.POST})
	public String tree_list(Map<String, Object> map) {
		return TREE_LIST;
	}
	
	@RequiresPermissions("TblMember:view")
	@RequestMapping(value="/tree", method={RequestMethod.GET, RequestMethod.POST})
	public String tree(Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		TblMemberUser user = shiroUser.getMemberUser();
		TblMember member = null;
		if(user != null) {
			member = memberService.getTree(user.getTblMember().getId());
		} else {
			member = memberService.getTree();
		}
		//memberService.get(new Long(1));
		map.put("member", member);
		return TREE;
	}
	
	@RequiresPermissions("TblMember:view")
	@RequestMapping(value="/list/{parentMemberId}", method={RequestMethod.GET, RequestMethod.POST})
	public String list(ServletRequest request, Page page, @PathVariable Long parentMemberId, 
			Map<String, Object> map) {
		
		TblMemberUser user = SecurityUtils.getLoginTblMemberUser();
		Specification<TblMember> specification = DynamicSpecifications.bySearchFilter(request, TblMember.class,
				new SearchFilter("parent.id", Operator.EQ, parentMemberId));
		
		if(user != null) {
			specification = DynamicSpecifications.bySearchFilter(request, TblMember.class,
					new SearchFilter("parent.id", Operator.EQ, parentMemberId),
					new SearchFilter("id", Operator.EQ, user.getTblMember().getId()));
		}
		
		List<TblMember> members = memberService.findByExample(specification, page);
		//如果没有下级回显点击的节点信息
		if(members.isEmpty()) {
			specification = DynamicSpecifications.bySearchFilter(request, TblMember.class, new SearchFilter("id", Operator.EQ,
					parentMemberId));
			members = memberService.findByExample(specification, page);
		}
		DataControlFilter.filterBeanList(members, "Member");
		
		map.put("page", page);
		map.put("members", members);
		map.put("parentMemberId", parentMemberId);

		return LIST;
	}
	
	@RequiresPermissions("TblMember:view")
	@RequestMapping(value="/weblist/{parentMemberId}", method={RequestMethod.GET, RequestMethod.POST})
	public String weblist(ServletRequest request, Page page, @PathVariable Long parentMemberId, 
			Map<String, Object> map) {
		
		//TblMemberUser user = SecurityUtils.getLoginTblMemberUser();
		Specification<TblMember> specification = DynamicSpecifications.bySearchFilter(request, TblMember.class,
				new SearchFilter("parent.id", Operator.EQ, parentMemberId));
		
		/*
		if(user != null) {
			specification = DynamicSpecifications.bySearchFilter(request, TblMember.class,
					new SearchFilter("parent.id", Operator.EQ, parentMemberId),
					new SearchFilter("id", Operator.EQ, user.getTblMember().getId()));
		}*/
		
		List<TblMember> members = memberService.findByExample(specification, page);
		//如果没有下级回显点击的节点信息
		/*
		if(members.isEmpty()) {
			specification = DynamicSpecifications.bySearchFilter(request, TblMember.class, new SearchFilter("id", Operator.EQ,
					parentMemberId));
			members = memberService.findByExample(specification, page);
		}
		*/
		
		// 附加父连锁
		specification = DynamicSpecifications.bySearchFilter(request, TblMember.class, new SearchFilter("id", Operator.EQ,
				parentMemberId));
		Page parentPage = new Page();
		List<TblMember> parent = memberService.findByExample(specification, parentPage);
		List<TblMember> aryMembers = new ArrayList<TblMember>();
		aryMembers.add(parent.get(0));
		aryMembers.addAll(members);
		page.setTotalCount(page.getTotalCount() + 1);
		// 附加父连锁
		
		DataControlFilter.filterBeanList(members, "Member");
		//LOG.debug("----- member lists size:" + members.size());
		for(TblMember m:members) {
			m.setProvinceName(addrService.getProvince(m.getProvinceCode())==null?"":addrService.getProvince(m.getProvinceCode()).getProvinceName());
			m.setCityName(addrService.getCity(m.getCityCode())==null?"":addrService.getCity(m.getCityCode()).getCityName());
			m.setAreaName(addrService.getArea(m.getAreaCode())==null?"":addrService.getArea(m.getAreaCode()).getAreaName());
		}
		//LOG.debug(members.toString());
		
		map.put("page", page);
		map.put("members", aryMembers);
		map.put("parentMemberId", parentMemberId);

		//List<Province> list = addrService.getAllProvince();
		//map.put("provinces", list);
		/*
		//已选省的市列表
		List<City> cityList = addrService.ge(member.getProvinceCode());
		map.put("cityList", cityList);
		//已选市的县区列表
		List<Area> areaList = addrService.getAllAreaByCityCode(member.getCityCode());
		map.put("areaList", areaList);
		//已选投递省的市列表
		List<City> dcityList = addrService.getAllCityByProvinceCode(member.getDeliverProvinceCode());
		map.put("dcityList", dcityList);
		//已选投递市的去列表
		List<Area> dareaList = addrService.getAllAreaByCityCode(member.getDeliverCityCode());
		map.put("dareaList", dareaList);
		*/
		return WEBLIST;
	}
	
	@RequiresPermissions(value={"TblMember:edit", "TblMember:save"}, logical=Logical.OR)
	@RequestMapping(value="/lookupParent/{id}", method={RequestMethod.GET})
	public String lookup(@PathVariable Long id, Map<String, Object> map) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		TblMemberUser user = shiroUser.getMemberUser();
		TblMember member = null;
		if(user != null) {
			member = memberService.getTree(user.getTblMember().getId());
		} else {
			member = memberService.getTree();
		}
		
		map.put("member", member);
		return LOOKUP_PARENT;
	}
	
	@RequestMapping(value="/register", method={RequestMethod.GET, RequestMethod.POST})
	public String register(ServletRequest request, Map<String, Object> map, TblMember member, TblMemberUser user) {
		if(request.getParameter("parent.id") == null) {
			return REGISTER;
		}
		//保存会员
		TblMember parent = memberService.get(new Long(1));//跟
		member.setParent(parent);
		member.setStatus(1);
		member = memberService.saveOrUpdate(member);
		//保存用户
		user.setTblMember(member);
		user.setStatus(TblMemberUser.STATUS_ENABLED);
		user.setCreateDate(new Date());
		user.setIsAdmin(0);
		if (StringUtils.isNotBlank(user.getPlainPassword()) && shiroRealm != null) {
			HashPassword hashPassword = ShiroDbRealm.encryptPassword(user.getPlainPassword());
			user.setSalt(hashPassword.salt);
			user.setPassword(hashPassword.password);
		}
		user = userDAO.save(user);
		//初始化用户的角色
		TblMemberRole mRole = roleService.get(new Long(1));
		TblMemberUserRole userRole = new TblMemberUserRole();
		userRole.setTblMemberRole(mRole);
		userRole.setTblMemberUser(user);
		userRole.setPriority(999);
		userRoleService.saveOrUpdate(userRole);
		shiroRealm.clearCachedAuthorizationInfo(user.getUserName());
		
		return REGISTER;
	}
}
