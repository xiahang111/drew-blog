/*本站留言*/
$(function(){
    $("#comment-submit").click(function(){
        var commentContent = $("#comment-textarea");
        var commentButton = $("#comment-submit");
        var promptBox = $('.comment-prompt');
        var promptText = $('.comment-prompt-text');
        var articleid = $('.articleid').val();
        promptBox.fadeIn(400);
        if(commentContent.val() === ''){
            promptText.text('请留下您的评论');
            return false;
        }
        commentButton.attr('disabled',true);
        commentButton.addClass('disabled');
        promptText.text('正在提交...');
        $.ajax({
            type:"POST",
            url:"/discussion/add",
            //url:"/Article/comment/id/" + articleid,
            data:"discussion=" + replace_em(commentContent.val()),
            cache:false, //不缓存此页面
            success:function(data){
                promptText.text('评论成功!');
                commentContent.val(null);
                $(".commentlist").fadeIn(300);
                /*$(".commentlist").append();*/
                commentButton.attr('disabled',false);
                commentButton.removeClass('disabled')
                window.location.reload();
            }
        });
        /*$(".commentlist").append(replace_em(commentContent.val()));*/
        promptBox.fadeOut(100);
        return false;
    });

});

//对文章内容进行替换
function replace_em(str){
    str = str.replace(/\</g,'&lt;');
    str = str.replace(/\>/g,'&gt;');
    str = str.replace(/\[em_([0-9]*)\]/g,'<img src="/static/images/arclist/$1.gif" border="0" />');
    return str;
}

function publish_cmt() {
    cmt_text = $('#J_Textarea').val();
    alert(cmt_text);
    jQuery.ajax({
        url: '/getConmts',
        type: 'post',
        data: {},
        success: function (args) {
            console.log(args);
            jQuery(ths).parent().next().children('.loading-ico-top').css('display', 'none');
            jQuery(ths).parent().next().children('.conment-list').css('display', 'block');
        }
    })

}

function replay_show(ths) {
    if ($(ths).parent().next().hasClass('hidden')) {
        $(ths).parent().next().removeClass('hidden');
        $(ths).text('收起');
        $(ths).addClass('operate-visited')

    } else {
        $(ths).parent().next().addClass('hidden');
        $(ths).text('回复');
        $(ths).removeClass('operate-visited')
    }

}

function cmt_reply(ths) {
    // alert($(ths).attr('cmt_id'));
    // console.log($(ths).prev().children('.reply-box-textarea').val());

    var discussionId = $(ths).attr('discussion_id');
    var replyBox =  $(ths).prev().children('.reply-box-textarea').val();

    $.ajax({
        type:"POST",
        url:"/discussion/add",
        //url:"/Article/comment/id/" + articleid,
        data:{discussion:replace_em(replyBox),parentId:discussionId},
        // data:"discussion=" + replace_em(replyBox.val()),"discussionId="+discussionId,
        cache:false, //不缓存此页面
        success:function(data){
            // promptText.text('评论成功!');
            // commentContent.val(null);
            // $(".commentlist").fadeIn(300);
            /*$(".commentlist").append();*/
            // commentButton.attr('disabled',false);
            // commentButton.removeClass('disabled')
            window.location.reload();
        }
    });

}

$(document).ready(function () {

    /*获取评论并展示*/
    $.getJSON('/discussion/getAll', function (result) {

        $.each(result["data"], function (i, item) {

            var html_discussion = '';

            var html_reply = '';

            if(item['articleReplyDTOS'] != null&&item['articleReplyDTOS'] != undefined&&item['articleReplyDTOS'] !=""){

                html_reply += '<div class="reply J_ReplyBlock"><div class="J_ReplyLatest"> <div class="reply-title"><p class="reply-title-text">更多回复</p>'+
                    '<span class="reply-title-mark"></span><div class="reply-title-line"> <span></span></div></div><div class="J_ReplyLatestBlock">'

                $.each(item['articleReplyDTOS'],function (j,reply) {

                    html_reply += '<div class="reply-block">'+
                        '<p class="reply-content"><span class="reply-user">'+reply['nickName']+' :&nbsp;</span> '+reply['reply']+' </p>'+
                        '<div class="reply-operate" data-id="6337676401185313214" data-targetid="2234842266">'+
                        '<span>'+reply['createTime']+'</span>'+
                        ' <i class="reply-dot reply-operate-report-dot">&middot;</i>'+
                        '</div></div>'

                });

                html_reply += '</div></div></div>'
            }




            html_discussion = '<div class="comment" comment-id="">' +
                '<div class="common-avatar J_User" data-userid="15850091">'+
                '<img src="'+item['avatarUrl']+'" width="100%" height="100%"/></div>'+
                '<div class="comment-block" comment-id="J_CommentBlock6337232328259750900">'+
                '<p class="comment-user">' + '来自' +
                '<span class="comment-username J_User" > '+ item['address']+' </span>'+ '的'+
                '<span class="comment-username J_User" data-userid="15850091">'+item['nickName']+' </span> <span class="comment-time">'+item['createTime']+'</span></p>'+
                '<div class="comment-content J_CommentContent">'+item['discussion']+'</div>'+html_reply+
                '<div class="comment-operate J_CommentOperate clearfix" data-targetid="2234842266" data-id="6337232328259750900">'+
                // '<span class="J_Vote comment-operate-item comment-operate-up">赞<i>16</i></span>'+
                '<span class="J_Reply comment-operate-item comment-operate-reply J_ReplyVisited" onclick="replay_show(this);">匿名回复</span></div>'+
                '<div class="reply-box J_ReplyBox hidden" >'+
                '<div class="reply-box-block"> <textarea class="reply-box-textarea J_ReplyTextArea"></textarea></div>'+
                '<div class="reply-box-btn J_ReplyBtn" discussion_id="'+item['discussionId']+'" onclick="cmt_reply(this);" data-targetid="2234842266" data-id="6337232328259750900" data-nick="" data-userid="">回复</div>'+
                '</div>'+
                '</div>'+
                '</div>'

            $('#postcomments').append(html_discussion);

        });


    });
});