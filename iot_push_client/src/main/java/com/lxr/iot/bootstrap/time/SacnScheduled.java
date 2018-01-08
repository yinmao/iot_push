package com.lxr.iot.bootstrap.time;

import com.lxr.iot.bootstrap.Producer;
import com.lxr.iot.pool.Scheduled;
import lombok.Data;

import java.util.concurrent.*;

/**
 * 扫描消息确认
 *
 * @author lxr
 * @create 2018-01-08 19:22
 **/
@Data
public class SacnScheduled extends ScanRunnable {

    private Producer producer;

    private  ScheduledFuture<?> submit;

    public SacnScheduled(ConcurrentLinkedQueue queue,Producer producer) {
        super(queue);
        this.producer=producer;
    }

    public  void start(){
        Scheduled  scheduled = new ScheduledPool();
        this.submit = scheduled.submit(this);
    }

    public  void close(){
        if(submit!=null && !submit.isCancelled()){
            submit.cancel(true);
        }
    }


    @Override
    public void doInfo(Object poll) {

    }


    static class ScheduledPool implements Scheduled {
        private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        public ScheduledFuture<?> submit(Runnable runnable){
            return scheduledExecutorService.scheduleAtFixedRate(runnable,10,10, TimeUnit.SECONDS);
        }


    }

}
