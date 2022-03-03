package com.xyl.library.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xyl.library.entity.BookCategory;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Zbais
 * @since 2021-10-06
 */
public interface BookCategoryService extends IService<BookCategory> {
    //图书类别分页查询
    public Page<BookCategory> selectBookCategoryByPageNum(int pageNum);

    int deleteBookCategoryById(int bookCategoryId);
}
