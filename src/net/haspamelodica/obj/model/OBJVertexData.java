package net.haspamelodica.obj.model;

import java.util.ArrayList;
import java.util.List;

public record OBJVertexData(List<OBJVertex> vertices, List<OBJTextureCoordinate> textureCoordinates, List<OBJVertexNormal> vertexNormals)
{
	public OBJVertexData(List<OBJVertex> vertices, List<OBJTextureCoordinate> textureCoordinates, List<OBJVertexNormal> vertexNormals)
	{
		this.vertices = List.copyOf(vertices);
		this.textureCoordinates = List.copyOf(textureCoordinates);
		this.vertexNormals = List.copyOf(vertexNormals);
	}

	public static class Builder
	{
		private final List<OBJVertex>				vertices;
		private final List<OBJTextureCoordinate>	textureCoordinates;
		private final List<OBJVertexNormal>			vertexNormals;

		public Builder()
		{
			this.vertices = new ArrayList<>();
			this.textureCoordinates = new ArrayList<>();
			this.vertexNormals = new ArrayList<>();
		}

		public void addVertex(OBJVertex vertex)
		{
			vertices.add(vertex);
		}

		public void addTextureCoordinate(OBJTextureCoordinate textureCoordinate)
		{
			textureCoordinates.add(textureCoordinate);
		}

		public void addVertexNormal(OBJVertexNormal vertexNormal)
		{
			vertexNormals.add(vertexNormal);
		}

		public OBJVertexData build()
		{
			return new OBJVertexData(vertices, textureCoordinates, vertexNormals);
		}
	}
}
