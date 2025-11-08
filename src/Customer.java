package src;

public class Customer {
    private final int id;
    private final double x;
    private final double y;
    private final double serviceTime;
    private final double a; // earliest
    private final double b; // latest

    public Customer(int id, double x, double y, double serviceTime, double a, double b) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.serviceTime = serviceTime;
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

    public double getServiceTime() {
        return serviceTime;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    @Override
    public String toString() {
        return String.format("Customer{id=%d, x=%.4f, y=%.4f, s=%.3f, [%.2f,%.2f]}",
                id, x, y, serviceTime, a, b);
    }
}