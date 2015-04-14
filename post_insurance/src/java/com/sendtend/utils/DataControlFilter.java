package com.sendtend.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.ss.formula.functions.T;

import com.sendtend.web.entity.member.TblMemberDataControl;
import com.sendtend.web.entity.member.TblMemberUser;

public class DataControlFilter {
	
	@SuppressWarnings("hiding")
	public static <T> List<T> filterBeanList(List<T> list, String filterEntityName) {
		if(filterEntityName == null || filterEntityName.trim().length()<=0) {
			return list;
		}
		Map<String, TblMemberDataControl> mdcs = SecurityUtils.getShiroUser().getHasTblMemberDataControls();
		/*
		 * =======================================
		 * 查询完对列进行列数据过滤
		 * mdcs为之前获取到的当前用户的数据权限
		 * =======================================
		 */
		String[] dc = null;
		if(mdcs != null && !mdcs.isEmpty()) {
			Collection<TblMemberDataControl> set = mdcs.values();
			String fv = null;
			for(TblMemberDataControl tmdc:set) {
				fv = tmdc.getControl();
				/*
				 * =======================
				 * Member:column:EQ:1,2,3
				 * 一样的配置，只是id改为column了
				 * =======================
				 */
				dc = fv.split(":");
				if(dc[0] != null && dc[0].equalsIgnoreCase(filterEntityName)) {
					/*
					 * 如果含有都好，一边拿情况下应该是in的操作
					 */
					if(dc[1].equalsIgnoreCase("column")) {
						/*
						 * =========================================
						 * 以下方法针对bean的属性过滤，也可以通过过滤生成json的数据
						 * 具体可以看看DataControlFilter所具有的方法
						 * =========================================
						 */
						list = DataControlFilter.toColumnsFilterBeanList(list, dc[2], dc[3].split(","));
					}
				}
			}
		}
		return list;
	}

	/**
	 * 
	 * @param list
	 * @param filter
	 * @return
	 */
	@SuppressWarnings("hiding")
	public static <T> List<?> mapListWithFilter(List<Map<String, Object>> list, Map<?, ?> filter) {
    	if(filter == null || filter.isEmpty()) {
    		return list;
    	}
    	Map<String, Object> map = null;
    	Iterator<?> itera = null;
    	String key = null;
    	boolean isIt = true;
    	@SuppressWarnings("rawtypes")
		List<Map> returnList = new ArrayList<Map>();
    	for(int i=0; list != null && i<list.size(); i++) {
    		map = (Map<String, Object>) list.get(i);
    		itera = filter.keySet().iterator();
    		while(itera.hasNext()) {
    			key = (String)itera.next();
    			if(!map.containsKey(key) || (map.containsKey(key) && (map.get(key) == null || map.get(key).equals(filter.get(key))))) {
    				isIt = true;
    			} else {
    				isIt = false;
    			}
    		}
    		if(!isIt) {
				returnList.add(map);
			}
    	}
    	return returnList;
    }
	
	/**
	 * 
	 * @param list
	 * @param columns
	 * @return
	 */
	@SuppressWarnings("hiding")
	public static <T> List<?> mapListWithFilter(List<Map<String, Object>> list, String[] columns) {
		if(list == null || list.isEmpty() || columns == null || columns.length<=0) {
    		return list;
    	}
    	Map<String, Object> map = null;
    	boolean isIt = true;
    	@SuppressWarnings("rawtypes")
		List<Map> returnList = new ArrayList<Map>();
    	for(int i=0; list != null && i<list.size(); i++) {
    		map = (Map<String, Object>) list.get(i);
    		for(String c:columns) {
    			if(!map.containsKey(c)) {
    				isIt = true;
    			} else {
    				isIt = false;
    			}
    		}
    		if(!isIt) {
				returnList.add(map);
			}
    	}
    	return returnList;
    }
	
	/**
	 * 
	 * @param list
	 * @param filter
	 * @return
	 */
	@SuppressWarnings("hiding")
	public static <T> List<?> mapListWithoutFilter(List<Map<String, Object>> list, Map<?, ?> filter) {
    	if(filter == null || filter.isEmpty()) {
    		return list;
    	}
    	Map<String, Object> map = null;
    	Iterator<?> itera = null;
    	String key = null;
    	boolean isIt = true;
    	@SuppressWarnings("rawtypes")
		List<Map> returnList = new ArrayList<Map>();
    	for(int i=0; list != null && i<list.size(); i++) {
    		map = (Map<String, Object>) list.get(i);
    		itera = filter.keySet().iterator();
    		while(itera.hasNext()) {
    			key = (String)itera.next();
    			if(!map.containsKey(key) || (map.containsKey(key) && (map.get(key) == null || map.get(key).equals(filter.get(key))))) {
    				isIt = true;
    			} else {
    				isIt = false;
    			}
    		}
    		if(isIt) {
				returnList.add(map);
			}
    	}
    	return returnList;
    }
	
	/**
	 * 
	 * @param list
	 * @param columns
	 * @return
	 */
	@SuppressWarnings("hiding")
	public static <T> List<?> mapListWithoutFilter(List<Map<String, Object>> list, String[] columns) {
    	if(list == null || list.isEmpty() || columns == null || columns.length<=0) {
    		return list;
    	}
    	Map<String, Object> map = null;
    	boolean isIt = true;
    	@SuppressWarnings("rawtypes")
		List<Map> returnList = new ArrayList<Map>();
    	for(int i=0; list != null && i<list.size(); i++) {
    		map = (Map<String, Object>) list.get(i);
    		for(String c:columns) {
    			if(!map.containsKey(c)) {
    				isIt = true;
    			} else {
    				isIt = false;
    			}
    		}
    		if(isIt) {
				returnList.add(map);
			}
    	}
    	return returnList;
    }
	
	/**
	 * 
	 * @param list
	 * @param columns
	 * @return
	 */
	@SuppressWarnings("hiding")
	public static <T> List<T> beanListWithoutFilter(List<T> list, String[] columns) {
		if(list == null || list.isEmpty() || columns == null || columns.length<=0) {
			return list;
		}
		for(Object T: list) {
			try {
				Map<?, ?> map = BeanUtils.describe(T);
				for(String c:columns) {
					if(map.containsKey(c)) {
						BeanUtils.setProperty(T, c, null);
					}
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	/**
	 * 
	 * @param list
	 * @param columns
	 * @return
	 */
	@SuppressWarnings("hiding")
	public static <T> List<T> beanListWithFilter(List<T> list, String[] columns) {
		if(list == null || list.isEmpty() || columns == null || columns.length<=0) {
			return list;
		}
		boolean isIt = false;
		for(Object T: list) {
			try {
				Map<?, ?> map = BeanUtils.describe(T);
				@SuppressWarnings("unchecked")
				Set<String> set = (Set<String>) map.keySet();
				for(String key:set) {
					for(String c:columns) {
						if(map.containsKey(c)) {
							if(key.equalsIgnoreCase(c)) {
								isIt = true;
								break;
							}
						}
					}
					if(!isIt) {
						BeanUtils.setProperty(T, key, null);
					}
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	/**
	 * 
	 * @param list
	 * @param operater
	 * @param columns
	 * @return
	 */
	@SuppressWarnings("hiding")
	public static <T> List<T> toColumnsFilterBeanList(List<T> list, String operater, String[] columns) {
		if(list == null || list.isEmpty() || operater == null || operater.length() < 2 || columns == null || columns.length <= 0) {
			return list;
		}
		if(operater.equalsIgnoreCase("eq") || operater.equalsIgnoreCase("in") || operater.equalsIgnoreCase("like")) {
			return beanListWithoutFilter(list, columns);
		} else if(operater.equalsIgnoreCase("neq")) {
			return beanListWithFilter(list, columns);
		}
		return list;
	}
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		TblMemberUser m = new TblMemberUser();
		m.setId(new Long(1));
		m.setUserName("----test111-----");
		List<TblMemberUser> list = new ArrayList<TblMemberUser>();
		list.add(m);
		System.out.println(list);
		String[] c = new String[]{"semail","realName"};
		list = DataControlFilter.beanListWithFilter(list, c);
		System.out.println(list);
		
	}
}
