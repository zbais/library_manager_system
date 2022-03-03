package com.xyl.library.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xyl.library.entity.Admin;
import com.xyl.library.entity.Book;
import com.xyl.library.entity.BookCategory;
import com.xyl.library.mapper.AdminMapper;
import com.xyl.library.mapper.BookMapper;
import com.xyl.library.service.AdminService;
import com.xyl.library.service.BookCategoryService;
import com.xyl.library.service.BookService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Zbais
 * @since 2021-10-06
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Resource
    private BookMapper bookMapper;

    @Resource
    private BookService bookService;

    @Resource
    private BookCategoryService bookCategoryService;

    @Resource
    private AdminService adminService;

    @Resource
    private AdminMapper adminMapper;

    @Override
    public boolean adminIsExist(String name) {
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.eq("admin_name", name);
        Admin admin = adminService.getOne(wrapper);
        if (admin == null) {
            return false;
        }

        return true;
    }

    @Override
    public Admin adminLogin(String name, String password) {
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        Map<String, Object> map = new HashMap<>();
        map.put("admin_name", name);
        map.put("admin_pwd", password);
        wrapper.allEq(map);
        Admin admin = adminService.getOne(wrapper);
        return admin;
    }

    @Override
    public boolean addBook(Book book) {
        int n = bookMapper.insert(book);
        if (n > 0) {
            return true;
        }
        return false;
    }

    @Override
    public List<BookCategory> getBookCategories() {
        return bookCategoryService.list();
    }

    @Override
    public boolean addBookCategory(BookCategory bookCategory) {
        return bookCategoryService.save(bookCategory);
    }

    @Override
    public boolean updateAdmin(Admin admin, HttpServletRequest request) {
        //获取session对象中admin对象
        Admin sessionAdmin = (Admin) request.getSession().getAttribute("admin");
        admin.setAdminId(sessionAdmin.getAdminId());
        UpdateWrapper<Admin> wrapper = new UpdateWrapper();
        wrapper.eq("admin_id", sessionAdmin.getAdminId());
        int n = adminMapper.update(admin, wrapper);

        if (n > 0) {
            Admin newAdmin = adminMapper.selectById(admin.getAdminId());
            request.getSession().setAttribute("admin", newAdmin);
            return true;
        }
        return false;
    }
}
