package me.silentdoer.springbootjpa.dao;

import me.silentdoer.springbootjpa.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 第一个泛型是@Entity的类，第二个是该类ID的类型，实现这个接口后自动就能调用findById之类的接口了，也是会通过代理产生Dao接口的实现类
 * @author liqi.wang
 * @version 1.0.0
 */
public interface AccountDao extends JpaRepository<Account, Long> {
}
