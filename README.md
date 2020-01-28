# qantasassignment

# Introduction 
Instructions to run the project
This is a Microservice architecture written in Spring Boot technology

# Getting Started
Prerequisite 
Technologies to be installed on the machine are
1. Java 8
2. Maven

How to run this project
1. Download the zip file into a folder Crawler
2. Unzip the file
3. cd into WebCrawler 
4. run "mvn clean install", This will create the jar file and run all unit tests cases
5. run "mvn spring-boot:run", this will start the server on localhost on port 15000

There is 1 API http://localhost:15000/v1/webcrawler/link?url=http://google.com&depth=8

The API has 2 query parameters 

1. url: This is a mandatory string field which takes the url which needs to be deep crawled
        This has to be in the format "http://example.com" or "https://example.com"
        Only http and https protocol are supported in this API. Other formats throw and error
   
2. depth: This is an optional filed. The maximum value allowed for this field is 10. 
          if this parameter is not specified, the default value of 10 is taken. If the 
          parameter is given it has to be less than or equal to 10.
          
Once the server starts use cUrl command to execute the api (sample command)

# Execute
curl -X GET 'http://localhost:15000/v1/webcrawler/link?url=http://google.com&depth=3'

# Design
1. The algorithm uses DFS ( Depth First Search ) approach. This means that the depth filed 
in the parameter is taken and for a particular node(page link) all the depth nodes are
listed and for each node again the depth is reached. This means that the last depth level
is traversed for all the nodes at that level

2. The Algorithm also maintains a global list of links This is to9 ensure that every link 
is visited only once. If the link is already visited it will be skipped

3. I have used Spring Cache which internally implements ConcurrencyHashMap which ensures
thread safe.
 
4. The algorithm also equates depth to breadth. This means the graph i am constructing is 
having same number of edges as the number of depth. This is just for convenience.
This can also be implemented by giving another breadth parameter. Hence if the depth parameter 
is 5, then the DFS algorithm traverses 5 levels deep and in each node has 5 edges.
As mentioned this equation i have implemented for convenience.

5. The complexity of this algorithm is, O(V+E) where V is vertices and E is edges.
Now since i have equated both V and E internally, its O(n) where n is the number of unique nodes

# Improvements
Some of the other things which can be added to the project
1. Swagger for better user interface to play with the API
2. A Cache server
