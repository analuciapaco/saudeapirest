Informação:A API foi desenvolvida na IDE Spring Tool Suite 4
***********************************************************************************************************
Instruções:
Rodar o script abaixo para criar o banco de dados no MySQL:
*********************************************************
CREATE DATABASE `saude_db`
    CHARACTER SET 'utf8'
    COLLATE 'utf8_general_ci';
*********************************************************
	
Após importar o projeto para dentro da IDE Acessar o arquivo application.properties que esta no 
caminho:dentro do projeto saudeapirest\src\main\resources e alterar os parametros:
spring.datasource.username=preencher com o usuário do seu banco de dados
spring.datasource.password=preencher com a senha do seu banco de dados

Em seguida acessar a classe SaudeapirestApplication que esta no pacote com.conexa.saudeapirest
clicar com mouse direito>Run As>Java Application
Durante a inicialização serão inseridos:
7 médicos,4 usuários e 3 pacientes.

Para testar por favor abrir o Postman:
1) Selecionar o método POST 
e digitar localhost:8080/api/usuario clicar body marcar raw e na opção Text que ira aparecer selecionar JSON
preencher o campo abaixo com
 {
  "usuario": "medico@email.com",
  "senha": "senhamedico"
} 
e clicar em send, será exibido no campo abaixo os dados do usuário, os dados do médico vinculado ao usuário e o token que tem validade
30 minutos (por favor selecione e copie o token sem as aspas)

2)De posse do token selecionar o método POST novamente 
e digitar localhost:8080/api/login clicar body marcar raw e na opção Text que irá aparecer selecionar JSON
preencher o campo abaixo com
 {
  "usuario": "medico@email.com",
  "senha": "senhamedico"
} 
e clicar na opção Headers abaixo da palavra key digitar/selecionar Content-Type e abaixo do Content-Type digitar/selecionar Authorization
abaixo da palavra value selecionar a digitar/selecionar application/json e no campo abaixo do application/json digitar Bearer dar um espaço
e colar o token obtido ao executar o instrução 1 e clicar em send (se o token tiver expirado pois ele tem validade de 30 minutos, será
preciso efetuar a instrução 1 novamente e copiar o novo token).
No campo abaixo serão exibidos o token, médico, especialidade e uma lista dos agendamentos de hoje(essa lista virá vazia pois ainda não foi 
lançado nenhum agendamento)

3) Para efetuar o logoff:

Repetir os passos da instrução 1 para obter um token válido copiar o token, em seguida selecionar a opção POST e digitar 
localhost:8080/api/logout
clicar na opção Headers abaixo da palavra key digitar/selecionar Content-Type e abaixo do Content-Type digitar/selecionar Authorization
abaixo da palavra value selecionar a digitar/selecionar application/json e no campo abaixo do application/json digitar Bearer dar um espaço
e colar o token e clicar em send. Será exibido o retorno 204 No Content, em seguida executar a instrução 2 novamente passando o token que
acabou de invalidar será exibido o retorno 401 Unauthorized.

4)Pacientes:
Para obter uma lista de pacientes abrir o Postman selecionar a opção GET e digitar localhost:8080/api/pacientes
Para buscar um paciente selecionar a opção GET e digitar localhost:8080/api/paciente e digitar barra e o id que se deseja buscar conforme
modelo abaixo:
localhost:8080/api/paciente/AB2
Para salvar um paciente seleciona a opção POST e digitar localhost:8080/api/paciente clicar na opção body marcar a opção raw selecionar 
a opção JSON,abaixo da palavra key digitar/selecionar Content-Type eabaixo da palavra value selecionar a digitar/selecionar application/json
 e preencher conforme abaixo: 
{
  "nome": "Rafael Braga",
  "cpf": "101.202.303-11",
  "idade": "33",
  "telefone": "(21) 3232-6565"
} 
Para editar um paciente seleciona a opção PUT e digitar localhost:8080/api/paciente clicar na opção body marcar a opção raw selecionar 
a opção JSON ,abaixo da palavra key digitar/selecionar Content-Type eabaixo da palavra value selecionar a digitar/selecionar application/json 
e preencher conforme abaixo informar o id do objeto a ser alterado: 
{
 "id":"24977b4",
  "nome": "Rafael Braga Alcantara",
  "cpf": "101.202.303-11",
  "idade": "33",
  "telefone": "(21) 3232-6565"
}
Para apagar um paciente seleciona a opção DELETE e digitar localhost:8080/api/paciente clicar na opção body marcar a opção raw selecionar 
a opção JSON e preencher conforme abaixo informar o objeto a ser apagado: 
{ 
"id":"24977b4",
  "nome": "Rafael Braga Alcantara",
  "cpf": "101.202.303-11",
  "idade": "33",
  "telefone": "(21) 3232-6565"
}
Será retornado o status 204 No Content

5) Agendamento:
Repetir os passos da instrução 1 para obter um token válido copiar o token, em seguida selecionar a opção POST e digitar 
localhost:8080/api/agendamento
clicar na opção Headers abaixo da palavra key digitar/selecionar Content-Type e abaixo do Content-Type digitar/selecionar Authorization
abaixo da palavra value selecionar a digitar/selecionar application/json e no campo abaixo do application/json digitar Bearer dar um espaço
e colar o token, e digitar 
{
  "data_hora_atendimento": "2020-08-03 09:00:00",
  "id_paciente":"24977b3"
}
e clicar em send, será devolvido um JSON com data_hora_atendimento, id do agendamento,dados do paciente e dados do médico. 

6)Ao realizar a instrução 2 novamente será exibido um JSON com o token, médico, especialidade e uma lista dos agendamentos de hoje 
preenchida com os agendamentos (Obs: Verificar se o teste esta sendo feito no dia 03/08/2020, caso contrário será preciso alterar a data 
ao cadastrar o agendamento da consulta para a data atual)
