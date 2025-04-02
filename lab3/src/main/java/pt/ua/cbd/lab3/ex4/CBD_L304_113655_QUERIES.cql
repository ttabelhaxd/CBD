// 1. Procurar todos os jogadores portugueses.
SELECT * FROM jogadores WHERE nacionalidade = 'Portugal';

// 2. Procurar todas as equipas que tenham mais de 20 vitórias.
SELECT nome FROM equipas WHERE estatisticas['vitorias'] > 20; // mal

// 3. Últimos 3 eventos de um jogo.
SELECT eventos FROM jogos WHERE id = a5748b06-0fd0-4a27-8cba-75670acbcf45 LIMIT 3;

// 4. Golos de um jogador por ordem.
SELECT golos FROM jogadores WHERE id = d617b05d-8fa6-4a0a-82b1-208d533f5567;

// 5. Equipas treinadas por um treinador.
SELECT equipas_treinadas FROM treinadores WHERE id = ae7ddb4e-053e-4e08-859a-fd9f842e2308;

// 6. Jogos com resultado '2-1'.
SELECT * FROM jogos WHERE resultado = '2-1';

// 7. Média de assistências de um jogador.
SELECT average_assistencias(assistencias) FROM jogadores WHERE id = d617b05d-8fa6-4a0a-82b1-208d533f5567; // ver a funcao

// 8. Nome e prémios de todos os treinadores premiados.
SELECT nome, premios FROM treinadores;

// 9. Número de eventos de um jogo.
SELECT count_events(eventos) FROM jogos WHERE id = a5748b06-0fd0-4a27-8cba-75670acbcf45; // ver a funcao

// 10. Golos de um jogador após os 90 minutos.
SELECT nome, golos FROM jogadores WHERE id =  55a08390-6ba9-46a6-94e9-f5955f8964f7 AND golos CONTAINS 90 ALLOW FILTERING;