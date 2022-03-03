package com.xyl.library.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xyl.library.entity.BorrowingBooks;
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
public interface BorrowingBooksMapper extends BaseMapper<BorrowingBooks> {

    //查询userId用户的所有借书记录
    List<BorrowingBooks> selectAllBorrowRecord(@Param("userId") int userId);

    //查询该userId用户的总借书记录数目
    int selectAllRecordCount(@Param("userId") int userId);

    // 分页查询所有记录
    List<BorrowingBooks> selectAllByPage(@Param("currIndex") int currIndex, @Param("pageSize") int pageSize);

    //查询所有记录总数
    int selectAll();
}
