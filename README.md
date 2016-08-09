taulukko-commons-ceu
====================

CEU (Cassandra Extended Utils) is a common util lib to connect in Cassandra database like a dbutils from Apache

To use Tauluko Maven Mirror, add in your settings.xml

	<mirror>
			<id>Central Internal</id>
			<url>http://repository.taulukko.com:8080/repository/internal</url>
			<mirrorOf>central</mirrorOf>
	</mirror>
	
	
WARNING : CASSANDRA VERSION COMPABILITY:
	
Cassandra 1.X use CEU < 2.0.0
	
Cassandra >= 2.1.X use CEU* > 2.1.X
	
	

*Is required with the first and second number versions are equals, eg: Cassandra 2.1.13 use any CEU 2.1.X versions