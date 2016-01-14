/**
 * Created by ShenYunjuie on 2015/11/18.
 */
(function (win) {
    var app = {};
    app.setBasePath = function (basePath) {
        app.basePath = basePath;
    };
    app.setup = function () {
        document.writeln('<link rel="stylesheet" type="text/css" href="' + app.basePath + '/static/easyui/themes/default/easyui.css" />');
        document.writeln('<link rel="stylesheet" type="text/css" href="' + app.basePath + '/static/easyui/themes/icon.css" />');
        document.writeln('<link rel="stylesheet" type="text/css" href="' + app.basePath + '/static/css/style.css" />');
        document.writeln('<link rel="stylesheet" type="text/css" href="' + app.basePath + '/static/css/icon.css" />');
        //document.writeln('<link rel="stylesheet" type="text/css" href="' + app.basePath + '/static/bootstrap/css/bootstrap.css" />');
        document.writeln('<script type="text/javascript" src="' + app.basePath + '/static/core/jquery.min.js" charset="UTF-8" ></script>');
        document.writeln('<script type="text/javascript" src="' + app.basePath + '/static/easyui/jquery.easyui.min.js" charset="UTF-8" ></script>');
        document.writeln('<script type="text/javascript" src="' + app.basePath + '/static/easyui/locale/easyui-lang-zh_CN.js" charset="UTF-8" ></script>');
        //document.writeln('<link rel="shortcut icon" href="'+app.basePath+'/img/favicon.ico">');
    };
    win.app = app;
})(window);
