package cn.wonhigh.serilizer.redis;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

/**
 * redis 序列化  Kryo 实现
 * 
 * @author tan.yf
 * @date 2017年5月17日 上午11:27:14
 * @version 1.4.0 
 * @copyright wonhigh.cn
 */
public class KryoRedisSerializer<T> implements RedisSerializer<T> {
	private Kryo kryo = new Kryo();
	
	public static final int BUFFER_SIZE = 4096;
	@Override
	public byte[] serialize(T t) throws SerializationException {
		if (t == null) {
			return new byte[0];
		}
		byte[] buffer = new byte[BUFFER_SIZE];
		Output output = new Output(buffer,-1);
		kryo.writeClassAndObject(output, t);
		return output.toBytes();
	}

	@SuppressWarnings("unchecked")
	@Override
	public T deserialize(byte[] bytes) throws SerializationException {
		if(bytes == null || bytes.length == 0) {
			return null;
		}
		Input input = new Input(bytes);
		T t = (T)kryo.readClassAndObject(input);
		return t;
	}

}
