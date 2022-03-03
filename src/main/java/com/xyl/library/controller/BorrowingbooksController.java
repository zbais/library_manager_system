package com.xyl.library.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xyl.library.entity.Vo.BorrowingBooksVo;
import com.xyl.library.service.BorrowingBooksRecordService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Zbais
 * @since 2021-10-06
 */
@Controller
public class BorrowingbooksController {
    @Resource
    private BorrowingBooksRecordService borrowingBooksRecordService;

    //返回所有用户借书记录页面
    @RequestMapping("/allBorrowBooksRecordPage")
    public String addBorrowBooksRecordPage(Model model, @RequestParam("pageNum") int pageNum) {
        Page<BorrowingBooksVo> page = borrowingBooksRecordService.selectAllByPage(pageNum);
        model.addAttribute("page", page);
        return "admin/allBorrowingBooksRecord";
    }

}
