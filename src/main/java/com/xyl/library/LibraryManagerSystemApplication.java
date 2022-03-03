package com.xyl.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@MapperScan("com.xyl.library.mapper")
public class LibraryManagerSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryManagerSystemApplication.class, args);
    }

}
