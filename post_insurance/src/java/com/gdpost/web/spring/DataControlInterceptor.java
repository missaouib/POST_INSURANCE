/**
 * 
 */
package com.gdpost.web.spring;

import java.io.StringReader;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.gdpost.utils.Exceptions;
import com.gdpost.utils.SecurityUtils;
import com.gdpost.web.SecurityConstants;
import com.gdpost.web.entity.main.DataControl;
import com.gdpost.web.entity.main.Module;
import com.gdpost.web.entity.main.User;
import com.gdpost.web.spring.DataControlXML.Condition;
import com.gdpost.web.util.persistence.DynamicSpecifications;
import com.gdpost.web.util.persistence.SearchFilter;
import com.gdpost.web.util.persistence.SearchFilter.Operator;

import freemarker.template.SimpleHash;

/**
 * @author 
 *
 */
public class DataControlInterceptor extends HandlerInterceptorAdapter {
	private final static Logger logger = LoggerFactory.getLogger(DataControlInterceptor.class);
	
	protected static final String PART_DIVIDER_TOKEN = ":";
	protected static final String PERMISSION_DIVIDER_TOKEN = ",";
	protected static final String HOLDER_DIVIDER_TOKEN = "#";
	
	protected static final String SHOW_METHOD_PREFIX = "list";
	protected static final String MANY_METHOD_SUFFIX = "Many";
	
	protected static final String SINGLE_KEY = "id";
	protected static final String MANY_KEY = "ids";
	
	@PersistenceContext
	private EntityManager em;
	
	private static Unmarshaller unmarshaller;
	
	static {
		try {
			unmarshaller = JAXBContext.newInstance(DataControlXML.class).createUnmarshaller(); 
		} catch (JAXBException e) {
			logger.error("DataControlXML JAXB??????????????????" + Exceptions.getStackTraceAsString(e));
		}
	}
	
	private static FreeMarkerParse freeMarkerParse = new FreeMarkerParse();
	
	/**
	 * @param request
	 * @param response
	 * @param handler
	 * @return
	 * @throws Exception
	 * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		DynamicSpecifications.putRequest(request);
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}
		
		final HandlerMethod handlerMethod = (HandlerMethod)handler;
		Method method = handlerMethod.getMethod();
		
		final RequiresPermissions rps = method.getAnnotation(RequiresPermissions.class);
		if (rps == null) {
			return true;
		}
		Logical logical = rps.logical();
		//logger.debug("-----------DataControlInteceptor:" + logical.name() + ":" + method.getName());
		String[] pv = rps.value();
		
		// ?????????????????????OR?????????????????????????????????????????????????????????????????????true???
		if (logical.equals(Logical.OR)) {
			for (String p : pv) {
				if (p.split(PART_DIVIDER_TOKEN).length < 3) {
					return true;
				} else if(p.endsWith("*")) {
					return true;
				}
			}
		}

		boolean firstPermitted = false;
		for (String p : pv) {
			String[] v = p.split(PART_DIVIDER_TOKEN);
			
			if (v.length == 3) {
				// ???????????????????????????shiro????????????????????????????????? 
				if (!firstPermitted) {
					Subject subject = SecurityUtils.getSubject();
					//logger.debug("-----------DataControlInteceptor p:" + p);
					//logger.debug("-----------DataControlInteceptor subject:" + subject.getPrincipals());
					if(p.endsWith("*")) {
						firstPermitted = true;
					} else if (!subject.isPermitted(p)){ 
						throw new UnauthorizedException("???????????????????????????");
					}
					firstPermitted = true;
				}
			
				try {
					// ????????????????????????????????????logical??????request
					request.setAttribute(SecurityConstants.NEST_DYNAMIC_SEARCH_LOGICAL, logical);
					boolean checkResult = (check(request, response, method, v[0], v[2]) == true) ? true : false;
					if (!checkResult) {
						throw new UnauthorizedException("???????????????????????????");
					}
					
					if (checkResult == true && logical.equals(Logical.OR)) {
						return true;
					}
				} catch (Exception e) {
					logger.error(Exceptions.getStackTraceAsString(e));
					throw new UnauthorizedException("???????????????????????????");
				}
			}
		}
		
		return true;
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#afterCompletion(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
	 */
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		DynamicSpecifications.removeRequest();
	}
	
	public boolean check(HttpServletRequest request, HttpServletResponse response, Method method,
			String moduleString, String dataControlString) {
		String[] m = moduleString.split(PERMISSION_DIVIDER_TOKEN);
		for (String sn : m) {
			String[] d = dataControlString.split(PERMISSION_DIVIDER_TOKEN);
			for (String name : d) {
				
				DataControl dataControl = SecurityUtils.getShiroUser().getHasDataControls().get(name); 
				if (dataControl == null) {
					continue;
				}
				
				// EQ_user.id=#userId, EQ_user.id_OR=2, id=#id
				String control = dataControl.getControl();
				Map<String, Object> controlMap = new HashMap<String, Object>();
				try {
					DataControlXML xml = (DataControlXML)unmarshaller.unmarshal(new StringReader(control));
					for (Condition c : xml.getItems()) {
						controlMap.put(c.getName(), c.getContent());
					}
				} catch (Exception e) {
					throw new IllegalArgumentException("?????????????????????????????????" + control, e);
				}
				
				Set<Entry<String, Object>> set = controlMap.entrySet();
				for (Entry<String, Object> entry : set) {
					String value = (String)entry.getValue();
					try {
						entry.setValue(parseValue(request, response, control));
					} catch (Exception e) {
						throw new IllegalArgumentException("?????????????????????????????????" + value, e);
					}
				}
				
				Map<String, SearchFilter> searchFilters = SearchFilter.parse(controlMap);
				Set<SearchFilter> filterSet = new HashSet<SearchFilter>(searchFilters.values());
				try {
					Module module = SecurityUtils.getShiroUser().getHasModules().get(sn);
					
					boolean handleResult = true;
					if (method.getName().startsWith(SHOW_METHOD_PREFIX)) {
						handleResult = (handleList(request, filterSet, method, dataControl, module) == true) ? true : false;
					} else {
						handleResult = (handleOther(request, filterSet, method, dataControl, module) == true) ? true : false;
					}
					
					if (!handleResult) {
						return false;
					}
				} catch (Exception e) {
					throw new IllegalArgumentException("?????????????????????????????????" + method.getName(), e);
				}
			}

		}
		
		return true;
	}
	
	/**
	 * ???????????????????????????
	 */
	@SuppressWarnings("unchecked")
	protected boolean handleList(HttpServletRequest request, Set<SearchFilter> filterSet, 
			Method method, DataControl dataControl, Module module) {
		Logical logical = (Logical)request.getAttribute(SecurityConstants.NEST_DYNAMIC_SEARCH_LOGICAL);
		if (logical.equals(Logical.AND)) {
			Set<SearchFilter> pre = (Set<SearchFilter>)request.getAttribute(SecurityConstants.NEST_DYNAMIC_SEARCH);
			if (pre == null) {
				pre = new HashSet<SearchFilter>();
				request.setAttribute(SecurityConstants.NEST_DYNAMIC_SEARCH, pre);
			}
				
			pre.addAll(filterSet);
		} else {
			request.setAttribute(SecurityConstants.NEST_DYNAMIC_SEARCH, filterSet);
		}
		
		return true;
	}
	
	/**
	 * ?????????????????? 
	 * @throws Exception 
	 * @throws  
	 * @throws InstantiationException 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected boolean handleOther(HttpServletRequest request, Set<SearchFilter> filterSet, 
			Method method, DataControl dataControl, Module module) throws Exception {
		
		String[] ids = null;
		if (method.getName().endsWith(MANY_METHOD_SUFFIX)) { // ?????????????????????
			ids = request.getParameterValues(MANY_KEY);
			if (ids != null) {
				filterSet.add(new SearchFilter("id", Operator.IN, ids));
			}
		} else {
			String id = request.getParameter("id");
			if (id != null) {
				filterSet.add(new SearchFilter("id", Operator.EQ, id));
			} else {
				// ????????????/update/{id}???id
				String uri = request.getRequestURI();
				String tmp = StringUtils.substringAfterLast(uri, "/");
				Long longId = NumberUtils.toLong(tmp);
				if (longId != 0L) {
					filterSet.add(new SearchFilter("id", Operator.EQ, longId));
				}
			}
		}
		
		Object clazz = Class.forName(module.getClassName()).newInstance();
		//Object clazz = null;
		Specification spec = DynamicSpecifications.bySearchFilter(request, clazz.getClass(), filterSet);
		
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery criteriaQuery = builder.createQuery(clazz.getClass());
		Root root = criteriaQuery.from(clazz.getClass());
		
		Predicate predicate = spec.toPredicate(root, criteriaQuery, builder);
		criteriaQuery.where(predicate);
		
		List<Object> objects = em.createQuery(criteriaQuery).getResultList();
		if (ids != null) {
			if (objects.size() == ids.length) {
				return true;
			} 
		} else {
			if (objects.size() > 0) {
				return true;
			}
		}
		
		return false;
	}
	
	protected String parseValue(HttpServletRequest request, String value) throws Exception {
		if (value.startsWith(HOLDER_DIVIDER_TOKEN)) {
			value = value.substring(1);
			
			// ??????????????????????????????
			if (value.startsWith("user.")) {
				value = value.substring(5);
				
				User user = (User) SecurityUtils.getLoginUser();
				return PropertyUtils.getProperty(user, value).toString();
			}
			
			// ??????????????????????????????
			//if (value.startsWith("tblMemberUser.")) {
				//value = value.substring(13);
				
				//TblMemberUser user = (TblMemberUser) SecurityUtils.getLoginUser();
				//return PropertyUtils.getProperty(user, value).toString();
			//} 
			
			return request.getParameter(value);
		} 
		
		return value;
	}
	
	protected String parseValue(HttpServletRequest request, HttpServletResponse response, 
			String control) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("user", SecurityUtils.getLoginUser());
		model.putAll(SecurityUtils.getShiroUser().getAttributes());
		
		WebApplicationContext context = RequestContextUtils.findWebApplicationContext(request);
		SimpleHash simpleHash = freeMarkerParse.buildTemplateModel(model, context.getServletContext(), request, response);
		
		return freeMarkerParse.renderString(control, simpleHash);
	}
}
