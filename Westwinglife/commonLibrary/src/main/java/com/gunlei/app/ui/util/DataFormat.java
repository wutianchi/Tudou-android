package com.gunlei.app.ui.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataFormat {

    public static String getFloat(String profit){
        DecimalFormat df=new DecimalFormat(".0");
        return df.format(Float.parseFloat(profit));
    }
    public static String getThousand(int number){
        DecimalFormat df=new DecimalFormat("##,###,###");
        return df.format(number);
    }
    public static String WashString(String content){
        String result=content.replaceAll("<.*?>", "").replaceAll("&.*?;", "").replaceAll("\\s*", "");
        return result;
    }
    public static String transInte(int index){
        String[] intStrs={"一","二","三","四","五","六","日"};
        if(index>0&&index<8){
            return intStrs[index+1];
        }else{
            return null;
        }
    }
    private static final char HEX_DIGITS[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F' };

    public static String toHexString(byte[] b) { //String to  byte
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
            sb.append(HEX_DIGITS[b[i] & 0x0f]);
        }
        return sb.toString();
    }
    public static String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            return toHexString(messageDigest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String setUuid(String inStr,int tag) {
        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++) {
            //1.存储密码长度*3
            //2.获取这个秘钥 异或加密
            a[i] = (char) (a[i] ^ tag);
        }
        String s = new String(a);
        return s;
    }
    public static String getUuid(String inStr,int tag) {
        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i] = (char) (a[i] ^ tag);
        }
        String k = new String(a);
        return k;
    }
    public static boolean isName(String name){
        //s.match(/^([u4e00-u9fa5]|[ufe30-uffa0]|[a-za-z0-9_])*$/);
//         只含有汉字、数字、字母、下划线不能以下划线开头和结尾：^(?!_)(?!.*?_$)[a-zA-Z0-9_\u4e00-\u9fa5]+$
//        解释：
//        ^  与字符串开始的地方匹配
//                (?!_)　　不能以_开头
//                (?!.*?_$)　　不能以_结尾
//                [a-zA-Z0-9_\u4e00-\u9fa5]+　　至少一个汉字、数字、字母、下划线
//        $　　与字符串结束的地方匹配
//        Pattern p = Pattern.compile("^[a-zA-Z0-9_\u4e00-\u9fa5]{1,14}$");
        Pattern p = Pattern.compile("^[\\w\\u4e00-\\u9fa5]{1,14}$");
        Matcher m = p.matcher(name);
        return m.matches();
    }
    public static boolean isCarNumber(String name){
        Pattern p = Pattern.compile("^([\\u4e00-\\u9fa5]{1})([A-Z]{1})([A-Z0-9]{4})([A-Z0-9\\u4e00-\\u9fa5]{1})$");
        Matcher m = p.matcher(name);
        return m.matches();
    }

    /*验证码短信获取*/
    public static String readNumber(String content){
        Pattern p = Pattern.compile("(?<![^0-9])([0-9]{6})(?![0-9])");
        Matcher m=p.matcher(content);
       String code="";
        while (m.find()){
         code=m.group(0);
        }
        return code;
    }
    public static boolean isMobile(String number){
		/*
		移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		联通：130、131、132、152、155、156、185、186
		电信：133、153、180、189、（1349卫通）
		总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		*/
    //    Pattern p = Pattern.compile("^((13\\d)｜(16\\d)｜(17\\d)|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Pattern p = Pattern.compile("^1[35678]\\d{9}$");
        Matcher m = p.matcher(number);
        return m.matches();
    }

    /*
     * 	 * 一个正则表达式验证密码强度
         return Regex.Replace(pwd, "^(?:([a-z])|([A-Z])|([0-9])|(.)){6,}|(.)+$", "$1$2$3$4$5").Length;
         密码
         //[A-Za-z][0-9]d{6,10}$   ^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$
        }*/
    public static boolean isPassWord(String password){
        Pattern p = Pattern.compile("^[a-zA-Z0-9_+=\\-@#~,.\\[\\]()!%]{6,16}$");
        Matcher m = p.matcher(password);
        return m.matches();
    }
}
