<%@ page import="cn.com.zhihetech.online.commons.Constant" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="e" uri="org.topteam/easyui" %>
<html>
<head>
    <title>轮播图管理</title>
    <c:set var="basePath" value="${pageContext.request.contextPath}"/>
    <script type="text/javascript" src="${basePath}/static/core/app.js"></script>
    <script type="text/javascript">
        app.setBasePath('${basePath}');
        app.setup();
    </script>

    <script type="text/javascript" src="${basePath}/static/core/plupload/plupload.full.min.js"></script>
    <script type="text/javascript" src="${basePath}/static/core/qiniu.min.js"></script>
    <script type="text/javascript" src="${basePath}/static/core/uploader.js"></script>

    <script>
        $(function () {
            /*=========初始化数据网格========*/
            $("#banner_gird").datagrid({
                url: '/admin/api/banner/list',
                singleSelect: true,
                pageSize: 20,
                pagination: true,
                fit: true,
                fitColumns: true,
                border: false,
                rownumbers: true,
                columns: [[
                    {field: 'bannerId', checkbox: true, align: 'center'},
                    {
                        field: 'imgInfo', title: '轮播图', width: 100, align: 'center',
                        formatter: function (value) {
                            return "<button value=" + value.domain + value.key + " onclick='preview(this.value)'>点击预览</button>";
                        }
                    },
                    {
                        field: 'bannerType', title: '轮播图所在的位置', width: 100, align: 'center', sortable: true,
                        formatter: function (value) {
                            switch (value) {
                                case <%=Constant.BANNER_MAIN%>:
                                    return "<span style='color:green'>主页</span>";
                                    break;
                                case <%=Constant.BANNER_DAILY_NEW%>:
                                    return "<span style='color: #0000FF'>每日上新</span>";
                                    break;
                                case <%=Constant.BANNER_BUY_KUNMING%>:
                                    return "<span style='color: chocolate'>买昆明</span>";
                                    break;
                                case <%=Constant.BANNER_BUY_PREFECTURES%>:
                                    return "<span style='color: black'>购地州</span>";
                                    break;
                                case <%=Constant.BANNER_TYPE_CATEGOR%>:
                                    return "<span style='color: #843534'>分类</span>";
                                    break;
                                case <%=Constant.BANNER_BIG_BRAND%>:
                                    return "<span style='color: darkcyan'>大品牌</span>";
                                    break;
                                case <%=Constant.BANNER_TYPE_ACTIVITY_ZONE%>:
                                    return "<span style='color: darkmagenta'>活动专区</span>";
                                    break;
                            }
                        }
                    },
                    {
                        field: 'viewType', title: '跳转页面类型', width: 100, align: 'center',
                        formatter: function (value) {
                            if (value == <%=Constant.BANNER_VIEWTYPE_TARGET%>) {
                                return "导航";
                            } else if (value == <%=Constant.BANNER_VIEWTYPE_MERCHANT%>) {
                                return '商户页面';
                            } else if (value == <%=Constant.BANNER_VIEWTYPE_GOODS%>) {
                                return '商品页面';
                            } else if (value == <%=Constant.BANNER_VIEWTYPE_ACTIVITY%>) {
                                return '活动页面';
                            } else if (value == <%=Constant.BANNER_VIEWTYPE_PAGE%>) {
                                return '指定页面';
                            } else {
                                return "<span style='color: red'>无</span>"
                            }
                        }
                    },
                    {
                        field: 'viewTargert', title: '跳转到哪个导航', width: 100, align: 'center',
                        formatter: function (value) {
                            if (value == <%=Constant.BANNER_VIEWTARGET_DAILY_NEW%>) {
                                return '今日上新';
                            } else if (value == <%=Constant.BANNER_VIEWTARGET_BUG_KUMING%>) {
                                return '买昆明';
                            } else if (value == <%=Constant.BANNER_VIEWTARGET_BUG_PREFECTURES%>) {
                                return '购地州';
                            } else if (value == <%=Constant.BANNER_VIEWTARGET_TYPE_CATEGOR%>) {
                                return '分类';
                            } else if (value == <%=Constant.BANNER_VIEWTARGET_BIG_BRAND%>) {
                                return '大品牌';
                            } else if (value == <%=Constant.BANNER_VIEWTARGET_TYPE_ACTIVITY_ZONE%>) {
                                return '活动专区';
                            } else {
                                return "<span style='color: red'>无</span>"
                            }
                        }
                    },
                    {field: 'bannerOrder', title: '轮播图顺序', sortable: true, width: 100, align: 'center'},
                    {field: 'createDate', title: '创建时间', width: 100, align: 'center'}
                ]],
                toolbar: [{
                    text: '添加',
                    iconCls: 'icon-add',
                    handler: function () {
                        add();
                    }
                }, '-', {
                    text: '修改',
                    iconCls: 'icon-edit',
                    handler: function () {
                        edit();
                    }
                }, '-', {
                    text: '删除',
                    iconCls: 'icon-cancel',
                    handler: function () {
                        deletes();
                    }
                }],
            });

            /*=========上传轮播图图片========*/
            initUploader({
                pickbutton: 'select',
                uploadbutton: 'upload',
                onFileAdd: function (file) {
                    $('#pic_name').textbox('setText', file.name);
                },
                onError: function (file, err, errTip) {

                },
                onBeforeUpload: function (up, file) {
                    total = up.total.size;
                    loaded = up.total.loaded;
                    progress = parseInt(loaded / total * 100);
                    $('#progress')[0].innerHTML = ' 正在上传...';
                },
                onUploaded: function (up, file, info) {
                    console.log(info);
                    $('#pic_id').val(info.imgInfoId);
                    $('#preview_icon').attr('src', info.url);
                    $('#progress')[0].innerHTML = ' 上传完成';
                },
                onComplete: function () {

                }
            });

            /*========增加轮播图========*/
            function add() {
                $("#add_form").form('reset');
                $("#add-dialog").dialog({
                    title: '添加',
                    width: 450,
                    iconCls: 'icon-add',
                    modal: true,
                    buttons: [{
                        text: '保存',
                        iconCls: 'icon-save',
                        handler: function () {
                            $("#add_form").form('submit', {
                                url: '${basePath}/admin/api/banner/add',
                                success: function (data) {
                                    data = $.parseJSON(data);
                                    if (data.success) {
                                        $.messager.show({
                                            title: '提示',
                                            msg: '操作成功'
                                        });
                                        $('#add-dialog').dialog('close');
                                        $('#banner_gird').datagrid('load');
                                    } else {
                                        $.messager.alert('提示', data.msg + ",稍后重试!");
                                    }
                                }
                            });
                        }

                    }, {
                        text: '取消',
                        iconCls: 'icon-cancel',
                        handler: function () {
                            $("#add-dialog").dialog('close');
                        }
                    }],
                    onBeforeOpen: function () {
                        $("#add-form").form('reset');
                        $('#preview_icon').attr('src', '${basePath}/static/images/preview.jpg');
                        $('#progress')[0].innerHTML = '';
                        $('#pic_name').textbox('setText', '');
                        $("#view_type option[value=1]").remove();
                        $("#view_type").append("<option value='1' >导航</option>");
                        $("#view_type").combobox({});
                        $('#view_type').combobox('setValue',<%=Constant.BANNER_VIEWTYPE_TARGET%>).combobox('setText','导航');
                        $('#annotation_view_target').animate({"opacity": 1});
                        $('#view_target').removeAttr('disabled');
                    }
                });
            }

            /*========删除轮播图========*/
            function deletes() {
                var rows = $("#banner_gird").datagrid('getChecked');
                console.log(rows[0]);
                if (rows.length < 1) {
                    $.messager.alert("提示", '请先选择要删除的行?');
                } else {
                    $.messager.confirm('删除数据', '确定要删除改行?',
                            function (flag) {
                                if (flag) {
                                    $.ajax({
                                        url: '${basePath}/admin/api/banner/delete',
                                        type: 'post',
                                        dataType: 'text',
                                        data: {
                                            bannerId: rows[0].bannerId,
                                            imgInfoId: rows[0].imgInfo.imgInfoId
                                        },
                                        beforeSend: function () {
                                            $("#add-viewP").numberbox('fix');
                                        },
                                        success: function (info) {
                                            info = $.parseJSON(info);
                                            console.log(info);
                                            if (info.success) {
                                                $.messager.show({
                                                    title: '提示',
                                                    msg: info.msg
                                                });
                                                $('#banner_gird').datagrid('load');
                                            } else {
                                                $.messager.alert('提示', '删除失败');
                                            }
                                        }
                                    });
                                }
                            }
                    );
                }
            }

            /*========修改轮播图========*/
            function edit() {
                $("#add_form").form('reset');
                var datas = $("#banner_gird").datagrid('getChecked');
                if (datas.length < 1) {
                    $.messager.alert("提示", '请先选择要修改的行');
                } else {
                    $("#add-dialog").dialog({
                        title: '编辑',
                        width: 450,
                        iconCls: 'icon-edit',
                        modal: true,
                        buttons: [{
                            text: '保存',
                            iconCls: 'icon-save',
                            handler: function () {
                                $("#add-dialog").dialog('close');
                                $("#add_form").form('submit', {
                                    url: '${basePath}/admin/api/banner/update',
                                    onSubmit: function () {
                                        $("#add-viewP").numberbox('fix');
                                        var isValid = $(this).form('validate');
                                        if (!isValid) {
                                            return isValid;
                                        }
                                    },
                                    success: function (data) {
                                        data = $.parseJSON(data);
                                        if (data.success) {
                                            $("#banner_grid").datagrid('reload');
                                            $.messager.show({
                                                title: '提示',
                                                msg: '修改成功'
                                            });
                                            $('#add-dialog').dialog('close');
                                            $('#banner_gird').datagrid('load');
                                        } else {
                                            $.messager.alert('提示', data.msg + ",稍后重试!");
                                        }
                                    }
                                });
                            }
                        }, {
                            text: '取消',
                            iconCls: 'icon-cancel',
                            handler: function () {
                                $("#add-dialog").dialog('close');
                            }

                        }],
                        onBeforeOpen: function () {
                            console.log(datas[0]);
                            var info = {
                                bannerId: datas[0].bannerId,
                                bannerOrder: datas[0].bannerOrder,
                                'imgInfo.imgInfoId': datas[0].imgInfo.imgInfoId,
                                imgName: datas[0].imgInfo.key,
                                imgURL: datas[0].imgInfo.url,
                                bannerType:datas[0].bannerType,
                                viewType: datas[0].viewType,
                                viewTargert: datas[0].viewTargert,
                                createDate: datas[0].createDate,
                            };
                            $("#add_form").form('load', info);
                            $('#pic_name').textbox('setText', info.imgName);
                            $('#preview_icon').attr('src', info.imgURL);
                            if ($('#view_type').combobox('getValue') != 1) {
                                $('#annotation_view_target').animate({"opacity": 0.7});
                                $('#view_target').attr('disabled', true);
                            } else {
                                $('#annotation_view_target').animate({"opacity": 1});
                                $('#view_target').removeAttr('disabled');
                            }
                            ;
                        }
                    });
                }
            }

            /*=======viewType与ViewTarget=======*/
            $('#view_type').combobox({
                onChange: function (newVal, oldVal) {
                    if (newVal != 1) {
                        $('#annotation_view_target').animate({"opacity": 0.7});
                        $('#view_target').attr('disabled', true);
                    } else {
                        $('#annotation_view_target').animate({"opacity": 1});
                        $('#view_target').removeAttr('disabled');
                    }
                }
            });

            /*========bannerType radio========*/
            $('#bannerType input[type=radio]').each(function (index, element) {
                $(this).click(function(){
                        if($(this).val() != <%=Constant.BANNER_MAIN%> ){
                            $("#view_type option[value=1]").remove();
                            $("#view_type").combobox({});
                            $('#view_type').combobox('setValue',<%=Constant.BANNER_VIEWTYPE_MERCHANT%>).combobox('setText','商家页面');
                            $('#annotation_view_target').animate({"opacity": 0.7});
                            $('#view_target').attr('disabled', true);
                        }else{
                            $("#view_type").append("<option value='1' >导航</option>");
                            $("#view_type").combobox({});
                            $('#view_type').combobox('setValue',<%=Constant.BANNER_VIEWTYPE_TARGET%>).combobox('setText','导航');
                            $('#annotation_view_target').animate({"opacity": 1});
                            $('#view_target').removeAttr('disabled');
                        }
                });
            });

        });

        /*========预览轮播图========*/
        function preview(url) {
            console.log(url);
            $('#preview').dialog({
                title: '预览',
                iconCls: 'icon-preview',
                modal: 'true',
                width: 500,
                height: 200,
            });
            $('#previes_img').attr('src', url);
        }

        function openPickDialog() {
            $("#upload-dialog").dialog('open');
        }


    </script>
</head>
<body>

<!--数据网格开始-->
<table id="banner_gird"></table>
<!--数据网格开始-->

<!--增加轮播图开始-->
<div id="add-dialog" align="center">
    <form action="${basePath}/admin/api/navigation/add" method="POST" id="add_form">
        <input type="hidden" id="banner_id" name="bannerId">
        <input type="hidden" name="imgInfo.imgInfoId" id="pic_id">
        <table style="margin: 20px auto 20px auto;border-collapse: separate;border-spacing: 7px">
            <tr>
                <td valign="top">上传轮播图图片:</td>
                <td>
                    <div style="margin-bottom: 5px">
                        <input type="text" class="easyui-textbox" id="pic_name" data-options="editable:false">
                        <button type="button" class="easyui-linkbutton" id="select">选择图片</button>
                        <button type="button" class="easyui-linkbutton" id="upload">上传图片</button>
                    </div>
                    <div>
                        <div style="border: dotted;border-width: 1px;float: left">
                            <img src="${basePath}/static/images/preview.jpg" id="preview_icon"
                                 style="width: 170px;height:80px">
                        </div>
                        <div id="progress" style="float: left;margin-top: 40px"></div>
                    </div>
                </td>
            </tr>
            <tr>
                <td valign="top"><label>轮播图所在位置:</label></td>
                <td>
                    <table id="bannerType">
                        <tr>
                            <td><input type="radio" name="bannerType" value="<%=Constant.BANNER_MAIN%>" checked >首页</td>
                            <td><input type="radio" name="bannerType" value="<%=Constant.BANNER_DAILY_NEW%>">每日上新</td>
                            <td><input type="radio" name="bannerType" value="<%=Constant.BANNER_BUY_KUNMING%>">买昆明</td>
                            <td><input type="radio" name="bannerType" value="<%=Constant.BANNER_BUY_PREFECTURES%>">购地州
                            </td>
                        </tr>
                        <td><input type="radio" name="bannerType" value="<%=Constant.BANNER_TYPE_CATEGOR%>">分类</td>
                        <td><input type="radio" name="bannerType" value="<%=Constant.BANNER_BIG_BRAND%>">大品牌</td>
                        <td><input type="radio" name="bannerType" value="<%=Constant.BANNER_TYPE_ACTIVITY_ZONE%>">活动专区
                        </td>
                    </table>
                </td>
            </tr>
            <tr id="annotation_vie_type">
                <td>
                    <label>跳转页面的类型:</label>
                </td>
                <td>
                    <select id="view_type" name="viewType" style="width:150px;">
                        <option value="2">商家页面</option>
                        <option value="3">商品页面</option>
                        <option value="4">活动页面</option>
                        <option value="5">指定页面</option>
                    </select>
                </td>
            </tr>
            <tr id="annotation_view_target">
                <td>跳转到哪个导航:</td>
                <td>
                    <select id="view_target" name="viewTargert" style="width:150px;">
                        <option value="1">每日上新</option>
                        <option value="2">大品牌</option>
                        <option value="3">买昆明</option>
                        <option value="4">购地州</option>
                        <option value="5">类别</option>
                        <option value="6">活动专区</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>轮播图顺序:</td>
                <td><input type="text" class="easyui-numberspinner" data-options="prompt:'导航顺序'" name="bannerOrder"
                           style="width: 250px" value="1"></td>
            </tr>
        </table>
    </form>
</div>
<!--增加轮播图结束-->

<!--预览轮播图开始-->
<div id="preview">
    <img id="previes_img" style="width: 100%;height: 100%">
</div>
<!--预览轮播图结束-->

</body>
</html>
