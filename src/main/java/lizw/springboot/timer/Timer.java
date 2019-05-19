package lizw.springboot.timer;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author lizhengwei
 * @date 2019年2月11日 下午2:54:55
 * 
 * @Description:定时任务的实现有三种：Java自带的java.util.Timer类，这个类允许你调度一个java.util.TimerTask任务。 最早的时候就是这样写定时任务的。
 * 								开源的第三方框架： Quartz 或者 elastic-job ， 但是这个比较复杂和重量级，适用于分布式场景下的定时任务，可以根据需要多实例部署定时任务。 
 * 				 				使用Spring提供的注解： @Schedule 。 如果定时任务执行时间较短，并且比较单一，可以使用这个注解。
 */
@Component
public class Timer {
	
	@Scheduled(cron = "0/5 * * * * *")
	public void test() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		System.out.println("定时任务已经执行！！！！ "+df.format(new Date()));
	}

}

/*
 * @Scheduled 注解来设置任务的执行时间，并且使用三种属性配置方式：
 * 	fixedRate：定义一个按一定频率执行的定时任务
 * 	fixedDelay：定义一个按一定频率执行的定时任务，与上面不同的是，改属性可以配合initialDelay， 定义该任务延迟执行时间。
 * 	cron：通过表达式来配置任务执行时间
 * 
 * @Scheduled(fixedRate = 5000) :每隔5秒执行一次
 * @Scheduled(cron = "0/5 * * * * *") :每隔5秒执行一次
 * 
 * @Async :并行执行（异步的），如果想串行执行无需添加
 * 
 */


