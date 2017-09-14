# Dictionary Vert.x-web Server

This is a `vertx-web` HTTP server witch holds a dictionary when receiving a word it 
returns the closest previously inserted word by value and by lexical order

To run:

```
mvn org.codehaus.mojo:exec-maven-plugin:exec -Dexec.executable=java -Dexec.args="-cp %classpath io.vertx.core.Launcher run server.Server"
```

To test:

```
send an http post request to http://localhost:8080/analyze
with key "text" and value as the input word 
```
