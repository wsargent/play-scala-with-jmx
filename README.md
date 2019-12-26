## Play Scala with JMX

This is a sample Play application that exposes `HomeController` through JMX and the cyclic buffer from Logback, and renders it by querying Jolokia as a client.

This is a companion piece to [Controlling Logging in a Running JVM](https://tersesystems.com/blog/2019/12/24/controlling-logging-in-a-running-jvm/).

Note that the way that Logback work means that if you want to get a dump of debugging or tracing data while still having a controller set to INFO level, using an appender won't help.  [Ringbuffer logging](https://tersesystems.com/blog/2019/07/28/triggering-diagnostic-logging-on-exception/) through a  [turbofilter](https://github.com/tersesystems/terse-logback#appender-based-ring-buffer) is the way to go there, but that's a bit off track for this example. 

## Building

Start the play application in production mode:

```bash
sbt runProd
```

## JMX Display Options

There are three main options, [Zulu Mission Control](https://www.azul.com/products/zulu-mission-control/), [VisualVM](https://visualvm.github.io/), and [Hawt](https://hawt.io/).

The Play application will be available under `play.core.server.ProdServerStart` for the GUI tools.

### Zulu Mission Control

[Zulu Mission Control](https://www.azul.com/products/zulu-mission-control/) is a rebranded version of Java Mission Control.  MBeans functionality is out of the box.

### VisualVM

[VisualVM](https://visualvm.github.io/) is a tool for profiling and monitoring the JDK.  The MBean support is available [as a plugin](https://visualvm.github.io/plugins.html).

TabularData is rendered a little differently than in Zulu Mission Control.

### Hawt

[Hawt](https://hawt.io/) is an application server that connects to Jolokia and gives an HTML admin UI.
 
You can control the JVM and see how the GC is running by running Hawt in another console window:

```
java -jar hawt/hawtio-app-2.8.0.jar
```

Or by running ./run-hawt.

Once hawt is up and running, add the connection as "http://localhost:8778/jolokia" and from there you can see the play beans.

Note that Hawt doesn't do well when displaying tabular data, as seen through the CyclicBuffer.