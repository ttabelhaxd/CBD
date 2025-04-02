package pt.ua.cbd.lab4.ex4;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;

import java.util.HashMap;
import java.util.Map;

/*
 Entities:
    - Customer
        - id
        - yearBirth
        - maritalStatus
        - income
        - dtCustomer

    - Product
        - C1 (category)

    - Campaign
        - C1 (category)

    - Family
        - type

    - Education
        - name

 Relations:
    - Customer -> BOUGHT -> Product
        - Properties:
            - amount

    - Customer -> PARTICIPATED_IN -> Campaign
        - Properties:
            - accepted

    - Customer -> HAS_KIDS -> Family
        - Properties:
            - numberOfKids

    - Customer -> HAS_TEENS -> Family
        - Properties:
            - numberOfTeens

    - Customer -> HAS_EDUCATION -> Education


 */

public class Main implements AutoCloseable {

    private final Driver driver;

    public Main(String uri, String user, String password) {
        driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
    }

    public void close() throws RuntimeException {
        driver.close();
    }

    private void loadCSV(String csvFilePath, String query) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath));
             Session session = driver.session()) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Map<String, Object> params = new HashMap<>();

                for (int i = 0; i < values.length; i++) {
                    String value = values[i].trim();
                    if (value.matches("\\d+")) {
                        params.put("col" + i, Integer.parseInt(value));
                    } else {
                        params.put("col" + i, value);
                    }
                }

                session.executeWrite(tx -> {
                    tx.run(query, params).consume();
                    return null;
                });
            }
        }
    }

    public void loadData() throws IOException {
        // Load customers
        String customerQuery = """
            MERGE (c:Customer {id: $col0})
            SET c.yearBirth = $col1, c.maritalStatus = $col3,
                c.income = toInteger($col4), c.dtCustomer = $col7;
            """;
        loadCSV("resources/customers.csv", customerQuery);

        // Load products
        String productQuery = """
            MERGE (p:Product {name: $col0});
            """;
        loadCSV("resources/products.csv", productQuery);

        // Load campaigns
        String campaignQuery = """
            MERGE (c:Campaign {name: $col0});
            """;
        loadCSV("resources/campaigns.csv", campaignQuery);

        // Load bought relationships
        String boughtQuery = """
            MATCH (cust:Customer {id: $col0}), (prod:Product {name: $col1})
            MERGE (cust)-[r:BOUGHT {amount: toInteger($col2)}]->(prod);
            """;
        loadCSV("resources/bought.csv", boughtQuery);

        // Load participated relationships
        String participatedQuery = """
            MATCH (cust:Customer {id: $col0}), (camp:Campaign {name: $col1})
            MERGE (cust)-[:PARTICIPATED_IN {accepted: $col2}]->(camp);
            """;
        loadCSV("resources/participated.csv", participatedQuery);

        // Load HAS_KIDS relationships
        String hasKidsQuery = """
            MATCH (cust:Customer {id: $col0})
            MERGE (family:Family {type: \"Kids\"})
            MERGE (cust)-[:HAS_KIDS {numberOfKids: toInteger($col5)}]->(family);
            """;
        loadCSV("resources/customers.csv", hasKidsQuery);

        // Load HAS_TEENS relationships
        String hasTeensQuery = """
            MATCH (cust:Customer {id: $col0})
            MERGE (family:Family {type: \"Teens\"})
            MERGE (cust)-[:HAS_TEENS {numberOfTeens: toInteger($col6)}]->(family);
            """;
        loadCSV("resources/customers.csv", hasTeensQuery);

        // Load HAS_EDUCATION relationships
        String hasEducationQuery = """
            MATCH (cust:Customer {id: $col0})
            MERGE (edu:Education {name: $col2})
            MERGE (cust)-[:HAS_EDUCATION]->(edu);
            """;
        loadCSV("resources/customers.csv", hasEducationQuery);
    }

    public void executeQueries() throws IOException {
        String outputFile = "CBD_L44c_output.txt";
        try (Session session = driver.session(); FileWriter writer = new FileWriter(outputFile)) {
            String[] queries = {
                    // Query 1 - Find all customers with kids and the number of kids they have
                    "MATCH (cust:Customer)-[:BOUGHT]->(prod:Product) RETURN cust.id AS customerId, COUNT(prod) AS productsBought ORDER BY productsBought DESC LIMIT 10",
                    // Query 2 - Find all customers with teens and their number of teens
                    "MATCH (cust:Customer)-[:PARTICIPATED_IN]->(camp:Campaign) RETURN cust.id AS customerId, COUNT(camp) AS campaignsParticipated ORDER BY campaignsParticipated DESC LIMIT 10",
                    // Query 3 - Find customers who have never participated in any campaign but have bought products
                    "MATCH (cust:Customer)-[:BOUGHT]->(prod:Product) WHERE NOT (cust)-[:PARTICIPATED_IN]->(:Campaign) RETURN cust.id AS customerId, COUNT(prod) AS productsBought ORDER BY productsBought DESC LIMIT 10",
                    // Query 4 - Get the total income of customers grouped by their education level
                    "MATCH (cust:Customer)-[:HAS_KIDS]->(family:Family {type: \"Kids\"}) RETURN cust.id AS customerId, family.numberOfKids AS numberOfKids ORDER BY numberOfKids DESC LIMIT 10",
                    // Query 5 - List campaigns with the highest participation (accepted or not)
                    "MATCH (cust:Customer)-[r:PARTICIPATED_IN]->(camp:Campaign) RETURN camp.name AS campaignName, COUNT(r) AS participationCount ORDER BY participationCount DESC LIMIT 10",
                    // Query 6 - Find the most purchased product categories by customers with kids
                    "MATCH (cust:Customer)-[:HAS_EDUCATION]->(edu:Education) RETURN edu.name AS educationName, COUNT(cust) AS customerCount ORDER BY customerCount DESC LIMIT 10",
                    // Query 7 - Find customers with the highest total number of kids and teens combined
                    "MATCH (cust:Customer) RETURN cust.maritalStatus AS maritalStatus, COUNT(cust) AS customerCount ORDER BY customerCount DESC LIMIT 10",
                    // Query 8 - Find customers who bought products and also participated in campaigns, regardless of matching names
                    "MATCH (cust:Customer)-[:BOUGHT]->(prod:Product), (cust)-[:PARTICIPATED_IN]->(camp:Campaign) RETURN cust.id AS customerId, COUNT(prod) AS productsBought, COUNT(camp) AS campaignsParticipated ORDER BY campaignsParticipated DESC, productsBought DESC LIMIT 10",
                    // Query 9 - Get the total number of kids and teens for each marital status
                    "MATCH (cust:Customer)-[:HAS_TEENS]->(family:Family {type: \"Teens\"}) RETURN cust.id AS customerId, family.numberOfTeens AS numberOfTeens ORDER BY numberOfTeens DESC LIMIT 10",
                    // Query 10 - Find customers who participated in the most campaigns and their education level
                    "MATCH (cust:Customer) RETURN AVG(cust.income) AS averageIncome"
            };

            for (int i = 0; i < queries.length; i++) {
                Result result = session.run(queries[i]);
                writer.write("Query " + (i + 1) + ":\n");

                while (result.hasNext()) {
                    writer.write(result.next().toString() + "\n");
                }

                writer.write("\n");
            }
        }
    }

    public static void main(String... args) throws IOException {
        try (var app = new Main("bolt://localhost:7687", "neo4j", "password")) {
            System.out.println("Loading data into Neo4j...");
            app.loadData();
            System.out.println("Data loaded successfully!");

            System.out.println("Executing queries and writing results to file...");
            app.executeQueries();
            System.out.println("Queries executed and results written to file successfully!");
        }
    }
}
