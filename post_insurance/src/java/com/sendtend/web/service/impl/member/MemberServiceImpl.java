/**
 * 
 */
package	com.sendtend.web.service.impl.member;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sendtend.web.dao.member.MUserDAO;
import com.sendtend.web.dao.member.MemberDAO;
import com.sendtend.web.entity.member.TblMember;
import com.sendtend.web.exception.NotDeletedException;
import com.sendtend.web.exception.NotExistedException;
import com.sendtend.web.service.member.MRolePermissionDataControlService;
import com.sendtend.web.service.member.MemberService;
import com.sendtend.web.util.dwz.Page;
import com.sendtend.web.util.dwz.PageUtils;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {
	
	private static final Logger LOG = LoggerFactory.getLogger(MemberServiceImpl.class);
	
	@Autowired
	private MemberDAO memberDAO;
	
	@Autowired
	private MUserDAO userDAO;
	
	@Autowired
	private MRolePermissionDataControlService mrpdc;
	
	@PersistenceContext
	private EntityManager em;
	
	/*
	@Override
	public TblMember add(TblMember m, String key) {
		Session session = em.unwrap(org.hibernate.Session.class);
		//LOG.debug(m.toString());
		String sql = "insert into tbl_member(member_name,description,contact,"
		//LOG.debug("------------sql:" + sql);
		Query q = session.createSQLQuery(sql);
		q.setProperties(m);
		q.executeUpdate();
		return m;
	}
	*/
	
	/*
	 * (non-Javadoc)
	 * @see com.sendtend.web.service.MemberService#get(java.lang.Long)  
	 */ 
	@Override
	public TblMember get(Long id) {
		return memberDAO.findOne(id);
	}
	
	/* (non-Javadoc)
	 * @see com.sendtend.web.service.MemberService#getByName(java.lang.String)
	 */
	@Override
	public TblMember getByName(String name) {
		return memberDAO.getByMemberName(name);
	}

	/*
	 * (non-Javadoc) 
	 * @see com.sendtend.web.service.MemberService#saveOrUpdate(com.sendtend.web.entity.main.TblMember)  
	 */
	@Override
	public TblMember saveOrUpdate(TblMember member) {
		if (member.getId() == null) {
			TblMember parentMember = memberDAO.findOne(member.getParent().getId());
			if (parentMember == null) {
				throw new NotExistedException("id=" + member.getParent().getId() + "父组织不存在！");
			}
			
			if (memberDAO.getByMemberName(member.getMemberName()) != null) {
				throw new NotExistedException((member.getMemberName()) + "已存在！");
			}
		}
		
		return memberDAO.save(member);
	}

	/*
	 * (non-Javadoc)
	 * @see com.sendtend.web.service.MemberService#delete(java.lang.Long)  
	 */
	@Override
	public void delete(Long id) {
		if (isRoot(id)) {
			throw new NotDeletedException("不允许删除根组织。");
		}
		
		TblMember member = this.get(id);
		
		//先判断是否存在子模块，如果存在子模块，则不允许删除
		if(member.getChildren().size() > 0){
			throw new NotDeletedException((member.getMemberName()) + "组织下存在子组织，不允许删除。");
		}
		
		if (userDAO.findById(id).size() > 0) {
			throw new NotDeletedException((member.getMemberName()) + "组织下存在用户，不允许删除。");
		}
		
		memberDAO.delete(member);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.sendtend.web.service.MemberService#findAll(com.sendtend.web.util.dwz.Page)  
	 */
	@Override
	public List<TblMember> findAll(Page page) {
		org.springframework.data.domain.Page<TblMember> springDataPage = memberDAO.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.sendtend.web.service.MemberService#findByExample(org.springframework.data.jpa.domain.Specification, com.sendtend.web.util.dwz.Page)	
	 */
	@Override
	public List<TblMember> findByExample(
			Specification<TblMember> specification, Page page) {
		org.springframework.data.domain.Page<TblMember> springDataPage = memberDAO.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	@Override
	public TblMember getTree() {
		List<TblMember> list = memberDAO.findAllWithCache();
		
		List<TblMember> rootList = makeTree(list);
				
		return rootList.get(0);
	}
	
	/* (non-Javadoc)
	 * @see com.sendtend.web.service.MemberService#getTree()
	 */
	@Override
	public TblMember getTree(Long id) {
		//List<TblMember> list = memberDAO.findByIdWithCache(id);
		List<TblMember> list = memberDAO.findAllWithCache();
		
		//List<TblMember> rootList = makeTree(list, id);
		List<TblMember> rootList = makeTree(list);
		//LOG.debug("---------" + rootList);
		TblMember root = rootList.get(0);
		List<TblMember> children = root.getChildren();
		for(TblMember rst : children) {
			if(rst.getId() == id) {
				return rst;
			}
		}
		
		return null;
	}
	
	/**
	 * 判断是否是根组织.
	 */
	private boolean isRoot(Long id) {
		return id == 1;
	}
	
	private List<TblMember> makeTree(List<TblMember> list) {
		List<TblMember> parent = new ArrayList<TblMember>();
		// get parentId = null;
		for (TblMember e : list) {
			if (e.getParent() == null) {
				//e.clearChildren();
				//e.getChildren().clear();
				e.setChildren(new ArrayList<TblMember>(0));
				parent.add(e);
			}
		}
		// 删除parentId = null;
		list.removeAll(parent);
		
		makeChildren(parent, list);
		
		return parent;
	}
	
	@Deprecated
	private List<TblMember> makeTree(List<TblMember> list, Long parent_id) {
		List<TblMember> parent = new ArrayList<TblMember>();
		// get parentId = null;
		for (TblMember e : list) {
			if (e.getId().equals(parent_id)) {
				e.setChildren(new ArrayList<TblMember>(0));
				parent.add(e);
			}
		}
		// 删除parentId = null;
		list.removeAll(parent);

		makeChildrenNoRoot(parent, list);
		
		return parent;
	}
	
	private void makeChildrenNoRoot(List<TblMember> parent, List<TblMember> children) {
		if (children.isEmpty()) {
			return ;
		}
		
		boolean bFlag = false;
		List<TblMember> tmp = new ArrayList<TblMember>();
		for (TblMember c1 : parent) {
			//LOG.info(c1.getId() + "------ c1 parent:" + c1.getParent().getId());
			for (TblMember c2 : children) {
				//LOG.info(c2.getId() + "------ c2 children");
				if(c2.getParent() != null) {
					//LOG.info("------ c2 children's parent:" + c2.getParent().getId());
					//c2.getChildren().clear();
					c2.setChildren(new ArrayList<TblMember>(0));
					if (c2.getParent() != null && c1.getId().equals(c2.getParent().getId())) {
						c1.getChildren().add(c2);
						tmp.add(c2);
						bFlag = true;
					}
				} else {
					bFlag = false;
				}
			}
		}
		//LOG.info("-----0000-----" + tmp);
		children.removeAll(tmp);
		
		if(bFlag) {
			makeChildrenNoRoot(tmp, children);
		}
		
		return;
	}
	
	private void makeChildren(List<TblMember> parent, List<TblMember> children) {
		if (children.isEmpty()) {
			return ;
		}
		
		List<TblMember> tmp = new ArrayList<TblMember>();
		for (TblMember c1 : parent) {
			for (TblMember c2 : children) {
				if(c2.getParent() != null) {
					//c2.getChildren().clear();
					c2.setChildren(new ArrayList<TblMember>(0));
					if (c1.getId().equals(c2.getParent().getId())) {
						c1.getChildren().add(c2);
						tmp.add(c2);
					}
				}
			}
		}
		//LOG.info("-----111-----" + tmp);
		children.removeAll(tmp);
		
		makeChildren(tmp, children);
	}
}
