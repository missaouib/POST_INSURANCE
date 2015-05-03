package com.gdpost.web.controller.uploaddatamanage;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gdpost.utils.SecurityUtils;
import com.gdpost.utils.SwfHelper.Common;
import com.gdpost.utils.SwfHelper.Pdf2Swf;
import com.gdpost.web.entity.member.TblMemberResource;
import com.gdpost.web.entity.member.TblMemberUser;
import com.gdpost.web.log.Log;
import com.gdpost.web.log.LogMessageObject;
import com.gdpost.web.log.impl.LogUitls;
import com.gdpost.web.service.uploaddatamanage.MemberResourceService;
import com.gdpost.web.shiro.ShiroUser;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.persistence.DynamicSpecifications;
import com.gdpost.web.util.persistence.SearchFilter;
import com.gdpost.web.util.persistence.SearchFilter.Operator;

@Controller
@RequestMapping("/uploaddatamanage/document")
public class DocumentController {
	private static final Logger LOG = LoggerFactory.getLogger(DocumentController.class); 
	
	private static final String STORE_DIR = "upload/";
	
	@Autowired
	private MemberResourceService memberResourceService;
	
	private static final String LIST = "uploaddatamanage/document/list";
	private static final String WEBLIST = "uploaddatamanage/document/weblist";
	private static final String VIEW = "uploaddatamanage/document/view";
	private static final String WEBVIEW = "uploaddatamanage/document/webview";
	private static final String INDEX = "uploaddatamanage/document/index";
	
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
	}
	
	@RequiresPermissions("Document:show")
	@RequestMapping(value="/list", method={RequestMethod.GET, RequestMethod.POST})
	public String list(HttpServletRequest request, Page page, Map<String, Object> map) {
//		TblMemberUser user = SecurityUtils.getLoginTblMemberUser();
//		long member_id = user.getTblMember().getId();
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		long member_id = shiroUser.getUser().getId();
		Specification<TblMemberResource> specification = DynamicSpecifications.bySearchFilter(request, TblMemberResource.class,
				new SearchFilter("tblMember.id", Operator.EQ, member_id));
		List<TblMemberResource> resources = memberResourceService.findAll(specification, page);
		
		map.put("page", page);
		map.put("resources", resources);

		return LIST;
	}
	
	@RequestMapping(value="/index", method={RequestMethod.GET, RequestMethod.POST})
	public String index(HttpServletRequest request, Page page, Map<String, Object> map) {

		return INDEX;
	}
	
	@RequiresPermissions("Document:show")
	@RequestMapping(value="/toWebList/{storeType}", method={RequestMethod.GET, RequestMethod.POST})
	public String webList(HttpServletRequest request, @PathVariable String storeType, Page page, Map<String, Object> map) {
//		TblMemberUser user = SecurityUtils.getLoginTblMemberUser();
//		long member_id = user.getTblMember().getId();
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		long member_id = shiroUser.getUser().getId();
		Specification<TblMemberResource> specification = DynamicSpecifications.bySearchFilter(request, TblMemberResource.class,
				new SearchFilter("tblMember.id", Operator.EQ, member_id),
				new SearchFilter("resource.storeType", Operator.EQ, storeType));
		
		List<TblMemberResource> resources = memberResourceService.findAll(specification, page);
		
		map.put("page", page);
		map.put("resources", resources);
		map.put("storetype", storeType);

		return WEBLIST;
	}
	
	@Log(message="阅读了{0}。")
	@RequiresPermissions("Document:view")
	@RequestMapping(value="/view/{id}", method=RequestMethod.GET)
	public String view(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id, Map<String, Object> map) {
		TblMemberResource resource = memberResourceService.get(id);
		if(resource == null) {
			return request.getServletContext().getContextPath() + "/members/index";
		}
		
		// 检查id 是否是该用户的
//		TblMemberUser user = SecurityUtils.getLoginTblMemberUser();	
//		long member_id = user.getTblMember().getId();
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		long member_id = shiroUser.getUser().getId();
		if(!resource.getTblMember().getId().equals(member_id)) {
			return request.getServletContext().getContextPath() + "/members/index";
		}
		
		Common conf = new Common();
		int numPages = conf.getTotalPages(getFileStorePath(request) + resource.getResource().getUuid() + ".pdf");
		
		map.put("resource", resource);
		map.put("numPages", numPages);
		
		if(resource.getStatus().equals(0)) {
			resource.setReadDate(new Timestamp(System.currentTimeMillis()));
			resource.setStatus(1);
			memberResourceService.saveOrUpdate(resource);
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{resource.getResource().getName()}));
		
		return VIEW;
	}
	
	@Log(message="阅读了{0}。")
	@RequiresPermissions("Document:view")
	@RequestMapping(value="/webview/{id}", method=RequestMethod.GET)
	public String webview(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id, Map<String, Object> map) {
		TblMemberResource resource = memberResourceService.get(id);
		if(resource == null) {
			return request.getServletContext().getContextPath() + "/web/index";
		}
		
		// 检查id 是否是该用户的
//		TblMemberUser user = SecurityUtils.getLoginTblMemberUser();	
//		long member_id = user.getTblMember().getId();
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		long member_id = shiroUser.getUser().getId();
		if(!resource.getTblMember().getId().equals(member_id)) {
			return request.getServletContext().getContextPath() + "/web/index";
		}
		
		Common conf = new Common();
		int numPages = conf.getTotalPages(getFileStorePath(request) + resource.getResource().getUuid() + ".pdf");
		
		map.put("resource", resource);
		map.put("numPages", numPages);
		
		if(resource.getStatus().equals(0)) {
			resource.setReadDate(new Timestamp(System.currentTimeMillis()));
			resource.setStatus(1);
			memberResourceService.saveOrUpdate(resource);
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{resource.getResource().getName()}));
		
		return WEBVIEW;
	}
	
	@RequiresPermissions("Document:view")
	@RequestMapping(value="/read/{id}/{format}/{page}", method=RequestMethod.GET)
	public void read(HttpServletRequest request, HttpServletResponse response,  @PathVariable Long id, @PathVariable String format, @PathVariable int page) {
		TblMemberResource resource = memberResourceService.get(id);
		
		// 检查id 是否是该用户的
//		TblMemberUser user = SecurityUtils.getLoginTblMemberUser();	
//		long member_id = user.getTblMember().getId();
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		long member_id = shiroUser.getUser().getId();
		if(!resource.getTblMember().getId().equals(member_id)) {
			return;
		}
		
		BufferedOutputStream outs = null;
		try {
			outs = new BufferedOutputStream(response.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Common conf 	= new Common();
		String doc 		= resource.getResource().getUuid();
		String pdfdoc	= doc;
		String pages	= "" + page;

		if(doc == null)return;
		if(!doc.endsWith(".pdf"))	{pdfdoc 	= doc + ".pdf";}
		if(pages == null)			{pages = "";}
		if(format == null)			{format="swf";}
		String swfdoc	= pdfdoc + ".swf";
		// split mode
		swfdoc 	= pdfdoc + "_" + pages + ".swf";

		String messages 	= "";
		String swfFilePath 	= conf.separate(getFileStorePath(request) + doc + "/") + swfdoc;
		String pdfFilePath 	= conf.separate(getFileStorePath(request)) + pdfdoc;
		String cmdFilePaht = conf.separate(getCmdFilePath(request));
		
		String error = "";
		if(!conf.validParams(pdfFilePath, pdfdoc, pages)){
			error += "Error:Incorrect file specified, please check your path.";
		}else{
			if(!conf.file_exists(swfFilePath)){
				Pdf2Swf pdfconv = new Pdf2Swf(request);
				messages = pdfconv.convert(cmdFilePaht, conf.separate(getFileStorePath(request)), conf.separate(getFileStorePath(request) + doc + "/"), pdfdoc, pages);
			}

			if(conf.file_exists(swfFilePath)){
				response.setContentType("application/x-shockwave-flash");
				response.setHeader("Accept-Ranges", "bytes");
				byte[] content = conf.file_get_contents(swfFilePath);
				response.setContentLength(content.length);
				try {
					outs.write(content);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				if(messages.length() == 0)
					messages = "[Cannot find SWF file. Please check your configuration]";
			}

			if(messages.length() > 0 && !"[OK]".equals(messages) && !"[Converted]".equals(messages)){
				try {
					outs.write(("Error:" + messages.substring(1,messages.length()-2)).getBytes());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		if(error.length() > 0) {
			try {
				outs.write(error.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			outs.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			outs.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//out.clear();
		//out = pageContext.pushBody();
	}
	
	private String getFileStorePath(HttpServletRequest request) {
		// 上传到网站目录以外，以防止通过url访问下载
		return request.getSession().getServletContext().getRealPath("/") + "../../" + STORE_DIR;
	}
	
	private String getCmdFilePath(HttpServletRequest request) {
		// 上传到网站目录以外，以防止通过url访问下载
		return request.getSession().getServletContext().getRealPath("/") + "../../SWFTools/";
	}
}
