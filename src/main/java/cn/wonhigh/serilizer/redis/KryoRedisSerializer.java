package cn.wonhigh.serilizer.redis;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * redis 序列化  Kryo 实现
 * 
 * @author tan.yf
 * @date 2017年5月17日 上午11:27:14
 * @version 1.4.0 
 * @copyright wonhigh.cn
 */
public class KryoRedisSerializer<T> implements RedisSerializer<T> {

	@Override
	public byte[] serialize(T t) throws SerializationException {
		if (t == null) {
			return new byte[0];
		}
		return KryoUtil.writeObjectToByteArray( t);
	}

	@Override
	public T deserialize(byte[] bytes) throws SerializationException {
		if(bytes == null || bytes.length == 0) {
			return null;
		}
		return KryoUtil.readFromByteArray( bytes);
	}

}
