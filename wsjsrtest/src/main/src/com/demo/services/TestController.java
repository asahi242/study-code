package com.demo.services;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import java.io.IOException;
import java.util.List;
@Path("/user")
public class TestController {
    @Context
    HttpServletRequest request;
    @Context
    HttpServletResponse response;
    //查询所有
    @GET
    @Path("users")
    @Produces("application/json")
    public  List<User> getUser(){
        List<User> users = User.getUsers();

        return users;
    }
    //查询单个/进入修改页
    @GET
    @Path("user/{id}")
    @Produces("application/json")
    public User getById(@PathParam("id") int id){
        List<User> users = new User().getById(id);
        return users.get(0);
    }
    //查询单个
    @POST
    @Path("user")
    @Produces("application/json")
    public User getUser(@FormParam("name") String name){
        List<User> users = User.getByName(name);
        return users.get(0);
    }
    //进入添加页
    @GET
    @Path("user")
    public String toAddPage(){
        return  "add.jsp";
    }
    //添加
    @POST
    @Path("user")
    public boolean addUser(@BeanParam User user){
        User.getUsers().add(user);
        return true;
    }
    //修改
    @PUT
    @Path("user")
    public boolean updateUser(@BeanParam User user){
        List<User> users = new User().getById(user.getId());
        users.get(0).setName(user.getName());
        return true;
    }
    //删除
    @DELETE
    @Path("user/{id}")
    public boolean delUser(@PathParam("id") int id){
        List<User> users = new User().getById(id);
        users.remove(users.get(0));
        return true;
    }

    //进入主页
    @GET
    @Path("/tohome")
    public void toHome() throws ServletException, IOException {
        request.getRequestDispatcher("/home.jsp").forward(request,response);
    }
}
