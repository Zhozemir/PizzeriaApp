package com.example.pizzeria.console;

import com.example.pizzeria.console.controller.OrderController;
import com.example.pizzeria.dto.OrderDTO;
import com.example.pizzeria.enumerators.OrderStatus;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class NotificationService {

    private final OrderController orderCtrl;
    private final Set<Long> seenDelivered = new HashSet<>();

    public NotificationService(OrderController orderCtrl){
        this.orderCtrl = orderCtrl;
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

//                    Optional<List<OrderDTO>> result = orderCtrl.listMyJson();
//
//                    if (result.isEmpty()) {
//                        System.err.println("Неуспешно зареждане на поръчките за текущия потребител.");
//                        continue;
//                    }

                    List<OrderDTO> orders = orderCtrl.listMyJson();
                    //List<OrderDTO> orders = orderCtrl.listMyJson();
                    Set<Long> delivered = new HashSet<>();

                   for(OrderDTO o : orders){

                       if(OrderStatus.DELIVERED.name().equals(o.getStatus()))
                           delivered.add(o.getId());

                   }

                   for(Long id : delivered){

                       if(!seenDelivered.contains(id))
                           System.out.printf("%nПоръчка %d е доставена.", id);

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
