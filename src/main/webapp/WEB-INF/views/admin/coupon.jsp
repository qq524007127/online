<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="e" uri="org.topteam/easyui" %>
<html>
<head>
    <title>商家优惠券</title>
    <%@include file="/WEB-INF/views/commons/header.jsp" %>
    <script type="text/javascript">
        var merchantId = '${merchant.merchantId}';
        var activityId = '${activityId}'
        function getFaceValue(value, row) {
            if (row.couponType == 1) {
                return value + '折';
            }
            return value + '元';
        }
        function getCouponType(value) {
            if (value == 1) {
                return '打折卷';
            }
            return '代金卷';
        }
        function onSubmitSeccess(data) {
            onAfterEdit(data, 'coupon-grid', function () {
                $('#add-dialog').dialog('close');
            })
        }
        function onEditSeccess(data) {
            onAfterEdit(data, 'coupon-grid', function () {
                $('#edit-dialog').dialog('close');
            })
        }

        function loadCouponInfo() {
            var rows = $('#coupon-grid').datagrid('getChecked');
            if (rows.length != 1) {
                $.messager.alert('提示', '请选择需要操作的数据', 'info');
                return;
            }
            $('#edit-form').form('load', rows[0]);
            $('#edit-dialog').dialog('open');
        }

        function removeCoupon() {
            var rows = $('#coupon-grid').datagrid('getChecked');
            if (rows.length != 1) {
                $.messager.alert('提示', '请选择需要操作的数据', 'info');
                return;
            }
            $.messager.confirm('提示', '确定要删除所选则优惠券吗，删除后不可恢复？', function (r) {
                if (r) {
                    request({
                        url: '${root}/admin/api/coupon/delete',
                        data: {
                            couponId: rows[0].couponId
                        },
                        success: function (data) {
                            if (data.success) {
                                $.messager.show({
                                    title: '提示',
                                    msg: data.msg
                                })
                                $('#coupon-grid').datagrid('load');
                            }
                            else {
                                $.messager.alert('提示', data.msg, 'info');
                            }
                        },
                        error: function () {
                            $.messager.alert('提示', '系统错误，刷新后重试或联系管理员', 'error');
                        }
                    })
                }
            });
        }
    </script>
</head>
<e:body>
    <e:datagrid id="coupon-grid" url="${root}/admin/api/activity/${activityId}/coupon/list" singleSelect="true"
                fit="true" pagination="true" fitColumns="true" rownumbers="true" border="false" striped="true">
        <e:eventListener event="onLoadError" listener="onGridLoadError"/>
        <e:columns>
            <e:column field="couponId" checkbox="true"/>
            <e:column field="faceValue" title="面值" align="center" width="20" sortable="true" formatter="getFaceValue"/>
            <e:column field="total" title="优惠券数量" align="center" width="20" sortable="true"/>
            <e:column field="totalReceived" title="已领取" align="center" width="20" sortable="true"/>
            <e:column field="couponType" title="卷类型" align="center" width="20" formatter="getCouponType"
                      sortable="true"/>
            <e:column field="couponMsg" title="使用规则" align="center" width="40"/>
        </e:columns>
        <e:facet name="toolbar">
            <e:button id="add-coupon" iconCls="icon-add" text="添加优惠券" plain="true">
                <e:event event="click" target="add-dialog" action="open"/>
            </e:button>
            <e:button id="edit-coupon" iconCls="icon-edit" text="修改优惠券" plain="true">
                <e:eventListener event="click" listener="loadCouponInfo"/>
            </e:button>
            <e:button id="remove-coupon" iconCls="icon-remove" text="删除优惠券" plain="true">
                <e:eventListener event="click" listener="removeCoupon"/>
            </e:button>
        </e:facet>
    </e:datagrid>
    <e:dialog id="add-dialog" style="width:540px;height:260px;" iconCls="icon-tip" title="优惠券信息" closed="true"
              closable="true" modal="true">
        <e:event event="onBeforeClose" target="add-form" action="reset"/>
        <e:form id="add-form" url="${root}/admin/api/coupon/add" method="post">
            <e:eventListener event="success" listener="onSubmitSeccess"/>
            <input type="hidden" name="merchant.merchantId" value="${merchant.merchantId}">
            <input type="hidden" name="activity.activitId" value="${activityId}">
            <table class="form-table">
                <tr>
                    <td><label>面值：</label></td>
                    <td>
                        <input type="text" class="easyui-numberbox" name="faceValue"
                               data-options="min:0,precision:1,required:true">
                    </td>
                    <td><label>份数：</label></td>
                    <td>
                        <input type="text" class="easyui-numberbox" name="total"
                               data-options="min:1,precision:0,required:true">
                    </td>
                </tr>
                <tr>
                    <td><label>券类型：</label></td>
                    <td colspan="3">
                        <select name="couponType" class="easyui-combobox" style="width: 140px"
                                data-options="panelHeight:50,required:true,editable:false">
                            <option value="1">打折卷</option>
                            <option value="2">代金券</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><label>使用规则：</label></td>
                    <td colspan="3">
                        <textarea cols="60" rows="4" name="couponMsg"></textarea>
                    </td>
                </tr>
            </table>
        </e:form>
        <e:facet name="buttons">
            <e:button id="cancel-btn" iconCls="icon-cancel" text="取消">
                <e:event event="click" target="add-dialog" action="close"/>
            </e:button>
            <e:button id="add-btn" iconCls="icon-ok" text="添加">
                <e:event event="click" target="add-form" action="submit"/>
            </e:button>
        </e:facet>
    </e:dialog>

    <e:dialog id="edit-dialog" style="width:540px;height:260px;" iconCls="icon-tip" title="优惠券信息" closed="true"
              closable="true" modal="true">
        <e:event event="onBeforeClose" target="edit-form" action="clear"/>
        <e:form id="edit-form" url="${root}/admin/api/coupon/editInfo" method="post">
            <e:eventListener event="success" listener="onEditSeccess"/>
            <input type="hidden" name="couponId">
            <table class="form-table">
                <tr>
                    <td><label>面值：</label></td>
                    <td>
                        <input type="text" class="easyui-numberbox" name="faceValue"
                               data-options="min:0,precision:1,required:true">
                    </td>
                    <td><label>份数：</label></td>
                    <td>
                        <input type="text" class="easyui-numberbox" name="total"
                               data-options="min:1,precision:0,required:true">
                    </td>
                </tr>
                <tr>
                    <td><label>券类型：</label></td>
                    <td colspan="3">
                        <select name="couponType" class="easyui-combobox" style="width: 140px"
                                data-options="panelHeight:50,required:true,editable:false">
                            <option value="1">打折卷</option>
                            <option value="2">代金券</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><label>使用规则：</label></td>
                    <td colspan="3">
                        <textarea cols="60" rows="4" name="couponMsg"></textarea>
                    </td>
                </tr>
            </table>
        </e:form>
        <e:facet name="buttons">
            <e:button id="cancel-edit" iconCls="icon-cancel" text="取消">
                <e:event event="click" target="edit-dialog" action="close"/>
            </e:button>
            <e:button id="submit-edit" iconCls="icon-ok" text="修改">
                <e:event event="click" target="edit-form" action="submit"/>
            </e:button>
        </e:facet>
    </e:dialog>
</e:body>
</html>
