<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>商家管理</title>
    <c:set var="basePath" value="${pageContext.request.contextPath}"/>
    <script type="text/javascript" src="${basePath}/static/core/app.js"></script>
    <script type="text/javascript">
        app.setBasePath('${basePath}');
        app.setup();
    </script>
    <script type="text/javascript" src="${basePath}/static/core/utils.js"></script>
    <script type="text/javascript">
        $(function(){
            $('#able-merch').click(function(){
                updateMerchPermit(true);
            });
            $('#disable-merch').click(function(){
                updateMerchPermit(false);
            });
        });
        function updateMerchPermit(permit){
            var rows = $('#merchant_grid').datagrid('getChecked');
            if(rows.length < 1){
                $.messager.alert('提示','请选择需要操作的数据','info');
                return;
            }
            var data = rows[0];
            if(data.permit == permit){
                $.messager.alert('提示','请勿重复操作','info');
                return;
            }
            $.messager.confirm('提示','确定要进行此操作吗？',function(r){
                if(r){
                    var json = {};
                    json.merchantId = data.merchantId;
                    json.permit = permit;
                    request({
                        data:json,
                        url:'${basePath}/admin/api/merchant/updatePermit',
                        success:function(data){
                            if(data.success){
                                $.messager.show({
                                    title:'提示',
                                    msg:data.msg
                                });
                                $("#merchant_grid").datagrid('reload');
                            }else{
                                $.messager.confirm('提示','操作异常，稍后重试！')
                            }

                        },
                        error:function(){

                        }
                    });
                }
            })
        }
    </script>
</head>
<body>
<table id="merchant_grid"></table>
<div id="grid_toolbar" class="toolbar-container">
    <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" id="able-merch" >启用</a>
    <a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" id="disable-merch" >禁用</a>
    <span class="separator"></span>
    <input id="grid_searchbox">
</div>
<div id="searchbox_menu" style="width:120px">
    <div data-options="name:'merchName'">商家名称</div>
    <div data-options="name:'merchTell'">商家电话</div>
    <div data-options="name:'district.name'">商圈</div>
</div>

<!--商圈数据网格开始-->
<div id="district_grid_dialog">
    <table id="district_grid"></table>
</div>
<div id="#district_grid_toolbar">
    <input id="district_searchbox">
</div>
<div id="district_searchbox_menu">
    <div data-options="name:'name'">All News</div>
</div>
<!--商圈数据网格结束-->

<!--商户图开始-->

<!--商户图结束-->
<div id="dd">Dialog Content.</div>
<iframe id="previewImg"></iframe>
<script type="text/javascript">
    (function ($) {
        /*============初始化商家数据网格==============*/
        $('#merchant_grid').datagrid({
            url: '/admin/api/merchant/list',
            columns: [[
                {field: '', checkbox: 'true'},
                {field: 'merchName', title: '商家名称', width: 100, align: 'center'},
                {field: 'merchTell', title: '联系电话', width: 100, align: 'center'},
                {field: 'alipayCode', title: '支付宝账号', width: 100, align: 'center'},
                {field: 'wxpayCode', title: '微信支付账号', width: 100, align: 'center'},
                {field: 'merchOrder', title: '排列顺序', width: 100, sortable: true, align: 'center'},
                {field:'examinState',title:'商家状态',width:100,align:'center',formatter:function(value){
                      switch (value){
                          case 1:
                            return '未提交审核';
                          case 2:
                              return '待审核';
                          case 3:
                              return '已通过审核';
                          case 4:
                              return '未通过审核';
                      }

                }},
                {
                    field: 'district', title: '所属商圈', width: 100, align: 'center', formatter: function (value) {
                    if (value) {
                        return value.districtName;
                    }
                }
                },
                {field: 'createDate', title: '入驻时间', width: 150, sortable: true, align: 'center'},
                {
                    field: 'permit',
                    title: '是否有效',
                    width: 100,
                    align: 'center',
                    sortable: true,
                    formatter: function (value) {
                        if (value) {
                            return "已启用";
                        }
                        else {
                            return "未启用";
                        }
                    }
                },
                {field: 'address', title: '详细地址', width: 260, align: 'center'},
                {
                    field: 'merchantId', title: '查看详情', width: 260, align: 'center',
                    formatter: function (value) {
                        return "<button value=" + value + " onclick='previewImg(this.value)'>查看详情</button>";
                    }
                }
            ]],
            toolbar: '#grid_toolbar',
            singleSelect: true,
            pageSize: 20,
            pagination: true,
            fit: true,
            fitColumns: true,
            border: false,
            onLoadError: function () {
                $.messager.alert('出错了', '数据加载失败，请重试', 'error');
            },
            onCheck: function (index, row) {
                rows = row;
            }
        });
        /*============初始化商家搜索框==============*/
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
                $('#merchant_grid').datagrid('load', param);
            }
        });
        /*============初始化商家数据网格toolbar功能按钮==============*/
        /*============初始化商圈数据网格===========*/
        $('#district_grid').datagrid({
            url: '/admin/api/district/list',
            columns: [[
                {field: 'districtId', title: 'ID', hidden: true, width: 100, align: 'center'},
                {field: 'districtName', title: '商圈名', width: 100, align: 'center'},
                {
                    field: 'districtArea', title: '商圈所属区域', width: 100,
                    formatter: function (value) {
                        if (value) {
                            return value.areaName;
                        }
                    }
                },
                {field: 'districtDesc', title: '描述', width: 100, align: 'center'}
            ]],
            toolbar: '#district_grid_toolbar',
            singleSelect: true,
            pageSize: 10,
            border: false,
            pagination: true,
            fit: true,
            fitColumns: true


        });

        $('#district_searchbox').searchbox({
            width: 300,
            menu: '#district_searchbox_menu',
            prompt: '输入关键字搜索',
            searcher: function (value, name) {
                var param = {};
                if (value) {
                    param.searchName = name;
                    param.searchValue = value;
                }
            }
        });
    })(jQuery);

    function previewImg(value) {
        $('#previewImg').dialog({
            width: 800,
            height: 400,
            title: '查看详情',
            maximizable: true,
            shadow: true,
            inline: true,
            modal: true,
            onBeforeOpen: function () {
                $('#previewImg').attr('src', "/admin/merchant/info/" + value);
            }
        });
    }
    function permitModify(datas) {
        $.ajax({
            url: '${basePath}/admin/api/merchant/edit',
            type: 'POST',
            dataType: 'json',
            data: datas,
            success: function (data) {
                if (data.success) {
                    $("#merchant_grid").datagrid('reload');
                    $.messager.show({
                        title: '提示',
                        msg: '<span style=\"color:red;\">' + data.msg + '</span>'
                    });
                } else {
                    $.messager.alert("提示", "操作失败!");
                }
            }
        });
    }
    var rows;


</script>
</body>
</html>
