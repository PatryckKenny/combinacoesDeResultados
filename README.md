# Aplicação de Verificação de Pontuação
Esta aplicação é responsável por calcular as combinações únicas de pontuação entre dois times em um jogo. Ela aceita uma pontuação no formato "time1xtime2" e retorna o número de combinações únicas de pontuação possíveis.

# Pré-requisitos
Java 17;

Maven para construir e executar a aplicação.

# Como usar
Clone este repositório em sua máquina local:


git clone https://github.com/PatryckKenny/combinacoesDeResultados.git

# Usando Docker
* Para subir a imagem

  `docker build -t app .`

  
* Para startar a aplicação

  `docker run -p 8080:8080 app`


# Acesse a documentação Swagger para interagir com a API:

http://localhost:8080/swagger-ui.html

# API Endpoints

# Calcular Combinações de Pontuação
 * URL

`/verify`

 * Método

`POST`

* Corpo da Requisição

`{
"score": "pontuacaoTime1xpontuacaoTime2"
}`

* Resposta de Sucesso 
  * Código: 200 OK
* Corpo:

`{
"combinacoes": 5
}`
* Resposta de Erro

  * Código: 400 Bad Request

* Corpo:

`{
"message": "Formato de pontuação inválido",
"status": 400,
"details": []
}`