package net.haspamelodica.obj.vboutils;

import java.util.function.DoubleConsumer;

import net.haspamelodica.obj.model.OBJFaceVertex;

public record IncludeFaceVertexMode(IncludeVerticesMode vertices,
		IncludeTextureCoordinatesMode texCoords, IncludeNormalsMode normals)
{
	public void acceptDoubles(OBJFaceVertex faceVertex, DoubleConsumer target)
	{
		vertices().acceptDoubles(faceVertex.vertex(), target);
		texCoords().acceptDoubles(faceVertex.textureCoordinate(), target);
		normals().acceptDoubles(faceVertex.normal(), target);
	}
	public int doublesPerVertex()
	{
		return vertices().doublesPerVertex() + texCoords().doublesPerVertex() + normals().doublesPerVertex();
	}
	public int verticesOffset()
	{
		return 0;
	}
	public int texCoordsOffset()
	{
		return vertices().doublesPerVertex();
	}
	public int normalsOffset()
	{
		return vertices().doublesPerVertex() + texCoords().doublesPerVertex();
	}
}
