package com.sales_control.pi.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Md5PasswordEncoderUtil implements PasswordEncoder {

  @Override
  public String encode(CharSequence rawPassword) {
    try {
      var md = MessageDigest.getInstance("MD5");
      var digest = md.digest(rawPassword.toString().getBytes());
      var sb = new StringBuilder();
      for (byte b : digest) sb.append(String.format("%02x", b & 0xff));

      return sb.toString();
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("Erro ao gerar hash MD5", e);
    }
  }

  @Override
  public boolean matches(CharSequence rawPassword, String encodedPassword) {
    return encode(rawPassword).equalsIgnoreCase(encodedPassword);
  }
}
