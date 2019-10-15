# ElasticSearch plugin for Cloudstack

A Java Web application to search for the cloudstack logs using REST API's stored in elasticsearch cluster.

## Getting Started

Below instructions will tell you what are the prerequisites that
needs to be installed, configurations that has to be done to run the project.

### Prerequisites

Below are the softwares that needs to be installed as a dependency

```
Java 8
Maven
```

### Assumptions

It is assumed that elasticsearch is installed and configured on either remote or local machine\
from where we want to fetch the data.

If elasticsearch is not installed then you follow the steps mentioned\
[here](https://www.digitalocean.com/community/tutorials/how-to-install-elasticsearch-logstash-and-kibana-elastic-stack-on-ubuntu-18-04) 

Once everything is setup, make sure that you can reach the elasticsearch endpoint by making a get request to

```bash
curl <IP address of elasticsearch cluster>:9200

{
  "name" : "0DWGlCW",
  "cluster_name" : "cloudstack",
  "cluster_uuid" : "ZZ5NFlLBSFqOqVW3ONi5JA",
  "version" : {
    "number" : "6.8.3",
    "build_flavor" : "default",
    "build_type" : "deb",
    "build_hash" : "0c48c0e",
    "build_date" : "2019-08-29T19:05:24.312154Z",
    "build_snapshot" : false,
    "lucene_version" : "7.7.0",
    "minimum_wire_compatibility_version" : "5.6.0",
    "minimum_index_compatibility_version" : "5.0.0"
  },
  "tagline" : "You Know, for Search"
}
```

If you dont see the above output then please configure it properly and then continue.

### Installing

A step by step series of examples that tell you how to get a development env running

1 . Git clone the repo to any Linux machine.

2 . Make sure that the elasticsearch version you have installed matches with the one mentioned in ```application.properties```
```java
<elasticsearch.version>6.8.3</elasticsearch.version>
```

3 . Get the index name and the type of the document you want to fetch from the elasticsearch
```bash
$ curl <IP of elasticsearch>:9200/_cat/indices?pretty
yellow open filebeat-2019.10.11             ZbB6mlWBTb6QfKfPRfX2lw 5 1 1737458   0 550.5mb 550.5mb
```

```bash
$ curl <IP of elasticsearch>:9200/filebeat-2019.10.11/_mapping?pretty
{
  "filebeat-2019.10.11" : {
    "mappings" : {
      "doc"
```
4 . Now that we know the name of index and the type name, store those values in ```application.properties```


### Building the packages

Below are the steps to build the JAR file and to start the backend server

```bash
mvn package -DskipTests

java -jar target/cloudstack-elasticsearch-0.0.1.jar
```

To build a docker image, run the command
```bash
docker build -t cloudstack-elasticsearch .
```

Run the docker image
```bash
docker run -p 5000:9229 cloudstack-elasticsearch
```

### Accessing the end points

1 . To search all the logs in the index navigate to 
```http request
http://localhost:9229/api/v1/search/listall
```

If running in docker then navigage to
```http request
http://localhost:9229/api/v1/search/listall
```

2 . To search for a log with particular ID, navigate to
```http request
http://localhost:9229/api/v1/search/<ID>
```

If running in docker then navigate to
```http request
http://localhost:9229/api/v1/search/<ID>
```

3 . To search for a particular keyword in a message, navigate to
```http request
http://localhost:9229/api/v1/search/query/<query string>
```

4 . To display all indices, navigate to
```http request
http://localhost:9229/api/v1/index
```

## Automating the generation of the docker images
If you want to build and push the docker images to the docker hub automatically\
then add the below lines to the pom.xml

```bash
<plugin>
	<groupId>com.spotify</groupId>
	<artifactId>dockerfile-maven-plugin</artifactId>
	<version>1.4.0</version>
	<configuration>
		<repository><your dockerhub username>/<repo name></repository>
		<tag>${project.version}</tag>
		<buildArgs>
			<JAR_FILE>target/${project.build.finalName}.jar</JAR_FILE>
		</buildArgs>
	</configuration>
	<executions>
		<execution>
			<id>default</id>
			<phase>install</phase>
			<goals>
				<goal>build</goal>
				<goal>push</goal>
			</goals>
		</execution>
	</executions>
</plugin>
```

Now you can build the docker image using
```bash
mvn package dockerfile:build
```

Push the docker image using
```bash
mvn dockerfile:push
```

To do the above two steps automatically, run
```bash
mvn install
```

This will build the packages, creates docker image and uploads it to docker hub

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management


## Authors
Rakesh Venkatesh
