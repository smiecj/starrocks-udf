package com.starrocks.udf.example;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

// AESDecrypt: AES 加解密 demo
// https://www.kancloud.cn/apachecn/howtodoinjava-zh/1953419
public class AESDecrypt {

  private static final String myKey = "test_aes_key";

  private static SecretKeySpec secretKey;
	private static byte[] key;

	static
	{
		MessageDigest sha = null;
		try {
			key = myKey.getBytes("UTF-8");
			sha = MessageDigest.getInstance("SHA-1");
			key = sha.digest(key);
			key = Arrays.copyOf(key, 16); 
			secretKey = new SecretKeySpec(key, "AES");
		} 
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} 
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public static String encrypt(String strToEncrypt) 
	{
		try 
		{
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
		} 
		catch (Exception e) 
		{
			System.out.println("Error while encrypting: " + e.toString());
		}
		return null;
	}

	public static String decrypt(String strToDecrypt) 
	{
		try
		{
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
		} 
		catch (Exception e) 
		{
			System.out.println("Error while decrypting: " + e.toString());
		}
		return null;
	}

  public final String evaluate(String value) {
    String res = AESDecrypt.decrypt(value);
    return res;
  }

  public static void main(String[] args) {
    String value = "smiecj";
    String result = AESDecrypt.encrypt(value); // WT8JGUKj79nh/iBZgYtq5Q==
    System.out.println("encrypted value: " + result);
    String encryptedValue = "WT8JGUKj79nh/iBZgYtq5Q==";
    result = AESDecrypt.decrypt(encryptedValue);
    System.out.println("decrypted value: " + result);
  }
}
