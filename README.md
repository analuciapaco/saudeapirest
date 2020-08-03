Informa��o:A API foi desenvolvida na IDE Spring Tool Suite 4
***********************************************************************************************************
Instru��es:
Rodar o script abaixo para criar o banco de dados no MySQL:
*********************************************************
CREATE DATABASE `saude_db`
    CHARACTER SET 'utf8'
    COLLATE 'utf8_general_ci';
*********************************************************
	
Ap�s importar o projeto para dentro da IDE Acessar o arquivo application.properties que esta no 
caminho:dentro do projeto saudeapirest\src\main\resources e alterar os parametros:
spring.datasource.username=preencher com o usu�rio do seu banco de dados
spring.datasource.password=preencher com a senha do seu banco de dados

Em seguida acessar a classe SaudeapirestApplication que esta no pacote com.conexa.saudeapirest
clicar com mouse direito>Run As>Java Application
Durante a inicializa��o ser�o inseridos:
7 m�dicos,4 usu�rios e 3 pacientes.

Para testar por favor abrir o Postman:
1) Selecionar o m�todo POST 
e digitar localhost:8080/api/usuario clicar body marcar raw e na op��o Text que ira aparecer selecionar JSON
preencher o campo abaixo com
 {
  "usuario": "medico@email.com",
  "senha": "senhamedico"
} 
e clicar em send, ser� exibido no campo abaixo os dados do usu�rio, os dados do m�dico vinculado ao usu�rio e o token que tem validade
30 minutos (por favor selecione e copie o token sem as aspas)

2)De posse do token selecionar o m�todo POST novamente 
e digitar localhost:8080/api/login clicar body marcar raw e na op��o Text que ir� aparecer selecionar JSON
preencher o campo abaixo com
 {
  "usuario": "medico@email.com",
  "senha": "senhamedico"
} 
e clicar na op��o Headers abaixo da palavra key digitar/selecionar Content-Type e abaixo do Content-Type digitar/selecionar Authorization
abaixo da palavra value selecionar a digitar/selecionar application/json e no campo abaixo do application/json digitar Bearer dar um espa�o
e colar o token obtido ao executar o instru��o 1 e clicar em send (se o token tiver expirado pois ele tem validade de 30 minutos, ser�
preciso efetuar a instru��o 1 novamente e copiar o novo token).
No campo abaixo ser�o exibidos o token, m�dico, especialidade e uma lista dos agendamentos de hoje(essa lista vir� vazia pois ainda n�o foi 
lan�ado nenhum agendamento)

3) Para efetuar o logoff:

Repetir os passos da instru��o 1 para obter um token v�lido copiar o token, em seguida selecionar a op��o POST e digitar 
localhost:8080/api/logout
clicar na op��o Headers abaixo da palavra key digitar/selecionar Content-Type e abaixo do Content-Type digitar/selecionar Authorization
abaixo da palavra value selecionar a digitar/selecionar application/json e no campo abaixo do application/json digitar Bearer dar um espa�o
e colar o token e clicar em send. Ser� exibido o retorno 204 No Content, em seguida executar a instru��o 2 novamente passando o token que
acabou de invalidar ser� exibido o retorno 401 Unauthorized.

4)Pacientes:
Para obter uma lista de pacientes abrir o Postman selecionar a op��o GET e digitar localhost:8080/api/pacientes
Para buscar um paciente selecionar a op��o GET e digitar localhost:8080/api/paciente e digitar barra e o id que se deseja buscar conforme
modelo abaixo:
localhost:8080/api/paciente/AB2
Para salvar um paciente seleciona a op��o POST e digitar localhost:8080/api/paciente clicar na op��o body marcar a op��o raw selecionar 
a op��o JSON,abaixo da palavra key digitar/selecionar Content-Type eabaixo da palavra value selecionar a digitar/selecionar application/json
 e preencher conforme abaixo: 
{
  "nome": "Rafael Braga",
  "cpf": "101.202.303-11",
  "idade": "33",
  "telefone": "(21) 3232-6565"
} 
Para editar um paciente seleciona a op��o PUT e digitar localhost:8080/api/paciente clicar na op��o body marcar a op��o raw selecionar 
a op��o JSON ,abaixo da palavra key digitar/selecionar Content-Type eabaixo da palavra value selecionar a digitar/selecionar application/json 
e preencher conforme abaixo informar o id do objeto a ser alterado: 
{
 "id":"24977b4",
  "nome": "Rafael Braga Alcantara",
  "cpf": "101.202.303-11",
  "idade": "33",
  "telefone": "(21) 3232-6565"
}
Para apagar um paciente seleciona a op��o DELETE e digitar localhost:8080/api/paciente clicar na op��o body marcar a op��o raw selecionar 
a op��o JSON e preencher conforme abaixo informar o objeto a ser apagado: 
{ 
"id":"24977b4",
  "nome": "Rafael Braga Alcantara",
  "cpf": "101.202.303-11",
  "idade": "33",
  "telefone": "(21) 3232-6565"
}
Ser� retornado o status 204 No Content

5) Agendamento:
Repetir os passos da instru��o 1 para obter um token v�lido copiar o token, em seguida selecionar a op��o POST e digitar 
localhost:8080/api/agendamento
clicar na op��o Headers abaixo da palavra key digitar/selecionar Content-Type e abaixo do Content-Type digitar/selecionar Authorization
abaixo da palavra value selecionar a digitar/selecionar application/json e no campo abaixo do application/json digitar Bearer dar um espa�o
e colar o token, e digitar 
{
  "data_hora_atendimento": "2020-08-03 09:00:00",
  "id_paciente":"24977b3"
}
e clicar em send, ser� devolvido um JSON com data_hora_atendimento, id do agendamento,dados do paciente e dados do m�dico. 

6)Ao realizar a instru��o 2 novamente ser� exibido um JSON com o token, m�dico, especialidade e uma lista dos agendamentos de hoje 
preenchida com os agendamentos (Obs: Verificar se o teste esta sendo feito no dia 03/08/2020, caso contr�rio ser� preciso alterar a data 
ao cadastrar o agendamento da consulta para a data atual)
