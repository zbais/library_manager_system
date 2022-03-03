package com.xyl.library.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xyl.library.entity.BorrowingBooks;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xyl.library.entity.Vo.BorrowingBooksVo;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Zbais
 * @since 2021-10-06
 */
public interface BorrowingBooksRecordService extends IService<BorrowingBooks> {
    //分页查询所有用户借书记录 【管理员使用】
    public Page<BorrowingBooksVo> selectAllByPage(int pageNum);


    // 查询用户的所有借书记录 【普通用户使用】
    public ArrayList<BorrowingBooksVo> selectAllBorrowRecord(HttpServletRequest request);
}
