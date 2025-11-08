package src;

import java.util.List;

public final class Main {

    public static void main(String[] args) {
        int[] sizes = new int[] { 200, 500, 1000 };
        int trials = 3;
        int kMax = 40;
        int passes = 2;

        long allStart = System.nanoTime();
        try {
            System.out.println("n,elapsed_s,total_distance,late_violations");

            for (int n : sizes) {
                for (int t = 0; t < trials; t++) {

                    Instance inst = InstanceGenerator.generate(n, /* horizon */ 200.0, /* seed */ 42 + t);

                    long t0 = System.nanoTime();
                    List<Route> routes = DCSolver.solve(inst, kMax, passes);
                    double elapsed = (System.nanoTime() - t0) / 1e9;

                    double dist = RouteCost.totalDistance(routes, inst);
                    int late = countViolations(routes, inst);

                    System.out.printf("%d,%.3f,%.3f,%d%n", n, elapsed, dist, late);

                }
            }

            long allEnd = System.nanoTime();
            double totalSec = (allEnd - allStart) / 1e9;
            System.out.printf("DONE: all runs completed in %.3f s%n", totalSec);
        } finally {
            // ensure the final line is flushed and the JVM exits
            System.out.flush();
            System.err.flush();
            System.exit(0);
        }
    }

    private static int countViolations(List<Route> routes, Instance inst) {
        int v = 0;
        var idMap = RouteCost.idIndex(inst);
        for (Route r : routes) {
            if (!VRPTWFeasibility.feasible(r, inst, idMap))
                v++;
        }
        return v;
    }
}
