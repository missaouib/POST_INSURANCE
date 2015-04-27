package com.gdpost.utils.UploadDataHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionChunk {
	public static Logger log = LoggerFactory.getLogger(SessionChunk.class);
	private String strKey = "UploadDataFileChunks";

	public void clear(HttpServletRequest request) {
		javax.servlet.http.HttpSession session = request.getSession();
		if(session.getAttribute(strKey) != null) {
			session.removeAttribute(strKey);
		}
	}
	
	public FileChunk getSessionChunk(HttpServletRequest request) {
		javax.servlet.http.HttpSession session = request.getSession();
		FileChunk chunk = null;
		if(session.getAttribute(strKey) != null) {
			chunk = (FileChunk)session.getAttribute(strKey);
		}
		
		return chunk;
	}

	public void setSessionChunk(HttpServletRequest request, FileChunk chunk) {
		javax.servlet.http.HttpSession session = request.getSession();
		session.setAttribute(strKey, chunk);
	}
	
	public long checkCurrentOffset(HttpServletRequest request, String strFileName, long lFileSize, int iChunkSize) {
		long lLastOffset = 0;
		HttpSession session = request.getSession();
		FileChunk chunk = null;
		if(session.getAttribute(strKey) != null) {
			chunk = (FileChunk)session.getAttribute(strKey);
		}
		
		if(chunk != null && 
				chunk.getFileName().equals(strFileName) && 
				chunk.getFileSize() == lFileSize && 
				chunk.getChunkSize() == iChunkSize) {
            int iCurrentChunk = chunk.getCurrentChunk();
            lLastOffset = iCurrentChunk * iChunkSize * 1024;
		}
		
		return(lLastOffset);
	}
}
