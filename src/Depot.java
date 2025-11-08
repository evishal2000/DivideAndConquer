package src;

public class Depot {
    private final int id;
    private final double x;
    private final double y;
    private final double a;
    private final double b;

    public Depot(int id, double x, double y, double a, double b) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.a = a;
        this.b = b;
    }

    public int getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    @Override
    public String toString() {
        return String.format("Depot{id=%d, x=%.4f, y=%.4f, [%.2f,%.2f]}",
                id, x, y, a, b);
    }
}