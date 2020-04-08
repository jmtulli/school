package br.tulli.jm.util;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class Cryptography {
  /**
   * Simetric key - AES
   */
  private static String key = "JmTull1D3V";

  public static String encrypt(String password) {
    byte[] encryptMessage;
    String encryptPassword;

    try {
      encryptMessage = Cryptography.encode(nullPadString(password).getBytes(), nullPadString(key).getBytes());
      encryptPassword = fromHex(encryptMessage);
      return encryptPassword;
    } catch (Exception ex) {
      return null;
    }
  }

  public static String decrypt(String password) {
    try {
      byte[] encryptMessage = toHex(password);
      byte[] decryptMessage = decode(encryptMessage, nullPadString(key).getBytes());
      String originalPassword = new String(decryptMessage).trim();
      return originalPassword;
    } catch (Exception ex) {
      return null;
    }
  }

  private static byte[] encode(byte[] input, byte[] key) throws NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException {
    SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
    Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
    cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
    byte[] encrypted = cipher.doFinal(input);
    return encrypted;
  }

  private static byte[] decode(byte[] input, byte[] key) throws NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException {
    SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
    Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
    cipher.init(Cipher.DECRYPT_MODE, skeySpec);
    byte[] decrypted = cipher.doFinal(input);
    return decrypted;
  }

  /**
   * Fill the message with null chars until a size multiple by 16 to avoid BadPaddingException: pad
   * block corrupted
   */
  private static String nullPadString(String original) {
    StringBuilder output = new StringBuilder(original);
    int remain = output.length() % 16;
    if (remain != 0) {
      remain = 16 - remain;
      for (int i = 0; i < remain; i++) {
        output.append((char) 0);
      }
    }
    return output.toString();
  }

  private static String fromHex(byte[] hex) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < hex.length; i++) {
      sb.append(Integer.toString((hex[i] & 0xff) + 0x100, 16).substring(1));
    }
    return sb.toString();
  }

  private static byte[] toHex(String s) {
    int len = s.length();
    byte[] data = new byte[len / 2];
    for (int i = 0; i < len; i += 2) {
      data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
    }
    return data;
  }
}
