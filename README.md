# [Elasticsearch-JavaAPI-connection](https://www.elastic.co/guide/en/elasticsearch/client/java-api-client/current/installation.html)
This repository shows how to set elastic stack and check each of them, also shows basic connection between JavaAPI and Elasticsearch server.

### Elastic stack installation guide:

- [Elasticsearch Java API Client](https://www.elastic.co/guide/en/elasticsearch/client/java-api-client/current/index.html): The Java API client provides strongly typed requests and responses for all Elasticsearch APIs.  
- [Download Elasticsearch](https://www.elastic.co/downloads/elasticsearch) <tr><img src="https://edent.github.io/SuperTinyIcons/images/svg/elastic.svg" width="25" title=""></tr>


- Before running it, disable the security configuration in the following file:  `c:\elastic-stack\elasticsearch-8.7.1\config\elasticsearch.yml` by making the configuration to `false`

![image](https://user-images.githubusercontent.com/24220136/236627245-7b96913a-51ef-4285-a644-85a204405fd1.png)

- Then go inside the following directory: `c:\elastic-stack\elasticsearch-8.7.1\bin` and run the command: `elasticsearch.bat`, then open the port: `localhost:9200` in the browser, you should see the following:

![image](https://user-images.githubusercontent.com/24220136/236627341-ada8ef31-851a-4dac-ae7f-9523bac9918e.png)

- [Download Kibana](https://www.elastic.co/kr/downloads/kibana)
- Go inside the following directory: `c:\elastic-stack\kibana-8.7.1\bin` then run the command: `kibana.bat`, there is nothing to make changes in the configuration files. Just open the port: `localhost:5601` in browser, then you kibana interface is running:

![image](https://user-images.githubusercontent.com/24220136/236627447-8d64ffa1-f6f0-41f1-bbc6-73c9a5e791a8.png)

- `Logstash` download from the link: https://www.elastic.co/downloads/logstash
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

### Elasticsearch + Kibana + Logstash Remote Server address of the company `KHSystems`:
```
- 서버 주소는 115.68.193.101 입니다.
- 현재 개방된 포트는 19200(ES), 15601(kibana) 입니다.
- Kibana의 경우 다음 주소로 접근 가능합니다. http:// 115.68.193.101: 15601
- 다음은 키바나 superuser 계정입니다.
- ID : khsystems
- PW : qwer12#$
```
1. `HighLevelRestClientRemoteServer` sample inside the folder: `ElasticHighLevelRemote` - First we check Elasticsearch remote server connection with High Level Rest Client. Api allows to enter the index name, field name and value(key word), then runs the `vector search` to bring the related result(ignore the error message, it is just saying that Log4j2 should be updated, api itself works fine):  

![image](https://user-images.githubusercontent.com/24220136/236647470-60da3ffa-e8c1-42bd-91b6-c0efdc9fba1c.png)

2. `HighLevelRestClientLocalServer` sample inside the folder: `ElasticHighLevelLocal` - Now we check High Level Rest Client Api lasticsearch local server. In the case of flowers_dataset data.

![image](https://user-images.githubusercontent.com/24220136/236648733-45a06d69-2a83-4b37-960c-83374e9a4cbe.png)

3. `LowLevelRestCLientRemoteServer` sample inside the folder: `ElasticLowLevelRemote` - is the Low Level Rest Client Api with Elasticsearch remote server: 

![image](https://user-images.githubusercontent.com/24220136/236648990-f6332097-a176-48fb-94d9-0796cf326de0.png)

4. `LowLevelRestClientLocalServer` sample inside the folder: `ElasticLowLevelLocal` - is the Low Level Rest Client Api with Elasticsearch local server:

![image](https://user-images.githubusercontent.com/24220136/236649253-39df4828-341f-49e5-9102-a8b47d74ae95.png)



 
