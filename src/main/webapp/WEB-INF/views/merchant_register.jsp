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
    <script type="text/javascript" src="${basePath}/static/easyui/jquery.easyui.min.js"></script>
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
            padding: 0px;
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

        #next-btn {
            width: 100px;
            height: 40px;
            border-radius: 8px;
            background-color: #ffffff;
            color: #000000;
            border: solid;
            border-width: 1px;
            border-color: #2d71b8
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
        });
    </script>

</head>
<body>
<div class="container">
    <h2>商家注册</h2>
    <!--基本信息表单-->
    <form role="form" class="registerForm" action="${basePath}/merchant/regist/infoForm" method="post">
        <!--注册账号-->
        <fieldset style="margin-top: 30px">
            <legend><span style="font-size:20px;color: #2d71b8;font-weight: bold">注册账号</span></legend>
            <div class="row">
                <div class="col-md-5">
                    <div class="form-group">
                        <label for="adminCode"><span style="color: red;font-weight: normal">* </span>账号</label>
                        <input type="text" class="form-control" id="adminCode" name="adminCode"
                               placeholder="请输入名称">
                    </div>
                </div>
                <div class="col-md-5">
                    <div class="form-group">
                        <label for="adminPwd"><span style="color: red;font-weight: normal">* </span>密码</label>
                        <input type="text" class="form-control" id="adminPwd" name="adminPwd"
                               placeholder="请输入名称">
                    </div>
                </div>
            </div>
        </fieldset>
        <!--商家基本信息-->
        <fieldset>
            <legend><span style="font-size:20px;color: #2d71b8;font-weight: bold">商家基本信息</span></legend>
            <div class="row">
                <div class="col-md-5">
                    <div class="form-group">
                        <label for="merchName"><span style="color: red;font-weight: normal">* </span>商家名称</label>
                        <input type="text" class="form-control" id="merchName" name="merchName"
                               placeholder="请输入名称">
                    </div>
                </div>
                <div class="col-md-5">
                    <div class="form-group">
                        <label for="merchTell"><span style="color: red;font-weight: normal">* </span>企业联系电话</label>
                        <input type="text" class="form-control" id="merchTell" name="merchTell"
                               placeholder="请输入名称">
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-5">
                    <div class="form-group">
                        <label for="address"><span style="color: red;font-weight: normal">* </span>详细地址</label>
                        <input type="text" class="form-control" id="address" name="address"
                               placeholder="请输入名称">
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
                               placeholder="请输入名称">
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-5">
                    <div class="form-group">
                        <label for="orgCode"><span style="color: red;font-weight: normal">* </span>组织机构代码</label>
                        <input type="text" class="form-control" id="orgCode" name="orgCode"
                               placeholder="请输入名称">
                    </div>
                </div>
                <div class="col-md-5">
                    <div class="form-group">
                        <label for="licenseCode"><span style="color: red;font-weight: normal">* </span>工商执照注册码</label>
                        <input type="text" class="form-control" id="licenseCode" name="licenseCode"
                               placeholder="请输入名称">
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-5">
                    <div class="form-group">
                        <label for="taxRegCode"><span style="color: red;font-weight: normal">* </span>税务登记证号</label>
                        <input type="text" class="form-control" id="taxRegCode" name="taxRegCode"
                               placeholder="请输入名称">
                    </div>
                </div>
                <div class="col-md-5">
                    <div class="form-group">
                        <label for="businScope"><span style="color: red;font-weight: normal">* </span>经营范围</label>
                        <input type="text" class="form-control" id="businScope" name="businScope"
                               placeholder="请输入名称">
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-5">
                    <div class="form-group">
                        <label for="emplyCount"><span style="color: red;font-weight: normal">* </span>企业规模</label>
                        <input type="number" class="form-control" id="emplyCount" name="emplyCount"
                               placeholder="请输入名称">
                    </div>
                </div>
            </div>
        </fieldset>
        <!--商家详细信息开始-->
        <fieldset>
            <legend><span style="font-size:20px;color: #2d71b8;font-weight: bold">详细信息</span></legend>
            <div class="row">
                <div class="col-md-5">
                    <div class="form-group" style="width: 200%">
                        <label for="merchantDetails"><span style="color: red;font-weight: normal">* </span>商家简介</label>
                    <textarea class="form-control" rows="3" id="merchantDetails" name="merchantDetails"
                              placeholder="输入商家的详细介绍"></textarea>
                    </div>
                </div>
            </div>
        </fieldset>
        <!--商家详细信息结束-->
        <!--联系人信息-->
        <fieldset>
            <legend><span style="font-size:20px;color: #2d71b8;font-weight: bold">联系人信息</span></legend>
            <div class="row">
                <div class="col-md-5">
                    <div class="form-group">
                        <label for="contactName"><span style="color: red;font-weight: normal">* </span>联系人</label>
                        <input type="text" class="form-control" id="contactName" name="contactName"
                               placeholder="请输入名称">
                    </div>
                </div>
                <div class="col-md-5">
                    <div class="form-group">
                        <label for="contactID"><span style="color: red;font-weight: normal">* </span>身份证号</label>
                        <input type="text" class="form-control" id="contactID" name="contactID"
                               placeholder="请输入名称">
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-5">
                    <div class="form-group">
                        <label for="contactMobileNO"><span style="color: red;font-weight: normal">* </span>手机号码</label>
                        <input type="text" class="form-control" id="contactMobileNO" name="contactMobileNO"
                               placeholder="请输入名称">
                    </div>
                </div>
                <div class="col-md-5">
                    <div class="form-group">
                        <label for="contactEmail"><span style="color: red;font-weight: normal">* </span>邮箱</label>
                        <input type="text" class="form-control" id="contactEmail" name="contactEmail"
                               placeholder="请输入名称">
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-5">
                    <div class="form-group">
                        <label for="contactPartAndPositon"><span style="color: red;font-weight: normal">* </span>联系人部门与职位</label>
                        <input type="text" class="form-control" id="contactPartAndPositon"
                               name="contactPartAndPositon"
                               placeholder="请输入名称">
                    </div>
                </div>

            </div>
            <input id="next-btn" type="submit" value="下一步"/>
        </fieldset>
    </form>
</div>
</body>
</html>
