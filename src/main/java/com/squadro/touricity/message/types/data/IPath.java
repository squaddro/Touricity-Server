package com.squadro.touricity.message.types.data;

import com.squadro.touricity.message.types.data.enumeration.PathType;

public interface IPath extends IEntry {
	class PathVertex {
		private String latitude;
		private String longitude;

		void setLatitude(String latitude) {
			this.latitude = latitude;
		}

		void setLongitude(String longitude) {
			this.longitude = longitude;
		}

		String getLatitude() {
			return latitude;
		}

		String getLongitude() {
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
