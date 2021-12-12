import api.BaseDirectedWeightedGraphAlgo;
import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import gui.GuiController;

import java.io.File;

/**
 * This class is the main class for Ex2
 */
public class Ex2 {
    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraph getGrapg(String json_file) {
        DirectedWeightedGraphAlgorithms algo = getGrapgAlgo(json_file);

        DirectedWeightedGraph ans = algo.getGraph();

        return ans;
    }
    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraphAlgorithms getGrapgAlgo(String json_file) {
        DirectedWeightedGraphAlgorithms ans = new BaseDirectedWeightedGraphAlgo();
        ans.load(json_file);

        return ans;
    }
    /**
     * This static function will run your GUI using the json fime.
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     *
     */
    public static void runGUI(String json_file) {
        DirectedWeightedGraphAlgorithms a = getGrapgAlgo(json_file);

        GuiController g = new GuiController(a, json_file);
        g.show();
    }

    public static void main(String[] args){
        if(args.length == 0){
            DirectedWeightedGraphAlgorithms a =  new BaseDirectedWeightedGraphAlgo();
            GuiController g = new GuiController(a, "New Graph");
            g.show();
        }
        if(args.length == 1){
            if(!new File(args[0]).exists()) {
                System.out.println("Error: File '" + args[0] + "' not Exist.");
                return;
            }
            runGUI(args[0]);
        }
        else{
            System.out.println("Usage:  java -jar Ex2.jar {graph_json_path}.");
        }
    }
}