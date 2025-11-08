package src;

import java.util.List;
import java.util.Map;

public final class BoundaryExchanger {

    public static boolean relocateOnce(List<Route> A, List<Route> B, Instance inst) {
        Map<Integer, Customer> idMap = RouteCost.idIndex(inst);

        for (int ai = 0; ai < A.size(); ai++) {
            Route ra = A.get(ai);
            for (int pos = 1; pos < ra.size() - 1; pos++) {
                int cust = ra.get(pos);

                // remove cust from A route
                Route raRemoved = ra.copy();
                raRemoved.remove(pos);
                if (!VRPTWFeasibility.feasible(raRemoved, inst, idMap))
                    continue;

                double baseCost = RouteCost.distance(ra, inst) + RouteCost.distanceList(B, inst);
                double afterARemove = RouteCost.distance(raRemoved, inst) + RouteCost.distanceList(B, inst);

                // try inserting into any route in B
                for (int bi = 0; bi < B.size(); bi++) {
                    Route rb = B.get(bi);
                    for (int ins = 1; ins < rb.size(); ins++) {
                        Route rbCand = rb.copy();
                        rbCand.add(ins, cust);
                        if (!VRPTWFeasibility.feasible(rbCand, inst, idMap))
                            continue;
                        double newCost = RouteCost.distance(raRemoved, inst)
                                + (RouteCost.distanceList(B, inst) - RouteCost.distance(rb, inst)
                                        + RouteCost.distance(rbCand, inst));
                        if (newCost + 1e-9 < baseCost) {
                            A.set(ai, raRemoved);
                            B.set(bi, rbCand);
                            return true;
                        }
                    }
                }

                // try creating new route in B
                Route newB = new Route();
                newB.add(1, cust);
                if (VRPTWFeasibility.feasible(newB, inst, idMap)) {
                    double newCost = RouteCost.distance(raRemoved, inst)
                            + RouteCost.distanceList(B, inst) + RouteCost.distance(newB, inst);
                    if (newCost + 1e-9 < baseCost) {
                        A.set(ai, raRemoved);
                        B.add(newB);
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
