package com.luos.util;

import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by luos on 2016/10/30.
 */
public class Md5Util {

    /**
     * 对密码进行加密
     * 不可逆
     *
     * @param pwd
     * @return
     */
    public static String EncoderPwdMd5(String pwd){
        String encoderPwd = null;
        try {
            //MessageDigest.getInstance()，参数传入加密算法——MD5
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            BASE64Encoder base64en = new BASE64Encoder();
            encoderPwd = base64en.encode(md5.digest(pwd.getBytes("utf-8")));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encoderPwd;
    }

    public static void main(String[] args){
        System.out.print(EncoderPwdMd5("luo"));
    }


}
