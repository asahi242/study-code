package com.demo.services;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("test")
public class Test {

	@POST
	@Path("getTest")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getTest(String json){
		return json;
	}
	
	@POST
	@Path("{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getUser(@PathParam("username") String userName){

		return userName;
	}
	
	@GET
	@Path("/getuser")
	@Produces("text/plain")
	public String getUser(@QueryParam("username") String userName,
							@QueryParam("password") String password){
		
		return "GET: "+userName+password;
	}
	
	@POST
	@Path("/getuser1")
	@Produces("application/x-www-form-urlencoded")
	public String getUser1(@FormParam("username") String userName,
							@FormParam("password") String password){
		
		return "POST: "+userName+password;
	}
	
	@GET
	@Path("/getuser2")
	public String getUser2(@HeaderParam("user-agent") String userAgent){
		
		return userAgent;
	}
	
	@GET
	@Path("/getcookie")
	public String getcookie(@CookieParam("JSESSIONID") String sessionid){
		
		return sessionid;
	}
	
	//localhost:8080/test/2020;author=zhangsan;country=beijing
	@GET
    @Path("{year}")
    public String getBooks(@PathParam("year") String year,
            @MatrixParam("author") String author,
            @MatrixParam("country") String country) {
		return year+author+country;
    }
	
	@POST
	@Path("getValue")
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED})
	@Produces({MediaType.APPLICATION_JSON})
	public String getValue(@DefaultValue("0") @FormParam("num") int num,
			@DefaultValue("zhangsan") @FormParam("name") String name){
		
		return num+"  "+name;
	}

	@POST
	@Path("/getuser3")
	@Produces(MediaType.APPLICATION_FORM_URLENCODED)
	public String getUser3(@Context HttpServletRequest request){
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		return id+"  "+name;
	}

	@POST
	@Path("/getuser4")
	@Consumes("application/x-www-form-urlencoded")
	public String getUser4(@BeanParam User user){
		return user.toString();
	}
}
