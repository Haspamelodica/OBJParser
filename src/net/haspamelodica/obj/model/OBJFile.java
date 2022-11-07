package net.haspamelodica.obj.model;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public record OBJFile(OBJVertexData vertexData, OBJObjectIndexed anonymousObject, Map<String, OBJObjectIndexed> namedObjects)
{
	public OBJFile(OBJVertexData vertexData, OBJObjectIndexed anonymousObject, Map<String, OBJObjectIndexed> namedObjects)
	{
		this.vertexData = vertexData;
		this.anonymousObject = anonymousObject;
		this.namedObjects = Map.copyOf(namedObjects);
	}

	public OBJObject anonymousObjectUnindexed()
	{
		return anonymousObject().unindexed(vertexData());
	}

	public static record NamedObjObject(String name, OBJObject object)
	{}
	public Stream<NamedObjObject> namedObjectsUnindexed()
	{
		return namedObjects.entrySet().stream().map(e -> new NamedObjObject(e.getKey(), e.getValue().unindexed(vertexData())));
	}

	public static class Builder
	{
		private final OBJVertexData.Builder			vertexDataBuilder;
		private OBJObjectIndexed					anonymousObject;
		private final Map<String, OBJObjectIndexed>	namedObjects;

		private String						currentOBJObjectName;
		private OBJObjectIndexed.Builder	currentOBJObjectBuilder;

		public Builder()
		{
			this.vertexDataBuilder = new OBJVertexData.Builder();
			this.anonymousObject = null;
			this.namedObjects = new HashMap<>();

			this.currentOBJObjectName = null;
			// The anonymous OBJ object always exists
			this.currentOBJObjectBuilder = new OBJObjectIndexed.Builder();
		}

		public OBJVertexData.Builder vertexDataBuilder()
		{
			return vertexDataBuilder;
		}

		public OBJObjectIndexed.Builder currentOBJObjectBuilder()
		{
			return currentOBJObjectBuilder;
		}
		public void newOBJObject(String name)
		{
			saveCurrentOBJObject();
			if(namedObjects.containsKey(name))
				//TODO better error handling
				System.err.println("WARNING: Duplicate object name " + name + ", later object will be used");
			currentOBJObjectName = name;
			currentOBJObjectBuilder = new OBJObjectIndexed.Builder();
		}
		public void addFaceToCurrentOBJObject(OBJFaceIndexed face)
		{
			currentOBJObjectBuilder().addFace(face);
		}

		public void addVertex(OBJVertex vertex)
		{
			vertexDataBuilder.addVertex(vertex);
		}

		public void addTextureCoordinate(OBJTextureCoordinate textureCoordinate)
		{
			vertexDataBuilder.addTextureCoordinate(textureCoordinate);
		}

		public void addVertexNormal(OBJVertexNormal vertexNormal)
		{
			vertexDataBuilder.addVertexNormal(vertexNormal);
		}

		public OBJFile build()
		{
			saveCurrentOBJObject();
			return new OBJFile(vertexDataBuilder.build(), anonymousObject, namedObjects);
		}

		private void saveCurrentOBJObject()
		{
			if(currentOBJObjectName == null)
				anonymousObject = currentOBJObjectBuilder.build();
			else
				namedObjects.put(currentOBJObjectName, currentOBJObjectBuilder.build());
		}
	}
}
