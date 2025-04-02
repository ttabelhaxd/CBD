package pt.tmg.cbd.lab1.ex3;

import redis.clients.jedis.Jedis;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class SimplePost {

	private Jedis jedis;
	public static String USERS = "users"; // Key set for users' name

	public SimplePost() {this.jedis = new Jedis("localhost", 6379);}
	public void saveUser(String username) {jedis.sadd(USERS, username);}
	public Set<String> getUser() {return jedis.smembers(USERS);}
	public Set<String> getAllKeys() {return jedis.keys("*");}

	public static void main(String[] args) {
		SimplePost board = new SimplePost();

		// set nome users
		System.out.println("Set:");
		board.jedis.del(USERS);
		String[] users = { "Ana", "Pedro", "Maria", "Luis" };
		for (String user : users){
			board.saveUser(user);
		}
		board.getUser().forEach(System.out::println);

		//List
		System.out.println("\nList:");
		board.jedis.del(USERS);
		for (String user : users)
			board.jedis.rpush(USERS, user);
		long usersLen = board.jedis.llen(USERS);
		board.jedis.lrange(USERS, 0, usersLen).forEach(System.out::println);

		//HashMap
		System.out.println("\nHashMap:");
		board.jedis.del(USERS);
		for (int i = 0; i < users.length; i++){
			board.jedis.hset(USERS, String.valueOf(i), users[i]);
		}
		for (int i = 0; i < board.jedis.hgetAll(USERS).size(); i++) {
			System.out.println("key: " + i + " value: " + board.jedis.hget(USERS, String.valueOf(i)));
		}

		System.out.println("\nKeys:");
		board.getAllKeys().forEach(System.out::println);

		board.jedis.close();
	}
}