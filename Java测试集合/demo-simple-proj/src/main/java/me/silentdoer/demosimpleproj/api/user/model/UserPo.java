package me.silentdoer.demosimpleproj.api.user.model;

import lombok.*;
import me.silentdoer.demosimpleproj.core.common.model.BasePo;
import me.silentdoer.demosimpleproj.core.util.ParamNamingTranslator;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 10/8/2018 9:50 AM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPo extends BasePo {

    private String fdUsername;

    private String fdNickname;

    private String fdPassword;

    private String fdSalt;

    private String fdCellphone;

    private String fdEmail;

    /**
     * 支付密码
     */
    private String fdPayPassword;

    /**
     * 账号状态
     */
    private Integer fdStatus;

    /**
     * 用户注册时间，目前这个时间等同于fdCreateTime，但是不排除以后变更业务使得账户注册需要审核
     */
    private Date fdRegisterTime;

    /**
     * 为用户产生的token，用于加签和验证用户是否仍处于登录态
     */
    private String fdToken;

    /**
     * 用户登录成功后会为客户端机器分配一个唯一ID存储在客户端，客户端每次操作包括登录都需要提供它
     */
    private String fdClientId;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public static void main(String[] args) {
        List<Field> fields = FieldUtils.getAllFieldsList(UserPo.class);
        StringBuilder sb = new StringBuilder(1024);

        List<String> result = fields.stream().map(e -> "`" + ParamNamingTranslator.lowerCamel2Underline(e.getName()) + "` " + getSuffix(e)).collect(Collectors.toList());
        String tbName = UserPo.class.getSimpleName();
        sb.append(String.format("%s %s;\n", "drop table if exists", tbName));
        sb.append(String.format("%s %s", "create table", tbName));
        // create table tb (
        // `fd_id`
        sb.append(result.stream().collect(Collectors.joining(", ", "(", ")")));
        System.out.println(sb);

        Calendar calendar = Calendar.getInstance();

        //calendar.set(1998, 11, 21);
        System.out.println(calendar.getTimeInMillis());
    }

    public static String getSuffix(Field f) {
        if (f.getType().equals(Integer.class)) {
            return "int(10)";
        } else if (f.getType().equals(Long.class)) {
            return "bigint(20)";
        } else if (f.getType().equals(String.class)) {
            return "varchar(45)";
        } else if (f.getType().equals(Boolean.class)) {
            return "tinyint(1)";
        } else if (f.getType().equals(Short.class)) {
            return "tinyint(4)";
        } else if (f.getType().equals(Date.class)) {
            return "datetime";
        } else if (f.getType().equals(BigDecimal.class)) {
            return "decimal(10, 2)";
        } else {
            return "varchar(5000)";
        }
    }
}
