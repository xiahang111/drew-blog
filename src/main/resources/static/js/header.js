document.writeln("<!doctype html>");
document.writeln("<html lang=\'zh-CN\'>");
document.writeln("<head>");
document.writeln("    <meta charset=\'utf-8\'>");
document.writeln("    <meta name=\'renderer\' content=\'webkit\'>");
document.writeln("    <meta http-equiv=\'X-UA-Compatible\' content=\'IE=edge\'>");
document.writeln("    <meta name=\'viewport\' content=\'width=device-width, initial-scale=1\'>");
document.writeln("    <title>德鲁大叔的博客</title>");
document.writeln("    <link rel=\'stylesheet\' type=\'text/css\' href=\'css/bootstrap.min.css\'>");
document.writeln("    <link rel=\'stylesheet\' type=\'text/css\' href=\'css/nprogress.css\'>");
document.writeln("    <link rel=\'stylesheet\' type=\'text/css\' href=\'css/style.css\'>");
document.writeln("    <link rel=\'stylesheet\' type=\'text/css\' href=\'css/font-awesome.min.css\'>");
document.writeln("    <link rel=\'apple-touch-icon-precomposed\' href=\'images/icon/icon.png\'>");
document.writeln("    <link rel=\'shortcut icon\' href=\'images/icon/favicon.ico\'>");
document.writeln("    <script src=\'js/jquery-2.1.4.min.js\'></script>");
document.writeln("    <script src=\'js/nprogress.js\'></script>");
document.writeln("    <script src=\'js/jquery.lazyload.min.js\'></script>");
document.writeln("    <script src=\'js/mock.js\'></script>");
document.writeln("");
document.writeln("    <!--[if gte IE 9]>");
document.writeln("    <script src=\'js/jquery-1.11.1.min.js\' type=\'text/javascript\'></script>");
document.writeln("    <script src=\'js/html5shiv.min.js\' type=\'text/javascript\'></script>");
document.writeln("    <script src=\'js/respond.min.js\' type=\'text/javascript\'></script>");
document.writeln("    <script src=\'js/selectivizr-min.js\' type=\'text/javascript\'></script>");
document.writeln("    <![endif]-->");
document.writeln("    <!--[if lt IE 9]>");
document.writeln("    <script>window.location.href=\'upgrade-browser.html\';</script>");
document.writeln("    <![endif]-->");
document.writeln("</head>");
document.writeln("<body>");
document.writeln("");
document.writeln("<header class=\'header\'>");
document.writeln("    <nav class=\'navbar navbar-default\' id=\'navbar\'>");
document.writeln("        <div class=\'container\'>");
document.writeln("            <div class=\'header-topbar hidden-xs link-border\'>");
document.writeln("                <ul class=\'site-nav topmenu\'>");
document.writeln("                    <li><a href=\'./tags.html\' target=\'headFrame\'>标签云</a></li>");
document.writeln("                    <li><a href=\'./readers.html\' rel=\'nofollow\'  target=\'headFrame\'>读者墙</a></li>");
document.writeln("                    <li><a href=\'./links.html\' rel=\'nofollow\'  target=\'headFrame\'>友情链接</a></li>");
document.writeln("                    <li class=\'dropdown\'><a class=\'dropdown-toggle\' data-toggle=\'dropdown\' role=\'button\' aria-haspopup=\'true\' aria-expanded=\'false\' rel=\'nofollow\'>关注本站 <span class=\'caret\'></span></a>");
document.writeln("                        <ul class=\'dropdown-menu header-topbar-dropdown-menu\'>");
document.writeln("                            <li><a data-toggle=\'modal\' data-target=\'#WeChat\' rel=\'nofollow\'  target=\'headFrame\'><i class=\'fa fa-weixin\'></i> 微信</a></li>");
document.writeln("                            <li><a href=\'#\' rel=\'nofollow\'  target=\'headFrame\'><i class=\'fa fa-weibo\'></i> 微博</a></li>");
document.writeln("                            <li><a data-toggle=\'modal\' data-target=\'#areDeveloping\' rel=\'nofollow\'><i class=\'fa fa-rss\'></i> RSS</a></li>");
document.writeln("                        </ul>");
document.writeln("                    </li>");
document.writeln("                </ul>");
document.writeln("                <a data-toggle=\'modal\' data-target=\'#loginModal\' class=\'login\' rel=\'nofollow\'><!--Hi,请登录-->&nbsp;&nbsp;&nbsp;&nbsp;</a>&nbsp;&nbsp;<a href=\'javascript:;\' class=\'register\' rel=\'nofollow\'><!--我要注册-->&nbsp;&nbsp;&nbsp;&nbsp;</a>&nbsp;&nbsp;<a href=\'\' rel=\'nofollow\'><!--找回密码-->&nbsp;&nbsp;&nbsp;&nbsp;</a> </div>");
document.writeln("            <div class=\'navbar-header\'>");
document.writeln("                <button type=\'button\' class=\'navbar-toggle collapsed\' data-toggle=\'collapse\' data-target=\'#header-navbar\' aria-expanded=\'false\'> <span class=\'sr-only\'></span> <span class=\'icon-bar\'></span> <span class=\'icon-bar\'></span> <span class=\'icon-bar\'></span> </button>");
document.writeln("                <h1 class=\'logo hvr-bounce-in\'><a href=\'\' title=\'\'><img src=\'images/Irving.jpeg\' alt=\'\' class=\'img-circle\'><a style=\'font-color:rgb(255, 255, 255);\'>&nbsp;&nbsp;德鲁大叔！</a></a></h1>");
document.writeln("            </div>");
document.writeln("            <div class=\'collapse navbar-collapse\' id=\'header-navbar\'>");
document.writeln("                <ul class=\'nav navbar-nav navbar-right\'>");
document.writeln("                    <li class=\'hidden-index active\'><a data-cont=\' 德鲁大叔首页\' href=\'/index\'>首页</a></li>");
document.writeln("                    <li><a href=\'/summary\'>技术总结</a></li>");
document.writeln("                    <li><a href=\'/data_share\'>个人分享</a></li>");
document.writeln("                    <li><a href=\'/readers\'>Blog留言</a></li>");
document.writeln("                    <li><a href=\'/category\'>关于本人</a></li>");
document.writeln("                    <!--");
document.writeln("                              <li><a href=\'category.html\'>程序人生</a></li>");
document.writeln("                    -->");
document.writeln("                </ul>");
document.writeln("                <form class=\'navbar-form visible-xs\' action=\'/Search\' method=\'post\'>");
document.writeln("                    <div class=\'input-group\'>");
document.writeln("                        <input type=\'text\' name=\'keyword\' class=\'form-control\' placeholder=\'请输入关键字\' maxlength=\'20\' autocomplete=\'off\'>");
document.writeln("                        <span class=\'input-group-btn\'>");
document.writeln("            <button class=\'btn btn-default btn-search\' name=\'search\' type=\'submit\'>搜索</button>");
document.writeln("            </span> </div>");
document.writeln("                </form>");
document.writeln("            </div>");
document.writeln("        </div>");
document.writeln("    </nav>");
document.writeln("</header>");
document.writeln("");
document.writeln("</body>");
document.writeln("</html>");