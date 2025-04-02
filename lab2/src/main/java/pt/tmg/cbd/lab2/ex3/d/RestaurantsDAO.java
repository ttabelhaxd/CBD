package pt.tmg.cbd.lab2.ex3.d;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import com.mongodb.client.model.Filters;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.mongodb.client.AggregateIterable;

public class RestaurantsDAO {
    private final MongoCollection<Document> mongoCollection;

    public RestaurantsDAO(MongoCollection<Document> mongoCollection) {
        this.mongoCollection = mongoCollection;
    }

    public static int countLocalidades(MongoCollection<Document> restaurants) {
        long count = restaurants.distinct("localidade", String.class).into(new ArrayList<>()).size();
        return (int) count;
    }

    public static Map<String, Integer> countRestByLocalidade(MongoCollection<Document> restaurants) {
        AggregateIterable<Document> results = restaurants.aggregate(List.of(
                new Document("$group", new Document("_id", "$localidade").append("count", new Document("$sum", 1)))
        ));

        return results.into(new ArrayList<>())
                .stream()
                .collect(Collectors.toMap(doc -> doc.getString("_id"), doc -> doc.getInteger("count")));
    }

    public static List<String> getRestWithNameCloserTo( MongoCollection<Document> restaurants, String name) {
        List<String> list = new ArrayList<>();
        restaurants.find(Filters.regex("nome", ".*" + name + ".*")).forEach((Document doc) -> list.add(doc.getString("nome")));
        return list;
    }

    public static void main(String[] args) {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("cbd");
        MongoCollection<Document> restaurants = database.getCollection("restaurants");

        System.out.println("Número de localidades distintas: " + countLocalidades(restaurants));
        System.out.println();

        System.out.println("Número de restaurantes por localidade: ");
        for (Map.Entry<String, Integer> entry : countRestByLocalidade(restaurants).entrySet()) {
            System.out.println("  -> " + entry.getKey() + " - " + entry.getValue());
        }
        System.out.println();

        String name = "Park";
        System.out.println("Nome de restaurantes contendo '" + name + "' no nome: ");
        for (String nome : getRestWithNameCloserTo(restaurants, name)) {
            System.out.println("  -> " + nome);
        }
    }
}
