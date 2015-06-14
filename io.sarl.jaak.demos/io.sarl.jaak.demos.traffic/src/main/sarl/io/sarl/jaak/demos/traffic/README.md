Demo: Traffic Simulation
========================

## Principle of the Demo

This demo simulates cars that are running on a road network.
The demo is based on the Jaak environment model.
A grid is used for modelling the world's structure.
The vehicles are spawn at specific border points.

## Compiling the Demo using Maven

You need to compile the demo with Maven. Type on the command
line:

> mvn clean package

## Launching the Demo

For launching the demo, you need to launch the agent
in a Janus runtime environments.
Type the following command line:

> mvn exec:java
>     -Dexec.mainClass=io.janusproject.Boot
>     -Dexec.args=io.sarl.demos.jaak.traffic.TrafficSimulationProblem

