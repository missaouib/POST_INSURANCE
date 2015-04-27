package com.gdpost.web.service.impl.uploaddatamanage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdpost.web.dao.member.ITblMemberDataTemplateFieldDAO;
import com.gdpost.web.entity.member.TblMemberDataTemplateField;
import com.gdpost.web.service.uploaddatamanage.TemplateFieldService;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.dwz.PageUtils;

@Service
@Transactional
public class TemplateFieldServiceImpl implements TemplateFieldService {

	@Autowired
	private ITblMemberDataTemplateFieldDAO fieldDAO;

	@Override
	public TblMemberDataTemplateField get(Long id) {
		return fieldDAO.findById(id);
	}

	@Override
	public TblMemberDataTemplateField saveOrUpdate(TblMemberDataTemplateField template) {
		return fieldDAO.save(template);
	}

	@Override
	public void delete(Long id) {
		fieldDAO.delete(id);
	}

	@Override
	public List<TblMemberDataTemplateField> findAll(
			Specification<TblMemberDataTemplateField> specification, Page page) {
		org.springframework.data.domain.Page<TblMemberDataTemplateField> springDataPage = fieldDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	@Override
	public List<TblMemberDataTemplateField> findAll(
			Specification<TblMemberDataTemplateField> specification, Sort sort) {
		return fieldDAO.findAll(specification, sort);
	}
	
	@Override
	public List<TblMemberDataTemplateField> getByTemplateId(Long id) {
		return fieldDAO.findByTblMemberDataTemplateId(id);
	}

}
