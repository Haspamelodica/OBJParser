package net.haspamelodica.obj.vboutils;

public enum IncludeWMode
{
	INCLUDE(1), DIVIDE_OTHERS(0), DROP(0);

	private final int doublesPerVertex;

	private IncludeWMode(int doublesPerVertex)
	{
		this.doublesPerVertex = doublesPerVertex;
	}

	public int doublesPerVertex()
	{
		return doublesPerVertex;
	}
}
