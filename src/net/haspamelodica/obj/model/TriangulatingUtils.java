package net.haspamelodica.obj.model;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TriangulatingUtils
{
	public static <V> Stream<V> triangulatedVertexStreamGeneric(List<V> vertices)
	{
		return switch(vertices.size())
		{
			case 0 -> Stream.empty();
			case 1 -> Stream.of(vertices.get(0), vertices.get(0), vertices.get(0));
			case 2 -> Stream.of(vertices.get(0), vertices.get(1), vertices.get(1));
			case 3 -> Stream.of(vertices.get(0), vertices.get(1), vertices.get(2));
			default ->
			{
				V base = vertices.get(0);
				yield IntStream
						.range(1, vertices.size() - 1)
						.mapToObj(i -> Stream.of(base, vertices.get(i), vertices.get(i + 1)))
						.flatMap(s -> s);
			}
		};
	}
	public static <V> int triangulatedVertexCountGeneric(List<V> vertices)
	{
		return switch(vertices.size())
		{
			case 0, 1, 2, 3 -> 3;
			default -> (vertices.size() - 2) * 3;
		};
	}

	private TriangulatingUtils()
	{}
}
