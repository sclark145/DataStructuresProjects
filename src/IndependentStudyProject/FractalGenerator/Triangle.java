package IndependentStudyProject.FractalGenerator;

import java.awt.geom.*;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.*;

public class Triangle
{
    
    private Point2D.Double p1;
    private Point2D.Double p2;
    private Point2D.Double p3;    
    private Point2D.Double center;
    private static Random gen = new Random();
    
    /**
     * Constructor for objects of class Triangle
     */
    public Triangle(Point2D.Double one, Point2D.Double two, Point2D.Double three)
    {
        p1 = one;
        p2 = two;
        p3 = three;
        
        double xMid = ( p1.x + p2.x + p3.x ) / 3;
        double yMid = ( p1.y + p2.y + p3.y ) / 3;
        center = new Point2D.Double(xMid, yMid);
    }

    public void drawTriangle(Graphics2D g2)
    {
        Line2D.Double connect1 = new Line2D.Double(p1, p2);
        Line2D.Double connect2 = new Line2D.Double(p2, p3);
        Line2D.Double connect3 = new Line2D.Double(p3, p1);

        g2.setColor(Color.BLACK); 
        
        g2.draw(connect1);
        g2.draw(connect2);
        g2.draw(connect3);
    }

    public void drawCentroid(Graphics2D g2)
    {
        Line2D.Double connect1 = new Line2D.Double(p1, center);
        Line2D.Double connect2 = new Line2D.Double(p2, center);
        Line2D.Double connect3 = new Line2D.Double(p3, center);

        g2.setColor(Color.BLACK); 
        g2.draw(connect1);
        g2.draw(connect2);
        g2.draw(connect3);
    }
    
    public Point2D.Double getPointOne()
    {
        return p1;
    }
    
    public Point2D.Double getPointTwo()
    {
        return p2;
    }
    
    public Point2D.Double getPointThree()
    {
        return p3;
    }
    
    public Point2D.Double getPointCenter()
    {
        return center;
    }
    
    public Color pickAColor() 
    {
        int colorNumber = gen.nextInt(8);

        switch (colorNumber) {

            case 0:   return Color.blue;
            case 1:   return Color.green;
            case 2:   return Color.red;
            case 3:   return Color.cyan;
            case 4:   return Color.blue;
            case 5:   return Color.magenta;
            case 6:   return Color.pink;
            case 7:   return Color.orange;

        }

        return Color.black;
    }
}

