package cn.wonhigh.serilizer.redis;

import org.nustaq.serialization.FSTConfiguration;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * redis 序列化  FST  实现
 * 
 * @author tan.yf
 * @date 2017年5月17日 上午11:27:14
 * @version 1.4.0 
 * @copyright wonhigh.cn
 */
public class FstRedisSerializer<T> implements RedisSerializer<T> {
	FSTConfiguration configuration = FSTConfiguration
			// .createDefaultConfiguration();
					.createStructConfiguration();
    
	@Override
	public byte[] serialize(T t) throws SerializationException {
		if (t == null) {
			return new byte[0];
		}
		byte[] data = configuration.asByteArray(t);
		return data;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T deserialize(byte[] bytes) throws SerializationException {
		if(bytes == null || bytes.length == 0) {
			return null;
		}
		return (T) configuration.asObject(bytes);
	}

}
