package com.meishipintu.fucaiShopNew.utils;

import android.util.Base64;
import android.util.Log;

import java.security.Key;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class Des2
{
    public static final String ALGORITHM_DES = "DES/CBC/PKCS5Padding";

    public static String MD5(String str)  
    {  
        MessageDigest md5 = null;  
        try  
        {  
            md5 = MessageDigest.getInstance("MD5"); 
        }catch(Exception e)  
        {  
            e.printStackTrace();  
            return "";  
        }  
          
        char[] charArray = str.toCharArray();  
        byte[] byteArray = new byte[charArray.length];  
          
        for(int i = 0; i < charArray.length; i++)  
        {  
            byteArray[i] = (byte)charArray[i];  
        }  
        byte[] md5Bytes = md5.digest(byteArray);  
          
        StringBuffer hexValue = new StringBuffer();  
        for( int i = 0; i < md5Bytes.length; i++)  
        {  
            int val = ((int)md5Bytes[i])&0xff;  
            if(val < 16)  
            {  
                hexValue.append("0");  
            }  
            hexValue.append(Integer.toHexString(val));  
        }  
        return hexValue.toString();  
    } 
    
    /**
     * DES绠楁硶锛屽姞瀵�
     *
     * @param data 寰呭姞瀵嗗瓧绗︿覆
     * @param key  鍔犲瘑绉侀挜锛岄暱搴︿笉鑳藉灏忎簬8浣�
     * @return 鍔犲瘑鍚庣殑瀛楄妭鏁扮粍锛屼竴鑸粨鍚圔ase64缂栫爜浣跨敤
     * @throws Exception 寮傚父
     */
    public static String encode(String key,String data) throws Exception
    {
    	key = MD5(key);
        return encode(key.substring(0,24), data.getBytes());
    }
    /**
     * DES绠楁硶锛屽姞瀵�
     *
     * @param data 寰呭姞瀵嗗瓧绗︿覆
     * @param key  鍔犲瘑绉侀挜锛岄暱搴︿笉鑳藉灏忎簬8浣�
     * @return 鍔犲瘑鍚庣殑瀛楄妭鏁扮粍锛屼竴鑸粨鍚圔ase64缂栫爜浣跨敤
     * @throws Exception 寮傚父
     */
    public static String encode(String key,byte[] data) throws Exception
    {
        try
        {
	    	DESKeySpec dks = new DESKeySpec(key.getBytes());
	    	
	    	SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            //key鐨勯暱搴︿笉鑳藉灏忎簬8浣嶅瓧鑺�
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            IvParameterSpec iv = new IvParameterSpec("12345678".getBytes());
            AlgorithmParameterSpec paramSpec = iv;
            cipher.init(Cipher.ENCRYPT_MODE, secretKey,paramSpec);
            
            byte[] bytes = cipher.doFinal(data);

            return Base64.encode(bytes,Base64.DEFAULT).toString();
        } catch (Exception e)
        {
            throw new Exception(e);
        }
    }

    /**
     * DES绠楁硶锛岃В瀵�
     *
     * @param data 寰呰В瀵嗗瓧绗︿覆
     * @param key  瑙ｅ瘑绉侀挜锛岄暱搴︿笉鑳藉灏忎簬8浣�
     * @return 瑙ｅ瘑鍚庣殑瀛楄妭鏁扮粍
     * @throws Exception 寮傚父
     */
    public static byte[] decode(String key,byte[] data) throws Exception
    {
        try
        {
        	SecureRandom sr = new SecureRandom();
	    	DESKeySpec dks = new DESKeySpec(key.getBytes());
	    	SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            //key鐨勯暱搴︿笉鑳藉灏忎簬8浣嶅瓧鑺�
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            IvParameterSpec iv = new IvParameterSpec("12345678".getBytes());
            AlgorithmParameterSpec paramSpec = iv;
            cipher.init(Cipher.DECRYPT_MODE, secretKey,paramSpec);
            return  cipher.doFinal(data);
        } catch (Exception e)
        {
            throw new Exception(e);
        }
    }

    /**
     * 鑾峰彇缂栫爜鍚庣殑鍊�
     * @param key
     * @param data
     * @return
     * @throws Exception
     */
    public static String decodeValue(String key,String data)
    {
    	byte[] datas;
    	String value = null;
    	key = MD5(key);
		try {
			if(System.getProperty("os.name") != null && (System.getProperty("os.name").equalsIgnoreCase("sunos") || System.getProperty("os.name").equalsIgnoreCase("linux")))
	        {
	    		datas = decode(key.substring(0,24), Base64.decode(data,Base64.DEFAULT));
	    		Log.d("DES2", "data=" + new String(datas));
	        }
	    	else
	    	{
	    		datas = decode(key.substring(0,24), Base64.decode(data,Base64.DEFAULT));
	    		Log.d("DES2", "data=" + new String(datas));
	    	}
			
			value = new String(datas);
		} catch (Exception e) {
			value = "";
		}
    	return value;
    }

}

