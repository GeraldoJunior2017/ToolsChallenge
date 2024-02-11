# ToolsChallenge

## API de Pagamentos

Bem-vindo à API de Pagamentos do Banco Elite! Esta API foi desenvolvida para atender às necessidades do time Elite de cartões de crédito, proporcionando operações de pagamento e estorno de forma eficiente e segura.

### Operações Disponíveis

**Pagamento**

Realize pagamentos utilizando esta operação.

- **Endpoint:** `/pagamentos`
- **Método HTTP:** POST
- **Corpo da Requisição:** JSON contendo os detalhes do pagamento

Exemplo de Corpo da Requisição:

{
  "transacao": {
    "cartao": "4444*****1234",
    "id": "1",
    "descricao": {
      "valor": "500.00",
      "dataHora": "01/01/2001",
      "estabelecimento": "pet shop"
    },
    "formaPagamento": {
      "tipo": "PARCELADO_EMISSOR",
      "parcelas": "1"
    }
  }
}
Resposta: JSON com os detalhes do pagamento, incluindo o status de autorização.
{
    "transacao": {
        "cartao": "4444*****1234",
        "id": 173,
        "descricao": {
            "valor": 500.00,
            "id": 402,
            "dataHora": "01/01/2001",
            "estabelecimento": "pet shop",
            "nsu": "345305",
            "codigoAutorizacao": "498214",
            "status": "AUTORIZADO"
        },
        "formaPagamento": {
            "tipo": "PARCELADO_EMISSOR",
            "parcelas": 1
        }
    }
}
Estorno
Efetue estornos de transações previamente autorizadas.

Endpoint: /estornos
Método HTTP: POST
Corpo da Requisição: JSON contendo os detalhes da transação de estorno
Exemplo de Corpo da Requisição:
{
  "transacao": {
    "cartao": "4444*****1234",
    "id": "1",
    "descricao": {
      "valor": "500.00",
      "dataHora": "01/01/2001",
      "estabelecimento": "pet shop"
    },
    "formaPagamento": {
      "tipo": "PARCELADO_EMISSOR",
      "parcelas": "5"
    }
  }
}
Resposta: JSON com os detalhes do estorno, incluindo o status e informações adicionais.
{
    "transacao": {
        "cartao": "4444*****1234",
        "id": 174,
        "descricao": {
            "valor": 500.00,
            "id": 403,
            "dataHora": "01/01/2001",
            "estabelecimento": "pet shop",
            "nsu": "906202",
            "codigoAutorizacao": "431832",
            "status": "NEGADO"
        },
        "formaPagamento": {
            "tipo": "PARCELADO_EMISSOR",
            "parcelas": 5
        }
    }
}
Consulta
Consulte transações realizadas, tanto de pagamentos quanto de estornos.

Endpoint: /consultas
Método HTTP: GET
Resposta: JSON contendo a lista de todas as transações registradas.
Consulta
Consulte transações realizadas, tanto de pagamentos quanto de estornos.

Consulta de Pagamentos por ID:

Endpoint: /consultas/pagamentos/{id}
Método HTTP: GET
Parâmetro: ID da transação de pagamento
Resposta: JSON com os detalhes da transação correspondente ao ID fornecido.
Consulta de Estornos por ID:

Endpoint: /consultas/estornos/{id}
Método HTTP: GET
Parâmetro: ID da transação de estorno
Resposta: JSON com os detalhes da transação correspondente ao ID fornecido.
Consulta de Todas as Transações:

Endpoint: /consultas
Método HTTP: GET
Resposta: JSON contendo a lista de todas as transações registradas.


Ambiente de Desenvolvimento
Esta API foi desenvolvida utilizando tecnologias modernas, incluindo Spring Boot, que oferece um ambiente de execução robusto e de fácil configuração.

Para começar a utilizar a API de Pagamentos, siga estes passos:

Clone este repositório para o seu ambiente local.
Configure as dependências e o ambiente de execução conforme descrito no arquivo de configuração.
Execute a aplicação e certifique-se de que está funcionando corretamente.
Utilize os endpoints fornecidos para realizar pagamentos, estornos e consultas de transações.
Sinta-se à vontade para explorar a API e entre em contato caso precise de assistência ou tenha alguma dúvida.
