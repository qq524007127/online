<%@ page import="cn.com.zhihetech.online.commons.Constant" %>
<%--
  Created by IntelliJ IDEA.
  User: YangDaiChun
  Date: 2015/12/14
  Time: 9:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>商品管理</title>
    <c:set var="basePath" value="${pageContext.request.contextPath}"/>
    <script type="text/javascript" src="${basePath}/static/core/app.js"></script>
    <script type="text/javascript">
        app.setBasePath('${basePath}');
        app.setup();
    </script>
    <script type="text/javascript">
        /*========初始化商品数据网格========*/
        $(document).ready(function () {
            $('#goods_win').datagrid({
                url: '${basePath}/admin/api/goods/examinDismissList',
                fit: true,
                fitColumns: true,
                pagination: true,
                singleSelect: true,
                rownumbers:true,
                columns: [[
                    {field: 'goodsId', checkbox: true, width: 100},
                    {field: 'goodsName', title: '商品名', width: 100, align: 'center'},
                    {field: 'goodsDesc', title: '商品描述', width: 100, align: 'center'},
                    {
                        field: 'categ', title: '商品所属分类', width: 100, align: 'center',
                        formatter:function(data){
                            if(data){
                                return data.categName;
                            }
                        }
                    },
                    {
                        field: 'coverImg', title: '商品封面图', width: 100, align: 'center',
                        formatter:function(data){
                            if(data){
                                return "&nbsp;&nbsp;<button onclick=\'preview(this.value)\' value="+data.domain+data.key+">点击预览</button>";
                            }
                            return "<span style='color: red' '>无封面图</span>"
                        }
                    },
                    {field: 'examinState', title: '是否通过审核', width: 100, align: 'center',
                        formatter:function(data){
                            if(data == <%=Constant.EXAMINE_STATE_SUBMITED%>){
                                return"<span style='color: blue'>待审核中</span>";
                            }else if(data == <%=Constant.EXAMINE_STATE_EXAMINED_OK%>){
                                return"<span style='color: green'>审核通过</span>";
                            }
                            return "<span style='color: red'>审核未通过</span>";
                        }},
                    {field: 'examinMsg', title: '审核结果', width: 100, align: 'center'},
                    {
                        field: 'isActivityGoods', title: '是否参加活动', width: 100, align: 'center',
                        formatter:function(data){
                            if(data){
                                return"<span style='color: green'>是</span>"
                            }
                            return "<span style='color: red'>否</span>"
                        }
                    },
                    {
                        field: 'onsale', title: '在售状态', width: 100, align: 'center',sortable:true,
                        formatter: function (value) {
                            if (value) {
                                return '<span style="color: green">上架</span>';
                            }
                            return "<span style='color: red'>下架</span>";
                        }
                    },
                    {
                        field: 'isPick', title: '是否可以自取', width: 100, align: 'center',sortable:true,
                        formatter: function (value) {
                            if (value) {
                                return "<span style='color: green'>可以</span>"
                            }
                            return "<span style='color: red'>不可以</span>"
                        }
                    },
                    {
                        field: 'carriageMethod', title: '快递方式', width: 100, align: 'center',
                        formatter: function (value) {
                            if (value) {
                                return "<span style='color: green;'>" + value + "</span>";
                            }
                            return "<span style='color: red'>无</span>";
                        }
                    },
                    {
                        field: 'carriage', title: '运费', width: 100, align: 'center',
                        formatter: function (value) {
                            return value + " 元";
                        }
                    },
                    {
                        field: 'price', title: '价格', width: 100, align: 'center',sortable:true,
                        formatter: function (value) {
                            return value + " 元";
                        }
                    },
                    {
                        field: 'stock', title: '库存量', width: 100, align: 'center',sortable:true,
                        formatter: function (value) {
                            return value + " 件";
                        }
                    },
                    {field: 'createDate', title: '添加时间', width: 100, align: 'center',sortable:true,}
                ]],
                toolbar: '#toobar_searchbar'
            });
            /*========searchbar========*/
            $('#grid_searchbox').searchbox({
                width: 300,
                menu: '#searchbox_menu',
                prompt: '输入关键字搜索',
                searcher: function (value, name) {
                    var param = {};
                    if (value) {
                        param.searchName = name;
                        param.searchValue = value;
                    }
                    $('#goods_win').datagrid('load', param);
                }
            });

            /*=========修改========*/
            $('#edit_btn').click(function () {
                var rows = $('#goods_win').datagrid('getSelections');
                if (rows.length == 1) {
                    $('#edit_goods').attr('src',"/admin/api/goods/edit/"+rows[0].goodsId);
                    $('#edit_goods').dialog({
                        title: '修改',
                        iconCls: 'icon-edit',
                        width: 700,
                        height: 500,
                        modal:true
                    });
                } else {
                    $.messager.alert('提示', '请选中一条数据!');
                }
            });

            /*=========删除========*/
            $('#delete_btn').click(function () {
                var rows = $('#goods_win').datagrid('getSelections');
                if (rows.length == 1) {
                    $('#delete_goods').dialog({
                        title: '警告',
                        iconCls: 'icon-cancel',
                        width: 300,
                        height: 150,
                        buttons: [{
                            text: '确认',
                            iconCls: 'icon-ok',
                            handler: function () {
                                $.ajax({
                                    url: '${basePath}/admin/api/goods/updateDeleteState',
                                    type: 'post',
                                    data: {
                                        goodsId: rows[0].goodsId
                                    },
                                    success: function (info) {
                                        $.messager.show({
                                            title: '提示',
                                            msg: info.msg
                                        });
                                        if (info.success) {
                                            $('#delete_goods').dialog('close');
                                            $('#goods_win').datagrid('load');
                                        }
                                    }
                                });
                            }
                        }, {
                            text: '取消',
                            iconCls: 'icon-cancel',
                            handler: function () {
                                $('#delete_goods').dialog('close');
                            }
                        }]
                    });
                } else {
                    $.messager.alert('提示', '请选中一条数据!');
                }

            });
        });

        function preview(key){
            $('#pic_preview').attr('src',key);
            $('#disp_pic').dialog({
                title:'封面图',
                iconCls:'icon-edit',
                width:'500',
                height:400,
            });
        }

    </script>
</head>
<body>
<!--toolbar&searchbar开始-->
<div id="toobar_searchbar">
    <a class="easyui-linkbutton" data-options="iconCls:'icon-edit2',plain:true" id="edit_btn">修改</a>
    <a class="easyui-linkbutton" data-options="iconCls:'icon-cancel2',plain:true" id="delete_btn">删除</a>
    <span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
    <input id="grid_searchbox">
</div>
<div id="searchbox_menu">
    <div data-options="name:'goodsName'">商品名</div>
</div>
<!--toolbar&searchbar结束-->

<!--显示商品数据网格开始-->
<div id="goods_win"></div>
<!--显示商品数据网格结束-->

<!--修改商品开始-->
<iframe id="edit_goods"></iframe>
<!--修改商品结束-->

<!--删除商品开始-->
<div id="delete_goods">
    <div style="width: 50%;margin-left: auto;margin-right: auto;margin-top: 10%;color: red;">
        确定要删除选中的商品吗?
    </div>
</div>
<!--删除商品结束-->

<!--显示封面图开始-->
<div id="disp_pic">
    <img id="pic_preview" style="width: 100%;height: 100%">
</div>
<!--显示封面图结束-->
</body>
</html>
