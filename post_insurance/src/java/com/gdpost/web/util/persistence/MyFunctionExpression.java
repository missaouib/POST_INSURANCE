package com.gdpost.web.util.persistence;

import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;
import org.hibernate.query.criteria.internal.compile.RenderingContext;
import org.hibernate.query.criteria.internal.expression.function.BasicFunctionExpression;

@SuppressWarnings("rawtypes")
public class MyFunctionExpression extends BasicFunctionExpression {
    /**
	 * 
	 */
	private static final long serialVersionUID = -4027405368157332154L;

	@SuppressWarnings("unchecked")
	public MyFunctionExpression(CriteriaBuilderImpl criteriaBuilder, Class javaType, String functionName) {
        super(criteriaBuilder, javaType, functionName);
    }

    @Override
    public String render(RenderingContext renderingContext) {
        return getFunctionName();
    }

}