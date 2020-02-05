/**
 * 
 */
package	com.gdpost.web.service.impl.insurance;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.druid.pool.DruidDataSource;
import com.gdpost.web.dao.CheckFixTypeDAO;
import com.gdpost.web.dao.CheckRecordDAO;
import com.gdpost.web.dao.CheckWriteDAO;
import com.gdpost.web.dao.ReuseRiskDAO;
import com.gdpost.web.dao.UnderWriteDAO;
import com.gdpost.web.dao.model.YbtPolicyModelDAO;
import com.gdpost.web.entity.basedata.CheckFixType;
import com.gdpost.web.entity.component.YbtPolicyModel;
import com.gdpost.web.entity.insurance.CheckRecord;
import com.gdpost.web.entity.insurance.CheckWrite;
import com.gdpost.web.entity.insurance.ReuseRisk;
import com.gdpost.web.entity.insurance.UnderWrite;
import com.gdpost.web.entity.main.Organization;
import com.gdpost.web.entity.main.User;
import com.gdpost.web.exception.ExistedException;
import com.gdpost.web.service.insurance.QyglService;
import com.gdpost.web.util.StatusDefine.STATUS;
import com.gdpost.web.util.StatusDefine.UW_STATUS;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.dwz.PageUtils;
import com.gdpost.web.util.persistence.DynamicSpecifications;
import com.gdpost.web.util.persistence.SearchFilter;
import com.gdpost.web.util.persistence.SearchFilter.Operator;
import com.mysql.cj.jdbc.JdbcStatement;

@Service
@Transactional
public class QyglServiceImpl implements QyglService {
	private static final Logger LOG = LoggerFactory.getLogger(QyglServiceImpl.class);
	
	@Autowired
	private CheckWriteDAO checkWriteDAO;
	
	@Autowired
	private CheckRecordDAO checkRecordDAO;
	
	@Autowired
	private UnderWriteDAO uwDAO;
	
	@Autowired
	private CheckFixTypeDAO checkFixTypeDAO;
	
	@Autowired
	private YbtPolicyModelDAO ybtDAO;
	
	@Autowired
	private ReuseRiskDAO reuseDAO;
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#get(java.lang.Long)  
	 */ 
	@Override
	public CheckWrite getCheckWrite(Long id) {
		return checkWriteDAO.findById(id).get();
	}
	
	/*
	 * (non-Javadoc) 
	 * @see com.gdpost.web.service.UserService#saveOrUpdate(com.gdpost.web.entity.main.Policy)  
	 */
	@Override
	public void saveOrUpdateCheckWrite(CheckWrite check) {
		if (check.getId() == null) {
			if (checkWriteDAO.getByPolicyPolicyNo(check.getPolicy().getPolicyNo()) != null) {
				throw new ExistedException("保单号：" + check.getPolicy().getPolicyNo() + "已存在。");
			}
		}
		
		checkWriteDAO.saveAndFlush(check);
	}
	
	@Override
	public void deleteCheckWrite(CheckWrite check) {
		checkWriteDAO.deleteById(check.getId());
	}
	
	@Override
	public List<CheckWrite> findAllCheckWrite(Page page) {
		org.springframework.data.domain.Page<CheckWrite> springDataPage = checkWriteDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#findByExample(org.springframework.data.jpa.domain.Specification, com.gdpost.web.util.dwz.Page)	
	 */
	@Override
	public List<CheckWrite> findByCheckWriteExample(
			Specification<CheckWrite> specification, Page page) {
		org.springframework.data.domain.Page<CheckWrite> springDataPage = checkWriteDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/* (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#getByPolicyNo(java.lang.String)
	 */
	@Override
	public CheckWrite getByCheckWritePolicyNo(String policyNo) {
		return checkWriteDAO.getByPolicyPolicyNo(policyNo);
	}
	
	@Override
	public List<CheckWrite> getTODOWriteIssueList(User user) {
		Organization userOrg = user.getOrganization();
		//默认返回未处理工单
		Specification<CheckWrite> specification = DynamicSpecifications.bySearchFilterWithoutRequest(CheckWrite.class,
				new SearchFilter("fixStatus", Operator.EQ, STATUS.NewStatus.name()),
				//new SearchFilter("fixStatus", Operator.OR_LIKE, STATUS.ReopenStatus.getDesc()),
				new SearchFilter("policy.organization.orgCode", Operator.LIKE_R, userOrg.getOrgCode()));
		
		if (userOrg.getOrgCode().length() <= 4) { //如果是省分的，看已回复的。
			specification = DynamicSpecifications.bySearchFilterWithoutRequest(CheckWrite.class,
					new SearchFilter("fixStatus", Operator.EQ, STATUS.DealStatus.name()),
					new SearchFilter("policy.organization.orgCode", Operator.LIKE_R, userOrg.getOrgCode()));
		}
		Page page = new Page();
		page.setNumPerPage(10);
		LOG.debug("------------ ready to search todo check write:");
		List<CheckWrite> issues = this.findByCheckWriteExample(specification, page);
		if (issues == null || issues.isEmpty()) {
			issues = new ArrayList<CheckWrite>();
		}
		
		/*
		//如果是非省分级别，加上重打开数据
		if(user.getOrganization().getOrgCode().length() > 4) {
			LOG.debug("------- 非省分级别，查找重打开数据" + issues);
			specification = DynamicSpecifications.bySearchFilterWithoutRequest(CheckWrite.class,
					new SearchFilter("fixStatus", Operator.LIKE, STATUS.NewStatus.name()),
					new SearchFilter("policy.organization.orgCode", Operator.LIKE, userOrg.getOrgCode()));
			List<CheckWrite> tmpList = this.findByCheckWriteExample(specification, page);
			LOG.debug("------------ tmpList:" + tmpList);
			if(tmpList != null && !tmpList.isEmpty()) {
				issues.addAll(tmpList);
			}
		}
		*/
		
		return issues;
	}

	@Override
	public CheckRecord getByCheckRecordPolicyNo(String policyNo) {
		return checkRecordDAO.getByPolicyPolicyNo(policyNo);
	}

	@Override
	public List<CheckRecord> findByCheckRecordExample(
			Specification<CheckRecord> specification, Page page) {
		org.springframework.data.domain.Page<CheckRecord> springDataPage = checkRecordDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	@Override
	public List<CheckRecord> findAllCheckRecord(Page page) {
		org.springframework.data.domain.Page<CheckRecord> springDataPage = checkRecordDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	@Override
	public void deleteCheckRecord(Long id) {
		CheckRecord cr = checkRecordDAO.findById(id).get();
		checkRecordDAO.deleteById(cr.getId());
	}

	@Override
	public void saveOrUpdateCheckRecord(CheckRecord check) {
		if (check.getId() == null) {
			if (checkRecordDAO.getByPolicyPolicyNo(check.getPolicy().getPolicyNo()) != null) {
				throw new ExistedException("保单号：" + check.getPolicy().getPolicyNo() + "已存在。");
			}
		}
		
		checkRecordDAO.saveAndFlush(check);
	}
	
	@Override
	public CheckRecord getCheckRecord(Long id) {
		return checkRecordDAO.findById(id).get();
	}

	
	@Override
	public List<CheckRecord> getTODORecordIssueList(User user) {
		Organization userOrg = user.getOrganization();
		//默认返回未处理工单
		Specification<CheckRecord> specification = DynamicSpecifications.bySearchFilterWithoutRequest(CheckRecord.class,
				new SearchFilter("fixStatus", Operator.EQ, STATUS.NewStatus.name()),
				//new SearchFilter("fixStatus", Operator.OR_LIKE, STATUS.ReopenStatus.getDesc()),
				new SearchFilter("policy.organization.orgCode", Operator.LIKE_R, userOrg.getOrgCode()));
		
		if (userOrg.getOrgCode().length() <= 4) { //如果是省分的，看已回复的。
			specification = DynamicSpecifications.bySearchFilterWithoutRequest(CheckRecord.class,
					new SearchFilter("fixStatus", Operator.EQ, STATUS.DealStatus.name()),
					new SearchFilter("policy.organization.orgCode", Operator.LIKE_R, userOrg.getOrgCode()));
		}
		Page page = new Page();
		page.setNumPerPage(10);
		LOG.debug("------------ ready to search check record issue:");
		List<CheckRecord> issues = this.findByCheckRecordExample(specification, page);
		if (issues == null || issues.isEmpty()) {
			issues = new ArrayList<CheckRecord>();
		}
		
		/*
		//如果是非省分级别，加上重打开数据
		if(user.getOrganization().getOrgCode().length() > 4) {
			LOG.debug("------- 非省分级别，查找重打开数据" + issues);
			specification = DynamicSpecifications.bySearchFilterWithoutRequest(CheckRecord.class,
					new SearchFilter("fixStatus", Operator.LIKE, STATUS.NewStatus.name()),
					new SearchFilter("policy.organization.orgCode", Operator.LIKE, userOrg.getOrgCode()));
			List<CheckRecord> tmpList = this.findByCheckRecordExample(specification, page);
			LOG.debug("------------ tmpList:" + tmpList);
			if(tmpList != null && !tmpList.isEmpty()) {
				issues.addAll(tmpList);
			}
		}
		*/
		
		return issues;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.insurance.XqglService#getUnderWrite(java.lang.Long)
	 */
	@Override
	public UnderWrite getUnderWrite(Long id) {
		return uwDAO.findById(id).get();
	}

	@Override
	public void saveOrUpdateUnderWrite(UnderWrite uw) {
		uwDAO.save(uw);
	}

	@Override
	public void deleteUnderWrite(Long id) {
		uwDAO.deleteById(id);
	}

	@Override
	public List<UnderWrite> findAllUnderWrite(Page page) {
		org.springframework.data.domain.Page<UnderWrite> springDataPage = uwDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	@Override
	public List<UnderWrite> findByUnderWriteExample(Specification<UnderWrite> specification, Page page) {
		org.springframework.data.domain.Page<UnderWrite> springDataPage = uwDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	@Override
	public UnderWrite getUnderWriteByPolicyNo(String policyNo) {
		return uwDAO.getByPolicyNo(policyNo);
	}

	@Override
	public UnderWrite getUnderWriteByFormNo(String formNo) {
		return uwDAO.getByFormNo(formNo);
	}

	@Override
	public List<UnderWrite> getTODOUnderWriteList(User user) {
		Organization userOrg = user.getOrganization();
		//默认返回未处理工单
		Specification<UnderWrite> specification = null;
		
		specification = DynamicSpecifications.bySearchFilterWithoutRequest(UnderWrite.class,
				new SearchFilter("billBackDate", Operator.ISNULL, new Date()),
				new SearchFilter("status", Operator.NEQ, UW_STATUS.DelStatus.name()),
				new SearchFilter("organization.orgCode", Operator.LIKE_R, userOrg.getOrgCode()));
		Page page = new Page();
		page.setNumPerPage(10);
		page.setOrderField("ybtDate");
		page.setOrderDirection("ASC");
		
		List<UnderWrite> issues = this.findByUnderWriteExample(specification, page);
		if (issues == null || issues.isEmpty()) {
			issues = new ArrayList<UnderWrite>();
		}
		
		return issues;
	}

	@Override
	public List<CheckFixType> getCheckFixTypeList() {
		return checkFixTypeDAO.findAll();
	}

	@SuppressWarnings("resource")
	@Override
	public Integer getOverdueUWCount(HttpServletRequest request, User user) {
		java.sql.Connection connection = null;
		JdbcStatement statement = null;
		//com.mysql.cj.jdbc.ServerPreparedStatement statement = null;
		try {
			Object objDataSource = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext()).getBean("dataSource");
			DruidDataSource dataSource = (DruidDataSource)objDataSource;
			connection = DriverManager.getConnection(dataSource.getUrl(), dataSource.getUsername(), dataSource.getPassword());
			
			String sql = "select count(t1.id) from t_under_write t1,t_organization t2 where t1.organ_id=t2.id and "
					+ "((prov_send_date is not null and city_send_date is null and area_send_date is null and NOW()-prov_send_date>3) or "
					+ "(city_send_date is not null and to_net=1 and bill_back_date is null and NOW()-city_send_date>5) or "
					+ "(city_send_date is not null and area_send_date is null and NOW()-city_send_date>3) or "
					+ "(area_send_date is not null and NOW()-area_send_date>5) or "
					+ "(sys_date is not null and now()-sys_date>15)) "
					+ "and t2.org_code like \"%" + user.getOrganization().getOrgCode() + "%\" and "
					+ "bill_back_date is null and t1.status=\"SendStatus\";";
			LOG.debug("----------- sql : " + sql);
			
			statement = (JdbcStatement)connection.createStatement();
			//statement = (com.mysql.cj.jdbc.ServerPreparedStatement)connection.prepareStatement(sql);
			
        	ResultSet rst = statement.executeQuery(sql);
        	while(rst.next()) {
        		return rst.getInt(1);
        	}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			if(statement != null) {
				try {
					statement.close();
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return 0;
	}

	@SuppressWarnings("resource")
	@Override
	public Integer getOverdueUWCall(HttpServletRequest request, User user) {
		java.sql.Connection connection = null;
		JdbcStatement statement = null;
		//com.mysql.cj.jdbc.ServerPreparedStatement statement = null;
		try {
			Object objDataSource = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext()).getBean("dataSource");
			DruidDataSource dataSource = (DruidDataSource)objDataSource;
			connection = DriverManager.getConnection(dataSource.getUrl(), dataSource.getUsername(), dataSource.getPassword());
			
			String sql = "select count(t1.id) from t_under_write t1,t_organization t2 where t1.organ_id=t2.id and "
					+ "((city_send_date is not null and to_net=1 and NOW()-city_send_date>10) or "
					+ "(area_send_date is not null and NOW()-area_send_date>10) or "
					+ "(sys_date is not null and now()-sys_date>15)) "
					+ "and t2.org_code like \"%" + user.getOrganization().getOrgCode() + "%\" and "
					+ "bill_back_date is null and t1.status=\"SendStatus\";";
			LOG.debug("----------- sql : " + sql);
			
			statement = (JdbcStatement)connection.createStatement();
			//statement = (com.mysql.cj.jdbc.ServerPreparedStatement)connection.prepareStatement(sql);
			
        	ResultSet rst = statement.executeQuery(sql);
        	while(rst.next()) {
        		return rst.getInt(1);
        	}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			if(statement != null) {
				try {
					statement.close();
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return 0;
	}
	
	@SuppressWarnings("resource")
	@Override
	public Integer getOverdueUWWeixin(HttpServletRequest request, User user) {
		java.sql.Connection connection = null;
		JdbcStatement statement = null;
		//com.mysql.cj.jdbc.ServerPreparedStatement statement = null;
		try {
			Object objDataSource = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext()).getBean("dataSource");
			DruidDataSource dataSource = (DruidDataSource)objDataSource;
			connection = DriverManager.getConnection(dataSource.getUrl(), dataSource.getUsername(), dataSource.getPassword());
			
			String sql = "select count(t1.id) from t_under_write t1,t_organization t2 where t1.organ_id=t2.id and "
					+ "(sys_date is not null and now()-sys_date>20) "
					+ "and t2.org_code like \"%" + user.getOrganization().getOrgCode() + "%\" and "
					+ "bill_back_date is null and t1.status=\"SendStatus\";";
			LOG.debug("----------- sql : " + sql);
			
			statement = (JdbcStatement)connection.createStatement();
			//statement = (com.mysql.cj.jdbc.ServerPreparedStatement)connection.prepareStatement(sql);
			
        	ResultSet rst = statement.executeQuery(sql);
        	while(rst.next()) {
        		return rst.getInt(1);
        	}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			if(statement != null) {
				try {
					statement.close();
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return 0;
	}

	@Override
	public List<UnderWrite> getOverdueUWList2Pop(User user, Page page) {
		org.springframework.data.domain.Page<UnderWrite> springDataPage = uwDAO.findDistinctUnderWrite2Pop("%" + user.getOrganization().getOrgCode() + "%", "SendStatus", PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	@Override
	public List<UnderWrite> getOverdueUWList2Call(User user, Page page) {
		org.springframework.data.domain.Page<UnderWrite> springDataPage = uwDAO.findDistinctUnderWrite2Call("%" + user.getOrganization().getOrgCode() + "%", "SendStatus", PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	@Override
	public List<UnderWrite> getOverdueUWList2Weixin(User user, Page page) {
		org.springframework.data.domain.Page<UnderWrite> springDataPage = uwDAO.findDistinctUnderWrite2Weixin("%" + user.getOrganization().getOrgCode() + "%", "SendStatus", PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	@Override
	public List<YbtPolicyModel> listYBTPolicys(String orgCode, String date1, String date2, Page page) {
		org.springframework.data.domain.Page<YbtPolicyModel> springDataPage = ybtDAO.findByOrgCodePolicyDate1PolicyDate2(orgCode, date1, date2, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	

	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#get(java.lang.Long)  
	 */ 
	@Override
	public ReuseRisk getReuseRisk(Long id) {
		return reuseDAO.findById(id).get();
	}
	
	/*
	 * (non-Javadoc) 
	 * @see com.gdpost.web.service.UserService#saveOrUpdate(com.gdpost.web.entity.main.Policy)  
	 */
	@Override
	public void saveOrUpdateReuseRisk(ReuseRisk check) {
		if (check.getId() == null) {
			if (reuseDAO.getByPolicyPolicyNo(check.getPolicy().getPolicyNo()) != null) {
				throw new ExistedException("保单号：" + check.getPolicy().getPolicyNo() + "已存在。");
			}
		}
		
		reuseDAO.saveAndFlush(check);
	}
	
	@Override
	public void deleteReuseRisk(ReuseRisk check) {
		reuseDAO.deleteById(check.getId());
	}
	
	@Override
	public List<ReuseRisk> findAllReuseRisk(Page page) {
		org.springframework.data.domain.Page<ReuseRisk> springDataPage = reuseDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#findByExample(org.springframework.data.jpa.domain.Specification, com.gdpost.web.util.dwz.Page)	
	 */
	@Override
	public List<ReuseRisk> findByReuseRiskExample(
			Specification<ReuseRisk> specification, Page page) {
		org.springframework.data.domain.Page<ReuseRisk> springDataPage = reuseDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/* (non-Javadoc)
	 * @see com.gdpost.web.service.UserService#getByPolicyNo(java.lang.String)
	 */
	@Override
	public ReuseRisk getByReuseRiskPolicyNo(String policyNo) {
		return reuseDAO.getByPolicyPolicyNo(policyNo);
	}
}
