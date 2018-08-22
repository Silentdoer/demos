import org.apache.commons.lang3.Validate;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 8/10/2018 7:22 PM
 */
public class ValidateEntrance {

    public static void main(String[] args) {
        // TODO Validate类很有用，这个类感觉用于判断的Api比Assert和Preconditions要多
        System.out.println(Validate.noNullElements(new Object[]{0, null, "ss"}));
        Validate.matchesPattern("sss", ".{2}");
    }
}
