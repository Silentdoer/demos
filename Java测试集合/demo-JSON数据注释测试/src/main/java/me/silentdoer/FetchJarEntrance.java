package me.silentdoer;

import java.io.*;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 10/19/2018 6:25 PM
 */
public class FetchJarEntrance {

    /**
     * 从exe里提取jar包
     */
    public static void main(String[] args) throws IOException {
        String file = "E:\\Desktop\\HiJson 2.1.2_jdk64.exe";
        String output = "E:\\Desktop\\HiJson 2.1.2_jdk64.jar";
        FileInputStream fin = new FileInputStream(file); // 可以将整个exe文件解码
        FileOutputStream fout = new FileOutputStream(output);
        BufferedInputStream bin = new BufferedInputStream(fin);
        BufferedOutputStream bout = new BufferedOutputStream(fout);
        int in = 0;
        do {
            in = bin.read();
            if (in == -1) {
                break;
            }
            in ^= 0x88;
            bout.write(in);
        } while (true);
        bin.close();
        fin.close();
        bout.close();
        fout.close();
    }
}
