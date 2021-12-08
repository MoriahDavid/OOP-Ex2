package gui;
import api.DirectedWeightedGraph;
import api.EdgeData;
import api.NodeData;
import gui.Shapes.BaseShape;
import gui.Shapes.EdgeShape;
import gui.Shapes.NodeShape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;


public class GraphDraw extends JPanel implements ActionListener {

    private static final int MINIMUM_NODE_SIZE = 32;
    private static final int NODE_PADDING = 8;

    private Color backGroundColor = new Color(0xE0F9F5);
    private Color nodeColor = Color.GREEN;
    private Color edgeColor = Color.PINK;

    private DirectedWeightedGraph graph;
//    private int last_mc;
    private double scale_x, scale_y;

    private HashMap<NodeData, NodeShape> m;

    private FontMetrics metrics;

    public GraphDraw(DirectedWeightedGraph graph){
        this.graph = graph;
        this.m = new HashMap<>();
    }

    public void set_scale(){
        double max_x=Double.MIN_VALUE, max_y=Double.MIN_VALUE;
        double min_x=Double.MAX_VALUE, min_y=Double.MAX_VALUE;

        for (Iterator<NodeData> it = this.graph.nodeIter(); it.hasNext(); ) {
            NodeData n = it.next();
            if(n.getLocation().x() > max_x){
                max_x = (int) n.getLocation().x();
            }
            else if(n.getLocation().x() < min_x){
                min_x = (int) n.getLocation().x();
            }
            if(n.getLocation().y() > max_y){
                max_y = (int) n.getLocation().y();
            }
            else if(n.getLocation().y() < min_y){
                min_y = (int) n.getLocation().y();
            }
        }
        this.scale_x = max_x / (this.getWidth()-100);
        this.scale_y = max_y / (this.getHeight()-100);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        set_scale();
        Graphics2D g2d = (Graphics2D) g.create();
        this.removeAll();

        metrics = g2d.getFontMetrics();

        paintBackground(g2d);
        drawNodes(g2d);
        drawEdges(g2d);

        this.setLayout(null);
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    private void drawNodes(Graphics2D g2d) {
        for (Iterator<NodeData> it = this.graph.nodeIter(); it.hasNext(); ) {
            NodeData node = it.next();
            drawNode(g2d, node);
        }
    }

    private int convertLocationX(double x){
        return (int) (x/this.scale_x);
    }

    private int convertLocationY(double y){
        return (int) (y/this.scale_y);
    }

    private void drawNode(Graphics2D g2d, NodeData node){
        NodeShape n = new NodeShape(""+node.getKey());
        int width = get_node_size(n);
        n.setBounds(convertLocationX(node.getLocation().x()), convertLocationY(node.getLocation().y()), width, width);
        n.addActionListener(this);
        n.setMargin(new Insets(0,0,0,0));
        n.SetColorStroke(this.nodeColor);

        this.m.put(node, n);
        this.add(n);
    }

    private void drawEdge(Graphics2D g2d, EdgeData edge){

        NodeShape ns1 = m.get(this.graph.getNode(edge.getSrc()));
        NodeShape ns2 = m.get(this.graph.getNode(edge.getDest()));

        EdgeShape e = new EdgeShape(""+edge.getWeight(), ns1.getCenter(), ns2.getCenter(), ns2.getWidth()/2);

        e.setBorder(null);
        e.setBounds(0,0, this.getWidth(), this.getHeight());

        e.addActionListener(this);
        e.setMargin(new Insets(0,0,0,0));
        e.SetColorFill(this.edgeColor);

        this.add(e);
    }

    private int get_node_size(BaseShape n) {
        int width = metrics.stringWidth(n.getText());
        if (width < MINIMUM_NODE_SIZE - 5) {
            return MINIMUM_NODE_SIZE;
        }
        return width + 2 * NODE_PADDING;
    }

    private void drawEdges(Graphics2D g2d) {
        for (Iterator<EdgeData> it = this.graph.edgeIter(); it.hasNext(); ) {
            EdgeData edge = it.next();
            drawEdge(g2d, edge);
        }
    }

    private void paintBackground(Graphics2D g2d) {
        g2d.setColor(this.backGroundColor);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e);
    }
}
