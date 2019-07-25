$(document).ready(function () {

    $.getJSON('/article/getArticles',function (result) {


        var html_title='';

        $.each(result["data"],function (i,item) {

            var html_resultinfo='';
            html_resultinfo += '<article class="excerpt excerpt-1">' + '<a class="focus" href="article/'+item['articleInfoId']+'" title="">' +
                '<img class="thumb" data-original="http://106.52.216.137:8081/'+item['articleImgUrl']+'" src="http://106.52.216.137:8081/'+item['articleImgUrl']+'" alt=""></a>'+
                '<header><a class="cat" href="/article/'+item['articleInfoId']+'">'+item['articleCategoryName']+'<i></i></a>' +
                '<h2><a href="/article/'+item['articleInfoId']+'" title="">'+item['articleHeadline']+'</a></h2></header>'+
                '<p class="meta">' + '<time class="time"><i class="glyphicon glyphicon-time"></i> '+item['articleDate']+'</time>' +
                '<span class="views"><i class="glyphicon glyphicon-eye-open"></i> 共'+item['articleVisitor']+'人围观</span>'+
                '<a class="comment" href=""><i class="glyphicon glyphicon-comment"></i> '+item['articleCommentsNum']+'个不明物体</a></p>'+
                '<p class="note">'+ cleanContent(item['articleContent']) +' </p>'+
                ' </article>'

            $('#index_title').append(html_resultinfo);

        });



    });

});


function cleanContent(str) {

    var result = str.replace(/<[^>]+>/g,"").replace(/&nbsp;/ig,"");//去掉所有的html标记

    return (result.substring(0,135) + "...");
    
}