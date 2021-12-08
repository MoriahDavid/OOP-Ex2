package gui;

import api.*;

import javax.swing.*;
import java.awt.*;

public class main {
    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.getContentPane();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        BaseDirectedWeightedGraphAlgo a = new BaseDirectedWeightedGraphAlgo();

        a.load("data\\G1.json");


        //create an object
//        DirectedWeightedGraph g = new BaseDirectedWeightedGraph();
        DirectedWeightedGraph g = a.getGraph();
        int key1 = 100000;
//        g.addNode(new BaseNodeData(key1, 0, "", 0, new BaseGeoLocation(1, 1,0)));
//        g.addNode(new BaseNodeData(2, 0, "", 0, new BaseGeoLocation(3, 3,0)));
//        g.connect(key1,2, 10);
//        g.connect(2, key1, 30);
        GraphDraw draw = new GraphDraw(g);

        //displaying graph
        draw.setSize(400,400);
        draw.set_scale();
        draw.setVisible(true);


        //####
        JButton button;

        button = new JButton("Button 1");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        frame.add(button, c);

        button = new JButton("Button 2");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 0;
        frame.add(button, c);

        button = new JButton("Button 3");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 2;
        c.gridy = 0;
        frame.add(button, c);

        //####


        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 700;      //make this component tall
        c.weightx = 0.0;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 1;

        frame.add(draw,c);
        JButton b = new JButton("ssdsd");

        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 0;       //reset to default
        c.weighty = 1.0;   //request any extra vertical space
        c.anchor = GridBagConstraints.PAGE_END; //bottom of space
        c.insets = new Insets(10,0,0,0);  //top padding
        c.gridx = 1;       //aligned with button 2
        c.gridwidth = 2;   //2 columns wide
        c.gridy = 2;       //third row
        frame.add(b, c);

        frame.setSize(1200,800);
    }
}
