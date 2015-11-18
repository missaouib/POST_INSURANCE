package com.gdpost.web.controller.insurance;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.gdpost.utils.SecurityUtils;
import com.gdpost.utils.UploadDataHelper.UploadDataUtils;
import com.gdpost.web.entity.component.Notice;
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
	@RequestMapping(value = "/list", method = RequestMethod.GET)
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
	public @ResponseBody String upload(HttpServletRequest request, @RequestParam(value = "file", required = true) MultipartFile file, Notice notice) {
        log.debug("-------------------------------------upload:" + file.getName());
        try
        {
        	String name = file.getName();
            Long lFileSize = Long.parseLong(request.getParameter("size"));
            int iNY = UploadDataUtils.getNianYue();
            String strPath = UploadDataUtils.getFileStorePath(request, iNY);
    		String strTempPath = UploadDataUtils.getFileStoreTempPath(request);
    		//String strFileName = file.getOriginalFilename();
            // 临时文件目录不存在创建目录
            File tempSavePath = new File(strTempPath);
            if(!tempSavePath.exists())
            {
                tempSavePath.mkdirs();
            }

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
            
            String strNewFileName = strOriginalFileName;
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
            	uploadedFile = new File(strTempPath, strNewFileName);
            	if(uploadedFile.exists()) {
            		uploadedFile.delete();
            	}
            	
            	uploadedFile = new File(strTempPath, strNewFileName);
            	
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
                		FileOutputStream fos = new FileOutputStream(strTempPath + File.separator + strOriginalFileName);
                		outChannel = fos.getChannel();
                		String strChunkFile = "";
                		ByteBuffer bb = ByteBuffer.allocate(BUFSIZE);
                	    for (int i = 0; i < iChunks; i++) {
                	    	strChunkFile = strTempPath + File.separator + i + "_" + strSessionID + "_" + strOriginalFileName;
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
                	    // 从临时目录复制到正式文件目录
                	    try {
                	    	Files.copy(java.nio.file.Paths.get(strTempPath + File.separator + strOriginalFileName), java.nio.file.Paths.get(strPath + "\\" + strOriginalFileName), StandardCopyOption.REPLACE_EXISTING);
                	    } catch(Exception e) {
                	    }
            	    	
                		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{strNewFileName}));
                	    ShiroUser shiroUser = SecurityUtils.getShiroUser();
                	    log.info(shiroUser.getLoginName() + "上传了" + strOriginalFileName);
    				
                        // 返回文件名，客户端根据文件名请求处理文件
                	    return AjaxObject.newOk("发布成功！").toString();
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
            	uploadedFile = new File(strTempPath, strNewFileName);
            	try {
    				org.apache.commons.io.FileUtils.writeByteArrayToFile(uploadedFile, file.getBytes());
            	    ShiroUser shiroUser = SecurityUtils.getShiroUser();
            	    log.info(shiroUser.getLoginName() + "上传了" + strNewFileName);
    				LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{strOriginalFileName}));
    			} catch (IOException e) {
    				log.error(e.getMessage());
    				return AjaxObject.newError("发布失败！").toString();
    			}
            	
            	// 从临时目录复制到正式文件目录
        	    try {
        	    	Files.copy(java.nio.file.Paths.get(strTempPath + File.separator + strNewFileName), java.nio.file.Paths.get(strPath + File.separator + strNewFileName), StandardCopyOption.REPLACE_EXISTING);
        	    } catch(Exception e) {
        	    }
        	    
                // 返回文件名，客户端根据文件名请求处理文件
        	    return AjaxObject.newOk("发布成功！").toString();
            }

        }
        catch (Exception e)
        {
            // 上传失败，IO异常！
            e.printStackTrace();
            log.error("--- UPLOAD FAIL ---");
            log.error(e.getMessage());
        }
		
        return AjaxObject.newOk("发布成功！").toString();
	}
	
}

