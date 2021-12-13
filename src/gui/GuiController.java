package gui;

import api.*;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GuiController {

    private DirectedWeightedGraphAlgorithms algo;
    private GraphDraw g_draw;
    private JFrame frame;
    private String title;

    public GuiController(DirectedWeightedGraphAlgorithms algo){
        this.algo = algo;
    }

    public GuiController(DirectedWeightedGraphAlgorithms algo, String title){
        this.algo = algo;
        this.title = title;
    }

    public void show(){

        this.frame = new JFrame();
        frame.getContentPane();
        this.set_title(this.title);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        this.g_draw = new GraphDraw(this.algo.getGraph());
        this.g_draw.setVisible(true);

        this.set_menu(frame);

        frame.add(this.g_draw);
        frame.setSize(1000,600);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

    }

    private void set_menu(JFrame frame){

        JMenuBar menuBar;
        JMenu menu, submenu;
        JMenuItem menuItem;
        JRadioButtonMenuItem rbMenuItem;
        JCheckBoxMenuItem cbMenuItem;

        //Create the menu bar.
        menuBar = new JMenuBar();

        //Build the first menu.
        menu = new JMenu("File");
        menuBar.add(menu);

        //a group of JMenuItems
        menuItem = new JMenuItem("Load Graph", KeyEvent.VK_T);
        menuItem.addActionListener((event) -> open_graph_file());
        menu.add(menuItem);

        menuItem = new JMenuItem("Save Graph", KeyEvent.VK_T);
        menuItem.addActionListener((event) -> save_graph_file());
        menu.add(menuItem);

        menu.addSeparator();

        menuItem = new JMenuItem("Exit", KeyEvent.VK_T);
        menuItem.addActionListener((event) -> System.exit(0));

        menu.add(menuItem);

        //Build second menu in the menu bar.
        menu = new JMenu("Graph");
        menu.setMnemonic(KeyEvent.VK_N);

        menuItem = new JMenuItem("Add Node", KeyEvent.VK_T); // TODO: AddNode
        menuItem.addActionListener((e) -> JOptionPane.showMessageDialog(this.frame,
                "For adding node, right click on the wanted position",
                "Add Node", JOptionPane.INFORMATION_MESSAGE));
        menu.add(menuItem);

        menuItem = new JMenuItem("Add Edge", KeyEvent.VK_T);
            menuItem.addActionListener((e) -> add_edge());
        menu.add(menuItem);


        menu.addSeparator();

        menuItem = new JMenuItem("Clear Marked Edges", KeyEvent.VK_T);
        menuItem.addActionListener((e) -> clear_edges_marks());
        menu.add(menuItem);

        menuItem = new JMenuItem("Clear Marked Nodes", KeyEvent.VK_T);
        menuItem.addActionListener((e) -> clear_nodes_marks());
        menu.add(menuItem);

        menu.addSeparator();
        menuItem = new JMenuItem("Add all for TSP", KeyEvent.VK_T);
        menuItem.addActionListener((e) -> select_all_for_tsp());
        menu.add(menuItem);

        menuItem = new JMenuItem("Remove all for TSP", KeyEvent.VK_T);
        menuItem.addActionListener((e) -> remove_all_for_tsp());
        menu.add(menuItem);

        menu.addSeparator();

        cbMenuItem = new JCheckBoxMenuItem("Enable Drag Nodes");
        cbMenuItem.addActionListener((e) -> toggle_enable_drag());
        cbMenuItem.setSelected(true);
        cbMenuItem.setMnemonic(KeyEvent.VK_H);
        menu.add(cbMenuItem);

        cbMenuItem = new JCheckBoxMenuItem("Show Edges Weight");
        cbMenuItem.addActionListener((e) -> toggle_show_edge_weight());
        cbMenuItem.setSelected(true);
        cbMenuItem.setMnemonic(KeyEvent.VK_H);
        menu.add(cbMenuItem);

        menuBar.add(menu);


        menu = new JMenu("Algorithms");
        menuItem = new JMenuItem("Is Connected", KeyEvent.VK_T);
        menuItem.addActionListener((e) -> algo_is_connected());
        menu.add(menuItem);

        menuItem = new JMenuItem("Shorted Path", KeyEvent.VK_T);
        menuItem.addActionListener((e) -> algo_shorted_path());
        menu.add(menuItem);

        menuItem = new JMenuItem("Center", KeyEvent.VK_T);
        menuItem.addActionListener((e) -> algo_center());
        menu.add(menuItem);

        menuItem = new JMenuItem("TSP", KeyEvent.VK_T);
        menuItem.addActionListener((e) -> algo_tsp());
        menu.add(menuItem);

        menuBar.add(menu);


        menu = new JMenu("Help");
        menuItem = new JMenuItem("Help", KeyEvent.VK_T);
        menuItem.addActionListener((e) -> open_help());
        menu.add(menuItem);

        menu.addSeparator();

        menuItem = new JMenuItem("About", KeyEvent.VK_T);
        menuItem.addActionListener((e) -> open_help());
        menu.add(menuItem);

        menuBar.add(menu);

        frame.setJMenuBar(menuBar);
    }

    private void toggle_enable_drag(){
        g_draw.set_drag_nodes(!g_draw.is_enabled_drag_nodes());
    }
    private void toggle_show_edge_weight(){
        g_draw.set_show_edges_weight(!g_draw.is_show_edges_weight());
        g_draw.set_update();
        g_draw.repaint();
    }
    private void clear_edges_marks(){
        this.g_draw.clear_marked_edges();
    }
    private void clear_nodes_marks(){
        this.g_draw.set_selected_src(null);
        this.g_draw.set_selected_dest(null);
    }

    private void add_edge(){
        NodeData src=g_draw.get_selected_src(), dest=g_draw.get_selected_dest();
        if(src == null || dest == null){
            String m = "Need to choose src and dest nodes.\nRight click on node and select.";
            JOptionPane.showMessageDialog(this.frame, m,"Add Edge", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String input=JOptionPane.showInputDialog(this.frame, "Enter edge weight:","Add Edge",JOptionPane.QUESTION_MESSAGE);
        try{
            double w = Double.parseDouble(input);
            this.algo.getGraph().connect(src.getKey(), dest.getKey(), w);
            this.g_draw.set_update();
            this.g_draw.repaint();
        }
        catch (NumberFormatException e){
            JOptionPane.showMessageDialog(this.frame, "Error: Weight should be double.","Add Edge", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void open_help(){
        JEditorPane ep =new JEditorPane("text/html",
                "<html><body><H2>Visit on project github readme for help and usage.</H2><br /> <br />"
                + "<H3><a href=\"https://github.com/MoriahDavid/OOP-Ex2/blob/main/README.md\">Project GitHub</a></H3><br /><br /></body></html>");
        ep.setEditable(false);
        ep.addHyperlinkListener(new HyperlinkListener() {
            @Override
            public void hyperlinkUpdate(HyperlinkEvent e) {
                if (e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED)) {openWebpage(""+e.getURL());}
            }});
        String[] buttons= {"Close"};
        JOptionPane.showOptionDialog(null,ep,"Help",JOptionPane.DEFAULT_OPTION,JOptionPane.PLAIN_MESSAGE,null,buttons,buttons[0]);
    }
    private static void openWebpage(String urlString) {
        try {
            Desktop.getDesktop().browse(new URL(urlString).toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void algo_is_connected(){
        boolean i_c = this.algo.isConnected();
        String msg = "The Graph is ";
        if(!i_c) msg += "not ";
        JOptionPane.showMessageDialog(this.frame, msg+"connected",
                "Algorithm: Is Connected", JOptionPane.INFORMATION_MESSAGE);
    }

    private void algo_center(){
        NodeData n_c = this.algo.center();
        if(n_c == null){
            String m = "The graph is not connected";
            JOptionPane.showMessageDialog(this.frame, m,"Algorithm: Center", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String msg = "The Center Node is "+ n_c.getKey();

        JOptionPane.showMessageDialog(this.frame, msg, "Algorithm: Center", JOptionPane.INFORMATION_MESSAGE);
    }

    private void select_all_for_tsp(){
        this.g_draw.set_all_selected_tsp();
    }
    private void remove_all_for_tsp(){
        this.g_draw.clear_selected_tsp();
    }

    private void algo_tsp(){
        List<NodeData> s = this.g_draw.get_selected_tsp();
        if(s == null || s.isEmpty()){
            JOptionPane.showMessageDialog(this.frame, "Error: There is no selected nodes for TSP\n\nSelect by right click on node or select all node on 'Graph' menu.","Algorithm: TSP", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(s.size() < 2){
            JOptionPane.showMessageDialog(this.frame, "Error: Need at least 2 nodes for TSP\n\nSelect by right click on node or select all node on 'Graph' menu.","Algorithm: TSP", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<NodeData> l = this.algo.tsp(s);
        List<EdgeData> edges = new ArrayList<>();
        double w = 0;
        for(int i=0; i<l.size()-1;i++){
            EdgeData e = this.algo.getGraph().getEdge(l.get(i).getKey(), l.get(i+1).getKey());
            if(e!=null){
                edges.add(e);
                w+=e.getWeight();
            }
        }
        g_draw.set_marked_edges(edges);

        String m = "Start from: " + l.get(0).getKey() + "\n Path len: ";
        JOptionPane.showMessageDialog(this.frame, m+w,"Algorithm: TSP", JOptionPane.INFORMATION_MESSAGE);
    }

    private void algo_shorted_path(){
        NodeData src=g_draw.get_selected_src(), dest=g_draw.get_selected_dest();
        if(src == null || dest == null){
            String m = "Need to choose src and dest nodes.\nRight click on node and select.";
            JOptionPane.showMessageDialog(this.frame, m,"Algorithm: Shortest Path", JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<NodeData> l = this.algo.shortestPath(src.getKey(), dest.getKey());
        if(l == null) {
            JOptionPane.showMessageDialog(this.frame, "There is no path.","Algorithm: Shortest Path", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<EdgeData> edges = new ArrayList<>();
        double w = 0;
        for(int i=0; i<l.size()-1;i++){
            EdgeData e = this.algo.getGraph().getEdge(l.get(i).getKey(), l.get(i+1).getKey());
            if(e!=null){
                edges.add(e);
                w+=e.getWeight();
            }
        }
        g_draw.set_marked_edges(edges);

        String m = "Path len: "+w;
        String t = "Algorithm: Shortest Path ("+src.getKey()+" -> "+dest.getKey()+")";
        JOptionPane.showMessageDialog(this.frame, m,t, JOptionPane.INFORMATION_MESSAGE);
    }

    private void open_graph_file(){
        JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getDefaultDirectory());
        File workingDirectory = new File(System.getProperty("user.dir"));
        j.setCurrentDirectory(workingDirectory);

        // set a title for the dialog
        j.setDialogTitle("Load Graph");

        // only allow files of .txt extension
        j.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter restrict = new FileNameExtensionFilter("Only .json files", "json");
        j.addChoosableFileFilter(restrict);

        // invoke the showsSaveDialog function to show the save dialog
        int r = j.showOpenDialog(null);

        // if the user selects a file
        if (r == JFileChooser.APPROVE_OPTION) {
            String path = j.getSelectedFile().getPath();
            if(this.algo.load(path)){
                set_title(path);
                System.out.println("Loaded Successfully :" + path);
                if(this.algo.getGraph().nodeSize() > 100){
                    JOptionPane.showMessageDialog(this.frame, "Graph have too many nodes.\nThe GUI may run slow.",
                            "Warning", JOptionPane.WARNING_MESSAGE);
                }
                this.g_draw.update(this.algo.getGraph());
            }
            else{
                System.out.println("Could Not Load :" + path);
                JOptionPane.showMessageDialog(this.frame, "The file is not valid graph json",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private void set_title(String title){
        this.title = title;
        this.frame.setTitle(title);
    }

    private void save_graph_file(){
        JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getDefaultDirectory());
        File workingDirectory = new File(System.getProperty("user.dir"));
        j.setCurrentDirectory(workingDirectory);

        // set a title for the dialog
        j.setDialogTitle("Save Graph");

        // only allow files of .txt extension
        j.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter restrict = new FileNameExtensionFilter("Only .json files", "json");
        j.addChoosableFileFilter(restrict);

        // invoke the showsSaveDialog function to show the save dialog
        int r = j.showSaveDialog(null);

        // if the user selects a file
        if (r == JFileChooser.APPROVE_OPTION) {
            String path = j.getSelectedFile().getPath();
            if(this.algo.save(path)){
                System.out.println("Saved Successfully :" + path);
            }
            else{
                System.out.println("Could Not save :" + path);
                JOptionPane.showMessageDialog(this.frame, "Could not save the graph",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {

        //        DirectedWeightedGraph g = new BaseDirectedWeightedGraph();
        //        g.addNode(new BaseNodeData(key1, 0, "", 0, new BaseGeoLocation(100, 100)));
        //        g.addNode(new BaseNodeData(2, 0, "", 0, new BaseGeoLocation(300, 300)));
        //        g.connect(key1,2, 10);
        //        g.connect(2, key1, 30);

        BaseDirectedWeightedGraphAlgo a = new BaseDirectedWeightedGraphAlgo();
        a.load("data\\G1.json");

        GuiController g = new GuiController(a);
        g.show();

    }
}
