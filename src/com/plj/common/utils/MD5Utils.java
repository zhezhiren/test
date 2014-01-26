package com.plj.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5生成�?
 * @author zhengxing
 * @version 1.0
 * @date 2013.1.17
 */
public class MD5Utils {
	protected static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9','a', 'b', 'c', 'd', 'e', 'f' };
	protected static MessageDigest messagedigest = null;
	static{
	   try{
	    messagedigest = MessageDigest.getInstance("MD5");
	   }catch(NoSuchAlgorithmException nsaex){
	    System.err.println(MD5Utils.class.getName()+ "��ʼ��ʧ�ܣ�MessageDigest��֧��MD5Util��");
	    nsaex.printStackTrace();
	   }
	}
	
	/**
	 * 生成大文件MD5�?
	 * @param file
	 * @return
	 * @throws IOException
	 */
	private static String getBigFileMD5String(File file) throws IOException {
		   FileInputStream in = new FileInputStream(file);
		   byte[] buffer = new byte[1024 * 1024 * 10];
		   int len = 0;
		   while ((len = in.read(buffer)) >0) {
		    messagedigest.update(buffer, 0, len);
		   }
		   in.close();
		   return bufferToHex(messagedigest.digest());
		}


	/**
	 * 生成文件MD5�?
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String getFileMD5String(File file) throws IOException {
		//文件如果大于100M，调�?生成大文件MD5方法
	   if(file.length() > 104857600) return getBigFileMD5String(file);
	   FileInputStream in = new FileInputStream(file);
	   FileChannel ch = in.getChannel();
	   MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
	   messagedigest.update(byteBuffer);
	   ch.close();
	   return bufferToHex(messagedigest.digest());
	}

	/**
	 * 生成字符串MD5�?
	 * @param s
	 * @return
	 */
	public static String getMD5String(String s) {
	   return getMD5String(s.getBytes());
	}

	private static String getMD5String(byte[] bytes) {
	   messagedigest.update(bytes);
	   return bufferToHex(messagedigest.digest());
	}

	private static String bufferToHex(byte bytes[]) {
	   return bufferToHex(bytes, 0, bytes.length);
	}

	private static String bufferToHex(byte bytes[], int m, int n) {
	   StringBuffer stringbuffer = new StringBuffer(2 * n);
	   int k = m + n;
	   for (int l = m; l < k; l++) {
	    appendHexPair(bytes[l], stringbuffer);
	   }
	   return stringbuffer.toString();
	}


	private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
	   char c0 = hexDigits[(bt & 0xf0) >> 4];
	   char c1 = hexDigits[bt & 0xf];
	   stringbuffer.append(c0);
	   stringbuffer.append(c1);
	}

	public static void main(String[] args) throws IOException {
		
		String b = null;
		String s = getMD5String(b);
		System.out.println(s);
	   long begin = System.currentTimeMillis();

	   File big = new File("e:/上海demo.rar");
	   System.out.println(big.length());

	   String md5=getFileMD5String(big);
	   //String md5 = getBigFileMD5String(big);
	   //String md5 = getMD5String("wangyu");

	   long end = System.currentTimeMillis();
	   System.out.println("md5:"+md5+" time:"+((end-begin)/1000)+"s");
	   
	   
	}
}
