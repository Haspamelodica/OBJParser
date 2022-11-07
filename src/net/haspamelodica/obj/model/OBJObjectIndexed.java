package net.haspamelodica.obj.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public record OBJObjectIndexed(List<OBJFaceIndexed> faces)
{
	public OBJObjectIndexed(List<OBJFaceIndexed> faces)
	{
		this.faces = List.copyOf(faces);
	}

	public OBJObject unindexed(OBJVertexData vertexData)
	{
		return new OBJObject(faces.stream().map(f -> f.unindexed(vertexData)).toList());
	}

	public Stream<OBJFaceVertexIndexed> triangulatedVertexStream()
	{
		return faces.stream().flatMap(OBJFaceIndexed::triangulatedVertexStream);
	}
	public int triangulatedVertexCount()
	{
		return faces.stream().mapToInt(OBJFaceIndexed::triangulatedVertexCount).sum();
	}

	public static class Builder
	{
		private final List<OBJFaceIndexed> faces;

		public Builder()
		{
			this.faces = new ArrayList<>();
		}

		public void addFace(OBJFaceIndexed face)
		{
			faces.add(face);
		}


		public OBJObjectIndexed build()
		{
			return new OBJObjectIndexed(faces);
		}
	}
}
