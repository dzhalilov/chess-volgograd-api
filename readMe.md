# Volgograd Chess Rating Informer
## Application Description:
This application consists of API and UI that shows Volgograd chess players' ratings.
### REST URLs:
- rest/players - get all players with filters  (for Users)
- rest/players/count - get count of players with filters (for Users)
- rest/admin/ratings - update data from API Chess Federation of Russia (for Admin. Needs credentials)
### UI URL:
- / - User interface.
## Used Technologies
Spring Boot, Spring Data JPA, Spring Security, PostgreSQL, JDK 17, JSP, JavaScrip, Prometheus, Grafana, Docker
### Server
``` 
127.0.0.1:10050
```
### Prometheus
``` 
127.0.0.1:9090
```
### Grafana
``` 
127.0.0.1:3000
```