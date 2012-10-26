Multiple MongoDB databases using Spring Data example
====================================================

This example shows how to interact with multiple MongoDB databases using Spring Data.

It employs the analogy of a market with products it has to offer. The currency, products and market are separate entities and on purpose reside in separate data stores.
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


Branch "spring-data-mongodb-1.2.0.BUILD-SNAPSHOT_dbref_association" (bleeding edge)
------------------------------------------------------------------
The product to market association is by id, meaning that product is saved in it's own database and referenced by id in the market collection.
Currency is associated to product using single direct DbRef association.

This is exactly what we want for both market and product, however tests fail when retrieving the associated product entities on market and associated single currency on product.
It seems to be a fault at the MongoDB java driver (2.9.1) level because when saving and retrieving using this driver, saving works but retrieving does not.
The retrieval mechenism of the driver does not return the correct DB reference for the DbRef associated.

Options to fix:
--------------
* Fork the MongoDB java driver and change mechanics to get the correct DB when retrieving DbRef (preferred)
* Fork spring data MongoDB and change call routines to explicit fetch method and inject specific DB


References:
----------
* [MongoDB] (http://www.mongodb.org)
* [Spring Data MongoDB] (http://www.springsource.org/spring-data/mongodb)
* [Stock Exchanges](http://en.wikipedia.org/wiki/Stock_exchange)
* [Instrument](http://en.wikipedia.org/wiki/Financial_instrument)
* [ISIN](http://en.wikipedia.org/wiki/International_Securities_Identifying_Number)