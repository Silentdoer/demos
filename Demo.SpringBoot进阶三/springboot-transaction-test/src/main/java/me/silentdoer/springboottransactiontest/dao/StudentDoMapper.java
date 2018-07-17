package me.silentdoer.springboottransactiontest.dao;

import lombok.experimental.var;
import lombok.val;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @since 7/9/2018 6:33 PM
 */
@Repository
@Mapper
public interface StudentDoMapper {

    @Insert("insert into tb_student" +
                    "(fd_name, fd_gender)" +
                    "values (#{name}, #{gender})")
    //@Options(useGeneratedKeys = true, )
    // TODO 经过测试userGeneratedKeys等只是当需要将主键返回时用到，如果只是需要判断插入多少条数据那么直接将void变成int即可
    int insert(@Param("name") String name, @Param("gender") Character gender);
}
