package gui.Shapes;

import api.BaseGeoLocation;
import api.GeoLocation;

import javax.swing.*;
import java.awt.*;


public abstract class BaseShape extends JButton {

    public static Color DEFAULT_COLOR_FILL = Color.WHITE;
    public static Color DEFAULT_COLOR_FILL_CLICKED = Color.lightGray;
    public static Color DEFAULT_COLOR_STROKE = Color.BLACK;
    public static Color DEFAULT_COLOR_TEXT = Color.BLUE;

    protected Color fillColor = BaseShape.DEFAULT_COLOR_FILL;
    protected Color clickedFillColor = BaseShape.DEFAULT_COLOR_FILL_CLICKED;
    protected Color strokeColor = BaseShape.DEFAULT_COLOR_STROKE;
    protected Color textColor = BaseShape.DEFAULT_COLOR_TEXT;

    Shape shape;
    protected int strokeWidth = 1;

    public BaseShape(String label){
        super(label);
        Dimension size = getPreferredSize();
//        size.width = size.height = Math.max(size.width, size.height);
//        setPreferredSize(size);
        setContentAreaFilled(false);
    }

    protected void paintComponent(Graphics g) {
        if (getModel().isArmed()) {
            g.setColor(this.clickedFillColor);
        } else {
            g.setColor(this.fillColor);
        }
        this.paintShape(g);
        super.paintComponent(g);
    }

    protected void paintShape(Graphics g){}

    protected void paintBorder(Graphics g){
//        super.paintBorder(g); // TODO: delete
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(this.strokeWidth));

        g.setColor(this.strokeColor);
    }

    public boolean contains(int x, int y) {
        if(this.shape == null){
            return false;
        }
        return shape.contains(x, y);
    }

    public void SetColorStroke(Color c){
        this.strokeColor = c;
    }
    public void SetColorFill(Color c){
        this.fillColor = c;
    }
    public void SetColorText(Color c){
        this.textColor = c;
    }
    public int getCenterX(){
        return this.getBounds().x + this.getWidth()/2;
    }
    public int getCenterY(){
        return this.getBounds().y + this.getHeight()/2;
    }
    public GeoLocation getCenter(){
        return new BaseGeoLocation(this.getCenterX(), this.getCenterY());
    }

}
