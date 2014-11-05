Demo: Ant Colony with Jaak
==========================

## Principle of the Demo

This demo simulates ants that are foraging and pratolling.
The demo is based on the Jaak environment model.
A grid is used for modelling the world's structure.
The ants are spawn in the ant colonies.

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
>     -Dexec.args=io.sarl.demos.jaak.ants.AntColonyProblem

