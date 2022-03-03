package com.xyl.library.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xyl.library.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Zbais
 * @since 2021-10-06
 */
public interface UserMapper extends BaseMapper<User> {
    //分页查询
    List<User> selectByPageNum(@Param("currIndex") int currIndex, @Param("pageSize") int pageSize);

    //查询总数
    int selectUserCount();
}
