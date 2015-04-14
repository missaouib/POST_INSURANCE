package com.sendtend.web.util.persistence;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

/**
 * 动态查询对象
 * @author 	sendtend
 * @since   2013年10月31日 上午11:30:53
 */
public class SearchFilter {

	public enum Operator {
		EQ, LIKE, GT, LT, GTE, LTE, IN, NEQ, ISNULL, NOTNULL
	}

	private String fieldName;
	private Object value;
	private Operator operator;

	public SearchFilter(String fieldName, Operator operator, Object value) {
		this.fieldName = fieldName;
		this.value = value;
		this.operator = operator;
	}

	/**
	 * searchParams中key的格式为OPERATOR_FIELDNAME
	 */
	public static Map<String, SearchFilter> parse(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();

		for (Entry<String, Object> entry : searchParams.entrySet()) {
			// 过滤掉空值
			String key = entry.getKey();
			Object value = entry.getValue();
			if (StringUtils.isBlank((String) value)) {
				continue;
			}

			// 拆分operator与filedAttribute
			String[] names = StringUtils.split(key, "_");
			if (names.length != 2) {
				throw new IllegalArgumentException(key + " is not a valid search filter name");
			}
			String filedName = names[1];
			Operator operator = Operator.valueOf(names[0]);

			// 创建searchFilter
			SearchFilter filter = new SearchFilter(filedName, operator, value);
			filters.put(key, filter);
		}

		return filters;
	}

	/**
	 * @return the fieldName
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * @param fieldName the fieldName to set
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * @return the operator
	 */
	public Operator getOperator() {
		return operator;
	}

	/**
	 * @param operator the operator to set
	 */
	public void setOperator(Operator operator) {
		this.operator = operator;
	}
	
	public static SearchFilter getSearchFilter(String field, String OP, Object value) {
		if(field == null || OP == null || value ==null) {
			return null;
		}
		if(OP.equalsIgnoreCase("eq")) {
			return new SearchFilter(field, SearchFilter.Operator.EQ, value);
		} else if(OP.equalsIgnoreCase("neq")) {
			return new SearchFilter(field, Operator.NEQ, value);
		} else if(OP.equalsIgnoreCase("gt")) {
			return new SearchFilter(field, Operator.GT, value);
		} else if(OP.equalsIgnoreCase("gte")) {
			return new SearchFilter(field, Operator.GTE, value);
		} else if(OP.equalsIgnoreCase("in")) {
			return new SearchFilter(field, Operator.IN, value);
		} else if(OP.equalsIgnoreCase("like")) {
			return new SearchFilter(field, Operator.LIKE, value);
		} else if(OP.equalsIgnoreCase("lt")) {
			return new SearchFilter(field, Operator.LT, value);
		} else if(OP.equalsIgnoreCase("lte")) {
			return new SearchFilter(field, Operator.LTE, value);
		} else if(OP.equalsIgnoreCase("isnull")) {
			return new SearchFilter(field, Operator.ISNULL, value);
		} else if(OP.equalsIgnoreCase("notnull")) {
			return new SearchFilter(field, Operator.NOTNULL, value);
		}
		
		return null;
	}
}