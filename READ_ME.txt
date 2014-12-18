-----
READ ME
-----

The folder named: "felix-framework-4.4.1_20141218" contains a turn-key Modus 1.1.5-SNAPSHOT over Apache Felix.
Some values in the conf files (in the data folder in Felix have to be filled).

-----
How to deploy on DHSA's OBR (OSGi Bundle Repository) located at: http://dhsa.orangeforge.rd.francetelecom.fr/obr/dhsa/com/francetelecom/acse/

ClientTR69\trunk>cls && mvn deploy -Dmaven.test.skip -DremoteOBR -DaltDeploymentRepository=obr-dhsa::default::scp://orangeforge.rd.francetelecom.fr/home/groups/dhsa/htdocs/obr/dhsa

Extra details on: https://orangeforge.rd.francetelecom.fr/wiki/index.php?pagename=Guide%20du%20developpeur%20DHSA&group_id=2228
 