package src;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class RouteCost {

    private RouteCost() {
    }

    public static double distance(Route r, Instance inst) {
        double sum = 0.0;
        for (int i = 0; i < r.size() - 1; i++) {
            int a = r.get(i);
            int b = r.get(i + 1);
            double[] A = coord(a, inst);
            double[] B = coord(b, inst);
            sum += hypot(A[0] - B[0], A[1] - B[1]);
        }
        return sum;
    }

    public static double totalDistance(List<Route> routes, Instance inst) {
        return routes.stream().mapToDouble(rt -> distance(rt, inst)).sum();
    }

    private static double[] coord(int id, Instance inst) {
        if (id == 0)
            return new double[] { inst.getDepot().getX(), inst.getDepot().getY() };
        Customer c = lookup(id, inst);
        return new double[] { c.getX(), c.getY() };
    }

    private static Customer lookup(int id, Instance inst) {
        for (Customer c : inst.getCustomers())
            if (c.getId() == id)
                return c;
        throw new IllegalArgumentException("Unknown customer id " + id);
    }

    private static double hypot(double dx, double dy) {
        return Math.hypot(dx, dy);
    }

    public static Map<Integer, Customer> idIndex(Instance inst) {
        return inst.getCustomers().stream().collect(Collectors.toMap(Customer::getId, c -> c));
    }

    public static double distanceList(List<Route> routes, Instance inst) {
        return totalDistance(routes, inst);
    }

}
