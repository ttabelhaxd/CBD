package pt.tmg.cbd.lab2.ex3.c;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Arrays;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;

public class MainC {
    public static void main(String[] args) {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("cbd");
        MongoCollection<Document> restaurants = database.getCollection("restaurants");

        // 8. Indique os restaurantes com latitude inferior a -95,7.
        //db.restaurants.find({"address.coord.0": {$lt: -95.7}})
        FindIterable<Document> query8 = restaurants.find(lt("address.coord.0", -95.7));
        for (Document doc : query8) {
            System.out.println(doc.toJson());
        }

        // 10. Liste o restaurant_id, o nome, a localidade e gastronomia dos restaurantes cujo nome começam por "Wil".
        //db.restaurants.find({"nome": /^Wil/}, {_id: 0, restaurant_id: 1, localidade: 1, gastronomia: 1})
        Bson query10Filter = regex("nome", "^Wil");
        Bson query10Projection = fields(include("restaurant_id", "nome", "localidade", "gastronomia"), excludeId());
        FindIterable<Document> query10 = restaurants.find(query10Filter).projection(query10Projection);
        for (Document doc : query10) {
            System.out.println(doc.toJson());
        }

        // 12. Liste o restaurant_id, o nome, a localidade e a gastronomia dos restaurantes localizados em "Staten Island", "Queens", ou "Brooklyn".
        //db.restaurants.find({localidade: {$in: ["Staten Island", "Queens", "Brooklyn"]}}, {_id: 0, restaurant_id: 1,localidade: 1, gastronomia: 1})
        Bson query12Filter = in("localidade", Arrays.asList("Staten Island", "Queens", "Brooklyn"));
        Bson query12Projection = fields(include("restaurant_id", "nome", "localidade", "gastronomia"), excludeId());
        FindIterable<Document> query12 = restaurants.find(query12Filter).projection(query12Projection);
        for (Document doc : query12) {
            System.out.println(doc.toJson());
        }

        // 14. Liste o nome e as avaliações dos restaurantes que obtiveram uma avaliação com um grade "A", um score 10 na data "2014-08-11T00: 00: 00Z" (ISODATE).
        //db.restaurants.find({"grades": {"$elemMatch": {grade:"A", score: 10, date: ISODate("2014-08-11T00:00:00Z")}}}, {_id: 0, "nome": 1, "grades.grade":1})
        Bson query14Filter = Filters.elemMatch("grades", and(eq("grade", "A"), eq("score", 10), eq("date", "2014-08-11T00:00:00Z")));
        Bson query14Projection = fields(include("nome", "grades.grade"), excludeId());
        FindIterable<Document> query14 = restaurants.find(query14Filter).projection(query14Projection);
        for (Document doc : query14) {
            System.out.println(doc.toJson());
        }
    }
}
