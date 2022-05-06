### Comunicação entre microserviços

![](https://img.shields.io/badge/Status-Em%20Desenvolvimento-orange)
![](https://img.shields.io/badge/Autor-Joel%20Schecheleski-brightgreen)
![](https://img.shields.io/badge/Language-java-brightgreen)
![](https://img.shields.io/badge/Framework-springboot-brightgreen)
	
## Sonar
[![Quality gate](https://sonarcloud.io/api/project_badges/quality_gate?project=JoelSchecheleski_communication-between-microservices)](https://sonarcloud.io/dashboard?id=JoelSchecheleski_communication-between-microservices)


![arquitetura-simples](https://user-images.githubusercontent.com/3439261/166570723-c933d159-4a10-4afb-b5a3-afd0d89b52ef.png)

1. Docker-compose:
	1. Para subir os containers docker, acessado a pasta docker e rodar: docker-compose up --build
	 > Acessar o mongodb deve abrir um terminal e rodar o comando: mongosh "mongodb://localhost:27017/sales"
2. RabbitMQ:
	1. Acessar: http://localhost:15672/#/
3. Auth API:
	1. Rodar o Dockerfile: docker image build -t auth-api .
	   > Nota: Após subir pode ser executado a image: docker run --name auth-api -e PORT=8080 -p 8080:8080 auth-api
4. Sales API:
   1.  Rodar o Dockerfile: docker image build -t sales-api .
   2.  > Nota: Após subir pode ser executado a image: docker run --name sales-api -e PORT=8081 -p 8081:8081 sales-api
5. Product API:
   1. Rodar o Dockerfile: docker image build -t product-api .
   2. > Nota:  Após subir pode ser executado a image: docker run --name product-api -e PORT=8082 -p 8082:8082 product-api


**Nota:** Para subir todos os containers docker com os projetos já definidos então basta usar o comando abaixo: 
> docker-compose up --build (da raiz do diretório principal)
