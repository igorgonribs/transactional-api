# Transactional API
Api de transações financeiras.

## Como executar
Primeiro, clone o projeto em sua máquina.

Para executar o projeto execute os seguintes comandos na pasta raiz do projeto.
```
docker-compose up -d
```

## Modelo de dados
O modelo de dados final está apresentado no arquivo ```modelo_dados.drawio```, na pasta ```documentation_files```.
Foi seguido o modelo sugerido na descrição do desafio, com exceção da adição de uma coluna chamada ```operation_signal``` à tabela ```operation_types``` para indicar se a operação request um valor positivo ou negativo.

## Arquitetura
O desenho da arquitetura está apresentado no arquivo ```arquitetura.drawio```, na pasta ```documentation_files```.

A arquitetura consiste em um serviço para a API de contas e outro serviço para a API de transações, dessa forma podemos escalar cada API independentemente, dado que cada API pode receber um volume diferente de carga.

## Endpoints

Para testar as aplicações, importe no postman o arquivo `TransactionalAPI.postman_collection.json`, na pasta ```documentation_files``` e execute as chamadas. 

### API de contas

#### Get Account by Id
Rota para buscar conta por id:
```
Request:  
    GET /account/api/accounts/{id}

Response
    {
        "id": 1,
        "documentNumber": "12345678900"
    }
```

#### Create Account
Rota para cadastrar nova conta:
```
Request:
    POST /account/api/accounts
    {
        "documentNumber": "11094463655"
    }

Response:
    Status-Code: 201
    {
        "id": 2,
        "documentNumber": "11094463655"
    }
```

### API de transações

#### Create Transaction
Rota para criar uma nova transação:
```
Request:
    POST /transactional/api/transactions
    {
        "accountId": 1,
        "operationTypeId": 1,
        "amount": 10.0
    }

Response:
  Status-Code: 201
    {
        "amount": -10.0,
        "eventDate": "2023-10-27T03:07:21.685951218",
        "operationTypeId": 1,
        "accountId": 1
    }
```

## Cobertura de testes
![alt text](https://github.com/igorgonribs/transactional-api/blob/main/documentation_files/CoverageEvidenceAccountAPI.png?raw=true)
![alt text](https://github.com/igorgonribs/transactional-api/blob/main/documentation_files/CoverageEvidenceTransactionalAPI.png?raw=true)
