# [Elasticsearch-JavaAPI-connection](https://www.elastic.co/guide/en/elasticsearch/client/java-api-client/current/installation.html)
This repository shows how to set elastic stack and check each of them, also shows basic connection between JavaAPI and Elasticsearch server.

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
