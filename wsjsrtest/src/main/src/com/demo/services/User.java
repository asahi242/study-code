package com.demo.services;

import javax.ws.rs.FormParam;
import java.util.ArrayList;
import java.util.List;

public class User {
	@FormParam("id")
	private int id;
	@FormParam("name")
	private String name;
	@FormParam("password")
	private String password;
	public static List<User> users;

	static{
		users = new ArrayList<>();

		users.add(new User(1,"zhangsan","123"));
		users.add(new User(2,"lisi","123"));
	}
	
	public User() {
		super();
	}

	public User(int id, String name,String password) {
		this.id = id;
		this.name = name;
		this.password = password;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static List<User> getUsers() {
		return users;
	}

	public static void setUsers(List<User> users) {
		User.users = users;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", name='" + name + '\'' +
				", password='" + password + '\'' +
				'}';
	}

	public List<User> getById(int id){
		List<User> users = User.getUsers();
		List<User> userList = new ArrayList<>();
		for (User user: users){
			if(user.getId()==id){
				userList.add(user);
			}
		}
		return userList;
	}
	public static List<User> getByName(String name){
		List<User> users = User.getUsers();
		List<User> userList = new ArrayList<>();
		for (User user: users){
			if(name.equals(user.getName())){
				userList.add(user);
			}
		}
		return userList;
	}
}
