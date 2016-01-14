
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="cn.com.zhihetech.online.bean.Goods" %>
<%@ page import="cn.com.zhihetech.online.bean.GoodsAttributeSet" %>
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
    <title>编辑商品</title>
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
            margin-top: 2%;
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
                $('#add_goods_form').form('submit', {
                    url: '${basePath}/admin/api/goods/update',
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


            /*=========上传顶部图片========*/
            initUploader({
                pickbutton: 'selectbutton',
                uploadbutton: 'uploadbutton',
                onFileAdd: function (file) {
                    $('#disp_pic_name').val(file.name);
                },
                onError: function (file, err, errTip) {

                },
                onBeforeUpload: function (up, file) {

                },
                onUploaded: function (up, file, info) {
                    console.log(info);
                    $('#coverImg').val(info.imgInfoId);
                },
                onComplete: function () {

                }
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

    </script>

</head>
<body>
<div id="container">
    <form method="post" id="add_goods_form">
        <fieldset class="info_forum">
            <legend class="info_forum_title"><span>1、商品基本信息</span></legend>
            <input type="hidden" name="goodsId" value="${requestScope.goods.goodsId}">
            <input type="hidden" name="merchant.merchantId" value="${requestScope.goods.merchant.merchantId}">
            <input type="hidden" name="createDate" value="${requestScope.goods.createDate}">
            <input type="hidden" name="examinState" value=${requestScope.goods.examinState}>
            <input type="hidden" name="examinMsg" value="${requestScope.goods.examinMsg}">
            <input type="hidden" name="deleteState" value=${requestScope.goods.deleteState}>
            <input type="hidden" name="isActivityGoods" value=${requestScope.goods.isActivityGoods}>

            <div class="form-group">
                <span class="item_title"><span style="color: red">*</span> 商品标题：</span>
                <input type="text" class="form-control" id="goodsName" name="goodsName"
                       placeholder="请输入商品的标题" value="${requestScope.goods.goodsName}">
            </div>
            <div class="form-group">
                <table>
                    <td><span class="item_title" style="display: block;float: left"><span
                            style="color: red">*</span> 商品分类：</span>
                    </td>
                    <td>
                        <%
                            Goods goods = (Goods) request.getAttribute("goods");
                            Iterator<GoodsAttributeSet> iterator = ((List<GoodsAttributeSet>) request.getAttribute("goodsAttributeSets")).iterator();
                            while (iterator.hasNext()) {
                                GoodsAttributeSet goodsAttributeSet = iterator.next();
                                if (goodsAttributeSet.getGoodsAttSetId().equals(goods.getGoodsAttributeSet().getGoodsAttSetId())) {
                        %>
                        <div style="float: left;width:25%;margin-top: 2px;margin-bottom: 2px">
                            <input type="radio" name="goodsAttributeSet.goodsAttSetId" checked="checked"
                                   value="<%=goodsAttributeSet.getGoodsAttSetId()%>"><%=goodsAttributeSet.getGoodsAttSetName()%>&nbsp;&nbsp;
                        </div>
                        <%
                                continue;
                            }
                        %>
                        <div style="float: left;width:25%;margin-top: 2px;margin-bottom: 2px">
                            <input type="radio" name="goodsAttributeSet.goodsAttSetId"
                                   value="<%=goodsAttributeSet.getGoodsAttSetId()%>"><%=goodsAttributeSet.getGoodsAttSetName()%>&nbsp;&nbsp;
                        </div>
                        <%
                            }
                        %>
                    </td>
                </table>
            </div>
            <div class="form-group">
                <span class="item_title" style="clear: both"><span style="color: red">*</span> 商品描述：</span>
                <textarea class="form-control" id="goodsDesc" name="goodsDesc" rows="3"
                          placeholder="请输入商品描述">${requestScope.goods.goodsDesc}</textarea>
            </div>
            <div class="form-group">
                <span class="item_title"><span style="color: red">*</span> 商品价格：</span>
                <input type="number" class="form-control" id="price" name="price"
                       placeholder="商品价格" style="width: 30%" value="${requestScope.goods.price}"> 元
            </div>
            <div class="form-group">
                <span class="item_title"><span style="color: red">*</span> 商品库存：</span>
                <input type="number" class="form-control" id="stock" name="stock"
                       placeholder="商品库存量" style="width: 30%" value="${requestScope.goods.stock}"> 件
            </div>
            <div class="form-group">
                <span class="item_title"><span style="color: red">*</span> 商品封面图：</span>
                <input type="text" class="form-control"
                       placeholder="图片名" style="width: 30%" id="disp_pic_name">
                <input type="hidden" name="coverImg.imgInfoId" id="coverImg">
                <button type="button" class="btn btn-primary" id="selectbutton">
                    选择图片
                </button>
                <button type="button" class="btn btn-default"
                        id="uploadbutton">上传图片
                </button>
            </div>
            <div class="form-group">
                <span class="item_title"><span style="color: red">*</span> 是否上架：</span>
                <input type="radio" name="onsale" value=true checked>是&nbsp;&nbsp;
                <input type="radio" name="onsale" value=false> 否
            </div>
        </fieldset>
        <fieldset class="info_forum">
            <legend class="info_forum_title"><span>2、商品物流信息</span></legend>
            <div class="form-group">
                <span class="item_title"><span style="color: red">*</span> 是否可以自取：</span>
                <c:choose>
                    <c:when test="${requestScope.goods.isPick == true}">
                        <input type="radio" name="isPick" value=true checked>是&nbsp;&nbsp;
                        <input type="radio" name="isPick" value=false>否
                    </c:when>
                    <c:otherwise>
                        <input type="radio" name="isPick" value=true>是&nbsp;&nbsp;
                        <input type="radio" name="isPick" value=false checked>否
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="form-group">
                <span class="item_title"><span style="color: red">*</span> 是否可以邮寄：</span>
                <c:choose>
                    <c:when test="${requestScope.goods.carriageMethod != null}">
                        <input type="radio" class="carriageState" name="carriageState" value="true"
                               onclick="carriageOpacity('true')" checked>是&nbsp;&nbsp;
                        <input type="radio" class="carriageState" name="carriageState" value="false"
                               onclick="carriageOpacity('false')">否
                    </c:when>
                    <c:otherwise>
                        <input type="radio" class="carriageState" name="carriageState" value="true"
                               onclick="carriageOpacity('true')">是&nbsp;&nbsp;
                        <input type="radio" class="carriageState" name="carriageState" value="false"
                               onclick="carriageOpacity('false')" checked>否
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="form-group" id="disp_carriagerMethod">
                <span class="item_title"> <span style="color: red">*</span> 快递方式：</span>
                <input type="text" class="form-control" id="carriageMethod" name="carriageMethod"
                       placeholder="快递方式" style="width: 30%" value="${requestScope.goods.carriageMethod}">
                <span class="no_edit" style="color: red">不可编辑</span>
            </div>
            <div class="form-group" id="disp_carriage">
                <span class="item_title"><span style="color: red">*</span> 快递运费：</span>
                <input type="number" class="form-control" id="carriage" name="carriage"
                       placeholder="快递运费" style="width: 30%" value="${requestScope.goods.carriage}"> 元
                <span class="no_edit" style="color: red">不可编辑</span>
            </div>
        </fieldset>
        <button id="submit_btn" style="margin-top: 4px;margin-left: 40%">修 改</button>
    </form>
    <button id="reset_btn" style="background-color: orangered">重 置</button>
</div>

</body>
</html>
