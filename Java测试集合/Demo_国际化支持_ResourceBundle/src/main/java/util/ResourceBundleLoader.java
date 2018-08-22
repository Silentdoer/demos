package util;

import org.apache.commons.lang3.math.NumberUtils;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.function.Supplier;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 8/15/2018 12:25 PM
 */
public final class ResourceBundleLoader {

    private ResourceBundle resourceBundle;

    public ResourceBundleLoader(String bundleName) {
        this.resourceBundle = PropertyResourceBundle.getBundle(bundleName);
    }

    public String getString(String key) {
        return this.resourceBundle.getString(key);
    }

    public String getStringDft(String key, Supplier<String> producer) {
        String result;
        try {
            result = this.resourceBundle.getString(key);
        } catch (Exception e) {
            result = producer.get();
        }
        return result;
    }

    public Double getDouble(String key) {
        String tmp = this.resourceBundle.getString(key);
        return Double.parseDouble(tmp);
    }

    public Double getDoubleDft(String key, Supplier<Double> producer) {
        Double result;
        try {
            String tmp = this.resourceBundle.getString(key);
            if (NumberUtils.isCreatable(tmp)) {
                result = Double.parseDouble(tmp);
            } else {
                result = producer.get();
            }
        } catch (Exception e) {
            result = producer.get();
        }
        return result;
    }
}
