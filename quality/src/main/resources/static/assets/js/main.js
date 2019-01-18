/** EasyWeb spa v3.0.4 data:2018-12-04 */

layui.config({
    base: 'module/'
}).extend({
    formSelects: 'formSelects/formSelects-v4',
    treetable: 'treetable-lay/treetable',
    dropdown: 'dropdown/dropdown',
    notice: 'notice/notice',
    step: 'step-lay/step',
    dtree: 'dtree/dtree',
    citypicker: 'city-picker/city-picker',
    tableSelect: 'tableSelect/tableSelect',
    treeSelect:'treeSelect/treeSelect',
    iconPicker:'iconPicker/iconPicker',
    authtree:'authtree/authtree'
}).use(['layer', 'element', 'config', 'index', 'admin', 'laytpl','layRouter'], function () {
    var $ = layui.jquery;
    var layer = layui.layer;
    var element = layui.element;
    var config = layui.config;
    var index = layui.index;
    var admin = layui.admin;
    var laytpl = layui.laytpl;
    var layRouter = layui.layRouter;

    // 检查是否登录
   /* if (!config.getToken()) {
        return location.replace('components/login/login.html');
    }*/

    // 获取用户信息
    admin.req('QualityUser/userInfo.do', {}, function (data) {
        if (200 == data.code) {
            config.putUser(data.user);
            $('#huName').text(data.user.userName);
        } else {
            layer.msg('获取用户失败', {icon: 2});
        }
    }, 'get');


    // 获取当前用户的菜单权限
    admin.req('QualityMenu/getQualityMenuBtn.do', {}, function (data) {
        if (200 == data.status) {
            if(data.data){
                config.putAuthorities(data.data);
            }
        } else {
            layer.msg('获取当前用户菜单权限失败', {icon: 2});
        }
    }, 'get');


    // 加载侧边栏
    admin.req('QualityMenu/getQualityMenuTree.do', {}, function (data) {
        laytpl(sideNav.innerHTML).render(data, function (html) {
            $('.layui-layout-admin .layui-side .layui-nav').html(html);
            element.render('nav');
        });
        index.regRouter(data);  // 注册路由
        index.loadHome({  // 加载主页
            url: '#/console/welcome',
            name: '<i class="layui-icon layui-icon-home"></i>'
        });
    }, 'get');

    // 退出登录
    $('#btnLogout').click(function () {
        layer.confirm('确定退出登录？', {
            skin: 'layui-layer-admin'
        }, function () {
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
        //单页面注册路由
        var data ={title:"个人信息",url:"#/template/user-info"}
        index.openNewTab(data)
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
        // 提示
      /*  if (!config.pageTabs) {
            layer.confirm('SPA版本默认关闭多标签功能，你可以在设置界面开启', {
                skin: 'layui-layer-admin',
                area: '280px',
                title: '温馨提示',
                shade: 0,
                btn: ['打开设置', '知道了']
            }, function (i) {
                layer.close(i);
                $('a[ew-event="theme"]').trigger('click');
            });
        }*/
    }, 500);
});

// 加载主题
function setTheme(theme) {
    layui.jquery('link[id^=layuicss-assetscsstheme]').remove();
    if (theme) {
        layui.link('./assets/css/theme/' + theme + '.css');
    }
}