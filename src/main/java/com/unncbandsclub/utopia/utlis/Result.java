 package com.unncbandsclub.utopia.utlis;

/**
 * @author Ruizhe Huang
 * @date 2022/05/17
 */
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;

 @Data
@ApiModel(value = "全局统一返回结果")
public class Result {
    @ApiModelProperty(value = "是否成功")
    private boolean success;
    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;
    @ApiModelProperty(value = "返回数据")
    private HashMap<String,Object> data = new HashMap<>();

    @ApiModelProperty(value="错误原因")
    private ErrorCase errorCase=ErrorCase.DEFAULT_CASE;

    public static final int SUCCESS_CODE=20000,FAIL_CODE=20001;


    private Result(){}

    public static Result ok(){
        Result r = new Result();//调用自己私有的构造方法
        r.setSuccess(true);
        r.setCode(SUCCESS_CODE);
        r.setMessage("succeed");
        return r;
    }

    public static Result ok(Object o){
        Result ok=ok();
        HashMap<String,Object> m=new HashMap<>();
        m.put("0",o);
        ok.data=m;
        return ok;
    }

    public static Result ok(HashMap<String,Object> data)
     {
         Result ok = ok();
         ok.data=data;
         return ok;
     }

     public static Result ok(HashMap<String,Object> data,String msg){
         Result ok = ok(data);
         ok.message=msg;
         return ok;
     }


    public static Result error(){
        Result r = new Result();//调用自己私有的构造方法
        r.setSuccess(false);
        r.setCode(FAIL_CODE);
        r.setMessage("failed");
        return r;
    }
    public static Result error(ErrorCase errorCase){
        Result error = Result.error();
        error.errorCase=errorCase;
        error.message=errorCase.getReason();
        return error;
    }
    public static Result error(ErrorCase errorCase, HashMap<String,Object> data){
        Result error = error(errorCase);
        error.data=data;
        return error;
    }
    public static Result error(ErrorCase errorCase,String msg){
        Result error=error(errorCase);
        error.message=msg;
        return error;
    }

    public static Result error(ErrorCase errorCase,HashMap<String,Object> data,String msg){
        Result error=error(errorCase,data);
        error.message=msg;
        return error;
    }
    public Result(boolean success, Integer code, String message, HashMap<String, Object> data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(Integer code, String message, HashMap<String, Object> data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 使用者调用ok方法或者error方法之后，如果需要修改message信息，那么可以继续调用这个方法，修改message信息
     * @param message
     * @return
     */
    public Result message(String message){
        this.setMessage(message);
        return this;
    }

    private Result code(Integer code){
        this.setCode(code);
        return this;
    }


    public Result data(HashMap<String,Object> map){
        this.setData(map);
        return this;
    }


    public Result data(String key,Object value){
        this.data.put(key,value);
        return this;
    }


}
