<%--
  Created by IntelliJ IDEA.
  User: YangDaiChun
  Date: 2016/1/5
  Time: 9:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="cn.com.zhihetech.online.commons.Constant" %>
<html>
<head>
    <title>订单管理</title>
    <c:set var="basePath" value="${pageContext.request.contextPath}"/>
    <script type="text/javascript" src="${basePath}/static/core/app.js"></script>
    <script type="text/javascript">
        app.setBasePath('${basePath}');
        app.setup();
    </script>
    <script type="text/javascript">
        $(document).ready(function () {
            $('#order_dg').datagrid({
                url: '${basePath}/admin/api/order/list',
                title: '所有订单',
                fitColumns: true,
                fit:true,
                pagination: true,
                singleSelect: true,
                rownumbers: true,
                toolbar: '#tb',
                columns: [[
                    {field: 'orderId', checkbox:true,align: 'center'},
                    {field: 'orderCode', title: '订单号', width: 100,align: 'center'},
                    {field: 'orderName', title: '订单名', width: 100, align: 'center'},
                    {
                        field: 'user', title: '买家用户名', width: 100, align: 'center',
                        formatter: function (value) {
                            return value.userName;
                        }
                    },
                    {
                        field: 'orderState', title: '订单状态', width: 100, align: 'center', sortable: true,
                        formatter: function (value) {
                            console.log(value);
                            switch (value) {
                                case <%=Constant.ORDER_STATE_NO_PAYMENT%>:
                                    return "<span style='color: red'>已提交，等待买家付款</span>";
                                    break;
                                case <%=Constant.ORDER_STATE_NO_DISPATCHER%>:
                                    return "<span style='color: firebrick'>买家已付款，等待卖家发货</span>";
                                    break;
                                case <%=Constant.ORDER_STATE_ALREADY_DISPATCHER%>:
                                    return "<span style='color: #0000FF'>卖家已经发货</span>";
                                    break;
                                case <%=Constant.ORDER_STATE_ALREADY_DELIVER%>:
                                    return "<span style='color:green'>买家已经确认收货</span>"
                                    break;
                                case <%=Constant.ORDER_STATE_ALREADY_CANCEL%>:
                                    return "<span style='color: red'>订单已取消</span>"
                            }
                        }
                    },
                    {field: 'userMsg', title: '买家留言', width: 100, align: 'center'},
                    {field: 'receiverName', title: '收件人', width: 100, align: 'center'},
                    {field: 'receiverPhone', title: '收件人电话号码', width: 100, align: 'center'},
                    {field: 'receiverAdd', title: '收件人地址', width: 100, align: 'center'},
                ]]
            });

            $('#seacher_box').searchbox({
                menu: '#seacher_menu',
                prompt: '请输入值'
            });


        });

    </script>
</head>
<body>
<!--toolbar&seacher开始-->
<div id="tb">
    <input id="seacher_box" style="width:15%">
</div>
<div id="seacher_menu">
    <div data-options="name:'orderCode'">订单号</div>
</div>
<!--toolbar&seacher结束-->

<!--数据网格开始-->
<div id="order_dg"></div>
<!--数据网格结束-->


</body>
</html>
