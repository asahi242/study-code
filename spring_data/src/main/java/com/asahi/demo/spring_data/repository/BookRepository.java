package com.asahi.demo.spring_data.repository;

import com.asahi.demo.spring_data.entity.Book;
import com.asahi.demo.spring_data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository  extends JpaRepository<Book,Integer> {

}
