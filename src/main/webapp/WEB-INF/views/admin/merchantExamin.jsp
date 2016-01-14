<%--
  Created by IntelliJ IDEA.
  User: YangDaiChun
  Date: 2015/12/8
  Time: 17:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>商家审核管理</title>
    <c:set var="basePath" value="${pageContext.request.contextPath}"/>
    <script type="text/javascript" src="${basePath}/static/core/app.js"></script>
    <script type="text/javascript">
        app.setBasePath('${basePath}');
        app.setup();
    </script>
    <script type="text/javascript">
        $(function () {
            /*========初始化商家数据网格========*/
            $('#examin_win').datagrid({
                url: '/admin/api/merchant/examinList',
                fit: true,
                fitColumns: true,
                pagination: true,
                columns: [[
                    {field: 'merchantId', checkbox: true, width: 100, align: 'center'},
                    {field: 'merchName', title: '商家名', width: 100, align: 'center'},
                    {
                        field: 'admin', title: '商家登录账号', width: 100, align: 'center',
                        formatter: function (data) {
                            if (data) {
                                return data.adminCode;
                            }
                        }
                    },
                    {field: 'address', title: '商家地址', width: 100, align: 'center'},
                    {field: 'merchTell', title: '商家联系电话', width: 100, align: 'center'},
                    {field: 'licenseCode', title: '工商执照注册码', width: 100, align: 'center'},
                    {field: 'taxRegCode', title: '税务登记证号', width: 100, align: 'center'},
                    {
                        field: 'examinState', title: '审核状态', width: 100, align: 'center', sortable: true,
                        formatter: function (data) {
                            return '<span style="color: red">待审核</span>';
                        },
                    },
                    {field: 'examinMsg', title: '审核信息', width: 100, align: 'center'}
                ]],
                rownumbers: true,
                singleSelect: true,
                checkOnSelect: true,
                toolbar: [{
                    text: '查看详细信息',
                    iconCls: 'icon-application-form-edit',
                    handler: function () {
                        browse();
                    }
                }]
            });

            /*========浏览商家提交的详细信息审核========*/
            function browse() {
                var rows = $('#examin_win').datagrid('getSelections');
                if (rows.length != 0) {
                    $('#browse_win').dialog({
                        title: '查看详细信息',
                        iconCls: 'icon-application-form-edit',
                        width: 800,
                        height: 500,
                        modal: true,
                        resizable: false,
                        onBeforeOpen: function () {
                            $('#browse_win').attr('src', "/admin/merchant/info/" + rows[0].merchantId);
                        },
                        buttons: [{
                            text: '通过审核',
                            iconCls: 'icon-ok',
                            handler: function () {
                                selectRole(rows[0]);
                            }
                        }, {
                            text: '审核未通过',
                            iconCls: 'icon-cancel',
                            handler: function () {
                                dismiss(rows[0]);
                            }
                        }]
                    });
                } else {
                    $.messager.alert('提示', '你未选中一条数据');
                }
            }

            /*========商家通过审核后添加为管理人员========*/
            function selectRole(merchant) {
                $('#select_role_win').dialog({
                    title: '添加为管理员',
                    width: '400px',
                    iconCls: 'icon-edit',
                    modal: true,
                    buttons: [{
                        text: '确认',
                        iconCls: 'icon-ok',
                        handler: function () {
                            $('#select_role_form').form('submit', {
                                success: function (data) {
                                    data = $.parseJSON(data);
                                    $.messager.show({
                                        title: '提示',
                                        msg: data.msg
                                    });
                                    if (data.success) {
                                        $('#select_role_win').dialog('close');
                                        $('#browse_win').dialog('close');
                                        $('#examin_win').datagrid('load');
                                    }
                                }
                            });
                        },
                    }],
                    onBeforeOpen: function () {
                        $('#select_role_form').form('reset');
                        $('#merchantId').attr('value', merchant.merchantId);
                        $('#add_adminCode').attr('value', merchant.merchTell);
                    }
                });
            }

            /*========商家通过审核未通过返回为通过信息========*/
            function dismiss(merchant) {
                $('#dissmiss_merchant_win').dialog({
                    title: '审核未通过原因',
                    iconCls: 'icon-edit',
                    width: '400px',
                    modal: true,
                    buttons: [{
                        text: '提交',
                        iconCls: 'icon-ok',
                        handler: function () {
                            $.ajax({
                                url: '/admin/api/merchant/updateExaminStateDissmiss',
                                type: "post",
                                dataType: 'text',
                                data: {
                                    merchantId: merchant.merchantId,
                                    examinMsg: $('#examinMsg').textbox('getText')
                                },
                                success: function (data) {
                                    data = $.parseJSON(data);
                                    $.messager.show({
                                        title: '提示',
                                        msg: data.msg
                                    });
                                    if (data.success) {
                                        $('#dissmiss_merchant_win').dialog('close');
                                        $('#browse_win').dialog('close');
                                        $('#examin_win').datagrid('load');
                                    }
                                }
                            });
                        }
                    }, {
                        text: '取消',
                        iconCls: 'icon-cancel',
                        handler:function(){
                            $('#dissmiss_merchant_win').dialog('close');
                            $('#browse_win').dialog('close');
                        }
                    }],
                    onBeforeOpen: function () {
                        $('#examinMsg').textbox({
                            width: 300,
                            height: 100,
                            multiline: true,
                            prompt: '请输入为什么审核未通过的原因',
                        });
                    }
                });
            }

        });
    </script>
</head>
<body>
<!--显示待审核的商家数据网格开始-->
<div id="examin_win"></div>
<!--显示待审核的商家数据网格结束-->

<!--显示待审核商家细信息开始-->
<iframe id="browse_win"></iframe>
<!--显示待审核商家细信息结束-->

<!--商家通过审核开始-->
<div id="select_role_win">
    <form id="select_role_form" action="${basePath}/admin/api/merchant/updateExaminStateOk" method="post">
        <input type="hidden" id="merchantId" name="merchantId">
        <table align="center">
            <tr>
                <td>
                    <label for="add_adminPermit">是否启用：</label>
                </td>
                <td>
                    <input id="add_adminPermit" name="permit" type="checkbox" value="true" checked="checked">
                </td>
            </tr>
            <tr>
                <td>
                    <label>角色(可多选)：</label>
                </td>
                <td>
                    <c:forEach items="${requestScope.roleList}" var="role">
                        <input type="checkbox" name="roleIds" value="${role.roleId}">${role.roleName}
                    </c:forEach>
                </td>
            </tr>
            <tr>
                <td>
                    <label for="add_adminIsAdmin">是否是管理员用户：</label>
                </td>
                <td>
                    <input id="add_adminIsAdmin" name="superAdmin" type="checkbox" value="true">
                </td>
            </tr>
        </table>
    </form>
</div>
<!--商家通过审核结束-->

<!--商家未通过审核开始-->

<!--商家未通过审核开始-->
<div id="dissmiss_merchant_win">
    <div style="margin:20px auto 20px auto ;width: 80%">
        <div style="margin-bottom: 10px">审核未通过的原因:</div>
        <input id="examinMsg"></div>
</div>
<!--商家未通过审核结束-->
</body>
</html>
