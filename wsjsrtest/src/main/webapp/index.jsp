<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2020/12/31
  Time: 10:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="https://cdn.staticfile.org/jquery/1.10.2/jquery.min.js">
    </script>
</head>
<body>
    <input type="text" name="name">
    <input type="text" name="password">
    <input id="login" type="button" value="确定">
    <script>
        $("#login").click(function(){
            var name = $("input[name=name]").val();
            var password = $("input[name=password]").val();
            // $.ajax({
            //     url:"/user/user/"+name,
            //     type:"get",
            //     dataType:"json",
            //     success:function(obj){
            //         console.log(obj);
            //         if (obj.password==password){
            //             alert("登录成功");
            //         }else{
            //             alert("用户名密码错误");
            //         }
            //     },
            //     error:function(){
            //         alert("请求失败");
            //     }
            // })
            $.get('/user/user/'+name,function(data,status){
                if (status=='success'){
                    if (obj.password==password){
                            alert("登录成功");
                        }else{
                            alert("用户名密码错误");
                        }
                }else{
                    alert("请求失败")
                }
            });
        })


    </script>
</body>
</html>
