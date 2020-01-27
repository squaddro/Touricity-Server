package com.squadro.touricity.message.types.data;

import com.squadro.touricity.message.types.data.enumeration.PathType;

public interface IPath extends IEntry {
	class PathVertex {
		private double latitude;
		private double longitude;

		public PathVertex(){

		}

		public PathVertex(double latitude, double longitude){
            this.latitude = latitude;
            this.longitude = longitude;
		}

		public void setLatitude(double latitude) {
			this.latitude = latitude;
		}

		public void setLongitude(double longitude) {
			this.longitude = longitude;
		}

		public double getLatitude() {
			return latitude;
		}

		public double getLongitude() {
			return longitude;
		}
	}

	String getPath_id();
	PathType getPath_type();
	PathVertex[] getVertices();

	void setPath_id(String pathId);
	void setPath_type(PathType pathType);
	void setVertices(PathVertex[] vertices);
}
