/**
 * Created by ShenYunjie on 2015/11/30.
 */

/**
 * 数据datagrid数据加载失败是调用
 */
function onGridLoadError() {
    $.messager.alert('提示', '数据加载失败，请重试！', 'error');
}

/**
 * 操作失败是调用接口
 */
function onExecutError(){
    $.messager.alert('提示', '系统出错了，请重试或联系管理', 'error');
}

/**
 * 操作datagrid成功后提示
 * @param data  服务器返回数据
 * @param target 操作的datagrid
 * @param successCallback 如果成功调用的回调函
 */
function onAfterEdit(data, target, successCallback) {
    data = $.parseJSON(data);
    if (data.success) {
        $.messager.show({
            title: '提示',
            msg: data.msg
        })
        $('#' + target).datagrid('load');
        if (successCallback) {
            successCallback();
        }
    }
    else {
        $.messager.alert('提示', data.msg, 'error');
    }
}

function request(param){
    var settings = {
        type: param.type||"POST",
        url:param.url,
        dataType:param.dataType||"text",
        data:param.data,
        error: function(XHR,textStatus,errorThrown) {
            if(param.error){
                param.error(XHR,textStatus,errorThrown);
            }
        },
        success: function(data,textStatus) {
            if(this.dataType == "text"){
                data = $.parseJSON(data);
            }
            if(param.success){
                param.success(data,textStatus);
            }
        },
        headers: {
            //"token": $.cookie("token")
        }
    };
    $.ajax(settings);
};
