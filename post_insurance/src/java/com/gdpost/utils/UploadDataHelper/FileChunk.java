package com.gdpost.utils.UploadDataHelper;

import java.util.ArrayList;
import java.util.List;

public class FileChunk {
	private String strSessionID = "";
	private String strFileGroup = "";
	private String strFileName = "";// 当前文件名
	private long lFileSize = 0;// 当前文件大小
	private int iCurrentChunk = 0;// 当前文件分块
	private int iChunks = 0;// 当前文件分块数
	private int iChunkSize = 512;// 当前文件块大小
	private long lLastOffset = 0;// 当前文件上传位移
	private List<String> listFileName = new ArrayList<String>();
	
	public String getSessionID() {
		return strSessionID;
	}
	public void setSessionID(String strSessionID) {
		this.strSessionID = strSessionID;
	}
	public String getFileName() {
		return strFileName;
	}
	public void setFileName(String strFileName) {
		this.strFileName = strFileName;
	}
	public long getFileSize() {
		return lFileSize;
	}
	public void setFileSize(long lFileSize) {
		this.lFileSize = lFileSize;
	}
	public int getCurrentChunk() {
		return iCurrentChunk;
	}
	public void setCurrentChunk(int iCurrentChunk) {
		this.iCurrentChunk = iCurrentChunk;
	}
	public int getChunks() {
		return iChunks;
	}
	public void setChunks(int iChunks) {
		this.iChunks = iChunks;
	}
	public int getChunkSize() {
		return iChunkSize;
	}
	public void setChunkSize(int iChunkSize) {
		this.iChunkSize = iChunkSize;
	}
	public long getLastOffset() {
		return lLastOffset;
	}
	public void setLastOffset(long iLastOffset) {
		this.lLastOffset = iLastOffset;
	}
	public String getFileGroup() {
		return strFileGroup;
	}
	public void setFileGroup(String strFileGroup) {
		this.strFileGroup = strFileGroup;
	}
	public List<String> getListFileName() {
		return listFileName;
	}
	public void setListFileName(String strFileName) {
		if(!this.listFileName.contains(strFileName)) {
			this.listFileName.add(strFileName);
		}
	}
}
