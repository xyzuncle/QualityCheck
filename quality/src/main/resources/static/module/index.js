/** EasyWeb spa v3.0.3 data:2018-11-19 */

layui.define(['layer', 'config', 'admin', 'laytpl', 'element', 'form'], function (exports) {
    var $ = layui.jquery;
    var layer = layui.layer;
    var config = layui.config;
    var admin = layui.admin;
    var element = layui.element;
    var form = layui.form;

    var index = {
        // 使用递归循环注册
        regRouter: function (menus) {
            $.each(menus, function (i, data) {
                if (data.url && data.url.indexOf('#!') == 0) {
                    Q.reg(data.url.substring(2), function () {
                        var menuId = data.url.substring(2);
                        index.loadView({
                            menuId: menuId,
                            menuPath: data.path,
                            menuName: data.name
                        });
                    });
                }
                if (data.subMenus) {
                    index.regRouter(data.subMenus);
                }
            });
        },
        // 路由加载组件
        loadView: function (param) {
            var menuId = param.menuId;
            var menuPath = param.menuPath;
            var menuName = param.menuName;
            var contentDom = '.layui-layout-admin .layui-body';
            var flag;  // 选项卡是否添加
            var isHttp = (menuPath.indexOf('http://') == 0 || menuPath.indexOf('https://') == 0);
            // 判断是否开启了选项卡功能
            if (config.pageTabs) {
                $('.layui-layout-admin .layui-body .layui-tab .layui-tab-title>li').each(function (index) {
                    if ($(this).attr('lay-id') === menuId) {
                        flag = true;
                        return false;
                    }
                });
                if (!flag) {
                    element.tabAdd('admin-pagetabs', {
                        title: menuName,
                        content: '<div id="' + menuId + '"></div>',
                        id: menuId
                    });
                }
                contentDom = '#' + menuId;
                element.tabChange('admin-pagetabs', menuId);
                admin.rollPage('auto');
                // 切换tab关闭表格内浮窗
                $('.layui-table-tips-c').trigger('click');
                $(window).resize();
            }
            if (!flag || admin.isRefresh) {
                if (isHttp) {
                    $(contentDom).html('<iframe class="admin-iframe" frameborder="0" src="' + menuPath + '"></iframe>');
                    admin.isRefresh = false;
                } else {
                    admin.showLoading($(contentDom).parent());
                    $(contentDom).load('components/' + menuPath, function () {
                        admin.isRefresh = false;
                        setTimeout(function () {
                            admin.removeLoading($(contentDom).parent());
                        }, 300);
                    });
                }

            }
            admin.activeNav(Q.lash);
            // 移动设备切换页面隐藏侧导航
            if (admin.getPageWidth() <= 750) {
                admin.flexible(true);
            }
        },
        // 打开新页面
        openNewTab: function (param) {
            var url = param.url;
            var title = param.title;
            var menuId = param.menuId;
            if (!menuId) {
                menuId = url.replace(/[?:=&/]/g, '_');
            }
            Q.reg(menuId, function () {
                index.loadView({
                    menuId: menuId,
                    menuPath: url,
                    menuName: title
                });
            });
            Q.go(menuId);
            return menuId;
        },
        // 关闭选项卡
        closeTab: function (menuId) {
            menuId = menuId.replace(/[?:=&/]/g, '_');
            element.tabDelete('admin-pagetabs', menuId);
        }
    };

    // tab选项卡切换监听
    element.on('tab(admin-pagetabs)', function (data) {
        var layId = $(this).attr('lay-id');
        Q.go(layId);
    });

    // 监听侧导航栏点击事件
    element.on('nav(admin-side-nav)', function (elem) {
        var $that = $(elem);
        if ($('.layui-side .layui-nav-tree').attr('lay-accordion') == 'true' && $that.parent().hasClass('layui-nav-item')) {
            if ($that.parent().hasClass('layui-nav-itemed') || $that.parent().hasClass('layui-this')) {
                $('.layui-layout-admin .layui-side .layui-nav .layui-nav-item').removeClass('layui-nav-itemed');
                $that.parent().addClass('layui-nav-itemed');
            }
        }
    });

    // 是否开启footer
    var openFooter = layui.data(config.tableName).openFooter;
    if (openFooter != undefined && openFooter == false) {
        $('.layui-layout-admin .layui-footer').css('display', 'none');
        $('.layui-layout-admin .layui-body').css('bottom', '0');
    }

    exports('index', index);
});
