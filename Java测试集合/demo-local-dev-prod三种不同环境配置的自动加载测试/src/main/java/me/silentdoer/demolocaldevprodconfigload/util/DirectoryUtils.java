package me.silentdoer.demolocaldevprodconfigload.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 12/13/2018 6:21 PM
 */
public final class DirectoryUtils {

    /**
     * @param dir
     * @param suffixes
     * @return
     */
    public static List<String> getAllFileNamesBaseDirWithSuffix(File dir, String suffixes) {
        if (!dir.isDirectory()) {
            return Collections.emptyList();
        }
        List<String> result = new LinkedList<>();
        String[] suffixesT = suffixes.split(",");
        for (File f : ObjectUtils.defaultIfNull(dir.listFiles(), new File[0])) {
            String extensionName = f.getName().substring(f.getName().lastIndexOf(".") + 1);
            if (f.isFile() && ArrayUtils.contains(suffixesT, extensionName)) {
                try {
                    String path = f.getCanonicalPath();
                    result.add(path);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                result.addAll(getAllFileNamesBaseDirWithSuffix(f, suffixes));
            }
        }
        return result;
    }
}
