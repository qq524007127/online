<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>发起活动</title>
    <c:set var="basePath" value="${pageContext.request.contextPath}"/>
    <script type="text/javascript" src="${basePath}/static/core/app.js"></script>
    <script type="text/javascript">
        app.setBasePath('${basePath}');
        app.setup();
    </script>
    <script type="text/javascript" src="${basePath}/static/core/plupload/plupload.full.min.js"></script>
    <script type="text/javascript" src="${basePath}/static/core/qiniu.min.js"></script>
    <script type="text/javascript" src="${basePath}/static/core/uploader.js"></script>
    <script type="text/javascript">
        $(function () {
            /*========初始化数据网格=======*/
            $('#activity_grid').datagrid({
                url: '${basePath}/admin/api/applyActivity/list',
                fit: true,
                fitColumns: true,
                pagination: true,
                singleSelect: true,
                rownumbers: true,
                title: '我发起的活动',
                columns: [[
                    {field: '', checkbox: true, width: 100},
                    {field: 'activitName', title: '活动名称', width: 100, align: 'center'},
                    {
                        field: 'category', title: '活动类别', width: 100, align: 'center',
                        formatter: function (category) {
                            return category.categName;
                        }
                    },
                    {
                        field: 'attributeSet', title: '商品类别', width: 100, align: 'center',
                        formatter: function (goodsCateg) {
                            return goodsCateg.goodsAttSetName;
                        }
                    },
                    {field: 'displayState', title: '当前状态', width: 50, align: 'center'},
                    {field: 'beginDate', title: '开始时间', width: 100, align: 'center'},
                    {field: 'endDate', title: '结束时间', width: 100, align: 'center'},
                    {field: 'createDate', title: '活动发起时间', width: 100, align: 'center'},
                    {field: 'contacterName', title: '活动联系人', width: 50, align: 'center'},
                    {field: 'contactTell', title: '联系电话', width: 100, align: 'center'},
                    {
                        field: 'activitId', title: '操作', width: 100, align: 'center',
                        formatter: function (value) {
                            var buttons = "<button value=" + value + " onclick='preview_details(this.value)'>详情</button> | ";
                            buttons += '<button onclick="javascript:alert(111)" data-activityId="' + value + '">商家联盟</button>';
                            return buttons;
                        }
                    },
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
                    $('#activity_grid').datagrid('load', param);
                }
            });

            /*========上传封面图========*/
            initUploader({
                pickbutton: 'pick-pics',
                uploadbutton: 'upload-btn',
                onFileAdd: function (file) {
                    $('#file-name').textbox('setText', file.name);
                },
                onBeforeUpload: function () {
                    $('#prgress')[0].innerHTML = ' 正在上传...';
                },
                onUploaded: function (file, up, info) {
                    console.log(info);
                    $('#coverImg').val(info.imgInfoId);
                    $('#preview_img').attr('src', info.url);
                    $('#prgress')[0].innerHTML = ' 上传完成';
                },
                onError: function () {
                    $.messager.alert('提示', '图片上传失败，请重试', 'error');
                },
                onComplete: function () {

                }
            });

            /*========发起新的活动========*/
            $('#add_btn').click(function () {
                $('#data-dialog').dialog({
                    title: '发起活动',
                    iconCls: 'icon-add2',
                    width: 500,
                    height: 600,
                    buttons: [{
                        text: '保存',
                        iconCls: 'icon-ok',
                        handler: function () {
                            $('#data-form').form('submit', {
                                url: '${root}/admin/api/activity/apply',
                                success: function (data) {
                                    data = $.parseJSON(data);
                                    $.messager.show({
                                        title: '提示',
                                        msg: data.msg
                                    });
                                    if (data.success) {
                                        $('#data-dialog').dialog('close');
                                        $('#activity_grid').datagrid('load');
                                    }
                                }
                            })
                        }
                    }, {
                        text: '取消',
                        iconCls: 'icon-cancel',
                        handler: function () {
                            $('#data-dialog').dialog('close');
                        }
                    }],
                    onBeforeOpen: function () {
                        $('#data-form').form('reset');
                    }
                });
            });

            /*========活动信息修改========*/
            $('#edit_btn').click(function () {
                var rows = $('#activity_grid').datagrid('getChecked');
                if (rows.length < 1) {
                    $.messager.alert('提示', '请选择需要修改的数据', 'error');
                    return;
                }
                var data = rows[0];
                $('#data-dialog').dialog({
                    title: '修改活动信息',
                    iconCls: 'icon-edit2',
                    width: 490,
                    buttons: [{
                        text: '保存',
                        iconCls: 'icon-ok',
                        handler: function () {
                            $('#data-form').form('submit', {
                                url: '${root}/admin/api/activity/editApply',
                                success: function (data) {
                                    data = $.parseJSON(data);
                                    $.messager.show({
                                        title: '提示',
                                        msg: data.msg
                                    });
                                    if (data.success) {
                                        $('#data-dialog').dialog('close');
                                        $('#activity_grid').datagrid('load');
                                    }
                                }
                            })
                        }
                    }, {
                        text: '取消',
                        iconCls: 'icon-cancel',
                        handler: function () {
                            $('#data-dialog').dialog('close');
                        }
                    }],
                    onBeforeOpen: function () {
                        console.log(data);
                        $('#data-form').form('reset');
                        $('#data-form').form('load', rows[0]);
                        $('#activitCategory').combobox('setValue', data.category.categId);
                        $('#goodsCategory').combobox('setValue', data.attributeSet.goodsAttSetId);
                        $('#file-name').textbox('setText', data.coverImg.key);
                        $('#preview_img').attr('src', data.coverImg.url);
                        $('#coverImg').attr('value', data.coverImg.imgInfoId);
                    }
                });
            });

            /*========商家联盟========*/
            $('#view_alliance').click(function () {
                var rows = $('#activity_grid').datagrid('getChecked');
                if (!rows || rows.length < 1) {
                    $.messager.alert('提示', '请选择需要一条数据', 'info');
                    return;
                }
                var act = rows[0];
                var content = '<iframe scrolling="auto" frameborder="0"  src="${root}/admin/activity/' + act.activitId + '/merchAlliance" style="width:100%;height:100%;"></iframe>';
                $('#merch-alliance-win').window({
                    content: content
                });

                $('#merch-alliance-win').window('open');
            });


        });

        /*========预览详情========*/
        function preview_details(id) {

        }


    </script>
    <style>
        .activity_title {
            font-size: 14px;
            text-align: center;
        }

        .activity_title div {
            border-bottom: solid;
            border-color: green;
            border-width: 1px;
            color: #ff4500
        }

        .activity_title p {
            font-weight: bold;
        }
    </style>
</head>

<!--toolbar&searchbar开始-->
<div id="toobar_searchbar">
    <a class="easyui-linkbutton" data-options="iconCls:'icon-add2',plain:true" id="add_btn">发起活动</a>
    <a class="easyui-linkbutton" data-options="iconCls:'icon-edit2',plain:true" id="edit_btn">活动信息修改</a>
    <a class="easyui-linkbutton" data-options="iconCls:'icon-submit',plain:true" id="notonsal_btn">提交申请</a>
    <a class="easyui-linkbutton" data-options="iconCls:'icon-alliance',plain:true" id="view_alliance">商家联盟</a>
    <span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
    <input id="grid_searchbox">
</div>
<div id="searchbox_menu">
    <div data-options="name:'activitName'">活动名称</div>
</div>
<!--toolbar&searchbar结束-->

<body>
<!--活动数据网格开始-->
<div id="activity_grid"></div>
<!--活动数据网格开始-->

<!--添加,修改开始-->
<div id="data-dialog">
    <form id="data-form" method="post">
        <input type="hidden" name="activitId" id="activitId">
        <input type="hidden" id="coverImg" name="coverImg.imgInfoId">
        <table style="width:90%;margin: 3% auto 3% auto;border-collapse: separate;border-spacing: 7px;font-size: 14px">
            <tr>
                <td colspan="2" class="activity_title">
                    <div>
                        <p>活动基本信息</p>
                    </div>
                </td>
            </tr>
            <tr>
                <td width="20%" style="font-size: 13px">活动名称：</td>
                <td width="80%">
                    <input name="activitName" class="easyui-textbox" data-options="required:true"
                           style="width:310px;" prompt="请输入活动名称"/>
                </td>
            </tr>
            <tr>
                <td style="font-size: 13px"><label>活动类别:</label></td>
                <td>
                    <input class="easyui-combobox" id="activitCategory" name="category.categId"
                           panelWidth="180" valueField="categId"
                           textField="categName" editable="fasle" required="true"
                           data-options="url:'${basePath}/admin/api/activityCategory/myCategList'"
                           style="width: 180px" prompt="请选择活动类别"/>
                </td>
            </tr>
            <tr>
                <td style="font-size: 13px"><label>商品类别：</label></td>
                <td>
                    <input class="easyui-combobox" id="goodsCategory" name="attributeSet.goodsAttSetId"
                           panelWidth="180" valueField="goodsAttSetId"
                           textField="goodsAttSetName" editable="fasle" required="true"
                           data-options="url:'${basePath}/admin/api/goodsAttSet/allAtts'"
                           style="width: 180px" prompt="请选择商品类别"/>
                </td>
            </tr>
            <tr>
                <td style="font-size: 13px">开始时间：</td>
                <td>
                    <input class="easyui-datetimebox" name="beginDate"
                           data-options="required:true,editable:false" style="width:180px" prompt="请选择活动开始时间"/>
                </td>
            </tr>
            <tr>
                <td style="font-size: 13px">结束时间：</td>
                <td>
                    <input class="easyui-datetimebox" name="endDate" style="width:180px"
                           data-options="required:true,editable:false" prompt="请选择活动结束时间"/>
                </td>
            </tr>
            <tr>
                <td style="font-size: 13px"><label>联系人：</label></td>
                <td>
                    <div id="contacter" required="true" class="easyui-textbox" name="contacterName"
                         style="width:310px;" prompt="请输入联系人姓名"/>
                </td>
            </tr>
            <tr>
                <td style="font-size: 13px">联系电话：</td>
                <td>
                    <input id="contactTell" required="true" class="easyui-textbox" name="contactTell"
                           style="width:310px;" prompt="请输入联系人电话"/>
                </td>
            </tr>
            <tr>
                <td style="font-size: 13px" valign="top">封面图：</td>
                <td>
                    <div>
                        <input class="easyui-textbox" id="file-name" style="width:185px" editable="false">
                        <button type="button" id="pick-pics" class="easyui-linkbutton" style="height: 25px">选择图片
                        </button>
                        <button type="button" id="upload-btn" class="easyui-linkbutton" style="height: 25px;">上传图片
                        </button>
                    </div>
                    <div>
                        <div style="border: dotted;border-width: 1px;border-color:green;width: 150px;height: 100px;margin-top:5px;float: left"
                             id="preview_img_wrap">
                            <img src="${basePath}/static/images/preview.jpg" style="width: 100%;height: 100%"
                                 id="preview_img">
                        </div>
                        <div id="prgress" style="float: left;margin-top: 45px"></div>
                    </div>
                </td>
            </tr>
            <tr>
                <td valign="top">活动详情：</td>
                <td colspan="2">
                        <textarea name="activitDetail" class="easyui-textbox"
                                  data-options="multiline:true,required:true"
                                  style="width:310px;height: 80px" prompt="请输入活动的详情"></textarea>
                </td>
            </tr>
        </table>
    </form>
</div>
<!--添加,修改结束-->

<div id="merch-alliance-win" class="easyui-window"
     data-options="closed:true,collapsible:false,minimizable:false,modal:true,maximized:true,maximizable:false,
         iconCls:'icon-tip',title:'商家联盟',draggable:false"></div>
</body>
</html>
