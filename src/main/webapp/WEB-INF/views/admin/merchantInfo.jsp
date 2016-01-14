<%--
  Created by IntelliJ IDEA.
  User: YangDaiChun
  Date: 2015/12/9
  Time: 11:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title></title>
    <c:set var="basePath" value="${pageContext.request.contextPath}"/>
    <script type="text/javascript" src="${basePath}/static/core/app.js"></script>
    <script type="text/javascript">
        app.setBasePath('${basePath}');
        app.setup();
    </script>
    <style>
        table {
            border: solid #add9c0;
            border-width: 1px;
        }

        table img{
            width:100%;
            height:auto;;
        }

        td {
            width: 50%;
            border-collapse: collapse;
            border: solid #add9c0;
            border-width: 0px 1px 1px 0px
        }

        table tr td {
            text-align: center;
            font-size: 12px;
            height: 26px;
        }

        .info_title {
            background-color: #67b168;
            color: #FFFFFF;
            font-size: 15px;
            font-weight: bold;
        }

        .title {
            color: #67b168;
            font-size: 14px;
            font-weight: bold;
            background-color: #edf4ff;
        }
    </style>
</head>
<body>
<table align="center" cellspacing="0px" cellpadding="0px">
    <tr>
        <td colspan="2" class="info_title">基本信息</td>
    </tr>
    <tr class="title">
        <td>商家名</td>
        <td>商家所属商圈</td>
    </tr>
    <tr>
        <td>${requestScope.merchant.merchName}</td>
        <td>${requestScope.merchant.district.districtName}</td>
    </tr>
    <tr class="title">
        <td>商家电话号码</td>
        <td>商家地址</td>
    </tr>
    <tr>
        <td>${requestScope.merchant.merchTell}</td>
        <td>${requestScope.merchant.address}</td>
    </tr>
    <tr class="title">
        <td>商家经营范围</td>
        <td>企业规模（员工人数）</td>
    </tr>
    <tr>
        <td>${requestScope.merchant.businScope}</td>
        <td>${requestScope.merchant.emplyCount}人</td>
    </tr>
    <tr class="title">
        <td>工商执照注册码</td>
        <td>税务登记证号</td>
    </tr>
    <tr>
        <td>${requestScope.merchant.licenseCode}</td>
        <td>${requestScope.merchant.taxRegCode}</td>
    </tr>
    <tr class="title">
        <td>组织机构代码</td>
        <td>商家地标</td>
    </tr>
    <tr>
        <td>${requestScope.merchant.orgCode}</td>
        <td>经度${requestScope.merchant.longitude}°,纬度${requestScope.merchant.latitude}°</td>
    </tr>
    <tr class="title">
        <td>入驻时间</td>
        <td>是否有效</td>
    </tr>
    <tr>
        <td>${requestScope.merchant.createDate}</td>
        <td>${requestScope.merchant.permit}</td>
    </tr>
    <tr>
        <td colspan="2" class="info_title">运营者（联系人）信息</td>
    </tr>
    <tr class="title">
        <td>商家联系人姓名</td>
        <td>联系人部门与职位</td>
    </tr>
    <tr>
        <td>${requestScope.merchant.contactName}</td>
        <td>${requestScope.merchant.contactPartAndPositon}</td>
    </tr>
    <tr class="title">
        <td>联系人手机号码</td>
        <td>联系人电子邮箱</td>
    </tr>
    <tr>
        <td>${requestScope.merchant.contactMobileNO}</td>
        <td>${requestScope.merchant.contactEmail}</td>
    </tr>
    <tr class="title">
        <td>联系人身份证号</td>
        <td></td>
    </tr>
    <tr>
        <td>${requestScope.merchant.contactID}</td>
        <td></td>
    </tr>
    <tr>
        <td colspan="2" class="info_title">注册时提供的相关材料(图片)</td>
    </tr>
    <tr class="title">
        <td colspan="2">运营者手持身份证照片</td>
    </tr>
    <tr>
        <td colspan="2"><img src="${requestScope.merchant.opraterIDPhoto.url}" style="width: 700px"></td>
    </tr>
    <tr class="title">
        <td colspan="2">组织机构代码证原件照片</td>
    </tr>
    <tr>
        <td colspan="2"><img src="${requestScope.merchant.orgPhoto.url}" style="width: 700px"></td>
    </tr>
    <tr class="title">
        <td colspan="2">工商营业执照原件照片</td>
    </tr>
    <tr>
        <td colspan="2"><img src="${requestScope.merchant.busLicePhoto.url}" style="width: 700px"></td>
    </tr>
    <tr class="title">
        <td colspan="2">加盖公章的申请认证公函</td>
    </tr>
    <tr>
        <td colspan="2"><img src="${requestScope.merchant.applyLetterPhoto.url}" style="width: 700px"></td>
    </tr>
</table>
</body>
</html>
