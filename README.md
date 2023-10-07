# Gerenciador de frequência acadêmica
Componentes:
* Antônio Cauê Oliveira Morais
* Davi da Silva Soares
* Karlos Wiliam da Rocha Marques

## Descrição

O objetivo deste projeto, em Java, será criar um sistema gerenciador de frequência acadêmica, tal qual funcionalidades variam desde análise panorâmica da frequência individual do discente por matéria, quanto feedback por aula no intuito de gerar autoanálise.

## Função técnica
O Sistema será a princípio apresentado por meio de CLI e posteriormente adaptado para interface usando dos próprios recursos do Java.

## Como compilar e executar
O projeto foi feito com o builder Maven, portanto, certifique-se que o tenha instalado em seu sistema. Se desloque à pasta principal do projeto _attendance-manager/_ e compile o projeto de forma limpa. Após isso executar o projeto é apenas uma questão de saber onde está o arquivo gerado.

```shell
cd attendance-manager/
mvn clean install
java -jar target/attendance-manager.jar
```

O Maven deve resolver as dependências do projeto e torná-lo executável automaticamente.

Note ainda que é necessário incluir seu próprio banco de dados no Database.java, atualmente apenas o mysql/mariadb é suportado.