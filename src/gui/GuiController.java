package gui;

import api.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
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

//        menuItem = new JMenuItem("Both text and icon", new ImageIcon("images/middle.gif"));
//        menuItem.setMnemonic(KeyEvent.VK_B);
//        menu.add(menuItem);
//
//        menuItem = new JMenuItem(new ImageIcon("images/middle.gif"));
//        menuItem.setMnemonic(KeyEvent.VK_D);
//        menu.add(menuItem);

        //a group of radio button menu items
//        menu.addSeparator();
//        ButtonGroup group = new ButtonGroup();
//        rbMenuItem = new JRadioButtonMenuItem("A radio button menu item");
//        rbMenuItem.setSelected(true);
//        rbMenuItem.setMnemonic(KeyEvent.VK_R);
//        group.add(rbMenuItem);
//        menu.add(rbMenuItem);
//
//        rbMenuItem = new JRadioButtonMenuItem("Another one");
//        rbMenuItem.setMnemonic(KeyEvent.VK_O);
//        group.add(rbMenuItem);
//        menu.add(rbMenuItem);

//        //a group of check box menu items
//        menu.addSeparator();
//        cbMenuItem = new JCheckBoxMenuItem("A check box menu item");
//        cbMenuItem.setMnemonic(KeyEvent.VK_C);
//        menu.add(cbMenuItem);
//
//        cbMenuItem = new JCheckBoxMenuItem("Another one");
//        cbMenuItem.setMnemonic(KeyEvent.VK_H);
//        menu.add(cbMenuItem);

//        //a submenu
//        menu.addSeparator();
//        submenu = new JMenu("A submenu");
//        submenu.setMnemonic(KeyEvent.VK_S);
//
//        menuItem = new JMenuItem("An item in the submenu");
//        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.ALT_MASK));
//        submenu.add(menuItem);
//
//        menuItem = new JMenuItem("Another item");
//        submenu.add(menuItem);
//        menu.add(submenu);

        //Build second menu in the menu bar.
        menu = new JMenu("Graph");
        menu.setMnemonic(KeyEvent.VK_N);

        menuItem = new JMenuItem("Add Node", KeyEvent.VK_T);
        menu.add(menuItem);

        menuItem = new JMenuItem("Delete Node", KeyEvent.VK_T);
        menuItem.addActionListener((event) -> this.delete_node());
        menu.add(menuItem);
        menu.addSeparator();

        menuItem = new JMenuItem("Add Edge", KeyEvent.VK_T);
        menu.add(menuItem);

        menuItem = new JMenuItem("Delete Edge", KeyEvent.VK_T);
        menu.add(menuItem);

        menuItem = new JMenuItem("Change Edge Wight", KeyEvent.VK_T);
        menu.add(menuItem);

        menuBar.add(menu);


        menu = new JMenu("Algorithms");
        menuItem = new JMenuItem("Is Connected", KeyEvent.VK_T);
        menu.add(menuItem);

        menuItem = new JMenuItem("Shorted Path", KeyEvent.VK_T);
        menu.add(menuItem);

        menuItem = new JMenuItem("Center", KeyEvent.VK_T);
        menu.add(menuItem);

        menuItem = new JMenuItem("TSP", KeyEvent.VK_T);
        menu.add(menuItem);

        menuBar.add(menu);

        frame.setJMenuBar(menuBar);
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

    private void delete_node(){
        NodeData n = choose_node();
        if(n == null) return;
        if(this.algo.getGraph().removeNode(n.getKey()) != null){

        }
        else{

        }
    }

    private NodeData choose_node(){

        JFrame f = new JFrame("Choose Node");
        //set size and location of frame
        f.setSize(300, 200);
        f.setLocation(100, 100);

        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel labelM = new JLabel("Select the wanted Node");
        labelM.setBounds(0, 0, 200, 30);

//        j.

        JButton b = new JButton("Select Node");
//        b.

        JLabel labelN = new JLabel("The selected node id: "+this.g_draw.getLastNodeClicked().getKey());
        labelM.setBounds(50, 150, 200, 30);

        f.add(labelM);
        f.add(b);
        f.add(labelN);

        f.setLayout(null);
        f.setVisible(true);

        return this.g_draw.getLastNodeClicked();
    }

    public static void main(String[] args){

        //        DirectedWeightedGraph g = new BaseDirectedWeightedGraph();
        //        g.addNode(new BaseNodeData(key1, 0, "", 0, new BaseGeoLocation(100, 100)));
        //        g.addNode(new BaseNodeData(2, 0, "", 0, new BaseGeoLocation(300, 300)));
        //        g.connect(key1,2, 10);
        //        g.connect(2, key1, 30);

        BaseDirectedWeightedGraphAlgo a = new BaseDirectedWeightedGraphAlgo();
        a.load("data\\G2.json");

        GuiController g = new GuiController(a);
        g.show();
    }
}
