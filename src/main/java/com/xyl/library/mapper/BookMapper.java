package com.xyl.library.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xyl.library.entity.Book;
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
public interface BookMapper extends BaseMapper<Book> {

    //按书籍种类分页查找
    List<Book> selectByCategoryId(@Param("categoryId") int categoryId, @Param("currIndex") int currIndex, @Param("pageSize") int PageSize);

    //查找某一类别书籍的总数
    int selectBookCountByCategoryId(@Param("categoryId") int categoryId);
}
