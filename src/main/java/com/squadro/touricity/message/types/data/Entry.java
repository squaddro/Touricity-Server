package com.squadro.touricity.message.types.data;


public abstract class Entry implements IEntry {
	
    public int duration;
    public int expense;
    public String comment;
	

	public int getDuration() {
		return duration;
	}

	public int getExpense() {
		return expense;
	}

	public String getComment() {
		return comment;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public void setExpense(int expense) {
		this.expense = expense;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
