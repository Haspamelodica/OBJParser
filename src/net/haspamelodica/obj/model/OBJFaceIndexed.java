package net.haspamelodica.obj.model;

import static net.haspamelodica.obj.model.TriangulatingUtils.triangulatedVertexCountGeneric;
import static net.haspamelodica.obj.model.TriangulatingUtils.triangulatedVertexStreamGeneric;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public record OBJFaceIndexed(List<OBJFaceVertexIndexed> vertices)
{
	public OBJFaceIndexed(List<OBJFaceVertexIndexed> vertices)
	{
		this.vertices = List.copyOf(vertices);
	}

	public OBJFace unindexed(OBJVertexData vertexData)
	{
		return new OBJFace(vertices.stream().map(v -> v.unindexed(vertexData)).toList());
	}

	public Stream<OBJFaceVertexIndexed> triangulatedVertexStream()
	{
		return triangulatedVertexStreamGeneric(vertices());
	}
	public int triangulatedVertexCount()
	{
		return triangulatedVertexCountGeneric(vertices());
	}
	
	public static class Builder
	{
		private final List<OBJFaceVertexIndexed> vertices;

		public Builder()
		{
			this.vertices = new ArrayList<>();
		}

		public void addFaceVertex(OBJFaceVertexIndexed vertex)
		{
			vertices.add(vertex);
		}

		public OBJFaceIndexed build()
		{
			return new OBJFaceIndexed(vertices);
		}
	}
}
