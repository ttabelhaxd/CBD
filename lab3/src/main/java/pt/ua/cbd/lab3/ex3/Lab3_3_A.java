package pt.ua.cbd.lab3.ex3;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;

import java.net.InetSocketAddress;
import java.util.UUID;

public class Lab3_3_A {
    private static CqlSession session;

    public static void main(String[] args) {
        try {
            // Connect to Cassandra
            connect();

            // Insert data
            insertUser();
            insertVideo();

            // Update data
            updateUserEmail("johndoe", "new_email@example.com");

            // Query data
            selectVideosByAuthor("johndoe");
            selectCommentsByVideo(UUID.fromString("60914626-f04f-42a6-b06e-f11dbfd16c77"));
            selectTop3CommentsByVideo(UUID.fromString("60914626-f04f-42a6-b06e-f11dbfd16c77"));
            selectVideosByTag("Aveiro");
            selectRatingsForVideo(UUID.fromString("60914626-f04f-42a6-b06e-f11dbfd16c77"));

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

    // Close session
    private static void close() {
        if (session != null) {
            session.close();
            System.out.println("Session closed.");
        }
    }

    // Insertions
    private static void insertUser() {
        session.execute("INSERT INTO users (username, name, email, registration_timestamp) " +
                "VALUES ('johndoe', 'John Doe', 'johndoe@example.com', toTimestamp(now()))");
        System.out.println("User inserted.");
    }

    private static void insertVideo() {
        UUID videoId = UUID.randomUUID();
        session.execute("INSERT INTO videos_by_author (author, upload_timestamp, video_id, video_name, description, tags) " +
                        "VALUES ('jbetton5', toTimestamp(now()), ?, 'My First Video', 'Description of my video', {'tag1', 'tag2'})",
                videoId);
        System.out.println("Video inserted with ID: " + videoId);
    }

    // Updates
    private static void updateUserEmail(String username, String newEmail) {
        session.execute("UPDATE users SET email = ? WHERE username = ?", newEmail, username);
        System.out.println("Email updated for user: " + username);
    }

    // Queries
    private static void selectVideosByAuthor(String author) {
        ResultSet resultSet = session.execute("SELECT * FROM videos_by_author WHERE author = ?", author);
        System.out.println("Videos by author " + author + ":");
        for (Row row : resultSet) {
            System.out.println(" - " + row.getString("video_name") + " | " + row.getString("description"));
        }
    }

    private static void selectCommentsByVideo(UUID videoId) {
        ResultSet resultSet = session.execute("SELECT * FROM comments_by_video WHERE video_id = ? ORDER BY comment_timestamp DESC", videoId);
        System.out.println("Comments for video " + videoId + ":");
        for (Row row : resultSet) {
            System.out.println(" - " + row.getString("comment") + " | Author: " + row.getString("author"));
        }
    }

    private static void selectTop3CommentsByVideo(UUID videoId) {
        ResultSet resultSet = session.execute("SELECT * FROM comments_by_video WHERE video_id = ? ORDER BY comment_timestamp DESC LIMIT 3", videoId);
        System.out.println("Top 3 comments for video " + videoId + ":");
        for (Row row : resultSet) {
            System.out.println(" - " + row.getString("comment"));
        }
    }

    private static void selectVideosByTag(String tag) {
        ResultSet resultSet = session.execute("SELECT * FROM videos_by_author WHERE tags CONTAINS ?", tag);
        System.out.println("Videos with tag " + tag + ":");
        for (Row row : resultSet) {
            System.out.println(" - " + row.getString("video_name") + " | Tags: " + row.getSet("tags", String.class));
        }
    }

    private static void selectRatingsForVideo(UUID videoId) {
        ResultSet resultSet = session.execute("SELECT * FROM video_ratings WHERE video_id = ?", videoId);
        Row row = resultSet.one();
        if (row != null) {
            int totalRating = row.getInt("total_rating");
            int ratingCount = row.getInt("rating_count");
            double avgRating = (double) totalRating / ratingCount;
            System.out.println("Average rating for video " + videoId + ": " + avgRating +
                    " | Total votes: " + ratingCount);
        } else {
            System.out.println("No ratings for video " + videoId);
        }
    }
}
