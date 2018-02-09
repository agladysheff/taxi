package testTaxi;

import java.io.File;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;


public class Target {
    private final Queue<String> dispatcherTarget = new ConcurrentLinkedQueue<>();
    private int id;
    private final String filePath = "d:/test/";

    public Target(int id) {
        this.id = id;
    }

    public Queue<String> getDispatcherTarget() {
        return dispatcherTarget;
    }

    public int getId() {
        return id;
    }

    public void execute(String filePath, int num) {

        String filePathNew = filePath + id + "/" + num + ".xml";
        String xmlStr = dispatcherTarget.poll();
        String idDisp = CreateXml.getTargetString(xmlStr, "dispatched");
        CreateXml.createFile(filePathNew, xmlStr);
        Dispatcher.getInstance().removeLogBook(idDisp);


    }

    public void createDir(String filePath) {

        File newDir = new File(filePath + "/" + id);
        newDir.mkdirs();
    }

    public boolean isEmpty() {
        return dispatcherTarget.isEmpty();
    }

    public void work(String filePath) {
        while (isEmpty()) {
            Thread.yield();
            if (!isEmpty()) break;

        }

        int i = 0;
        while (!isEmpty()) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            i++;


            execute(filePath, i);

        }


    }


}
