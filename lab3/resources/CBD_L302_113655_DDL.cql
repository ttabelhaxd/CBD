CREATE KEYSPACE IF NOT EXISTS cbd_videos WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};
use cbd_videos;

CREATE TABLE IF NOT EXISTS users (
    username TEXT PRIMARY KEY,
    name TEXT,
    email TEXT,
    registration_timestamp TIMESTAMP
);

CREATE TABLE IF NOT EXISTS videos_by_author (
    author TEXT,
    video_id UUID,
    video_name TEXT,
    description TEXT,
    tags SET<TEXT>,
    upload_timestamp TIMESTAMP,
    PRIMARY KEY (author, upload_timestamp)
) WITH CLUSTERING ORDER BY (upload_timestamp DESC);

CREATE TABLE IF NOT EXISTS comments_by_video (
    video_id UUID,
    comment_timestamp TIMESTAMP,
    author TEXT,
    comment TEXT,
    PRIMARY KEY (video_id, comment_timestamp)
) WITH CLUSTERING ORDER BY (comment_timestamp DESC);

CREATE TABLE IF NOT EXISTS comments_by_user (
    username TEXT,
    comment_timestamp TIMESTAMP,
    video_id UUID,
    comment TEXT,
    PRIMARY KEY (username, comment_timestamp)
) WITH CLUSTERING ORDER BY (comment_timestamp DESC);

CREATE TABLE IF NOT EXISTS comments_by_followed_videos (
    follower_username TEXT,
    video_id UUID,
    comment_timestamp TIMESTAMP,
    author TEXT,
    comment TEXT,
    PRIMARY KEY (follower_username, video_id, comment_timestamp)
) WITH CLUSTERING ORDER BY (video_id ASC, comment_timestamp DESC);

CREATE TABLE IF NOT EXISTS video_followers (
    video_id UUID,
    follower_username TEXT,
    follow_timestamp TIMESTAMP,
    PRIMARY KEY (video_id, follower_username)
);

CREATE TABLE IF NOT EXISTS video_events (
    video_id UUID,
    user_username TEXT,
    event_timestamp TIMESTAMP,
    event_type TEXT,
    video_time_seconds INT,
    PRIMARY KEY ((user_username, event_type), event_timestamp)
) WITH CLUSTERING ORDER BY (event_timestamp DESC);

CREATE TABLE IF NOT EXISTS video_ratings (
    video_id UUID,
    total_rating INT,
    rating_count INT,
    PRIMARY KEY (video_id, total_rating)
) WITH CLUSTERING ORDER BY (total_rating DESC);

CREATE TABLE IF NOT EXISTS playlists (
    playlist_id UUID PRIMARY KEY,
    playlist_name TEXT,
    username TEXT,
    creation_timestamp TIMESTAMP
);

CREATE TABLE IF NOT EXISTS playlist_videos (
    playlist_id UUID,
    video_id UUID,
    PRIMARY KEY (playlist_id, video_id)
);

CREATE TABLE IF NOT EXISTS playlists_by_user (
    username TEXT,
    playlist_id UUID,
    playlist_name TEXT,
    creation_timestamp TIMESTAMP,
    PRIMARY KEY (username, playlist_id)
);

CREATE INDEX IF NOT EXISTS ON videos_by_author (video_id);
CREATE INDEX IF NOT EXISTS tags_index ON videos_by_author (tags);

CREATE TABLE IF NOT EXISTS videos (
    video_id UUID,
    author TEXT,
    video_name TEXT,
    description TEXT,
    tags SET<TEXT>,
    upload_timestamp TIMESTAMP,
    PRIMARY KEY (video_id, upload_timestamp)
) WITH CLUSTERING ORDER BY (upload_timestamp DESC);

CREATE TABLE IF NOT EXISTS videos_by_upload (
    video_id UUID,
    author TEXT,
    video_name TEXT,
    description TEXT,
    tags SET<TEXT>,
    upload_timestamp TIMESTAMP,
    PRIMARY KEY ((video_id), upload_timestamp)
) WITH CLUSTERING ORDER BY (upload_timestamp DESC);

CREATE TABLE IF NOT EXISTS tags_count (
    tag TEXT PRIMARY KEY,
    video_count INT
);