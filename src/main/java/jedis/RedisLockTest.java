package jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class RedisLockTest {
    private static JedisPool pool;

    private static AtomicInteger num = new AtomicInteger(10);

    static {
        JedisPoolConfig config = new JedisPoolConfig();
        // 设置最大连接数
        config.setMaxTotal(200);
        // 设置最大空闲数
        config.setMaxIdle(8);
        // 设置最大等待时间
        config.setMaxWaitMillis(1000 * 100);
        // 在borrow一个jedis实例时，是否需要验证，若为true，则所有jedis实例均是可用的
        config.setTestOnBorrow(true);
        pool = new JedisPool(config, "172.27.109.15", 6379, 3000);
    }

    public void getA1() {
        String requestId = UUID.randomUUID().toString();
        try (Jedis resource = pool.getResource()) {
            boolean a1 = RedisTool.tryGetDistributedLock(resource, "a1", requestId, 5000);
            if (a1) {
                System.out.println(Thread.currentThread().getName() + "获得商品a1，还剩余：" + num.getAndIncrement() + "件");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    RedisTool.releaseDistributedLock(resource, "a1", requestId);
                }
            }
        }
    }

    public static void main(String[] args) {
        RedisLockTest RedisLockTest = new RedisLockTest();
        for (int i = 0; i <= 10; i++) {
            Thread A = new Thread(new Runnable() {
                @Override
                public void run() {
                    RedisLockTest.getA1();
                }
            });
            A.setName("Thread" + i);
            A.start();
        }
    }
}
