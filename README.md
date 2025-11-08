````markdown
# Divide and Conquer — Java VRPTW Demo

This is a small Java demo that implements the divide-and-conquer ideas for a Vehicle Routing Problem with Time Windows (VRPTW) described in the project. It provides:

- A tiny synthetic instance generator (customers + depot with time windows).
- A recursive spatial partitioner (k-d style median splits) that divides customers into clusters of bounded size.
- A simple demo `Main` that generates an instance, partitions it, and prints cluster summaries.

Project layout

- DivideAndConquer/src/Customer.java
- DivideAndConquer/src/Depot.java
- DivideAndConquer/src/Instance.java
- DivideAndConquer/src/InstanceGenerator.java
- DivideAndConquer/src/Partitioner.java
- DivideAndConquer/src/Main.java

Requirements

- JDK 11+ (any recent JDK 11/17/21 will work)
- No external dependencies

Compile & run (quick guide)

1. From the project root compile all Java files:

   ```bash
   # compile, output classes to 'out' directory
   javac -d out DivideAndConquer/src/*.java
   ```
````

2. Run the demo:

   ```bash
   # run default: 500 customers, kMax=32
   java -cp out Main

   # or specify arguments: <nCustomers> <kMax> [seed]
   java -cp out Main 500 32 42
   ```

What the demo prints

- It prints the depot information, number of generated customers, number of clusters created, the time for partitioning, and a short listing of each cluster (size, bounding box, and up to 3 example customers).

Example output snippet

```
Generating instance with n=500, kMax=32, seed=42
Depot: Depot{id=0, x=0.5000, y=0.5000, [0.00,10.00]}
Total customers generated: 500
Partitioned into 16 clusters in 0.012 s
Cluster 0: size=32, bbox=[0.001,0.002] x [0.010,0.123]
  Customer{id=1, x=0.0123, y=0.0456, s=0.050, [0.23,2.34]}
  Customer{id=2, x=0.0345, y=0.0678, s=0.030, [0.12,1.90]}
  ...

```
