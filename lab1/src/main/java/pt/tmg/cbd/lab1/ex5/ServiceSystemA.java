package pt.tmg.cbd.lab1.ex5;

import redis.clients.jedis.Jedis;

public class ServiceSystemA {

    private static final int DEFAULT_LIMIT = 30;
    private static final int DEFAULT_TIMESLOT = 3600;  // 60min * 60s
    private Jedis jedis;
    private int limit;
    private int timeslot;

    public ServiceSystemA() {
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

    public void makeOrder(String username, String product) {
        String key = username + ":orders";
        long currentTime = System.currentTimeMillis() / 1000;
        jedis.zremrangeByScore(key, 0, currentTime - timeslot);
        long numOrders = jedis.zcard(key);

        if (numOrders >= limit) {
            System.out.println("Order limit exceeded by user: " + username);
            return;
        }

        jedis.zadd(key, currentTime, product);
        System.out.println("Order registered for user: " + username + ": " + product);
    }

    public static void main(String[] args) {
        Jedis jedis = new Jedis();
        long startTime = System.currentTimeMillis();
        jedis.flushAll();

        ServiceSystemA sistemaAtendimentoA = new ServiceSystemA();
        sistemaAtendimentoA.setTimeslotSeconds(10);

        for (int i = 0; i < 40; i++) {
            sistemaAtendimentoA.makeOrder("user1", "productA_" + i);
        }

        for (int i = 0; i < 10; i++) {
            sistemaAtendimentoA.makeOrder("user2", "productB_" + i);
        }

        try {
            Thread.sleep(10000);
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

        jedis.close();
    }
}