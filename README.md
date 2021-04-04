Country Search is Java / Spring Boot web application to search for countries by code, name or full name.
To run it on your local machine, perform the following steps:

Windows 10:
1. Download and install JDK https://adoptopenjdk.net/ OpenJDK 8 (LTS), HotSpot. It is required to run a Java app.
2. Download source code: https://github.com/Dmitrii-Lavlinskii/country-search/archive/refs/heads/main.zip
3. Unpack the source code to a folder, let's say c:\src\country-search\
4. Open the command prompt (Win + R, type cmd, hit Enter).
5. Navigate to the app's source folder: type `cd c:\src\country-search\`, hit enter.
6. You should see c:\src\country-search>
7. Make sure you are in the right folder. Type "dir", press Enter, see if the files list contains "gradlew". If not, double check step 3.
8. Type `gradlew bootRun`. This will start the application server. You should see the output like this:

```
Microsoft Windows [Version 10.0.19041.867]
(c) 2020 Microsoft Corporation. All rights reserved.

c:\src\country-search>gradlew bootRun

> Task :bootRun

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v2.4.4)

2021-04-04 10:43:21.461  INFO 8984 --- [           main] com.country.search.SearchApplication     : Starting SearchApplication using Java 1.8.0_282 on DESKTOP-4U48123 with PID 8984 (C:\src\country-search\build\classes\java\main started by Dima in C:\src\country-search)
2021-04-04 10:43:21.476  INFO 8984 --- [           main] com.country.search.SearchApplication     : No active profile set, falling back to default profiles: default
2021-04-04 10:43:22.148  INFO 8984 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
2021-04-04 10:43:22.148  INFO 8984 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2021-04-04 10:43:22.148  INFO 8984 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.44]
2021-04-04 10:43:22.195  INFO 8984 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2021-04-04 10:43:22.195  INFO 8984 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 688 ms
2021-04-04 10:43:22.335  INFO 8984 --- [           main] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
2021-04-04 10:43:22.382  INFO 8984 --- [           main] o.s.b.a.w.s.WelcomePageHandlerMapping    : Adding welcome page template: index
2021-04-04 10:43:22.460  INFO 8984 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2021-04-04 10:43:22.460  INFO 8984 --- [           main] com.country.search.SearchApplication     : Started SearchApplication in 1.302 seconds (JVM running for 1.612)
<==========---> 80% EXECUTING [1m 1s]
> :bootRun
```
9. Open a web browser and navigate to http://localhost:8080/
10. You should be able to see the web application interface.
11. Choose search type in the dropdown.
12. Type your search string in the search input. 
13. Press Enter or click Submit. You should be able to perform search.

To terminate the app, press Ctrl + C in the command prompt and confirm.

```
> :bootRun
Terminate batch job (Y/N)? Y

c:\src\country-search>
```

To run the tests:
1. type `gradlew test`
2. Press Enter. You should see a successful run.
3. To see more detailed output you might want to run `gradlew test -i` instead.