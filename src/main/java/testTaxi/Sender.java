package testTaxi;

public class Sender {

    int id;

    public Sender(int id) {
        this.id = id;
    }

    public Message createMessage(int idTarget, int num) {
        Message message = new Message();
        message.setId(id * 10 + num);

        String xmlString = CreateXml.createXmlString(idTarget);
        message.setXmlString(xmlString);
        Dispatcher.getInstance().adMessageFromSender(message);


        return message;
    }




}
