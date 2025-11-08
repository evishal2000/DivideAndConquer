package src;

import java.util.List;

/**
 * Demo application using default-package simple structure.
 *
 * Save as DivideAndConquer/Main.java
 *
 * Compile:
 * javac DivideAndConquer/*.java
 *
 * Run:
 * java -cp . Main 500 32
 */
public class Main {

    public static void main(String[] args) {
        int nCustomers = 500;
        int kMax = 32;
        long seed = 42L;

        if (args.length >= 1) {
            nCustomers = Integer.parseInt(args[0]);
        }
        if (args.length >= 2) {
            kMax = Integer.parseInt(args[1]);
        }
        if (args.length >= 3) {
            seed = Long.parseLong(args[2]);
        }

        System.out.printf("Generating instance with n=%d, kMax=%d, seed=%d%n", nCustomers, kMax, seed);
        Instance inst = InstanceGenerator.generate(nCustomers, 10.0, 0.05, 0.02, seed);

        System.out.println("Depot: " + inst.getDepot());

        List<Customer> customers = inst.getCustomers();
        System.out.println("Total customers generated: " + customers.size());

        long t0 = System.currentTimeMillis();
        List<List<Customer>> clusters = Partitioner.recursivePartition(customers, kMax);
        long t1 = System.currentTimeMillis();

        System.out.printf("Partitioned into %d clusters in %.3f s%n", clusters.size(), (t1 - t0) / 1000.0);
        int idx = 0;
        for (List<Customer> cl : clusters) {
            System.out.printf("Cluster %d: size=%d, bbox=%s%n", idx++, cl.size(), bboxString(cl));
            for (int i = 0; i < Math.min(3, cl.size()); i++) {
                System.out.println("  " + cl.get(i));
            }
        }
    }

    private static String bboxString(List<Customer> cl) {
        double minX = Double.POSITIVE_INFINITY, maxX = Double.NEGATIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY, maxY = Double.NEGATIVE_INFINITY;
        for (Customer c : cl) {
            if (c.getX() < minX)
                minX = c.getX();
            if (c.getX() > maxX)
                maxX = c.getX();
            if (c.getY() < minY)
                minY = c.getY();
            if (c.getY() > maxY)
                maxY = c.getY();
        }
        return String.format("[%.3f,%.3f] x [%.3f,%.3f]", minX, minY, maxX, maxY);
    }
}