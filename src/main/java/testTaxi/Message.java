package testTaxi;

public class Message implements MessageInt {

    private String xmlString;
    private int id;


    public Message() {
    }

    public Message(int id) {
        this.id = id;
    }

    public Message(String xmlString, int id) {
        this.xmlString = xmlString;
        this.id = id;
    }

    public String getXmlString() {
        return xmlString;
    }

    public void setXmlString(String xmlString) {
        this.xmlString = xmlString;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
