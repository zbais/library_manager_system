package com.xyl.library;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LibraryManagerSystemApplicationTests {

/*    @Resource
    private UserService userService;

    @Resource
    private UserMapper userMapper;

    @Resource
    private BorrowingBooksRecordService borrowingBooksRecordService;

    @Resource
    private BorrowingBooksMapper borrowingBooksMapper;

    @Test
    void getUser(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", "user1").eq("user-pwd", "123456");
        User user = userMapper.selectOne(queryWrapper);
        System.out.println(user);
    }*/

    @Test
    void contextLoads() {

    }

/*    @Test
    void userBorrowBookRecord(){
        List<BorrowingBooks> borrowingBooks = borrowingBooksMapper.selectAllBorrowRecord(1);
        System.out.println(borrowingBooks);
    }*/

}
