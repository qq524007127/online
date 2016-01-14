<%--
  Created by IntelliJ IDEA.
  User: YangDaiChun
  Date: 2015/12/14
  Time: 12:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>添加新商品</title>
    <c:set var="basePath" value="${pageContext.request.contextPath}"/>
    <script type="text/javascript" src="${basePath}/static/core/jquery.min.js"></script>
    <script type="text/javascript" src="${basePath}/static/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${basePath}/static/easyui/locale/easyui-lang-zh_CN.js" charset="UTF-8"></script>
    <link rel="stylesheet" href="${basePath}/static/easyui/themes/default/easyui.css">
    <link rel="stylesheet" href="${basePath}/static/easyui/themes/icon.css">

    <script type="text/javascript" src="${basePath}/static/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${basePath}/static/bootstrap/js/bootstrapValidator.js"></script>
    <script type="text/javascript" src="${basePath}/static/bootstrap/js/bootstrapValidator.min.js"></script>
    <link rel="stylesheet" href="${basePath}/static/bootstrap/css/bootstrap.css">
    <link rel="stylesheet" href="${basePath}/static/bootstrap/css/bootstrapValidator.css">
    <link rel="stylesheet" href="${basePath}/static/bootstrap/css/bootstrap.min.css">

    <script type="text/javascript" src="${basePath}/static/core/plupload/plupload.full.min.js"></script>
    <script type="text/javascript" src="${basePath}/static/core/qiniu.min.js"></script>
    <script type="text/javascript" src="${basePath}/static/core/uploader.js"></script>
    <style>
        body {
            margin: 0px;
            padding: 0px;
        }

        #container {
            margin-top: 1%;
            margin-left: 5%;
            margin-right: 5%;
            margin-bottom: 80px;
        }

        .info_forum {
            width: 90%;
            margin-left: auto;
            margin-right: auto;
            margin-bottom: 10px;
            padding-bottom: 10px;
            color: #333;
            border: solid;
            border-width: 1px;
            border-color: green;
        }

        .info_forum_title {
            width: auto;
            height: 20px;
            padding-left: 10px;
            padding-right: 10px;
            margin-left: 2%;
            line-height: 20px;
            border: none;
        }

        .form-group {
            margin-left: 50px;
            margin-top: 10px;
            margin-bottom: 5px;
        }

        .form-control {
            width: 70%;
            display: inline;
        }

        legend span {
            font-size: 14px;
            color: #ff4500;
            font-weight: bold;
        }

        .item_title {
            width: 120px;
            display: block;
            float: left;
        }

        #submit_btn, #reset_btn {
            width: 80px;
            height: 30px;
            border-radius: 5px;
            display: block;
            float: left;
            margin-left: 5px;
            background-color: #5599fe;
            color: #ffffff;
            font-weight: bold;
            border: none;
        }
    </style>
    <script type="text/javascript">
        $(function () {
            /*========设置表单验证=======*/
            $('#add_goods_form').bootstrapValidator({
                message: 'This value is not valid',
                feedbackIcons: {
                    valid: 'glyphicon glyphicon-ok',
                    invalid: 'glyphicon glyphicon-remove',
                    validating: 'glyphicon glyphicon-refresh'
                },
                fields: {
                    goodsName: {
                        validators: {
                            notEmpty: {
                                message: '商品名不可以为空'
                            },
                            stringLength: {
                                min: 2,
                                max: 30,
                                message: '长度最少为2个字符，最大为30个字符'
                            }
                        }
                    },
                    goodsDesc: {
                        validators: {
                            notEmpty: {
                                message: '商品描述不可以为空'
                            },
                            stringLength: {
                                min: 2,
                                max: 500,
                                message: '长度最少为2个字符，最大为30个字符'
                            }
                        }
                    },
                    price: {
                        validators: {
                            notEmpty: {
                                message: '商品价格不可以为空'
                            },
                        }
                    },
                    stock: {
                        validators: {
                            notEmpty: {
                                message: '库存量不可以为空'
                            }
                        }
                    },
                    carriageMethod: {
                        validators: {
                            notEmpty: {
                                message: '库存量不可以为空'
                            },
                            stringLength: {
                                min: 2,
                                max: 30,
                                message: '长度最少为2个字符，最大为30个字符'
                            },
                        }
                    },
                    carriage: {
                        validators: {
                            notEmpty: {
                                message: '库存量不可以为空'
                            },
                            stringLength: {
                                min: 2,
                                max: 30,
                                message: '长度最少为2个字符，最大为30个字符'
                            }
                        }
                    }
                }
            });

            /*========提交表单========*/
            $('#submit_btn').click(function () {
//                console.log();
                $('#banners_pic .pic_preview').each(function (index, element) {
                    var imgId = $($(element).children().get(0)).next().val();
                    $($(element).children().get(0)).next().next().attr('value', imgId + "#" + index);
                });
                $('#details_pic .pic_preview').each(function (index, element) {
                    var imgId = $($(element).children().get(0)).next().val();
                    var viewType = $($(element).children().get(0)).next().next().next().next().val();
                    var viewTarget = $($(element).children().get(0)).next().next().next().next().next().next().val();
                    $($(element).children().get(0)).next().next().attr('value', imgId + "#" +viewType+"*"+viewTarget+","+ index);
                });

                $('#add_goods_form').form('submit', {
                    url: '${basePath}/admin/api/goods/add',
                    success: function (info) {
                        info = $.parseJSON(info);
                        if (info.success) {
                            $.messager.show({
                                title: '提示',
                                msg: info.msg
                            });
                        } else {
                            $.messager.show({
                                title: '提示',
                                msg: '你有未填或不正确的输入！'
                            });
                        }
                    }
                });
            });

            /*========重置表单========*/
            $('#reset_btn').click(function () {
                $('#add_goods_form')[0].reset();
            });

            var flag = $('input[name="carriageState"]:checked').val();
            carriageOpacity(flag);


            /*=========上传图片========*/
            $('.select_btn').each(function (index, element) {
                initUploader({
                    pickbutton: $(element).attr('id'),
                    onFileAdd: function (file) {
                        if ($(element).next().val() == "coverImg") {
                            $(element).prev().val(file.name);
                        } else if ($(element).next().val() == "banner") {
                            $(element).before("<div  class='pic_preview' style='border: solid;border-width: 1px;border-color:#d98b4b;width: 115px;height: 115px;float:left;margin:0px 10px 0px 0px;'></div>");
                            $(element).prev().append("<img src='/static/images/loading.gif' style='width: 100%;height: 100%;'>");
                            $(element).prev().append("<input type='hidden'/><input type='hidden' name='goodsBanners'/>");
                            $(element).prev().append("<a style='display: block;width: 40px;margin: 0px auto 0px auto;text-align: center' onclick='deleteImg(this)'>删 除</a>");
                        } else {
                            $(element).before("<div  class='pic_preview' style='border: solid;border-width: 1px;border-color:#d98b4b;width: 205px;height:115px;float:left;margin:0px 15px 0px 0px;'></div>");
                            $(element).prev().append("<img src='/static/images/loading.gif' style='width:113px;height: 113px;display: block;float: left;margin-right: 5px'>");
                            $(element).prev().append("<input type='hidden'/><input type='hidden' name='goodsDetails'/>");
                            $(element).prev().append("<span style='color: #337ab7;'>跳转类型:</span><select class='form-control detail_img_select'  style='width: 80px;height: 25px;font-size: 10px;padding: 0px;'><option value='1'>导航</option><option value='2'>商家页面</option><option value='3'>商品页面</option><option value='4'>活动页面</option><option value='5'>指定页面</option></select>");
                            $(element).prev().append("<span style='color: #337ab7;'>跳转导航:</span><select class='form-control' style='width: 80px;height: 25px;font-size: 10px;padding: 0px;'><option value='1'>每日上新</option><option value='2'>大品牌</option><option value='3'>买昆明</option><option value='4'>购地州</option><option value='5'>类别</option><option value='6'>活动专区</option></select>");
                            $(element).prev().append("<a style='display: block;width: 40px;margin: 0px auto 0px auto;text-align: center' onclick='deleteImg(this)'>删 除</a>");
                            dispNavSelect();
                        }
                    },
                    onError: function (file, err, errTip) {

                    },
                    onBeforeUpload: function (up, file) {
                        total = up.total.size;
                        loaded = up.total.loaded;
                        progress = parseInt(loaded / total * 100);
                        if ($(element).next().val() == "coverImg") {
                            $(element).next().next().next().next()[0].innerHTML = ' 正在上传...';
                        } else if ($(element).next().val() == "banner") {

                        } else {

                        }
                    },
                    onUploaded: function (up, file, info) {
                        if ($(element).next().val() == "coverImg") {
                            $(element).prev().prev().attr('value', info.imgInfoId);
                            $(element).next().next().next().children().attr('src', info.url);
                            $(element).next().next().next().next()[0].innerHTML = ' 上传完成';
                        } else if ($(element).next().val() == "banner") {
                            $($(element).prev().children().get(0)).attr('src', info.url);
                            $($(element).prev().children().get(0)).next().attr('value', info.imgInfoId);
                        } else {
                            $($(element).prev().children().get(0)).attr('src', info.url);
                            $($(element).prev().children().get(0)).next().attr('value', info.imgInfoId);
                        }
                    },
                    onComplete: function () {

                    }
                });

                function dispNavSelect() {
                    $('.detail_img_select').each(function (index, element) {
                        if ($(element).val() != 1) {
                            $(element).next().animate({"opacity": 0});
                            $(element).next().next().animate({"opacity": 0});
                            $(element).next().next().attr('disabled', true);
                        } else {
                            $(element).next().animate({"opacity": 1});
                            $(element).next().next().animate({"opacity": 1});
                            $(element).next().next().removeAttr('disabled');
                        }
                        $(element).change(function () {
                            if ($(element).val() != 1) {
                                $(element).next().animate({"opacity": 0});
                                $(element).next().next().animate({"opacity": 0});
                                $(element).next().next().attr('disabled', true);
                            } else {
                                $(element).next().animate({"opacity": 1});
                                $(element).next().next().animate({"opacity": 1});
                                $(element).next().next().removeAttr('disabled');
                            }
                        });
                    });
                }
            });

            $('#selectbutton1').click(function () {
                $('#preview_img').attr('src', '${basePath}/static/images/preview.jpg');
                $('#cov_pregress').html("");
            });


        });

        /*========设置邮寄方式是否可编辑========*/
        function carriageOpacity(flag) {
            if (flag == 'true') {
                $('.no_edit').hide();
                $('#disp_carriagerMethod').animate({"opacity": 1});
                $('#disp_carriage').animate({"opacity": 1});
                $('#carriageMethod').attr('disabled', false);
                $('#carriage').attr('disabled', false);
            } else {
                $('.no_edit').show();
                $('#disp_carriagerMethod').animate({"opacity": 0.6});
                $('#disp_carriage').animate({"opacity": 0.6});
                $('#carriageMethod').attr('disabled', true);
                $('#carriage').attr('disabled', true);
            }
        }

        function deleteImg(element) {
            $(element).parent().remove();
        }


    </script>

</head>
<body>
<div id="container">
    <form method="post" id="add_goods_form">
        <fieldset class="info_forum">
            <legend class="info_forum_title"><span>1、商品基本信息</span></legend>
            <div class="form-group">
                <span class="item_title"><span style="color: red">*</span> 商品标题：</span>
                <input type="text" class="form-control" id="goodsName" name="goodsName"
                       placeholder="请输入商品的标题">
            </div>
            <div class="form-group">
                <table>
                    <td><span class="item_title" style="display: block;float: left"><span
                            style="color: red">*</span> 商品分类：</span>
                    </td>
                    <td>
                        <c:forEach items="${requestScope.goodsAttributeSets}" var="goodsAttributeSet">
                            <div style="float: left;width:25%;margin-top: 2px;margin-bottom: 2px">
                                <input type="radio" name="goodsAttributeSet.goodsAttSetId"
                                       value="${goodsAttributeSet.goodsAttSetId}">${goodsAttributeSet.goodsAttSetName}&nbsp;&nbsp;
                            </div>
                        </c:forEach>
                    </td>
                </table>
            </div>
            <div class="form-group">
                <span class="item_title" style="clear: both"><span style="color: red">*</span> 商品描述：</span>
                <textarea class="form-control" id="goodsDesc" name="goodsDesc" rows="3"
                          placeholder="请输入商品描述"></textarea>
            </div>
            <div class="form-group">
                <span class="item_title"><span style="color: red">*</span> 商品价格：</span>
                <input type="number" class="form-control" id="price" name="price"
                       placeholder="商品价格" style="width: 30%"> 元
            </div>
            <div class="form-group">
                <span class="item_title"><span style="color: red">*</span> 商品库存：</span>
                <input type="number" class="form-control" id="stock" name="stock"
                       placeholder="商品库存量" style="width: 30%"> 件
            </div>
            <div class="form-group">
                <span class="item_title"><span style="color: red">*</span> 商品封面图：</span>
                <input type="hidden" name="coverImg.imgInfoId">
                <input type="text" class="form-control" placeholder="图片名" style="width: 19%">
                <button type="button" class="btn btn-primary select_btn" id="selectbutton1">选择图片</button>
                <input type="hidden" value="coverImg">

                <div style="clear: both"></div>
                <div style="border: dotted;border-width: 1px;width: 120px;height: 120px;float:left;margin:10px auto 10px 120px;">
                    <img src="${basePath}/static/images/preview.jpg" style="width: 100%;height: 100%" id="preview_img">
                </div>
                <div style="margin-top: 55px;" id="cov_pregress"></div>
            </div>
        </fieldset>

        <fieldset class="info_forum">
            <legend class="info_forum_title"><span>2、商品详细信息</span></legend>

            <div class="form-group" id="banners_pic">
                <span class="item_title">
                    <span style="color: red">*</span>
                    商品轮播图：
                    <br><span style="font-size: 11px;color: red">(请自行排好顺序)</span>
                </span>

                <input type="image" src="${basePath}/static/images/upload.jpg" class="btn btn-default select_btn"
                       id="selectbutton2"/>
                <input type="hidden" value="banner">
            </div>

            <div class="form-group" style="clear: both;margin-top: 20px;margin-bottom: 20px" id="details_pic">
                <span class="item_title">
                    <span style="color: red">*</span>
                    商品详情图：
                    <br><span style="font-size: 11px;color: red">(请自行排好顺序)</span>
                </span>
                <input type="image" src="${basePath}/static/images/upload.jpg" class="btn btn-default select_btn"
                       id="selectbutton3"/>
                <input type="hidden" value="detail">

            </div>

        </fieldset>

        <fieldset class="info_forum">
            <legend class="info_forum_title"><span>3、商品物流信息</span></legend>
            <div class="form-group">
                <span class="item_title"><span style="color: red">*</span> 是否可以自取：</span>
                <input type="radio" name="isPick" value=true checked>是&nbsp;&nbsp;
                <input type="radio" name="isPick" value=false>否
            </div>
            <div class="form-group">
                <span class="item_title"><span style="color: red">*</span> 是否可以邮寄：</span>
                <input type="radio" class="carriageState" name="carriageState" value="true"
                       onclick="carriageOpacity('true')" checked>是&nbsp;&nbsp;
                <input type="radio" class="carriageState" name="carriageState" value="false"
                       onclick="carriageOpacity('false')">否
            </div>
            <div class="form-group" id="disp_carriagerMethod">
                <span class="item_title"> <span style="color: red">*</span> 快递方式：</span>
                <input type="text" class="form-control" id="carriageMethod" name="carriageMethod"
                       placeholder="快递方式" style="width: 30%">
                <span class="no_edit" style="color: red">不可编辑</span>
            </div>
            <div class="form-group" id="disp_carriage">
                <span class="item_title"><span style="color: red">*</span> 快递运费：</span>
                <input type="number" class="form-control" id="carriage" name="carriage"
                       placeholder="快递运费" style="width: 30%"> 元
                <span class="no_edit" style="color: red">不可编辑</span>
            </div>
        </fieldset>
        <button id="submit_btn" style="margin-top: 4px;margin-left: 40%">添 加</button>
    </form>
    <button id="reset_btn" style="background-color: orangered">重 置</button>
</div>

</body>
</html>
