<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="e" uri="org.topteam/easyui" %>
<html>
<head>
    <title>七牛图片上传测试</title>
    <%@include file="commons/header.jsp"%>
    <e:resource location="static/core/plupload" name="plupload.full.min.js"/>
    <e:resource location="static/core" name="qiniu.min.js"/>
    <e:resource location="static/core" name="uploader.js"/>
</head>
<e:body>
    <e:button id="open-dialog" iconCls="icon-ok" text="打开对话框"/>
    <e:dialog id="dialog" iconCls="icon-save" modal="true" style="width:500px;height:300px;" closable="true"
              closed="true">
        <e:button id="pickbutton" iconCls="icon-ok" text="选择文件"/>
        <e:button id="upload" iconCls="icon-save" text="上传文件"/>
    </e:dialog>
</e:body>
<script type="text/javascript">
    (function ($) {
        $('#open-dialog').click(function () {
            $('#dialog').dialog('open');
            initUploader({
                pickbutton:'pickbutton',
                uploadbutton:'upload',
                onFileAdd:function(file){
                    $('#fileinfo').textbox('setText',file.name)
                },
                onError:function(file,err,errTip){
                    $.messager.progress('close');
                },
                onBeforeUpload:function(up,file){
                    $.messager.progress();
                },
                onUploaded:function(up,file,info){
                    console.log($.parseJSON(info));
                }
            });
        });
    })(jQuery);
</script>
</html>
