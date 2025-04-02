package pt.tmg.cbd.lab1.ex5;

import java.util.List;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.resps.Tuple;

public class ServiceSystemB {
    private static final int DEFAULT_LIMIT = 30;
    private static final int DEFAULT_TIMESLOT = 3600; //60min*60s

    private Jedis jedis;
    private int limit;
    private int timeslot;

    public ServiceSystemB() {
        this.jedis = new Jedis();
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
        String key = username + ":pedidos";
        long currentTime = System.currentTimeMillis() / 1000;
        jedis.zremrangeByScore(key, 0, currentTime - timeslot);

        List<Tuple> quantityOfProducts = jedis.zrangeByScoreWithScores(key, 0, Double.POSITIVE_INFINITY);

        int totalUnits= 0;
        for (Tuple p : quantityOfProducts) {
            totalUnits += Integer.parseInt(p.getElement().split("/")[1].trim());
        }

        if (totalUnits + quantity > limit) {
            System.out.println("Limit of product units exceeded by user: " + username);
            return;
        }

        jedis.zadd(key, currentTime, product + "/ " + quantity);
        System.out.println("Order registered for user: " + username + ": " + product + " - Quantity: " + quantity);
    }
    public static void main(String[] args) {
        Jedis jedis = new Jedis();
        long startTime = System.currentTimeMillis();
        jedis.flushAll();
        jedis.close();

        ServiceSystemB sistemaAtendimentoB = new ServiceSystemB();
        sistemaAtendimentoB.setTimeslotSeconds(10);

        sistemaAtendimentoB.makeOrder("user1", "productA", 10);
        sistemaAtendimentoB.makeOrder("user1", "productB", 20);
        sistemaAtendimentoB.makeOrder("user1", "productC", 30);
        sistemaAtendimentoB.makeOrder("user1", "productD", 40);

        try {
            Thread.sleep(5000);
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

        jedis.close();
    }
}
