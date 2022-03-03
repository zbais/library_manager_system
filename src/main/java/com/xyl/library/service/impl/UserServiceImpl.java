package com.xyl.library.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xyl.library.entity.Book;
import com.xyl.library.entity.BorrowingBooks;
import com.xyl.library.entity.Department;
import com.xyl.library.entity.User;
import com.xyl.library.entity.Vo.BorrowingBooksVo;
import com.xyl.library.mapper.BookMapper;
import com.xyl.library.mapper.BorrowingBooksMapper;
import com.xyl.library.mapper.DepartmentMapper;
import com.xyl.library.mapper.UserMapper;
import com.xyl.library.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Zbais
 * @since 2021-10-06
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private DepartmentMapper departmentMapper;

    @Resource
    private BorrowingBooksMapper borrowingBooksMapper;

    @Resource
    private BookMapper bookMapper;

    @Override
    public List<User> findUserByUserName(String userName) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userName", userName);
        List<User> users = userMapper.selectList(queryWrapper);
        return users;
    }

    @Override
    public List<Department> findAllDepts() {
        QueryWrapper<Department> queryWrapper = new QueryWrapper<>();
        List<Department> departments = departmentMapper.selectList(queryWrapper);
        return departments;
    }


    @Override
    public User userLogin(String userName, String password) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", userName).eq("user_pwd", password);
        User user = userMapper.selectOne(queryWrapper);
        return user;
    }

    @Override
    public boolean updateUser(User user, HttpServletRequest request) {
        User sessionUser = (User) request.getSession().getAttribute("user");
        user.setUserId(sessionUser.getUserId());
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        HashMap<String, Object> map = new HashMap<>();
        map.put("user_name", sessionUser.getUserName());
        map.put("user_pwd", sessionUser.getUserPwd());
        map.put("user_email", sessionUser.getUserEmail());
        updateWrapper.allEq(map).eq("user_id", sessionUser.getUserId());
        int n = userMapper.update(user, updateWrapper);
        if (n > 0) {
            //修改成功，更新session对象
            User newUser = userMapper.selectById(user.getUserId());
            request.getSession().setAttribute("user", newUser);
            return true;
        }
        return false;
    }

    @Override
    public List<BorrowingBooksVo> findAllBorrowingBooks(HttpServletRequest request) {
        User sessionUser = (User) request.getSession().getAttribute("user");
        //设置查询条件 userId
        QueryWrapper<BorrowingBooks> queryWrapper = new QueryWrapper<>();
        List<BorrowingBooks> borrowingBooksList = borrowingBooksMapper.selectList(queryWrapper);
        if (borrowingBooksList == null) {
            return null;
        }
        //将数据库表对应的对象(Do)转化成视图层对象（VO）
        List<BorrowingBooksVo> res = new LinkedList<>();
        for (BorrowingBooks bb : borrowingBooksList) {
            Book book = bookMapper.selectById(bb.getBookId());
            BorrowingBooksVo borrowingBooksVo = new BorrowingBooksVo();
            borrowingBooksVo.setBook(book);
            //日期转换
            Date date1 = bb.getDate();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateOfBorrowing = sdf.format(date1);

            //算出还书日期
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date1);
            calendar.add(Calendar.MONTH, 2);//增加两个月
            Date date2 = calendar.getTime();
            String dateOfReturn = sdf.format(date2);

            borrowingBooksVo.setDateOfBorrowing(dateOfBorrowing);
            borrowingBooksVo.setDateOfReturn(dateOfReturn);
            res.add(borrowingBooksVo);
        }
        return res;
    }

    @Override
    public boolean userReturnBook(int bookId, HttpServletRequest request) {
        User sessionUser = (User) request.getSession().getAttribute("user");
        //设置查询条件 userId
        QueryWrapper<BorrowingBooks> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", sessionUser.getUserId()).eq("book_id", bookId);
        //删除数据库中user_d等于userId,book_id等于bookId的记录
        int n = borrowingBooksMapper.delete(queryWrapper);
        if (n > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean userBorrowingBook(int bookId, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");

        //检查该书是否可借,从借书记录表中查询该书是否已借出
        QueryWrapper<BorrowingBooks> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("book_id", bookId);
        List<BorrowingBooks> list = borrowingBooksMapper.selectList(queryWrapper);
        if (list.size() > 0) {
            return false;
        }
        BorrowingBooks borrowingbooks = new BorrowingBooks();
        borrowingbooks.setBookId(bookId);
        borrowingbooks.setUserId(user.getUserId());
        borrowingbooks.setDate(new Date());
        int n = 0;
        try {
            //数据库中增加一条借书记录 【如果插入失败 , 则借书失败】
            n = borrowingBooksMapper.insert(borrowingbooks);
        } catch (Exception e) {
            return false;
        }
        if (n > 0) {
            return true;
        }
        return false;
    }

    @Override
    public User findUserById(int id) {
        return userMapper.selectById(id);
    }

    @Override
    public Page<User> findUserByPage(int pageNum) {
        List<User> users = userMapper.selectByPageNum((pageNum - 1) * 10, 10);
        Page<User> page = new Page<>();
        page.setRecords(users);
        page.setCurrent(pageNum);
        page.setSize(10);

        int userCount = userMapper.selectUserCount();
        int pageCount = userCount / 10;
        if (userCount % 10 != 0) {
            pageCount++;
        }
        page.setTotal(pageCount);
        return page;
    }

    @Override
    public int insertUser(User user) {
        return userMapper.insert(user);
    }

    @Override
    public int deleteUserById(int userId) {
        return userMapper.deleteById(userId);
    }
}
