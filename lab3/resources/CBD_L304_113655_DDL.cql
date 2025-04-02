CREATE KEYSPACE IF NOT EXISTS football WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};
USE football;

CREATE TABLE IF NOT EXISTS jogadores (
    id UUID PRIMARY KEY,
    nome TEXT,
    posicao TEXT,
    nacionalidade TEXT,
    golos SET<INT>,
    assistencias LIST<INT>,
    estatisticas MAP<TEXT, INT>
);

CREATE TABLE IF NOT EXISTS equipas (
    id UUID PRIMARY KEY,
    nome TEXT,
    liga TEXT,
    jogadores SET<UUID>,
    estatisticas MAP<TEXT, INT>
);

CREATE TABLE IF NOT EXISTS jogos (
    id UUID PRIMARY KEY,
    equipa_casa UUID,
    equipa_fora UUID,
    resultado TEXT,
    eventos LIST<TEXT>
);

CREATE TABLE IF NOT EXISTS treinadores (
    id UUID PRIMARY KEY,
    nome TEXT,
    equipas_treinadas LIST<UUID>,
    premios SET<TEXT>
);

CREATE INDEX IF NOT EXISTS jogadores_nacionalidade_index ON jogadores (nacionalidade);
CREATE INDEX IF NOT EXISTS jogos_resultado_index ON jogos (resultado);

CREATE FUNCTION IF NOT EXISTS football.average_assistencias (assistencias list<int>) // nao consegui adicionar na db
    RETURNS NULL ON NULL INPUT
    RETURNS double
    LANGUAGE java AS '
    double sum = 0;
    for (int i : assistencias) sum += i;
    return assistencias.size() == 0 ? 0 : sum / assistencias.size();
';

CREATE OR REPLACE FUNCTION football.count_function (state int, event text)
    CALLED ON NULL INPUT
    RETURNS int
    LANGUAGE java AS '
    return state + 1;
';

CREATE AGGREGATE IF NOT EXISTS football.count_events (text)
    SFUNC count_function
    STYPE int
    INITCOND 0;
