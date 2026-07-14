# **Problema do Passeio do Cavalo**

O **Problema do Passeio do Cavalo** é um problema clássico da matemática e da ciência da computação, 
relacionado ao movimento do cavalo no tabuleiro de xadrez.

Dado um tabuleiro de xadrez (_normalmente de 8x8 casas_), o problema pergunta:

    É possível mover um cavalo pelo tabuleiro de forma que ele visite cada casa exatamente uma vez?

Esse percurso é chamado de passeio do cavalo ([Knight's tour](https://en.wikipedia.org/wiki/Knight%27s_tour)).

O código deste repositório fornece uma implementação em **Java** que calcula a quantidade de caminhos possíveis 
do passeio do cavalo de um tabuleiro de xadrez de dimensão **N**x**N**.

---

## **Como utilizar a API**


Para transformar o cálculo matemático em um serviço acessível, o sistema foi exposto através de uma API REST.

*   **Base URL: http://localhost:8080/api/cavalo (ou a URL de produção correspondente, ex: https://passeio-cavalo.starfishcloud.com.br/api/cavalo)**

### 1. Iniciar o cálculo
Utilize este endpoint para disparar o processamento do passeio do cavalo.

* **Endpoint:** `POST /api/cavalo/calcular`
* **Corpo da requisição (JSON):**
    ```json
    {
      "dimensao": 8,
      "token": "seu_token_aqui"
    }
    ```
* **Nota:** O cálculo é processado em background para não bloquear o servidor.

### 2. Monitorar o progresso
Como o processamento pode ser demorado (especialmente em tabuleiros maiores), utilize este endpoint para consultar o status atual da execução.

* **Endpoint:** `GET /api/cavalo/status`
* **Resposta esperada:**
    ```json
    {
      "progresso": "45.00%",
      "concluidas": 7,
      "total": 16
    }
    ```

### 3. Verificação de prontidão
* **Endpoint:** `GET /api/cavalo/health`
* **Resposta:** Retorna `UP` se o serviço estiver rodando corretamente.

---