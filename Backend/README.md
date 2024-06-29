# Backend Documentation

## Services

### Database Reader
Receives Requests to read from Database  
  
**Technology**  
MariaDB JDBC Driver  
Spring Boot Data JPA  

- Port: 8081
- host: localhost
- API Endpoints: 
    - GET /database-reader/picture/1

### Database Writer
Receives Requests to write to Database  
  
**Technology**  
MariaDB JDBC Driver  
Spring Boot Data JPA  

- Port:8082
- host: localhost
- API Endpoints: 
    - POST /database-writer/add

### Monitoring Service
Monitores all Services.  
  
**Technology**  
Micrometer Prometheus Monitoring Server  

- Port:8080
- host: localhost

### Gateway Service
API Gateway for accessing all service endpoints via one well defined access point.  
  
**Technology**  
Spring Cloud Gateway  
- Port:8181
- host: localhost

### Discovery/Registration Service
To register all services and find their host and port.  
  
**Technology**  
Netflix Eureka Server  

- Port:8761
- host: 192.168.0.64