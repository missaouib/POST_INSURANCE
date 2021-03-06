/**
 * 
 */
package	com.gdpost.web.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdpost.web.dao.OrganizationDAO;
import com.gdpost.web.dao.UserDAO;
import com.gdpost.web.entity.main.Organization;
import com.gdpost.web.entity.main.User;
import com.gdpost.web.exception.NotDeletedException;
import com.gdpost.web.exception.NotExistedException;
import com.gdpost.web.service.OrganizationService;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.dwz.PageUtils;
import com.gdpost.web.util.persistence.DynamicSpecifications;
import com.gdpost.web.util.persistence.SearchFilter;
import com.gdpost.web.util.persistence.SearchFilter.Operator;

@Service
@Transactional
public class OrganizationServiceImpl implements OrganizationService {
	private static final Logger LOG = LoggerFactory.getLogger(OrganizationServiceImpl.class);
	
	@Autowired
	private OrganizationDAO organizationDAO;
	
	@Autowired
	private UserDAO userDAO;

	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.OrganizationService#get(java.lang.Long)  
	 */ 
	@Override
	public Organization get(Long id) {
		return organizationDAO.findById(id).get();
	}
	
	/* (non-Javadoc)
	 * @see com.gdpost.web.service.OrganizationService#getByName(java.lang.String)
	 */
	@Override
	public Organization getByName(String name) {
		return organizationDAO.getByName(name);
	}
	
	@Override
	public Organization getByOrgCode(String orgCode) {
		return organizationDAO.getByOrgCode(orgCode);
	}

	/*
	 * (non-Javadoc) 
	 * @see com.gdpost.web.service.OrganizationService#saveOrUpdate(com.gdpost.web.entity.main.Organization)  
	 */
	@Override
	public void saveOrUpdate(Organization organization) {
		if (organization.getId() == null) {
			Organization parentOrganization = organizationDAO.findById(organization.getParent().getId()).get();
			if (parentOrganization == null) {
				throw new NotExistedException("id=" + organization.getParent().getId() + "?????????????????????");
			}
			organization.setLevel(parentOrganization.getLevel() + 1);
			if (organizationDAO.getByName(organization.getName()) != null) {
				throw new NotExistedException(organization.getName() + "????????????");
			}
		}
		
		organizationDAO.save(organization);
	}

	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.OrganizationService#delete(java.lang.Long)  
	 */
	@Override
	public void delete(Long id) {
		if (isRoot(id)) {
			throw new NotDeletedException("???????????????????????????");
		}
		
		Organization organization = this.get(id);
		
		//???????????????????????????????????????????????????????????????????????????
		if(organization.getChildren().size() > 0){
			throw new NotDeletedException(organization.getName() + "?????????????????????????????????????????????");
		}
		
		if (userDAO.findByOrganizationId(id).size() > 0) {
			throw new NotDeletedException(organization.getName() + "??????????????????????????????????????????");
		}
		
		organizationDAO.delete(organization);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.OrganizationService#findAll(com.gdpost.web.util.dwz.Page)  
	 */
	@Override
	public List<Organization> findAll(Page page) {
		org.springframework.data.domain.Page<Organization> springDataPage = organizationDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.OrganizationService#findByExample(org.springframework.data.jpa.domain.Specification, com.gdpost.web.util.dwz.Page)	
	 */
	@Override
	public List<Organization> findByExample(
			Specification<Organization> specification, Page page) {
		org.springframework.data.domain.Page<Organization> springDataPage = organizationDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	/* (non-Javadoc)
	 * @see com.gdpost.web.service.OrganizationService#getTree()
	 */
	@Override
	public Organization getTree() {
		List<Organization> list = organizationDAO.findAllWithCache();
		
		List<Organization> rootList = makeTree(list);
				
		return rootList.get(0);
	}
	
	@Override
	public Organization getTree(User user) {
		LOG.debug(" ---------------- user org id: " + user.getOrganization().getId());
		//List<Organization> list = organizationDAO.findAllByOrgIdWithCache(user.getOrganization().getId());
		List<Organization> list = organizationDAO.findAllWithCache();
		//List<Organization> list = organizationDAO.findByIdOrParent(user.getOrganization().getId(), user.getOrganization().getParent());
		//List<TblMember> rootList = makeTree(list, id);
		List<Organization> rootList = makeTree(list, user);
		LOG.debug("---------" + rootList);
		Organization root = rootList.get(0);
		
		return root;
		/*
		List<Organization> children = root.getChildren();
		LOG.debug("--------222 children size: " + children.size());
		for(Organization rst : children) {
			LOG.debug("---------------rst id-" + rst.getId());
			if(rst.getId().intValue() == user.getOrganization().getId().intValue()) {
				return rst;
			}
			LOG.debug("------------------333" + rst.getName());
			children = rst.getChildren();
			LOG.debug("------------------children size: " + children.size());
		}
		
		return null;*/
	}
	
	/**
	 * ????????????????????????.
	 */
	private boolean isRoot(Long id) {
		return id == 1;
	}
	
	private List<Organization> makeTree(List<Organization> list) {
		List<Organization> parent = new ArrayList<Organization>();
		// get parentId = null;
		for (Organization e : list) {
			if (e.getParent() == null) {
				e.setChildren(new ArrayList<Organization>(0));
				parent.add(e);
			}
		}
		// ??????parentId = null;
		list.removeAll(parent);
		
		makeChildren(parent, list);
		
		return parent;
	}
	
	private List<Organization> makeTree(List<Organization> list, User user) {
		List<Organization> parent = new ArrayList<Organization>();
		List<Organization> tmp = new ArrayList<Organization>();
		// get parentId = null;
		LOG.debug(" ----------------- LIST size: " + list.size());
		list.remove(user.getOrganization().getParent());
		for (Organization e : list) {
			LOG.debug(" ----------------- LIST: " + e.getName());
			if (e.getId() == user.getOrganization().getId()) {
				e.setChildren(new ArrayList<Organization>(0));
				parent.add(e);
			} else {
				if(e.getParent() == null) {
					tmp.add(e);
				} else if(e.getParent().getId() == user.getOrganization().getParent().getId()) {
					tmp.add(e);
				}
			}
			
		}
		// ??????parentId = null;
		//list.remove(index)
		list.removeAll(parent);
		list.removeAll(tmp);
		
		makeChildren(parent, list);
		
		return parent;
	}
	
	private void makeChildren(List<Organization> parent, List<Organization> children) {
		if (children.isEmpty() || parent.isEmpty()) {
			return ;
		}
		
		List<Organization> tmp = new ArrayList<Organization>();
		for (Organization c1 : parent) {
			for (Organization c2 : children) {
				if(c2.getParent() == null) {
					continue;
				}
				c2.setChildren(new ArrayList<Organization>(0));
				if (c1.getId().equals(c2.getParent().getId())) {
					c1.getChildren().add(c2);
					tmp.add(c2);
				}
			}
		}
		
		if(tmp.isEmpty()) {
			return;
		}
		
		children.removeAll(tmp);
		
		makeChildren(tmp, children);
	}

	@Override
	public List<TreeMap<String, String>> getOrgCodeAndNameMap(String orgCode) {
		List<TreeMap<String, String>> rst = new ArrayList<TreeMap<String, String>>();
		Page page = new Page();
		page.setNumPerPage(500);
		page.setOrderField("orgCode");
		page.setOrderDirection("ASC");
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("orgCode", Operator.LIKE, orgCode));
		
		Specification<Organization> specification = DynamicSpecifications.bySearchFilter(null, csf);
		
		List<Organization> list = findByExample(specification, page);
		TreeMap<String, String> citymap = new TreeMap<String, String>();
		for(Organization org : list) {
			if(org.getOrgCode().length()==6) {
				citymap.put(org.getOrgCode(), org.getShortName().contains("?????????")?"?????????":org.getShortName().substring(0, 2));
			}
		}
		TreeMap<String, String> areamap = new TreeMap<String, String>();
		for(Organization org : list) {
			if(org.getOrgCode().length()==8) {
				areamap.put(org.getOrgCode(), org.getShortName());
			}
		}
		rst.add(citymap);
		rst.add(areamap);
		return rst;
	}
}
