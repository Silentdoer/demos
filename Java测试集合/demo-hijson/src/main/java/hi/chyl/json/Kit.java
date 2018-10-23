//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package hi.chyl.json;

import javax.swing.tree.DefaultMutableTreeNode;

public class Kit {
    public static final String split = " : ";
    public static final String sign = "-";
    public static final String cNull = "k";
    public static final String cNum = "n";
    public static final String cObj = "o";
    public static final String cArr = "a";
    public static final String cStr = "v";
    public static final String cBool = "b";
    public static final String sNull = "k-";
    public static final String sNum = "n-";
    public static final String sObj = "o-";
    public static final String sArr = "a-";
    public static final String sStr = "v-";
    public static final String sBool = "b-";
    public static final String array = "Array";
    public static final String object = "Object";

    public Kit() {
    }

    public static DefaultMutableTreeNode nullNode(String key) {
        return treeNode("k-" + key + " : " + "<null>");
    }

    public static DefaultMutableTreeNode nullNode(int index) {
        return nullNode(fkey(index));
    }

    public static DefaultMutableTreeNode numNode(String key, String val) {
        return treeNode("n-" + key + " : " + val);
    }

    public static DefaultMutableTreeNode numNode(int index, String val) {
        return numNode(fkey(index), val);
    }

    public static DefaultMutableTreeNode boolNode(String key, Boolean val) {
        String sVal = "false";
        if (val) {
            sVal = "true";
        }

        return treeNode("b-" + key + " : " + sVal);
    }

    public static DefaultMutableTreeNode boolNode(int index, Boolean val) {
        return boolNode(fkey(index), val);
    }

    public static DefaultMutableTreeNode strNode(String key, String val) {
        return treeNode("v-" + key + " : " + "\"" + val + "\"");
    }

    public static DefaultMutableTreeNode strNode(int index, String val) {
        return strNode(fkey(index), val);
    }

    public static DefaultMutableTreeNode objNode(String key) {
        return treeNode("o-" + key);
    }

    public static DefaultMutableTreeNode objNode(int index) {
        return objNode(fkey(index));
    }

    public static DefaultMutableTreeNode arrNode(String key) {
        return treeNode("a-" + key);
    }

    public static DefaultMutableTreeNode arrNode(int index) {
        return arrNode(fkey(index));
    }

    public static DefaultMutableTreeNode treeNode(String str) {
        return new DefaultMutableTreeNode(str);
    }

    public static DefaultMutableTreeNode treeNode(String type, int index, String val) {
        return treeNode(type + "[" + index + "]");
    }

    public static String fkey(int index) {
        return "[" + index + "]";
    }

    public static String fArrKey(int index) {
        return "a-" + fkey(index);
    }

    public static int getIndex(String str) {
        int index = -1;
        if (str != null && str.length() != 0) {
            index = str.lastIndexOf("[");
            if (index >= 0) {
                try {
                    index = Integer.parseInt(str.substring(index + 1, str.length() - 1));
                } catch (Exception var3) {
                    index = -1;
                }
            }

            return index;
        } else {
            return index;
        }
    }

    public static String getKey(String str) {

        if (str != null && str.length() != 0) {
            int index = str.lastIndexOf("[");
            if (index >= 0) {
                return str.substring(0, index);
            } else {
                StringBuffer sb = null;
                return str;
            }
        } else {
            return str;
        }
    }

    public static String[] pstr(String str) {
        String[] arr = new String[]{str.substring(0, 1), null, null};
        int i = str.indexOf(" : ");
        if ("a".equals(arr[0])) {
            arr[1] = str.substring(2);
            arr[2] = "Array";
        } else if ("o".equals(arr[0])) {
            arr[1] = str.substring(2);
            arr[2] = "Object";
        } else if ("v".equals(arr[0])) {
            arr[1] = str.substring(2, i);
            arr[2] = str.substring(i + 4, str.length() - 1);
        } else {
            arr[1] = str.substring(2, i);
            arr[2] = str.substring(i + 3, str.length());
        }

        return arr;
    }
}
