package com.unncbandsclub.utopia.utlis;

import java.util.regex.Pattern;

public class RegularUtil {
    public static final String SINGLE_EMAIL_REGEX = "(?:(?:[A-Za-z0-9\\-_@!#$%&'*+/=?" +
            "^`{|}~]|(?:\\\\[\\x00-\\xFF]?)|(?:\"[\\x00-\\xFF]*\"))+(?:\\.(?:(?:[A-Za-z0-9\\-"
            + "_@!#$%&'*+/=?^`{|}~])|(?:\\\\[\\x00-\\xFF]?)|(?:\"[\\x00-\\xFF]*\"))+)*)"
            + "@(?:(?:[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?\\.)+"
            + "(?:(?:[A-Za-z0-9]*[A-Za-z][A-Za-z0-9]*)(?:[A-Za-z0-9-]*[A-Za-z0-9])?))";

    /**
     * 用户名验证
     *
     * @param name
     * @return
     */
    public static boolean checkName(String name) {
        String regExp = "^\\D[\\w_]{5,9}$";
        return name.matches(regExp);
    }

    /**
     * 密码验证
     *
     * @param pwd
     * @return
     */
    public static boolean checkPwd(String pwd) {
        String regExp = "^[\\w_]{6,20}$";
        return pwd.matches(regExp);
    }

    public static final Pattern SINGLE_EMAIL_REGEX_PATTERN = Pattern.compile(SINGLE_EMAIL_REGEX);
    public static boolean checkMail(String mail) {
        return mail.length() <= 50 && SINGLE_EMAIL_REGEX_PATTERN.matcher(mail).matches();
    }

    /**
     * 0 unknown 1 male 2 female
     *
     * @param sex
     * @return
     */
    public static boolean checkSex(Integer sex) {
        return sex != null && sex >= 0 && sex <= 2;
    }

    //TODO Regular check operations... Fuck me

    public static boolean checkURL(String url) {
        return url.length() <= 255;
    }

    public static boolean checkQQ(String q) {
        return q.length() <= 20;
    }

    public static boolean checkWechat(String w) {
        return w.length() <= 30;
    }

    public static boolean checkRealName(String realName) {
        return realName.length() <= 10;
    }

    public static boolean checkStudentId(Integer sId) {
        return sId >= 10000000 && sId <= 99999999;
    }

    public static boolean checkNickName(String nickName) {
        return nickName.length() <= 19;
    }


  /**
   * 获取文件后缀
   * @param ordinaryFileName
   * @return
   */
  public static String extractFileSuffix(String ordinaryFileName){
      String[] s1 = ordinaryFileName.split("/");
      ordinaryFileName=s1[s1.length-1];
      String[] s2 = ordinaryFileName.split("\\.");
      if(s2.length<=1){
        return "";
      }
      return s2[s2.length-1];
    }


}
