package pt.ua.cbd.lab3.ex3;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;

import java.net.InetSocketAddress;
import java.util.UUID;

public class Lab3_3_B {
    private static CqlSession session;

    public static void main(String[] args) {
        try {
            // Connect to Cassandra
            connect();

            // Perform queries
            getLast3CommentsForVideo(UUID.fromString("60914626-f04f-42a6-b06e-f11dbfd16c77"));
            getTagsForVideo("johndoe", UUID.fromString("60914626-f04f-42a6-b06e-f11dbfd16c77"));
            getVideosWithTag("Aveiro");
            getAllEventsForUser("johndoe");

        } finally {
            // Close session
            close();
        }
    }

    // Connect to Cassandra
    private static void connect() {
        session = CqlSession.builder()
                .addContactPoint(new InetSocketAddress("127.0.0.1", 9042))
                .withLocalDatacenter("datacenter1")
                .withKeyspace("cbd_videos")
                .build();

        System.out.println("Connected to Cassandra!");
    }

    // Close the session
    private static void close() {
        if (session != null) {
            session.close();
            System.out.println("Session closed.");
        }
    }

    // 1. Os ultimos 3 comentarios introduzidos para um video;
    private static void getLast3CommentsForVideo(UUID videoId) {
        ResultSet resultSet = session.execute(
                "SELECT comment, author, comment_timestamp " +
                        "FROM comments_by_video " +
                        "WHERE video_id = ? " +
                        "ORDER BY comment_timestamp DESC " +
                        "LIMIT 3", videoId
        );

        System.out.println("Last 3 comments for video " + videoId + ":");
        for (Row row : resultSet) {
            System.out.println(" - Comment: " + row.getString("comment") +
                    " | Author: " + row.getString("author") +
                    " | Timestamp: " + row.getInstant("comment_timestamp"));
        }
    }

    // 2. Lista das tags de determinado video;UA.DETI.CBD- 2023/24 15
    private static void getTagsForVideo(String author, UUID videoId) {
        ResultSet resultSet = session.execute(
                "SELECT tags " +
                        "FROM videos_by_author " +
                        "WHERE author = ? AND video_id = ?", author, videoId
        );

        Row row = resultSet.one();
        if (row != null) {
            System.out.println("Tags for video " + videoId + " by author " + author + ": " + row.getSet("tags", String.class));
        } else {
            System.out.println("No tags found for video " + videoId + " by author " + author);
        }
    }

    // 3. Todos os videos com a tag Aveiro;
    private static void getVideosWithTag(String tag) {
        ResultSet resultSet = session.execute(
                "SELECT author, video_id, video_name, description, upload_timestamp " +
                        "FROM videos_by_author " +
                        "WHERE tags CONTAINS ?", tag
        );

        System.out.println("Videos with tag '" + tag + "':");
        for (Row row : resultSet) {
            System.out.println(" - Video Name: " + row.getString("video_name") +
                    " | Description: " + row.getString("description") +
                    " | Author: " + row.getString("author") +
                    " | Video ID: " + row.getUuid("video_id") +
                    " | Upload Time: " + row.getInstant("upload_timestamp"));
        }
    }

    // 4.b. Todos os eventos de determinado utilizador;
    private static void getAllEventsForUser(String username) {
        ResultSet resultSet = session.execute(
                "SELECT video_id, event_timestamp, video_time_seconds " +
                        "FROM video_events " +
                        "WHERE user_username = ? AND event_type IN (?, ?, ?)",
                username, "pause", "play", "stop"
        );

        System.out.println("All events for user '" + username + "':");
        for (Row row : resultSet) {
            System.out.println(" - Video ID: " + row.getUuid("video_id") +
                    " | Timestamp: " + row.getInstant("event_timestamp") +
                    " | Video Time: " + row.getInt("video_time_seconds") + " seconds");
        }
    }
}
