package cn.wonhigh.serilizer.redis;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * 
 * @author tan.yf
 * @date 2017年5月23日 上午9:07:20
 * @version 0.1.0 
 * @copyright wonhigh.cn
 */
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Fork(1)
@Warmup(iterations = 2)
@Measurement(iterations = 5)
@BenchmarkMode(Mode.SingleShotTime)
public class TestParent {

	@Setup
	public void init() {
		//
	}

	@TearDown
	public void destory() {
		// destory 
	}

	private static UserInfo getUserInfo(int i) {
		UserInfo userInfo = new UserInfo();
		userInfo.setId(i);
		userInfo.setAccount("admin"+i);
		userInfo.setName("admin"+i);
		userInfo.setCreateTime(new Date());
		return userInfo;
	}
	
	protected Object getObj() {
		List<UserInfo> uList = new ArrayList<>(1);
		for (int i = 0; i < 5; i++) {
			uList.add(getUserInfo(i));
		}
//		return getUserInfo();
		return uList;
	}
	
	// 序列化次数
	protected int getSerNum() {
		return 100000;
	}

	// 反序列化次数
	protected int getDesNum() {
		return 100000;
	}
	
	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder()
        .include("cn.wonhigh.serilizer.redis.*")
        .build();
		new Runner(opt).run();
	}
}
