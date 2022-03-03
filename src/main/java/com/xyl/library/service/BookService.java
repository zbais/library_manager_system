package com.xyl.library.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xyl.library.entity.Book;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xyl.library.entity.Vo.BookVo;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Zbais
 * @since 2021-10-06
 */
public interface BookService extends IService<Book> {
    /**
     * 根据 书籍的部分信息 去数据库中查找书籍
     *
     * @param partInfo
     * @return
     */
    List<BookVo> selectBooksByBookPartInfo(String partInfo);

    /**
     * 根据书籍种类id查找书籍,分页查找
     *
     * @param categoryId
     * @return
     */
    Page<BookVo> findBooksByCategoryId(int categoryId, int pageNum);
}
