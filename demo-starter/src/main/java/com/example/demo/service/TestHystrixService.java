package com.example.demo.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.umpay.online.tools.exception.OnlineException;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;

/**
 * @author tianxiaoyang
 * @date 2021-11-30 14:16
 * @describe
 */
@Service
public class TestHystrixService {
    /**
     * 参数参考：https://www.jianshu.com/p/39763a0bd9b8，https://blog.csdn.net/tongtong_use/article/details/78611225
     *
     * execution.isolation.strategy：
     * 隔离策略
     * THREAD —— 在固定大小线程池中，以单独线程执行，并发请求数受限于线程池大小。
     * SEMAPHORE —— 在调用线程中执行，通过信号量来限制并发量。
     *
     * execution.isolation.semaphore.maxConcurrentRequests：
     * 设置当使用ExecutionIsolationStrategy.SEMAPHORE时，HystrixCommand.run()方法允许的最大请求数。如果达到最大并发数时，后续请求会被拒绝。
     *
     * circuitBreaker.requestVolumeThreshold：
     * 设置在一个滚动窗口中，打开断路器的最少请求数。
     * 比如：如果值是20，在一个窗口内（比如10秒），收到19个请求，即使这19个请求都失败了，断路器也不会打开。
     *
     * circuitBreaker.sleepWindowInMilliseconds
     * 设置在回路被打开，拒绝请求到再次尝试请求并决定回路是否继续打开的时间。默认值：5000（毫秒）
     *
     * circuitBreaker.errorThresholdPercentage
     * 设置打开回路并启动回退逻辑的错误比率。 默认值：50
     *
     * metrics.rollingStats.timeInMilliseconds
     * 设置统计的滚动窗口的时间段大小。该属性是线程池保持指标时间长短。 默认值：10000（毫秒）
     *
     * metrics.rollingStats.numBuckets
     * 设置滚动的统计窗口被分成的桶（bucket）的数目。
     *
     * @param key
     * @return
     */
    @HystrixCommand(commandKey = "#key",groupKey = "#key",fallbackMethod = "fail"
            ,commandProperties = {
            @HystrixProperty(name = "execution.isolation.strategy",value = "SEMAPHORE")
            ,@HystrixProperty(name = "execution.isolation.semaphore.maxConcurrentRequests",value = "1")
            ,@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "2")
            ,@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "50000")
            ,@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "1")
            ,@HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds",value = "10000")
            ,@HystrixProperty(name = "metrics.rollingStats.numBuckets",value = "10")
            ,@HystrixProperty(name = "requestCache.enabled",value = "false")
    },ignoreExceptions = {OnlineException.class})
    public String testHystrix(String key){
        if (RandomUtils.nextBoolean()) {
            System.out.println(1 / 0);
//            OnlineException.cast("123","ERROR");
        }
        return key;
    }
    public String fail(String key){
        return key+ "走了熔断逻辑";
    }
}
