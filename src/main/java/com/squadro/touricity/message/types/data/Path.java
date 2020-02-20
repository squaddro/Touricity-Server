package com.squadro.touricity.message.types.data;

import com.squadro.touricity.message.types.IMessage;
import com.squadro.touricity.message.types.data.enumeration.PathType;

public class Path extends Entry implements IPath {

	private String path_id;
	private PathType path_type;
	private PathVertex[] vertices;
	
	public Path(){
		
	}
	
	public Path(String path_id, PathType path_type, PathVertex[] vertices, int duration, int expense, String comment){
		this.path_id = path_id;
		this.path_type = path_type;
		this.vertices = vertices;
		this.duration = duration;
		this.expense = expense;
		this.comment = comment;
	}

	public Path(String path_id, PathType path_type, PathVertex[] vertices, int duration, int expense, String comment, int index){
		this.path_id = path_id;
		this.path_type = path_type;
		this.vertices = vertices;
		this.duration = duration;
		this.expense = expense;
		this.comment = comment;
		this.index = index;
	}
	
	public String getPath_id() {
		return path_id;
	}

	public int getPath_typeAsInt() {
		if(path_type.equals(PathType.WALKING))
			return 0;
		else if(path_type.equals(PathType.BUS))
			return 1;
		else if(path_type.equals(PathType.DRIVING))
			return 2;
		else
			return 3;
	}

	public PathType getPath_type(){
		return path_type;
	}


	public PathVertex[] getVertices() {
		return vertices;
	}

	public void setPath_id(String pathId) {
		path_id = pathId;
	}

	public void setPath_type(PathType pathType) {
		path_type = pathType;
	}

	public void setVertices(PathVertex[] vertices) {
		this.vertices = vertices;
	}
}
