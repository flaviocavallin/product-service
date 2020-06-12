<h1>Product Service</h1>

<h4>1. To run the application execute</h4>
<p>./product-service.sh</p>

<h4>2. To run the application for development</h4>
<h2>Start MySQL on your local:</h2>
docker run -d -p 33060:3306 --name mysql -e MYSQL_ROOT_PASSWORD=root mysql:latest
docker start mysql
<h2>Run application with -Dspring.profiles.active=local</h2>

<h4>3. If you have Postman</h4>
<p>Import into postman the collection product-service.postman_collection.json</p>
Define a variable HOST with value -localhost:8080

<h4>3. Use swagger</h4>
<p>http://localhost:8080/product-service/api-docs/</p>
<p>http://localhost:8080/product-service/api-docs.yaml</p>
<p>http://localhost:8080/product-service/swagger-ui.html</p>

<h4>4. Architecture Details</h4>
<br>

<a>
    <img src="/architecture.jpg" />
</a>
  

