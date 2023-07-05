# [Elasticsearch-JavaAPI-connection](https://www.elastic.co/guide/en/elasticsearch/client/java-api-client/current/installation.html)
This repository shows how to set elastic stack and check each of them, also shows basic connection between JavaAPI and Elasticsearch server.

### Troubleshooting

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

<p align="center">
  <img src="https://github.com/af4092/Elasticsearch-JavaAPI-connection/assets/24220136/5a2b5c51-142c-4a75-a980-ce914dc46325" alt="Image">
</p>

- [Vector Search usage](https://www.datasciencebyexample.com/2023/03/18/elasticsearch-dense-vector-search/) - vector search using Elastic Search, index and search example using python requests library
- [Verifying HTTPS with a CA certificate](https://www.elastic.co/guide/en/elasticsearch/client/java-api-client/current/connecting.html) - The generated root CA certificate can be found in the certs directory in your Elasticsearch config location. If you’re running Elasticsearch in Docker there is [additional documentation](https://www.elastic.co/guide/en/elasticsearch/reference/8.8/docker.html) for retrieving the CA certificate.

<p align="center">
  <img src="https://github.com/af4092/Elasticsearch-JavaAPI-connection/assets/24220136/b6f66593-2135-409a-be7d-74b3863f6201" alt="Image">
</p>

----------------------------

- `ElasticHighLevelRemoteHTTPS` folder includes the `ElasticsearchConnectionCheckHTTP` source code which just checks the connection with Elasticsearch server without any SSH. `ElasticsearchConnectionChekHTTPS` checks the connection with the `HTTPS` security. Remote Elasticsearch credentials must be entered. The following is the demo test run:

<p align="center">
  <img src="https://github.com/af4092/Elasticsearch-JavaAPI-connection/assets/24220136/37383a13-a438-49a2-9800-68312bb7b2d8" alt="Image">
</p>

- While making connection with the remote Elasictsearch server with SSH protection `HTTPS`, there is a need for Elasticsearch `http_ca.crt` certificate file. But the server uses a self-signed certificate or a certificate signed by a Certificate Authority (CA) that is not recognized by the Java truststore. To resolve this issue, we need to add the Elasticsearch server's SSL certificate to the truststore used by Java application.
   ```
   keytool -import -alias http_ca -file C:Users\User\Downloads\http_ca.crt -keystore truststore.jks
   ```
  - The above command can be used for other clients as well. The keytool command is a generic tool that can be used to manage certificates and keystores. The -import option is not specific to Java clients. It can be used to import certificates into any keystore that supports the JKS format.
  - The truststore is a file that contains a list of trusted certificates. When a client connects to a server using SSL/TLS, the client will check the server's certificate against the truststore. If the certificate is found in the truststore, the client will trust the server and the connection will be established.
  - The truststore can be used by any client that supports SSL/TLS. This includes Java clients, as well as clients written in other languages.
