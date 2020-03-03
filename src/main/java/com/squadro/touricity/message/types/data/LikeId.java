package com.squadro.touricity.message.types.data;

public class LikeId implements ILikeId{

	private String like_id;

	public LikeId(){ }

	@Override
	public void setLike_id(String like_id) {
		this.like_id = like_id;
	}

	@Override
	public String getLike_id() {
		return like_id;
	}
}
