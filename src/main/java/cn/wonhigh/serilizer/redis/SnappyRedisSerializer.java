package cn.wonhigh.serilizer.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.xerial.snappy.Snappy;

/**
 * Snappy 压缩
 * 
 * @author tan.yf
 * @date 2017年5月17日 下午2:11:18
 * @version 1.4.0 
 * @copyright wonhigh.cn
 */
public class SnappyRedisSerializer<T> implements RedisSerializer<T> {
	private static final Logger logger = LoggerFactory.getLogger(SnappyRedisSerializer.class);
	private RedisSerializer<T> innerSerializer;

	public SnappyRedisSerializer(RedisSerializer<T> innerSerializer) {
		this.innerSerializer = innerSerializer;
	}

	public void destroy(){
		Snappy.cleanUp();
	}
	
	@Override
	public byte[] serialize(T graph) throws SerializationException {

		if (logger.isTraceEnabled())
			logger.trace("SnappyRedisSerializer serialize start ...");

		if (graph == null)
			return new byte[0];

		byte[] bytes = innerSerializer.serialize(graph);
		try {
			byte[] result = Snappy.compress(bytes);

			if (logger.isTraceEnabled())
				logger.trace("SnappyRedisSerializer serialize  压缩前=[{}], 压缩后=[{}]", bytes.length, result.length);
			return result;
		} catch (Exception e) {
			throw new SerializationException("Snappy 序列化异常", e);
		}
	}

	@Override
	public T deserialize(byte[] bytes) throws SerializationException {

		if (logger.isTraceEnabled())
			logger.trace("deserialize start ...");

		if (bytes == null || bytes.length == 0)
			return null;

		try	{
			byte[] bos = Snappy.uncompress(bytes);
			T result = (T) innerSerializer.deserialize(bos);

			if (logger.isTraceEnabled())
				logger.trace("deserialize. result=[{}]", result);

			return result;
		} catch (Exception e) {
			throw new SerializationException("Snappy 反序列化异常！", e);
		}
	}
}
