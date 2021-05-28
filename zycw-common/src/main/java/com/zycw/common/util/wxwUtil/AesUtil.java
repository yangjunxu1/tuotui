package com.zycw.common.util.wxwUtil;


import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

@Component
@SuppressWarnings("restriction")
public class AesUtil {
	
		public static String key = "eshop11223344556";  //16位
		private static String iv = "eshop11223344556";//偏移量字符串必须是16位 当模式是CBC的时候必须设置偏移量
	   
		    	    
	    public static String decryptAES(String data) throws Exception {
	        try {
	            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(data);

	            Cipher cipher = Cipher.getInstance("AES/CBC/NOPadding");
	            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
	            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

	            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);

	            byte[] original = cipher.doFinal(encrypted1);
	            String originalString = new String(original);
	            return originalString;
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }
	    
	    public static String encryptAES(String data) throws Exception {

	        try {

	            Cipher cipher = Cipher.getInstance("AES/CBC/NOPadding");    //参数分别代表 算法名称/加密模式/数据填充方式
	            int blockSize = cipher.getBlockSize();

	            byte[] dataBytes = data.getBytes();
	            int plaintextLength = dataBytes.length;
	            if (plaintextLength % blockSize != 0) {
	                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
	            }

	            byte[] plaintext = new byte[plaintextLength];
	            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

	            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
	            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

	            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
	            byte[] encrypted = cipher.doFinal(plaintext);

	            return new sun.misc.BASE64Encoder().encode(encrypted);

	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }
	    
}
