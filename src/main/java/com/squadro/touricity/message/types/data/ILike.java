package com.squadro.touricity.message.types.data;

public interface ILike extends IDataType{
    String getLike_id();
    String getAccount_id();
    int getScore();

    void setLike_id(String like_id);
    void setAccount_id(String account_id);
    void setScore(int score);
}
