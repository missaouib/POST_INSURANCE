package com.gdpost.web.controller.insurance;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.gdpost.utils.SecurityUtils;
import com.gdpost.utils.StringUtil;
import com.gdpost.utils.UploadDataHelper.UploadDataUtils;
import com.gdpost.web.entity.component.Notice;
import com.gdpost.web.entity.component.NoticeAtt;
import com.gdpost.web.entity.main.Organization;
import com.gdpost.web.entity.main.Role;
import com.gdpost.web.entity.main.User;
import com.gdpost.web.entity.main.UserRole;
import com.gdpost.web.exception.ServiceException;
import com.gdpost.web.log.Log;
import com.gdpost.web.log.LogLevel;
import com.gdpost.web.log.LogMessageObject;
import com.gdpost.web.log.LogModule;
import com.gdpost.web.log.impl.LogUitls;
import com.gdpost.web.service.UserRoleService;
import com.gdpost.web.service.insurance.NoticeService;
import com.gdpost.web.shiro.ShiroUser;
import com.gdpost.web.util.dwz.AjaxObject;
import com.gdpost.web.util.dwz.Page;

@Controller
@RequestMapping("/notice")
public class NoticeController {
	private static final Logger log = LoggerFactory.getLogger(NoticeController.class);
	
	//@Autowired
	//private UploadDataService uploadDataService;
	
	@Autowired
	private NoticeService noticeService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	private static final String VIEW = "notice/view";
	private static final String CREATE = "notice/create";
	private static final String LIST = "notice/list";
	
	String strError = "{\"jsonrpc\":\"2.0\",\"result\":\"error\",\"id\":\"id\",\"message\":\"操作失败。\"}";
	
	@RequiresPermissions(value={"Notice:view"}, logical=Logical.OR)
	@RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
	public String list(HttpServletRequest request, Map<String, Object> map, Page page) {
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();
		Organization org = user.getOrganization();
		List<UserRole> urs = userRoleService.findByUserId(user.getId());
		List<Role> roles = new ArrayList<Role>();
		UserRole ur = null;
		for(int i=0; i<urs.size(); i++) {
			ur = urs.get(i);
			roles.add(ur.getRole());
		}
		
		String sd1 = request.getParameter("sendDate1");
		String sd2 = request.getParameter("sendDate2");
		request.setAttribute("sendDate1", sd1);
		request.setAttribute("sendDate2", sd2);
		Date d1 = null;
		Date d2 = null;
		Date[] d = null;
		if(d1 != null || d2 != null) {
			if (sd1 == null || sd1.trim().length() <= 0) {
				d1 = StringUtil.str2Date("2015-10-01", "yyyy-MM-dd");
			}
			if (sd2 == null || sd2.trim().length() <= 0) {
				d2 = StringUtil.str2Date("9999-12-31", "yyyy-MM-dd");
			}
			d = new Date[2];
			d[0] = d1;
			d[2] = d2;
		}
		/*
		Collection<SearchFilter> csf = new HashSet<SearchFilter>();
		csf.add(new SearchFilter("user.id", Operator.OR_EQ, user.getId()));
		csf.add(new SearchFilter("sender.id", Operator.OR_EQ, user.getId()));
		//csf.add(new SearchFilter("role.id", Operator.OR_IN, userRoleId));
		csf.add(new SearchFilter("organization.orgCode", Operator.OR_LIKE, org.getOrgCode()));
		
		Specification<Notice> specification = DynamicSpecifications.bySearchFilter(request, Notice.class, csf);
		List<Notice> basedata = noticeService.findByNoticeExample(specification, page);
		 */
		List<Notice> basedata = noticeService.findByOwnNoticeList(page, "%" + org.getOrgCode() + "%", roles, user, user, d);
		
		map.put("page", page);
		map.put("basedata", basedata);
		return LIST;
	}

	@RequiresPermissions(value={"Notice:view"}, logical=Logical.OR)
	@RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
	public String view(HttpServletRequest request, @PathVariable("id") Long id, Map<String, Object> map) {
		Notice notice = noticeService.getNotice(id);
		map.put("notice", notice);
		return VIEW;
	}
	
	@RequiresPermissions(value={"Notice:add"}, logical=Logical.OR)
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String preUpload(HttpServletRequest request, Map<String, Object> map) {
		
		return CREATE;
	}
	
	@Log(message="上传了{0}。", level=LogLevel.WARN, module=LogModule.GGGL)
	@RequiresPermissions(value={"Notice:add"}, logical=Logical.OR)
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody String create(HttpServletRequest request, @RequestParam(value = "file", required = true) MultipartFile file, Notice notice) {
        log.debug("-------------------------------------upload:" + file.getOriginalFilename());
        log.debug("------------" + notice.toString());
        int iNY = UploadDataUtils.getNianYue();
        String strPath = UploadDataUtils.getNoticeFileStorePath(request, iNY);
        String updatePath = UploadDataUtils.getNoticeRelateFileStorePath(request, iNY);
		//String strTempPath = UploadDataUtils.getFileStoreTempPath(request);
		String strNewFileName = null;
		boolean hasFile = false;
        if(file != null && file.getOriginalFilename() != null && file.getOriginalFilename().trim().length()>0) {
	        try
	        {
	        	hasFile = true;
	        	String name = file.getOriginalFilename();
	            Long lFileSize = file.getSize();
	
	            com.gdpost.utils.UploadDataHelper.SessionChunk sessionChunk = new com.gdpost.utils.UploadDataHelper.SessionChunk();
	            com.gdpost.utils.UploadDataHelper.FileChunk fileChunk = sessionChunk.getSessionChunk(request);
	            if(fileChunk == null) {
	                fileChunk = new com.gdpost.utils.UploadDataHelper.FileChunk();
	            }
	            String chunkSize = request.getParameter("chunkSize");
	            int iChunkSize = Integer.parseInt(chunkSize==null?"0":chunkSize); //分块大小
	            String strOriginalFileName = name;
	            String strSessionID = request.getSession().getId();
	            
	            int iCurrentChunk = 1;
	            int iChunks = 1;
	            try {
	            	iCurrentChunk = Integer.parseInt(request.getParameter("chunk")); //当前分块
	            	iChunks = Integer.parseInt(request.getParameter("chunks"));//总的分块数量
	            } catch(Exception e) {
	            }
	            
	            strNewFileName = strOriginalFileName;
	            if (iChunks > 1) {
	            	strNewFileName = iCurrentChunk + "_" + strSessionID + "_" + strOriginalFileName;   //按文件块重命名块文件
	            }
	            
	            String strFileGroup = request.getParameter("fileGroup"); // 当前文件组
	            fileChunk.setChunks(iChunks);
	            fileChunk.setChunkSize(iChunkSize);
	            fileChunk.setCurrentChunk(iCurrentChunk);
	            fileChunk.setFileName(strOriginalFileName);
	            fileChunk.setFileGroup(strFileGroup);
	            fileChunk.setFileSize(lFileSize);
	            fileChunk.setListFileName(strOriginalFileName);
	            
	            sessionChunk.setSessionChunk(request, fileChunk);
	            
	            File uploadedFile = null;
	            if (iChunks > 1) {
	            	uploadedFile = new File(strPath, strNewFileName);
	            	if(uploadedFile.exists()) {
	            		uploadedFile.delete();
	            	}
	            	
	            	uploadedFile = new File(strPath, strNewFileName);
	            	
	            	try {
	    				org.apache.commons.io.FileUtils.writeByteArrayToFile(uploadedFile, file.getBytes());
	    			} catch (IOException e) {
	    				log.error(e.getMessage());
	    				return (strError);
	    			} catch (Exception e) {
	    				log.error(e.getMessage());
	    				return (strError);				
	    			}
	            	
	                if(iCurrentChunk + 1 == iChunks) {
	                    // 上传完成转移到完成文件目录
	                    int BUFSIZE = 1024 * 1024 * 8;
	                    FileChannel outChannel = null;
	                    
	                	try {
	                		FileOutputStream fos = new FileOutputStream(strPath + File.separator + strOriginalFileName);
	                		outChannel = fos.getChannel();
	                		String strChunkFile = "";
	                		ByteBuffer bb = ByteBuffer.allocate(BUFSIZE);
	                	    for (int i = 0; i < iChunks; i++) {
	                	    	strChunkFile = strPath + File.separator + i + "_" + strSessionID + "_" + strOriginalFileName;
	                	    	FileInputStream fis = new FileInputStream(strChunkFile);
	                	    	FileChannel fc = fis.getChannel();
	                	    	while(fc.read(bb) != -1){
	                	    		bb.flip();
	                	    	    outChannel.write(bb);
	                	    		bb.clear();
	                	    	}
	                	    	
	                	    	fc.close();
	                	    	fis.close();
	                	    
	                	    	java.nio.file.Path path = java.nio.file.Paths.get(strChunkFile);
	                	    	Files.delete(path);
	            	    	}
	
	                	    fos.close();
	            	    	
	                		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{strNewFileName}));
	                	    ShiroUser shiroUser = SecurityUtils.getShiroUser();
	                	    log.info(shiroUser.getLoginName() + "上传了" + strOriginalFileName);
	    				
	            		} catch (FileNotFoundException e) {
	            			log.error(e.getMessage());
	            			return AjaxObject.newError("发布失败！").toString();
	    				} catch (IOException e) {
	    					log.error(e.getMessage());
	    					return AjaxObject.newError("发布失败！").toString();
	    				} catch (Exception e) {
	    					log.error(e.getMessage());
	    					return AjaxObject.newError("发布失败！").toString();
	    				}
	                	finally {
	            			try {
	            				if(outChannel != null) {
	            					outChannel.close();
	            				}
	    					} catch (IOException e) {
	    					}
	                	}
	                }
	            } else { 
	                // 单个文件直接保存
	            	log.debug("-------------single file name:" + strOriginalFileName);
	            	uploadedFile = new File(strPath, strNewFileName);
	            	try {
	    				org.apache.commons.io.FileUtils.writeByteArrayToFile(uploadedFile, file.getBytes());
	            	    ShiroUser shiroUser = SecurityUtils.getShiroUser();
	            	    log.info(shiroUser.getLoginName() + "上传了" + strNewFileName);
	    				LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{strOriginalFileName}));
	    			} catch (IOException e) {
	    				log.error(e.getMessage());
	    				return AjaxObject.newError("发布失败！").toString();
	    			}
	            	
	            }
	        }
	        catch (Exception e)
	        {
	            // 上传失败，IO异常！
	            e.printStackTrace();
	            log.error("--- UPLOAD FAIL ---");
	            log.error(e.getMessage());
	            return AjaxObject.newError("发布失败！").toString();
	        }
        }
        String roleId = request.getParameter("role.id");
        String orgId = request.getParameter("organization.id");
        String userId = request.getParameter("user.id");
        if(roleId!=null && roleId.trim().length() <=0) {
        	notice.setRole(null);
        }
        if(orgId != null && orgId.trim().length()<=0) {
        	notice.setOrganization(null);
        }
        if(userId != null && userId.trim().length() <=0) {
        	notice.setUser(null);
        }
        ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User user = shiroUser.getUser();
		notice.setSender(user);
		notice.setSendDate(new Date());
        log.debug("-----------save notice finish:" + notice.toString());
        notice = noticeService.saveOrUpdateNotice(notice);
        if (hasFile) {
	        NoticeAtt att = new NoticeAtt();
	        att.setNotice(notice);
	        att.setAttrLink(updatePath + "/" + strNewFileName);
	        log.debug("-----notice attr:" + att.toString());
	        noticeService.saveOrUpdateNoticeAtt(att);
        }
        return AjaxObject.newOk("发布成功！").toString();
	}
	
	@Log(message="删除了{0}通知。", level=LogLevel.WARN, module=LogModule.GGGL)
	@RequiresPermissions(value={"Notice:delete"}, logical=Logical.OR)
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public @ResponseBody String deleteMany(Long[] ids) {
		String[] policys = new String[ids.length];
		try {
			for (int i = 0; i < ids.length; i++) {
				Notice notice = noticeService.getNotice(ids[i]);
				noticeService.deleteNotice(ids[i]);;
				
				policys[i] = notice.getNoticeTitle();
			}
		} catch (ServiceException e) {
			return AjaxObject.newError("删除通知失败：" + e.getMessage()).setCallbackType("").toString();
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{Arrays.toString(policys)}));
		return AjaxObject.newOk("删除通知成功！").setCallbackType("").toString();
	}
	
	// 使用初始化绑定器, 将参数自动转化为日期类型,即所有日期类型的数据都能自动转化为yyyy-MM-dd格式的Date类型
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}
}

