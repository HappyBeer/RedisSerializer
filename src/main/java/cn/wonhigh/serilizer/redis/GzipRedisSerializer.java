package cn.wonhigh.serilizer.redis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * Gzip 压缩
 * 
 * @author tan.yf
 * @date 2017年5月17日 下午2:11:18
 * @version 1.4.0 
 * @copyright wonhigh.cn
 */
public class GzipRedisSerializer<T> implements RedisSerializer<T> {
	private static final Logger logger = LoggerFactory.getLogger(GzipRedisSerializer.class);
	private RedisSerializer<T> innerSerializer;

	public GzipRedisSerializer(RedisSerializer<T> innerSerializer) {
		this.innerSerializer = innerSerializer;
	}

	public static final int BUFFER_SIZE = 4096;

	@Override
	public byte[] serialize(T graph) throws SerializationException {

		if (logger.isTraceEnabled())
			logger.trace("GzipRedisSerializer serialize start ...");

		if (graph == null)
			return new byte[0];

		byte[] bytes = innerSerializer.serialize(graph);
		try (ByteArrayOutputStream bos = new ByteArrayOutputStream(); GZIPOutputStream gzip = new GZIPOutputStream(bos);) {
			gzip.write(bytes);
			gzip.close();
			byte[] result = bos.toByteArray();

			if (logger.isTraceEnabled())
				logger.trace("GzipRedisSerializer serialize  压缩前=[{}], 压缩后=[{}]", bytes.length, result.length);
			return result;
		} catch (Exception e) {
			throw new SerializationException("Gzip 序列化异常", e);
		}
	}

	@Override
	public T deserialize(byte[] bytes) throws SerializationException {

		if (logger.isTraceEnabled())
			logger.trace("deserialize start ...");

		if (bytes == null || bytes.length == 0)
			return null;

		try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
				ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
				GZIPInputStream gzip = new GZIPInputStream(bis);) {

			byte[] buff = new byte[BUFFER_SIZE];
			int n;
			while ((n = gzip.read(buff, 0, BUFFER_SIZE)) > 0) {
				bos.write(buff, 0, n);
			}
			T result = (T) innerSerializer.deserialize(bos.toByteArray());

			if (logger.isTraceEnabled())
				logger.trace("deserialize. result=[{}]", result);

			return result;
		} catch (Exception e) {
			throw new SerializationException("Gzip 反序列化异常！", e);
		}
	}
}
