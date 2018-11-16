package com.quality.common.util;


/*import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;*/

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.MessageDigest;

/**
 * 说明：MD5处理
 * @author
 * 修改时间：2014年9月20日
 * @version
 */
public class MD5 {

	public static String md5(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			str = buf.toString();
		} catch (Exception e) {
			e.printStackTrace();

		}
		return str;
	}
/*
	public static boolean compilePass(String newPass,String oldPass){
		boolean result = new BCryptPasswordEncoder().matches(newPass, oldPass);
		return result;
	}*/





	/**
	 * 加密密码
	 * 随机盐值
	 */

	public static String encryptPassword(String passWord){
		String reuslt  = new BCryptPasswordEncoder().encode(passWord);
		return reuslt;
	}


	public static void main(String[] args) {
		String pd = MD5.encryptPassword("123456");
		System.out.println(pd);

	}
}
