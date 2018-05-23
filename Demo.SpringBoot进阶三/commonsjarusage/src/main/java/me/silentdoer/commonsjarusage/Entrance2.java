package me.silentdoer.commonsjarusage;

import me.silentdoer.commonsjarusage.model.BaseModel;
import me.silentdoer.commonsjarusage.model.TestModel;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
public class Entrance2 {
    public static void main(String[] args){
        BaseModel baseModel = new BaseModel().setFId(33L).setFName("Test");
        System.out.println(baseModel);
        BaseModel foo = new TestModel().setFGender("men").setFMemo("slfk").setFId(88L).setFName("MMM");
        TestModel foo2 = (TestModel) foo;
        System.out.println(foo);
        System.out.println(foo2);
        /*
        这个是当BaseModel上也有@Data时的结果：
        BaseModel(fId=33, fName=Test)
        TestModel(fGender=men, fMemo=slfk)
        TestModel(fGender=men, fMemo=slfk)
         */
    }
}
