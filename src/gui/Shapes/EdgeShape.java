package gui.Shapes;

import api.EdgeData;
import api.GeoLocation;

import java.awt.*;
import java.awt.geom.*;

public class EdgeShape extends BaseShape {

    private final int ARR_SIZE = 7;
    private final int ARROW_Y_POS = -2;
    private final int TEXT_SIZE = 12;

    double dx, dy, angle;
    public int len;
    int arrowDis;
    String text;
    GeoLocation start, end;

    private EdgeData edge;

    public EdgeShape(String label, GeoLocation start, GeoLocation end, int arrowDist, EdgeData edge) {
        super("");
        this.text = label;
        this.strokeWidth = 2;

        this.dx = end.x() - start.x();
        this.dy = end.y() - start.y();
        this.angle = Math.atan2(dy, dx);
        this.len = (int) Math.sqrt(dx*dx + dy*dy);
        this.arrowDis = arrowDist+2;

        this.start = start;
        this.end = end;

        this.edge = edge;
        this.clickedFillColor = new Color(0x918C94);
    }

    public EdgeData get_edge(){
        return this.edge;
    }

    @Override
    protected void paintShape(Graphics g) {
        this.drawArrow(g);
    }

    protected void paintBorder(Graphics g) {
        super.paintBorder(g);
    }

    void drawArrow(Graphics g1) {
        Graphics2D g = (Graphics2D) g1;

        g.translate(start.x(), start.y());
        g.rotate(angle);

        int l = len - arrowDis;

        Shape arc = new Arc2D.Double(0,-10, l-5, 20, 0, 180, Arc2D.OPEN);
        Polygon poly = new Polygon(new int[] {l, l-ARR_SIZE, l-ARR_SIZE, l},
                new int[] {ARROW_Y_POS, -ARR_SIZE+ARROW_Y_POS, ARR_SIZE+ARROW_Y_POS, ARROW_Y_POS}, 4);

        g.setStroke(new BasicStroke(this.strokeWidth));
        g.draw(arc);
        g.fillPolygon(poly);

        this.draw_text(g);
    }

    private void draw_text(Graphics2D g){
        int txt_len = g.getFontMetrics().stringWidth(this.text);

        Font font = new Font(null, Font.PLAIN, TEXT_SIZE);
        AffineTransform affineTransform = new AffineTransform();

        int y_pos = -15;
        int x_pos = (len/2)-(txt_len/2);

        if(Math.abs(angle) > 2){
            affineTransform.rotate(Math.toRadians(180), 0, 0);
            y_pos = -25;
            x_pos = x_pos + 40;
        }
        Font rotatedFont = font.deriveFont(affineTransform);
        g.setFont(rotatedFont);

        g.setColor(this.textColor);
        g.drawString(this.text, x_pos, y_pos);
    }

    public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            Rectangle2D rect = new Rectangle2D.Double( 0, -20, len, 20 );

            AffineTransform at = AffineTransform.getTranslateInstance(0,0);
            at.translate(this.start.x(), this.start.y());
            at.concatenate(AffineTransform.getRotateInstance(angle));

            this.shape = at.createTransformedShape(rect);
        }
        return shape.contains(x, y);
    }

}


