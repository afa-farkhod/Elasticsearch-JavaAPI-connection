# Elasticsearch-JavaAPI-connection
This repository shows how to set elastic stack and check each of them, also shows basic connection between JavaAPI and Elasticsearch server.

### Elastic stack installation guide:

`Elasticsearch` install from the following link: https://www.elastic.co/downloads/elasticsearch
Go inside the following folder: `c:\elastic-stack\elasticsearch-8.7.1\bin` and before running it, disable the security configuration in the following file: `c:\elastic-stack\elasticsearch-8.7.1\config\elasticsearch.yml`


----------------------------

### Java Rest API Connection (Low Level Client & High Level Client)

Elasticsearch official documentation: https://www.elastic.co/guide/en/elasticsearch/client/java-api-client/current/index.html

In this repository we'll run two demos: first is to run the elasticsearch server simply on localhost, and second one is to run elasticsearch inside the docker container.

1) After donwloading elasticsearch and kibana from its official website (https://www.elastic.co/kr/downloads/), we run them locally:

first we bring up the elasticsearch server:

![image](https://user-images.githubusercontent.com/24220136/227844385-3d872be4-1833-4288-8561-3d0d23bac9e2.png)

After successful run, we can check elasticsearch on port:9200 (localhost:9200), if it runs without issue output should look like as following:

![image](https://user-images.githubusercontent.com/24220136/227844513-f5be5821-12c1-4276-a90d-15c1c95fef2b.png)

Then we bring up the kibana:

![image](https://user-images.githubusercontent.com/24220136/227844638-578756ad-0ce4-4165-943e-35328ae0bd5b.png)


after running kibana without issue, we can check it on port:5601 (localhost:5601)

![image](https://user-images.githubusercontent.com/24220136/227845001-78c50910-1e30-4d0e-b64a-5554631970cf.png)

In kibana (localhost:5601) 'Dev Tools' we create sample data and put in to elasticsearch database:

![image](https://user-images.githubusercontent.com/24220136/227845470-9805635e-dc30-4110-a5c1-c0f9b51953b3.png)

Now we open Java API inside: ElasticAPI\src\main\java\ElasticAPI\ElasticAPI\QueryLocalHost.java
Then run search for the key word "Uzbekistan", out shows the data which is on elasticsearch server. (ignore the error message, it is just saying that Log4j2 should be updated, api itself works fine):

![image](https://user-images.githubusercontent.com/24220136/227845751-e5aeb7c1-6de0-4591-8a6f-e0375da83407.png)

2. Now we check the same java api with the elasticsearch running inside the docker container. First, we run docker, and bring the elasticsearch docker-compose.yml file:

![image](https://user-images.githubusercontent.com/24220136/227846350-d2fc6be7-e676-4dd4-9038-ba248b6e67f3.png)

We can see our running servers inside the docker containers with the following command:

![image](https://user-images.githubusercontent.com/24220136/227846458-ea87f3dd-fc05-4904-b5e4-7ae8544d99b6.png)

We can check elasticsearch on port:9200 (if you pay attention to cluster_name: "docker-cluster")

![image](https://user-images.githubusercontent.com/24220136/227846675-a1fc845e-7bb8-4bf5-8d64-19c5ebed703e.png)

Then we open the kibana on the same port:5601, then check with api(java api: ElasticAPI\src\main\java\ElasticAPI\ElasticAPI\QueryLocalHostDocker.java) connection, everything works fine. 

