package com.gdpost.web.controller.uploadadmin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.gdpost.utils.SecurityUtils;
import com.gdpost.utils.TemplateHelper.Template;
import com.gdpost.utils.TemplateHelper.Template.FileTemplate;
import com.gdpost.utils.UploadDataHelper.UploadDataUtils;
import com.gdpost.web.SecurityConstants;
import com.gdpost.web.entity.main.Role;
import com.gdpost.web.log.Log;
import com.gdpost.web.log.LogLevel;
import com.gdpost.web.log.LogMessageObject;
import com.gdpost.web.log.LogModule;
import com.gdpost.web.log.impl.LogUitls;
import com.gdpost.web.shiro.ShiroUser;

@Controller
@RequestMapping("/management/uploaddata")
public class UploadController {
	private static final Logger log = LoggerFactory.getLogger(UploadController.class);
	
//	@Autowired
//	private UploadDataService uploadDataService;
//	
//	@Autowired
//	private UserService userService;
	
	private static final String UPLOAD = "management/uploaddata/upload";
	
	String strError = "{\"jsonrpc\":\"2.0\",\"result\":\"error\",\"id\":\"id\",\"message\":\"操作失败。\"}";
	
	@RequiresPermissions(value={"UploadIssue:upload", "UploadData:upload", 
			"UploadRenewed:upload", "UploadCallFail:upload", "UploadCheck:upload", 
			"UploadPay:upload", "UploadIssue:upload", "UploadBq:upload", "CityUpload:upload"}, logical=Logical.OR)
	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String preUpload(HttpServletRequest request, Map<String, Object> map) {
		int ny = UploadDataUtils.getNianYue();
		
		@SuppressWarnings("unchecked")
		List<Role> roles = (List<Role>) request.getSession().getAttribute(SecurityConstants.LOGIN_USER_ROLE);
		List<String> roleDefine = new ArrayList<String>();
		boolean isCity = false;
		for(Role r:roles) {
			if(r.getName().contains("地市") || r.getName().contains("县市")) {
				isCity = true;
			}
			if(r.getName().contains("契约") || r.getName().contains("管理员")) {
				roleDefine.add("QY");
			}
			if(r.getName().contains("保全") || r.getName().contains("管理员")) {
				roleDefine.add("BQ");
			}
			if(r.getName().contains("客服") || r.getName().contains("管理员")) {
				roleDefine.add("KF");
			}
			if(r.getName().contains("回访") || r.getName().contains("管理员")) {
				roleDefine.add("HF");
			}
			if(r.getName().contains("续期") || r.getName().contains("管理员")) {
				roleDefine.add("XQ");
			}
			if(r.getName().contains("理赔") || r.getName().contains("管理员")) {
				roleDefine.add("LP");
			}
		}
		if (!isCity) {
			roleDefine.add("ALL");
		}
		FileTemplate[] fts = FileTemplate.values();
		List<FileTemplate> temp = new ArrayList<FileTemplate>();
		for(FileTemplate ft:fts) {
			if(isCity && ft.getDesc().contains("市县")) {
				temp.add(ft);
			} else {
				for(String t:roleDefine) {
					if(ft.getType().equals(t)) {
						temp.add(ft);
					}
				}
			}
		}
		List<FileTemplate> disTemp = temp.stream().distinct().collect(Collectors.toList());
		FileTemplate[] ftl = new FileTemplate[disTemp.size()];
		map.put("templateList", disTemp.toArray(ftl));
		FileTemplate ft = FileTemplate.Policy;
		String type = request.getParameter("type");
		if (type != null) {
			switch(type) {
			case "issue":
				//ft = FileTemplate.Issue;
				break;
				/*
			case "renewed":
				ft = FileTemplate.Renewed;
				break;
			case "city":
				ft = FileTemplate.RenewedCityList;
				break;
			case "callfail":
				ft = FileTemplate.CallFail;
				*/
			case "check":
				ft = FileTemplate.CheckWrite;
				break;
			case "payto":
				ft = FileTemplate.PayToFailList;
				break;
			case "payfrom":
				ft = FileTemplate.PayFromFailList;
				break;
			case "conversatoinreq":
				ft = FileTemplate.ConversationReq;
				break;
			}
		}
		//log.debug("-------------------- uplodate list type:" + ft);
		Template t = new Template();
		t.setTemplateValue(ft.toString());
		request.setAttribute("myTemplate", t);
		
		map.put("ny", ny);
		
		return UPLOAD;
	}

	@Log(message="上传了{0}。", level=LogLevel.WARN, module=LogModule.WJSC)
	@RequiresPermissions(value={"UploadIssue:upload", "UploadData:upload", "UploadRenewed:upload", 
			"UploadCallFail:upload", "UploadCheck:upload", 
			"UploadPay:upload", "UploadIssue:upload", "CityUpload:upload"}, logical=Logical.OR)
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public @ResponseBody String upload(HttpServletRequest request, @RequestParam String name, @RequestParam(value = "file", required = true) MultipartFile file) {
        log.debug("------------------UploadController ADMIN-------------------upload");
        try
        {
            Long lFileSize = Long.parseLong(request.getParameter("size"));
            int iNY = UploadDataUtils.getNianYue();
            String strPath = UploadDataUtils.getFileStorePath(request, iNY, "UPDATE");
    		String strTempPath = UploadDataUtils.getFileStoreTempPath(request);
    		String strFileName = file.getOriginalFilename();
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
                	    	Files.copy(java.nio.file.Paths.get(strTempPath + File.separator + strOriginalFileName), java.nio.file.Paths.get(strPath + File.separator + strOriginalFileName), StandardCopyOption.REPLACE_EXISTING);
                	    } catch(Exception e) {
                	    }
            	    	
                		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{strNewFileName}));
                	    ShiroUser shiroUser = SecurityUtils.getShiroUser();
                	    log.info(shiroUser.getLoginName() + "上传了" + strOriginalFileName);
    				
                        // 返回文件名，客户端根据文件名请求处理文件
                        return("{\"jsonrpc\":\"2.0\",\"result\":\"success\",\"id\":\"id\",\"strFileName\":\"" + strOriginalFileName + "\",\"message\":\"已上传\"}");
            		} catch (FileNotFoundException e) {
            			log.error(e.getMessage());
    					return (strError);
    				} catch (IOException e) {
    					log.error(e.getMessage());
    					return (strError);						
    				} catch (Exception e) {
    					log.error(e.getMessage());
    					return (strError);						
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
            	uploadedFile = new File(strTempPath, strNewFileName);
            	try {
    				org.apache.commons.io.FileUtils.writeByteArrayToFile(uploadedFile, file.getBytes());
            	    ShiroUser shiroUser = SecurityUtils.getShiroUser();
            	    log.info(shiroUser.getLoginName() + "上传了" + strNewFileName);
    				LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{strOriginalFileName}));
    			} catch (IOException e) {
    				log.error(e.getMessage());
    				return (strError);
    			}
            	
            	// 从临时目录复制到正式文件目录
        	    try {
        	    	Files.copy(java.nio.file.Paths.get(strTempPath + File.separator + strNewFileName), java.nio.file.Paths.get(strPath + File.separator + strNewFileName), StandardCopyOption.REPLACE_EXISTING);
        	    } catch(Exception e) {
        	    }
        	    
                // 返回文件名，客户端根据文件名请求处理文件
                return("{\"jsonrpc\":\"2.0\",\"result\":\"success\",\"id\":\"id\",\"strFileName\":\"" + strFileName + "\",\"message\":\"\"}");
            }

        }
        catch (Exception e)
        {
            // 上传失败，IO异常！
            e.printStackTrace();
            log.error("--- UPLOAD FAIL ---");
            log.error(e.getMessage());
        }
		
		return (strError);
	}
	
	@RequiresPermissions(value={"UploadIssue:upload", "UploadData:upload", "UploadRenewed:upload", 
			"UploadCallFail:upload", "UploadCheck:upload", 
			"UploadPay:upload", "UploadIssue:upload", "CityUpload:upload"}, logical=Logical.OR)
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	public @ResponseBody String check(HttpServletRequest request) {
		log.debug("-------------------------------------upload check");
		String strFileName = request.getParameter("fileName"); //当前分块
		log.debug("-------------------------------------upload check filename:" + strFileName);
        long lFileSize = Long.parseLong(request.getParameter("fileSize"));//文件大小
        int iChunkSize = Integer.parseInt(request.getParameter("chunkSize")); //分块大小
        long lLastOffset = 0;
        
        com.gdpost.utils.UploadDataHelper.SessionChunk sessionChunk = new com.gdpost.utils.UploadDataHelper.SessionChunk();
        lLastOffset = sessionChunk.checkCurrentOffset(request, strFileName, lFileSize, iChunkSize);
        
        String strReturn = "{\"lastOffset\":" + lLastOffset + "}";
		
		return(strReturn);
	}
}

