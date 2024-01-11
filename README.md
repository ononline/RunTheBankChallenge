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
>   - Spring Security
>   - Spring for RabbitMQ
>   - Lombok
>   - Spring REST Docs

---
## Comentários sobre o desenvolvimento

- Uso de [Lombok](https://projectlombok.org/) para gerar código básico e repetitivo de forma automatizada (getters, setters, construtores simples, equals, hashCode e toString)
  - Obs.: O Lombok exige o uso de um plugin na IDE para funcionar corretamente. Verifique a documentação 
- Uso de [SpringDoc OpenApi](https://springdoc.org/)  para simplificar documentação das APIs 