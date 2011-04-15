* Welcome to Bitton *

** What is Bitton? **

Bitton is a facetted browser that works over SPARQL stores. As supplied it is configured for navigating research information, however it is reasonably flexible and could be reused for other data.

** Getting Started: Prerequisites **

Bitton speaks SPARQL 1.1 over HTTP, so you need a SPARQL server. We also provide some example data to get you started.

Here we'll use [fuseki](http://www.openjena.org/wiki/Fuseki). Unzip fuseki in the bitton directory:

    $ unzip ~/Downloads/fuseki-0.2.0-20110325.114915-6.zip
    $ cd Fuseki-0.2.0-SNAPSHOT

Load data:

    $ java -cp fuseki-sys.jar tdb.tdbloader --loc DB ../example_data.nq

And finally start the server:

    $ ./fuseki-server --loc DB /research

** Running the service **

Move to bitton directory and compile it, then move to the web app directory:

    $ mvn install
    $ cd facets-spring3-freemarker

Edit the `revrev.properties` file and ensure it's pointing at your SPARQL endpoint (for local fuseki it should be correct).

    $ vi src/main/webapp/WEB-INF/classes/resrev.properties

Finally run it:

    $ mvn jetty:run

Visit [the server](http://localhost:8080/) (username: bob, password: bobspassword) and explore.