package cn.wonhigh.serilizer.redis;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.GroupThreads;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * TODO: 增加描述
 * 
 * @author tan.yf
 * @date 2017年5月23日 上午9:08:55
 * @version 0.1.0 
 * @copyright wonhigh.cn
 */
public class JacsonTest extends TestParent{
	private final RedisSerializer serializer = new Jackson2JsonRedisSerializer(Object.class);
	
	private void testserialize(){
		for (int i= 0 ; i < getSerNum() ; i++){
			serializer.serialize(getObj());
		}
	}
	private void testdeserialize(){
		for (int i= 0 ; i < getDesNum() ; i++){
			serializer.deserialize(serializer.serialize(getObj()));
		}
	}
	@Benchmark
//	@GroupThreads(10)
	public void serialize() throws SerializationException {
		testserialize();
	}
	@Benchmark
//	@GroupThreads(10)
	public void deserialize() throws SerializationException {
		testdeserialize();
	}
}
