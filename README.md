# Gerenciador de frequência acadêmica
**Componentes (Banco e POO):**
* Antônio Cauê Oliveira Morais
* Davi da Silva Soares
* João Felype Feitosa Rego
* Karlos Wiliam da Rocha Marques

**Componentes (Teste de Software):**
* Antônio Cauê Oliveira Morais
* Davi da Silva Soares
* Karlos Wiliam da Rocha Marques

## Descrição

O objetivo deste projeto em Java é criar um sistema gerenciador acadêmico focado exclusivamente nos alunos. Este sistema oferecerá funcionalidades que abrangem desde análise detalhada da frequência individual do aluno em relação a cada disciplina até um mecanismo para registrar e monitorar as notas acadêmicas.

## Função técnica
O Sistema será a princípio apresentado por meio de CLI/TUI e posteriormente adaptado para interface usando dos próprios recursos do Java.

## Como compilar e executar
O projeto foi feito com o builder Maven, portanto, certifique-se que o tenha instalado em seu sistema. Se desloque à pasta principal do projeto _attendance-manager/_ onde o arquivo *pom.xml* estiver e compile o projeto de forma limpa.

```shell
# para compilar:
cd attendance-manager/
mvn clean install
# para executar:
java -jar target/attendance-manager.jar
```

O Maven deve resolver as dependências do projeto e torná-lo executável automaticamente.

### Banco de dados

O banco de dados configurado atualmente é público e portanto serve apenas para fins de testes. O projeto é pensado no uso exclusivo individual, portanto é interessante usar o _./DB/academic_attendance.sql_ para criar seu próprio banco de dados. 