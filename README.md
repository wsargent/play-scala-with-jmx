## Play Scala with JMX

## Building

```bash
sbt docker:publishLocal
```

## Running

Start the play application:

```bash
./run-docker
```

## Admin UI
 
Diagnostics and control of the JVM is managed through Jolokia running as an agent. You can control the JVM and see how the GC is running by running Hawt in another console window:

java -jar hawt/hawtio-app-2.8.0.jar

Or by running ./run-hawt.

Once hawt is up and running, add the connection as "http://localhost:8778/jolokia" and from there you can see GC and dump JFR logs.