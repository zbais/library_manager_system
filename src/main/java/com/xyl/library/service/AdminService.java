package com.xyl.library.service;

import com.xyl.library.entity.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xyl.library.entity.Book;
import com.xyl.library.entity.BookCategory;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Zbais
 * @since 2021-10-06
 */
public interface AdminService extends IService<Admin> {
    //验证用户是否存在
    boolean adminIsExist(String name);

    //管理员登陆
    Admin adminLogin(String name, String password);

    //&emsp;&emsp;录入新书
    boolean addBook(Book book);

    //获取所有图书类别
    List<BookCategory> getBookCategories();

    //增加图书类别
    boolean addBookCategory(BookCategory bookCategory);

    // 更新管理员信息
    boolean updateAdmin(Admin admin, HttpServletRequest request);

}
