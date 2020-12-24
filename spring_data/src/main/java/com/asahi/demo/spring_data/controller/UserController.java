package com.asahi.demo.spring_data.controller;

import com.asahi.demo.spring_data.entity.Book;
import com.asahi.demo.spring_data.entity.Role;
import com.asahi.demo.spring_data.entity.User;
import com.asahi.demo.spring_data.repository.BookRepository;
import com.asahi.demo.spring_data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;

    @PostMapping("/saveUser")
    public User saveUser(@RequestBody User user){
        return userRepository.save(user);
    }
    @PostMapping("/getByEmail")
    public User getByEmail(String email){
        return userRepository.getByEmail(email);
    }
    @PostMapping("/getById")
    public User getById(Integer userId){
        return userRepository.getById(userId);
    }
    @PostMapping("/getByUserName")
    public User getByUserName(String username){
        return userRepository.getByUserName(username);
    }
    @PostMapping("/queryByEmailAndUserName")
    public User queryByEmailAndUserName(String email, String username){
        return userRepository.queryByEmailAndUserName(email, username);
    }
    @PostMapping("/findByUsernameLike")
    public Page<User> findByUsernameLike(String username){
        return userRepository.findByUsernameLike(username,PageRequest.of(0, 5));
    }
    @PostMapping("/findByUsernameAndSort")
    public List<User> findByUsernameAndSort(String username){
        return userRepository.findByUsernameAndSort(username,Sort.by(Sort.Direction.DESC,"id"));
    }
    @PostMapping("/updatePasswordByUsername")
    public Integer updatePasswordByUsername(String username,String password){
        return userRepository.updatePasswordByUsername(username,password);
    }
    @PostMapping("/deleteByUsername")
    public Integer deleteByUsername(String username){
        return userRepository.deleteByUsername(username);
    }
    //多对多
    @PostMapping("/getUserRole")
    public Set<Role> getUserRole(Integer id){
        Optional<User> optional = userRepository.findById(id);
        Set<Role> roles = optional.get().getRoles();
        return roles;
    }
    //一对多
    @PostMapping("/getUserBook")
    public Set<Book> getUserBook(Integer id){
        Optional<User> optional = userRepository.findById(id);
        Set<Book> books = optional.get().getBooks();
        return books;
    }
    //多对一
    @PostMapping("/getBookUser")
    public Book getBookUser(Integer id){
        Optional<Book> optional = bookRepository.findById(id);
        Book book = optional.get();
        return book;
    }
    //查所有
    @PostMapping("/getAllUser")
    public List<User> getAllUser(){
        List<User> allUser = userRepository.getAllUser();
        return allUser;
    }
}
