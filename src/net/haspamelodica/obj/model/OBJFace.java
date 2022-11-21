package net.haspamelodica.obj.model;

import static net.haspamelodica.obj.model.TriangulatingUtils.triangulatedVertexCountGeneric;
import static net.haspamelodica.obj.model.TriangulatingUtils.triangulatedVertexStreamGeneric;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public record OBJFace(List<OBJFaceVertex> vertices)
{
	public OBJFace(List<OBJFaceVertex> vertices)
	{
		this.vertices = List.copyOf(vertices);
	}

	public Stream<OBJFaceVertex> triangulatedVertexStream()
	{
		return triangulatedVertexStreamGeneric(vertices());
	}
	public int triangulatedVertexCount()
	{
		return triangulatedVertexCountGeneric(vertices());
	}

	public static class Builder
	{
		private final List<OBJFaceVertex> vertices;

		public Builder()
		{
			this.vertices = new ArrayList<>();
		}

		public void addFaceVertex(OBJFaceVertex vertex)
		{
			vertices.add(vertex);
		}

		public OBJFace build()
		{
			return new OBJFace(vertices);
		}
	}
}
