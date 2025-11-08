package src;

import java.util.List;

public class Instance {
    private final Depot depot;
    private final List<Customer> customers;
    private final double horizon;

    public Instance(Depot depot, List<Customer> customers, double horizon) {
        this.depot = depot;
        this.customers = customers;
        this.horizon = horizon;
    }

    public Depot getDepot() {
        return depot;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public double getHorizon() {
        return horizon;
    }
}