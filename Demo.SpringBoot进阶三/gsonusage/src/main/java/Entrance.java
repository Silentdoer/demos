import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.GsonBuildConfig;
import com.google.gson.reflect.TypeToken;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @since 7/2/2018 11:25 AM
 */
public class Entrance {
    public static void main(String[] args) {
        String str = "{\n" +
                "\t\"f_id\":33,\n" +
                "\t\"f-name\":\"sdf接口\",\n" +
                "\t\"fGender\":\"男\"\n" +
                "}";
        Gson gson = new GsonBuilder().disableHtmlEscaping().serializeNulls().setPrettyPrinting()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        Student student = gson.fromJson(str, Student.class);
        System.out.println(student.toString());
        System.out.println(gson.toJson(student));
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Student {
        @SerializedName("f_id")
        private Long fId;
        @SerializedName(value = "fName", alternate = {"f_name", "FName", "f-name", "F-Name"})
        private String fName;
        @SerializedName("fGender")
        private Character fGender;
    }
}
