# Custumer Service

### Requisitos

1. JDK 8
1. Maven 3

### Rodando

1. Clone o projeto: `https://github.com/allanalves92/practical-test.git`
1. Entre na pasta `practical-test` e execute: `mvn spring-boot:run`
1. Acesse: `http://localhost:8080/customers`


### Documentação
1. Rode a aplicação
2. Digite `http://localhost:8080/swagger-ui/index.html`

### Requisições
1. Envie um GET para `http://localhost:8080/customers` para encontrar todos os customer sem paginação.
2. Envie um GET para `http://localhost:8080/customers/pageable?page=0&size=2` para encontrar os customers paginado.
3. Envie um GET para `http://localhost:8080/customers/{id}` para encontrar o customer por id.
4. Envie um POST para `http://localhost:8080/customers/create` para criar um customer (Necessita de um body em json no formato listado no item 7).
5. Envie um PUT para `http://localhost:8080/customers/update/{id}` para atualizar um customer (Necessita de um body em json no formato listado no item 7). 
6. Envie um DELETE para `http://localhost:8080/customers/delete/{id}`para deleter um customer.
7. Formato Json
Envie o json no seguinte formato.
      ```json 
            {    
                "id": 0,
                "name": "Roberto Santos",
                "email": "robersantos@gmail.com",
                "addresses":[
                    {
                    "id": 0,
                    "street":"Rua da Joana",
                    "district":"Jardim Botuquara",
                    "city":"São Paulo",
                    "zipcode":"01234-567",
                    "states": "SP",
                    "number":"99"
                    },
                    {
                    "id": 0,
                    "street":"Rua da Bruna",
                    "district":"Jardim Nova Esperança",
                    "city":"São Paulo",
                    "zipcode":"76543-210",
                    "states": "SP",
                    "number":"458"
                    }
                  ]
            }
   
