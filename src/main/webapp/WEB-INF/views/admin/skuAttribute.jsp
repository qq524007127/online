<%--
  Created by IntelliJ IDEA.
  User: YangDaiChun
  Date: 2015/12/2
  Time: 16:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>SKU属性管理</title>
    <c:set var="basePath" value="${pageContext.request.contextPath}"/>
    <script type="text/javascript" src="${basePath}/static/core/app.js"></script>
    <script type="text/javascript">
        app.setBasePath('${basePath}');
        app.setup();
    </script>
</head>
<body>
<!--sku属性数据网格开始-->
<table id="skuAttribute_grid"></table>
<!--sku属性数据网格结束-->

<!--添加sku属性开始-->
<div id="add_win">
    <form id="add_form" action="" method="post">
        <table align="center">
            <tr>
                <td>
                    sku属性名:
                </td>
                <td>
                    <input class="easyui-textbox" name="skuAttName" data-options="required:true">
                </td>
            </tr>
            <tr>
                <td>
                    是否是根级sku属性:
                </td>
                <td>
                    <input type="radio" name="isRoot" value="true" onclick="is_disp(true)">是
                    <input type="radio" name="isRoot" value="false" onclick="is_disp(false)" checked>否
                </td>
            </tr>
            <tr class="disp_parent">
                <td>
                    >请选择父级sku属性:
                </td>
                <td>
                    <input class="parentSkuAtt" name="parentySkuAttribute.skuAttId">
                </td>
            </tr>
            <tr class="disp_goodsAttSet_info">
                <td>该sku属性属于哪个商品分类:</td>
                <td style="font-style: italic;font-size: 12px;text-decoration: underline;color: red">与父级sku属性所属商品分类一致</td>
            </tr>
            <tr class="disp_goodsAttSet">
                <td>
                    <div>该sku属性属于哪个商品分类:</div>
                    <c:forEach items="${requestScope.goodsAttSets}" var="goodsAttSet">
                        <input type="checkbox" name="goodsAttSetIds" value="${goodsAttSet.goodsAttSetId}">${goodsAttSet.goodsAttSetName}
                    </c:forEach>
                </td>
            </tr>
        </table>
    </form>
</div>
<!--添加sku属性结束-->

<!--修改sku属性开始-->
<div id="edit_win">
    <form id="edit_form" action="" method="post">
        <table align="center">
            <input type="hidden" name="skuAttId">
            <tr>
                <td>
                    sku属性名:
                </td>
                <td>
                    <input class="easyui-textbox" name="skuAttName" data-options="required:true">
                </td>
            </tr>
            <tr>
                <td>
                    是否是根级sku属性:
                </td>
                <td>
                    <input type="radio" name="isRoot" value="true" onclick="is_disp(true)" id="isRoot_true">是
                    <input type="radio" name="isRoot" value="false" onclick="is_disp(false)" id="isRoot_false">否
                </td>
            </tr>
            <tr class="disp_parent">
                <td>
                    >请选择父级sku属性:
                </td>
                <td>
                    <input class="parentSkuAtt" name="parentySkuAttribute.skuAttId">
                </td>
            </tr>
            <tr class="disp_goodsAttSet_info">
                <td>该sku属性属于哪个商品分类:</td>
                <td style="font-style: italic;font-size: 12px;text-decoration: underline;color: red">与父级sku属性所属商品分类一致</td>
            </tr>
            <tr class="disp_goodsAttSet">
                <td>
                    <div>该sku属性属于哪个商品分类:</div>
                    <c:forEach items="${requestScope.goodsAttSets}" var="goodsAttSet">
                        <input type="checkbox" name="goodsAttSetIds" value="${goodsAttSet.goodsAttSetId}">${goodsAttSet.goodsAttSetName}
                    </c:forEach>
                </td>
            </tr>
        </table>
    </form>
</div>
<!--修改sku属性结束-->

</body>

<script type="text/javascript">
    $(function () {
        /*========初始化sku属性数据网格========*/
        $('#skuAttribute_grid').datagrid({
            url: '/admin/api/skuAttribute/list',
            fitColumns: true,
            fit: true,
            pagination: true,
            pageSize:20,
            columns: [[
                {field: 'skuAttId', checkbox: true, width: 100, align: 'center'},
                {field: 'skuAttName', title: 'sku属性名', width: 100, align: 'center'},
                {field: 'skuAttCode', title: 'sku属性码', width: 100, align: 'center'},
                {field: 'skuAttDesc', title: 'sku属性描述', width: 100, align: 'center'},
                {
                    field: 'parentySkuAttribute', title: '上一级sku属性', width: 100, align: 'center',sortable:true,
                    formatter: function (value) {
                        if (!value) {
                            return '<span style="color: red">无</span>';
                        } else {
                            return value.skuAttName;
                        }
                    }
                },
                {
                    field: 'goodsAttributeSets', title: '此sku属性属于哪一个商品类', width: 100, align: 'center',
                    formatter:function(goodsAttributeSets){
                        if(goodsAttributeSets && goodsAttributeSets.length > 0){
                            var tmp = '';
                            $.each(goodsAttributeSets, function(index,goodsAttributeSet){
                                tmp += goodsAttributeSet.goodsAttSetName + '、';
                            });
                            return tmp;
                        }
                        return '<span style="color: red">空</span>';
                    }
                }
            ]],
            toolbar: [{
                iconCls: 'icon-add',
                text: '添加',
                handler: function () {
                    add();
                }
            }, '-', {
                iconCls: 'icon-edit',
                text: '修改',
                handler: function () {
                    edit();
                }
            }],
        });
    });

    /*========添加sku属性========*/
    function add() {
        $("#add_win").dialog({
            title: '添加',
            width: 500,
            buttons: [{
                text: '添加',
                iconCls:'icon-add',
                handler: function () {
                    $('#add_form').form('submit', {
                        url: '/admin/api/skuAttribute/add',
                        success: function (info) {
                            info = $.parseJSON(info);
                            $.messager.show({
                                title: '提示',
                                msg: info.msg
                            });
                            if (info.success) {
                                $('#add_win').dialog('close');
                                $('#skuAttribute_grid').datagrid('load');
                            }
                        }
                    })
                }
            }, {
                text: '关闭',
                iconCls:'icon-cancel',
                handler: function () {
                    $('#add_win').dialog('close');
                }
            }],
            onBeforeOpen: function () {
                $('.parentSkuAtt').combobox({
                    url: '/admin/api/skuAttribute/rootList',
                    valueField: 'skuAttId',
                    textField: 'skuAttName',
                    panelHeight:100,
                });
                $('#add_form').form('reset');
                var flag = $('#add_form input[type="radio"]:checked').val();
                if (flag == 'false') {
                    is_disp(false);
                } else {
                    is_disp(true);
                }
            }
        });
    }

    /*========修改sku属性========*/
    function edit(){
        var rows = $('#skuAttribute_grid').datagrid('getSelections');
        if(rows.length ==1){
            $('#edit_win').dialog({
                title:'修改',
                width:500,
                iconCls:'icon-edit',
                buttons:[{
                    text:'修改',
                    iconCls:'icon-edit',
                    handler:function(){
                        $('#edit_form').form('submit',{
                            url:'/admin/api/skuAttribute/edit',
                            success:function(info){
                                info = $.parseJSON(info);
                                $.messager.show({
                                    title:'提示',
                                    msg:info.msg
                                });
                                if(info.success){
                                    $('#edit_win').dialog('close');
                                    $('#skuAttribute_grid').datagrid('load');
                                }
                            }
                        })
                    }
                },{
                    text:'取消',
                    iconCls:'icon-cancel',
                    handler:function(){
                        $('#edit_win').dialog('close');
                    }
                }],
                onBeforeOpen:function(){
                    $('.parentSkuAtt').combobox({
                        url: '/admin/api/skuAttribute/rootList',
                        valueField: 'skuAttId',
                        textField: 'skuAttName',
                        panelHeight:100,
                    });
                    $('#edit_form').form('reset');
                    $('#edit_form').form('load',rows[0]);
                    if(typeof (rows[0].parentySkuAttribute) == 'undefined'){
                        $('#isRoot_true').attr('checked',true);
                        $('#isRoot_false').attr('checked',false);

                    }else{
                        $('#isRoot_true').attr('checked',false);
                        $('#isRoot_false').attr('checked',true);
                        $('.parentSkuAtt').combobox('setValue', rows[0].parentySkuAttribute.skuAttId);
                        $('.parentSkuAtt').combobox('setText', rows[0].parentySkuAttribute.skuAttName);
                    }
                    var goodsAttSets = $('#edit_form input[type=checkbox]');
                    $.each(goodsAttSets,function(index,item){
                        $(item).prop('checked',false);
                        console.log(rows[0]);
                        $.each(rows[0].goodsAttributeSets,function(i,goodsAttributeSet){
                            if($(item).val() == goodsAttributeSet.goodsAttSetId){
                                $(item).prop('checked',true);
                            }
                        });
                    });
                    var flag = $('#edit_form input[type="radio"]:checked').val();
                    if (flag == 'false') {
                        is_disp(false);
                    } else {
                        is_disp(true);
                    }
                }
            });
        }else if(rows.length == 0){
            $.messager.alert('提示','请选中一行进行修改');
        }else{
            $.messager.alert('提示','你选中了多行，请选中一行进行修改');
        }

    }

    /*========是否显示父级sku属性下拉框========*/
    function is_disp(flag) {
        if (flag) {
            $('.disp_parent').hide();
            $('.disp_goodsAttSet_info').hide();
            $('.disp_goodsAttSet').show();
            $('.parentSkuAtt').combobox('clear');
        } else {
            $('.disp_parent').show();
            $('.disp_goodsAttSet_info').show();
            $('.disp_goodsAttSet').hide();
            $('.parentSkuAtt').combobox
        }
    }

</script>
</html>
