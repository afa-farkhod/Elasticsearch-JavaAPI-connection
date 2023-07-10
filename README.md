# [Elasticsearch-JavaAPI-connection](https://www.elastic.co/guide/en/elasticsearch/client/java-api-client/current/installation.html)
This repository shows how to set elastic stack and check each of them, also shows basic connection between JavaAPI and Elasticsearch server.

## [Implementation](https://github.com/af4092/Elasticsearch-JavaAPI-connection/tree/main/ElasticsearchConnectionCheckHTTP/src/main/java/org/example)
1. Java client `ElasticsearchConnectionCheckHTTP` that establishes a connection to an Elasticsearch cluster and checks if the connection is successful using HTTP.
- Variables:
  - The code declares and initializes several variables:
  - host: Represents the IP address or hostname of the Elasticsearch cluster.
  - port: Represents the port number on which the Elasticsearch cluster is running.
  - username: Represents the username for authentication with the Elasticsearch cluster.
  - password: Represents the password for authentication with the Elasticsearch cluster.
- Building the Elasticsearch Client:
  - The code creates a RestClientBuilder object to configure the Elasticsearch client.
  - It uses the RestClient.builder() method and specifies the host, port, and "http" scheme to build an HTTP host.
  - The setHttpClientConfigCallback method is used to configure the HTTP client with authentication credentials.
  - It creates a CredentialsProvider object, sets the AuthScope to ANY, and provides the UsernamePasswordCredentials.
  - The httpClientBuilder is then configured with the setDefaultCredentialsProvider method.
  - Finally, the RestHighLevelClient is created using the configured RestClientBuilder.
- Checking the Connection:
  - The code attempts to ping the Elasticsearch cluster using the client.ping method and passing RequestOptions.DEFAULT.
  - If the ping is successful, it prints "Connection is SUCCESSFUL."
  - If an IOException occurs during the ping, it prints "Connection is FAILED" along with the exception message.
- Closing the Client:
  - The client.close() method is called in a finally block to ensure the client resources are released.
2. `FileReadabilityCheck` checks the readability of a file.
- Import Statement:
  - The code imports the `java.io.File` class, which represents a file on the file system.
- File Path:
  - The code declares a variable named `filePath` and assigns it a file path string.
- Creating a File Object:
  - The code creates a `File object` using the filePath string.
  - The File object represents the file specified by the file path.
- Checking File Readability:
  - The code uses an if statement to check the existence and readability of the file.
  - The `exists()` method checks if the file exists.
  - The `canRead()` method checks if the file is readable.
  - If both conditions are true, it prints "File is readable".
  - If either condition is false, it prints "File is not readable".

- Demo run screenshot:

<p align="center">
  <img src="https://github.com/af4092/Elasticsearch-JavaAPI-connection/assets/24220136/de993e8c-729d-46f1-8e1d-4a2ce308c6f3" alt="Image">
</p>

## [Troubleshooting](https://github.com/af4092/Elasticsearch-JavaAPI-connection/tree/main)

- [Elasticsearch Java API Client](https://www.elastic.co/guide/en/elasticsearch/client/java-api-client/current/index.html): The Java API client provides strongly typed requests and responses for all Elasticsearch APIs.  
- [Elasticsearch warning - ignore_throttled parameter is deprecated](https://stackoverflow.com/questions/72271872/elasticsearch-warning-ignore-throttled-parameter-is-deprecated) - [Stackoverflow](https://stackoverflow.com/) - Elasticsearch warning - ignore_throttled parameter is deprecated issued which has answer
- [Do not send default ignore_throttled parameter since it is deprecated](https://github.com/elastic/elasticsearch/pull/84827) - github discussion board about the particular issue
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
