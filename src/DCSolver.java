package src;

import java.util.*;

/** Full D&C orchestration with P boundary passes. */
public final class DCSolver {

    public static List<Route> solve(Instance inst, int kMax, int passes) {
        // Divide
        Partitioner.Node root = Partitioner.build(inst.getCustomers(), kMax);
        List<Partitioner.Leaf> leaves = Partitioner.leaves(root);

        // Conquer
        List<List<Route>> perCluster = new ArrayList<>();
        for (Partitioner.Leaf leaf : leaves) {
            perCluster.add(ClusterSolver.solveCluster(leaf.customers, inst));
        }

        // Combine (boundary relocate moves across adjacent leaves)
        List<AbstractMap.SimpleEntry<Partitioner.Leaf, Partitioner.Leaf>> pairs = Partitioner.adjacentPairs(root);
        Map<Partitioner.Leaf, Integer> leafIndex = new HashMap<>();
        for (int i = 0; i < leaves.size(); i++)
            leafIndex.put(leaves.get(i), i);

        for (int p = 0; p < passes; p++) {
            for (AbstractMap.SimpleEntry<Partitioner.Leaf, Partitioner.Leaf> pr : pairs) {
                int i = leafIndex.get(pr.getKey());
                int j = leafIndex.get(pr.getValue());
                // A -> B then B -> A relocate attempts
                BoundaryExchanger.relocateOnce(perCluster.get(i), perCluster.get(j), inst);
                BoundaryExchanger.relocateOnce(perCluster.get(j), perCluster.get(i), inst);
            }
        }

        // flatten
        List<Route> all = new ArrayList<>();
        for (List<Route> rs : perCluster)
            for (Route r : rs)
                if (r.size() > 2)
                    all.add(r);
        return all;
    }
}
