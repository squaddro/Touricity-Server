package com.squadro.touricity.message.types.data;


public abstract class Entry implements IEntry {
	
    protected int duration;
    protected int expense;
    protected String comment;
    protected int index;
	

	public int getDuration() {
		return duration;
	}

	public int getExpense() {
		return expense;
	}

	public String getComment() {
		return comment;
	}

	public int getIndex() {return index;}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public void setExpense(int expense) {
		this.expense = expense;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

    public void setIndex(int index){this.index = index;}
}
