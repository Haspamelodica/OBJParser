package net.haspamelodica.obj.vboutils;

import java.util.stream.DoubleStream;

import net.haspamelodica.obj.model.OBJObject;

public class VBOUtils
{
	public static DoubleStream triangulatedVertexDataStream(OBJObject object, IncludeFaceVertexMode includeMode)
	{
		return object
				.triangulatedVertexStream()
				.mapMultiToDouble((faceVertex, target) -> includeMode.acceptDoubles(faceVertex, target));
	}
	public static int triangulatedVertexDataCount(OBJObject object, IncludeFaceVertexMode includeMode)
	{
		return object.triangulatedVertexCount() * includeMode.doublesPerVertex();
	}

	private VBOUtils()
	{}
}
