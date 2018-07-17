package me.silentdoer.springboottransactiontest.service.impl;

import lombok.experimental.var;
import lombok.val;
import lombok.extern.slf4j.Slf4j;
import me.silentdoer.springboottransactiontest.dao.StudentDoMapper;
import me.silentdoer.springboottransactiontest.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @since 7/9/2018 6:30 PM
 */
@Service
public class TestServiceImpl implements ITestService {

    private final StudentDoMapper studentDoMapper;

    @Autowired
    public TestServiceImpl(StudentDoMapper studentDoMapper) {
        this.studentDoMapper = studentDoMapper;
    }

    /**
     * TODO 如果propagation是REQUIRED，那么当有一个已经是@Transactional的方法调用此方法后，当前方法的事务将会由外部事务统一管理，即内部事务抛出异常会导致外部事务回滚，外部事务
     * 抛出异常也会导致内部事务回滚（a n b，a和b是外部事务的tx操作，n是内部事务的tx操作）；
     *
     * Propagation.NOT_SUPPORTED表示，如果当前存在事务，那么挂起事务，然后当前方法以不用事务的方式执行（即即便抛出异常当前方法的tx操作不会回滚，且当前方法抛出异常不会影响外部方法）
     * 但是外部方法仍然是有事务管理的（可以理解为 a n b，然后n被隐身了）
     *
     * Propagation.NEW则是a n b的外部和内部都会开启单独的事务互不影响，但是n抛出异常n会回滚，a或b抛出异常外部会回滚（但ab和n不会互相影响）
     *
     * TODO @Transactional的Isolation成员是JDK自带也有的，而不是说是要Spring里才有（但是事务传播特性似乎是Spring特有），
     * JDK里设置事务的传播特性用 connection.setTransactionIsolation(Connection.xx)来设置
     */
    @Override
    // 这个注解会是的addStudent(..)内如果存在要用到mybatis的session的操作就会开启事务（如果当前线程没有事务，即没有其他开启了事务的方法调用了本方法）
    // TODO 经过测试这里的rollbackFor的Exception是只要是其子类即可并非精确匹配
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    // TODO 配置为 readOnly = true（默认是false），那么如果这个方法内部的Mapper不是只有select那么会抛异常，而且不存在回滚的说法，因为select不需要回滚
    //@Transactional(readOnly = true)
    public int addStudent(String name, Character gender) {

        // TODO 由于开启了事务，因此自动提交被关闭，那么这里insert本质上是insert到当前session的缓存里，如果整个完整的事务没有抛异常，
        // 那么就会自动提交（即外部的finally会commit，catch会根据rollbackFor等策略选择性rollback，这里的策略还要根据传播特性来决定）
        int count = this.studentDoMapper.insert(name, gender);

        /*Connection connection;
        connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);*/

        // TODO 由于这里抛了异常，会导致上面的insert语句回滚（session缓存的insert的数据会被清除），所以数据库里是看不到数据的
        if (true) {
            throw new RuntimeException("ssss");
        }
        return count;
    }
}
