package pt.tmg.cbd.lab1.ex5;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.List;
import java.util.concurrent.TimeUnit;
import static com.mongodb.client.model.Filters.*;

public class SistemaDeAtendimentoB {

    private static final int DEFAULT_LIMIT = 30;
    private static final int DEFAULT_TIMESLOT = 3600; // 60min * 60s
    private MongoClient mongoClient;
    private MongoCollection<Document> ordersCollection;
    private int limit;
    private int timeslot;

    public SistemaDeAtendimentoB() {
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

    public void makeOrder(String username, String product, int quantity) {
        long currentTime = System.currentTimeMillis() / 1000;
        long timeWindowStart = currentTime - timeslot;

        Bson timeFilter = and(eq("username", username), lt("timestamp", timeWindowStart));
        ordersCollection.deleteMany(timeFilter);

        Bson recentOrdersFilter = and(eq("username", username), gte("timestamp", timeWindowStart));
        List<Document> recentOrders = ordersCollection.find(recentOrdersFilter).into(new java.util.ArrayList<>());

        int totalUnits = 0;
        for (Document order : recentOrders) {
            totalUnits += order.getInteger("quantity");
        }

        if (totalUnits + quantity > limit) {
            System.out.println("Limit of product units exceeded by user: " + username);
            return;
        }

        Document order = new Document("username", username)
                .append("product", product)
                .append("quantity", quantity)
                .append("timestamp", currentTime);
        ordersCollection.insertOne(order);

        System.out.println("Order registered for user: " + username + ": " + product + " - Quantity: " + quantity);
    }

    public void close() {
        mongoClient.close();
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        SistemaDeAtendimentoB sistemaAtendimentoB = new SistemaDeAtendimentoB();
        sistemaAtendimentoB.setTimeslotSeconds(10);

        sistemaAtendimentoB.makeOrder("user1", "productA", 10);
        sistemaAtendimentoB.makeOrder("user1", "productB", 20);
        sistemaAtendimentoB.makeOrder("user1", "productC", 30);
        sistemaAtendimentoB.makeOrder("user1", "productD", 40);

        try {
            TimeUnit.SECONDS.sleep(5); //Wait for 5 seconds
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted");
            e.printStackTrace();
        }
        System.out.println("\n5 seconds passed\n");

        sistemaAtendimentoB.makeOrder("user2", "produtoD", 5);
        sistemaAtendimentoB.makeOrder("user2", "produtoE", 10);
        sistemaAtendimentoB.makeOrder("user2", "produtoF", 5);
        sistemaAtendimentoB.makeOrder("user2", "produtoG", 10);

        long endTime = System.currentTimeMillis();
        System.out.println("\n\nExecution time: " + (endTime - startTime) + "ms");

        sistemaAtendimentoB.close();
    }
}
