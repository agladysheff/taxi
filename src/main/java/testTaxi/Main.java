package testTaxi;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        String filePath = "d:/test/";
        List<Sender> senders = new ArrayList<>();
        List<Target> targets = new ArrayList<>();


        /*Создаем  отправителей*/
        for (int i = 0; i < 100; i++) {
            senders.add(new Sender(i));
        }

        /*Создаем  исполнителей*/
        for (int i = 1; i < 11; i++) {
            Target target = new Target(i);
            targets.add(target);
            target.createDir(filePath);
        }

        /*Создаем диспетчера*/
        Dispatcher dispatcher = Dispatcher.getInstance();

        /*Отправители создают и отправляют  каждый в своем потоке сообщения диспетчеру, в сообщение прописывается исполнитель*/
        for (Sender x : senders
                ) {
            Thread thread = new Thread(() -> {
                for (int i = 0; i < 10; i++) {
                    int idTarget = (int) (Math.random() * 10) + 1;
                    Message message = x.createMessage(idTarget, i);
                }
            });
            thread.start();
        }

         /*Диспетчер в отдельном потоке принимает сообщения, присваивает входящий номер,
        передает их исполнителям и делает запись в журнале переданных на выполнение сообщений*/

        Thread threadDispatcher = new Thread(() -> {
            dispatcher.init(targets);
        }
        );


        threadDispatcher.start();

        /*Исполнители, каждый в своем потоке, принимают сообщения от диспетчера,
         сохраняют их в своей папке и сообщают диспетчеру  о выполнении.*/
        for (Target t : targets
                ) {
            Thread thread = new Thread(() -> {
                t.work(filePath);
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


