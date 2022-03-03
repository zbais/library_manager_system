package com.xyl.library.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xyl.library.entity.Book;
import com.xyl.library.entity.BorrowingBooks;
import com.xyl.library.entity.Vo.BookVo;
import com.xyl.library.mapper.BookMapper;
import com.xyl.library.mapper.BorrowingBooksMapper;
import com.xyl.library.service.BookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
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
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {

    @Resource
    private BookMapper bookMapper;

    @Resource
    private BorrowingBooksMapper borrowingBooksMapper;

    @Override
    public List<BookVo> selectBooksByBookPartInfo(String partInfo) {
        List<BookVo> bookVos = new LinkedList<>();
        QueryWrapper<Book> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("book_name", partInfo);
        List<Book> books = bookMapper.selectList(queryWrapper);

        if (books == null) {
            return bookVos;
        }
        for (Book b : books) {
            BookVo bookVo = new BookVo();
            bookVo.setBookId(b.getBookId());
            bookVo.setBookName(b.getBookName());
            bookVo.setBookAuthor(b.getBookAuthor());
            bookVo.setBookPublish(b.getBookPublish());
            QueryWrapper<BorrowingBooks> wrapper = new QueryWrapper<>();
            wrapper.eq("book_id", b.getBookId());
            List<BorrowingBooks> borrowingBooks = borrowingBooksMapper.selectList(wrapper);
            if (borrowingBooks == null || borrowingBooks.size() < 1) {
                bookVo.setIsExist("可借");
            } else {
                bookVo.setIsExist("不可借");
            }
            bookVos.add(bookVo);
        }
        return bookVos;
    }

    @Override
    public Page<BookVo> findBooksByCategoryId(int categoryId, int pageNum) {
        List<Book> books = bookMapper.selectByCategoryId(categoryId, (pageNum - 1) * 10, 10);
        List<BookVo> bookVos = new LinkedList<>();
        Page<BookVo> page = new Page<>();
        if (books == null) {
            page.setCurrent(1);
            page.setTotal(1);
            return page;
        }
        for (Book b : books) {
            BookVo bookVo = new BookVo();
            bookVo.setBookId(b.getBookId());
            bookVo.setBookName(b.getBookName());
            bookVo.setBookAuthor(b.getBookAuthor());
            bookVo.setBookPublish(b.getBookPublish());
            QueryWrapper<BorrowingBooks> wrapper = new QueryWrapper<>();
            wrapper.eq("book_id", b.getBookId());
            List<BorrowingBooks> borrowingBooks = borrowingBooksMapper.selectList(wrapper);
            if (borrowingBooks == null || borrowingBooks.size() < 1) {
                bookVo.setIsExist("可借");
            } else {
                bookVo.setIsExist("不可借");
            }
            bookVos.add(bookVo);
        }
        page.setRecords(bookVos);
        page.setCurrent(pageNum);
        page.setSize(10);
        int bookCount = bookMapper.selectBookCountByCategoryId(categoryId);
        int pageCount = 0;
        pageCount = bookCount / 10;
        if (bookCount % 10 != 0) {
            pageCount++;
            ;
        }
        page.setTotal(pageCount);
        if (bookCount == 0) {
            page.setTotal(1);
        }
        return page;
    }
}
