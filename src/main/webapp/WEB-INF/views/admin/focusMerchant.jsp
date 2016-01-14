<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>商家关注用户查询</title>
    <c:set var="basePath" value="${pageContext.request.contextPath}"></c:set>
    <script type="text/javascript" src="${basePath}/static/core/app.js"></script>
    <script>
        app.setBasePath('${basePath}');
        app.setup();
    </script>
    <script type="text/javascript">
        function initDialog(merchantId){
            $("#userI").dialog({
                title:'用户详情',
                width:400,
                height:200,
                closed:false,
                modal:true,
                maximizable:true,
                minimizable:true,
                onBeforeOpen:function(){
                    $("#userI").attr('src',"/admin/focusMerchant/Info/"+merchantId);
                }
            });
        };
        var initFocusGrid = function () {
            $("#focus_grid").datagrid({
                url: '/admin/api/focusMerchant/list',
                columns: [[
                    {
                        field: 'user', title: '关注用户', width: 100,align:'center', formatter: function (value,row) {
                        var merchantId=row.merchant.merchantId;
                        return  "<a href='#' onclick=\"initDialog('"+merchantId+"')\">"+value.userName+"</a>";
                    }
                    },
                    {
                        field: 'merchant', title: '商户', width: 100,align:'center', formatter: function (value) {
                        return value.merchName;
                    }
                    },
                    {field: 'focusDate', title: '关注时间', align:'center', width: 100}
                ]],
                fitColumns: true,
                toolbar: '#tool-bar',
                pagination: true,
                singleSelect: true,
                sortName: 'focusDate',
                pageSize: 20,
                shadow:true,
                fit:true,
                onBeforeLoad:function(){
                    initSearchBox();
                }
            });
        };
        var initSearchBox = function () {
            $("#search-box").searchbox({
                width: 300,
                menu: '#searchbox_menu',
                prompt: '输入关键字检索',
                searcher: function (name, value) {
                    var params = {};
                    if (value) {
                        params.searchName = value;
                        params.searchValue = name;
                    }
                    $("#focus_grid").datagrid('load', params);
                }
            });
        };

        $(function () {
            initFocusGrid();
        });

    </script>
    <script>

    </script>
</head>
<body>
<div id="tool-bar" class="toolbar-container">
    <input id="search-box"/>
</div>
<div id="searchbox_menu" style="width:120px">
    <div data-options="name:'user.userName'">关注用户</div>
    <div data-options="name:'merchant.merchName'">商户</div>
</div>
<table id="focus_grid"></table>
<iframe id="userI" ></iframe>


</div>
</body>
</html>