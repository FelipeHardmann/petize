# Petize - Sistema de Gestão de Pedidos

Este projeto foi desenvolvido para a empresa **Petize** com o objetivo de gerenciar pedidos de forma eficiente. A aplicação possui uma rota principal para manipulação de pedidos e foi containerizada utilizando Docker.

## Funcionalidades

- **Gestão de Pedidos:** Criação, atualização e visualização de pedidos.
- **Dockerized:** Fácil configuração e deployment utilizando Docker Compose.

## Pré-requisitos

- **Docker** instalado na máquina.
- **Docker Compose** instalado.

## Como rodar o projeto

1. **Clone o repositório:**

   ```bash
   git clone https://github.com/FelipeHardmann/petize.git
   cd petize
2. **Suba os containers usando Docker Compose:**
docker-compose up -d

3. **Acesse a aplicação:**

A aplicação estará disponível em http://localhost:8080

Aqui está o README com todas as partes devidamente formatadas em Markdown:

markdown

# Petize - Sistema de Gestão de Pedidos

Este projeto foi desenvolvido para a empresa **Petize** com o objetivo de gerenciar pedidos de forma eficiente. A aplicação possui uma rota principal para manipulação de pedidos e foi containerizada utilizando Docker.

## Funcionalidades

- **Gestão de Pedidos:** Criação, atualização e visualização de pedidos.
- **Dockerized:** Fácil configuração e deployment utilizando Docker Compose.

## Pré-requisitos

- **Docker** instalado na máquina.
- **Docker Compose** instalado.

## Como rodar o projeto

1. **Clone o repositório:**

   ```bash
   git clone https://github.com/usuario/petize-sistema-pedidos.git
   cd petize-sistema-pedidos

    Suba os containers usando Docker Compose:

    bash

    docker-compose up -d

    Isso irá iniciar todos os serviços definidos no arquivo docker-compose.yml.

    Acesse a aplicação:

    A aplicação estará disponível em http://localhost:porta, dependendo da configuração no seu docker-compose.yml.

###Rota Principal

A aplicação possui uma única rota para a manipulação de pedidos:

    POST /pedido: Cria um novo pedido.
    GET /pedido/{id}: Retorna os detalhes de um pedido específico.
    PUT /pedido/{id}: Atualiza as informações de um pedido existente.

Tecnologias Utilizadas

    Docker e Docker Compose para containerização.
    [Java, Spring, RabbitMQ]
    [MYSQL]

# Visualizando o RabbitMQ

Este documento fornece instruções sobre como acessar o RabbitMQ usando a porta padrão e as credenciais padrão.

## Pré-requisitos

- **RabbitMQ** deve estar em execução na sua máquina ou servidor.
- Acesso ao navegador web.

## Como Acessar o RabbitMQ

1. **Abra seu navegador web:**

   Para visualizar o painel de gerenciamento do RabbitMQ, abra o seu navegador preferido.

2. **Digite a URL padrão:**

   No navegador, digite o seguinte endereço para acessar o RabbitMQ:

http://localhost:15672


Aqui, `localhost` se refere à máquina local onde o RabbitMQ está sendo executado. A porta `15672` é a porta padrão utilizada pelo painel de gerenciamento do RabbitMQ.

3. **Faça login com as credenciais padrão:**

Quando o prompt de login aparecer, use as credenciais padrão:

- **Usuário:** `guest`
- **Senha:** `guest`

**Nota:** As credenciais padrão (`guest`/`guest`) funcionam apenas se você estiver acessando o RabbitMQ a partir de `localhost`. Para acesso remoto, as configurações padrão de segurança do RabbitMQ desativam o usuário `guest`.

4. **Navegue no painel de gerenciamento:**

Após o login, você terá acesso ao painel de gerenciamento do RabbitMQ, onde pode visualizar e gerenciar filas, exchanges, bindings, entre outras configurações.
