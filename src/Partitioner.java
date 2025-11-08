package src;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Spatial partitioner using recursive median split (k-d tree style).
 *
 * Save as DivideAndConquer/Partitioner.java
 */
public final class Partitioner {

    private Partitioner() {
    }

    /**
     * Partition customers into clusters of size at most kMax.
     *
     * @param customers input list (will not be modified)
     * @param kMax      maximum cluster size
     * @return list of clusters
     */
    public static List<List<Customer>> recursivePartition(List<Customer> customers, int kMax) {
        List<Customer> copy = new ArrayList<>(customers);
        List<List<Customer>> clusters = new ArrayList<>();
        recSplit(copy, kMax, clusters);
        return clusters;
    }

    private static void recSplit(List<Customer> list, int kMax, List<List<Customer>> clusters) {
        if (list.size() <= kMax) {
            clusters.add(new ArrayList<>(list));
            return;
        }

        double minX = Double.POSITIVE_INFINITY, maxX = Double.NEGATIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY, maxY = Double.NEGATIVE_INFINITY;
        for (Customer c : list) {
            if (c.getX() < minX)
                minX = c.getX();
            if (c.getX() > maxX)
                maxX = c.getX();
            if (c.getY() < minY)
                minY = c.getY();
            if (c.getY() > maxY)
                maxY = c.getY();
        }

        boolean splitX = (maxX - minX) >= (maxY - minY);

        list.sort(splitX ? Comparator.comparingDouble(Customer::getX) : Comparator.comparingDouble(Customer::getY));

        int mid = list.size() / 2;
        List<Customer> left = new ArrayList<>(list.subList(0, mid));
        List<Customer> right = new ArrayList<>(list.subList(mid, list.size()));

        if (left.isEmpty() || right.isEmpty()) {
            clusters.add(new ArrayList<>(list));
            return;
        }

        recSplit(left, kMax, clusters);
        recSplit(right, kMax, clusters);
    }

    /**
     * Compute bounding box for a cluster.
     * 
     * @param cluster cluster list
     * @return array {minX, minY, maxX, maxY}
     */
    public static double[] boundingBox(List<Customer> cluster) {
        double minX = Double.POSITIVE_INFINITY, maxX = Double.NEGATIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY, maxY = Double.NEGATIVE_INFINITY;
        for (Customer c : cluster) {
            if (c.getX() < minX)
                minX = c.getX();
            if (c.getX() > maxX)
                maxX = c.getX();
            if (c.getY() < minY)
                minY = c.getY();
            if (c.getY() > maxY)
                maxY = c.getY();
        }
        return new double[] { minX, minY, maxX, maxY };
    }
}