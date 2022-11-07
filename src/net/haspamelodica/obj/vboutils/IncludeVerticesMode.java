package net.haspamelodica.obj.vboutils;

import java.util.function.DoubleConsumer;

import net.haspamelodica.obj.model.OBJVertex;

public record IncludeVerticesMode(boolean x, boolean y, boolean z, IncludeWMode w)
{
	public static final IncludeVerticesMode INCLUDE_ALL = new IncludeVerticesMode(true, true, true, IncludeWMode.INCLUDE);
	public static final IncludeVerticesMode DIVIDE_W = new IncludeVerticesMode(true, true, true, IncludeWMode.DIVIDE_OTHERS);
	public static final IncludeVerticesMode DROP_ALL = new IncludeVerticesMode(false, false, false, IncludeWMode.DROP);

	public void acceptDoubles(OBJVertex vertex, DoubleConsumer target)
	{
		double wFactor = w() == IncludeWMode.DIVIDE_OTHERS ? vertex.w() : 1.0;

		if(x())
			target.accept(vertex.x() / wFactor);
		if(y())
			target.accept(vertex.y() / wFactor);
		if(z())
			target.accept(vertex.z() / wFactor);
		if(w() == IncludeWMode.INCLUDE)
			target.accept(vertex.w());
	}
	public int doublesPerVertex()
	{
		return (x() ? 1 : 0) + (y() ? 1 : 0) + (z() ? 1 : 0) + w().doublesPerVertex();
	}
}
