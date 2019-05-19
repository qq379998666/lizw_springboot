package lizw.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
public class LizwSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(LizwSpringbootApplication.class, args);
		System.out.println("-----------------------------------------------");
		System.out.println("------------Spring boot start success ---------");
		System.out.println("-----------------------------------------------");
	}
}

/**
 * @SpringBootApplication 是一个三体结构的复合注解: @Configuration @EnableAutoConfiguration @ComponentScan
 * @Configuration 是javaConfig形式的SpringIOC容器的配置类使用的那个 @Configuration,启动类标注了 @Configuration 之后,其实本身也是一个IOC容器的配置类
 * @ComponentScan 的功能是自动扫描并加载符合条件的组件或bean定义,最终将这些bean定义加载到容器中
 * @EnableAutoConfiguration 借助 @import 的帮助,将所有符合自动配置的bean定义加载到IOC容器之中
 *    @EnableScheduling 通过 @Import 将Spring调度框架相关的bean定义加载到IOC容器
 *    @EnableMBeanExport 通过 @Import 将JMX相关的bean定义加载到IOC容器
 *
 * @EnableScheduling 定时任务
 * @EnableAsync 开启并行执行（异步的），如果想串行执行无需开启
 * @EnableCaching 开启缓存
 *
 *
 */


