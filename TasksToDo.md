# Tasks To Do

* Add more error reporting during CSV import failure, i.e., show the original line of CSV that failed to provide more context.

* More responsive:

  * https://www.smashingmagazine.com/2016/05/fluid-typography/
  
* Add "no transaction yet" to the view if there are no transactions

* Ensure that loading from CSV Import stores in database, but load from startup does not double-store in the database

  * Maybe just load all txns in constructor for Account (which will bypass the load function anyway)
