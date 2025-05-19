package com.example.pizzeria.services;

import com.example.pizzeria.models.Order;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayedOrder implements Delayed {

    //private final Order order;
    private final Long orderId;
    private final long triggerTime;

    public DelayedOrder(Long orderId, long delay, TimeUnit unit){

        this.orderId = orderId;
        this.triggerTime = System.currentTimeMillis() + unit.toMillis(delay);

    }

    public Long getOrderId(){
        return orderId;
    }

    @Override
    public long getDelay(TimeUnit unit){
        return unit.convert(triggerTime - System.currentTimeMillis(), TimeUnit.MICROSECONDS);
    }

    @Override
    public int compareTo(Delayed other){

        if(other == this)
            return 0;
        long diff = this.triggerTime - ((DelayedOrder) other).triggerTime;
        return Long.compare(diff, 0L);

    }

}
