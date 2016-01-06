# Linkcollector

Main goal of the application is to provide search results with higher
signal to noise ratio and sometimes avoid browsing Google results for hours.

Linkcollector is something like search proxy server. It takes your search
query and sends it to the Bing search engine. All results from Bing then
are scored by linkcollector and showed according to ranking.

## Requirements

 * JDK 1.8.

 * Apache Tomcat 8, for
 another server one may need some configuration tuning.

 * MySQL 5.6 or later. Using other SQL database is possible, but may require changes
in configuration.

 * Maven with access to the repository (linkcollector depends from the
 Spring framework and some libraries).

## Environment Configuration

1. Create the database and user with rights on the database. Database name,
user and password must be according to parameters in
`PersistenceJPAConfig.dataSource()` method.

## Application Configuration

The application configuration options are located in
`/src/main/resources/config`.
You should edit the `config.sample` file from sources according to provided
comments and save it as `config`.

## Installing

Use Maven to build the application. Deploy the application to your server.

## Using Linkcollector

1. On the homepage enter the search query.

2. Fill the tags table.

3. Press _Search_ button.

4. Linkcollector will send your search query to the Bing search engine. Eache result from
the Bing will be scanned for presents of the tag's text. If tag's text is present on the result
page, the page score value will be increased on the tag's weight.

5. All processed Bing results will be shown sorted by the score value.

6. You can click _Edit search_ link to edit search parameters and repeat it. Or you can 
just click _HOME_ to accomplish a new search.

## Brief of Technologies Involved

* [Spring Framework](http://spring.io/).

* [Spring Web Flow](http://projects.spring.io/spring-webflow/) for building the search page workflow.

* [Thymeleaf template engine](http://www.thymeleaf.org/) for front-end HTML generating.

* [Jsoup library](http://jsoup.org/) for processing HTML of scored web pages.

* Java EE 7 JSON processing capabilities for parsing data from the Bing and
data interchange inside the application.

* [Hibernate Validator](http://hibernate.org/validator/) for validating data of the web form.

* [Hibernate ORM](http://hibernate.org/orm/) as JPA provider.

* [MySQL](https://www.mysql.com/) for storing data.
