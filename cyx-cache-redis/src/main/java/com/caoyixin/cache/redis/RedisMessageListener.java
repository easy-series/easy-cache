package com.caoyixin.cache.redis;

import com.caoyixin.cache.notification.CacheEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Redis缓存消息监听器
 */
@Slf4j
public class RedisMessageListener implements MessageListener {

    private final RedisMessageListenerContainer listenerContainer;
    private final RedisTemplate<String, String> redisTemplate;
    private final String topicPrefix;
    private final ObjectMapper objectMapper;
    private final ConcurrentHashMap<String, Boolean> subscribedTopics = new ConcurrentHashMap<>();
    private RedisCacheNotifier cacheNotifier;

    /**
     * 创建Redis消息监听器
     *
     * @param listenerContainer Redis消息监听容器
     * @param redisTemplate     Redis模板
     * @param topicPrefix       主题前缀
     */
    public RedisMessageListener(RedisMessageListenerContainer listenerContainer,
                                RedisTemplate<String, String> redisTemplate,
            String topicPrefix) {
        this.listenerContainer = listenerContainer;
        this.redisTemplate = redisTemplate;
        this.topicPrefix = topicPrefix != null ? topicPrefix : "cyx-cache";
        this.objectMapper = new ObjectMapper();
        log.info("初始化RedisMessageListener, topicPrefix={}", this.topicPrefix);
    }

    /**
     * 设置缓存通知器，用于分发事件
     *
     * @param cacheNotifier 缓存通知器
     */
    public void setCacheNotifier(RedisCacheNotifier cacheNotifier) {
        this.cacheNotifier = cacheNotifier;
    }

    /**
     * 订阅缓存主题
     *
     * @param cacheName 缓存名称
     */
    public void subscribe(String cacheName) {
        String topic = buildTopic(cacheName);
        if (subscribedTopics.putIfAbsent(topic, Boolean.TRUE) == null) {
            listenerContainer.addMessageListener(this, new ChannelTopic(topic));
            log.info("订阅Redis主题: {}", topic);
        }
    }

    /**
     * 取消订阅缓存主题
     *
     * @param cacheName 缓存名称
     */
    public void unsubscribe(String cacheName) {
        String topic = buildTopic(cacheName);
        if (subscribedTopics.remove(topic) != null) {
            listenerContainer.removeMessageListener(this, new ChannelTopic(topic));
            log.info("取消订阅Redis主题: {}", topic);
        }
    }

    @Override
    public void onMessage(org.springframework.data.redis.connection.Message message, byte[] pattern) {
        String body = redisTemplate.getStringSerializer().deserialize(message.getBody());
        if (body == null) {
            return;
        }

        try {
            CacheEvent event = objectMapper.readValue(body, CacheEvent.class);

            log.debug("收到Redis消息: channel={}, eventType={}, cacheName={}, key={}",
                    redisTemplate.getStringSerializer().deserialize(message.getChannel()),
                    event.getEventType(), event.getCacheName(), event.getKey());

            // 分发事件到缓存通知器
            if (cacheNotifier != null) {
                cacheNotifier.dispatchEvent(event);
            }
        } catch (IOException e) {
            log.error("解析Redis消息失败", e);
        } catch (Exception e) {
            log.error("处理Redis消息异常", e);
        }
    }

    /**
     * 构建主题名称
     *
     * @param cacheName 缓存名称
     * @return 主题名称
     */
    private String buildTopic(String cacheName) {
        return topicPrefix + ":topic:" + cacheName;
    }
}