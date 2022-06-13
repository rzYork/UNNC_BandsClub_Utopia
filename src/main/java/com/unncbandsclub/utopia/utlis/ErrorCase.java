package com.unncbandsclub.utopia.utlis;

import java.util.Arrays;
import java.util.HashMap;

public enum ErrorCase {
  DEFAULT_CASE(1300, "未知错误"),
  USER_NOT_EXIST(1301, "用户不存在"),
  WRONG_PASSWORD(1302, "密码错误"),
  USER_EXIST(1303, "用户已存在"),
  EMAIL_NOT_EXIST(1304, "邮箱不存在"),
  INVALID_ACCOUNT(1305, "账户不存在"),

  ILLEGAL_PARAMETER(1306, "非法参数"),

  SYSTEM_MAINTAIN(5000, "系统维护"),
  SYSTEM_ERROR(5001, "系统错误"),
  INVALID_KEY(1307, "无效激活密匙"),

  MODIFY_USER_FAIL(1308, "修改用户状态失败"),
  REPEAT_OPERATION(1309, "重复操作"),
  ROLE_ID_NOT_EXIST(1310, "角色ID不存在"),
  DISABLED_ACCOUNT(1311, "账户已注销未激活！"),
  INVALID_TOKEN(5002, "无效Token验证！"),

  ILLEGAL_NICKNAME(1312,"昵称过长")

    ,

  ILLEGAL_PASSWORD(1313,"非法密码")
    ,

  ILLEGAL_USERNAME(1314,"非法账户名")
    ,

  ILLEGAL_MAIL(1315,"非法邮箱")
    ,

  ILLEGAL_WECHAT(1316,"非法微信")
     ,

  ILLEGAL_QQ(1317,"非法QQ")
    ,

  ILLEGAL_AVATAR_URL(1318,"非法头像URL")
    ,

  ILLEGAL_SEX_OPTION(1319,"非法性别选项")
    ,

  ILLEGAL_REAL_NAME(1320,"非法实名")
    ,

  ILLEGAL_STUDENT_ID(1321,"非法学生ID")
    ,

  TOO_WEAK_PASSWORD(1322,"密码强度不足")
    ,

  UPLOAD_FILE_FAIL(1323,"上传文件失败")
    ,

  INVALID_BAND_NAME(1324,"无效乐队名称")
    ,

  BAND_NAME_EXIST(1325,"乐队名已存在")
  ,

  ID_NOT_EXISTS(1326,"ID不存在")
  ,

  INVALID_VO_OBJECT(5001,"无效VO对象")
  ,

  NULL_OR_EMPTY_NECESSARY_PARAMETER(5002,"无效或空的必须参数")
  ,

  ILLEGAL_FILE_CONTENT_TYPE(5003,"错误的文件类型")
  ,

  TOO_LARGE_FILE(1327,"文件过大")


    ;

  private String reason;
  private int errorCode;

  ErrorCase(int errorCode, String reason) {
    this.errorCode = errorCode;
    this.reason = reason;
  }

  public void setReason(String newReason) {
    this.reason = newReason;
  }

  public String getReason() {
    return this.reason;
  }

  public int getErrorCode() {
    return this.errorCode;
  }

  @Override
  public String toString() {
    return "ErrorCase{" +
      "reason='" + reason + '\'' +
      ", errorCode=" + errorCode +
      '}';
  }

  public static HashMap<Integer, String> getCasesMap() {
    HashMap<Integer, String> resultMap = new HashMap<>();
    Arrays.stream(ErrorCase.values()).forEach(errorCase -> resultMap.put(errorCase.getErrorCode(), errorCase.getReason()));
    return resultMap;
  }


}
