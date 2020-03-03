package com.squadro.touricity.message.types.data;

public class Like implements ILike {

    private String like_id;
    private String account_id;
    private int score;

    public Like(){

    }

    public Like(String like_id, String account_id, int score){
        this.account_id = account_id;
        this.like_id = like_id;
        this.score = score;
    }
    @Override
    public String getLike_id() {
        return like_id;
    }

    @Override
    public String getAccount_id() {
        return account_id;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public void setLike_id(String like_id) {
        this.like_id = like_id;
    }

    @Override
    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    @Override
    public void setScore(int score) {
        this.score = score;
    }
}
