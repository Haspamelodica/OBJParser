package net.haspamelodica.obj.vboutils;

import java.util.function.DoubleConsumer;

import net.haspamelodica.obj.model.OBJVertexNormal;

public record IncludeNormalsMode(boolean x, boolean y, boolean z)
{
	public static final IncludeNormalsMode INCLUDE_ALL = new IncludeNormalsMode(true, true, true);
	public static final IncludeNormalsMode DROP_ALL = new IncludeNormalsMode(false, false, false);

	public void acceptDoubles(OBJVertexNormal normal, DoubleConsumer target)
	{
		if(x())
			target.accept(normal.x());
		if(y())
			target.accept(normal.y());
		if(z())
			target.accept(normal.z());
	}
	public int doublesPerVertex()
	{
		return (x() ? 1 : 0) + (y() ? 1 : 0) + (z() ? 1 : 0);
	}
}
