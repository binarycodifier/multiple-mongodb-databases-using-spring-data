Multiple MongoDB databases using Spring Data example
====================================================

This example shows how to interact with multiple MongoDB databases using Spring Data.

It employs the analogy of a market with products it has to offer. The products and market are separate entities and on purpose reside in separate data stores.

Interestingly this is very similar to a very real world market, in a real world stock exchange (market) people/companies purchase instruments (products).
Market participants do not need to know everything about the instrument, rather they need to identify the instrument and they are typically concerned about the price on the day.
Most of these instruments are referenced using well known identifiers e.g. CUSIP, ISIN, etc.


Databases
---------
* Market
* Product

Collection names are the same as the database names.


References:
----------
* [MongoDB] (http://www.mongodb.org)
* [Spring Data MongoDB] (http://www.springsource.org/spring-data/mongodb)
* [Stock Exchanges](http://en.wikipedia.org/wiki/Stock_exchange)
* [Instrument](http://en.wikipedia.org/wiki/Financial_instrument)
* [ISIN](http://en.wikipedia.org/wiki/International_Securities_Identifying_Number)