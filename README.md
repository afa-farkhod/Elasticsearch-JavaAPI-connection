# [Elasticsearch-JavaAPI-connection](https://www.elastic.co/guide/en/elasticsearch/client/java-api-client/current/installation.html)
This repository shows how to set elastic stack and check each of them, also shows basic connection between JavaAPI and Elasticsearch server.

### [Elastic stack installation guide:](https://www.elastic.co/guide/en/elastic-stack/current/installing-elastic-stack.html)

- [Beginner's Crash Course to Elastic Stack](https://youtu.be/gS_nHTWZEJ8) - Official Elastic Community. Beginner’s Crash Course is a series of workshops for all developers with little to no experience with Elasticsearch and Kibana or those who could use a refresher. By the end of this workshop, you will be able to: 
  - understand how the products of Elastic Stack work together to search, analyze, and visualize data in real time
  - understand the basic architecture of Elasticsearch
  - run CRUD operations with  Elasticsearch and Kibana 
- [Problems with access to Elasticsearch form outside machine](https://discuss.elastic.co/t/problems-with-access-to-elasticsearch-form-outside-machine/172450) - Following shows the issue with accessing Elasticsearch form outside machine.
- [Unable to remotely access ES server hosted on AWS EC2 on port 9200](https://discuss.elastic.co/t/unable-to-remotely-access-es-server-hosted-on-aws-ec2-on-port-9200/33243) - Unable to remotely access ES server hosted on AWS EC2 on port 9200. It includes some solutions
- [Elasticsearch Java API Client](https://www.elastic.co/guide/en/elasticsearch/client/java-api-client/current/index.html): The Java API client provides strongly typed requests and responses for all Elasticsearch APIs.  
- [Elasticsearch api secure using SSH tunneling](https://pramodshehan.medium.com/elasticsearch-api-secure-using-ssh-tunnels-c6e82595842f) - SSH is a method for secure remote login (encrypted secure shell connection) from one machine to another machine. Following shows how to do elasticsearch securing using ssh tunneling
- [Elasticsearch warning - ignore_throttled parameter is deprecated](https://stackoverflow.com/questions/72271872/elasticsearch-warning-ignore-throttled-parameter-is-deprecated) - [Stackoverflow](https://stackoverflow.com/) - Elasticsearch warning - ignore_throttled parameter is deprecated issued which has answer
- [Do not send default ignore_throttled parameter since it is deprecated](https://github.com/elastic/elasticsearch/pull/84827) - github discussion board about the particular issue
- [Host name does not match the certificate](https://discuss.elastic.co/t/host-name-does-not-match-the-certificate/186618) - need to disable hostname verification your in HttpAsyncClientBuilder
```
public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
  return httpClientBuilder
         .setDefaultCredentialsProvider(credentialsProvider)
         .setSSLContext(sslContext)
         .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE);
}
```
- [How to connect to elasticsearch server using rest client with IP address](https://stackoverflow.com/questions/56951310/how-to-connect-to-elasticsearch-server-using-rest-client-with-ip-address) - [Stackoverflow](https://stackoverflow.com/): The cluster coordination algorithm has changed in 7.0 234 and in order to be safe it requires some specific configuration. We relax that requirement (that is, we run in a less-safe mode) when you bind to localhost only, but if/when you change network.host we enforce that your configure the cluster safely.
- [Discovery configuration is required in production](https://www.elastic.co/guide/en/elasticsearch/reference/7.0/breaking-changes-7.0.html#breaking_70_discovery_changes) - production deployments of Elasticsearch now require at least one of the following settings to be specified in the `elasticsearch.yml` configuration file
- [k-nearest neighbor (kNN) search](https://www.elastic.co/guide/en/elasticsearch/reference/current/knn-search.html) - A k-nearest neighbor (kNN) search finds the k nearest vectors to a query vector, as measured by a similarity metric. Common use cases for kNN include:
  - Relevance ranking based on natural language processing (NLP) algorithms
  - Product recommendations and recommendation engines
  - Similarity search for images or videos
- [Vector Search](https://www.elastic.co/what-is/vector-search) - Vector search engines — known as vector databases, semantic, or cosine search — find the nearest neighbors to a given (vectorized) query. Where traditional search relies on mentions of keywords, lexical similarity, and the frequency of word occurrences, vector search engines use distances in the embedding space to represent similarity. Finding related data becomes searching for nearest neighbors of your query.
  
  ![image](https://github.com/af4092/Elasticsearch-JavaAPI-connection/assets/24220136/5a2b5c51-142c-4a75-a980-ce914dc46325)

- [Vector Search usage](https://www.datasciencebyexample.com/2023/03/18/elasticsearch-dense-vector-search/) - vector search using Elastic Search, index and search example using python requests library
- [Verifying HTTPS with a CA certificate](https://www.elastic.co/guide/en/elasticsearch/client/java-api-client/current/connecting.html) - The generated root CA certificate can be found in the certs directory in your Elasticsearch config location. If you’re running Elasticsearch in Docker there is [additional documentation](https://www.elastic.co/guide/en/elasticsearch/reference/8.8/docker.html) for retrieving the CA certificate.

![image](https://github.com/af4092/Elasticsearch-JavaAPI-connection/assets/24220136/b6f66593-2135-409a-be7d-74b3863f6201)

- [Download Elasticsearch](https://www.elastic.co/downloads/elasticsearch) <tr><img src="https://edent.github.io/SuperTinyIcons/images/svg/elastic.svg" width="35" title=""></tr>

- Before running it, disable the security configuration in the following file:  `c:\elastic-stack\elasticsearch-8.7.1\config\elasticsearch.yml` by making the configuration to `false`

![image](https://user-images.githubusercontent.com/24220136/236627245-7b96913a-51ef-4285-a644-85a204405fd1.png)

- Then go inside the following directory: `c:\elastic-stack\elasticsearch-8.7.1\bin` and run the command: `elasticsearch.bat`, then open the port: `localhost:9200` in the browser, you should see the following:

![image](https://user-images.githubusercontent.com/24220136/236627341-ada8ef31-851a-4dac-ae7f-9523bac9918e.png)

- [Download Kibana](https://www.elastic.co/kr/downloads/kibana) <tr><img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT3_RqXgpJRFZ-5KPzNMyzWJaJxwXERWjSxjA&usqp=CAU" width="30" title=""></tr> 
- Go inside the following directory: `c:\elastic-stack\kibana-8.7.1\bin` then run the command: `kibana.bat`, there is nothing to make changes in the configuration files. Just open the port: `localhost:5601` in browser, then you kibana interface is running:

![image](https://user-images.githubusercontent.com/24220136/236627447-8d64ffa1-f6f0-41f1-bbc6-73c9a5e791a8.png)

- [Download Logstash](https://www.elastic.co/kr/downloads/logstash) <tr><img src="https://elastic-content-share.eu/wp-content/uploads/edd/2020/06/logstash-logo-color.png" width="35" title=""></tr>
- Logstash is basically used as the pipeline to ingest the data to elasticsearch. So in the following we get sample from `kaggle.com` dataset website, the following is the sample dataset `flowers.csv`:

![image](https://user-images.githubusercontent.com/24220136/236627845-e8a5fa86-e5e9-4760-9010-8bdaeecd7fa2.png)

- So now we create `logstash.conf` file, and show the dataset location to ingest to elasticsearch. Before, in `flowers.csv` file we have to show the following columns: 

![image](https://user-images.githubusercontent.com/24220136/236627979-41abeadb-4218-4025-81bd-c8b8500e121e.png)

- Following is the `logstash.conf` file with the dataset path and columns names:

![image](https://user-images.githubusercontent.com/24220136/236628145-12d9bdf1-b7b2-45a8-b9e8-2ad997435924.png)

- To ingest the data, we have to be in a folder where our `logstash.conf` file is located. Then run the following command: `logstash -f logstash.conf`

![image](https://user-images.githubusercontent.com/24220136/236628258-5b135b2c-93d9-4627-a994-123f6bb039d9.png)

- Then in Kibana interface we go to Dev Tools and can check our ingested data: 

![image](https://user-images.githubusercontent.com/24220136/236628339-81aa632a-ad9c-4be2-b10b-652b752b52c2.png)

-------------------

### Elasticsearch on Linux

- We can also run the Elasticsearch, Kibana and Logstash at once inside the `docker-compose.yml` file based on `Linux` Operating System:
- Following is the `Elasticsearch` container by pulling the `elasticsearch:7.16.2` image from the Docker hub:

![image](https://user-images.githubusercontent.com/24220136/236646804-424bf108-ac1f-4a7c-a125-160caa1dfdd8.png)

- Following is `Kibana` container by pulling the `kibana:7.16.2` image from the Docker hub:

![image](https://user-images.githubusercontent.com/24220136/236646816-7c0ba095-3333-42e8-ab97-72d835a7a730.png)

- Following is `Logstash` container by pulling the `logstash:7.16.2` image from the Docker hub:

![image](https://user-images.githubusercontent.com/24220136/236646902-52215f82-5c92-4b25-ada7-b8adaa824350.png)

- Then we bring them all inside the `docker-compose.yml` file which runs the elasticsearch inside the docker container. To do so we run the command `docker-compose.up`:

![image](https://user-images.githubusercontent.com/24220136/227846350-d2fc6be7-e676-4dd4-9038-ba248b6e67f3.png)

- We can see our running servers inside the docker containers with the following command:

![image](https://user-images.githubusercontent.com/24220136/227846458-ea87f3dd-fc05-4904-b5e4-7ae8544d99b6.png)

- We can check elasticsearch on port:9200 (if you pay attention to cluster_name: "docker-cluster")

![image](https://user-images.githubusercontent.com/24220136/227846675-a1fc845e-7bb8-4bf5-8d64-19c5ebed703e.png)

-------------------------

- Don't forget to set environmental variables as following in the beginning!:

![image](https://user-images.githubusercontent.com/24220136/236627530-518d74a9-fb68-4317-b4ec-bd9dbc0b4a34.png)


----------------------------

## Java Rest API Connection (Low Level Client & High Level Client)

1. `HighLevelRestClientRemoteServer` sample inside the folder: `ElasticHighLevelRemote` - First we check Elasticsearch remote server connection with High Level Rest Client. Api allows to enter the index name, field name and value(key word), then runs the `vector search` to bring the related result(ignore the error message, it is just saying that Log4j2 should be updated, api itself works fine):  

![image](https://user-images.githubusercontent.com/24220136/236648733-45a06d69-2a83-4b37-960c-83374e9a4cbe.png)

2. `HighLevelRestClientLocalServer` sample inside the folder: `ElasticHighLevelLocal` - Now we check High Level Rest Client Api Elasticsearch local server. In the case of flowers_dataset data.

![image](https://user-images.githubusercontent.com/24220136/236648733-45a06d69-2a83-4b37-960c-83374e9a4cbe.png)

3. `LowLevelRestCLientRemoteServer` sample inside the folder: `ElasticLowLevelRemote` - is the Low Level Rest Client Api with Elasticsearch remote server: 

![image](https://user-images.githubusercontent.com/24220136/236648990-f6332097-a176-48fb-94d9-0796cf326de0.png)

4. `LowLevelRestClientLocalServer` sample inside the folder: `ElasticLowLevelLocal` - is the Low Level Rest Client Api with Elasticsearch local server:

![image](https://user-images.githubusercontent.com/24220136/236649253-39df4828-341f-49e5-9102-a8b47d74ae95.png)

5. `ElasticHighLevelRemoteHTTPS` folder includes the `ElasticsearchConnectionCheckHTTP` source code which just checks the connection with Elasticsearch server without any SSH. `ElasticsearchConnectionChekHTTPS` checks the connection with the `HTTPS` security. Remote Elasticsearch credentials must be entered. The following is the demo test run:

![image](https://github.com/af4092/Elasticsearch-JavaAPI-connection/assets/24220136/37383a13-a438-49a2-9800-68312bb7b2d8)

6. While making connection with the remote Elasictsearch server with SSH protection `HTTPS`, there is a need for Elasticsearch `http_ca.crt` certificate file. But the server uses a self-signed certificate or a certificate signed by a Certificate Authority (CA) that is not recognized by the Java truststore. To resolve this issue, we need to add the Elasticsearch server's SSL certificate to the truststore used by Java application.
   ```
   keytool -import -alias http_ca -file C:Users\User\Downloads\http_ca.crt -keystore truststore.jks
   ```
- The above command can be used for other clients as well. The keytool command is a generic tool that can be used to manage certificates and keystores. The -import option is not specific to Java clients. It can be used to import certificates into any keystore that supports the JKS format.
- The truststore is a file that contains a list of trusted certificates. When a client connects to a server using SSL/TLS, the client will check the server's certificate against the truststore. If the certificate is found in the truststore, the client will trust the server and the connection will be established.
- The truststore can be used by any client that supports SSL/TLS. This includes Java clients, as well as clients written in other languages.
----------------------

## [Add and remove nodes in your cluster](https://www.elastic.co/guide/en/elasticsearch/reference/current/add-elasticsearch-nodes.html)

- When you start an instance of Elasticsearch, you are starting a node. An Elasticsearch cluster is a group of nodes that have the same cluster.name attribute. As nodes join or leave a cluster, the cluster automatically reorganizes itself to evenly distribute the data across the available nodes.
- If you are running a single instance of Elasticsearch, you have a cluster of one node. All primary shards reside on the single node. No replica shards can be allocated, therefore the cluster state remains yellow. The cluster is fully functional but is at risk of data loss in the event of a failure.

![image](https://github.com/af4092/Elasticsearch-JavaAPI-connection/assets/24220136/b7cb2c70-7a49-473d-be23-9d102bdb60b4)

- You add nodes to a cluster to increase its capacity and reliability. By default, a node is both a data node and eligible to be elected as the master node that controls the cluster. You can also configure a new node for a specific purpose, such as handling ingest requests. For more information, see Nodes.
- When you add more nodes to a cluster, it automatically allocates replica shards. When all primary and replica shards are active, the cluster state changes to green.

![image](https://github.com/af4092/Elasticsearch-JavaAPI-connection/assets/24220136/61524c45-61ff-44e2-ab51-46c868af989c)
