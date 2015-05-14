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
import com.gdpost.utils.UploadDataHelper.UploadDataUtils;
import com.gdpost.web.entity.member.TblMember;
import com.gdpost.web.log.Log;
import com.gdpost.web.log.LogMessageObject;
import com.gdpost.web.log.impl.LogUitls;
import com.gdpost.web.service.member.MemberService;
import com.gdpost.web.service.uploaddatamanage.UploadDataService;
import com.gdpost.web.shiro.ShiroUser;

@Controller
@RequestMapping("/uploaddatamanage/uploaddata")
public class UploadDataController {
	private static final Logger log = LoggerFactory.getLogger(UploadDataController.class);
	
	@Autowired
	private UploadDataService uploadDataService;
	
	@Autowired
	private MemberService memberService;
	
	private static final String UPLOAD = "uploaddatamanage/uploaddata/upload";
	private static final String WEBUPLOAD = "uploaddatamanage/uploaddata/webupload";
	private static final String INDEX = "uploaddatamanage/uploaddata/index";
	
	String strError = "{\"jsonrpc\":\"2.0\",\"result\":\"error\",\"id\":\"id\",\"message\":\"操作失败。\"}";
	
	@RequiresPermissions("UploadData:Upload")
	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String preUpload(HttpServletRequest request, Map<String, Object> map) {
		int ny = UploadDataUtils.getNianYue();
		int lastNY = UploadDataUtils.getLastNianYue();
		int nextNY = UploadDataUtils.getNextNianYue();
		int lastNY2 = UploadDataUtils.getLastNianYue(lastNY);
		
		List<Integer> listNY = new ArrayList<Integer>();
		//listNY.add(nextNY);
		listNY.add(ny);
		listNY.add(lastNY);
		listNY.add(lastNY2);
		
		map.put("ny", listNY);
		
		return UPLOAD;
	}
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(HttpServletRequest request, Map<String, Object> map) {
		
		return INDEX;
	}
	
	@RequiresPermissions("UploadData:Upload")
	@RequestMapping(value = "/toWebUpload", method = RequestMethod.GET)
	public String toWebUpload(HttpServletRequest request, Map<String, Object> map) {
		int ny = UploadDataUtils.getNianYue();
		int lastNY = UploadDataUtils.getLastNianYue();
		int nextNY = UploadDataUtils.getNextNianYue();
		int lastNY2 = UploadDataUtils.getLastNianYue(lastNY);
		
		List<Integer> listNY = new ArrayList<Integer>();
		//listNY.add(nextNY);
		listNY.add(ny);
		listNY.add(lastNY);
		listNY.add(lastNY2);
		
		map.put("ny", listNY);
		
		return WEBUPLOAD;
	}

	@RequiresPermissions("UploadData:Upload")
	@RequestMapping(value = "/checkimportny", method = RequestMethod.POST)
	public @ResponseBody String checkImportNY(HttpServletRequest request) {
		//TblMemberUser shiroUser = SecurityUtils.getLoginTblMemberUser();	
		//long member_id = shiroUser.getTblMember().getId();
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
	    //TblMember member = shiroUser.getMemberUser().getTblMember();
	    // 只增加一级连锁的积分，需要判断当前连锁是否一级连锁
//	    while(member.getParent().getId() != 1)
//	    {
//	    	member = member.getParent();
//	    }
	    long member_id = shiroUser.getUser().getId();
		boolean bFlag = true;
		bFlag = uploadDataService.checkImportNY(request);
		
	    if(bFlag) {
	    	return("{\"jsonrpc\":\"2.0\",\"result\":\"success\",\"id\":\"id\",\"message\":\"\"}");
	    } else {
	    	return("{\"jsonrpc\":\"2.0\",\"result\":\"error\",\"id\":\"id\",\"message\":\"该年月已经上传过数据。\"}");
	    }
	}
		
	@Log(message="上传了{0}。")
	@RequiresPermissions("UploadData:Upload")
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public @ResponseBody String upload(HttpServletRequest request, @RequestParam String name, @RequestParam(value = "file", required = true) MultipartFile file) {
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
		String strPath = UploadDataUtils.getFileStorePath(request, iNY);
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
            		FileOutputStream fos = new FileOutputStream(strTempPath + "\\" + strOriginalFileName);
            		outChannel = fos.getChannel();
            		String strChunkFile = "";
            		ByteBuffer bb = ByteBuffer.allocate(BUFSIZE);
            	    for (int i = 0; i < iChunks; i++) {
            	    	strChunkFile = strTempPath + "\\" + i + "_" + strSessionID + "_" + strOriginalFileName;
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
            	    	Files.copy(java.nio.file.Paths.get(strTempPath + "\\" + strOriginalFileName), java.nio.file.Paths.get(strPath + "\\" + strOriginalFileName), StandardCopyOption.REPLACE_EXISTING);
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
    	    	Files.copy(java.nio.file.Paths.get(strTempPath + "\\" + strNewFileName), java.nio.file.Paths.get(strPath + "\\" + strNewFileName), StandardCopyOption.REPLACE_EXISTING);
    	    } catch(Exception e) {
    	    }
    	    
            // 返回文件名，客户端根据文件名请求处理文件
            return("{\"jsonrpc\":\"2.0\",\"result\":\"success\",\"id\":\"id\",\"strFileName\":\"" + strFileName + "\",\"message\":\"\"}");
        }
		
		return (strError);
	}
	
	@Log(message="{0}")
	@RequiresPermissions("UploadData:Upload")
	@RequestMapping(value = "/import", method = RequestMethod.POST)
	public @ResponseBody String doImport(HttpServletRequest request, @RequestParam String strFileGroup, @RequestParam String memo) {
		com.gdpost.utils.UploadDataHelper.SessionChunk sessionChunk = new com.gdpost.utils.UploadDataHelper.SessionChunk();
		com.gdpost.utils.UploadDataHelper.FileChunk fileChunk = sessionChunk.getSessionChunk(request);
		if(fileChunk == null) {
			return(strError);
		}
		
	    ShiroUser shiroUser = SecurityUtils.getShiroUser();
//	    TblMember member = shiroUser.getMemberUser().getTblMember();
	    // 只增加一级连锁的积分，需要判断当前连锁是否一级连锁
//	    while(member.getParent().getId() != 1)
//	    {
//	    	member = member.getParent();
//	    }
//	    long member_id = member.getId();
	    long member_id = shiroUser.getUser().getId();
	    //int currentNY = UploadDataUtils.getNianYue();
	    //int lastNY = UploadDataUtils.getLastNianYue();
	    //int currentNY = ny;
	    //int lastNY = UploadDataUtils.getLastNianYue(currentNY);
	    String strMessage = ""; // 返回客户端的详细信息
	    boolean bFlag = true;
	    StringBuilder builder = new StringBuilder();
	    
		if(fileChunk.getFileGroup().equals(strFileGroup)) {
			List<String> listFiles = fileChunk.getListFileName();
			bFlag = uploadDataService.handleData(request, listFiles, shiroUser.getId(), shiroUser.getLoginName(), 0, builder, memo);
		}
		
	    // 请SessionChunk
	    sessionChunk.clear(request);
	    strMessage = builder.toString();
	    
	    // 上传文件，加10分积分
	    //member.setScore(member.getScore() + 10);
	    //memberService.saveOrUpdate(member);
	    
	    // 每月11日前，加10分积分
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int iToDay = calendar.get(Calendar.DATE);
	    if(iToDay < 11)
	    {
		    //member.setScore(member.getScore() + 10);
		    //memberService.saveOrUpdate(member);
	    }
	    
	    if(bFlag) {
	    	// 导入数据，加50分积分
		    //member.setScore(member.getScore() + 50);
		   // memberService.saveOrUpdate(member);
		    
	    	LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{"导入了数据。"}));
	    	
	    	if(!strMessage.equals("")) {
				// 如有数据检查提示，则提示，如确认不导入，则提交request执行清除
	    		return("{\"jsonrpc\":\"2.0\",\"result\":\"confirm\",\"id\":\"id\",\"message\":\"" + strMessage + "\"}");
	    	} else {
	    		return("{\"jsonrpc\":\"2.0\",\"result\":\"success\",\"id\":\"id\",\"message\":\"" + strMessage + "\"}");
	    	}
	    } else {
	    	LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{"导入数据出错，" + strMessage + "。"}));
	    	
	    	uploadDataService.setImportDone(request, shiroUser.getId(), shiroUser.getLoginName(), 0, memo);
	    	//TblMember member = memberService.get(member_id);
//	    	if(member.getAlertMsg() != null && !"".equals(member.getAlertMsg())) {
//	    		strMessage = member.getAlertMsg();
//	    	}

	    	return("{\"jsonrpc\":\"2.0\",\"result\":\"error\",\"id\":\"id\",\"message\":\"" + strMessage + "\"}");
	    }
	}
	
	@Log(message="取消上传{0}月数据。")
	@RequiresPermissions("UploadData:Upload")
	@RequestMapping(value = "/cancelupload", method = RequestMethod.POST)
	public @ResponseBody String cancelUpload(HttpServletRequest request, @RequestParam String strFileGroup) {
	    ShiroUser shiroUser = SecurityUtils.getShiroUser();
	    //long member_id = shiroUser.getId();
//	    TblMember member = shiroUser.getMemberUser().getTblMember();
	    // 只增加一级连锁的积分，需要判断当前连锁是否一级连锁
//	    while(member.getParent().getId() != 1)
//	    {
//	    	member = member.getParent();
//	    }
//	    long member_id = member.getId();
	    long member_id = shiroUser.getUser().getId();
	    
	    int currentNY = UploadDataUtils.getNianYue();
	    //int currentNY = ny;
	    String strMessage = "取消导入成功。"; // 返回客户端的详细信息
	    boolean bFlag = true;
	    
	    bFlag = uploadDataService.clearImport(request);
	    bFlag = uploadDataService.clearImportDone(request);
	    // 取消增加的10积分
//	    member.setScore(member.getScore() - 10);
//	    memberService.saveOrUpdate(member);
	    
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int iToDay = calendar.get(Calendar.DATE);
	    if(iToDay < 11)
	    {
//		    member.setScore(member.getScore() - 10);
//		    memberService.saveOrUpdate(member);
	    }
	    
	    LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{currentNY}));
	    return("{\"jsonrpc\":\"2.0\",\"result\":\"success\",\"id\":\"id\",\"message\":\"" + strMessage + "\"}");
    }

	@Log(message="取消导入{0}月数据。")
	@RequiresPermissions("UploadData:Upload")
	@RequestMapping(value = "/cancelimport", method = RequestMethod.POST)
	public @ResponseBody String clearImport(HttpServletRequest request, @RequestParam String strFileGroup) {
	    ShiroUser shiroUser = SecurityUtils.getShiroUser();
	    //long member_id = shiroUser.getId();
//	    TblMember member = shiroUser.getMemberUser().getTblMember();
	    // 只增加一级连锁的积分，需要判断当前连锁是否一级连锁
//	    while(member.getParent().getId() != 1)
//	    {
//	    	member = member.getParent();
//	    }
//	    long member_id = member.getId();
	    long member_id = shiroUser.getUser().getId();
	    
	    int currentNY = UploadDataUtils.getNianYue();
	    //int currentNY = ny;
	    String strMessage = "取消导入成功。"; // 返回客户端的详细信息
	    boolean bFlag = true;
	    
	    bFlag = uploadDataService.clearImport(request);
	    bFlag = uploadDataService.clearImportDone(request);
	    // 取消增加的60积分
//	    member.setScore(member.getScore() - 60);
//	    memberService.saveOrUpdate(member);
	    
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int iToDay = calendar.get(Calendar.DATE);
	    if(iToDay < 11)
	    {
//		    member.setScore(member.getScore() - 10);
//		    memberService.saveOrUpdate(member);
	    }
	    
	    LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{currentNY}));
	    return("{\"jsonrpc\":\"2.0\",\"result\":\"success\",\"id\":\"id\",\"message\":\"" + strMessage + "\"}");
    }
	
	@RequiresPermissions("UploadData:Upload")
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
	
}