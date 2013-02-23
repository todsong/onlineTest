package com.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util
{
	public static String getMD5(String src)
	{ 
	    MessageDigest md=null; 
	    try { 
	        md=MessageDigest.getInstance("MD5"); 
	    } catch (NoSuchAlgorithmException e) { 
	        e.printStackTrace(); 
	    } 
	    md.update(src.getBytes()); //MD5加密算法只是对字符数组而不是字符串进行加密计算，得到要加密的对象 
	    byte[] res = md.digest();   //进行加密运算并返回字符数组 
	    StringBuffer sb=new StringBuffer(); 
	    for(int i=0;i<res.length;i++){    //字节数组转换成十六进制字符串，形成最终的密文 
	        int v=res[i]&0xff; 
	        if(v<16){ 
	            sb.append(0); 
	        } 
	        sb.append(Integer.toHexString(v)); 
	    } 
	    return sb.toString();
	}
//	public static void main(String[] agrs)
//	{
//		System.out.println(MD5Util.getMD5("admin2admin2"));
//	}
}