import org.apache.commons.collections.ListUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 10/18/2018 11:31 AM
 */
public class GroupingByEntrance {

    public static class Student {

        private Long id;

        private String name;

        private Integer sClass;

        public Student(Long id, String name, Integer sClass) {
            this.id = id;
            this.name = name;
            this.sClass = sClass;
        }

        @Override
        public String toString() {
            return String.valueOf(this.sClass);
        }
    }

    public static void main(String[] args) {
        List<Student> records = new LinkedList<>();
        Student stud1 = new Student(1L, "aa", 1);
        Student stud2 = new Student(1L, "bb", 2);
        Student stud3 = new Student(1L, "cc", 1);
        records.addAll(Arrays.asList(stud1, stud2, stud3));

        // Collectors.groupingBy(fun)的fun传入元素，返回的是分类器classifier，这里的分类器是sClass；
        // 最终得到一个Map，其中key是分类器，值是同一分类器的元素集合
        Map<Integer, List<Student>> group = records.stream().collect(Collectors.groupingBy(r -> r.sClass));
        // group的key是sClass，即1班和2班，然后value是1班的学生集合2班的学生集
        List<List<Student>> reduce = group.entrySet().stream().reduce(new LinkedList<List<Student>>(), (a, b) -> {
            a.add(b.getValue());
            return a;
        }, (a, b) -> {a.addAll(b);
            System.err.println("Test"); return a;});
        // 输出[[1, 1], [2]]，说明确实经过了分组
        System.out.println(reduce);
        System.out.println(records);

        // TODO 产生了一个新的集合，但是不会产生新的元素
        System.out.println(records.stream().sorted(Comparator.comparingInt(a -> a.sClass)).collect(Collectors.toList()));
        System.out.println(records);

    }
}
