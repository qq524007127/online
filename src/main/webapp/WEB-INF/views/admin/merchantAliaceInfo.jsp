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
    <script>
    </script>
    <style>
        table {
            border: solid #add9c0;
            border-width: 1px;
        }

        table img {
            width: 700px;
        }

        td {
            width: 20%;
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

        .info_t tr td {
            width: 14%;
        }

    </style>
</head>
<body>
<table align="center" cellspacing="0px" cellpadding="0px" width="90%">
    <tr>
        <td colspan="4" class="info_title">活动基本信息</td>
    </tr>
    <tr class="title">
        <td>活动名称:</td>
        <td>发起商家:</td>
        <td>商品名称:</td>
        <td>商品类别:</td>
    </tr>
    <tr>
        <td>${requestScope.activity.activitName}</td>
        <td>${requestScope.activity.activityPromoter.merchName}</td>
        <td>${requestScope.activity.category.categName}</td>
        <td>${requestScope.activity.attributeSet.goodsAttSetName}</td>
    </tr>
    <tr class="title">
        <td>联系电话:</td>
        <td>活动创建时间:</td>
        <td>开始时间:</td>
        <td>结束时间:</td>
    </tr>
    <tr>
        <td>${requestScope.activity.contactTell}</td>
        <td>${requestScope.activity.createDate}</td>
        <td>${requestScope.activity.beginDate}</td>
        <td>${requestScope.activity.endDate}</td>
    </tr>
    <tr class="title">
        <td>活动备注信息:</td>
        <td>当前状态内容描述:</td>
        <td>活动联系人:</td>
        <td></td>
    </tr>
    <tr>
        <td>${requestScope.activity.activitDesc}</td>
        <td>${requestScope.activity.displayState}</td>
        <td>${requestScope.activity.contacterName}</td>
        <td></td>
    </tr>
    <tr class="title">
        <td colspan="4" class="info_title">参与活动的商家</td>
    </tr>
    <tr>
        <td colspan="4">
            <table align="center" style="width: 100%">
                <c:set var="index" value="0"></c:set>
                <c:forEach items="${requestScope.merchants.rows}" var="item">
                    <tr>
                        <td colspan="4">商家${index = index+1}</td>
                    </tr>
                    <tr class="title">
                        <td>商家名称</td>
                        <td>联系电话</td>
                        <td>所属商圈</td>
                        <td>入驻时间</td>
                    </tr>
                    <tr>
                        <td>${item.merchName}</td>
                        <td>${item.merchTell}</td>
                        <td>${item.district.districtName}</td>
                        <td>${item.createDate}</td>
                    </tr>
                    <tr class="title">
                        <td>详细地址</td>
                        <td>红包</td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>${item.address}</td>
                        <td>
                            <c:forEach items="${requestScope.redEnvelops}" var="item">
                                <c:forEach items="${item.value}" var="ite">
                                    ${ite.totalMoney}
                                </c:forEach>
                            </c:forEach>
                        </td>
                        <td></td>
                        <td></td>
                    </tr>
                </c:forEach>
            </table>
        </td>
    </tr>
    <tr class="title">
        <td colspan="4" class="info_title">活动顶部封面图</td>
    </tr>
    <tr>
        <td colspan="4"><img src="${requestScope.activity.coverImg.url}" style="width: 100%"></td>
    </tr>
</table>
</body>
</html>
