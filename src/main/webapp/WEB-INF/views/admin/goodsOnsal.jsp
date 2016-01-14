<%--
  Created by IntelliJ IDEA.
  User: YangDaiChun
  Date: 2015/12/15
  Time: 16:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>未上架的商品</title>
    <c:set var="basePath" value="${pageContext.request.contextPath}"/>
    <script type="text/javascript" src="${basePath}/static/core/app.js"></script>
    <script type="text/javascript">
        app.setBasePath('${basePath}');
        app.setup();
    </script>
    <script type="text/javascript">
        $(document).ready(function () {
            /*========初始化数据网格========*/
            $('#onsal_win').datagrid({
                url: '${basePath}/admin/api/onsalGoods/list',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                columns: [[
                    {field: 'goodsId', checkbox: true, width: 100},
                    {field: 'goodsName', title: '商品名', width: 100, align: 'center'},
                    {field: 'goodsDesc', title: '商品描述', width: 100, align: 'center'},
                    {
                        field: 'categ', title: '商品所属分类', width: 100, align: 'center',
                        formatter: function (data) {
                            if (data) {
                                return data.categName;
                            }
                        }
                    },
                    {
                        field: 'coverImg', title: '商品封面图', width: 100, align: 'center',
                        formatter: function (data) {
                            if (data) {
                                return  "&nbsp;&nbsp;<button onclick=\'preview(this.value)\' value=" + data.domain + data.key + ">点击预览</button>";
                            }
                            return "<span style='color: red' '>无封面图</span>"
                        }
                    },
                    {
                        field: 'isPick', title: '是否可以自取', width: 100, align: 'center',
                        formatter: function (value) {
                            if (value) {
                                return "<span style='color: green'>可以</span>"
                            }
                            return "<span style='color: red'>不可以</span>"
                        }
                    }, {
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
                    {field: 'createDate', title: '添加时间', width: 100, align: 'center', sortable: true,}
                ]],
                toolbar: '#toobar_searchbar',
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
                    $('#notOnsal_win').datagrid('load', param);
                }
            });

            /*========下架========*/
            $('#onsal_btn').click(function () {
                var rows = $('#onsal_win').datagrid('getSelections');
                if (rows.length == 1) {
                    $('#onsal_goods_dialog').dialog({
                        title: '警告',
                        iconCls: 'icon-notonsal',
                        width: 300,
                        height: 150,
                        modal: true,
                        buttons: [{
                            text: '确认',
                            iconCls: 'icon-ok',
                            handler: function () {
                                $.ajax({
                                    url: '${basePath}/admin/api/Goods/updateOnsalState',
                                    type: 'post',
                                    data: {
                                        goodsId: rows[0].goodsId,
                                        onsale: false
                                    },
                                    success: function (info) {
                                        $.messager.show({
                                            title: '提示',
                                            msg: info.msg
                                        });
                                        if (info.success) {
                                            $('#onsal_goods_dialog').dialog('close');
                                            $('#onsal_win').datagrid('load');
                                        }
                                    }
                                });
                            }
                        }, {
                            text: '取消',
                            iconCls: 'icon-cancel',
                            handler: function () {
                                $('#onsal_goods_dialog').dialog('close');
                            }
                        }]
                    });
                } else {
                    $.messager.alert('提示', '请选中一条数据!');
                }
            });

        });

        function preview(key) {
            $('#pic_preview').attr('src', key);
            $('#disp_pic').dialog({
                title: '封面图',
                iconCls: 'icon-edit',
                width: '500',
                height: 400,
            });
        }
    </script>
</head>
<body>
<!--toolbar&searchbar开始-->
<div id="toobar_searchbar">
    <a class="easyui-linkbutton" data-options="iconCls:'icon-notonsal',plain:true" id="onsal_btn">下架</a>
    <input id="grid_searchbox">
</div>
<div id="searchbox_menu">
    <div data-options="name:'goodsName'">商品名</div>
</div>
<!--toolbar&searchbar结束-->

<!--已上架商品列表开始-->
<div id="onsal_win"></div>
<!--已上架商品列表开始-->

<!--下架商品开始-->
<div id="onsal_goods_dialog">确定要上架选中的商品吗?</div>
<!--下架商品结束-->

<!--显示封面图开始-->
<div id="disp_pic">
    <img id="pic_preview" style="width: 100%;height: 100%">
</div>
<!--显示封面图结束-->
</body>
</html>
