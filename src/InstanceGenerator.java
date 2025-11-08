package src;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class InstanceGenerator {

    private InstanceGenerator() {
    }

    public static Instance generate(int nCustomers, double horizon, double serviceMean,
            double serviceStd, long seed) {
        Random rnd = (seed == Long.MIN_VALUE) ? new Random() : new Random(seed);

        Depot depot = new Depot(0, 0.5, 0.5, 0.0, horizon);
        List<Customer> customers = new ArrayList<>(nCustomers);

        for (int i = 1; i <= nCustomers; i++) {
            double x = rnd.nextDouble();
            double y = rnd.nextDouble();
            double s = Math.max(0.0, serviceMean + serviceStd * rnd.nextGaussian());
            double distToDepot = Math.hypot(x - depot.getX(), y - depot.getY());
            double a = rnd.nextDouble() * (horizon / 2.0);
            double slack = Math.max(0.5, 3.0 * distToDepot + rnd.nextDouble() * 2.0);
            double b = Math.min(horizon, a + slack);
            customers.add(new Customer(i, x, y, s, a, b));
        }

        return new Instance(depot, customers, horizon);
    }

    public static Instance generate(int n, double horizon, long seed) {
        return generate(n, 1.0, 1.0, horizon, seed);
    }

}