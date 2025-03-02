package com.easy.cache.spring;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Easy Cache 配置属性
 */
@ConfigurationProperties(prefix = "easy.cache")
public class CacheProperties {

    /**
     * 是否启用缓存
     */
    private boolean enabled = true;

    /**
     * 本地缓存配置
     */
    private Local local = new Local();

    /**
     * Redis缓存配置
     */
    private Redis redis = new Redis();

    /**
     * 多级缓存配置
     */
    private MultiLevel multiLevel = new MultiLevel();

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Local getLocal() {
        return local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }

    public Redis getRedis() {
        return redis;
    }

    public void setRedis(Redis redis) {
        this.redis = redis;
    }

    public MultiLevel getMultiLevel() {
        return multiLevel;
    }

    public void setMultiLevel(MultiLevel multiLevel) {
        this.multiLevel = multiLevel;
    }

    /**
     * 本地缓存配置
     */
    public static class Local {
        /**
         * 是否启用本地缓存
         */
        private boolean enabled = true;

        /**
         * 最大缓存条目数
         */
        private int maximumSize = 10000;

        /**
         * 初始容量
         */
        private int initialCapacity = 100;

        /**
         * 写入后过期时间
         */
        private long expireAfterWrite = 30;

        /**
         * 时间单位
         */
        private TimeUnit timeUnit = TimeUnit.MINUTES;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public int getMaximumSize() {
            return maximumSize;
        }

        public void setMaximumSize(int maximumSize) {
            this.maximumSize = maximumSize;
        }

        public int getInitialCapacity() {
            return initialCapacity;
        }

        public void setInitialCapacity(int initialCapacity) {
            this.initialCapacity = initialCapacity;
        }

        public long getExpireAfterWrite() {
            return expireAfterWrite;
        }

        public void setExpireAfterWrite(long expireAfterWrite) {
            this.expireAfterWrite = expireAfterWrite;
        }

        public TimeUnit getTimeUnit() {
            return timeUnit;
        }

        public void setTimeUnit(TimeUnit timeUnit) {
            this.timeUnit = timeUnit;
        }
    }

    /**
     * Redis缓存配置
     */
    public static class Redis {
        /**
         * 是否启用Redis缓存
         */
        private boolean enabled = true;

        /**
         * Redis主机
         */
        private String host = "localhost";

        /**
         * Redis端口
         */
        private int port = 6379;

        /**
         * Redis密码
         */
        private String password = null;

        /**
         * Redis数据库
         */
        private int database = 0;

        /**
         * 连接超时时间（毫秒）
         */
        private int timeout = 2000;

        /**
         * 最大连接数
         */
        private int maxTotal = 8;

        /**
         * 最大空闲连接数
         */
        private int maxIdle = 8;

        /**
         * 最小空闲连接数
         */
        private int minIdle = 0;

        /**
         * 序列化器类型（JDK或JSON）
         */
        private String serializer = "JSON";

        /**
         * 写入后过期时间
         */
        private long expireAfterWrite = 1;

        /**
         * 时间单位
         */
        private TimeUnit timeUnit = TimeUnit.HOURS;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public int getDatabase() {
            return database;
        }

        public void setDatabase(int database) {
            this.database = database;
        }

        public int getTimeout() {
            return timeout;
        }

        public void setTimeout(int timeout) {
            this.timeout = timeout;
        }

        public int getMaxTotal() {
            return maxTotal;
        }

        public void setMaxTotal(int maxTotal) {
            this.maxTotal = maxTotal;
        }

        public int getMaxIdle() {
            return maxIdle;
        }

        public void setMaxIdle(int maxIdle) {
            this.maxIdle = maxIdle;
        }

        public int getMinIdle() {
            return minIdle;
        }

        public void setMinIdle(int minIdle) {
            this.minIdle = minIdle;
        }

        public String getSerializer() {
            return serializer;
        }

        public void setSerializer(String serializer) {
            this.serializer = serializer;
        }

        public long getExpireAfterWrite() {
            return expireAfterWrite;
        }

        public void setExpireAfterWrite(long expireAfterWrite) {
            this.expireAfterWrite = expireAfterWrite;
        }

        public TimeUnit getTimeUnit() {
            return timeUnit;
        }

        public void setTimeUnit(TimeUnit timeUnit) {
            this.timeUnit = timeUnit;
        }
    }

    /**
     * 多级缓存配置
     */
    public static class MultiLevel {
        /**
         * 是否启用多级缓存
         */
        private boolean enabled = true;

        /**
         * 是否启用写透模式
         */
        private boolean writeThrough = true;

        /**
         * 是否启用异步写入
         */
        private boolean asyncWrite = false;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public boolean isWriteThrough() {
            return writeThrough;
        }

        public void setWriteThrough(boolean writeThrough) {
            this.writeThrough = writeThrough;
        }

        public boolean isAsyncWrite() {
            return asyncWrite;
        }

        public void setAsyncWrite(boolean asyncWrite) {
            this.asyncWrite = asyncWrite;
        }
    }
}