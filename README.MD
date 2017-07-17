# About
Internet Auction web application. Servlets, Tomcat, Maven, Oracle DB 11g, JDBC, HTML, JavaScript.
# Requirements
JDK 8.0 above installed.
Maven, Oracle 11g xe, SQL Developer installed.
# Installation
Open SQL Developer and create a new User with the name "Maxim".
Create a new connection under the new User.
Open MPInstall.sql file, choose the connection for the file in the right upper corner and click Run Script.
Database is now successfully configured.

Download JDBC Driver into WEB-INF/lib (ojdbc6 for Oracle 11g xe from http://www.oracle.com/technetwork/apps-tech/jdbc-112010-090769.html).
Go to project folder and run mvn install:install-file -Dfile=pathToTheProjectFolder\\OnlineMarketplaceWA\\src\\main\\webapp\\WEB-INF\\lib\\ojdbc6.jar -DgroupId=com.oracle -DartifactId=ojdbc6 -Dversion=11.2.0 -Dpackaging=jar for ojdbc6.
Read an example on https://www.mkyong.com/maven/how-to-add-oracle-jdbc-driver-in-your-maven-local-repository/

Build the project: mvn clean install.
Run the project: mvn tomcat7:run
Run your favourite browser and go to http://localhost:8888
# How to use
# Licence
# Contact
Maxim Eremin
maxim_eremin@epam.com