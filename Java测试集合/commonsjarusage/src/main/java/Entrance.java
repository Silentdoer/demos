import com.jfinal.ext.kit.ClassSearcher;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.ThreadUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 7/18/2018 3:03 PM
 */
public class Entrance {

    public static void main(String[] args) {
        String overlay = StringUtils.overlay("13136161100", "*****", 3, 8);
        System.out.println(overlay);
        Integer[] arr = new Integer[] {1, 2, 3};
        org.apache.commons.lang3.ArrayUtils.shift(arr, 3);
        ArrayList arrayList = new ArrayList<Integer>();
        arrayList.addAll(Arrays.asList(arr));
        // 输出 2,3,1；如果shift(arr, 1)则输出3，1,2；由此可以这么理解shift
        // shift(arr, n)表示将arr的后n个元素移动到初始位置，这里当n为3则没有变化；
        System.out.println(arrayList);
        Date now = new Date();
        // format是可以只输出指定的内容，即可以只输出年、时、毫秒这三项，而不是必须得是年月日、时分秒这种连续的格式
        System.out.println(DateFormatUtils.format(now, "HH:mm:ss:SSS"));
        System.out.println(DateFormatUtils.format(now, "yyyy HH ss"));


        BigDecimal money = null;
        // 如果是null则返回另外一个值否则返回其本身，类似三元操作符
        BigDecimal result = ObjectUtils.defaultIfNull(money, BigDecimal.ZERO);
        //CollectionUtils.addIgnoreNull()
        //ClassSearcher.of(null).scanPackages(Collections.singletonList())

    }
}
