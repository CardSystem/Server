package redis;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;

public class RedisLockUtil {
	 private static final String REDIS_HOST = "localhost";
	    private static final int REDIS_PORT = 6379;
	    private static final String LOCK_KEY_PREFIX = "lock:";

	    private static RedissonClient redissonClient;

	    static {
	        Config config = new Config();
	        SingleServerConfig serverConfig = config.useSingleServer()
	                .setAddress("redis://" + REDIS_HOST + ":" + REDIS_PORT)
	                .setTimeout(5000);
	        redissonClient = Redisson.create(config);
	    }

	    public static RLock acquireLock(String lockKey) {
	        String fullLockKey = LOCK_KEY_PREFIX + lockKey;
	        RLock lock = redissonClient.getLock(fullLockKey);
	        lock.lock();
	        return lock;
	    }

	    public static void releaseLock(RLock lock) {
	        lock.unlock();
	    }
}
