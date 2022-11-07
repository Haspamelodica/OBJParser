package net.haspamelodica.obj.model;

/**
 * {@link #textureCoordinate} and {@link #normal} can be unset, which is represented by a negative number.
 * Unlike the OBJ file format, {@link #vertex}, {@link #textureCoordinate}, {@link #normal} are 0-indiced.
 */
public record OBJFaceVertexIndexed(int vertex, int textureCoordinate, int normal)
{
	public OBJFaceVertex unindexed(OBJVertexData vertexData)
	{
		return new OBJFaceVertex(
				vertexData.vertices().get(vertex()),
				vertexData.textureCoordinates().get(textureCoordinate()),
				vertexData.vertexNormals().get(normal()));
	}
}
