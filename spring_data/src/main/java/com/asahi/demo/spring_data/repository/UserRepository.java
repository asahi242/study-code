package com.asahi.demo.spring_data.repository;

import com.asahi.demo.spring_data.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

//@RepositoryDefinition(domainClass=User.class,idClass=Integer.class) //不使用继承 使用这个也可以
public interface UserRepository extends JpaRepository<User,Integer> {
    //自定义查询
    @Query("select u from User u where u.email=?1")
    User getByEmail(String email);
    //使用命名参数
    @Query("select u from User u where u.id= :id")
    User getById(@Param("id") Integer userId);
    //使用el表达式
    @Query("select u from #{#entityName} u where u.username = ?1")
    User getByUserName(String username);
    //原生查询
    @Query(value = "select * from tb_user u where u.email= :email and u.username=:username" ,nativeQuery = true)
    User queryByEmailAndUserName(@Param("email") String email,@Param("username") String username);
    //分页
    @Query("select u from User u where u.username like ?1%")
    Page<User> findByUsernameLike(String username, Pageable pageable);
    //排序
    @Query("select u from User u where u.username like ?1%")
    List<User> findByUsernameAndSort(String username , Sort sort);
    //修改
    @Modifying
    @Transactional
    @Query("update User u set u.password=?2 where u.username=?1")
    Integer updatePasswordByUsername(String username,String password);
    //删除
    @Modifying
    @Transactional
    @Query("delete from User u where u.username=?1")
    Integer deleteByUsername(String username);

    //查所有
    @Query("select u from User u")
    List<User> getAllUser();

}
