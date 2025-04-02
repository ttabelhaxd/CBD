package pt.tmg.cbd.lab2.ex4;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;
import java.util.concurrent.TimeUnit;
import static com.mongodb.client.model.Filters.*;


public class SistemaDeAtendimentoA {

    private static final int DEFAULT_LIMIT = 30;
    private static final int DEFAULT_TIMESLOT = 3600;  // 60min * 60s
    private MongoClient mongoClient;
    private MongoCollection<Document> ordersCollection;
    private int limit;
    private int timeslot;

    public SistemaDeAtendimentoA() {
        this.mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("orderSystem");
        this.ordersCollection = database.getCollection("orders");
        this.limit = DEFAULT_LIMIT;
        this.timeslot = DEFAULT_TIMESLOT;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setTimeslotSeconds(int timeslot) {
        this.timeslot = timeslot;
    }

    public void makeOrder(String username, String product) {
        long currentTime = System.currentTimeMillis() / 1000;
        long timeWindowStart = currentTime - timeslot;

        Bson timeFilter = and(eq("username", username), lt("timestamp", timeWindowStart));
        ordersCollection.deleteMany(timeFilter);

        Bson recentOrdersFilter = and(eq("username", username), gte("timestamp", timeWindowStart));
        long numOrders = ordersCollection.countDocuments(recentOrdersFilter);

        if (numOrders >= limit) {
            System.out.println("Order limit exceeded by user: " + username);
            return;
        }

        Document order = new Document("username", username)
                .append("product", product)
                .append("timestamp", currentTime);
        ordersCollection.insertOne(order);

        System.out.println("Order registered for user: " + username + ": " + product);
    }

    public void close() {
        mongoClient.close();
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        SistemaDeAtendimentoA sistemaAtendimentoA = new SistemaDeAtendimentoA();
        sistemaAtendimentoA.setTimeslotSeconds(10);

        for (int i = 0; i < 40; i++) {
            sistemaAtendimentoA.makeOrder("user1", "productA_" + i);
        }

        for (int i = 0; i < 10; i++) {
            sistemaAtendimentoA.makeOrder("user2", "productB_" + i);
        }

        try {
            TimeUnit.SECONDS.sleep(10);  // Wait for 10 seconds
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted");
            e.printStackTrace();
        }
        System.out.println("\n10 seconds passed\n");

        for (int i = 30; i < 60; i++) {
            sistemaAtendimentoA.makeOrder("user1", "productA_" + i);
        }

        long endTime = System.currentTimeMillis();
        System.out.println("\n\nExecution time: " + (endTime - startTime) + "ms");

        sistemaAtendimentoA.close();
    }
}
