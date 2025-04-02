package pt.tmg.cbd.lab2.ex3.b;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MainB {
    public static void main(String[] args) {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("cbd");
        MongoCollection<Document> collection = database.getCollection("restaurants");

        collection.createIndex(new Document("localidade", 1));
        collection.createIndex(new Document("gastronomia", 1));
        collection.createIndex(new Document("nome", "text"));

        System.out.println("Indexes for localidade, gastronomia and nome created");

        Document queryByLocalidade = new Document("localidade", "Manhattan");
        long startTime = System.nanoTime();
        for (Document doc : collection.find(queryByLocalidade)) {
            System.out.println("Result found for localidade: " + doc.toJson());
        }
        long endTime = System.nanoTime();
        System.out.println("Execution time for localidade: " + (endTime - startTime) + " nanoseconds");

        System.out.println();
        Document queryByGastronomia = new Document("gastronomia", "Irish");
        startTime = System.nanoTime();
        for (Document doc : collection.find(queryByGastronomia)) {
            System.out.println("Result found for gastronomia: " + doc.toJson());
        }
        endTime = System.nanoTime();
        System.out.println("Execution time for gastronomia: " + (endTime - startTime) + " nanoseconds");

        System.out.println();
        Document textSearch = new Document("$text", new Document("$search", "Dj Reynolds"));
        startTime = System.nanoTime();
        for (Document doc : collection.find(textSearch)) {
            System.out.println("Result found for nome: " + doc.toJson());
        }
        endTime = System.nanoTime();
        System.out.println("Execution time for nome: " + (endTime - startTime) + " nanoseconds");

        mongoClient.close();
    }
}
