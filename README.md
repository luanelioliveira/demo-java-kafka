<h1 align="center">
    Sample Kafak Pub/Sub
</h1>

<h3 align="center">
   Messaging Applications
</h3>

<p align="center">
  <img alt="GitHub language count" src="https://img.shields.io/github/languages/count/luanelioliveira/sample-kafka-pub-sub?color=%2304D361">

  <img alt="Repository size" src="https://img.shields.io/github/repo-size/luanelioliveira/sample-kafka-pub-sub">

  <a href="https://www.linkedin.com/in/luanoliveira/" target="_blank">
    <img alt="Made by Luan Eli Oliveira" src="https://img.shields.io/badge/made%20by-Luan%20Eli%20Oliveira-brightgreen">
  </a>

  <a href="https://github.com/luanelioliveira/sample-kafka-pub-sub/commits/master">
    <img alt="GitHub last commit" src="https://img.shields.io/github/last-commit/luanelioliveira/sample-kafka-pub-sub">
  </a>

  <a href="https://github.com/luanelioliveira/sample-kafka-pub-sub/issues">
    <img alt="Repository issues" src="https://img.shields.io/github/issues/luanelioliveira/sample-kafka-pub-sub">
  </a>

  <img alt="License" src="https://img.shields.io/badge/license-MIT-brightgreen">
</p>
<p align="center">
  <a href="#rocket-tecnologias">Tecnologias</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#-projeto">Projeto</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#-diagrama">Diagrama</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#-instalacao">Instalação</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#-contribuir">Contribuir</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#memo-licenca">Licença</a>
</p>

<br>

## :rocket: Tecnologias

Este projeto foi desenvolvido com as seguintes tecnologias:

- [Java](https://www.java.com/)
- [Apache Kafka](https://kafka.apache.org/)

## 💻 Projeto

<p>
Este projeto foi uma iniciativa de estudar e implementar uma aplicação consumindo e produzindo mensagens
através do message broker Apacha Kafka. 
Com isso, foram implementados alguns serviços para efetuar um pedido de compra e com base nele,
executar outros serviços de forma assíncrona. 
Basicamente, o fluxo é criar um pedido de compra, enviar um email com pedido solicitado,
efetuar o processamento de pagamento, enviar um email com resultado do pagamento.
e também um serviço de log para receber todos as mensagens que estão sendo processadas pelo broker.
</p>

## 🔖 Diagrama

<table>
  <tbody>
	 <tr>
	   <td><img alt="Sign In" src="https://github.com/luanelioliveira/sample-kafka-pub-sub/blob/master/.github/img/diagram.png" width="500px" /></td>
	 </tr>
  </tbody>
</table>


## :desktop_computer: Instalação

- Primeiro efetue o clone do projeto: ```git clone https://github.com/luanelioliveira/sample-kafka-pub-sub.git```
- Entre na pasta do projeto:  ```cd sample-kafka-pub-sub```
- Efetue o build das aplicações: ```make build```
- [Inslale e configure o Kafka](./docs/kafka.md)

## 🤔 Contribuir!
Pull requests são bem vindos! 

Para maiores alterações, por favor abra uma issue para discutirmos o que você gostaria que fosse alterado.
Certifique-se de atualizar os testes conforme apropriado.

- Faça um fork
- Crie uma branch com sua feature: `git checkout -b my-feature`;
- Efetuar commit das alterações: `git commit -m 'feat: My new feature'`;
- Efetuar o push da sua branch: `git push origin my-feature`.

Depois de efetuar o merge da sua alteração, você pode excluir a sua branch.

## :memo: Licença

Este projeto está sob a licença do MIT.
Veja a [LICENSE](LICENSE) para mais detalhes.

---
Feito por Luan Eli Oliveira :wave: [Get in touch!](https://www.linkedin.com/in/luanoliveira/)
