import com.google.gson.*;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 11/13/2018 7:41 PM
 */
public class TestEntrance {

    public static void main(String[] args) {
        JsonObject object = new JsonObject();
        object.addProperty("pro1", 88);
        object.addProperty("pro2", true);
        object.addProperty("pro3", "是MS");
        JsonArray array = new JsonArray();
        JsonObject child = new JsonObject();
        child.addProperty("pro11", "UUU");
        child.addProperty("pro12", 33);
        array.add(child);
        object.add("pro4", array);
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String tmp = gson.toJson(object);
        System.out.println(tmp);
        String jsonStr = "{\"pro1\":88,\"pro2\":true,\"pro3\":\"是MS\",\"pro4\":[{\"pro11\":\"UUU\"/*是88色素*/,\"pro12\":33}]}";
        JsonElement obj2 = gson.fromJson(jsonStr, JsonElement.class);
        // TODO OK，说明可以不用JsonParser
        JsonElement object1 = gson.fromJson(tmp, JsonElement.class);
        System.out.println(object1.getClass());
        // JsonArray pro4 = object1.getAsJsonArray("pro4");
        // OK，首先pro4.get(0)先获得child，然后将child转换为JsonObject【JsonElement是可以当做任何对象的包括基础类型】，然后获得pro12成员转换为int类型
        // System.out.println(pro4.get(0).getAsJsonObject().get("pro12").getAsInt());
        // JSON字符串是可以由注释的，但是对于这种基础类型的则不能有注释
        System.out.println(gson.fromJson("21", JsonElement.class).getClass());
    }
}
