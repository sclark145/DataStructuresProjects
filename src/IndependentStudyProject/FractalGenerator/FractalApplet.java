package IndependentStudyProject.FractalGenerator;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.awt.geom.*;

public class FractalApplet extends JApplet {

    private static final long serialVersionUID = 1L;

    private int count;
    private int pointNum;
    private MouseListener listener;
    private int currentX = 20;
    private int currentY = 20;

    private static ArrayList<Point2D.Double> triPoints;
    private static ArrayList<Triangle> triangleArray;

    /**
     * Called by the browser or applet viewer to inform this JApplet that it has
     * been loaded into the system. It is always called before the first time
     * that the start method is called.
     */
    @Override
    public void init() {
        
        count = 0;
        triPoints = new ArrayList<Point2D.Double>();
        triangleArray = new ArrayList<Triangle>();
        listener = new MouseClickedListener();
        addMouseListener(listener);

        this.setSize(750, 500);
      
    }

    /**
     * Paint method for applet.
     *
     * @param g the Graphics object for this applet
     */
    @Override
    public void paint(Graphics g) {
        
        g.drawString ("Welcome to the Triangle Fractal Generator!", 10, 20);
        g.drawString ("Begin by clicking three points within the window " +
                "(make sure to keep mouse still while clicking).\n", 10, 40);
        g.drawString ("Next, continue clicking to make more fractals! Enjoy!", 10, 60);
        
        Graphics2D g2 = (Graphics2D) g;

        if (triPoints.size() < 3) {
            return;
        }

        Triangle original = new Triangle(triPoints.get(0), triPoints.get(1), triPoints.get(2));
        original.drawTriangle(g2);

        if (pointNum == 4) {
            original.drawCentroid(g2);
            triangleArray.add(original);
        }

        if (pointNum >= 5) {
            triangleArray.add(new Triangle(triangleArray.get(count).getPointOne(),
                    triangleArray.get(count).getPointTwo(),
                    triangleArray.get(count).getPointCenter()));

            triangleArray.get(count).drawCentroid(g2);

            triangleArray.add(new Triangle(triangleArray.get(count).getPointTwo(),
                    triangleArray.get(count).getPointThree(),
                    triangleArray.get(count).getPointCenter()));

            triangleArray.get(count).drawCentroid(g2);

            triangleArray.add(new Triangle(triangleArray.get(count).getPointThree(),
                    triangleArray.get(count).getPointOne(),
                    triangleArray.get(count).getPointCenter()));

            triangleArray.get(count).drawCentroid(g2);

            count++;
        }

    }

    public class MouseClickedListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent event) {
            pointNum++;
            currentX = (int) (event.getX());
            currentY = (int) (event.getY());

            if (pointNum <= 3) {
                triPoints.add(new Point2D.Double(currentX, currentY));
            }
            if (pointNum >= 3) {
                repaint();
            }

        }

        @Override
        public void mousePressed(MouseEvent event) {
        }

        @Override
        public void mouseReleased(MouseEvent event) {
        }

        @Override
        public void mouseEntered(MouseEvent event) {
        }

        @Override
        public void mouseExited(MouseEvent event) {
        }

    }

}
