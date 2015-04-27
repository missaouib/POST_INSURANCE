package com.gdpost.utils.SwfHelper;

import javax.servlet.http.HttpServletRequest;

public class SplitPdf extends Common {
	HttpServletRequest request = null;

	public SplitPdf(HttpServletRequest request){
		this.request = request;
	}

	public String convert(String doc, String page) {
		try {
			String command = getConfig("cmd.conversion.splitpdffile", "");
	
			command = command.replace("{path.pdf}", separate(getConfig("path.pdf", "")));
			command = command.replace("{path.swf}", separate(getConfig("path.swf", "")));			
			command = command.replace("{pdffile}", doc);
			
			if(exec(command)){
				return "[OK]";
			}else{
				return "[Error converting splitting PDF, please check your configuration]";
			}
		} catch (Exception ex) {
			return "[" + ex.toString() + "]";
		}
	}
}

