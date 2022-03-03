package com.xyl.library.controller;


import com.xyl.library.entity.Department;
import com.xyl.library.entity.User;
import com.xyl.library.entity.Vo.BorrowingBooksVo;
import com.xyl.library.service.BookService;
import com.xyl.library.service.BorrowingBooksRecordService;
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
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Zbais
 * @since 2021-10-06
 */
@Controller
public class UserController {
    @Resource
    private UserService userService;

    @Resource
    private BorrowingBooksRecordService borrowingBooksRecordService;

    @Resource
    private BookService bookService;

    //用户登录
    @PostMapping("/userLogin")
    public String userLogin(@Param("userName") String userName,
                            @Param("password") String password, HttpServletRequest request) {
        User user = userService.userLogin(userName, password);
        if (user != null) {
            //flag = 0 表示用户名密码校验成功 【用于前端校验】
            request.getSession().setAttribute("flag", 0);

            request.getSession().setAttribute("user", user);
            return "user/index";
        }
        //flag = 1 表示登陆失败 【用于前端校验】
        request.getSession().setAttribute("flag", 1);
        return "index";
    }

    //验证用户是否存在
    @RequestMapping("/isUserExist")
    @ResponseBody
    public String isUserExist(@Param("userName") String userName) {
        List<User> users = userService.findUserByUserName(userName);
        if (users == null) {
            return "false";
        }
        if (users.size() < 1) {
            return "false";
        }
        return "true";
    }

    //查找所有部门

    @RequestMapping("/getDepts")
    @ResponseBody
    public List<Department> getDepts() {
        List<Department> depts = userService.findAllDepts();
        return depts;
    }

    //返回用户借书记录页面
    @RequestMapping("/userBorrowBookRecord")
    public String userBorrowBookRecord(Model model, HttpServletRequest request) {
        ArrayList<BorrowingBooksVo> res = borrowingBooksRecordService.selectAllBorrowRecord(request);
        model.addAttribute("borrowingBookList", res);
        return "user/borrowingBooksRecord";
    }

    //返回还书页面
    @RequestMapping("/userReturnBooksPage")
    public String userReturnBooksPage() {
        return "user/returnBooks";
    }

    //返回个人信息页面
    @RequestMapping("/userMessagePage")
    public String userMessagePage(Model model, HttpServletRequest request) {
        User session_user = (User) request.getSession().getAttribute("user");
        User user = userService.findUserById(session_user.getUserId());
        model.addAttribute("message_user", user);
        return "user/userMessage";
    }

    //返回借书页面
    @RequestMapping("/borrowingPage")
    public String borrowing() {
        return "user/borrowingBooks";
    }

    //返回用户首页
    @RequestMapping("userIndex")
    public String userIndex() {
        return "user/index";
    }

    //更新用户信息
    @RequestMapping("/updateUser")
    @ResponseBody
    public boolean updateUser(User user, HttpServletRequest request) {
        return userService.updateUser(user, request);
    }

    //用户还书
    @RequestMapping("userReturnBook")
    @ResponseBody
    public boolean returnBook(int bookId, HttpServletRequest request) {
        return userService.userReturnBook(bookId, request);
    }

    //用户借书
    @RequestMapping("userBorrowingBook")
    @ResponseBody
    public boolean borrowingBook(int bookId, HttpServletRequest request) {
        return userService.userBorrowingBook(bookId, request);
    }

    //返回管理员登陆界面
    @RequestMapping("/adminLoginPage")
    public String adminLoginPage() {
        return "admin/index";
    }

    //用户退出登陆
    @RequestMapping("/userLogOut")
    public String userLogOut(HttpServletRequest request) {
        request.getSession().invalidate();
        return "index";
    }

    //返回用户索书页面
    @RequestMapping("/findBookPage")
    public String findBookPage() {
        return "user/findBook";
    }

    //根据用户id删除用户
    @RequestMapping("/deleteUser")
    @ResponseBody
    public String deleteUserByUserId(@RequestParam("userId") int userId) {
        int res = userService.deleteUserById(userId);
        if (res > 0) {
            return "true";
        }
        return "false";
    }

    //添加用户
    @RequestMapping("/addUser")
    @ResponseBody
    public String addUser(User user) {
        int res = userService.insertUser(user);
        if (res > 0) {
            return "true";
        } else {
            return "false";
        }
    }
}
