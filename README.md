modus-tr69-client
=================

 - [origin project site](http://modus-tr-069.sourceforge.net/)
 - this repository is a clone of SVN branch [1.1.5-SNAPSHOT/trunk]((https://sourceforge.net/p/modus-tr-069/code/HEAD/tree/branches/1.1.5-SNAPSHOT/trunk/)) available by [sourceforge.net](https://sourceforge.net/projects/modus-tr-069/)
 - the only significant changes:
   - [SSL hostname verification for ManagementServer.URL](https://github.com/p-alik/modus-tr69-client/commit/c0571595319a281afd453b1380b9d520dea0028b)
   - [add maven assemble configuration for 1.1.7-SNAPSHOT](https://github.com/p-alik/modus-tr69-client/commit/e9d79331e622f92f8f70c8425c70183d62d3a78d)

Install/Assabmle
----------------


```bash
maven clean install
maven assembly:assembly
cd target
unzip modus-clientTR69-1.1.7-SNAPSHOT-distrib.zip
```

Configuration
-------------
 - setup the bootstrap in _data/usine.txt_ and _data/config.cfg_ files
 - After modification delete _data/data.sav_

Run
---
 - HTTP ManagementServer.URL
 ```bash
java -jar bin/felix.jar
```

 - HTTPS ManagementServer.URL 
 ```bash
 java -Djsse.enableSNIExtension=false \
      -Djavax.net.ssl.trustStore="%%PATH_TO_KEYSTORE_FILE%%" \
      -Djavax.net.ssl.trustStorePassword=%%KEYSTORE_PASSWORD%%" \
      -jar bin/felix.jar
 ```
