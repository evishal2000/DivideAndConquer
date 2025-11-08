package src;

import java.util.Map;

public final class VRPTWFeasibility {

    private VRPTWFeasibility() {
    }

    public static boolean feasible(Route route, Instance inst, Map<Integer, Customer> idMap) {
        double t = 0.0;
        for (int i = 0; i < route.size() - 1; i++) {
            int a = route.get(i);
            int b = route.get(i + 1);
            double[] A = coord(a, inst, idMap);
            double[] B = coord(b, inst, idMap);

            // travel
            t += Math.hypot(A[0] - B[0], A[1] - B[1]);

            // time window at node b (depot has infinite window)
            if (b != 0) {
                Customer c = idMap.get(b);
                double a_i = c.getA();
                double b_i = c.getB();
                if (t < a_i)
                    t = a_i; // wait
                if (t > b_i)
                    return false; // too late
                t += c.getServiceTime(); // service duration
            }
        }
        return true;
    }

    private static double[] coord(int id, Instance inst, Map<Integer, Customer> idMap) {
        if (id == 0)
            return new double[] { inst.getDepot().getX(), inst.getDepot().getY() };
        Customer c = idMap.get(id);
        return new double[] { c.getX(), c.getY() };
    }
}
