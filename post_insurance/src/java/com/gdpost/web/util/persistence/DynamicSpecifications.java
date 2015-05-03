package com.gdpost.web.util.persistence;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import com.gdpost.utils.Exceptions;
import com.gdpost.utils.ServletUtils;
import com.gdpost.web.SecurityConstants;

public class DynamicSpecifications {
	private static final Logger logger = LoggerFactory.getLogger(DynamicSpecifications.class);
	
	// 用于存储每个线程的request请求
	private static final ThreadLocal<HttpServletRequest> LOCAL_REQUEST = new ThreadLocal<HttpServletRequest>();
	
	private static final String SHORT_DATE = "yyyy-MM-dd";
	private static final String LONG_DATE = "yyyy-MM-dd HH:mm:ss";
	private static final String TIME = "HH:mm:ss";
	
	public static void putRequest(HttpServletRequest request) {
		LOCAL_REQUEST.set(request);
	}
	
	public static HttpServletRequest getRequest() {
		return LOCAL_REQUEST.get();
	}
	
	public static void removeRequest() {
		LOCAL_REQUEST.remove();
	}
	
	public static Collection<SearchFilter> genSearchFilter(ServletRequest request) {
		Map<String, Object> searchParams = ServletUtils.getParametersStartingWith(request, SecurityConstants.SEARCH_PREFIX);
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		return filters.values();
	}
	
	public static <T> Specification<T> bySearchFilter(ServletRequest request, final Class<T> entityClazz, final Collection<SearchFilter> searchFilters) {
		/*
		 * =======================
		 * 定义过滤条件列表
		 * =======================
		 */
		Collection<SearchFilter> idfilters = new HashSet<SearchFilter>();
		//String[] dc = null;
		/*
		 * =======================
		 * 从登录用户中获取到授权的数据权限
		 * =======================
		 */
//		Map<String, TblMemberDataControl> mdcs = SecurityUtils.getShiroUser().getHasTblMemberDataControls();
		/*
		 * ======================================
		 * 实际上只有连锁用户（会员）才需要这个数据权限的过滤
		 * ======================================
		 */
//		if(SecurityUtils.getLoginTblMemberUser() != null) {
//			if(mdcs != null && !mdcs.isEmpty()) {
//				Collection<TblMemberDataControl> set = mdcs.values();
//				String fv = null;
//				SearchFilter sf = null;
//				for(TblMemberDataControl tmdc:set) {
//					fv = tmdc.getControl();
					/*
					 * ============================================================
					 * Member:id:EQ:1,2,3 —— 共四个参数必须配置，看以下注释
					 * 参数1：Member，建议为Entity Name，也可以自定义，不过配置和程序中的名称要一致即可
					 * 参数2：id，即行id，其实也可以是其他列名（column name）
					 * 参数3：EQ，即Operator中的操作符号
					 * 参数4：值列表，可以为多个，以英文逗号分隔
					 * =============================================================
					 */
//					dc = fv.split(":");
					/*
					 * ==============================================
					 * 配置的Member必须和下面这一行的member判断是否一致
					 * ==============================================
					 */
//					if(dc[0] != null && dc[0].equalsIgnoreCase("Member")) {
						/*
						 * ==========================================
						 * 如果含有都好，一边拿情况下应该是in的操作
						 * ==========================================
						 */
//						if(dc[1].equalsIgnoreCase("id")) {
//							if(dc[3].indexOf(",") != -1) { //一般情况下有“,”，应为in的操作
//								sf = SearchFilter.getSearchFilter(dc[1], dc[2], dc[3].split(","));
//							} else {
//								sf = SearchFilter.getSearchFilter(dc[1], dc[2], dc[3]);
//							}
//							if(sf != null) {
//								idfilters.add(sf);
//							}
//						}
//					}
//				}
//			}
//		}
		/*
		 * =====================================
		 * 如果数据权限过滤不为空，则最终以数据权限作为过滤条件
		 * =====================================
		 */
		if(idfilters != null && !idfilters.isEmpty()) {
			Collection<SearchFilter> filters = genSearchFilter(request);
			Set<SearchFilter> set = new HashSet<SearchFilter>(filters);
			SearchFilter[] sfs = idfilters.toArray(new SearchFilter[]{});
			for (SearchFilter searchFilter : sfs) {
				set.add(searchFilter);
			}
			return DynamicSpecifications.bySearchFilter(entityClazz, set);
		}
		return bySearchFilter(request, entityClazz, searchFilters.toArray(new SearchFilter[]{}));
	}
	
	public static <T> Specification<T> bySearchFilterWithoutRequest(final Class<T> entityClazz, final Collection<SearchFilter> searchFilters) {
		/*
		 * =======================
		 * 定义过滤条件列表
		 * =======================
		 */
		Collection<SearchFilter> idfilters = new HashSet<SearchFilter>();
		//String[] dc = null;
		/*
		 * =======================
		 * 从登录用户中获取到授权的数据权限
		 * =======================
		 */
//		Map<String, TblMemberDataControl> mdcs = SecurityUtils.getShiroUser().getHasTblMemberDataControls();
		/*
		 * ======================================
		 * 实际上只有连锁用户（会员）才需要这个数据权限的过滤
		 * ======================================
		 */
//		if(SecurityUtils.getLoginTblMemberUser() != null) {
//			if(mdcs != null && !mdcs.isEmpty()) {
//				Collection<TblMemberDataControl> set = mdcs.values();
//				String fv = null;
//				SearchFilter sf = null;
//				for(TblMemberDataControl tmdc:set) {
//					fv = tmdc.getControl();
					/*
					 * ============================================================
					 * Member:id:EQ:1,2,3 —— 共四个参数必须配置，看以下注释
					 * 参数1：Member，建议为Entity Name，也可以自定义，不过配置和程序中的名称要一致即可
					 * 参数2：id，即行id，其实也可以是其他列名（column name）
					 * 参数3：EQ，即Operator中的操作符号
					 * 参数4：值列表，可以为多个，以英文逗号分隔
					 * =============================================================
					 */
//					dc = fv.split(":");
					/*
					 * ==============================================
					 * 配置的Member必须和下面这一行的member判断是否一致
					 * ==============================================
					 */
//					if(dc[0] != null && dc[0].equalsIgnoreCase("Member")) {
						/*
						 * ==========================================
						 * 如果含有都好，一边拿情况下应该是in的操作
						 * ==========================================
						 */
//						if(dc[1].equalsIgnoreCase("id")) {
//							if(dc[3].indexOf(",") != -1) { //一般情况下有“,”，应为in的操作
//								sf = SearchFilter.getSearchFilter(dc[1], dc[2], dc[3].split(","));
//							} else {
//								sf = SearchFilter.getSearchFilter(dc[1], dc[2], dc[3]);
//							}
//							if(sf != null) {
//								idfilters.add(sf);
//							}
//						}
//					}
//				}
//			}
//		}
		/*
		 * =====================================
		 * 如果数据权限过滤不为空，则最终以数据权限作为过滤条件
		 * =====================================
		 */
		if(idfilters != null && !idfilters.isEmpty()) {
			return DynamicSpecifications.bySearchFilter(entityClazz, idfilters);
		}
		return bySearchFilterWithoutRequest(entityClazz, searchFilters.toArray(new SearchFilter[]{}));
	}
	
	public static <T> Specification<T> bySearchFilter(ServletRequest request, final Class<T> entityClazz, final SearchFilter...searchFilters) {
		/*
		 * =======================
		 * 定义过滤条件列表
		 * =======================
		 */
		Collection<SearchFilter> idfilters = new HashSet<SearchFilter>();
		//String[] dc = null;
		/*
		 * =======================
		 * 从登录用户中获取到授权的数据权限
		 * =======================
		 */
//		Map<String, TblMemberDataControl> mdcs = SecurityUtils.getShiroUser().getHasTblMemberDataControls();
		/*
		 * ======================================
		 * 实际上只有连锁用户（会员）才需要这个数据权限的过滤
		 * ======================================
		 */
//		if(SecurityUtils.getLoginTblMemberUser() != null) {
//			if(mdcs != null && !mdcs.isEmpty()) {
//				Collection<TblMemberDataControl> set = mdcs.values();
//				String fv = null;
//				SearchFilter sf = null;
//				for(TblMemberDataControl tmdc:set) {
//					fv = tmdc.getControl();
					/*
					 * ============================================================
					 * Member:id:EQ:1,2,3 —— 共四个参数必须配置，看以下注释
					 * 参数1：Member，建议为Entity Name，也可以自定义，不过配置和程序中的名称要一致即可
					 * 参数2：id，即行id，其实也可以是其他列名（column name）
					 * 参数3：EQ，即Operator中的操作符号
					 * 参数4：值列表，可以为多个，以英文逗号分隔
					 * =============================================================
					 */
//					dc = fv.split(":");
					/*
					 * ==============================================
					 * 配置的Member必须和下面这一行的member判断是否一致
					 * ==============================================
					 */
//					if(dc[0] != null && dc[0].equalsIgnoreCase("Member")) {
						/*
						 * ==========================================
						 * 如果含有都好，一边拿情况下应该是in的操作
						 * ==========================================
						 */
//						if(dc[1].equalsIgnoreCase("id")) {
//							if(dc[3].indexOf(",") != -1) { //一般情况下有“,”，应为in的操作
//								sf = SearchFilter.getSearchFilter(dc[1], dc[2], dc[3].split(","));
//							} else {
//								sf = SearchFilter.getSearchFilter(dc[1], dc[2], dc[3]);
//							}
//							if(sf != null) {
//								idfilters.add(sf);
//							}
//						}
//					}
//				}
//			}
//		}
		/*
		 * =====================================
		 * 如果数据权限过滤不为空，则最终以数据权限作为过滤条件
		 * =====================================
		 */
		if(idfilters != null && !idfilters.isEmpty()) {
			Collection<SearchFilter> filters = genSearchFilter(request);
			Set<SearchFilter> set = new HashSet<SearchFilter>(filters);
			SearchFilter[] sfs = idfilters.toArray(new SearchFilter[]{});
			for (SearchFilter searchFilter : sfs) {
				set.add(searchFilter);
			}
			return DynamicSpecifications.bySearchFilter(entityClazz, set);
		}
		/*
		 * 再处理其他情况
		 */
		Collection<SearchFilter> filters = genSearchFilter(request);
		Set<SearchFilter> set = new HashSet<SearchFilter>(filters);
		for (SearchFilter searchFilter : searchFilters) {
			set.add(searchFilter);
		}
		return bySearchFilter(entityClazz, set);
	}
	
	public static <T> Specification<T> bySearchFilterWithoutRequest(final Class<T> entityClazz, final SearchFilter...searchFilters) {
		/*
		 * =======================
		 * 定义过滤条件列表
		 * =======================
		 */
		Collection<SearchFilter> idfilters = new HashSet<SearchFilter>();
		//String[] dc = null;
		/*
		 * =======================
		 * 从登录用户中获取到授权的数据权限
		 * =======================
		 */
//		Map<String, TblMemberDataControl> mdcs = SecurityUtils.getShiroUser().getHasTblMemberDataControls();
		/*
		 * ======================================
		 * 实际上只有连锁用户（会员）才需要这个数据权限的过滤
		 * ======================================
		 */
//		if(SecurityUtils.getLoginTblMemberUser() != null) {
//			if(mdcs != null && !mdcs.isEmpty()) {
//				Collection<TblMemberDataControl> set = mdcs.values();
//				String fv = null;
//				SearchFilter sf = null;
//				for(TblMemberDataControl tmdc:set) {
//					fv = tmdc.getControl();
					/*
					 * ============================================================
					 * Member:id:EQ:1,2,3 —— 共四个参数必须配置，看以下注释
					 * 参数1：Member，建议为Entity Name，也可以自定义，不过配置和程序中的名称要一致即可
					 * 参数2：id，即行id，其实也可以是其他列名（column name）
					 * 参数3：EQ，即Operator中的操作符号
					 * 参数4：值列表，可以为多个，以英文逗号分隔
					 * =============================================================
					 */
//					dc = fv.split(":");
					/*
					 * ==============================================
					 * 配置的Member必须和下面这一行的member判断是否一致
					 * ==============================================
					 */
//					if(dc[0] != null && dc[0].equalsIgnoreCase("Member")) {
						/*
						 * ==========================================
						 * 如果含有都好，一边拿情况下应该是in的操作
						 * ==========================================
						 */
//						if(dc[1].equalsIgnoreCase("id")) {
//							if(dc[3].indexOf(",") != -1) { //一般情况下有“,”，应为in的操作
//								sf = SearchFilter.getSearchFilter(dc[1], dc[2], dc[3].split(","));
//							} else {
//								sf = SearchFilter.getSearchFilter(dc[1], dc[2], dc[3]);
//							}
//							if(sf != null) {
//								idfilters.add(sf);
//							}
//						}
//					}
//				}
//			}
//		}
		/*
		 * =====================================
		 * 如果数据权限过滤不为空，则最终以数据权限作为过滤条件
		 * =====================================
		 */
		if(idfilters != null && !idfilters.isEmpty()) {
			return DynamicSpecifications.bySearchFilter(entityClazz, idfilters);
		}
		Set<SearchFilter> set = new HashSet<SearchFilter>();
		for (SearchFilter searchFilter : searchFilters) {
			set.add(searchFilter);
		}
		return bySearchFilter(entityClazz, set);
	}

	@SuppressWarnings("unchecked")
	public static <T> Specification<T> bySearchFilter(final Class<T> entityClazz, final Collection<SearchFilter> searchFilters) {
		final Set<SearchFilter> filterSet = new HashSet<SearchFilter>();
		ServletRequest request = getRequest();
		if (request != null) {
			// 数据权限中的filter
			Collection<SearchFilter> nestFilters = 
					(Collection<SearchFilter>)request.getAttribute(SecurityConstants.NEST_DYNAMIC_SEARCH);
			if (nestFilters != null && !nestFilters.isEmpty()) {
				for (SearchFilter searchFilter : nestFilters) {
					filterSet.add(searchFilter);
				}
			}
		}
		
		// 自定义
		for (SearchFilter searchFilter : searchFilters) {
			filterSet.add(searchFilter);
		}
		
		return new Specification<T>() {
			@SuppressWarnings({ "rawtypes"})
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				if (filterSet != null && !filterSet.isEmpty()) {
					List<Predicate> predicates = new ArrayList<Predicate>();
					for (SearchFilter filter : filterSet) {
						// nested path translate, 如Task的名为"user.name"的filedName, 转换为Task.user.name属性
						String[] names = StringUtils.split(filter.getFieldName(), ".");
						Path expression = root.get(names[0]);
						for (int i = 1; i < names.length; i++) {
							expression = expression.get(names[i]);
						}

						// 自动进行enum和date的转换。
						Class clazz = expression.getJavaType();
						if (Date.class.isAssignableFrom(clazz) && !filter.getValue().getClass().equals(clazz)) {
							filter.setValue(convert2Date((String)filter.getValue()));
						} else if (Enum.class.isAssignableFrom(clazz) && !filter.getValue().getClass().equals(clazz)) {
							filter.setValue(convert2Enum(clazz, (String)filter.getValue()));
						}
						
						// logic operator
						switch (filter.getOperator()) {
						case EQ:
							predicates.add(builder.equal(expression, filter.getValue()));
							break;
						case LIKE:
							predicates.add(builder.like(expression, "%" + filter.getValue() + "%"));
							break;
						case GT:
							predicates.add(builder.greaterThan(expression, (Comparable) filter.getValue()));
							break;
						case LT:
							predicates.add(builder.lessThan(expression, (Comparable) filter.getValue()));
							break;
						case GTE:
							predicates.add(builder.greaterThanOrEqualTo(expression, (Comparable) filter.getValue()));
							break;
						case LTE:
							predicates.add(builder.lessThanOrEqualTo(expression, (Comparable) filter.getValue()));
							break;
						case IN:
							predicates.add(builder.and(expression.in((Object[])filter.getValue())));
							break;
						case NEQ:
							predicates.add(builder.notEqual(expression, filter.getValue()));
							break;
						case ISNULL:
							predicates.add(builder.isNull(expression));
							break;
						case NOTNULL:
							predicates.add(builder.isNotNull(expression));
							break;
						}
					}

					// 将所有条件用 and 联合起来
					if (predicates.size() > 0) {
						return builder.and(predicates.toArray(new Predicate[predicates.size()]));
					}
				}

				return builder.conjunction();
			}
		};
	}
	
	private static Date convert2Date(String dateString) {
		SimpleDateFormat sFormat = new SimpleDateFormat(SHORT_DATE);
		try {
			return sFormat.parse(dateString);
		} catch (ParseException e) {
			try {
				return sFormat.parse(LONG_DATE);
			} catch (ParseException e1) {
				try {
					return sFormat.parse(TIME);
				} catch (ParseException e2) {
					logger.error("Convert time is error! The dateString is " + dateString + "." + Exceptions.getStackTraceAsString(e2));
				}
			}
		}

		return null;
	}
		
	
	private static <E extends Enum<E>> E convert2Enum(Class<E> enumClass, String enumString) {
		return EnumUtils.getEnum(enumClass, enumString);
	}
}
