package com.squadro.touricity.message.types.data;

import java.util.List;

public class RouteLike {

	private Route route;
	private double likeScore;

	public RouteLike(Route route, double likeScore ) {
		this.route = route;
		this.likeScore = likeScore;
	}

	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	public double getLikeScore() { return likeScore; }

	public void setLikeScore(int likeScore){ this.likeScore = likeScore; }
}
