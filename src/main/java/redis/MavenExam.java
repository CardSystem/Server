package redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class MavenExam {
	public static void main(String[] args) {

		String IP = "localhost";
		int PORT = 6379;
		int TIME_OUT = 1000;

		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();

		JedisPool pool = new JedisPool(jedisPoolConfig, IP, PORT, TIME_OUT);

		Jedis jedis = pool.getResource();

		jedis.set("NAME", "YUNJU");

		System.out.println(jedis.get("NAME"));

		if (jedis != null) {
			jedis.close();
			jedis = null;
		}
		pool.close();
	}
}