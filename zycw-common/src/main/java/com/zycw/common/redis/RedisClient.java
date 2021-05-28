package com.zycw.common.redis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.exceptions.JedisException;
import redis.clients.jedis.JedisSentinelPool;
/**
 * <p>
 * Title: Redis方法封装.
 * </p>
 * <p>
 * Description: 需要以Redis哨兵模式部署及启动.
 * </p>
 * @author   yangjunxu
 * @version  1.0
 * @since    
 * @Date	 2018年4月4日  上午12:19:56	
 */
public class RedisClient {
    private static JedisPool jedisPool;//非切片连接池
    //private static JedisSentinelPool jedisSentinelPool;//非切片连接池
    private static String redistype;
 
    /** 
     * 初始化
     */ 
    private static JedisPool initialShardedPool() 
    { 
		Properties propsRedis = new Properties();
		try {
			propsRedis.load(RedisClient.class.getClassLoader().getResourceAsStream("redisConfig.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		redistype = propsRedis.getProperty("redis.type");
		if(redistype!=null&&"sentinel".equals(redistype)){
	        /*
		        String redisSentinel1=propsRedis.getProperty("redisSentinel1.ip");
		        String redisSentinel2=propsRedis.getProperty("redisSentinel2.ip");
		
		        Set<String> sentinels = new HashSet<String>(); 
		        sentinels.add(redisSentinel1);  
		        sentinels.add(redisSentinel2);  
		    	String mastername = String.valueOf(propsRedis.getProperty("redis.mastername").trim());
		    	String password = String.valueOf(propsRedis.getProperty("redis.password").trim());
		    	int maxid=Integer.valueOf(propsRedis.getProperty("redis.pool.maxIdle").trim());
		    	int maxtotal=Integer.valueOf(propsRedis.getProperty("redis.pool.maxTotal").trim());
		    	long maxWaitMillis=Long.valueOf(propsRedis.getProperty("redis.pool.maxWaitMillis").trim());
		    	boolean testOnBorrow=Boolean.valueOf(propsRedis.getProperty("redis.pool.testOnReturn").trim());
		    	int timeOut = Integer.valueOf(propsRedis.getProperty("redis.pool.timeOut").trim());
		        JedisPoolConfig config = new JedisPoolConfig(); 
		        config.setMaxTotal(maxtotal);
		        config.setMaxIdle(maxid); 
				config.setMaxWaitMillis(maxWaitMillis);
				config.setTestOnBorrow(testOnBorrow); 
		        jedisSentinelPool =new JedisSentinelPool(mastername, sentinels, config, password);
		        return jedisSentinelPool;
	        */
			return null;
		}else{
	        String ip=propsRedis.getProperty("redis.ip");
	        String redisport = propsRedis.getProperty("redis.port");
	        int port=0;
	        if(redisport!=null&&!"".equals(redisport)){
	        	port=Integer.parseInt(redisport);
	        }
			String password = String.valueOf(propsRedis.getProperty("redis.password").trim());
	    	int maxid=Integer.valueOf(propsRedis.getProperty("redis.pool.maxIdle").trim());
	    	int maxtotal=Integer.valueOf(propsRedis.getProperty("redis.pool.maxTotal").trim());
	    	long maxWaitMillis=Long.valueOf(propsRedis.getProperty("redis.pool.maxWaitMillis").trim());
	    	boolean testOnBorrow=Boolean.valueOf(propsRedis.getProperty("redis.pool.testOnReturn").trim());
	    	int timeOut = Integer.valueOf(propsRedis.getProperty("redis.pool.timeOut").trim());
	        JedisPoolConfig config = new JedisPoolConfig(); 
	        config.setMaxTotal(maxtotal);
	        config.setMaxIdle(maxid); 
			config.setMaxWaitMillis(maxWaitMillis);
			config.setTestOnBorrow(testOnBorrow); 
			jedisPool = new JedisPool(config, ip, port, 1000, password);
	        return jedisPool;
		}
    } 

    public static void returnResource(JedisPool shardedJedisPool, Jedis jedis) {
        if (jedis != null) {
        	//shardedJedis.close();
        	shardedJedisPool.returnResource(jedis);
        	jedis.quit();
        	jedis=null;
        }
    }

    /**
     * 获取ShardedJedis
     * 
     * @param key
     * @return
     */
    public static Jedis getJedis(){
    	if(jedisPool==null)initialShardedPool(); 
    	return jedisPool.getResource();
    }

    /**
     * 获取数据
     * 
     * @param key
     * @return
     */
    public static void put(String key,String value){
        
    	Jedis jedis = null;
        try {
        	jedis = getJedis();
            jedis.set(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            returnResource(jedisPool, jedis);
            
            jedis=null;
        }
        
    }
    /**
     * 获取数据
     * 
     * @param key
     * @return
     */
    public static boolean exists(String key){
    	boolean value = false;
        
        Jedis jedis = null;
        try {
        	jedis = getJedis();
            value = jedis.exists(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            returnResource(jedisPool, jedis);
            jedis=null;
        }
        
        return value;
    }
    /**
     * 获取数据
     * 
     * @param key
     * @return
     */
    public static String get(String key){
        String value = null;
        
        Jedis jedis = null;
        try {
        	jedis = getJedis();
            value = jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            returnResource(jedisPool, jedis);
            jedis=null;
        }
        
        return value;
    }
    /**
     * 删除数据
     * 
     * @param key
     * @return
     */
    public static void del(String key){
        String value = null;
        
        Jedis jedis = null;
        try {
        	jedis = getJedis();
        	jedis.del(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            returnResource(jedisPool, jedis);
            jedis=null;
        }
        
    }
    /**
     * 获取锁
     * 
     * @param key
     * @return
     */
    public static Long Setnx(String lockKey,String identifier){
    	Long value = 0l;
        
        Jedis jedis = null;
        try {
        	jedis = getJedis();
        	value = jedis.setnx(lockKey, identifier);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            returnResource(jedisPool, jedis);
            jedis=null;
        }
        return value;
    }

    /**
     * 设置超时
     * 
     * @param key
     * @return
     */
    public static Long Expire(String lockKey,int lockExpire){
       long value=0l;
        Jedis jedis = null;
        try {
        	jedis = getJedis();
        	value=jedis.expire(lockKey, lockExpire);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            returnResource(jedisPool, jedis);
            jedis=null;
        }
        return value;
    }

    /**
     * 设置超时
     * 
     * @param key
     * @return
     */
    public static Long TTL(String lockKey){
       long value=0l;
        Jedis jedis = null;
        try {
        	jedis = getJedis();
        	value=jedis.ttl(lockKey);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            returnResource(jedisPool, jedis);
            jedis=null;
        }
        return value;
    }

    /**
     * 设置超时
     * 
     * @param key
     * @return
     */
    public static void Watch(String lockKey){
        Jedis jedis = null;
        try {
        	jedis = getJedis();
        	jedis.watch(lockKey);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            returnResource(jedisPool, jedis);
            jedis=null;
        }
    }

    /**
     * 设置超时
     * 
     * @param key
     * @return
     */
    public static void Unwatch(){
        Jedis jedis = null;
        try {
        	jedis = getJedis();
        	jedis.unwatch();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            returnResource(jedisPool, jedis);
            jedis=null;
        }
    }
    

    /**
     * 设置超时
     * 
     * @param key
     * @return
     */
    public static Transaction Multi(){
    	Transaction transaction=null;;
        Jedis jedis = null;
        try {
        	jedis = getJedis();
        	transaction=jedis.multi();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            returnResource(jedisPool, jedis);
            jedis=null;
        }
        return transaction;
    }
    

   
    

	/**
	 * 给数据库每一条数据存到数据库  其中key暂且定为 k_Id(兑换券的ID主键)
	 * @param key
	 * @param field
	 * @param value
	 */
	public static void setValue(String key ,String field,String value){
		 Jedis jedis = null;
	        try {
	        	jedis = getJedis();
	        	if(key!=null&&!"".equals(key)&&value!=null)
				jedis.hset(key, field, value);
			}catch(Exception e){
				e.printStackTrace();
			}finally {
	            //返还到连接池
	            returnResource(jedisPool, jedis);
	            jedis=null;
	        }
	}
	
	/**
	 * 给数据库每一条数据存到数据库 
	 * @param key
	 * @param value
	 */
	public static void setValueMsg(String key ,String value){
		 Jedis jedis = null;
	        try {
	        	jedis = getJedis();
	        	if(key!=null&&!"".equals(key)&&value!=null)
				jedis.set(key, value);
			}catch(Exception e){
				e.printStackTrace();
			}finally {
	            //返还到连接池
	            returnResource(jedisPool, jedis);
	            jedis=null;
	        }
	}
	/**
	 * 给数据库每一条数据存到数据库 
	 * @param key
	 * @param value
	 */
	public static String getValueMsg(String key){
			String result=null;
			Jedis jedis = null;
	        try {
	        	jedis = getJedis();
				result=jedis.get(key);
				return result;
			}catch(Exception e){
				e.printStackTrace();
			}finally {
	            //返还到连接池
	            returnResource(jedisPool, jedis);
	            jedis=null;
	        }
			return result;
		}
	

	/**
	 * 给数据库每一条数据存到数据库  其中key暂且定为 k_Id(兑换券的ID主键)
	 * @param key
	 * @param field
	 * @param value
	 */
	public static void Hdel(String key ,String field){
		 Jedis jedis = null;
	        try {
	        	jedis = getJedis();
				if(key!=null&&!"".equals(key))
					jedis.hdel(key, field);
			}catch(Exception e){
				e.printStackTrace();
			}finally {
	            //返还到连接池
	            returnResource(jedisPool, jedis);
	            jedis=null;
	        }
	}
	/**
	 * 给数据库每一条数据存到数据库  其中key暂且定为 k_Id(兑换券的ID主键)
	 * @param key
	 * @param field
	 * @param value
	 */
	public static String getValue(String key ,String field){
		String result=null;
		Jedis jedis = null;
        try {
        	jedis = getJedis();
			result=jedis.hget(key, field);
			return result;
		}catch(Exception e){
			e.printStackTrace();
		}finally {
            //返还到连接池
            returnResource(jedisPool, jedis);
            jedis=null;
        }
		return result;
	}

	/**
	 * 给数据库每一条数据存到数据库  其中key暂且定为 k_Id(兑换券的ID主键)
	 * @param key
	 * @param field
	 * @param value
	 */
	public static Map<String, String> getAllValue(String key){
		Map<String, String> result=null;
		Jedis jedis = null;
        try {
        	jedis = getJedis();
			result=jedis.hgetAll(key);
		}catch(Exception e){
			e.printStackTrace();
		}finally {
            //返还到连接池
            returnResource(jedisPool, jedis);
            jedis=null;
        }
		return result;
	}
    

	/**
	 * 给数据库每一条数据存到数据库  其中key暂且定为 k_Id(兑换券的ID主键)
	 * @param key
	 * @param field
	 * @param value
	 */
	public static void HincrBy(String key ,String field,long value){
		 Jedis jedis = null;
	        try {
	        	jedis = getJedis();
				if(key!=null&&!"".equals(key))
					jedis.hincrBy(key, field ,value);
			}catch(Exception e){
				e.printStackTrace();
			}finally {
	            //返还到连接池
	            returnResource(jedisPool, jedis);
	            jedis=null;
	        }
	}

	
	
	
	
	
	/**
	 * 向redis中存入兑换券的id和兑换券的数量(也是在有效期内的，根据系统的当前时间来进行判断)
	 * message:1 表示返回成功 0 表示返回失败
	 */
	public static String setVoucherIdAndNumToRedis( String id, int num) {

    	
		String message = "";
		// 加锁
		String value = lockWithTimeout("lock", 20000, 10000);
		if (value == null) {
			return "抢券超时，请重新尝试";
		}
		try {
			String number = getValue("activitynum", id);
			System.out.println("活动ID:"+id+" number:" + number);
			if (number != null && !"".equals(number)) {
				Integer redisActivitTotalNum = Integer.parseInt(number);
				// 数据库中的券总数 >=  Redis缓存的券的剩余数量,Redis缓存的券的剩余数量 > 0
				if (null != redisActivitTotalNum && num >= redisActivitTotalNum && redisActivitTotalNum > 0) {
					//更新Redis数据中剩余券的数量 -1
					HincrBy("activitynum", id, -1);
					message = "1";
				} else {
					message = "0";
				}
			} else {
				System.out.println("活动ID不存在 或者 活动已经超时");
				message = "0";
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "0";
		} finally {
			// 释放锁;成不成功都得释放锁
			releaseLock(id, value);
		}
		return message;

	}
	
	
	
	/**
	 * 加锁的时候要和时间戳结合在一起
	 * 锁到了快释放的时候就让它失效

	/**
	 * 加锁
	 * 
	 * @param locaName
	 *            锁的key
	 * @param acquireTimeout
	 *            获取超时时间
	 * @param timeout
	 *            锁的超时时间
	 * @return 锁标识
	 */
	public static String lockWithTimeout(String locaName, long acquireTimeout, long timeout) {

		Jedis jedis = null;
		String retIdentifier = null;
        try {
        	jedis = getJedis();
			// 随机生成一个value
			String identifier = UUID.randomUUID().toString();
			// 锁名，即key值
			String lockKey = "lock:" + locaName;
			// 超时时间，上锁后超过此时间则自动释放锁
			int lockExpire = (int) (timeout / 1000);

			// 获取锁的超时时间，超过这个时间则放弃获取锁
			long end = System.currentTimeMillis() + acquireTimeout;
			System.out.println("action...........");
			while (System.currentTimeMillis() < end) {
				if (jedis.setnx(lockKey, identifier) == 1) {
					System.out.println(jedis.expire(lockKey, lockExpire)+"expire......");
					// 返回value值，用于释放锁时间确认
					retIdentifier = identifier;
					return retIdentifier;
				}
				// 返回-1代表key没有设置超时时间，为key设置一个超时时间
				if (jedis.ttl(lockKey) == -1) {
					System.out.println(jedis.expire(lockKey, lockExpire)+"expire222......");
				}

				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					
				}
			}
			System.out.println("end...........");
		} catch (JedisException e) {
			e.printStackTrace();
		} finally {
            returnResource(jedisPool, jedis);
            jedis=null;
		}
		return retIdentifier;
	}

	/**
	 * 释放锁
	 * 
	 * @param lockName
	 *            锁的key
	 * @param identifier
	 *            释放锁的标识
	 * @return
	 */
	public static boolean releaseLock(String lockName, String identifier) {
		Jedis jedis = null;
		String lockKey = "lock:" + lockName;
		boolean retFlag = false;
        try {
        	jedis = getJedis();
			//获取资源，此时的jedisPool只是起获取资源的作用
			while (true) {
				// 监视lock，准备开始事务
				jedis.watch(lockKey);
				
				/**
				 *  通过前面返回的value值判断是不是该锁，若是该锁，则删除，释放锁，其实就是根据key值来进行的判断
				 */
				if (identifier.equals(jedis.get(lockKey))) {
					//
					Transaction transaction = jedis.multi();
					//若是该锁，则要删除该锁，释放锁
					transaction.del(lockKey);
					List<Object> results = transaction.exec();
					if (results == null) {
						continue;
					}
					retFlag = true;
				}
				jedis.unwatch();
				break;
			}
		} catch (JedisException e) {
			e.printStackTrace();
		} finally {
            returnResource(jedisPool, jedis);
            jedis=null;
		}
		return retFlag;
	}
	
	
	
	
    public static void main(String[] args) {
    	RedisClient.put("yang", "yangjunxu");
    	System.out.println(RedisClient.get("yang"));
    	RedisClient.put("yang", "yangjunxu2");
    	System.out.println(RedisClient.get("yang"));
    	RedisClient.put("yang", "yangjunxu3");
    	System.out.println(RedisClient.get("yang"));
    	RedisClient.setVoucherIdAndNumToRedis("aaaa",1);
    }
    
    
}