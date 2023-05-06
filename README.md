# Elasticsearch-JavaAPI-connection
This repository shows how to set elastic stack and check each of them, also shows basic connection between JavaAPI and Elasticsearch server.

### Elastic stack installation guide:

- `Elasticsearch` install from the following link: https://www.elastic.co/downloads/elasticsearch
- Before running it, disable the security configuration in the following file:  `c:\elastic-stack\elasticsearch-8.7.1\config\elasticsearch.yml` by making the configuration to `false`

![image](https://user-images.githubusercontent.com/24220136/236627245-7b96913a-51ef-4285-a644-85a204405fd1.png)

- Then go inside the following directory: `c:\elastic-stack\elasticsearch-8.7.1\bin` and run the command: `elasticsearch.bat`, then open the port: `localhost:9200` in the browser, you should see the following:

![image](https://user-images.githubusercontent.com/24220136/236627341-ada8ef31-851a-4dac-ae7f-9523bac9918e.png)

- `Kibana` install from the following link: https://www.elastic.co/downloads/kibana 
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

Then you have to set environmental variables as following:

![image](https://user-images.githubusercontent.com/24220136/236627530-518d74a9-fb68-4317-b4ec-bd9dbc0b4a34.png)


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

