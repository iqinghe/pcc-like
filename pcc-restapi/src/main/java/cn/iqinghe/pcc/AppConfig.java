package cn.iqinghe.pcc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
@MapperScan("cn.iqinghe.pcc.dao")
@EnableCaching(mode = AdviceMode.PROXY, proxyTargetClass = true)
public class AppConfig {
	@Autowired
	private Environment env;

	@Bean
	public JedisPoolConfig jedisPoolConfig() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setBlockWhenExhausted(false);
		jedisPoolConfig.setMaxIdle(Integer.parseInt(env.getProperty("spring.redis.pool.max-idle")));
		jedisPoolConfig.setMinIdle(Integer.parseInt(env.getProperty("spring.redis.pool.min-idle")));
		jedisPoolConfig.setMaxTotal(3000);
		jedisPoolConfig.setMaxWaitMillis(300);
		jedisPoolConfig.setTestOnBorrow(false);
		jedisPoolConfig.setTestOnReturn(false);
		return jedisPoolConfig;
	}

	public RedisClusterConfiguration redisClusterConfiguration() {
		RedisClusterConfiguration clusterConfig = new RedisClusterConfiguration();
		clusterConfig.clusterNode(env.getProperty("spring.redis.host"),
				Integer.parseInt(env.getProperty("spring.redis.port")));
		clusterConfig.setMaxRedirects(10000);
		return clusterConfig;
	}

	@Bean
	public RedisConnectionFactory jedisConnectionFactory() {
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisClusterConfiguration());
		// JedisConnectionFactory jedisConnectionFactory = new
		// JedisConnectionFactory(jedisPoolConfig());
		// jedisConnectionFactory.setHostName(env.getProperty("spring.redis.host"));
		// jedisConnectionFactory.setPort(Integer.parseInt(env.getProperty("spring.redis.port")));
		// jedisConnectionFactory.setTimeout(Integer.parseInt(env.getProperty("spring.redis.timeout")));
		// jedisConnectionFactory.setPassword(env.getProperty("spring.redis.password"));
		return jedisConnectionFactory;
	}

	@Bean
	public RedisTemplate<Object, Object> redisTemplate() {
		RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<Object, Object>();
		redisTemplate.setConnectionFactory(jedisConnectionFactory());
		redisTemplate.setKeySerializer(new GenericJackson2JsonRedisSerializer());
		redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		redisTemplate.setEnableDefaultSerializer(false);
		redisTemplate.setEnableTransactionSupport(true);
		return redisTemplate;
	}

	@Bean
	public CacheManager cacheManager() {
		RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate());
		// Number of seconds before expiration. Defaults to unlimited (0)
		cacheManager.setDefaultExpiration(0);
		cacheManager.setUsePrefix(true);
		return cacheManager;
	}

	// @Bean
	// public EmbeddedServletContainerFactory servletContainer() {
	// TomcatEmbeddedServletContainerFactory tomcat = new
	// TomcatEmbeddedServletContainerFactory();
	// return tomcat;
	// }
}
