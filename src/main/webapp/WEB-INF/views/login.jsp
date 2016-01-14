<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>挚合电商后台管理系统</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/core/jquery.min.js"></script>
    <script type="text/javascript">
        $(function () {
            $('#loginBtn').click(function () {
                doLogin($('#adminCode').val(), $('#adminPwd').val())
            });
        });
        function doLogin(adminCode, adminPwd) {
            if ($.trim(adminCode)=="") {
                alert('登录账号不能为空');
                $('#adminCode').focus();
                return;
            }
            if ($.trim(adminPwd)=="") {
                alert('登录密码不能为空');
                $('#adminPwd').focus();
                return;
            }
            $.ajax({
                url:'/admin/login',
                type:'post',
                data:{
                    adminCode:adminCode,
                    adminPwd:adminPwd
                },
                dataType:'text',
                success:function(data){
                    data = $.parseJSON(data);
                    if(data.success){
                        location.href = "/admin/index";
                    }
                    else{
                        alert(data.msg);
                    }
                }
            });
        }
    </script>
</head>
<body>
<div>
    <p>
        <label>账号：</label>
        <input id="adminCode" name="adminCode">
    </p>

    <p>
        <label>密码：</label>
        <input id="adminPwd" type="password" name="adminPwd">
    </p>

    <p>
        <button id="loginBtn">登录</button>
        <a href="${pageContext.request.contextPath}/merchant/regist">商家注册</a>
    </p>
</div>
</body>
</html>
