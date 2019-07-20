package com.drew.item.dto;

import com.drew.item.pojo.DrewDiscussion;
import com.drew.utils.DateUtils;


public class ArticleReplyDTO {

    private Long parentId;
    private String nickName;
    private String avatarUrl;
    private String address;
    private int praiseNum;
    private String createTime;
    private String reply;

    public ArticleReplyDTO(){}

    public ArticleReplyDTO(DrewDiscussion drewDiscussion){

        this.reply = drewDiscussion.getDiscussion();
        this.parentId = drewDiscussion.getParentId();
        this.nickName = drewDiscussion.getNickName();
        this.avatarUrl = drewDiscussion.getAvatarUrl();
        this.address = drewDiscussion.getAddress();
        this.praiseNum = drewDiscussion.getPraiseNum();
        this.createTime = DateUtils.dateToString(drewDiscussion.getCreateTime(),DateUtils.DATE_FORMAT);

    }
    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPraiseNum() {
        return praiseNum;
    }

    public void setPraiseNum(int praiseNum) {
        this.praiseNum = praiseNum;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }
}
