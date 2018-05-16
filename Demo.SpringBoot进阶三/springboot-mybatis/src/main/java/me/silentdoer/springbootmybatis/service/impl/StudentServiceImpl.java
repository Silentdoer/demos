package me.silentdoer.springbootmybatis.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import me.silentdoer.springbootmybatis.dao.StudentMapper;
import me.silentdoer.springbootmybatis.pojo.Student;
import me.silentdoer.springbootmybatis.service.StudentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.Objects;

/**
 * @author silentdoer
 * @version 1.0
 */
@Slf4j
@Service("studentService")
public class StudentServiceImpl implements StudentService {
    @Resource
    private StudentMapper studentMapper;

    /**
     * 为此service方法添加事物管理，默认是readOnly为false，且Propagation是REQUIRED
     */
    @Transactional(readOnly = true)
    @Override
    public Student getSingleStudent(Long uid) {
        if(Objects.isNull(uid) || uid <= 0){
            //throw new UncheckedIOException("")
            throw new IllegalArgumentException(String.format("用于查询的uid不和法，其值为:%s", (uid == null ? "NULL" : uid)));
        }
        return this.studentMapper.selectStudentByPrimaryKey(uid);
    }

    /**
     * TODO PageHelper是Mybatis的一个拦截器插件，和SpringBoot整合可以不用怎么配置，添加相关pom依赖即可
     * ，然后mybatis执行如getStudents()时获取的结果实际上是被PageHelper拦截过的；（即可以不用doSelectPage(..)
     *
     * @param pageNum 页数，从1开始
     * @param pageSize 每页大小
     * @return
     */
    @Override
    public List<Student> getStudents(int pageNum, int pageSize) {
        // TODO 成功，确实是返回了两条而且setOrderBy("uid")没有报错；而uid desc也没报错，但是注意它是先对所有结果进行desc然后才取2条的（num=1，size=2）
        // 如果要取出来后以某个或某些字段排序可以自己用jdk的方法实现；
        /*List<Student> list = PageHelper.startPage(pageNum, pageSize).setOrderBy("uid desc").doSelectPage(() -> studentMapper.selectAll());*/

        // TODO 如果不需要pageInfo，那么可以直接用.doSelectPage()
        // 注意，如果要分页前提是对应的mapper方法本身就是能够获取list的而不是单个，否则只会返回一个list但是元素只有一个
        // TODO 可以使用Page对象作为startPage的参数，page对象里就有设置pageNum、pageSize、orderBy等信息（但似乎要先配置pagehelper的相关信息）
        // 第三个参数是orderBy
        //PageInfo<Student> pageInfo = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> studentMapper.selectStudentByPrimaryKey(1L));
        //List<Student> list = pageInfo.getList();
        // TODO getPages获取的是总页数，
        //pageInfo.getPages();
        // TODO 获取当前是第几页
        //pageInfo.getPageNum();
        // TODO 获取页大小
        //pageInfo.getPageSize();
        PageInfo<Student> pageInfo = PageHelper.startPage(2, pageSize, "uid").doSelectPageInfo(() -> studentMapper.selectAll());
        // pageSizes() 共两页,getPageSize()，pageNum是当前页是第几页，total是总共多少条记录，
        log.info("{},{},{},{},{},{},{},{},{},{},{},{},{}", pageInfo.getPages(), pageInfo.getPageSize(), pageInfo.getPageNum(), pageInfo.getTotal(), pageInfo.getStartRow(),
                pageInfo.getEndRow(), pageInfo.getPrePage(), pageInfo.getNextPage(), pageInfo.getSize(), pageInfo.isIsLastPage(), pageInfo.isHasNextPage(),
                pageInfo.isHasPreviousPage(), pageInfo.isHasPreviousPage());
        List<Student> list = pageInfo.getList();
        return list;
    }
    /*
    getPages()获取的是总共多少页，getPageSize获取的是一般而言每页多少条记录，getPageNum获取的是当前页是第几页（从1开始），
    getTotal获取的是总共有多少条记录，getStartRow是指开始的那一条记录是第几条（相对于所有记录），getEndRow则是当前页的最后一条记录相对于所有记录是第几条
    getPrePage是获取上一页是第几页（注意如果是0说明没有上一页，即当前页是第一页），getNextPage是获取下一页是第几页（TODO 如果没有下一页也是返回0）
    TODO getSize是获取当前页有多少条记录，isIsLastPage判断当前页是否是最后一页，
     */
}
