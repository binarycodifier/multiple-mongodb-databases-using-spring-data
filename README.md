Multiple MongoDB databases using Spring Data example
====================================================

This example shows how to interact with multiple MongoDB databases using Spring Data.

It employs the analogy of a market with products (with currency) it has to offer. The currency, products and market are separate entities and on purpose, which reside in separate data stores.
Currency is associated directly to the product, whereas products are associated to market in the form of a collection.

Interestingly this is very similar to a very real world market, in a real world stock exchange (market) people/companies purchase instruments (products).
Market participants do not need to know everything about the instrument, rather they need to identify the instrument and they are typically concerned about the currency price on the day.
Most of these instruments are referenced using well known identifiers e.g. CUSIP, ISIN, etc.


Databases
---------
* Market
* Product
* Currency

Collection names are the same as the database names.


Issue:
------
There is an issue with retrieving DfRef associated objects in separate databases (persisting works fine).
The DbRef can be retrieved from the same database, however cannot be retrieved from another separate database.
It appears that there is an issue at the MongoDB driver (2.9.1) when retrieving, but will do more verification.


Options to fix:
--------------
* Fork the MongoDB java driver and change mechanics to get the correct DB when retrieving associated DbRef (preferred).
* Fork spring data MongoDB and change call routines to explicit fetch method with specific DB injection.


Dependency versions:
-------------------
* Spring Data MongoDb 1.1.1.RELEASE
* Mongo Java Driver 2.9.1
* Spring Framework 3.1.2.RELEASE


Branches used to verify associations
------------------------------------

* [Branch "spring-data-mongodb-1.2.0.BUILD-SNAPSHOT_dbref_association" (bleeding edge)](https://github.com/binarycodifier/multiple-mongodb-databases-using-spring-data/tree/spring-data-mongodb-1.2.0.BUILD-SNAPSHOT_dbref_association)
* [Branch "spring-data-mongodb-1.1.1.RELEASE_dbref_association"](https://github.com/binarycodifier/multiple-mongodb-databases-using-spring-data/tree/spring-data-mongodb-1.1.1.RELEASE_dbref_association)
* [Branch "spring-data-mongodb-1.1.0.M2_dbref_association"](https://github.com/binarycodifier/multiple-mongodb-databases-using-spring-data/tree/spring-data-mongodb-1.1.0.M2_dbref_association)
* [Branch "spring-data-mongodb-1.0.0.RELEASE_dbref_association_same_database"](https://github.com/binarycodifier/multiple-mongodb-databases-using-spring-data/tree/spring-data-mongodb-1.0.0.RELEASE_dbref_association_same_database)
* [Branch "spring-data-mongodb-1.0.0.RELEASE_dbref_association"](https://github.com/binarycodifier/multiple-mongodb-databases-using-spring-data/tree/spring-data-mongodb-1.0.0.RELEASE_dbref_association)
* [Branch "spring-data-mongodb-1.0.0.RELEASE_inline_association"](https://github.com/binarycodifier/multiple-mongodb-databases-using-spring-data/tree/spring-data-mongodb-1.0.0.RELEASE_inline_association)


References:
----------
* [MongoDB] (http://www.mongodb.org)
* [Spring Data MongoDB] (http://www.springsource.org/spring-data/mongodb)
* [MongoDB java driver project source] (https://github.com/mongodb/mongo-java-driver)
* [Spring Data MongoDB project source] (https://github.com/SpringSource/spring-data-mongodb)
* [Stock Exchanges](http://en.wikipedia.org/wiki/Stock_exchange)
* [Instrument](http://en.wikipedia.org/wiki/Financial_instrument)
* [ISIN](http://en.wikipedia.org/wiki/International_Securities_Identifying_Number)