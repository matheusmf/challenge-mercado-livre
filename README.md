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

Alguns padrões de projeto foram utilizados na implementação do teste.

Foi utilizado o padrão Singleton para controlar algumas injeções de dependência. Fazendo com que um objeto esteja disponível de forma global no projeto com uma única instância.

Também foi utilizado o padrão de projeto Builder com auxílio do Lombok para facilitar a criação de objetos, necessário para trafegar os dados entre as camadas da arquitetura.

E também foi utilizado o padrão de projeto Chain of Responsibility para realizar as validações das regras 2, 3 e 4. Estas validações foram implementadas na classe DomainUserValidationService, onde é realizada uma validação e em seguida é passada para a próxima validação caso não tenha problemas na primeira validação.

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

## Mensageria

No endpoint de criação de usuários foi incluído uma integração de mensageria via o Apache Kafka. Onde após criar um usuário é enviado uma mensagem com o id deste usuário para o tópico 'new-user'. A própria aplicação consome a mensagem do tópico e cria um registro de Email contento o e-mail do usuário cadastrado e uma mensagem de boas vindas.

A implementação do envio da mensagem ao tópico e do consumo da mensagem estão implementadas nas seguintes classes respectivamente:
- NewUserProducer
- NewUserConsumer

## Testes

Foram implementados os testes unitários do service dos usuários, do service de validação dos usuários e do repository dos usuários, respectivamente nas seguintes classes:
- UserServiceTest
- UserValidationServiceTest
- UserSpringDataAdapterTest

Também foram implementados os testes unitários do service dos e-mais e do repositório dos e-mails nas classes a seguir:
- EmailServiceTest
- EmailSpringDataAdapterTest

Além disto, foi implementado um teste integrado da API de usuários que está disponível na classe:
- UserControllerIntegrationTest

