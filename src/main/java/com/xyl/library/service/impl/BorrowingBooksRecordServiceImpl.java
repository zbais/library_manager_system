package com.xyl.library.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xyl.library.entity.Book;
import com.xyl.library.entity.BorrowingBooks;
import com.xyl.library.entity.User;
import com.xyl.library.entity.Vo.BorrowingBooksVo;
import com.xyl.library.mapper.BookMapper;
import com.xyl.library.mapper.BorrowingBooksMapper;
import com.xyl.library.mapper.UserMapper;
import com.xyl.library.service.BorrowingBooksRecordService;
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
public class BorrowingBooksRecordServiceImpl extends ServiceImpl<BorrowingBooksMapper, BorrowingBooks> implements BorrowingBooksRecordService {

    @Resource
    private BorrowingBooksMapper borrowingBooksMapper;

    @Resource
    private BookMapper bookMapper;

    @Resource
    private UserMapper userMapper;


    @Override
    public Page<BorrowingBooksVo> selectAllByPage(int pageNum) {
        List<BorrowingBooks> list = borrowingBooksMapper.selectAllByPage((pageNum - 1) * 10, 10);
        if (null == list) {
            return null;
        }
        Page<BorrowingBooksVo> page = new Page<>();
        List<BorrowingBooksVo> borrowingBooksVos = new LinkedList<>();
        for (BorrowingBooks b : list) {
            User user = userMapper.selectById(b.getUserId());
            Book book = bookMapper.selectById(b.getBookId());
            BorrowingBooksVo borrowingBooksVo = new BorrowingBooksVo();

            borrowingBooksVo.setUser(user);
            borrowingBooksVo.setBook(book);
            //日期转换
            Date date1 = b.getDate();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateOfBorrowing = sdf.format(date1);

            //算出还书日期
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date1);
            calendar.add(Calendar.MONTH, 2);
            Date date2 = calendar.getTime();
            String dateOfReturn = sdf.format(date2);

            borrowingBooksVo.setDateOfBorrowing(dateOfBorrowing);
            borrowingBooksVo.setDateOfReturn(dateOfReturn);
            borrowingBooksVos.add(borrowingBooksVo);
        }
        page.setRecords(borrowingBooksVos);
        page.setCurrent(pageNum);
        page.setSize(10);

        //查找总页数
        int recordCount = 0;
        recordCount = borrowingBooksMapper.selectAll();
        //计算页数
        int pageCount = recordCount / 10;
        if (recordCount % 10 != 0) {
            pageCount++;
        }
        page.setTotal(pageCount);
        return page;
    }

    @Override
    public ArrayList<BorrowingBooksVo> selectAllBorrowRecord(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (null == user) {
            return null;
        }

        List<BorrowingBooks> list = borrowingBooksMapper.selectAllBorrowRecord(user.getUserId());
        if (null == list) {
            return null;
        }

        ArrayList<BorrowingBooksVo> borrowingBooksVos = new ArrayList<>();

        for (BorrowingBooks b : list) {
            Book book = bookMapper.selectById(b.getBookId());
            BorrowingBooksVo borrowingBooksVo = new BorrowingBooksVo();

            borrowingBooksVo.setBook(book);
            //日期转换
            Date date1 = b.getDate();
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

            borrowingBooksVos.add(borrowingBooksVo);
        }

        return borrowingBooksVos;
    }
}
