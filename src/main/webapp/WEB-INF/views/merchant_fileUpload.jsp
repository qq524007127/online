<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/12/1
  Time: 9:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<title>商户注册</title>
<head>
    <c:set var="basePath" value="${pageContext.request.contextPath}"/>
    <link rel="stylesheet" href="${basePath}/static/bootstrap/css/bootstrap.css">
    <link rel="stylesheet" href="${basePath}/static/bootstrap/css/bootstrapValidator.css">
    <link rel="stylesheet" href="${basePath}/static/bootstrap/css/bootstrap.min.css">
    <script type="text/javascript" src="${basePath}/static/core/jquery.min.js"></script>
    <script type="text/javascript" src="${basePath}/static/core/plupload/plupload.full.min.js"></script>
    <script type="text/javascript" src="${basePath}/static/core/qiniu.min.js"></script>
    <script type="text/javascript" src="${basePath}/static/core/uploader.js"></script>
    <script type="text/javascript" src="${basePath}/static/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${basePath}/static/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${basePath}/static/bootstrap/js/bootstrapValidator.js"></script>
    <script type="text/javascript" src="${basePath}/static/bootstrap/js/bootstrapValidator.min.js"></script>
    <%--<script type="text/javascript" src="${basePath}/static/core/json2.js"></script>--%>
    <style>
        body {
            font: 400 12px/14px Geneva, "宋体", Tahoma, sans-serif;
        }

        .container {
            width: 60%;
            height: auto;
            margin-top: 2%;
            margin-left: auto;
            margin-right: auto;
            padding: 0px 0px 10px 0px;
            border-left: solid;
            border-bottom: solid;
            border-right: solid;
            border-width: 1px;
            border-color: #2d71b8;
        }

        h2 {
            width: 100%;
            height: 60px;
            margin-left: auto;
            margin-right: auto;
            margin-top: 0px;
            background-color: #2d71b8;
            line-height: 60px;
            color: #FFFFFF;
            font-weight: bold;
            text-align: center;
        }

        fieldset {
            width: 80%;
            margin-left: auto;
            margin-right: auto;
            color: #333;
            border-top: solid;
            border-width: 2px;
            border-color: #2d71b8;
        }

        legend {
            width: auto;
            height: 20px;
            padding-left: 10px;
            padding-right: 10px;
            line-height: 20px;
            text-align: center;
            font-weight: 400;
        }

        label{
            font-size: 12px;
            display: block;
        }
        .form-control {
            padding: 6px 64px;
        }

    </style>
    <script type="text/javascript">
        var imgInfoIds = {};
        function merchant_img(img_name, img_id) {
            imgInfoIds[img_name] = img_id;
            console.log(imgInfoIds);
        };

        $(function () {
            $.each($('.select_btn'), function (index, element) {
                var keyName = $(element).data('key');
                var pickbutton = $(element).attr('id');
                var uploadbutton = $(element).next().attr('id');
                var filename = $(element).prev();
                initUploader({
                    pickbutton: pickbutton,
                    uploadbutton: uploadbutton,
                    onFileAdd: function (file) {
                        filename.val(file.name);
                    },
                    onError: function (file, err, errTip) {

                    },
                    onBeforeUpload: function (up, file) {
                        total = up.total.size;
                        loaded = up.total.loaded;
                        progress = parseInt(loaded / total * 100);
                        $(element).next().next().next()[0].innerHTML = ' 正在上传...';
                    },
                    onUploaded: function (up, file, info) {
                        if (keyName) {
                            console.log(info);
                            merchant_img(keyName, info.imgInfoId);
                            $(element).next().next().children().attr('src', info.url);
                            $(element).next().next().next()[0].innerHTML = ' 上传完成';
                        }
                    },
                    onComplete: function () {

                    }
                });
            });

            $('#submit').click(function () {
                if (Object.keys(imgInfoIds).length == 6) {
                    $.ajax({
                        url: '${basePath}/merchant/register/uploadImg',
                        data: imgInfoIds,
                        dataType: 'text',
                        type: 'post',
                        success: function (data) {
                            console.log('upload ok');
                        }
                    });
                } else {
                    alert("你有未上传的图片");
                }
            });
        });
    </script>
</head>
<body>
<div>
    <div class="container">
        <h2>商家注册</h2>
        <fieldset>
            <legend>
                <span style="font-size:20px">提交有关审核图片</span>
            </legend>
            <table cellspacing="10" style="border-collapse: separate;border-spacing: 15px;width: 100%">
                <tr>
                    <td width="50%">
                        <label><span style="color: red;font-weight: normal">* </span>商家封面图:</label>
                        <input class="fileName" type="text" placeholder="图片名">
                        <button id="select_cover_img" type="button" class="select_btn btn btn-default random"
                                data-key="coverImg.imgInfoId">
                            选择图片
                        </button>
                        <button id="upload_cover_img" type="button" class="btn btn-primary random" title="提示"
                                data-container="body"
                                data-trigger="focus" data-placement="top" data-content="请先选择需要上传的图片">
                            上传图片
                        </button>
                        <div style="border: dotted;border-width: 1px;width: 120px;height: 120px;margin-top:10px;float: left">
                            <img src="${basePath}/static/images/preview.jpg" style="width: 100%;height: 100%">
                        </div>
                        <div style="float: left;margin-top: 55px"></div>
                    </td>
                    <td width="50%">
                        <label><span style="color: red;font-weight: normal">* </span>商家顶部图:</label>
                        <input class="fileName" type="text" class="form-control"
                               placeholder="Text input">
                        <button id="select_headerImg" type="button" class="select_btn btn btn-default random"
                                data-key="headerImg.imgInfoId">
                            选择图片
                        </button>
                        <button id="upload_headerImg" type="button" class="btn btn-primary random" title="提示"
                                data-container="body"
                                data-trigger="focus" data-placement="top" data-content="请先选择需要上传的图片">
                            上传图片
                        </button>
                        <div style="border: dotted;border-width: 1px;width: 120px;height: 120px;margin-top:10px;float: left">
                            <img src="${basePath}/static/images/preview.jpg" style="width: 100%;height: 100%">
                        </div>
                        <div style="float: left;margin-top: 55px"></div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label><span style="color: red;font-weight: normal">* </span>加盖公章的申请认证公函(与商家纠纷事件裁定等)照片:</label>
                        <input class="fileName" type="text" class="form-control">
                        <button id="select_applyLetterPhoto" type="button" class="select_btn btn btn-default random"
                                data-key="applyLetterPhoto.imgInfoId">
                            选择图片
                        </button>
                        <button id="upload_applyLetterPhoto" type="button" class="btn btn-primary random" title="提示"
                                data-container="body"
                                data-trigger="focus" data-placement="top" data-content="请先选择需要上传的图片">
                            上传图片
                        </button>
                        <div style="border: dotted;border-width: 1px;width: 120px;height: 120px;margin-top:10px;float: left">
                            <img src="${basePath}/static/images/preview.jpg" style="width: 100%;height: 100%">
                        </div>
                        <div style="float: left;margin-top: 55px"></div>
                    </td>
                    <td>
                        <label><span style="color: red;font-weight: normal">* </span>运营者手持身份证照片:</label>
                        <input class="fileName" type="text" class="form-control">
                        <button id="select_opraterIDPhoto" type="button" class="select_btn btn btn-default random"
                                data-key="opraterIDPhoto.imgInfoId">
                            选择图片
                        </button>
                        <button id="upload_opraterIDPhoto" type="button" class="btn btn-primary random" title="提示"
                                data-container="body"
                                data-trigger="focus" data-placement="top" data-content="请先选择需要上传的图片">
                            上传图片
                        </button>
                        <div style="border: dotted;border-width: 1px;width: 120px;height: 120px;margin-top:10px;float: left">
                            <img src="${basePath}/static/images/preview.jpg" style="width: 100%;height: 100%">
                        </div>
                        <div style="float: left;margin-top: 55px"></div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label><span style="color: red;font-weight: normal">* </span>组织机构代码证原件照片:</label>
                        <input class="fileName" type="text" class="form-control">
                        <button id="select_orgPhoto" type="button" class="select_btn btn btn-default random"
                                data-key="orgPhoto.imgInfoId">
                            选择图片
                        </button>
                        <button id="upload_orgPhoto" type="button" class="btn btn-primary random" title="提示"
                                data-container="body"
                                data-trigger="focus" data-placement="top" data-content="请先选择需要上传的图片">
                            上传图片
                        </button>
                        <div style="border: dotted;border-width: 1px;width: 120px;height: 120px;margin-top:10px;float: left">
                            <img src="${basePath}/static/images/preview.jpg" style="width: 100%;height: 100%">
                        </div>
                        <div style="float: left;margin-top: 55px"></div>
                    </td>
                    <td>
                        <label><span style="color: red;font-weight: normal">* </span>工商营业执照原件照片:</label>
                        <input class="fileName" type="text" class="form-control">
                        <button id="select_busLicePhoto" type="button" class="select_btn btn btn-default random"
                                data-key="busLicePhoto.imgInfoId">
                            选择图片
                        </button>
                        <button id="upload_busLicePhoto" type="button" class="btn btn-primary random" title="提示"
                                data-container="body"
                                data-trigger="focus" data-placement="top" data-content="请先选择需要上传的图片">
                            上传图片
                        </button>
                        <div style="border: dotted;border-width: 1px;width: 120px;height: 120px;margin-top:10px;float: left">
                            <img src="${basePath}/static/images/preview.jpg" style="width: 100%;height: 100%">
                        </div>
                        <div style="float: left;margin-top: 55px"></div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <button id="submit">提交</button>
                    </td>
                </tr>
            </table>
        </fieldset>
    </div>
</div>
</body>
</html>
