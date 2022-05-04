### Comunicação entre microserviços


![arquitetura-simples](https://user-images.githubusercontent.com/3439261/166570723-c933d159-4a10-4afb-b5a3-afd0d89b52ef.png)

1. Postgres
2. Mongodb
	2.1 Para subir um container docker do mongodb deve ser acessado a pasta docker e rodar: docker-compose -f stack.yml up
	 mongosh "mongodb://localhost:27017/sales"
3. RabbitMQ
	3.1 Para subir um container docker do RabbitMQ: docker run --name sales_rabbit -p 5672:5672 -p 25676:25676 -p 15672:15672 rabbitmq:3-management
	3.2 Acessar: http://localhost:15672/#/
4. Auth API
5. Containers docker

Nota: para subir o docker compose do projeto:
	Ir até a pasta que contenha o docker-compose.yml e rodar o comando: docker-compose up --build
