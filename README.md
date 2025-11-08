# Divide and Conquer Approach for Multivehicle Delivery Routing

## Overview

This project implements a **Divide and Conquer (D&C)** algorithm for the **Vehicle Routing Problem with Time Windows (VRPTW)** — a key challenge in logistics and transportation systems.

The algorithm partitions a delivery network into smaller **spatial clusters**, solves routing subproblems independently within each cluster, and merges results through a limited number of **cross-cluster boundary exchanges**.  
This yields **near-linear scalability** while preserving **time-window feasibility** for all deliveries.

This repository accompanies the IEEE-style paper _“Divide and Conquer Approach for Multivehicle Delivery Routing.”_

---

## Problem Context

In modern delivery systems — e-commerce, grocery delivery, and courier logistics — thousands of customers must be served daily within strict time windows.  
Each delivery vehicle starts from a depot, serves multiple customers, and returns to the depot.

The **Vehicle Routing Problem with Time Windows (VRPTW)** is NP-hard.  
Exact methods like Branch-and-Price or MILP cannot scale to thousands of deliveries in real time.

To overcome this, a **Divide and Conquer** strategy decomposes the problem into manageable geographic clusters.

### Strategy Summary

1. **Divide:** Partition customers into clusters using a spatial data structure (k-d tree).
2. **Conquer:** Solve each cluster’s local routing problem (using heuristic or dynamic programming).
3. **Combine:** Perform bounded **cross-cluster moves** to minimize total travel cost while maintaining time-window feasibility.

---

## Project Structure

```
DivideAndConquer/
│
├── src/
│ |
│ │ ├── Customer.java # Represents a customer with coordinates, service time, and time window
│ │ ├── Depot.java # Represents the central depot node
│ │ ├── Instance.java # Represents a complete VRPTW instance
│ │ ├── InstanceGenerator.java # Generates random synthetic VRPTW test data
│ │
│ |
│ │ ├── Partitioner.java # Builds spatial partitions (k-d tree) for divide step
│ │ ├── ClusterSolver.java # Solves local VRPTW subproblems inside each cluster
│ │ ├── BoundaryExchanger.java # Exchanges customers between clusters (merge step)
│ │ ├── DCSolver.java # Orchestrates divide → conquer → combine workflow
│ │
│ |
│ │ ├── VRPTWFeasibility.java # Validates time-window feasibility of routes
│ │ ├── Route.java # Stores route and arrival-time information
│ │ ├── RouteCost.java # Computes Euclidean travel cost for routes
│ │
│ | |─ Main.java # Entry point for running experiments
│ | |─ results.csv # Generated experiment output (runtime, distance, violations)
│
├── README.md # Project documentation (this file)
└── results_template.csv # Optional: Example output structure for visualization
```

## Build Instructions

### Compile

From the project root:

```bash
javac -d bin src/*.java


java -cp bin src.Main
```

## Sample Output

```
n,elapsed_s,total_distance,late_violations
200,0.48,184.2,0
500,1.10,460.8,0
1000,2.45,910.3,0
3000,8.20,2745.9,0
5000,22.70,4523.4,0
```
