package com.gdpost.utils.SwfHelper;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Pdf2Swf extends Common{

	HttpServletRequest request = null;

	public Pdf2Swf(HttpServletRequest request){
		this.request = request;
	}

	public String convert(String doc, String page) {
		String pdfFilePath	= separate(getConfig("path.pdf", "")) + doc;
		String swfFilePath	= separate(getConfig("path.swf", "")) + doc + page + ".swf";

		String command		= getConfig("cmd.conversion.singledoc", "");
		if("true".equals(getConfig("splitmode", "")))
			command = getConfig("cmd.conversion.splitpages", "");

		command = command.replace("{path.pdf}", separate(getConfig("path.pdf", "")));
		command = command.replace("{path.swf}", separate(getConfig("path.swf", "")));
		command = command.replace("{pdffile}", doc);

		try {
			if (!isNotConverted(pdfFilePath ,swfFilePath)) {
				return "[Converted]";
			}
		} catch (Exception ex) {
			return "Error," + ex.toString();
		}

		boolean return_var = false;

		if("true".equals(getConfig("splitmode", ""))){
			String pagecmd = command.replace("%", page);
			pagecmd = pagecmd + " -p " + page;

			return_var = exec(pagecmd);
			int hash = getStringHashCode(command);
			HttpSession session = request.getSession(true);
			String constr = "CONVERSION_" + hash;
			String conversion = (String) session.getAttribute(constr);
            if(conversion == null){
                exec(command);
                session.setAttribute(constr, "true");
            }
		}else
			return_var = exec(command);
		String s = "Error converting document, make sure the conversion tool is installed and that correct user permissions are applied to the SWF Path directory" + 
					getDocUrl();
		if(return_var) {
			s="[Converted]";
		}
		return s;
	}

	public String convert(String cmdFilePath, String pdfFilePath, String swfFilePath, String doc, String page) {
		//String pdfFilePath	= separate(getConfig("path.pdf", "")) + doc;
		//String swfFilePath	= separate(getConfig("path.swf", "")) + doc + page + ".swf";

		String command = cmdFilePath + "pdf2swf.exe \"{path.pdf}{pdffile}\" -o \"{path.swf}{pdffile}_%.swf\" -f -T 9 -t -s storeallcharacters -s linknameurl";

		command = command.replace("{path.pdf}", pdfFilePath);
		command = command.replace("{path.swf}", swfFilePath);
		command = command.replace("{pdffile}", doc);
		
		mkdir(swfFilePath);

		try {
			if (!isNotConverted(pdfFilePath + doc, swfFilePath + doc + page + ".swf")) {
				return "[Converted]";
			}
		} catch (Exception ex) {
			return "Error," + ex.toString();
		}

		boolean return_var = false;

		String pagecmd = command.replace("%", page);
		pagecmd = pagecmd + " -p " + page;

		return_var = exec(pagecmd);
		int hash = getStringHashCode(command);
		HttpSession session = request.getSession(true);
		String constr = "CONVERSION_" + hash;
		String conversion = (String) session.getAttribute(constr);
        if(conversion == null){
            exec(command);
            session.setAttribute(constr, "true");
        }

		String s = "Error converting document, make sure the conversion tool is installed and that correct user permissions are applied to the SWF Path directory" + 
					getDocUrl();
		if(return_var) {
			s="[Converted]";
		}
		return s;
	}
	
	public boolean isNotConverted(String pdfFilePath,String swfFilePath) throws Exception {
		File f = new File(pdfFilePath);
		if (!f.exists()) {
			throw new Exception("Document does not exist");
		}
		if (swfFilePath == null) {
			throw new Exception("Document output file name not set");
		} else {
			File s = new File(swfFilePath);
			if (!s.exists()) {
				return true;
			} else {
				if(f.lastModified() > s.lastModified()) return true;
			}
		}
		return false;
	}
}