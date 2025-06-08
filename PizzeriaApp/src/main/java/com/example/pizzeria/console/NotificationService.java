package com.example.pizzeria.console;

import com.example.pizzeria.console.controller.OrderController;
import com.example.pizzeria.console.notifications.OrderDeliveredListener;
import com.example.pizzeria.dto.OrderDTO;
import com.example.pizzeria.enumerators.OrderStatus;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

public class NotificationService {

    private final OrderController orderCtrl;
    private final Set<Long> seenDelivered = new HashSet<>();
    private final List<OrderDeliveredListener> listeners = new CopyOnWriteArrayList<>();

    public NotificationService(OrderController orderCtrl){
        this.orderCtrl = orderCtrl;
    }

    public void addListener(OrderDeliveredListener listener){
        listeners.add(listener);
    }

    public void removeListener(OrderDeliveredListener listener){
        listeners.remove(listener);
    }

    private void notifyListeners(long orderId){

        for(OrderDeliveredListener listener : listeners){
            listener.onOrderDelivered(orderId);
        }

    }

    // стартира се нишка, която на всеки 10 секунди проверява доставените поръчки
    public void start(){

        Thread t = new Thread(() ->{

            while(!Thread.currentThread().isInterrupted()){

                try{

                    Thread.sleep(10_000);

                    if (ConsoleSession.getCurrentUser() == null) {
                        continue;
                    }

                    List<OrderDTO> orders = orderCtrl.getMyOrders();
                    Set<Long> delivered = new HashSet<>();

                   for(OrderDTO o : orders){

                       if(OrderStatus.DELIVERED.name().equals(o.getStatus()))
                           delivered.add(o.getId());

                   }

                   for(Long id : delivered){

                       if(!seenDelivered.contains(id))
                           notifyListeners(id);

                   }

                   seenDelivered.clear();
                   seenDelivered.addAll(delivered);

                } catch (InterruptedException e){
                    Thread.currentThread().interrupt();
                } catch (Exception ex){
                    System.out.println("Грешка при NotificationsService: " + ex.getMessage());
                    ex.printStackTrace();
                }

            }

        }, "OrderNotifier");
        t.setDaemon(true);
        t.start();

    }

}
