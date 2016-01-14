<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="e" uri="org.topteam/easyui" %>
<html>
<head>
    <title>活动列表</title>
    <%@include file="/WEB-INF/views/commons/header.jsp" %>
    <script type="text/javascript">
        function onSubmitSuccess(data) {
            onAfterEdit(data, 'activity-grid', function () {
                $('#data-dialog').dialog('close');
            })
        }
        function getMerchName(merchant) {
            if (merchant) {
                return merchant.merchName;
            }
        }
        function getCategName(category) {
            if (category) {
                return category.categName;
            }
        }
        function getGoodsCategName(goodsCateg) {
            if (goodsCateg) {
                return goodsCateg.goodsAttSetName;
            }
        }
        function getState(state) {
            console.log(state);
            switch (state) {
                case 1:
                    return '未提交';
                    break;
                case 1:
                    return '审核中';
                    break;
                case 1:
                    return '<span style="color:red;">审核未通过</span>';
                    break;
                case 1:
                    return '已审核通过';
                    break;
                default :
                    break;
            }
        }
        function getButtons(){
            return '<a class="easyui-linkbutton">预览</a>';
        }
    </script>
</head>
<e:body>
    <e:datagrid id="activity-grid" url="${root}/admin/api/activity/list" singleSelect="true" fit="true"
                pagination="true" fitColumns="true" rownumbers="true" border="false">
        <e:eventListener event="onLoadError" listener="onGridLoadError"/>
        <e:columns>
            <e:column field="activitId" checkbox="true"/>
            <e:column field="activitName" title="活动名称" align="center" width="20"/>
            <e:column field="activityPromoter" title="发起商" align="center" width="20" formatter="getMerchName"/>
            <e:column field="category" title="活动类别" align="center" width="20" formatter="getCategName"/>
            <e:column field="attributeSet" title="商品类别" align="center" width="20" formatter="getGoodsCategName"/>
            <e:column field="currentState" title="当前状态" align="center" width="10" formatter="getState"/>
            <e:column field="contacterName" title="活动联系人" align="center" width="15"/>
            <e:column field="contactTell" title="联系电话" align="center" width="20"/>
            <e:column field="activitDetail" title="活动介绍" align="center" width="25"/>
        </e:columns>
        <e:facet name="toolbar">
            <e:inputSearch id="gird-searchbox" prompt="输入关键字搜索" style="width:260px;">
                <e:facet name="menu">
                    <div name="activityName">活动名称</div>
                </e:facet>
            </e:inputSearch>
        </e:facet>
    </e:datagrid>
</e:body>
</html>
