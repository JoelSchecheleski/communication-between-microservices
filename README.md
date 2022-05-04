### Comunicação entre microserviços


![arquitetura-simples](https://user-images.githubusercontent.com/3439261/166570723-c933d159-4a10-4afb-b5a3-afd0d89b52ef.png)

1. Postgres
2. Mongodb
	2.1 Para subir uma versão do docker teve ser acessado a pasta docker e rodar: docker-compose -f stack.yml up
	 mongosh "mongodb://localhost:27017/sales"
3. RabbitMQ
4. Auth API
5. Containers docker
