UPDATE jogadores SET golos = golos + {90} WHERE id = d617b05d-8fa6-4a0a-82b1-208d533f5567;
UPDATE equipas SET estatisticas['vitorias'] = 26 WHERE id =  1181074c-da36-4951-8ca7-26b24726df63;
UPDATE treinadores SET premios = premios + {'Melhor Treinador 2024'} WHERE id = ae7ddb4e-053e-4e08-859a-fd9f842e2308;
UPDATE jogadores SET assistencias = assistencias + [90] WHERE id = d617b05d-8fa6-4a0a-82b1-208d533f5567;
UPDATE jogos SET eventos = eventos + ['90'': Golo - Modric'] WHERE id = a5748b06-0fd0-4a27-8cba-75670acbcf45;

DELETE assistencias[0] FROM jogadores WHERE id = d617b05d-8fa6-4a0a-82b1-208d533f5567;
DELETE estatisticas['vitorias'] FROM equipas WHERE id = 1181074c-da36-4951-8ca7-26b24726df63;
DELETE FROM treinadores WHERE id = ae7ddb4e-053e-4e08-859a-fd9f842e2308;
DELETE FROM jogos WHERE id = a5748b06-0fd0-4a27-8cba-75670acbcf45;
DELETE FROM equipas WHERE id = 1181074c-da36-4951-8ca7-26b24726df63;
