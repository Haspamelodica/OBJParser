package net.haspamelodica.obj.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public record OBJObject(List<OBJFace> faces)
{
	public OBJObject(List<OBJFace> faces)
	{
		this.faces = List.copyOf(faces);
	}

	public Stream<OBJFaceVertex> triangulatedVertexStream()
	{
		return faces.stream().flatMap(OBJFace::triangulatedVertexStream);
	}
	public int triangulatedVertexCount()
	{
		return faces.stream().mapToInt(OBJFace::triangulatedVertexCount).sum();
	}

	public static class Builder
	{
		private final List<OBJFace> faces;

		public Builder()
		{
			this.faces = new ArrayList<>();
		}

		public void addFace(OBJFace face)
		{
			faces.add(face);
		}


		public OBJObject build()
		{
			return new OBJObject(faces);
		}
	}
}
