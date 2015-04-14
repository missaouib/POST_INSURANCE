/**
 * 
 */
package	com.sendtend.web.service.impl.member;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sendtend.web.dao.member.MModuleDAO;
import com.sendtend.web.entity.member.TblMemberModule;
import com.sendtend.web.exception.ExistedException;
import com.sendtend.web.exception.NotDeletedException;
import com.sendtend.web.service.member.MModuleService;
import com.sendtend.web.util.dwz.Page;
import com.sendtend.web.util.dwz.PageUtils;

@Service
@Transactional
public class MModuleServiceImpl implements MModuleService {
	
	@Autowired
	private MModuleDAO moduleDAO;

	/*
	 * (non-Javadoc)
	 * @see com.sendtend.web.service.ModuleService#get(java.lang.Long)  
	 */ 
	@Override
	public TblMemberModule get(Long id) {
		return moduleDAO.findOne(id);
	}

	/*
	 * (non-Javadoc) 
	 * @see com.sendtend.web.service.ModuleService#saveOrUpdate(com.sendtend.web.entity.main.Module)  
	 */
	@Override
	public void saveOrUpdate(TblMemberModule module) {
		if (module.getId() == null) {
			if (moduleDAO.getBySn(module.getSn()) != null) {
				throw new ExistedException("已存在sn=" + module.getSn() + "的模块。");
			}
		}

		moduleDAO.save(module);
	}

	/*
	 * (non-Javadoc)
	 * @see com.sendtend.web.service.ModuleService#delete(java.lang.Long)  
	 */
	@Override
	public void delete(Long id) {
		if (isRoot(id)) {
			throw new NotDeletedException("不允许删除根模块。");
		}
		
		TblMemberModule module = this.get(id);
		
		//先判断是否存在子模块，如果存在子模块，则不允许删除
		if(module.getChildren().size() > 0){
			throw new NotDeletedException(module.getName() + "模块下存在子模块，不允许删除。");
		}
		
		moduleDAO.delete(module);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.sendtend.web.service.ModuleService#findAll(com.sendtend.web.util.dwz.Page)  
	 */
	@Override
	public List<TblMemberModule> findAll(Page page) {
		org.springframework.data.domain.Page<TblMemberModule> springDataPage = moduleDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.sendtend.web.service.ModuleService#findByExample(org.springframework.data.jpa.domain.Specification, com.sendtend.web.util.dwz.Page)	
	 */
	@Override
	public List<TblMemberModule> findByExample(
			Specification<TblMemberModule> specification, Page page) {
		org.springframework.data.domain.Page<TblMemberModule> springDataPage = moduleDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	/* (non-Javadoc)
	 * @see com.sendtend.web.service.ModuleService#findAll()
	 */
	@Override
	public List<TblMemberModule> findAll() {
		return moduleDAO.findAll();
	}
	
	/**
	 * 判断是否是根模块.
	 */
	private boolean isRoot(Long id) {
		return id == 1;
	}

	/**
	 * 
	 * @return  
	 * @see com.sendtend.web.service.member.ModuleService#getTree()
	 */
	public TblMemberModule getTree() {
		List<TblMemberModule> list = moduleDAO.findAllWithCache();
		
		List<TblMemberModule> rootList = makeTree(list);
				
		return rootList.get(0);
	}

	private List<TblMemberModule> makeTree(List<TblMemberModule> list) {
		List<TblMemberModule> parent = new ArrayList<TblMemberModule>();
		// get parentId = null;
		for (TblMemberModule e : list) {
			if (e.getParent() == null) {
				e.setChildren(new ArrayList<TblMemberModule>(0));
				parent.add(e);
			}
		}
		// 删除parentId = null;
		list.removeAll(parent);
		
		makeChildren(parent, list);
		
		return parent;
	}
	
	private void makeChildren(List<TblMemberModule> parent, List<TblMemberModule> children) {
		if (children.isEmpty()) {
			return ;
		}
		
		List<TblMemberModule> tmp = new ArrayList<TblMemberModule>();
		for (TblMemberModule c1 : parent) {
			for (TblMemberModule c2 : children) {
				c2.setChildren(new ArrayList<TblMemberModule>(0));
				if (c1.getId().equals(c2.getParent().getId())) {
					c1.getChildren().add(c2);
					tmp.add(c2);
				}
			}
		}
		
		children.removeAll(tmp);
		
		makeChildren(tmp, children);
	}
}
