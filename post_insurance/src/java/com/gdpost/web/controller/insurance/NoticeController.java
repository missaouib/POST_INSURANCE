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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.gdpost.utils.SecurityUtils;
import com.gdpost.utils.UploadDataHelper.UploadDataUtils;
import com.gdpost.web.entity.component.Notice;
import com.gdpost.web.entity.component.NoticeAtt;
import com.gdpost.web.log.Log;
import com.gdpost.web.log.LogMessageObject;
import com.gdpost.web.log.impl.LogUitls;
import com.gdpost.web.service.insurance.NoticeService;
import com.gdpost.web.service.uploaddatamanage.UploadDataService;
import com.gdpost.web.shiro.ShiroUser;
import com.gdpost.web.util.dwz.AjaxObject;
import com.gdpost.web.util.dwz.Page;
import com.gdpost.web.util.persistence.DynamicSpecifications;

@Controller
@RequestMapping("/notice")
public class NoticeController {
	private static final Logger log = LoggerFactory.getLogger(NoticeController.class);
	
	@Autowired
	private UploadDataService uploadDataService;
	
	@Autowired
	private NoticeService noticeService;
	
	private static final String CREATE = "notice/create";
	private static final String LIST = "notice/list";
	
	String strError = "{\"jsonrpc\":\"2.0\",\"result\":\"error\",\"id\":\"id\",\"message\":\"操作失败。\"}";
	
	@RequiresPermissions(value={"Notice:view"}, logical=Logical.OR)
	@RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
	public String list(HttpServletRequest request, Map<String, Object> map, Page page) {
		Specification<Notice> specification = DynamicSpecifications.bySearchFilter(request, Notice.class);
		List<Notice> basedata = noticeService.findByNoticeExample(specification, page);

		map.put("page", page);
		map.put("basedata", basedata);
		return LIST;
	}

	@RequiresPermissions(value={"Notice:add"}, logical=Logical.OR)
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String preUpload(HttpServletRequest request, Map<String, Object> map) {
		
		return CREATE;
	}

	@Log(message="上传了{0}。")
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
        if(file != null && file.getOriginalFilename() != null) {
	        try
	        {
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
	        }
        }
		
        notice = noticeService.saveOrUpdateNotice(notice);
        NoticeAtt att = new NoticeAtt();
        log.debug("-----------save notice finish:" + notice.toString());
        att.setNotice(notice);
        att.setAttrLink(updatePath + "/" + strNewFileName);
        log.debug("-----notice attr:" + att.toString());
        noticeService.saveOrUpdateNoticeAtt(att);
        
        return AjaxObject.newOk("发布成功！").toString();
	}
	
	// 使用初始化绑定器, 将参数自动转化为日期类型,即所有日期类型的数据都能自动转化为yyyy-MM-dd格式的Date类型
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}
}

