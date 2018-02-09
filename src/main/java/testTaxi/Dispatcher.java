package testTaxi;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;


public class Dispatcher {

    private static Dispatcher instance = new Dispatcher();

    private final LinkedBlockingQueue<MessageInt> senderDispatcher = new LinkedBlockingQueue<>();
    private final ConcurrentHashMap<String, String> storage = new ConcurrentHashMap<>();

    private Dispatcher() {
    }

    public static Dispatcher getInstance() {
        return instance;
    }

    public LinkedBlockingQueue<MessageInt> getSenderDispatcher() {
        return senderDispatcher;
    }

    public ConcurrentHashMap<String, String> getStorage() {
        return storage;
    }




    public void handler(MessageInt message1, List<Target> targets) {

        Message message=(Message) message1;
        int idMessage = message.getId();
        String xmlString = message.getXmlString();
        String strUpd = CreateXml.updateXmlString(xmlString, idMessage);
        String idTarget = CreateXml.getTargetString(strUpd, "target");
        Target target = targets.get(Integer.parseInt(idTarget) - 1);
        target.getDispatcherTarget().add(strUpd);
        storage.put(String.valueOf(idMessage), "передано на исполнение");

    }


    public boolean isEmpty() {
        return getSenderDispatcher().isEmpty();
    }



    public void init(List<Target> targets) {
        Boolean stop = false;
        while (!stop) {

            MessageInt message = null;
            try {
                message = senderDispatcher.take();
            } catch (InterruptedException e) {
                ;
            }
            if (message instanceof Pill) stop = true;
            else handler(message, targets);
        }


    }

    public void initTargets(List<Thread>threadsTargets){

        for (Thread threadTarget : threadsTargets
                ) {
            threadTarget.start();
        }

    }

    public void adMessageFromSender(MessageInt message){
        getSenderDispatcher().add(message);
    }

    public void serviceStop() {
        Boolean flag = false;
        while (!flag) {
            try {
                Thread.sleep(5000);
                System.out.println(getStorage().isEmpty());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (getStorage().isEmpty()) {
                Dispatcher.getInstance().adMessageFromSender(new Pill());

                flag = true;

            }
        }

    }

    public  void removeLogBook(String idDisp){
        getStorage().remove(idDisp);
    }



}