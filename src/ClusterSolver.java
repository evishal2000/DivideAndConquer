package src;

import java.util.*;

public final class ClusterSolver {

    public static List<Route> solveCluster(List<Customer> cluster, Instance inst) {
        Map<Integer, Customer> idMap = RouteCost.idIndex(inst);

        List<Customer> order = new ArrayList<>(cluster);
        order.sort(Comparator.comparingDouble(Customer::getB));

        List<Route> routes = new ArrayList<>();

        for (Customer cust : order) {
            int id = cust.getId();
            double bestInc = Double.POSITIVE_INFINITY;
            int bestR = -1, bestPos = -1;

            // try inserting into existing routes
            for (int rIdx = 0; rIdx < routes.size(); rIdx++) {
                Route r = routes.get(rIdx);
                for (int pos = 1; pos < r.size(); pos++) {
                    Route cand = r.copy();
                    cand.add(pos, id);
                    if (VRPTWFeasibility.feasible(cand, inst, idMap)) {
                        double inc = RouteCost.distance(cand, inst) - RouteCost.distance(r, inst);
                        if (inc < bestInc) {
                            bestInc = inc;
                            bestR = rIdx;
                            bestPos = pos;
                        }
                    }
                }
            }

            if (bestR >= 0) {
                routes.get(bestR).add(bestPos, id);
            } else {
                Route newR = new Route();
                newR.add(1, id);
                if (!VRPTWFeasibility.feasible(newR, inst, idMap)) {

                    continue;
                }
                routes.add(newR);
            }
        }

        return routes;
    }
}
