package br.ufpr.c3sl.pageHTML;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.ufpr.c3sl.util.Util;

public class Html {
	private String code, legend, title;
	
	public Html(String code) {
		this.code = code;
		this.legend = getTagValue("legend");
		this.title = getTagValue("title");
		
		while (getTagValue("img") != null);
		rebuildImages();
	}
	
	private void rebuildImages() {
		Pattern pattern = Pattern.compile("<img .*?src=[\"'](.*?)[\"'][^>]*>");
		Matcher matcher = pattern.matcher(code);
		
		while(matcher.find()) {
			String path = matcher.group(1);
			String newPath = Util.getPath(getClass(), path).toString();
			code = code.replaceAll(path, newPath);
		}
	}
	
	private String getTagValue(String tag) {
		String open = "<" + tag + ">";
		String close = "</" + tag + ">";
		String replacement = "";
		String returnVal;

		if (code == null || open == null || close == null) {
			return null;
		}
		int start = code.indexOf(open);
		if (start != -1) {
			int end = code.indexOf(close, start + open.length());
			if (end != -1) {
				returnVal =  code.substring(start + open.length(), end);
				if (tag == "img") {
					System.err.println("Found <img></img> tag in \"" + legend + "\", <img></img> has been deprecated, use <img src=\"\" /> instead.");
					replacement = "<img src=" + Util.getPath(getClass(), returnVal) + " />";
				}
				code = code.substring(0, start) + replacement + code.substring(end + close.length());
				return returnVal;
			}
		}
		
		return null;
	}
	
	public String getCode() {
		String code = this.code;
		if (code.matches("^<html>.*</html>$")) {
			System.err.println("Found <html> tag in \"" + legend + "\", <html> has been deprecated, you are encouraged to remove it.");
		}
		code = "" + 
			"<html>" + 
				"<head>" +
					"<style>" +
					"*, body {font-size: 10px; font-family: Arial, Helvetica, sans-serif }" +
					"</style>" +
				"</head>" +
				"<body>" +
					code +
				"</body>" +
			"</html>";
		return code;
	}

	public String getTitle() {
		return title;
	}

	public String getLegend() {
		return legend;
	}
}
