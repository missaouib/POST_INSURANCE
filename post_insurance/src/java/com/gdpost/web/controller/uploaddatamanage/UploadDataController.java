package com.gdpost.web.controller.uploaddatamanage;

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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.gdpost.utils.SecurityUtils;
import com.gdpost.utils.TemplateHelper.Template.FileTemplate;
import com.gdpost.utils.UploadDataHelper.UploadDataUtils;
import com.gdpost.web.entity.main.User;
import com.gdpost.web.log.Log;
import com.gdpost.web.log.LogLevel;
import com.gdpost.web.log.LogMessageObject;
import com.gdpost.web.log.LogModule;
import com.gdpost.web.log.impl.LogUitls;
import com.gdpost.web.service.uploaddatamanage.UploadDataService;
import com.gdpost.web.shiro.ShiroUser;
import com.gdpost.web.util.DoRst;

@Controller
@RequestMapping("/uploaddatamanage/uploaddata")
public class UploadDataController {
	private static final Logger log = LoggerFactory.getLogger(UploadDataController.class);
	
	@Autowired
	private UploadDataService uploadDataService;
	
	private static final String UPLOAD = "uploaddatamanage/uploaddata/upload";
	private static final String WEBUPLOAD = "uploaddatamanage/uploaddata/webupload";
	private static final String INDEX = "uploaddatamanage/uploaddata/index";
	
	String strError = "{\"jsonrpc\":\"2.0\",\"result\":\"error\",\"id\":\"id\",\"message\":\"操作失败。\"}";
	
	@RequiresPermissions(value={"UploadIssue:upload", "UploadData:upload", 
			"UploadRenewed:upload", "UploadCallFail:upload", "UploadCheck:upload", 
			"UploadPay:upload", "UploadIssue:upload", "CityUpload:upload"}, logical=Logical.OR)
	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String preUpload(HttpServletRequest request, Map<String, Object> map) {
		int ny = UploadDataUtils.getNianYue();
		int lastNY = UploadDataUtils.getLastNianYue();
		//int nextNY = UploadDataUtils.getNextNianYue();
		int lastNY2 = UploadDataUtils.getLastNianYue(lastNY);
		
		List<Integer> listNY = new ArrayList<Integer>();
		//listNY.add(nextNY);
		listNY.add(ny);
		listNY.add(lastNY);
		listNY.add(lastNY2);
		
		map.put("ny", listNY);
		
		// 当前日期
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int iDate = calendar.get(Calendar.DAY_OF_MONTH);
		map.put("currentNY", ny);
		map.put("lastNY", lastNY);
		map.put("today", iDate);
		
		return UPLOAD;
	}
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(HttpServletRequest request, Map<String, Object> map) {
		
		return INDEX;
	}
	
	@RequiresPermissions(value={"UploadIssue:upload", "UploadData:upload", 
			"UploadRenewed:upload", "UploadCallFail:upload", "UploadCheck:upload", 
			"UploadPay:upload", "UploadIssue:upload", "CityUpload:upload"}, logical=Logical.OR)
	@RequestMapping(value = "/toWebUpload", method = RequestMethod.GET)
	public String toWebUpload(HttpServletRequest request, Map<String, Object> map) {
		int ny = UploadDataUtils.getNianYue();
		int lastNY = UploadDataUtils.getLastNianYue();
		//int nextNY = UploadDataUtils.getNextNianYue();
		int lastNY2 = UploadDataUtils.getLastNianYue(lastNY);
		
		List<Integer> listNY = new ArrayList<Integer>();
		//listNY.add(nextNY);
		listNY.add(ny);
		listNY.add(lastNY);
		listNY.add(lastNY2);
		
		map.put("ny", listNY);
		// 当前日期
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int iDate = calendar.get(Calendar.DAY_OF_MONTH);
		map.put("currentNY", ny);
		map.put("lastNY", lastNY);
		map.put("today", iDate);
		
		return WEBUPLOAD;
	}

	@Log(message="上传了{0}。", level=LogLevel.WARN, module=LogModule.WJSC)
	@RequiresPermissions(value={"UploadIssue:upload", "UploadData:upload", 
			"UploadRenewed:upload", "UploadCallFail:upload", "UploadCheck:upload", 
			"UploadPay:upload", "UploadIssue:upload", "CityUpload:upload"}, logical=Logical.OR)
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public @ResponseBody String upload(HttpServletRequest request, @RequestParam String name, @RequestParam(value = "file", required = true) MultipartFile file) {
		log.debug("------------------UploadDateController-------------------");
        String strFileGroup = request.getParameter("fileGroup"); // 当前文件组
        int iCurrentChunk = 1;
        int iChunks = 1;
        try {
        	iCurrentChunk = Integer.parseInt(request.getParameter("chunk")); //当前分块
        	iChunks = Integer.parseInt(request.getParameter("chunks"));//总的分块数量
        } catch(Exception e) {
        }
        
        int iNY = UploadDataUtils.getNianYue();
        try {
        	iNY = Integer.parseInt(request.getParameter("ny")); //上传数据年月
        } catch(Exception e) {
        }
        
        long lFileSize = Long.parseLong(request.getParameter("fileSize"));//文件大小
        int iChunkSize = Integer.parseInt(request.getParameter("chunkSize")); //分块大小
        String strOriginalFileName = name;
        String strSessionID = request.getSession().getId();
        
		//String strPath = UploadDataUtils.getFileStorePath(request);
		String strPath = UploadDataUtils.getFileStorePath(request, iNY, "UPDATE");
		String strTempPath = UploadDataUtils.getFileStoreTempPath(request);
		String strFileName = file.getOriginalFilename();
		
		String strNewFileName = strOriginalFileName;
        if (iChunks > 1) {
        	strNewFileName = iCurrentChunk + "_" + strSessionID + "_" + strOriginalFileName;   //按文件块重命名块文件
        }
		
        // 分块上传，记录当前已上传块数，根据文件名、文件大小、块大小、块数
        com.gdpost.utils.UploadDataHelper.SessionChunk sessionChunk = new com.gdpost.utils.UploadDataHelper.SessionChunk();
        com.gdpost.utils.UploadDataHelper.FileChunk fileChunk = sessionChunk.getSessionChunk(request);
        if(fileChunk == null) {
            fileChunk = new com.gdpost.utils.UploadDataHelper.FileChunk();
        }
        
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
                    return("{\"jsonrpc\":\"2.0\",\"result\":\"success\",\"id\":\"id\",\"strFileName\":\"" + strOriginalFileName + "\",\"message\":\"\"}");
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
		
		return (strError);
	}
	
	@Log(message="{0}", level=LogLevel.WARN, module=LogModule.WJSC)
	@RequiresPermissions(value={"UploadIssue:upload", "UploadData:upload", 
			"UploadRenewed:upload", "UploadCallFail:upload", "UploadCheck:upload", 
			"UploadPay:upload", "UploadIssue:upload", "CityUpload:upload"}, logical=Logical.OR)
	@RequestMapping(value = "/import", method = RequestMethod.POST)
	public @ResponseBody String doImport(HttpServletRequest request, @RequestParam String strFileGroup, @RequestParam int ny, @RequestParam String template, @RequestParam String memo, @RequestParam int shouldFileNum) {
		log.debug("-----------no admin--------------import data by use template: " + template);
		com.gdpost.utils.UploadDataHelper.SessionChunk sessionChunk = new com.gdpost.utils.UploadDataHelper.SessionChunk();
		com.gdpost.utils.UploadDataHelper.FileChunk fileChunk = sessionChunk.getSessionChunk(request);
		if(fileChunk == null) {
			return(strError);
		}
		
	    ShiroUser shiroUser = SecurityUtils.getShiroUser();
	    User member = shiroUser.getUser();
	    long member_id = member.getId();
	    //int currentNY = UploadDataUtils.getNianYue();
	    //int lastNY = UploadDataUtils.getLastNianYue();
	    int currentNY = ny;
	    int lastNY = UploadDataUtils.getLastNianYue();
	    String strMessage = ""; // 返回客户端的详细信息
	    //boolean bFlag = false;
	    DoRst dr = null;
	    StringBuilder builder = new StringBuilder();
	    
	    List<String> listFiles = fileChunk.getListFileName();
		log.debug("------------------" + listFiles);
		log.debug(" ---------------" + shouldFileNum);
		int uc = 0;
		if(fileChunk.getFileGroup().equals(strFileGroup) && listFiles.size()== shouldFileNum) {
			log.info(" ---------- 开始导入以下文件：" + listFiles);
			log.debug("--------------- do import:" + strFileGroup);
			FileTemplate ft = FileTemplate.valueOf(template);
			log.debug("--------------- do import template:" + ft);
			//synchronized (this) {
			dr = uploadDataService.handleData(ft, request, member_id, listFiles, currentNY, lastNY, shiroUser.getId(), shiroUser.getLoginName(), 0, builder, memo);
			uc += dr.getUpdateRow();
			//}
		} else {
			return("{\"jsonrpc\":\"2.0\",\"result\":\"success\",\"id\":\"id\",\"message\":\"等待所有文件上传完毕。\"}");
		}
		
	    // 请SessionChunk
	    sessionChunk.clear(request);
	    strMessage = dr.getMsg();// + "," + builder.toString();
	    
	    if(dr.isFlag()) {
		    
	    	LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{"导入了" + template + "的" + currentNY + "数据。共" + shouldFileNum + "个文件共导入/更新了" + uc + "记录。" + dr.getMsg()}));
	    	
	    	if(strMessage != null && !strMessage.equals("")) {
				// 如有数据检查提示，则提示，如确认不导入，则提交request执行清除
	    		return("{\"jsonrpc\":\"2.0\",\"result\":\"confirm\",\"id\":\"id\",\"message\":\"共：" + dr.getNum() + "条记录；共导入/更新了：" + uc + "条记录。" + strMessage + "\"}");
	    	} else {
	    		return("{\"jsonrpc\":\"2.0\",\"result\":\"success\",\"id\":\"id\",\"message\":\"共：" + dr.getNum() + "条记录；共导入/更新了：" + uc + "条记录，\"}");
	    	}
	    } else {
	    	LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{"导入" + template + "的数据出错，" + strMessage + "。"}));
	    	
	    	uploadDataService.setImportDone(request, member_id, currentNY, shiroUser.getId(), shiroUser.getLoginName(), 0, memo);

	    	return("{\"jsonrpc\":\"2.0\",\"result\":\"error\",\"id\":\"id\",\"message\":\"" + strMessage + "\"}");
	    }
	}
	
	@Log(message="取消上传{0}月数据。", level=LogLevel.WARN, module=LogModule.WJSC)
	@RequiresPermissions(value={"UploadIssue:upload", "UploadData:upload", 
			"UploadRenewed:upload", "UploadCallFail:upload", "UploadCheck:upload", 
			"UploadPay:upload", "UploadIssue:upload", "CityUpload:upload"}, logical=Logical.OR)
	@RequestMapping(value = "/cancelupload", method = RequestMethod.POST)
	public @ResponseBody String cancelUpload(HttpServletRequest request, @RequestParam String strFileGroup, @RequestParam int ny) {
	    //ShiroUser shiroUser = SecurityUtils.getShiroUser();
	    //long member_id = shiroUser.getId();
	    //User member = shiroUser.getUser();
	    //long member_id = member.getId();
	    
	    //int currentNY = UploadDataUtils.getNianYue();
	    int currentNY = ny;
	    String strMessage = "取消导入成功。"; // 返回客户端的详细信息
	    //boolean bFlag = true;
	    
	    //bFlag = uploadDataService.clearImport(request, member_id, currentNY);
	    //bFlag = uploadDataService.clearImportDone(request, member_id, currentNY);
	    
	    LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{currentNY}));
	    return("{\"jsonrpc\":\"2.0\",\"result\":\"success\",\"id\":\"id\",\"message\":\"" + strMessage + "\"}");
    }

	@Log(message="取消导入{0}月数据。", level=LogLevel.WARN, module=LogModule.WJSC)
	@RequiresPermissions(value={"UploadIssue:upload", "UploadData:upload", 
			"UploadRenewed:upload", "UploadCallFail:upload", "UploadCheck:upload", 
			"UploadPay:upload", "UploadIssue:upload", "CityUpload:upload"}, logical=Logical.OR)
	@RequestMapping(value = "/cancelimport", method = RequestMethod.POST)
	public @ResponseBody String clearImport(HttpServletRequest request, @RequestParam String strFileGroup, @RequestParam int ny) {
	    //ShiroUser shiroUser = SecurityUtils.getShiroUser();
	    //long member_id = shiroUser.getId();
	    //User member = shiroUser.getUser();
	    //long member_id = member.getId();
	    
	    //int currentNY = UploadDataUtils.getNianYue();
	    int currentNY = ny;
	    String strMessage = "取消导入成功。"; // 返回客户端的详细信息
	    //boolean bFlag = true;
	    
	    //bFlag = uploadDataService.clearImport(request, member_id, currentNY);
	    //bFlag = uploadDataService.clearImportDone(request, member_id, currentNY);
	    
	    LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{currentNY}));
	    return("{\"jsonrpc\":\"2.0\",\"result\":\"success\",\"id\":\"id\",\"message\":\"" + strMessage + "\"}");
    }
	
	@RequiresPermissions(value={"UploadIssue:upload", "UploadData:upload", 
			"UploadRenewed:upload", "UploadCallFail:upload", "UploadCheck:upload", 
			"UploadPay:upload", "UploadIssue:upload", "CityUpload:upload"}, logical=Logical.OR)
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	public @ResponseBody String check(HttpServletRequest request) {
		String strFileName = request.getParameter("fileName"); //当前分块
        long lFileSize = Long.parseLong(request.getParameter("fileSize"));//文件大小
        int iChunkSize = Integer.parseInt(request.getParameter("chunkSize")); //分块大小
        long lLastOffset = 0;
        
        com.gdpost.utils.UploadDataHelper.SessionChunk sessionChunk = new com.gdpost.utils.UploadDataHelper.SessionChunk();
        lLastOffset = sessionChunk.checkCurrentOffset(request, strFileName, lFileSize, iChunkSize);
        
        String strReturn = "{\"lastOffset\":" + lLastOffset + "}";
		
		return(strReturn);
	}

	@Log(message="{0}", level=LogLevel.WARN, module=LogModule.WJSC)
	@RequestMapping(value = "/callImport", method = RequestMethod.POST)
	public @ResponseBody String callImport(HttpServletRequest request, @RequestParam String fileName, @RequestParam String template) {
		log.debug("-----------no admin--------------import data by use template: " + template);
		log.debug("-----------no admin--------------import data by filename: " + fileName);
	    String strMessage = ""; // 返回客户端的详细信息
	    long member_id = 1;
	    //int currentNY = UploadDataUtils.getNianYue();;
	    //int lastNY = UploadDataUtils.getLastNianYue();
	    DoRst dr = null;
	    StringBuilder builder = new StringBuilder();
	    
		int uc = 0;
		String strFilePath = UploadDataUtils.getFileStoreTempPath(request);
		File file = new File(strFilePath + File.separator + fileName);
		log.debug("-----------no admin--------------: " + file);
		if(file != null && file.exists() && file.canRead()) {
			log.info(" ---------- 开始导入以下文件：" + file.getAbsolutePath() + file.getName());
			FileTemplate ft = FileTemplate.valueOf(template);
			log.debug("--------------- do import template:" + ft);
			List<String> fileStrs = new ArrayList<String>();
			fileStrs.add(fileName);
			dr = uploadDataService.handleData(ft, request, member_id, fileStrs, 0, 0, member_id, "System", 0, builder, null);
			uc += dr.getUpdateRow();
		} else {
			return("{\"jsonrpc\":\"2.0\",\"result\":\"error\",\"id\":\"id\",\"message\":\"文件不存在或者文件不可读。\"}");
		}
		
	    strMessage = dr.getMsg();
	    
	    if(dr.isFlag()) {
		    
	    	LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{"导入了" + template + "的数据。共1个文件共导入/更新了" + uc + "记录。" + dr.getMsg()}));
	    	
	    	if(strMessage != null && !strMessage.equals("")) {
				// 如有数据检查提示，则提示，如确认不导入，则提交request执行清除
	    		return("{\"jsonrpc\":\"2.0\",\"result\":\"success\",\"id\":\"id\",\"message\":\"共：" + dr.getNum() + "条记录；共导入/更新了：" + uc + "条记录。" + strMessage + "\"}");
	    	} else {
	    		return("{\"jsonrpc\":\"2.0\",\"result\":\"success\",\"id\":\"id\",\"message\":\"共：" + dr.getNum() + "条记录；共导入/更新了：" + uc + "条记录，\"}");
	    	}
	    } else {
	    	LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{"导入" + template + "的数据出错，" + strMessage + "。"}));
	    	
	    	uploadDataService.setImportDone(request, member_id, 0, 1, "System", 0, null);
	
	    	return("{\"jsonrpc\":\"2.0\",\"result\":\"error\",\"id\":\"id\",\"message\":\"" + strMessage + "\"}");
	    }
	}
	
}
