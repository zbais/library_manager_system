package com.xyl.library.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xyl.library.entity.Book;
import com.xyl.library.entity.BookCategory;
import com.xyl.library.entity.Vo.BookVo;
import com.xyl.library.service.AdminService;
import com.xyl.library.service.BookCategoryService;
import com.xyl.library.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
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
public class BookController {

    @Resource
    private AdminService adminService;

    @Resource
    private BookService bookService;

    @Resource
    private BookCategoryService bookCategoryService;

    /**
     * 管理员&emsp;&emsp;录入新书
     *
     * @param book
     * @return
     */
    @RequestMapping("/addBook")
    @ResponseBody
    public String addBook(Book book) {
        boolean res = adminService.addBook(book);
        if (res) {
            return "true";
        }
        return "false";
    }

    //返回查询书籍结果页
    @RequestMapping("/showBookaResultPageByCategoryId")
    public String showBookaResultPageByCategoryId(@RequestParam("pageNum") int pageNum,
                                                  @RequestParam("bookCategory") int bookCategory, Model model) {
        Page<BookVo> page = bookService.findBooksByCategoryId(bookCategory, pageNum);
        model.addAttribute("page", page);
        model.addAttribute("bookCategory", bookCategory);
        return "admin/showBooks";
    }

    //返回用户 查询书籍结果页
    @RequestMapping("/findBookByBookPartInfo")
    public String findBookByBookPartInfo(@RequestParam("bookPartInfo") String bookPartInfo, Model model) {
        List<BookVo> bookVos = bookService.selectBooksByBookPartInfo(bookPartInfo);

        model.addAttribute("bookList", bookVos);
        return "user/findBook";

    }

    /**
     * 查询所有书籍种类
     *
     * @return
     */
    @RequestMapping("/findAllBookCategory")
    @ResponseBody
    public List<BookCategory> findAllBookCategory() {
        return adminService.getBookCategories();
    }

    //新建书籍种类
    @RequestMapping("/addBookCategory")
    @ResponseBody
    public String addBookCategory(BookCategory bookCategory) {
        boolean b = adminService.addBookCategory(bookCategory);
        if (b) {
            return "true";
        }
        return "false";
    }

    //根据书籍种类id删除种类

    @RequestMapping("/deleteCategory")
    @ResponseBody
    public String deleteBookCategoryById(@RequestParam("bookCategoryId") int bookCategoryId) {
        int res = bookCategoryService.deleteBookCategoryById(bookCategoryId);
        if (res > 0) {
            return "true";
        }
        return "false";
    }
}
