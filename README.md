# 📊 SalesControl Web

Sistema de controle de vendas desenvolvido em **Spring Boot + MySQL** com front-end em **HTML, CSS e JavaScript**.  
Este projeto é a evolução da versão desktop (Swing), trazendo para a web as funcionalidades de cadastro de produtos, registro de vendas, relatórios e controle de permissões de usuários (ADMIN e EMPLOYEE).

---

## 🚀 Tecnologias

- **Backend**
    - Java 17+
    - Spring Boot (Web, Data JPA, Validation, Security)
    - MySQL 8
    - JWT para autenticação
- **Frontend**
    - HTML5, CSS3, JavaScript (fetch API)
- **Ferramentas**
    - Maven
    - Git/GitHub para versionamento

---

## ⚙️ Funcionalidades

- **Autenticação e Permissões**
    - Login com usuário e senha
    - Perfis: `ADMIN` e `EMPLOYEE`
    - ADMIN pode cadastrar/editar produtos, ver relatórios e registrar vendas
    - EMPLOYEE pode apenas registrar vendas

- **Produtos**
    - Cadastro, edição, exclusão e listagem
    - Busca por nome
    - Validações de preço, quantidade e campos obrigatórios

- **Carrinho**
    - Adicionar, remover e limpar itens
    - Validação de estoque

- **Vendas**
    - Finalização de venda com cálculo automático do valor total
    - Relatórios filtrados por data, categoria e nome do produto
    - Exportação de relatórios em CSV

---

## 🗄️ Estrutura do Banco de Dados

Tabelas principais:
- `usuario` (id, nome, login, senha, tipo)
- `produto` (id, nome, categoria, precounitario, unidademedida, quantidade)
- `venda` (id, datavenda, valortotal)
- `venda_produto` (venda_id, produto_id, quantidade, precounitario, valortotal)

---

## ▶️ Como rodar o projeto

### Pré-requisitos
- Java 17+
- Maven
- MySQL 8

### Passos
1. Clone o repositório:
   ```bash
   git clone https://github.com/Maria-Eduarda-Rodrigues-Schwinn/sboot-sales-control.git
   cd sboot-sales-control
   
2. Rodar os scripts de criação das tabelas necessárias localizados em **src/main/resources/db**
   
3. Configure o banco no application.properties:
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/sales_control
    spring.datasource.username=seu_usuario
    spring.datasource.password=sua_senha
    ```

4. Rode a aplicação:
   ``mvn spring-boot:run``

5. Acesse no navegador:
   http://localhost:8080/login.html
