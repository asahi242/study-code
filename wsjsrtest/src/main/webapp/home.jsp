<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
%>
<html>
<head>
    <title>Title</title>
    <script src="https://cdn.staticfile.org/jquery/1.10.2/jquery.min.js"></script>
    <script src="<%=path%>/js/Mapping.js"></script>
</head>
<body>
<div id="copy">
    <input type="text" name="name">
    <input type="text" name="password">
</div>
<script>
    $(function(){
        ajax();

    })
    function ajax(){
        $.get('user/1',function(data,status){
            if (status=='success'){
                var mapping = new Mapping("copy","name");
                mapping.setValue('name',data.name);
                mapping.setValue('password',data.password);
            }else{
                alert("请求失败")
            }
        });

    }
</script>
</body>
</html>
