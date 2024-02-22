# RunTheBankChallenge
Minha versão de solução para o desafio [Run The Bank](https://github.com/vieiraitalo/Back-End-Challenge), do usuário vieiraitalo

---

## Projeto criado utilizando Spring Initializr

> Configurações utilizadas:  
> - Project: Gradle - Groovy
> - Spring Boot: 3.2.1
> - Packaging: Jar
> - Java: 17
> - Dependencies:
>   - Spring Data JPA
>   - H2 Database
>   - Spring Web
>   - Lombok

---
## Comentários sobre o desenvolvimento

- Uso de [SpringDoc OpenApi](https://springdoc.org/)  para simplificar documentação das APIs
- Testes unitários criados utilizando pacote padrão do Spring Web MVC (JUnit, Mockito)
- Documentação de uso dos Endpoints do projeto pode ser encontrado no [Swagger](http://localhost:8080/swagger-ui), ao 
executar o projeto
  - Também pode ser visualizada de forma estática no arquivo [openapi.md](openapi.md)
- Originalmente este projeto foi criado utilizando [Lombok](https://projectlombok.org/), mas por questões de legibilidade
de código e facilitação em executar o mesmo em qualquer ambiente, foi realizado um refactor para remover o mesmo.

---
## Pontos a melhorar

- Como este é um teste, para que possa ser entregue em tempo hábil acabei desenvolvendo algumas partes de forma mais 
simples do que faria num ambiente real.
  - Não foi aplicada nenhuma camada de segurança, mesmo que os clientes tenham senha, inclusive a mesma é guardada como 
texto simples, sem criptografia
  - A aplicação foi criada como um "monolito", em um ambiente real seria interessante desacoplar estas funções em
microsserviços a fim de permitir escalabilidade vertical
  - Foram feitos apenas testes unitários, não criei testes integrados
  - A transação está sendo gravada como um único registro. O ideal seria que fossem dois registros, um por conta, com os
dados vinculados.
  - O envio da notificação para o servidor externo deveria ocorrer através de uma fila, devido à possível instabilidade 
indicada na documentação. A fila permitiria que o envio fosse assíncrono e que fossem realizadas novas tentativas no 
caso de indisponibilidade do serviço.