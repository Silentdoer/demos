package util;

public class StackTraceHelper {
    public static String getCurLineInfo(){
        StringBuilder result = new StringBuilder(1024);
        StackTraceElement[] stacks = (new Throwable()).getStackTrace();
        for(int i=1;i<stacks.length;i++){
            result.append(String.format("文件名:%s    类名:%s    方法名:%s    行号:%s%s", stacks[i].getFileName(), stacks[i].getClassName(), stacks[i].getMethodName(), stacks[i].getLineNumber(), System.getProperty("line.separator")));
        }
        return result.toString();
    }
}
