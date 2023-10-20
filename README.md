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
O projeto foi feito com o builder Maven, portanto, certifique-se que o tenha instalado em seu sistema. **Se desloque à pasta principal do projeto _"attendance-manager/"_ onde o arquivo _"pom.xml"_ estiver** e compile o projeto de forma limpa.

O código-fonte está em _attendance-manager/src/main/java/org/academic_.
O código de testes está em _attendance-manager/src/test/java/org/academic_.

```shell
# para compilar:
cd attendance-manager/
mvn clean install # ou "mvn clean install -DskipTests" para compilar sem testes
# para apenas rodar testes:
mvn test -e
# para executar:
java -jar target/attendance-manager.jar
# para abrir menu (TUI):
java -jar target/attendance-manager.jar menu
```

O Maven deve resolver as dependências do projeto e torná-lo executável automaticamente.

_Obs: para usuários Windows, evitem usar acentuação gráfica por enquanto, existem problemas (possivelmente) de encodificação a serem resolvidos._

### Banco de dados

O banco de dados configurado atualmente é público e portanto serve apenas para fins de testes. O projeto é pensado no uso exclusivo individual, portanto é interessante usar o _./DB/academic_attendance.sql_ para criar seu próprio banco de dados. 