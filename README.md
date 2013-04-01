(Head)LineApp
=============

A Grails application to manage and distribute informations about filmed headlines.


Requirements
------------
* Java SDK 1.5+ (for Grails 1.2 or greater)
* Grails 1.3.8 ([http://grails.org](http://grails.org))
* Maven 2.x or 3.x ([http://maven.apache.org](http://maven.apache.org))


Run
---

1. Create a `GRAILS_HOME` environment variable that points to the path of the Grails distribution and add the `bin` folder in the Grails distribution to the `PATH` environment variable

    `set GRAILS_HOME=C:\temp\Grails\grails-1.3.8`
	
    `set PATH=%PATH%;%GRAILS_HOME%\bin`

2. Create a `JAVA_HOME` environment variable that points to the location of your JDK

    `set JAVA_HOME=C:\Programme\Java\jdk1.6.0_16`

3. Copy third-party libraries with Maven

    `mvn dependency:copy-dependencies -DoutputDirectory=lib` or use `copyLibs.bat`

4. Run `grails run-app` in the `lineapp` directory

5. Browse to [http://localhost:8080/lineapp](http://localhost:8080/lineapp)

6. Login as administrator (admin:geheim) or user (jdoe:passwort)

7. Celebrate
