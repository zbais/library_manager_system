package com.xyl.library.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xyl.library.entity.Department;
import com.xyl.library.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xyl.library.entity.Vo.BorrowingBooksVo;

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
public interface UserService extends IService<User> {
    // 查询用户名 为"userName"的所有用户
    List<User> findUserByUserName(String userName);

    //查询所有部门
    List<Department> findAllDepts();

    //用户登录
    User userLogin(String userName, String password);

    //更新用户信息
    boolean updateUser(User user, HttpServletRequest request);

    //查询用户借书记录
    List<BorrowingBooksVo> findAllBorrowingBooks(HttpServletRequest request);

    //用户还书
    boolean userReturnBook(int bookId, HttpServletRequest request);

    //用户借书
    boolean userBorrowingBook(int bookId, HttpServletRequest request);

    //通过id查找用户
    User findUserById(int id);

    //分页查询用户
    Page<User> findUserByPage(int pageNum);

    //添加用户
    int insertUser(User user);

    //根据用户id删除用户
    int deleteUserById(int userId);
}
