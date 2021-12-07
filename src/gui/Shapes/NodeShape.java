package gui.Shapes;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class NodeShape extends BaseShape {

    public NodeShape(String text){
        super(text);
        this.strokeWidth = 3;
    }

    @Override
    protected void paintShape(Graphics g) {
        g.fillOval(0, 0, getSize().width, getSize().height);
    }

    protected void paintBorder(Graphics g) {
        super.paintBorder(g);

        int size = getSize().width - this.strokeWidth+1;

        g.drawOval(0+this.strokeWidth/2, 0+this.strokeWidth/2, size, size);

    }

    public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
        }
        return shape.contains(x, y);
    }
}
