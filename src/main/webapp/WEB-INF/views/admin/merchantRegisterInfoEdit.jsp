<%--
  Created by IntelliJ IDEA.
  User: YangDaiChun
  Date: 2015/12/11
  Time: 9:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>商户注册</title>
    <c:set var="basePath" value="${pageContext.request.contextPath}"/>
    <script type="text/javascript" src="${basePath}/static/core/app.js"></script>
    <script type="text/javascript">
        app.setBasePath('${basePath}');
        app.setup();
    </script>
    <link rel="stylesheet" href="${basePath}/static/bootstrap/css/bootstrap.css">
    <link rel="stylesheet" href="${basePath}/static/bootstrap/css/bootstrapValidator.css">
    <link rel="stylesheet" href="${basePath}/static/bootstrap/css/bootstrap.min.css">
    <script type="text/javascript" src="${basePath}/static/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${basePath}/static/bootstrap/js/bootstrapValidator.js"></script>
    <script type="text/javascript" src="${basePath}/static/bootstrap/js/bootstrapValidator.min.js"></script>
    <style>
        body {
            font: 400 12px/14px Geneva, "宋体", Tahoma, sans-serif;
            margin: 0px;
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
            border-color: green;
        }

        h2 {
            width: 100%;
            height: 40px;
            margin-left: auto;
            margin-right: auto;
            margin-top: 0px;
            font-size: 18px;
            background-color: green;
            line-height: 40px;
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
            border-color: green;
        }

        legend {
            width: auto;
            height: 20px;
            padding-left: 10px;
            padding-right: 10px;
            line-height: 14px;
            text-align: center;
            font-weight: 400;
            border: none;
        }

        #next_btn, #submit_btn {
            width: 100px;
            height: 40px;
            border-radius: 8px;
            background-color: #ffffff;
            color: #000000;
            border: solid;
            border-width: 1px;
            border-color: green;
        }

        #num {
            font-size: 12px;
            padding: 0px;
            margin-top: -4px;
            height: 18px;
        }
    </style>
    <script type="text/javascript">
        $(function () {
            var examinState = ${requestScope.merchant.examinState};
            if (examinState == 3) {
                $('#edit_title')[0].innerHTML = "商家信息修改（审核已通过）"
                $('#next_btn').hide();
                $('#submit_btn').show();
            } else if (examinState == 2) {
                $('#edit_title')[0].innerHTML = "商家信息修改（等待审核）"
                $('#next_btn').show();
                $('#submit_btn').hide();
            } else {
                $('#edit_title')[0].innerHTML = "商家信息修改（审核未通过）"
                $('#next_btn').show();
                $('#submit_btn').hide();
            }

            $(".registerForm").bootstrapValidator({
                message: 'This value is not valid',
                feedbackIcons: {
                    valid: 'glyphicon glyphicon-ok',
                    invalid: 'glyphicon glyphicon-remove',
                    validating: 'glyphicon glyphicon-refresh'
                },
                fields: {
                    adminCode: {
                        message: '账号无效',
                        validators: {
                            notEmpty: {
                                message: '账号不可以为空'
                            },
                            stringLength: {
                                min: 2,
                                max: 30,
                                message: '长度最少为2个字符，最大为30个字符'
                            },
                            regexp: {
                                regexp: /^[a-zA-Z0-9_]+$/,
                                message: '账号只能由字母，下划线，和数字组成'
                            }
                        }
                    },
                    adminPwd: {
                        message: '密码无效',
                        validators: {
                            notEmpty: {
                                message: '密码不可以为空'
                            },
                            stringLength: {
                                min: 6,
                                max: 50,
                                message: '长度最少为6个字符'
                            },
                        }
                    },
                    merchName: {
                        message: '商家名无效',
                        validators: {
                            notEmpty: {
                                message: '商家名不能为空'
                            },
                            stringLength: {
                                min: 2,
                                max: 30,
                                message: 'The merchName must be more than 6 and less than 30 characters long'
                            }
                        }
                    },
                    merchTell: {
                        message: '无效',
                        validators: {
                            notEmpty: {
                                message: '企业联系电话不能为空'
                            },
                            regexp: {
                                regexp: /^[0-9]{11}$/,
                                message: '输入的不是有效的电话号码'
                            }
                        }
                    },
                    address: {
                        message: '地址无效',
                        validators: {
                            notEmpty: {
                                message: '地址不能为空'
                            },
                        },
                    },
                    alipayCode: {
                        message: '输入的支付宝账号无效',
                        validators: {
                            notEmpty: {
                                message: '支付宝账号不能为空'
                            }
                        }
                    },
                    wxpayCode: {
                        message: '输入的微信账号无效',
                        validators: {
                            notEmpty: {
                                message: '微信支付账号不能为空'
                            }
                        }
                    },
                    orgCode: {
                        message: '企业代码无效',
                        validators: {
                            notEmpty: {
                                message: '企业代码不能为空值'
                            }
                        }
                    },
                    licenseCode: {
                        message: '工商执照注册码无效',
                        validators: {
                            notEmpty: {
                                message: '工商执照注册码不能为空'
                            }
                        }
                    },
                    taxRegCode: {
                        message: '税务登记证号无效',
                        validators: {
                            notEmpty: {
                                message: '税务登记证号不能为空'
                            }
                        }
                    },
                    businScope: {
                        message: '所填值无效',
                        validators: {
                            notEmpty: {
                                message: '经营范围不能为空'
                            }
                        }
                    },
                    contactEmail: {
                        message: '邮箱地址无效',
                        validators: {
                            notEmpty: {
                                message: '联系人电子邮箱不能为空'
                            }
                        }
                    },
                    contactID: {
                        message: '联系人身份证号无效',
                        validators: {
                            notEmpty: {
                                message: '联系人身份证号不能为空'
                            }
                        }
                    },
                    contactMobileNO: {
                        message: '电话号码无效',
                        validators: {
                            notEmpty: {
                                message: '联系人手机号码不能为空'
                            }
                        }
                    },
                    contactName: {
                        message: '企业联系人姓名无效',
                        validators: {
                            notEmpty: {
                                message: '企业联系人姓名不能为空'
                            }
                        }
                    },
                    contactPartAndPositon: {
                        message: '填入的值无效',
                        validators: {
                            notEmpty: {
                                message: '联系人部门与职位不能为空'
                            }
                        }
                    },
                    emplyCount: {
                        message: '填入的值无效',
                        validators: {
                            notEmpty: {
                                message: '此项不能为空'
                            }
                        }
                    },
                }
            });

            $("#num").change(function () {
                var values = $(this).val();
                var el = $(this).parent().next();
                var str = ['支付宝账号', 'wxpayCode', 'alipayCode'];
                el.attr("id", (values != str[0]) ? str[1] : str[2]);
                el.attr("name", (values != str[0]) ? str[1] : str[2]);
                $(this).parent().attr("for", el[0].name);
            });


            $('#submit_btn').click(function () {
                $('#info_form').form('submit', {
                    url: '${basePath}/admin/api/merchant/editMerchantInfoForm',
                    success: function (data) {
                        data = $.parseJSON(data);
                        $.messager.show({
                            title: '提示',
                            msg: data.msg
                        });
                    }
                });
            });
        });
    </script>
</head>
<body>
<div class="container">
    <h2 id="edit_title"></h2>
    <!--基本信息表单-->
    <form role="form" class="registerForm" id="info_form"
          action="${basePath}/admin/api/merchant/editMerchantRegisterInfoForm" method="post">
        <input type="hidden" name="merchantId" value="${requestScope.merchant.merchantId}">
        <input type="hidden" name="examinState" value="${requestScope.merchant.examinState}">
        <!--商家基本信息-->
        <fieldset style="margin-top: 30px">
            <legend><span style="font-size:14px;color: green;font-weight: bold">商家基本信息</span></legend>
            <div class="row">
                <div class="col-md-5">
                    <div class="form-group">
                        <label for="merchName"><span style="color: red;font-weight: normal">* </span>商家名称</label>
                        <input type="text" class="form-control" id="merchName" name="merchName"
                               value="${requestScope.merchant.merchName}">
                    </div>
                </div>
                <div class="col-md-5">
                    <div class="form-group">
                        <label for="merchTell"><span style="color: red;font-weight: normal">* </span>企业联系电话</label>
                        <input type="text" class="form-control" id="merchTell" name="merchTell"
                               value="${requestScope.merchant.merchTell}">
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-5">
                    <div class="form-group">
                        <label for="address"><span style="color: red;font-weight: normal">* </span>详细地址</label>
                        <input type="text" class="form-control" id="address" name="address"
                               value="${requestScope.merchant.address}">
                    </div>
                </div>
                <div class="col-md-5">
                    <div class="form-group">
                        <label for="alipayCode">
                            <select id="num" class="form-control" style="font-size: 12px">
                                <option>支付宝账号</option>
                                <option>微信账号</option>
                            </select>
                        </label>

                        <input type="text" class="form-control" id="alipayCode" name="alipayCode"
                               value="${requestScope.merchant.alipayCode}">
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-5">
                    <div class="form-group">
                        <label for="orgCode"><span style="color: red;font-weight: normal">* </span>组织机构代码</label>
                        <input type="text" class="form-control" id="orgCode" name="orgCode"
                               value="${requestScope.merchant.orgCode}">
                    </div>
                </div>
                <div class="col-md-5">
                    <div class="form-group">
                        <label for="licenseCode"><span style="color: red;font-weight: normal">* </span>工商执照注册码</label>
                        <input type="text" class="form-control" id="licenseCode" name="licenseCode"
                               value="${requestScope.merchant.licenseCode}">
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-5">
                    <div class="form-group">
                        <label for="taxRegCode"><span style="color: red;font-weight: normal">* </span>税务登记证号</label>
                        <input type="text" class="form-control" id="taxRegCode" name="taxRegCode"
                               value="${requestScope.merchant.taxRegCode}">
                    </div>
                </div>
                <div class="col-md-5">
                    <div class="form-group">
                        <label for="businScope"><span style="color: red;font-weight: normal">* </span>经营范围</label>
                        <input type="text" class="form-control" id="businScope" name="businScope"
                               value="${requestScope.merchant.businScope}">
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-5">
                    <div class="form-group">
                        <label for="emplyCount"><span style="color: red;font-weight: normal">* </span>企业规模</label>
                        <input type="number" class="form-control" id="emplyCount" name="emplyCount"
                               value="${requestScope.merchant.emplyCount}">
                    </div>
                </div>
            </div>
        </fieldset>
        <!--商家详细信息开始-->
        <fieldset>
            <legend><span style="font-size:14px;color: green;font-weight: bold">详细信息</span></legend>
            <div class="row">
                <div class="col-md-5">
                    <div class="form-group" style="width: 200%">
                        <label for="merchantDetails"><span style="color: red;font-weight: normal">* </span>商家简介</label>
                         <textarea class="form-control" rows="3" id="merchantDetails" name="merchantDetails"
                                   placeholder="输入商家的详细介绍">
                             ${requestScope.merchant.merchantDetails}
                         </textarea>
                    </div>
                </div>
            </div>
        </fieldset>
        <!--商家详细信息结束-->
        <!--联系人信息-->
        <fieldset>
            <legend><span style="font-size:14px;color: green;font-weight: bold">联系人信息</span></legend>
            <div class="row">
                <div class="col-md-5">
                    <div class="form-group">
                        <label for="contactName"><span style="color: red;font-weight: normal">* </span>联系人</label>
                        <input type="text" class="form-control" id="contactName" name="contactName"
                               value="${requestScope.merchant.contactName}">
                    </div>
                </div>
                <div class="col-md-5">
                    <div class="form-group">
                        <label for="contactID"><span style="color: red;font-weight: normal">* </span>身份证号</label>
                        <input type="text" class="form-control" id="contactID" name="contactID"
                               value="${requestScope.merchant.contactID}">
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-5">
                    <div class="form-group">
                        <label for="contactMobileNO"><span style="color: red;font-weight: normal">* </span>手机号码</label>
                        <input type="text" class="form-control" id="contactMobileNO" name="contactMobileNO"
                               value="${requestScope.merchant.contactMobileNO}">
                    </div>
                </div>
                <div class="col-md-5">
                    <div class="form-group">
                        <label for="contactEmail"><span style="color: red;font-weight: normal">* </span>邮箱</label>
                        <input type="text" class="form-control" id="contactEmail" name="contactEmail"
                               value="${requestScope.merchant.contactEmail}">
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-5">
                    <div class="form-group">
                        <label for="contactPartAndPositon"><span style="color: red;font-weight: normal">* </span>联系人部门与职位</label>
                        <input type="text" class="form-control" id="contactPartAndPositon"
                               name="contactPartAndPositon"
                               value="${requestScope.merchant.contactPartAndPositon}">
                    </div>
                </div>

            </div>
            <input type="submit" id="next_btn" value="下一步"/>
            <button id="submit_btn">修改</button>
        </fieldset>
    </form>
</div>
</body>
</html>
