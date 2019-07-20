var articleId = "";

$(document).ready(function () {

    $.ajax({
        type: 'HEAD', // 获取头信息，type=HEAD即可
        url : window.location.href,
        async:false,
        success:function (data, status, xhr) {
            articleId = xhr.getResponseHeader("articleId");
        }
    });
    
    $.getJSON('/article/getHotArticle',{"articleId":articleId},function (hotResult) {

        var html_hot = '<ul>';

        var html_hotResultInfo='';

        var html_recommend = '<ul>';

        $.each(hotResult["data"],function (i,item1) {


            html_hotResultInfo += '<li><a href="/article/'+item1['articleInfoId']+
                '"><span class="thumbnail"><img class="thumb" data-original="'+item1['articleImgUrl']+
                '" src="http://106.52.216.137:8081/'+item1['articleImgUrl']+'" alt=""></span><span class="text">'+item1['articleHeadline']+
                '</span><span class="muted"><i class="glyphicon glyphicon-time"></i> '+ item1['articleDate'] +
                ' </span><span class="muted"><i class="glyphicon glyphicon-eye-open"></i>'+item1['articleVisitor']+'</span></a></li>';

            html_recommend += '<li><a href="/article/'+item1['articleInfoId']+'">'+item1['articleHeadline']+'</a></li>'

        });

        html_recommend += '</ul>';

        var html_result = html_hot + html_hotResultInfo + '</ul>';

        $('#hot_title').append(html_result);

        $('.title-recommend').append(html_recommend);


    });



    $.getJSON('/sentence/getToday',function (everydayResult1) {

       /* var  everydayResult = everydayResult1['data'][0];*/

        /*var html_everyday = '<h4>'+everydayResult['year']+'年'+everydayResult['month']
            +'月'+everydayResult['day']+'日星期'+everydayResult['week']+'</h4>' +
            '<p>'+everydayResult['sentence']+'</p>';*/
        /*var html_everyday = '<h4> 2018 年 06月 19日 </h4><p> 天生我材必有用！</p>'*/


       /* $('.widget-sentence-content').append(html_everyday);*/

        var  everydayResult = everydayResult1['data'];

        var html_everyday = '<h4>'+everydayResult['showTime']+'</h4>' +
            '<p style="font-size: large">'+everydayResult['content']+'</p>';

        $('.widget-sentence-content').append(html_everyday);

    });

});

















