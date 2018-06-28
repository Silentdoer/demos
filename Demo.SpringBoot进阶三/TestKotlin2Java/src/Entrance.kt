
import java.util.*

/**
 *
 * @author liqi.wang
 * @version 1.0.0
 */
fun main(args: Array<String>): Unit {
    val list = ArrayList<Long>(8)
    //list[0] = 3
    list.add(3)
    list.addAll(Arrays.asList(4, 5, 6, 7))
    /*for (key in list) {
        println(key)
        if (Objects.equals(key, 4L)) {
            println(list.remove(key))
        }
    }*/
    println(list)
    // kotlin里不存在for (int i = 0; i < length; i++)，因此这里iterator只能拿出来不能一同写在for里，如果kotlin里要用到下标则list.indices
    val listIterator = list.listIterator()
    while (listIterator.hasNext()) {
        // 对于kotlin而言，为5是生成的Int，而为5L生成的是Long，因此这里必须写5L，否则恒不等，或者直接用==，那么kotlin会报Long和Int不匹配
        if (Objects.equals(listIterator.next(), 5L)) {
            listIterator.remove()
        }
    }
    // 读 in di si z是index的复数
    for (index in list.indices) {
        println(list[index])
    }
    println(list)

    for ((index, value) in list.withIndex()) {
        // 当出现类似 aa.bb的时候那么{}不能省略
        println("index:$index, value${value}")
    }
}