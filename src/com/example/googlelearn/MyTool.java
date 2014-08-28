package com.example.googlelearn;

/** 
 *  描述:TODO
 *
 * @author mfx
 * @date 2014年8月28日 下午11:49:42
 */
public class MyTool {

	public static String decipher(String s){
		return s+"b";
	}
	
	public static String rot13(String input) {
		   StringBuilder sb = new StringBuilder();
		   for (int i = 0; i < input.length(); i++) {
		       char c = input.charAt(i);
		       if       (c >= 'a' && c <= 'm') c += 13;
		       else if  (c >= 'A' && c <= 'M') c += 13;
		       else if  (c >= 'n' && c <= 'z') c -= 13;
		       else if  (c >= 'N' && c <= 'Z') c -= 13;
		       sb.append(c);
		   }
		   return sb.toString();
		}
}
