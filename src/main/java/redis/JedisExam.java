package redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisExam {
	public static void main(String[] args) {

		int TIME_OUT = 1000;

		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();

		JedisPool pool = new JedisPool(jedisPoolConfig, "localhost", 6379, TIME_OUT);

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