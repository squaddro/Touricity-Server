package com.squadro.touricity.database;

import com.squadro.touricity.database.query.routeQueries.InsertNewEntryQuery;
import com.squadro.touricity.message.types.data.IPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.util.Base64;

public class ByteArrays {
	protected static Logger  logger = LoggerFactory.getLogger(ByteArrays.class);
	public static class Encoders {
		public static String encodePathVertexArray(IPath.PathVertex[] vertexArray) {
			double[] doubleArray = new double[vertexArray.length * 2];
			int i=0;
			for (IPath.PathVertex pathVertex : vertexArray) {
				doubleArray[i++] = pathVertex.getLatitude();
				doubleArray[i++] = pathVertex.getLongitude();
			}

			return encodeDoubleArray(doubleArray);
		}

		public static String encodeDoubleArray(double[] doubleArray) {
			String str = Base64.getEncoder().encodeToString(Converters.doubleArrayToByteArray(doubleArray));
			return str;
		}
	}

	public static class Decoders {
		public static double[] decodeDoubleArrays(String base64Encoded) {
			return Converters.byteToDoubleArray(Base64.getDecoder().decode(base64Encoded));
		}

		public static IPath.PathVertex[] decodePathVertexArray(String base64Encoded) {
			double[] doubles = decodeDoubleArrays(base64Encoded);
			IPath.PathVertex[] pathVertices = new IPath.PathVertex[doubles.length/2];

			for(int i=0; i<doubles.length; i+=2)
				pathVertices[i/2] = new IPath.PathVertex(doubles[i], doubles[i+1]);

			return pathVertices;
		}
	}

	public static class Converters {
		public static byte[] doubleArrayToByteArray (double[] array) {
			ByteBuffer buf = ByteBuffer.allocate(Double.SIZE / Byte.SIZE * array.length);
			buf.asDoubleBuffer().put(array);
			return buf.array();
		}

		public static double[] byteToDoubleArray(byte[] bytes) {
			DoubleBuffer buf = ByteBuffer.wrap(bytes).asDoubleBuffer();
			double[] doubleArray = new double[buf.limit()];
			buf.get(doubleArray);
			return doubleArray;
		}
	}
}
