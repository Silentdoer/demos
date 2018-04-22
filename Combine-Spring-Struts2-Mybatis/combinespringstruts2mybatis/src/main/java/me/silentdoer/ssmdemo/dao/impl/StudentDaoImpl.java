package me.silentdoer.ssmdemo.dao.impl;

import me.silentdoer.ssmdemo.dao.StudentDao;
import me.silentdoer.ssmdemo.po.Student;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author silentdoer
 * @version 1.0
 * @description the description
 * @date 4/21/18 2:31 PM
 */
// this.sqlSessionProxy.selectOne(statement), proxy will use invoke method to call sqlSessionFactory.openSession();
    // this session is sqlSession = new SqlSessionTemplate(sqlSessionFactory); not real SqlSession, in template use proxy to exec
@Repository("studentDao")  // default singleton, but in the inside it's will use SqlSessionFactory to openSession(), so can be singleton.
public class StudentDaoImpl extends SqlSessionDaoSupport implements StudentDao {

    public Student selectOne(long uid) {
        final SqlSession session = this.getSqlSession();
        String statement = "me.silentdoer.ssmdemo.po.StudentMapper.selectOne";
        return session.selectOne(statement, uid);
    }

    /*@Autowired  // by type
    @Qualifier("sqlSessionFactory")*/
    @Resource  // by name and default is setter rm set and lower first char
    @Override
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }
}
