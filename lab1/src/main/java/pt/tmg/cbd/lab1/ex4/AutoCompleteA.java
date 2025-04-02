package pt.tmg.cbd.lab1.ex4;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

import redis.clients.jedis.Jedis;

public class AutoCompleteA {
    private Jedis jedis;
    public AutoCompleteA() {this.jedis = new Jedis("localhost", 6379);}

    public static void main(String[] args) {
        AutoCompleteA ac = new AutoCompleteA();
        ac.jedis.flushDB();

        try {
            File file = new File("src/main/java/pt/tmg/cbd/lab1/ex4/names.txt");
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                String name = sc.nextLine();
                for (int i = 0; i < name.length(); i++) {
                    //adicionar nomes ao set
                    ac.jedis.zadd("names", 0, name);
                }
            }

            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("Ficheiro não encontrado.");
            e.printStackTrace();
        }

        Scanner sc = new Scanner(System.in);
        String search = " ";

        while (!search.equals("")) {
            System.out.print("\nSearch for ('Enter' for quit): ");
            search = sc.nextLine().toLowerCase();

            if (!search.equals("")) {
                //dar print dos nomes que começam pelo input no search
                ac.jedis.zrangeByLex("names", "[" + search, "[" + search + (char) 0xFF).forEach(System.out::println);
            }
        }

        sc.close();
        ac.jedis.close();
    }
}
