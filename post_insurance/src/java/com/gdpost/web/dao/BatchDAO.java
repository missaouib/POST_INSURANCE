package com.gdpost.web.dao;

import java.util.List;

import org.apache.poi.ss.formula.functions.T;

public interface BatchDAO {
	public void batchInsert(List<T> list);

	public void batchUpdate(List<T> list);
}
