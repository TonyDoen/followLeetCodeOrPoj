package example.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * tools for retrieving redis client 
 * @author zhuxinze
 *
 */
public class RedisClient {
	
	/**
	 * nested class，avoid simultaneous invoking
	 *
	 */
	private static class JedisPoolHolder{
		private static final JedisPool jedisPool;
		static{
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxActive(Integer.valueOf(CommonPropertiesUtil.get("redis.maxActive")));
			config.setMaxIdle(Integer.valueOf(CommonPropertiesUtil.get("redis.maxIdle")));
			config.setMaxWait(Integer.valueOf(CommonPropertiesUtil.get("redis.maxWait")));
			config.setTestOnBorrow(Boolean.valueOf(CommonPropertiesUtil.get("redis.testOnBorrow")));
			jedisPool = new JedisPool(config,CommonPropertiesUtil.get("redis.host"),
					Integer.valueOf(CommonPropertiesUtil.get("redis.port")),
					Integer.valueOf(CommonPropertiesUtil.get("redis.timeout")),
					CommonPropertiesUtil.get("redis.password"));
		}
	}
	
	/**
	 * get jedis instance from jedis pool
	 * @return
	 */
	public static Jedis getJedis(){
		return JedisPoolHolder.jedisPool.getResource();
	}
	
	/**
	 * release jedis instance back into the pool
	 * @param jedis
	 */
	public static void returnJedis(Jedis jedis){
		JedisPoolHolder.jedisPool.returnResource(jedis);
	}
}
