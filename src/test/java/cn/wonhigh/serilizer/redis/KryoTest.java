package cn.wonhigh.serilizer.redis;

import org.openjdk.jmh.annotations.Benchmark;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * TODO: 增加描述
 * 
 * @author tan.yf
 * @date 2017年5月23日 上午9:08:09
 * @version 0.1.0 
 * @copyright wonhigh.cn
 */
public class KryoTest extends TestParent{
	protected RedisSerializer serializer = new KryoRedisSerializer<>();
	
	@Benchmark
	public void serialize() throws SerializationException {
		for (int i= 0 ; i < getSerNum() ; i++){
			serializer.serialize(getObj());
		}
	}
	@Benchmark
	public void deserialize() throws SerializationException {
		for (int i= 0 ; i < getSerNum() ; i++){
			serializer.deserialize(serializer.serialize(getObj()));
		}
	}
	
}
