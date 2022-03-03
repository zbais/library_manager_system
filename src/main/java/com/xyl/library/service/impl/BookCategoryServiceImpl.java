package com.xyl.library.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.xyl.library.entity.BookCategory;
import com.xyl.library.mapper.BookCategoryMapper;
import com.xyl.library.service.BookCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Zbais
 * @since 2021-10-06
 */
@Service
public class BookCategoryServiceImpl extends ServiceImpl<BookCategoryMapper, BookCategory> implements BookCategoryService {

    @Resource
    private BookCategoryMapper bookCategoryMapper;

    @Autowired
    private BookCategoryService bookCategoryService;

    @Override
    public Page<BookCategory> selectBookCategoryByPageNum(int current) {
        Page<BookCategory> page = new Page();
        //QueryWrapper<BookCategory> queryWrapper = new QueryWrapper<>();
        List<BookCategory> list = bookCategoryMapper.selectByPageNum((current - 1), 10);
        page.setSize(10);
        page.setCurrent(current);
        page.setRecords(list);
        int recordCount = bookCategoryMapper.selectAllCount();
        int total = recordCount / 10;
        if (recordCount % 10 != 0) {
            total++;
        }
        page.setTotal(total);
        return page;
    }

    @Override
    public int deleteBookCategoryById(int bookCategoryId) {
        int n = bookCategoryMapper.deleteById(bookCategoryId);
        return n;
    }
}
