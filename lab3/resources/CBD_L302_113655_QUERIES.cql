// 1. Os ultimos 3 comentarios introduzidos para um video;
SELECT comment, author, comment_timestamp
FROM comments_by_video
WHERE video_id = 48fe7f15-d846-4db7-843e-e5b34051542b
ORDER BY comment_timestamp DESC
LIMIT 3;

// 2. Lista das tags de determinado video;UA.DETI.CBD- 2023/24 15
SELECT tags
FROM videos_by_author
WHERE author = 'johndoe' AND video_id = 60914626-f04f-42a6-b06e-f11dbfd16c77;

// 3. Todos os videos com a tag Aveiro;
SELECT author, video_id, video_name, description, upload_timestamp
FROM videos_by_author
WHERE tags CONTAINS 'Aveiro';

// 4.a. Os últimos 5 eventos de determinado vídeo realizados por um utilizador;
SELECT event_type, event_timestamp, video_time_seconds
FROM video_events
WHERE video_id = d4fc01d0-996e-4a90-9002-f5784d22a094 AND user_username = 'bob'
ORDER BY event_timestamp DESC
LIMIT 5;

// 4.b. Todos os eventos de determinado utilizador;
SELECT video_id, event_timestamp, video_time_seconds
FROM video_events
WHERE user_username = 'alice' AND event_type IN ('pause', 'play', 'stop');

// 4.c. Todos os eventos de determinado utilizador to tipo "pause"
SELECT video_id, event_timestamp, video_time_seconds
FROM video_events
WHERE user_username = 'alice' AND event_type = 'pause'
ORDER BY event_timestamp DESC;

// 5. Videos partilhados por determinado utilizador (maria1987, por exemplo) num determinado periodo de tempo (Agosto de 2017, por exemplo);
SELECT video_id, video_name, description, upload_timestamp
FROM videos_by_author
WHERE author = 'maria1987' AND upload_timestamp >= '2017-08-01' AND upload_timestamp < '2017-09-01';

// 6. Os ultimos 10 videos, ordenado inversamente pela data da partilhada;


// 7. Todos os seguidores (followers) de determinado video;
SELECT follower_username, follow_timestamp
FROM video_followers
WHERE video_id = a24fc2cd-baec-4641-b84d-b0391302561d;

// 8. Todos os comentarios (dos videos) que determinado utilizador esta a seguir (following);
SELECT video_id, comment_timestamp, author, comment
FROM comments_by_followed_videos
WHERE follower_username = 'johndoe';

// 9. Os 5 videos com maior rating;
SELECT video_id, total_rating, rating_count
FROM video_ratings
LIMIT 5;

// 10. Uma query que retorne todos os videos e que mostre claramente a forma pela qual estao ordenados;
SELECT author, video_id, video_name, description, upload_timestamp
FROM videos
ORDER BY upload_timestamp DESC;

// 11. Lista com as Tags existentes e o numero de videos catalogados com cada uma delas;
// não é possivel

// 12. Lista todas as playlists criadas por um determinado utilizador.
SELECT playlist_id, playlist_name, creation_timestamp
FROM playlists_by_user
WHERE username = 'johndoe';

// 13. Lista todos os videos de uma determinada playlist;
SELECT playlist_id, video_id
FROM playlist_videos
WHERE playlist_id = e5e0ba74-3dc5-4bc6-9526-b443fe68ef39;

// 14. Lista todos os utilizadores que criaram playlists e o numero de playlists criadas por cada um;
SELECT username, COUNT(playlist_id) AS playlist_count
FROM playlists_by_user
GROUP BY username;

