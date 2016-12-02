# What's this?

A simple demo of using Spark Java to power a backend API for parsing input numbers as human readable text

# How to build

Run `mvn clean install` from the root director.  It will run unit and integration tests

# How to run

After building, there will be a capsule JAR in `target` which you can run using `java -jar target/numbers-capsule.jar`

## APIs

You can see an overview of routes at [http://localhost:4567/debug/routeoverview/](http://localhost:4567/debug/routeoverview/)

You can invoke the number translator service at [http://localhost:4567/numbers/en](http://localhost:4567/numbers/en) passing in a query param `number`

## UI

The UI can be accessed [here](http://localhost:4567/)