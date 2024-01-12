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

- Uso de [Lombok](https://projectlombok.org/) para gerar código básico e repetitivo de forma automatizada (getters, setters, construtores simples, equals, hashCode e toString)
  - Obs.: O Lombok exige o uso de um plugin na IDE para funcionar corretamente. Verifique a documentação 
- Uso de [SpringDoc OpenApi](https://springdoc.org/)  para simplificar documentação das APIs
- Testes unitários criados utilizando pacote padrão do Spring Web MVC (JUnit, Mockito)

---
## Pontos a melhorar

- Como este é um teste, para que possa ser entregue em tempo hábil acabei desenvolvendo algumas partes de forma mais simples do que faria num ambiente real.
  - Não foi aplicada nenhuma camada de segurança, mesmo que os clientes tenham senha, inclusive a mesma é guardada como texto simples, sem criptografia
  - A aplicação foi criada como um "monolito", em um ambiente real seria interessante desacoplar estas funções em microsserviços a fim de permitir escalabilidade vertical
  - Foram feitos apenas testes unitários, não criei testes integrados
  