package testTaxi;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        String filePath = "d:/test/";
        List<Sender> senders = new ArrayList<>();


        /*Создаем  отправителей*/
        for (int i = 0; i < 100; i++) {
            senders.add(new Sender(i));
        }


        /*Создаем диспетчера*/
        Dispatcher dispatcher = Dispatcher.getInstance();



         /*Диспетчер в отдельном потоке создает исполнителей. Запускает их каждого в отдельном потоке.
        Принимает сообщения от отправителя, присваивает входящий номер,
        передает их исполнителям и делает запись в журнале переданных на выполнение сообщений
        Исполнители, принимают сообщения от диспетчера,
        сохраняют их в своей папке и сообщают диспетчеру  о выполнении.*/


        Thread threadDispatcher = new Thread(() -> {
            dispatcher.initTargets(filePath);
            dispatcher.init();

        }
        );


        threadDispatcher.start();

        /*Отправители создают и отправляют  каждый в своем потоке сообщения диспетчеру, в сообщение прописывается исполнитель*/
        for (Sender x : senders) {
            Thread thread = new Thread(() -> {
                for (int i = 0; i < 10; i++) {
                    int idTarget = (int) (Math.random() * 10) + 1;
                    Message message = x.createMessage(idTarget, i);
                }
            });
            thread.start();
        }



        /*Запускаем сервис по остановке диспетчера,  5 сек после окончания выполнения задач исполнителями*/
        Thread threadStop = new Thread(() -> {
            dispatcher.serviceStop();

        });
        threadStop.start();

    }


}


