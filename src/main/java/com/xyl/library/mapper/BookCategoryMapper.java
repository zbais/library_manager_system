package com.xyl.library.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xyl.library.entity.BookCategory;
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
public interface BookCategoryMapper extends BaseMapper<BookCategory> {

    int deleteByPrimaryKey(Integer categoryId);


    //分页查询
    List<BookCategory> selectByPageNum(@Param("currIndex") int currIndex, @Param("pageSize") int pageSize);

    int selectAllCount();


}
