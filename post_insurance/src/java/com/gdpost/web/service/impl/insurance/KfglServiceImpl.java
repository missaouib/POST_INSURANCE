/**
 * 
 */
package	com.gdpost.web.service.impl.insurance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdpost.web.dao.InquireDAO;
import com.gdpost.web.dao.IssueDAO;
import com.gdpost.web.dao.RoleDAO;
import com.gdpost.web.dao.UserRoleDAO;
import com.gdpost.web.entity.insurance.Inquire;
import com.gdpost.web.entity.insurance.Issue;
import com.gdpost.web.entity.main.Organization;
import com.gdpost.web.entity.main.Role;
import com.gdpost.web.entity.main.User;
import com.gdpost.web.entity.main.UserRole;
import com.gdpost.web.exception.ExistedException;
import com.gdpost.web.service.insurance.KfglService;
import com.gdpost.web.util.StatusDefine.STATUS;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.dwz.PageUtils;
import com.gdpost.web.util.persistence.DynamicSpecifications;
import com.gdpost.web.util.persistence.SearchFilter;
import com.gdpost.web.util.persistence.SearchFilter.Operator;

@Service
@Transactional
public class KfglServiceImpl implements KfglService {
	//private static final Logger LOG = LoggerFactory.getLogger(KfglServiceImpl.class);
	
	@Autowired
	private IssueDAO issueDAO;
	
	@Autowired
	private InquireDAO inquireDAO;
	
	@Autowired
	private UserRoleDAO userRoleDAO;
	
	@Autowired
	private RoleDAO roleDAO;
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#get(java.lang.Long)  
	 */ 
	@Override
	public Issue get(Long id) {
		return issueDAO.findById(id).get();
	}

	/*
	 * (non-Javadoc) 
	 * @see com.gdpost.web.service.UserService#saveOrUpdate(com.gdpost.web.entity.main.Issue)  
	 */
	@Override
	public void saveOrUpdate(Issue policy) {
		if (policy.getId() == null) {
			if (issueDAO.getByIssueNo(policy.getIssueNo()) != null) {
				throw new ExistedException("工单号：" + policy.getIssueNo() + "已存在。");
			}
		}
		
		issueDAO.save(policy);
	}

	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#delete(java.lang.Long)  
	 */
	@Override
	public void delete(Long id) {
		issueDAO.deleteById(id);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#findAll(com.gdpost.web.util.dwz.Page)  
	 */
	@Override
	public List<Issue> findAll(Page page) {
		org.springframework.data.domain.Page<Issue> springDataPage = issueDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#findByExample(org.springframework.data.jpa.domain.Specification, com.gdpost.web.util.dwz.Page)	
	 */
	@Override
	public List<Issue> findByExample(
			Specification<Issue> specification, Page page) {
		org.springframework.data.domain.Page<Issue> springDataPage = issueDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/* (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#getByIssueNo(java.lang.String)
	 */
	@Override
	public Issue getByIssueNo(String issueNo) {
		return issueDAO.getByIssueNo(issueNo);
	}

	@Override
	public List<Issue> getTODOIssueList(User user) {
		Organization userOrg = user.getOrganization();
		Page page = new Page();
		page.setNumPerPage(5);
		page.setOrderField("shouldDate");
		page.setOrderDirection("DESC");
		//默认返回未处理工单
		Specification<Issue> specification = null;
		
		//如果是县区局登录的机构号为8位，需要根据保单的所在机构进行筛选
		if (user.getOrganization().getOrgCode().length() > 4) {
			specification = DynamicSpecifications.bySearchFilterWithoutRequest(Issue.class,
					new SearchFilter("status", Operator.EQ, STATUS.NewStatus.getDesc()),
					new SearchFilter("policy.organization.orgCode", Operator.LIKE_R, userOrg.getOrgCode()));
		} else {
			specification = DynamicSpecifications.bySearchFilterWithoutRequest(Issue.class,
					new SearchFilter("status", Operator.OR_EQ, STATUS.NewStatus.getDesc()),
					new SearchFilter("status", Operator.OR_EQ, STATUS.DealStatus.getDesc()),
					new SearchFilter("policy.organization.orgCode", Operator.LIKE_R, userOrg.getOrgCode()));
		}
		
		List<Issue> issues = this.findByExample(specification, page);
		
		return issues;
	}

	@Override
	public List<String> getIssueTypeList() {
		return issueDAO.getIssueTypeList();
	}
	
	/*
	 * =========================================
	 * Inquire
	 * =========================================
	 */
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#get(java.lang.Long)  
	 */ 
	@Override
	public Inquire getInquire(Long id) {
		return inquireDAO.findById(id).get();
	}

	/*
	 * (non-Javadoc) 
	 * @see com.gdpost.web.service.UserService#saveOrUpdate(com.gdpost.web.entity.main.Inquire)  
	 */
	@Override
	public void saveOrUpdateInquire(Inquire policy) {
		if (policy.getId() == null) {
			if (inquireDAO.getByInquireNo(policy.getInquireNo()) != null) {
				throw new ExistedException("咨询号：" + policy.getInquireNo() + "已存在。");
			}
		}
		
		inquireDAO.save(policy);
	}

	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#delete(java.lang.Long)  
	 */
	@Override
	public void deleteInquire(Long id) {
		inquireDAO.deleteById(id);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#findAll(com.gdpost.web.util.dwz.Page)  
	 */
	@Override
	public List<Inquire> findAllInquire(Page page) {
		org.springframework.data.domain.Page<Inquire> springDataPage = inquireDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#findByExample(org.springframework.data.jpa.domain.Specification, com.gdpost.web.util.dwz.Page)	
	 */
	@Override
	public List<Inquire> findByInquireExample(
			Specification<Inquire> specification, Page page) {
		org.springframework.data.domain.Page<Inquire> springDataPage = inquireDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/* (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#getByInquireNo(java.lang.String)
	 */
	@Override
	public Inquire getByInquireNo(String inquireNo) {
		return inquireDAO.getByInquireNo(inquireNo);
	}
	
	@Override
	public List<String> getInquireSubtypeList() {
		return inquireDAO.getInquireSubtypeList();
	}
	
	@Override
	public List<Inquire> getTODOInquireList(User user, Boolean... urge) {
		List<UserRole> urs = userRoleDAO.findByUserId(user.getId());
		
		Organization userOrg = user.getOrganization();
		String orgCode = userOrg.getOrgCode();
		boolean isCity = false;
		if(orgCode.length()>4) {
			isCity = true;
		}

		List<Long> roleIds = new ArrayList<Long> (0);
		boolean isAdmin = false; 
		for(UserRole ur:urs) {
			if(ur.getRole().getName().contains("管理员") || ur.getRole().getName().contains("省分客服")) {
				isAdmin = true;
			}
			roleIds.add(ur.getRole().getId());
		}
		// 默认返回未处理工单
		String inquireStatus = null;
		String kfstatus_flag = null;
		boolean isNull = false;
		if (inquireStatus == null || (inquireStatus != null && inquireStatus.trim().length() <= 0 && kfstatus_flag != null
				&& kfstatus_flag.equals("null"))) {
			isNull = true;
		}

		Page page = new Page();
		if (page.getOrderField() == null || page.getOrderField().trim().length() <= 0) {
			page.setOrderField("operateTime");
			page.setOrderDirection("ASC");
		}

		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		if(urge != null && urge.length>0) {
			csf.add(new SearchFilter("urge", Operator.EQ, true));
		}
		if(!isAdmin) {
			csf.add(new SearchFilter("organ.orgCode", Operator.LIKE_R, orgCode));
		}
		if(!isAdmin && !isCity) {
			for(Long roleId:roleIds) {
				csf.add(new SearchFilter("roleids", Operator.LIKE, "," + roleId.toString() + ","));
			}
		}
		//只回显需要地市处理的
		if(orgCode.length() >= 6) {
			csf.add(new SearchFilter("cityDealFlag", Operator.EQ, 1));
		}
		
		// 如果是县区局登录的机构号为8位，需要根据保单的所在机构进行筛选
		if (user.getOrganization().getOrgCode().length() > 4) {
			if (isNull) {
				csf.add(new SearchFilter("inquireStatus", Operator.EQ, STATUS.NewStatus.name()));
			}
		} else {
			if (isNull) {
				csf.add(new SearchFilter("inquireStatus", Operator.OR_EQ, STATUS.NewStatus.name()));
				csf.add(new SearchFilter("inquireStatus", Operator.OR_EQ, STATUS.IngStatus.name()));
			}
		}
		Specification<Inquire> specification = DynamicSpecifications.bySearchFilter(Inquire.class, csf);
		List<Inquire> inquires = findByInquireExample(specification, page);

		//convert rolename
		String name = "省分%";
		List<Role> roles = roleDAO.findByNameLike(name);
		List<Inquire> rst = new ArrayList<Inquire>();
		String tmpIds = null;
		String[] ids = null;
		StringBuffer rname = null;
		for(Inquire inq:inquires) {
			rname = new StringBuffer("");
			tmpIds = inq.getRoleids();
			if(tmpIds != null) {
				ids = tmpIds.split(",");
				for(String rid:ids) {
					if(rid != null && rid.length()>0) {
						for(Role r:roles) {
							if(rid.equals(r.getId().toString())) {
								rname.append(r.getName()+ " ");
								break;
							}
						}
					}
				}
			}
			inq.setAssignTo(rname.toString());
			rst.add(inq);
		}
		

		//List<String> inquireSubtypes = getInquireSubtypeList();
		//List<Inquire> issues = this.findByInquireExample(specification, page);
		
		return rst;
	}
	
	/*
	 * ============================================================
	 * end of inquire
	 * ============================================================
	 */
	
	
}
