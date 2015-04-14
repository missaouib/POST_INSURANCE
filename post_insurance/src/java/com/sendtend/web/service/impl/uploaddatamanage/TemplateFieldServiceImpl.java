package com.sendtend.web.service.impl.uploaddatamanage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sendtend.web.dao.member.ITblMemberDataTemplateFieldDAO;
import com.sendtend.web.entity.member.TblMemberDataTemplateField;
import com.sendtend.web.service.uploaddatamanage.TemplateFieldService;
import com.sendtend.web.util.dwz.Page;
import com.sendtend.web.util.dwz.PageUtils;

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
