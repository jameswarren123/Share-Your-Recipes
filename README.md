# Share Your Recipes!
A Java based a fullstack application which utilizes HTML, CSS, JavaScript, SpringBoot, MySQL, and Docker to provide a highly interactive user environment. This four person team worked to build out Model, Controller, Service layers to develop a collection of services and webpages which allows users to enjoy sharing and engaging with recipes accross the platform.
## Features
- Secure registering and authentication.
- Protected querying through Java prepared statements.
- The ability to post and view your own recipes and others.
- Able to follow chefs users organically discover.
- Functions to rate and review recipes.
- A search function which allows for a variety of parameters to find the right recipe for you including sort features by age, rating, and view count.
## Technologies Used
- Languages: Java, HTML, CSS, SQL
- Developed in: VSCode
- Environment: Local Host
## Setup
1. Clone this repository
```bash
git clone https://github.com/jameswarren123/Share-Your-Recipes.git
```
2. Open in IDE of your choice
3. Create a Docker container to host the MySQL server
4. Use DDL.sql to create the correct database
5. Run the code with
```bash
mvn spring-boot:run -D"spring-boot.run.jvmArguments='-Dserver.port=8081'"
```
6. Go to http://localhost:8081 and as of the latest version some dummy data will be provided
