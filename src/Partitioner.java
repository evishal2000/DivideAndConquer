package src;

import java.util.*;

public final class Partitioner {

    public static final class Leaf {
        public final List<Customer> customers;

        Leaf(List<Customer> customers) {
            this.customers = customers;
        }
    }

    public static final class Node {
        Leaf leaf;
        Node left, right;
        int depth;

        Node(Leaf leaf, Node left, Node right, int depth) {
            this.leaf = leaf;
            this.left = left;
            this.right = right;
            this.depth = depth;
        }

        boolean isLeaf() {
            return leaf != null;
        }
    }

    /** Build a k-d tree with leaves of size <= kMax. */
    public static Node build(List<Customer> customers, int kMax) {
        return buildRec(new ArrayList<>(customers), kMax, 0);
    }

    private static Node buildRec(List<Customer> pts, int kMax, int depth) {
        if (pts.size() <= kMax)
            return new Node(new Leaf(pts), null, null, depth);
        int axis = depth % 2;
        pts.sort(Comparator.comparingDouble(c -> (axis == 0 ? c.getX() : c.getY())));
        int mid = pts.size() / 2;
        List<Customer> leftPts = pts.subList(0, mid);
        List<Customer> rightPts = pts.subList(mid, pts.size());
        return new Node(null,
                buildRec(new ArrayList<>(leftPts), kMax, depth + 1),
                buildRec(new ArrayList<>(rightPts), kMax, depth + 1),
                depth);
    }

    /** Return all leaves (clusters). */
    public static List<Leaf> leaves(Node root) {
        List<Leaf> out = new ArrayList<>();
        collectLeaves(root, out);
        return out;
    }

    private static void collectLeaves(Node n, List<Leaf> out) {
        if (n == null)
            return;
        if (n.isLeaf())
            out.add(n.leaf);
        else {
            collectLeaves(n.left, out);
            collectLeaves(n.right, out);
        }
    }

    public static List<AbstractMap.SimpleEntry<Leaf, Leaf>> adjacentPairs(Node root) {
        List<AbstractMap.SimpleEntry<Leaf, Leaf>> pairs = new ArrayList<>();
        collectPairs(root, pairs);
        return pairs;
    }

    private static List<Leaf> collectPairs(Node n, List<AbstractMap.SimpleEntry<Leaf, Leaf>> pairs) {
        if (n == null)
            return Collections.emptyList();
        if (n.isLeaf())
            return Collections.singletonList(n.leaf);
        List<Leaf> L = collectPairs(n.left, pairs);
        List<Leaf> R = collectPairs(n.right, pairs);
        for (Leaf a : L)
            for (Leaf b : R)
                pairs.add(new AbstractMap.SimpleEntry<>(a, b));
        ArrayList<Leaf> both = new ArrayList<>(L);
        both.addAll(R);
        return both;
    }
}
