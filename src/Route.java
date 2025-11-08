package src;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Route {
    private final List<Integer> seq;

    public Route() {
        this.seq = new ArrayList<>();
        this.seq.add(0);
        this.seq.add(0);
    }

    public Route(List<Integer> seq) {
        this.seq = new ArrayList<>(seq);
    }

    public List<Integer> sequence() {
        return Collections.unmodifiableList(seq);
    }

    public int size() {
        return seq.size();
    }

    public int get(int i) {
        return seq.get(i);
    }

    public void set(int i, int val) {
        seq.set(i, val);
    }

    public void add(int i, int val) {
        seq.add(i, val);
    }

    public void remove(int i) {
        seq.remove(i);
    }

    public Route copy() {
        return new Route(this.seq);
    }

    @Override
    public String toString() {
        return seq.toString();
    }
}
