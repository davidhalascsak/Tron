package model;

import java.awt.Color;

public class Colour
{
    private final Color   color;
    private final String  name;

    public Colour(String name, Color color)
    {
        this.color = color;
        this.name = name;
    }

    public Color getColor() { return color; }

    public String getName() { return name; }

    @Override
    public String toString()    { return name; }
}
