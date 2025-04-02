INSERT INTO jogadores (id, nome, posicao, nacionalidade, golos, assistencias, estatisticas)
VALUES (d617b05d-8fa6-4a0a-82b1-208d533f5567, 'Cristiano Ronaldo', 'Avançado', 'Portugal', {12, 34, 56}, [15, 45, 78], {'jogos': 200, 'cartoes_amarelos': 5});
INSERT INTO jogadores (id, nome, posicao, nacionalidade, golos, assistencias, estatisticas)
VALUES (uuid(), 'Cristiano Ronaldo', 'Avançado', 'Portugal', {12, 34, 56}, [15, 45, 78], {'jogos': 200, 'cartoes_amarelos': 5});
INSERT INTO jogadores (id, nome, posicao, nacionalidade, golos, assistencias, estatisticas)
VALUES (uuid(), 'Lionel Messi', 'Avançado', 'Argentina', {11, 44, 88}, [10, 35, 67], {'jogos': 250, 'cartoes_amarelos': 2});
INSERT INTO jogadores (id, nome, posicao, nacionalidade, golos, assistencias, estatisticas)
VALUES (uuid(), 'Kevin De Bruyne', 'Médio', 'Bélgica', {9, 28}, [20, 50, 70], {'jogos': 180, 'cartoes_amarelos': 7});
INSERT INTO jogadores (id, nome, posicao, nacionalidade, golos, assistencias, estatisticas)
VALUES (uuid(), 'Neymar Jr.', 'Avançado', 'Brasil', {15, 33, 60}, [18, 40, 65], {'jogos': 190, 'cartoes_amarelos': 8});
INSERT INTO jogadores (id, nome, posicao, nacionalidade, golos, assistencias, estatisticas)
VALUES (uuid(), 'Virgil van Dijk', 'Defesa', 'Países Baixos', {5, 18}, [5, 15], {'jogos': 210, 'cartoes_amarelos': 6});
INSERT INTO jogadores (id, nome, posicao, nacionalidade, golos, assistencias, estatisticas)
VALUES (uuid(), 'Karim Benzema', 'Avançado', 'França', {20, 40, 70}, [15, 35], {'jogos': 220, 'cartoes_amarelos': 3});
INSERT INTO jogadores (id, nome, posicao, nacionalidade, golos, assistencias, estatisticas)
VALUES (uuid(), 'Erling Haaland', 'Avançado', 'Noruega', {10, 35, 50}, [5, 15, 30], {'jogos': 120, 'cartoes_amarelos': 2});
INSERT INTO jogadores (id, nome, posicao, nacionalidade, golos, assistencias, estatisticas)
VALUES (uuid(), 'Luka Modric', 'Médio', 'Croácia', {5, 20}, [25, 45, 60], {'jogos': 230, 'cartoes_amarelos': 3});
INSERT INTO jogadores (id, nome, posicao, nacionalidade, golos, assistencias, estatisticas)
VALUES (uuid(), 'Robert Lewandowski', 'Avançado', 'Polónia', {15, 40, 60}, [10, 20], {'jogos': 200, 'cartoes_amarelos': 4});
INSERT INTO jogadores (id, nome, posicao, nacionalidade, golos, assistencias, estatisticas)
VALUES (uuid(), 'Kylian Mbappé', 'Avançado', 'França', {12, 30, 45}, [15, 30, 50], {'jogos': 180, 'cartoes_amarelos': 5});
INSERT INTO jogadores (id, nome, posicao, nacionalidade, golos, assistencias, estatisticas)
VALUES ( 55a08390-6ba9-46a6-94e9-f5955f8964f7, 'Sergio Ramos', 'Defesa', 'Espanha', {8, 12, 15, 90}, [3, 10], {'jogos': 250, 'cartoes_amarelos': 10});


INSERT INTO equipas (id, nome, liga, jogadores, estatisticas)
VALUES ( 1181074c-da36-4951-8ca7-26b24726df63, 'Real Madrid', 'La Liga', {uuid(), uuid()}, {'vitorias': 25, 'empates': 5, 'derrotas': 3});
INSERT INTO equipas (id, nome, liga, jogadores, estatisticas)
VALUES (uuid(), 'Barcelona', 'La Liga', {uuid(), uuid()}, {'vitorias': 22, 'empates': 8, 'derrotas': 3});
INSERT INTO equipas (id, nome, liga, jogadores, estatisticas)
VALUES (uuid(), 'Manchester City', 'Premier League', {uuid(), uuid()}, {'vitorias': 27, 'empates': 6, 'derrotas': 2});
INSERT INTO equipas (id, nome, liga, jogadores, estatisticas)
VALUES (uuid(), 'Paris Saint-Germain', 'Ligue 1', {uuid(), uuid()}, {'vitorias': 30, 'empates': 4, 'derrotas': 1});
INSERT INTO equipas (id, nome, liga, jogadores, estatisticas)
VALUES (uuid(), 'Bayern Munich', 'Bundesliga', {uuid(), uuid()}, {'vitorias': 26, 'empates': 5, 'derrotas': 3});
INSERT INTO equipas (id, nome, liga, jogadores, estatisticas)
VALUES (uuid(), 'Juventus', 'Serie A', {uuid(), uuid()}, {'vitorias': 20, 'empates': 10, 'derrotas': 5});
INSERT INTO equipas (id, nome, liga, jogadores, estatisticas)
VALUES (uuid(), 'Chelsea', 'Premier League', {uuid(), uuid()}, {'vitorias': 18, 'empates': 10, 'derrotas': 6});
INSERT INTO equipas (id, nome, liga, jogadores, estatisticas)
VALUES (uuid(), 'Atlético Madrid', 'La Liga', {uuid(), uuid()}, {'vitorias': 20, 'empates': 8, 'derrotas': 5});
INSERT INTO equipas (id, nome, liga, jogadores, estatisticas)
VALUES (uuid(), 'Inter Milan', 'Serie A', {uuid(), uuid()}, {'vitorias': 22, 'empates': 5, 'derrotas': 7});
INSERT INTO equipas (id, nome, liga, jogadores, estatisticas)
VALUES (uuid(), 'Borussia Dortmund', 'Bundesliga', {uuid(), uuid()}, {'vitorias': 19, 'empates': 9, 'derrotas': 4});
INSERT INTO equipas (id, nome, liga, jogadores, estatisticas)
VALUES (uuid(), 'Liverpool', 'Premier League', {uuid(), uuid()}, {'vitorias': 23, 'empates': 6, 'derrotas': 3});


INSERT INTO jogos (id, equipa_casa, equipa_fora, resultado, eventos)
VALUES (uuid(), uuid(), uuid(), '2-1', ['45'': Golo - Benzema', '78'': Cartão Amarelo - Ramos']);
INSERT INTO jogos (id, equipa_casa, equipa_fora, resultado, eventos)
VALUES (uuid(), uuid(), uuid(), '2-1', ['10'': Golo - Benzema', '45'': Golo - Modric', '78'': Cartão Amarelo - Ramos']);
INSERT INTO jogos (id, equipa_casa, equipa_fora, resultado, eventos)
VALUES (uuid(), uuid(), uuid(), '1-0', ['35'': Golo - Messi', '90'': Cartão Amarelo - Alba']);
INSERT INTO jogos (id, equipa_casa, equipa_fora, resultado, eventos)
VALUES (uuid(), uuid(), uuid(), '3-2', ['12'': Golo - Neymar', '50'': Golo - Mbappé', '75'': Cartão Vermelho - Piqué']);
INSERT INTO jogos (id, equipa_casa, equipa_fora, resultado, eventos)
VALUES (uuid(), uuid(), uuid(), '0-0', ['80'': Cartão Amarelo - Ramos']);
INSERT INTO jogos (id, equipa_casa, equipa_fora, resultado, eventos)
VALUES ( a5748b06-0fd0-4a27-8cba-75670acbcf45, uuid(), uuid(), '4-3', ['5'': Golo - Haaland', '70'': Golo - Foden', '85'': Cartão Vermelho - Walker']);
INSERT INTO jogos (id, equipa_casa, equipa_fora, resultado, eventos)
VALUES (uuid(), uuid(), uuid(), '2-2', ['15'': Golo - Lewandowski', '55'': Golo - Müller', '88'': Golo - Ronaldo']);
INSERT INTO jogos (id, equipa_casa, equipa_fora, resultado, eventos)
VALUES (uuid(), uuid(), uuid(), '3-1', ['12'': Golo - Haaland', '60'': Golo - Silva', '90'': Golo - Foden']);
INSERT INTO jogos (id, equipa_casa, equipa_fora, resultado, eventos)
VALUES (uuid(), uuid(), uuid(), '2-2', ['25'': Golo - Benzema', '75'': Golo - Modric', '85'': Cartão Amarelo - Courtois']);
INSERT INTO jogos (id, equipa_casa, equipa_fora, resultado, eventos)
VALUES (uuid(), uuid(), uuid(), '1-0', ['45'': Golo - Mbappé', '70'': Cartão Vermelho - Ramos']);
INSERT INTO jogos (id, equipa_casa, equipa_fora, resultado, eventos)
VALUES (uuid(), uuid(), uuid(), '0-1', ['55'': Golo - Lewandowski', '80'': Cartão Vermelho - Alba']);
INSERT INTO jogos (id, equipa_casa, equipa_fora, resultado, eventos)
VALUES (uuid(), uuid(), uuid(), '4-0', ['10'': Golo - Neymar', '35'': Golo - Messi', '60'': Golo - Hakimi']);


INSERT INTO treinadores (id, nome, equipas_treinadas, premios)
VALUES (uuid(), 'Zinedine Zidane', [uuid(), uuid()], {'Melhor Treinador 2020', 'Melhor Treinador 2017'});
INSERT INTO treinadores (id, nome, equipas_treinadas, premios)
VALUES (uuid(), 'Pep Guardiola', [uuid(), uuid(), uuid()], {'Melhor Treinador 2022', 'Melhor Treinador Premier League'});
INSERT INTO treinadores (id, nome, equipas_treinadas, premios)
VALUES (uuid(), 'Zinedine Zidane', [uuid(), uuid()], {'Melhor Treinador 2020', 'Melhor Treinador Champions League'});
INSERT INTO treinadores (id, nome, equipas_treinadas, premios)
VALUES (uuid(), 'Jürgen Klopp', [uuid(), uuid()], {'Melhor Treinador 2021'});
INSERT INTO treinadores (id, nome, equipas_treinadas, premios)
VALUES (uuid(), 'Carlo Ancelotti', [uuid(), uuid(), uuid()], {'Treinador com mais títulos'});
INSERT INTO treinadores (id, nome, equipas_treinadas, premios)
VALUES (uuid(), 'Thomas Tuchel', [uuid(), uuid()], {'Melhor Treinador 2023', 'Treinador do Ano UEFA'});
INSERT INTO treinadores (id, nome, equipas_treinadas, premios)
VALUES (ae7ddb4e-053e-4e08-859a-fd9f842e2308, 'Mauricio Pochettino', [uuid()], {'Melhor Treinador Ligue 1'});
INSERT INTO treinadores (id, nome, equipas_treinadas, premios)
VALUES (uuid(), 'Diego Simeone', [uuid(), uuid()], {'Melhor Treinador La Liga', 'Treinador Mais Intenso'});
INSERT INTO treinadores (id, nome, equipas_treinadas, premios)
VALUES (uuid(), 'Erik ten Hag', [uuid(), uuid()], {'Melhor Treinador Eredivisie', 'Treinador do Ano 2022'});
INSERT INTO treinadores (id, nome, equipas_treinadas, premios)
VALUES (uuid(), 'Massimiliano Allegri', [uuid(), uuid()], {'Melhor Treinador Serie A', 'Treinador com Mais Títulos Italianos'});
INSERT INTO treinadores (id, nome, equipas_treinadas, premios)
VALUES (uuid(), 'Hans-Dieter Flick', [uuid(), uuid()], {'Melhor Treinador da Bundesliga', 'Treinador da Tríplice Coroa'});
INSERT INTO treinadores (id, nome, equipas_treinadas, premios)
VALUES (uuid(), 'Antonio Conte', [uuid(), uuid(), uuid()], {'Melhor Treinador Premier League', 'Melhor Treinador Serie A'});