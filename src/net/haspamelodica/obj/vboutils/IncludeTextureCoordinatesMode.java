package net.haspamelodica.obj.vboutils;

import java.util.function.DoubleConsumer;

import net.haspamelodica.obj.model.OBJTextureCoordinate;

public record IncludeTextureCoordinatesMode(boolean u, boolean v, IncludeWMode w)
{
	public static final IncludeTextureCoordinatesMode INCLUDE_ALL = new IncludeTextureCoordinatesMode(true, true, IncludeWMode.INCLUDE);
	public static final IncludeTextureCoordinatesMode INCLUDE_UV = new IncludeTextureCoordinatesMode(true, true, IncludeWMode.DROP);
	public static final IncludeTextureCoordinatesMode DIVIDE_W = new IncludeTextureCoordinatesMode(true, true, IncludeWMode.DIVIDE_OTHERS);
	public static final IncludeTextureCoordinatesMode DROP_ALL = new IncludeTextureCoordinatesMode(false, false, IncludeWMode.DROP);

	public void acceptDoubles(OBJTextureCoordinate textureCoordinate, DoubleConsumer target)
	{
		double wFactor = w() == IncludeWMode.DIVIDE_OTHERS ? textureCoordinate.w() : 1.0;

		if(u())
			target.accept(textureCoordinate.u() / wFactor);
		if(v())
			target.accept(textureCoordinate.v() / wFactor);
		if(w() == IncludeWMode.INCLUDE)
			target.accept(textureCoordinate.w());
	}
	public int doublesPerVertex()
	{
		return (u() ? 1 : 0) + (v() ? 1 : 0) + w().doublesPerVertex();
	}
}
