$(document).ready(function () {

    $.getJSON('/discussion/getHot',function (result) {

        var html_discussion = "<ul>";

        $.each(result["data"],function (i,item) {

            html_discussion += '<li><a href=""><div class="col-md-2" style="padding-left: 5px">' +
                '<div class="common-avatar">' + '<img src="'+item['avatarUrl']+'" width="100%" height="100%"/>' + '</div></div>'+
                '<div class="col-md-10" style="padding-left: 0px">'+
                '<span class="hot-comment-name">'+item['nickName']+'：</span>'+
                '<span>'+item['discussion']+'</span>'+'</div></a></li>'

        });
        html_discussion += '</ul>';

        $('#hot_comment').append(html_discussion);
    });

});