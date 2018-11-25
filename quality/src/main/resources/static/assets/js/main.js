/** EasyWeb spa v3.0.3 data:2018-11-19 */

layui.config({
    base: 'module/'
}).extend({
    formSelects: 'formSelects/formSelects-v4',
    treetable: 'treetable-lay/treetable',
    dropdown: 'dropdown/dropdown',
    notice: 'notice/notice',
    step: 'step-lay/step',
    treeSelect:'treeSelect/treeSelect',
    iconPicker:'iconPicker/iconPicker',
    authtree:'authtree/authtree'
}).use(['layer', 'element', 'config', 'index', 'admin', 'laytpl'], function () {
    var $ = layui.jquery;
    var layer = layui.layer;
    var element = layui.element;
    var config = layui.config;
    var index = layui.index;
    var admin = layui.admin;
    var laytpl = layui.laytpl;

   /* // 检查是否登录
    if (!config.getToken()) {
        return location.replace('login.html');
    }*/

    // 多标签加载主页
    if (config.pageTabs) {
        element.tabAdd('admin-pagetabs', {
            title: '<i class="layui-icon layui-icon-home"></i>',
            content: '<div id="console"></div>',
            id: 'console'
        });
        $('#console').load('components/console/console.html');
    }

    // 获取用户信息
    admin.req('QualityUser/userInfo.do', {}, function (data) {
        if (200 == data.code) {
            if(data.user){
                config.putUser(data.user);
                $('#huName').text(data.user.userName);
            }

        } else {
            layer.msg('获取用户失败', {icon: 2});
        }
    }, 'get');

    // 加载侧边栏
    admin.req('QualityMenu/getQualityMenuTree.do', {}, function (data) {
    laytpl(sideNav.innerHTML).render(data, function (html) {
            $('.layui-layout-admin .layui-side .layui-nav').html(html);
            element.render('nav');
            admin.activeNav(Q.lash);
        });
        // 注册路由
        index.regRouter(data);
        Q.init({
            index: 'console'
        });
    }, 'get');

    // 退出登录
    $('#btnLogout').click(function () {
        layer.confirm('确定退出登录？', function () {
            config.removeToken();
            $.ajax({
                url: config.base_server + 'logout',
                type:"get",
                success:function(){
                    location.reload();
                }
            })

        });
    });

    // 修改密码
    $('#setPsw').click(function () {
        admin.open({
            title: '修改密码',
            url: 'components/tpl/password.html'
        });
    });

    // 个人信息
    $('#setInfo').click(function () {

    });

    // 消息
    $('#btnMessage').click(function () {
        admin.popupRight({url: 'components/tpl/message.html'});
    });

    // 加载设置的主题
    var theme = layui.data(config.tableName).theme;
    setTheme(theme);

    // 移除loading动画
    setTimeout(function () {
        $('.page-loading').remove();
    }, 500);
});

// 加载主题
function setTheme(theme) {
    layui.jquery('link[id^=layuicss-assetscsstheme]').remove();
    if (theme) {
        layui.link('./assets/css/theme/' + theme + '.css');
    }
}