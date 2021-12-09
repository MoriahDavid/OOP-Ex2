package gui;

import api.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.event.KeyEvent;
import java.io.File;

public class GuiController {

    private DirectedWeightedGraphAlgorithms algo;
    private GraphDraw g_draw;
    private JFrame frame;

    public GuiController(DirectedWeightedGraphAlgorithms algo){
        this.algo = algo;
    }

    public void show(){

        this.frame = new JFrame();
        frame.getContentPane();

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
        menu.add(menuItem);

        menuItem = new JMenuItem("Add Edge", KeyEvent.VK_T); // TODO: AddEdge
        menu.add(menuItem);

        menuBar.add(menu);

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


        menu = new JMenu("Algorithms");
        menuItem = new JMenuItem("Is Connected", KeyEvent.VK_T);
        menuItem.addActionListener((e) -> algo_is_connected());
        menu.add(menuItem);

        menuItem = new JMenuItem("Shorted Path", KeyEvent.VK_T); // TODO: add option
        menu.add(menuItem);

        menuItem = new JMenuItem("Center", KeyEvent.VK_T);
        menuItem.addActionListener((e) -> algo_center());
        menu.add(menuItem);

        menuItem = new JMenuItem("TSP", KeyEvent.VK_T); // TODO: add option
        menu.add(menuItem);

        menuBar.add(menu);


        menu = new JMenu("Help");
        menuItem = new JMenuItem("Help", KeyEvent.VK_T);
        menu.add(menuItem);

        menu.addSeparator();

        menuItem = new JMenuItem("About", KeyEvent.VK_T);
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
            JOptionPane.showMessageDialog(this.frame, "The graph is not connected",
                    "Algorithm: Center", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String msg = "The Center Node is "+ n_c.getKey();

        JOptionPane.showMessageDialog(this.frame, msg,
                "Algorithm: Center", JOptionPane.INFORMATION_MESSAGE);
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
                System.out.println("Loaded Successfully :" + path);
                this.g_draw.update(this.algo.getGraph());
            }
            else{
                System.out.println("Could Not Load :" + path);
                JOptionPane.showMessageDialog(this.frame, "The file is not valid graph json",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
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
    public static void main(String[] args){

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
