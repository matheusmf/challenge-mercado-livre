# challenge-mercado-livre

## Introdução

Este projeto é um teste prático para atuar como back-end no Mercado Livre.
O teste consiste em criar uma API Restful para um recurso de usuários com as operações de:
 - Cadastro
 - Atualização
 - Busca de um único recurso
 - Busca de vários recursos

Sendo que as regras a seguir precisam ser atendidas.
1. Os dados precisam ser persistidos;
2. Somente usuários acima de 18 anos serão cadastrados;
3. Não será permitido usuários com e-mail e CPF duplicados;
4. Quando buscar por vários usuários, deve permitir realizar um filtro pelo nome;
5. Permitir a alteração parcial;
6. Validar se o CPF é válido (dígitos verificadores);

## Arquitetura e padrões de projeto

Para implementação do projeto foi escolhida a Arquitetura Hexagonal, por se tratar de um padrão que reduz o acomplamento entre as diferentes camadas, criando um isolamento do domínio o que leva a uma maior testabilidade.

Também foram utilizados dois padrões de projeto na implementação.

O padrão de projeto Builder foi utilizado para facilitar a criação de objetos, necessário para trafegar os dados entre as camadas da arquitetura.

Já o padrão de projeto Chain of Responsibility foi utilizado para realizar as validações das regras 2, 3 e 4.

## Persitência de Dados

Como solicitado na regra 1 do teste é necessário que os dados sejam persistidos, para isto foi escolhido o MongoDB.

Foi criado um arquivo docker-compose.yml na raiz do projeto onde está a dependência do MongoDB.

## API

A API de usuários está implementada na classe UserController. Sua documentação pode ser acessada através do Swagger, sendo acessível após subir a aplicação em /swagger-ui/index.html.

A API possui 5 endpoints que estão listados a seguir:
- GET /users (Busca todos os usuários com a possibilidade de filtrar por nome)
- POST /users (Criação de usuários)
- GET /users/{id} (Busca um único usuário por ID)
- DELETE /users/{id} (Apaga um usuário por ID)
- PATCH /users/{id} (Atualiza dados de um usuário)

## Testes

Foram implementados os testes unitários do service dos usuários, do service de validação dos usuários e do repository dos usuários, respectivamente nas seguintes classes:
- UserServiceTest
- UserValidationServiceTest
- UserSpringDataAdapterTest

Também foi implementado um teste integrado da API de usuários que está disponível na classe:
- UserControllerIntegrationTest