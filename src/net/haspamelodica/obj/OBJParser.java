package net.haspamelodica.obj;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Scanner;

import net.haspamelodica.obj.model.OBJFaceIndexed;
import net.haspamelodica.obj.model.OBJFaceVertexIndexed;
import net.haspamelodica.obj.model.OBJFile;
import net.haspamelodica.obj.model.OBJTextureCoordinate;
import net.haspamelodica.obj.model.OBJVertex;
import net.haspamelodica.obj.model.OBJVertexNormal;

public class OBJParser
{
	public static OBJFile parseOBJ(Path objFile) throws IOException
	{
		return parseOBJ(Files.readString(objFile));
	}

	public static OBJFile parseOBJ(URL objFile) throws IOException
	{
		try(Scanner in = new Scanner(objFile.openStream()))
		{
			return parseOBJ(in);
		}
	}
	public static OBJFile parseOBJ(String objFile)
	{
		try(Scanner in = new Scanner(objFile))
		{
			return parseOBJ(in);
		}
	}

	public static OBJFile parseOBJ(Scanner objFile)
	{
		OBJFile.Builder builder = new OBJFile.Builder();

		while(objFile.hasNextLine())
		{
			String objLine = objFile.nextLine();
			objLine = objLine.split("#", 2)[0].trim();
			if(objLine.isEmpty())
				continue;

			parseOBJLine(builder, objLine);
		}

		return builder.build();
	}

	private static void parseOBJLine(OBJFile.Builder builder, String objLine)
	{
		try(Scanner objLineScanner = new Scanner(objLine))
		{
			// use '.' as the decimal separator
			objLineScanner.useLocale(Locale.US);
			String command = objLineScanner.next();
			switch(command)
			{
				case "o" -> builder.newOBJObject(objLineScanner.next("(?s:.*)\\z"));
				case "mtllib", "usemtl", "s", "g" ->
				{
					// ignore mtllib, usemtl, smooth shading, face groups.
					// Explicit return to avoid trailing data warning.
					return;
				}
				case "vt" ->
				{
					double u = objLineScanner.nextDouble();
					double v = nextDoubleOrDefault(objLineScanner, 0);
					double w = nextDoubleOrDefault(objLineScanner, 0);
					builder.addTextureCoordinate(new OBJTextureCoordinate(u, v, w));
				}
				case "vn" ->
				{
					double x = objLineScanner.nextDouble();
					double y = objLineScanner.nextDouble();
					double z = objLineScanner.nextDouble();
					builder.addVertexNormal(new OBJVertexNormal(x, y, z));
				}
				case "v" ->
				{
					double x = objLineScanner.nextDouble();
					double y = objLineScanner.nextDouble();
					double z = objLineScanner.nextDouble();
					double w = nextDoubleOrDefault(objLineScanner, 1);
					builder.addVertex(new OBJVertex(x, y, z, w));
				}
				case "f" ->
				{
					OBJFaceIndexed.Builder faceBuilder = new OBJFaceIndexed.Builder();
					while(objLineScanner.hasNext())
						faceBuilder.addFaceVertex(parseFaceVertex(objLineScanner.next()));
					builder.addFaceToCurrentOBJObject(faceBuilder.build());
				}
				case "l" ->
				{
					OBJFaceIndexed.Builder faceBuilder = new OBJFaceIndexed.Builder();
					faceBuilder.addFaceVertex(new OBJFaceVertexIndexed(objLineScanner.nextInt() - 1, -1, -1));
					faceBuilder.addFaceVertex(new OBJFaceVertexIndexed(objLineScanner.nextInt() - 1, -1, -1));
					builder.addFaceToCurrentOBJObject(faceBuilder.build());
				}
				default ->
				{
					//TODO better error handling
					System.err.println("WARNING: Ignored line with unknown command: " + objLine);
				}
			}
			if(objLineScanner.hasNext())
				//TODO better error handling
				System.err.println("WARNING: Ignored trailing data in line: " + objLine);
		}
	}

	public static double nextDoubleOrDefault(Scanner objLineScanner, int defaultValue)
	{
		return objLineScanner.hasNextDouble() ? objLineScanner.nextDouble() : defaultValue;
	}

	public static OBJFaceVertexIndexed parseFaceVertex(String objFaceVertex)
	{
		String[] parts = objFaceVertex.split("/");
		if(parts.length < 1)
			throw new IllegalStateException("String.split returned empty array");
		if(parts.length > 3)
			System.err.println("WARNING: Ignored trailing face vertex attributes: " + objFaceVertex);

		int vertex = Integer.parseInt(parts[0]);
		int textureCoordinate = parts.length == 1 ? 0 : parts.length == 3 && parts[1].isEmpty() ? 0 : Integer.parseInt(parts[1]);
		int normal = parts.length < 3 ? 0 : Integer.parseInt(parts[2]);
		// -1 because these are 1-indiced
		return new OBJFaceVertexIndexed(vertex - 1, textureCoordinate - 1, normal - 1);
	}

	private OBJParser()
	{}
}
