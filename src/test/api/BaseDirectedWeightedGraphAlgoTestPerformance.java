package api;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

class BaseDirectedWeightedGraphAlgoTestPerformance {

    String[] paths = {"data/G1.json", "data/G2.json", "data/G3.json",
            "data/1000Nodes.json", "data/10000Nodes.json", "data/100000.json"};

    @Test
    void algoPerformanceIsConnected() {
        BaseDirectedWeightedGraphAlgo algo = new BaseDirectedWeightedGraphAlgo();
        int timeout = 60;

        for (String path : paths) {
            if (algo.load(path)) {
                System.out.print("isConnected (" + path + ") - ");
                try {
                    long t = System.currentTimeMillis();
                    boolean f = timedCall(() -> algo.isConnected(), timeout, TimeUnit.SECONDS);
                    System.out.println((System.currentTimeMillis() - t) + "ms");
                    assertTrue(f);
                } catch (InterruptedException | ExecutionException | TimeoutException e) {
                    System.out.println("Timed out (" + timeout + " Sec)");
                }
            } else {
                System.out.println("File not found (" + path + ")");
            }
        }
    }

    @Test
    void algoPerformanceCenter() {
        BaseDirectedWeightedGraphAlgo algo = new BaseDirectedWeightedGraphAlgo();
        int timeout = 60*15;

        for (String path : paths) {
            if (algo.load(path)) {
                System.out.print("Center (" + path + ") - ");
                try {
                    long t = System.currentTimeMillis();
                    NodeData n = timedCall(() -> algo.center(), timeout, TimeUnit.SECONDS);
                    System.out.println((System.currentTimeMillis() - t) + "ms");
                    assertNotNull(n);
                } catch (InterruptedException | ExecutionException | TimeoutException e) {
                    System.out.println("Timed out (" + timeout + " Sec)");
                }
            } else {
                System.out.println("File not found (" + path + ")");
            }
        }
    }

    @Test
    void algoPerformanceTSP() {
        BaseDirectedWeightedGraphAlgo algo = new BaseDirectedWeightedGraphAlgo();
        int timeout = 60;

        for (String path : paths) {
            if (algo.load(path)) {
                List<NodeData> l = new ArrayList<>();
                algo.getGraph().nodeIter().forEachRemaining((n) -> l.add(n));

                System.out.print("TSP (" + path + ") - ");
                try {
                    long t = System.currentTimeMillis();
                    List<NodeData> r = timedCall(() -> algo.tsp(l), timeout, TimeUnit.SECONDS);
                    System.out.println((System.currentTimeMillis() - t) + "ms");
                    assertTrue(l.size() <= r.size());
                } catch (InterruptedException | ExecutionException | TimeoutException e) {
                    System.out.println("Timed out (" + timeout + " Sec)");
                }
            } else {
                System.out.println("File not found (" + path + ")");
            }
        }
    }

    @Test
    void algoPerformanceShortestPath() {
        BaseDirectedWeightedGraphAlgo algo = new BaseDirectedWeightedGraphAlgo();
        int timeout = 60;

        for (String path : paths) {
            if (algo.load(path)) {
                System.out.print("ShortestPath (" + path + ") - ");
                try {
                    long t = System.currentTimeMillis();
                    List<NodeData> r = timedCall(() -> algo.shortestPath(1,10), timeout, TimeUnit.SECONDS);
                    System.out.println((System.currentTimeMillis() - t) + "ms");
                    assertNotNull(r);
                } catch (InterruptedException | ExecutionException | TimeoutException e) {
                    System.out.println("Timed out (" + timeout + " Sec)");
                }
            } else {
                System.out.println("File not found (" + path + ")");
            }
        }
    }


    private static <T> T timedCall(Callable c, long timeout, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        FutureTask<T> task=new FutureTask(c);
        new Thread(task).start();
        return task.get(timeout,timeUnit);
    }
    private static void timedCallVoid(Callable c, long timeout, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        FutureTask task=new FutureTask(c);
        new Thread(task).start();
        task.get(timeout,timeUnit);
    }
}
