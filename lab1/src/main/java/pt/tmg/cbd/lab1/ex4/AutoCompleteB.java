    package pt.tmg.cbd.lab1.ex4;

    import java.util.List;
    import java.util.Scanner;
    import java.io.File;
    import java.io.FileNotFoundException;

    import redis.clients.jedis.Jedis;

    public class AutoCompleteB {
        private Jedis jedis;
        public AutoCompleteB() {this.jedis = new Jedis("localhost", 6379);}

        public static void main(String[] args) {
            AutoCompleteB ac = new AutoCompleteB();
            ac.jedis.flushDB();

            try {
                File file = new File("src/main/java/pt/tmg/cbd/lab1/ex4/nomes-pt-2021.csv");
                Scanner sc = new Scanner(file);

                while (sc.hasNextLine()) {
                    String[] line = sc.nextLine().split(";");
                    String name = line[0];
                    int count = Integer.parseInt(line[1]);

                    ac.jedis.zadd("nomes", count, name);
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
                    List<String> names = ac.jedis.zrevrangeByScore("nomes", "+inf", "-inf");
                    for (String name : names) {
                        if (name.toLowerCase().startsWith(search)) {
                            System.out.println(name);
                        }
                    }
                }
            }

            sc.close();
            ac.jedis.close();
        }
    }
