/**
 * 
 */
package com.mljr.spider.pattern;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Splitter;

/**
 * @author Ckex zha </br>
 *         2016年11月6日,下午7:21:12
 *
 */
public class PatternTests {

	/**
	 * 
	 */
	public PatternTests() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Pattern pat = Pattern.compile("\'(.*?)\'");
		pat = Pattern.compile("(?<=\\()(.+?)(?=\\))"); // 宽断言,(?<=exp)匹配exp后面的位置
		String str = "<a href=\"#\" onClick=\"return open_new_window( '/patroninfo~S0*chx/1069163/modpinfo' )\">";
		str = "querycallback({\"Mobile\":\"15601662655\",\"QueryResult\":\"True\",\"TO\":\"中国联通\",\"Corp\":\"中国联通\",\"Province\":\"上海\",\"City\":\"上海\",\"AreaCode\":\"021\",\"PostCode\":\"200000\",\"VNO\":\"\",\"Card\":\"\"});";
		Matcher mat = pat.matcher(str);
		if (mat.find()) {
			System.out.println(mat.group(1));
		}
		System.out.println(" --------- ");
		String s = " 上海  中国联通";
		System.out.println(Splitter.onPattern("  ").omitEmptyStrings().trimResults().splitToList(s).size());
	}

}
