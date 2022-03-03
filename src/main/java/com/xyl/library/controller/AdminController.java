package com.xyl.library.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xyl.library.entity.Admin;
import com.xyl.library.entity.BookCategory;
import com.xyl.library.entity.User;
import com.xyl.library.entity.Vo.BookVo;
import com.xyl.library.service.AdminService;
import com.xyl.library.service.BookCategoryService;
import com.xyl.library.service.UserService;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Zbais
 * @since 2021-10-06
 */
@Controller
public class AdminController {

    @Resource
    private AdminService adminService;
    @Resource
    private BookCategoryService bookCategoryService;
    @Resource
    private UserService userService;

    //判断admin是否存在
    @RequestMapping("/isAdminExist")
    @ResponseBody
    public String adminIsExist(@Param("adminName") String adminName) {
        boolean b = adminService.adminIsExist(adminName);
        if (b) {
            return "true";
        } else {
            return "false";
        }
    }

    //管理员登陆
    @PostMapping("/adminLogin")
    public String adminLogin(@Param("userName") String userName, @Param("password") String password, HttpServletRequest request) {
        Admin admin = adminService.adminLogin(userName, password);
        if (admin == null) {
            //flag 为1 表示登陆失败
            request.getSession().setAttribute("flag", 1);
            return "index";
        }
        //flag 为0 表示用户名密码校验成功
        request.getSession().setAttribute("flag", 0);
        request.getSession().setAttribute("admin", admin);
        return "admin/index";

    }

    //返回添加书籍页面
    @RequestMapping("/addBookPage")
    public String addBookPage() {
        return "admin/addBook";
    }

    //返回添加类别页面
    @RequestMapping("/addCategoryPage")
    public String addCategoryPage(@RequestParam("pageNum") int pageNum, Model model) {
        Page<BookCategory> page = bookCategoryService.selectBookCategoryByPageNum(pageNum);
        model.addAttribute("page", page);
        return "admin/addCategory";
    }

    //返回查询状态页面
    @RequestMapping("/showStausPage")
    public String showStausPage() {
        return "admin/showStaus";
    }

    //返回管理员首页
    @RequestMapping("/adminIndex")
    public String returnAdminIndexPage() {
        return "admin/index";
    }

    //返回查询用户页面
    @RequestMapping("/showUsersPage")
    public String showUserPage(Model model, @RequestParam("pageNum") int pageNum) {
        Page<User> page = userService.findUserByPage(pageNum);
        model.addAttribute("page", page);
        return "admin/showUsers";
    }

    //返回查询书籍页面
    @RequestMapping("/showBooksPage")
    public String showBooksPage(Model model) {
        Page<BookVo> page = new Page<>();
        page.setTotal(1);
        page.setCurrent(1);
        model.addAttribute("page", page);
        return "admin/showBooks";
    }

    //管理员退出登陆
    @RequestMapping("/adminLogOut")
    public String userLogOut(HttpServletRequest request) {
        request.getSession().invalidate();
        return "index";
    }

    //返回新增用户界面
    @RequestMapping("addUserPage")
    public String addUserPage() {
        return "index";
    }

    @RequestMapping("/adminInfoPage")
    public String adminInfo() {
        return "admin/adminInfo";
    }

    //更新管理员信息
    @RequestMapping("/updateAdmin")
    @ResponseBody
    public boolean updateAdmin(Admin admin, HttpServletRequest request) {
        return adminService.updateAdmin(admin, request);
    }


}
