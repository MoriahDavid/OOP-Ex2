package gui;
import api.*;
import gui.Shapes.BaseShape;
import gui.Shapes.EdgeShape;
import gui.Shapes.NodeShape;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class GraphDraw extends JPanel {

    private static final int MINIMUM_NODE_SIZE = 32;
    private static final int NODE_PADDING = 8;

    private Color nodeColorSrc = new Color(0xA03BE8);
    private Color nodeColorDest = new Color(0xFF0080);
    private Color edgeColorMarked = Color.CYAN;
    private Color backGroundColor = new Color(0xFAFAFA);
    private Color nodeColor = new Color(0x9B4565);
    private Color edgeColor = new Color(0x44A98E);

    private DirectedWeightedGraph graph;
    private double max_x=Double.MIN_VALUE, max_y=Double.MIN_VALUE;
    private double min_x=Double.MAX_VALUE, min_y=Double.MAX_VALUE;

    private HashMap<NodeData, NodeShape> m;

    private FontMetrics metrics;
    private int width, height;
    private boolean should_update = true;

    private NodeData srcSelectedNode;
    private NodeData destSelectedNode;

    private int last_mc;

    private boolean enable_drag_nodes = true;
    private boolean show_edges_weight = true;

    private java.util.List<EdgeData> marked_edges;

    public void set_drag_nodes(boolean f){
        this.enable_drag_nodes = f;
    }
    public boolean is_enabled_drag_nodes(){
        return this.enable_drag_nodes;
    }

    public void set_show_edges_weight(boolean f){this.show_edges_weight = f;}
    public boolean is_show_edges_weight(){return this.show_edges_weight;}

    public NodeData get_selected_src(){
        return this.srcSelectedNode;
    }
    public NodeData get_selected_dest(){
        return this.destSelectedNode;
    }
    public void set_selected_src(NodeData n){
        this.srcSelectedNode = n;
        set_update();
        repaint();
    }
    public void set_selected_dest(NodeData n){
        this.destSelectedNode=n;
        set_update();
        repaint();
    }

    public GraphDraw(DirectedWeightedGraph graph){
        this.graph = graph;
        this.last_mc = graph.getMC();

        this.m = new HashMap<>();
        this.width = 0;
        this.height = 0;

        this.setLayout(null);
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.set_scale();
        this.marked_edges = new LinkedList<>();
    }

    public void set_marked_edges(List<EdgeData> e){
        this.marked_edges = e;
        this.set_update();
        this.repaint();
    }
    public void clear_marked_edges(){
        this.marked_edges.clear();
        this.set_update();
        this.repaint();
    }

    public void update(DirectedWeightedGraph g){
        this.graph = g;
        this.last_mc = g.getMC();
        this.should_update = true;
        this.repaint();
    }

    public void set_scale(){
        max_y=max_x=Double.MIN_VALUE;
        min_y=min_x=Double.MAX_VALUE;

        for (Iterator<NodeData> it = this.graph.nodeIter(); it.hasNext(); ) {
            NodeData n = it.next();
            if(n.getLocation().x() > max_x){
                max_x = n.getLocation().x();
            }
            else if(n.getLocation().x() < min_x){
                min_x = n.getLocation().x();
            }
            if(n.getLocation().y() > max_y){
                max_y = n.getLocation().y();
            }
            else if(n.getLocation().y() < min_y){
                min_y = n.getLocation().y();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        metrics = g2d.getFontMetrics();
        paintBackground(g2d);

        if(this.last_mc!=this.graph.getMC() || should_update || this.width != this.getWidth() || this.height != this.getHeight()){
            System.out.println("rePainting!");
            this.m.clear();
            this.removeAll();

            this.width = this.getWidth();
            this.height = this.getHeight();
            this.should_update = false;
            this.last_mc = this.graph.getMC();

            set_scale();
            drawNodes(g2d);
            drawEdges(g2d);
        }

    }

    private void drawNodes(Graphics2D g2d) {
        for (Iterator<NodeData> it = this.graph.nodeIter(); it.hasNext(); ) {
            NodeData node = it.next();
            drawNode(g2d, node);
        }
    }

    private int convertLocationX(double x){
        return (int) map(x, min_x, max_x, 20, this.getWidth()-50);
    }

    private int convertLocationY(double y){
        return (int) map(y, min_y, max_y, 20, this.getHeight()-50);
    }

    private double backConvertLocationX(double x){
        return map(x, 20, this.getWidth()-50, min_x, max_x);
    }

    private double backConvertLocationY(double y){
        return map(y, 20, this.getHeight()-50, min_y, max_y);
    }

    private GeoLocation convertLocation(GeoLocation l){
        return new BaseGeoLocation(convertLocationX(l.x()), convertLocationY(l.y()));
    }

    private double map(double x, double in_min, double in_max, double out_min, double out_max) {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }

    private void drawNode(Graphics2D g2d, NodeData node){
        NodeShape n = new NodeShape(""+node.getKey(), node);
        int width = get_node_size(n);
        n.setBounds(convertLocationX(node.getLocation().x()), convertLocationY(node.getLocation().y()), width, width);

        n.addMouseListener(new PopClickListener());
        n.addMouseMotionListener(new PopClickListener());
        n.setMargin(new Insets(0,0,0,0));
        if(node == this.srcSelectedNode){
            n.SetColorStroke(this.nodeColorSrc);
        }
        else if(node == this.destSelectedNode){
            n.SetColorStroke(this.nodeColorDest);
        }
        else {
            n.SetColorStroke(this.nodeColor);
        }
        this.m.put(node, n);
        this.add(n);
    }

    private void drawEdge(Graphics2D g2d, EdgeData edge){

        NodeShape ns1 = m.get(this.graph.getNode(edge.getSrc()));
        NodeShape ns2 = m.get(this.graph.getNode(edge.getDest()));
        if (ns1 == null || ns2 == null) return;

        String t = "";

        if(this.show_edges_weight)
            t = String.format("%.5f", edge.getWeight());

        EdgeShape e = new EdgeShape(t, ns1.getCenter(), ns2.getCenter(), ns2.getWidth()/2, edge);

        e.setBorder(null);
        e.setBounds(0,0, this.getWidth(), this.getHeight());

        e.addMouseListener(new PopClickListener());
        e.setMargin(new Insets(0,0,0,0));
        if(this.marked_edges.contains(edge)){
            e.SetColorFill(this.edgeColorMarked);
        }else {
            e.SetColorFill(this.edgeColor);
        }
        this.add(e);
    }
    public void set_update(){
        this.should_update = true;
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

    private void delete_edge(EdgeData e){
        this.graph.removeEdge(e.getSrc(), e.getDest());
        this.repaint();
    }
    private void delete_node(NodeData n){
        this.graph.removeNode(n.getKey());
        this.repaint();
    }

    class PopClickListener extends MouseAdapter {

        @Override
        public void mouseDragged(MouseEvent e) {
            if(!enable_drag_nodes) return;
            Object o = e.getSource();
            if (o instanceof NodeShape) {
                NodeShape s = ((NodeShape) o);
                NodeData n = s.get_node();

                if(Math.abs(e.getX()) > 3 || Math.abs(e.getY()) > 3){
                    int new_x = s.getX()+e.getX()-s.getWidth()/2;
                    int new_y = s.getY()+e.getY()-s.getWidth()/2;
                    n.setLocation(new BaseGeoLocation(backConvertLocationX(new_x), backConvertLocationY(new_y)));
                    s.setLocation(new_x, new_y);
                    repaint();
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }
        @Override
        public void mouseReleased(MouseEvent e) {
            set_update();
            repaint();

            if (e.isPopupTrigger()) {
                Object o = e.getSource();
                if (o instanceof NodeShape) { doPopNode(e, ((NodeShape) o).get_node());}
                else if (o instanceof EdgeShape) { doPopEdge(e, ((EdgeShape) o).get_edge()); }
            }
        }
        private void doPopNode(MouseEvent e, NodeData n) {
            PopUpNode menu = new PopUpNode(graph, n, e);
            menu.show(e.getComponent(), e.getX(), e.getY());
        }
        private void doPopEdge(MouseEvent e, EdgeData ed) {
            PopUpEdge menu = new PopUpEdge(graph, ed);
            menu.show(e.getComponent(), e.getX(), e.getY());
        }
    }

    class PopUpNode extends JPopupMenu {
        JMenuItem anItem;
        public PopUpNode(DirectedWeightedGraph g, NodeData n, MouseEvent e) {
            anItem = new JMenuItem("Delete Node");
            anItem.addActionListener((event) -> delete_node(n));
            add(anItem);
            addSeparator();
            anItem = new JMenuItem("Select as Src");
            anItem.addActionListener((event) -> set_selected_src(n));
            add(anItem);
            anItem = new JMenuItem("Select as Dest");
            anItem.addActionListener((event) -> set_selected_dest(n));
            add(anItem);
        }
    }
    class PopUpEdge extends JPopupMenu {
        JMenuItem anItem;
        public PopUpEdge(DirectedWeightedGraph g, EdgeData edge) {
            anItem = new JMenuItem("Delete Edge");
            anItem.addActionListener((event) -> delete_edge(edge));
            add(anItem);
        }
    }
}

