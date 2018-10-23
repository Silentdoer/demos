package me.silentdoer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 10/19/2018 5:39 PM
 */
public class JSONObjectTest {

    public static void main(String[] args) {
        String jsonStr = "{\n" +
                "    \"pro1\": 11,\n" +
                "    \"pro2\": [\n" +
                "        {\n" +
                "            \"pro21\": \"test\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"pro21\": \"foo\",\n" +
                "            \"pro22\": 88\n" +
                "        }\n" +
                "    ],\n" +
                "    \"pro3\": {\n" +
                "        \"pro31\": {\n" +
                "            \"pro311\": 33,\n" +
                "            \"pro312\": \"foo\"\n" +
                "        }\n" +
                "    }\n" +
                "}";
        JSONObject obj = JSON.parseObject(jsonStr);

        // 输出的类型是com.alibaba.fastjson.JSONArray，说明确实可以自动识别是数组从而用JSONArray存储
        System.out.println(obj.get("pro2").getClass());

        // java.lang.Integer
        System.out.println(obj.get("pro1").getClass());

        // com.alibaba.fastjson.JSONObject
        System.out.println(obj.get("pro3").getClass());
        if (obj.get("pro3") instanceof JSONObject) {
            // com.alibaba.fastjson.JSONObject
            System.out.println(((JSONObject) obj.get("pro3")).get("pro31").getClass());
        }

        String jsonStr2 = "[\n" +
                "        {\n" +
                "            \"pro21\": \"test\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"pro21\": \"foo\",\n" +
                "            \"pro22\": 88\n" +
                "        }\n" +
                "    ]";
        Object parse = JSON.parse(jsonStr2);
        // com.alibaba.fastjson.JSONArray TODO 最好还是用parse就好，不要用parseObject，因为[开头的也可以是JSON字符串
        System.out.println(parse.getClass());

        // TODO 由上述结果得出，可以通过能否将字符串parse为JSONObject或JSONArray或其它来识别它是数组、复杂类型、简单类型【基础类型加字符串和Date之类】
        // TODO 从而提供了根据这些判断来画JSON UI图的可能：是JSONObject和JSONArray都要有+号，但是Array的还要多个[]，而如果是普通类型则直接在右侧显示结果
        // JSONObject的点击加号后继续判断子类型是复杂对象、数组或普通类型来显示
    }
}
