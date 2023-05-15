# STRONG TEAM backend test assignment

## Description 
This task is done by using java spring boot framework.<br />
You can run this project in docker by following commands:
-  ./mvnw clean package or mvn clean package (if maven is installed)
- docker build -t strong_backend_test_assignment.jar . 
- docker compose up -d

## Authentication
I used spring security to implement authentication part.
All get methods and requests related to authentication don't need Bearer token, other must include Bearer token in header.<br/>
Here is register method and its request body:<br/>
<img src="images/img1.png"/><br/>
That is response:<br/>
<img src="images/img2.png"/><br/><br/>
Login(authenticate) request method and request body:<br/>
<img src="images/img3.png"/><br/>
Response:<br/>
<img src="images/img4.png"/>"/><br/><br/>

## GET, POST, PUT, DELETE methods for news topics
To post topic, you have to be authorized. Its endpoint and and request body:<br/>
<img src="images/img5.png"/><br/>
Response:<br/>
<img src="images/img6.png"/><br/><br/>
Put request:<br/>
<img src="images/img7.png"/><br/>
Response:<br/>
<img src="images/img8.png"/><br/><br/>
Delete request:<br/>
<img src="images/img9.png"/><br/><br/>
Get all topics(no token required):<br/>
<img src="images/img10.png"/><br/>
Example of response:<br/>
<img src="images/img11.png"/><br/><br/>
Get exact topic:<br/>
<img src="images/img12.png"/><br/>
Response:<br/>
<img src="images/img13.png"/><br/><br/>

## GET, POST, PUT, DELETE methods for news sources
All requests for sources are identical to topics(main:the same request body).But instead of 'news-topics' in endpoint, I chose to write 'news-sources'.

## GET, POST, PUT, DELETE methods for news
Post request for news. Its request body and response:<br/>
<img src="images/img14.png"/><br/>
<img src="images/img15.png"/><br/><br>
Put request has the same request body and response, but another endpoint(at the end '/{id}'):<br/>
<img src="images/img16.png"/><br/><br>
Delete single news:<br/>
<img src="images/img17.png"/><br/><br/>
Get single news:<br/>
<img src="images/img18.png"/><br/>
Response:<br/>
<img src="images/img19.png"/><br/><br/>
## GET method for getting list of all news (with pagination)
I will provide only endpoint, because I couldn't make long screenshot:</br>
<img src="images/img20.png"/><br/><br/>
## GET method for getting list of news by source id (with pagination)
Endpoint:<br/>
<img src="images/img21.png"/><br/><br/>
## GET method for getting list of news by topic id (with pagination)
Endpoint:<br/>
<img src="images/img22.png"/><br/><br/>
## Scheduling task:
I wrote code in NewsSourceService.java, which creates every midnight csv file named 'source_news_statistics', which shows how much news each source holds.<br/>
<img src="images/img23.png"/><br/><br/>









