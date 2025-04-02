package pt.tmg.cbd.lab2.ex3.a;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;
import org.bson.Document;

import java.util.Arrays;

public class MainA {
    public static void main(String[] args) {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("cbd");
        MongoCollection<Document> collection = database.getCollection("restaurants");

        //Create
        Document restaurant = new Document("address", new Document("building", "351")
                .append("coord", Arrays.asList(-73.98513559999999, 40.7676919))
                .append("rua", "West 57 Street")
                .append("zipcode", "10019"))
                .append("localidade", "Manhattan")
                .append("gastronomia", "Irish")
                .append("grades", Arrays.asList(
                        new Document().append("date", "2014-09-06T00:00:00.000Z").append("grade", "A").append("score", 2),
                        new Document().append("date", "2013-07-22T00:00:00.000Z").append("grade", "A").append("score", 11),
                        new Document().append("date", "2012-07-31T00:00:00.000Z").append("grade", "A").append("score", 12),
                        new Document().append("date", "2011-12-29T00:00:00.000Z").append("grade", "A").append("score", 12)
                ))
                .append("nome", "Dj Reynolds Pub And Restaurant")
                .append("restaurant_id", "30191841");

        //Insert
        collection.insertOne(restaurant);
        System.out.println("Inserted restaurant: " + restaurant.toJson());

        //Update
        Document query = new Document("restaurant_id", "30191841");
        Document new_grade = new Document().append("date", "2015-09-06T00:00:00.000Z").append("grade", "A").append("score", 2);

        collection.updateOne(query, Updates.push("grades", new_grade));
        System.out.println("Updated grades for restaurant with id 30191841");

        //Search
        Document found = collection.find(query).first();
        if (found != null) {
            System.out.println("Found restaurant: " + found.toJson());
        } else {
            System.out.println("Restaurant not found");
        }

        mongoClient.close();
    }
}
