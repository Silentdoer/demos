package me.silentdoer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 10/16/2018 7:10 PM
 */
public class Entrance {

    public static void main(String[] args) {
        // TODO 经过测试JSON数据里在,等符号后面有空格没事，但是有//的注释会抛异常，但是用/**/的注释是没有问题的
        String jsonStr = "{\"code\":0,\"thirdCode\":null, /*df我*/\"msg\":/*注释22*/\"操作成功\",\"data\":3,\"success\":true}";
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        System.out.println(jsonObject);
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        ApiResult result = gson.fromJson(jsonStr, ApiResult.class);
        System.out.println(result.getMsg());
    }

    public static class ApiResult {

        private Integer code;

        private Integer thirdCode;

        private String msg;

        private Integer data;

        private Boolean success;

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public Integer getThirdCode() {
            return thirdCode;
        }

        public void setThirdCode(Integer thirdCode) {
            this.thirdCode = thirdCode;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public Integer getData() {
            return data;
        }

        public void setData(Integer data) {
            this.data = data;
        }

        public Boolean getSuccess() {
            return success;
        }

        public void setSuccess(Boolean success) {
            this.success = success;
        }
    }
}
