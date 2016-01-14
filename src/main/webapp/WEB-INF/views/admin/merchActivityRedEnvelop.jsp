<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="e" uri="org.topteam/easyui" %>
<html>
<head>
    <title>活动红包</title>
    <%@include file="/WEB-INF/views/commons/header.jsp" %>
    <script type="text/javascript">
        var merchId = '${merchant.merchantId}';
        var activityId = '${activityId}';
        /**
         *添加红包的表单提交成功之后毁掉方法
         * @param data
         */
        function onFormSubmitSuccess(data) {
            onAfterEdit(data, 'redenvelop-grid', function () {
                $('#redenvelop-dialog').dialog('close');
            })
        }

        /**
         *修改红包信息成功之后回调
         *@param data
         */
        function onUpdateSuccess(data) {
            onAfterEdit(data, 'redenvelop-grid', function () {
                $('#update-dialog').dialog('close');
            })
        }

        /**
         * 点击修改按钮，弹出修改对话框
         */
        function loadInfo() {
            var rows = $('#redenvelop-grid').datagrid('getChecked');
            if (!rows || rows.length != 1) {
                $.messager.alert('提示', '请选择需要操作的数据', 'info');
                return;
            }
            $('#update-dialog').dialog('open');
            $('#update-form').form('load', rows[0]);
        }

        /**
         *   删除红包
         */
        function removeRedEnvelop() {
            var rows = $('#redenvelop-grid').datagrid('getChecked');
            if (!rows || rows.length != 1) {
                $.messager.alert('提示', '请选择需要操作的数据', 'info');
                return;
            }
            $.messager.confirm('提示', '确定要删除此红包吗，删除后不可恢复？', function (r) {
                if (r) {
                    request({
                        url: '${root}/admin/api/redEnvelop/delete',
                        data: {
                            envelopId: rows[0].envelopId
                        },
                        success: function (data) {
                            if (data.success) {
                                $('#redenvelop-grid').datagrid('load');
                                $.messager.show({
                                    title: '提示',
                                    msg: data.msg
                                })
                            }
                            else {
                                $.messager.alert('提示', data.msg, 'info');
                            }
                        },
                        error: function () {
                            $.messager.alert('提示', '系统出错，请联系管理员！', 'error');
                        }
                    })
                }
            })
        }
    </script>
</head>
<e:body>
    <e:datagrid id="redenvelop-grid" url="${root}/admin/api/activity/${activityId}/redEnvelop/list" singleSelect="true"
                fit="true" pagination="true" fitColumns="true" rownumbers="true" border="false" striped="true">
        <e:eventListener event="onLoadError" listener="onGridLoadError"/>
        <e:columns>
            <e:column field="envelopId" checkbox="true"/>
            <e:column field="totalMoney" title="红包金额" align="center" width="20" sortable="true"/>
            <e:column field="numbers" title="红包个数" align="center" width="20" sortable="true"/>
            <e:column field="createDate" title="添加时间" align="center" width="20" sortable="true"/>
            <e:column field="envelopMsg" title="留言" align="center" width="40"/>
        </e:columns>
        <e:facet name="toolbar">
            <e:button id="add-redmoney" iconCls="icon-add" text="添加红包" plain="true">
                <e:event event="click" target="redenvelop-dialog" action="open"/>
            </e:button>
            <e:button id="add-coupon" iconCls="icon-edit" text="编辑红包" plain="true">
                <e:eventListener event="click" listener="loadInfo"/>
            </e:button>
            <e:button id="remove-btn" iconCls="icon-cancel" text="删除红包" plain="true">
                <e:eventListener event="click" listener="removeRedEnvelop"/>
            </e:button>
        </e:facet>
    </e:datagrid>

    <e:dialog id="redenvelop-dialog" title="活动红包" iconCls="icon-tip" style="width:500px;height:260px" closed="true"
              modal="true">
        <e:event event="onBeforeClose" target="redenvelop-form" action="reset"/>
        <e:form id="redenvelop-form" url="${root}/admin/api/redEnvelop/add" method="post">
            <e:eventListener event="success" listener="onFormSubmitSuccess"/>
            <input type="hidden" name="merchant.merchantId" value="${merchant.merchantId}">
            <input type="hidden" name="activity.activitId" value="${activityId}">
            <table class="form-table">
                <tr>
                    <td><label>金额：</label></td>
                    <td>
                        <input type="text" class="easyui-numberbox" name="totalMoney"
                               data-options="min:1,precision:2,required:true">
                    </td>
                    <td><label>数量：</label></td>
                    <td>
                        <input type="text" class="easyui-numberbox" name="numbers"
                               data-options="min:1,precision:0,required:true">
                    </td>
                </tr>
                <tr>
                    <td><label>留言：</label></td>
                    <td colspan="3">
                        <textarea name="envelopMsg" cols="50" rows="4"></textarea>
                    </td>
                </tr>
            </table>
        </e:form>
        <e:facet name="buttons">
            <e:button id="redenvelop-cancel-btn" iconCls="icon-cancel" text="取消">
                <e:event event="click" target="redenvelop-dialog" action="close"/>
            </e:button>
            <e:button id="redenvelop-submit-btn" iconCls="icon-ok" text="保存">
                <e:event event="click" target="redenvelop-form" action="submit"/>
            </e:button>
        </e:facet>
    </e:dialog>

    <e:dialog id="update-dialog" title="活动红包" iconCls="icon-edit" style="width:500px;height:260px" closed="true"
              modal="true">
        <e:event event="onBeforeClose" target="update-form" action="clear"/>
        <e:form id="update-form" url="${root}/admin/api/redEnvelop/updateInfo" method="post">
            <e:eventListener event="success" listener="onUpdateSuccess"/>
            <input type="hidden" name="envelopId">
            <table class="form-table">
                <tr>
                    <td><label>金额：</label></td>
                    <td>
                        <input type="text" class="easyui-numberbox" name="totalMoney"
                               data-options="min:1,precision:2,required:true">
                    </td>
                    <td><label>数量：</label></td>
                    <td>
                        <input type="text" class="easyui-numberbox" name="numbers"
                               data-options="min:1,precision:0,required:true">
                    </td>
                </tr>
                <tr>
                    <td><label>留言：</label></td>
                    <td colspan="3">
                        <textarea name="envelopMsg" cols="50" rows="4"></textarea>
                    </td>
                </tr>
            </table>
        </e:form>
        <e:facet name="buttons">
            <e:button id="update-cancel-btn" iconCls="icon-cancel" text="取消">
                <e:event event="click" target="update-dialog" action="close"/>
            </e:button>
            <e:button id="update-submit-btn" iconCls="icon-ok" text="保存">
                <e:event event="click" target="update-form" action="submit"/>
            </e:button>
        </e:facet>
    </e:dialog>
</e:body>
</html>
