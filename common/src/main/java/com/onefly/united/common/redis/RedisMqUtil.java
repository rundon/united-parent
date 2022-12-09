package com.onefly.united.common.redis;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.onefly.united.common.constant.Constant;
import com.onefly.united.common.utils.SpringContextUtils;
import org.apache.commons.lang.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;

public class RedisMqUtil {

    private static RedisProperties redisProperties;

    static {
        redisProperties = SpringContextUtils.getBean(RedisProperties.class);
    }

    private static Supplier<RedissonClient> cachedSupplier = Suppliers.memoize(new Supplier<RedissonClient>() {
        @Override
        public RedissonClient get() {
            Config config = new Config();
            config.useSingleServer().setAddress("redis://" + redisProperties.getHost() + ":" + redisProperties.getPort()).setDatabase(0);
            if (StringUtils.isNotBlank(redisProperties.getPassword())) {
                config.useSingleServer().setPassword(redisProperties.getPassword());
            }
            return Redisson.create(config);
        }
    });

    /**
     * 推入消息
     *
     * @param data
     */
    public static void addQueueTask(String data) {
        RBlockingQueue<String> queue = cachedSupplier.get().getBlockingQueue(Constant.LOG_CHANNEL_TOPIC);
        queue.addAsync(data);
    }

    /**
     * 获取消息
     *
     * @return
     * @throws InterruptedException
     */
    public static String takeQueueTask() throws InterruptedException {
        RBlockingQueue<String> queue = cachedSupplier.get().getBlockingQueue(Constant.LOG_CHANNEL_TOPIC);
        return queue.take();
    }

}
