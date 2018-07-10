package xmlapplication;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

public class Answer {

    XMLStreamWriter writer;
    XMLStreamReader parser;

    private String number;
    private String text;
    private boolean right;
    
    Answer(XMLStreamReader parser, XMLStreamWriter writer ){
        this.parser = parser;
        this.writer = writer;
        this.number = parser.getAttributeValue(0);
        this.text = parser.getAttributeValue(1);
        this.right = Boolean.parseBoolean(parser.getAttributeValue(2));
    }
    
    public String getNumber(){
        return number;
    }
    public boolean hasRight(){
        return right;
    }
      
    public void writeAnswer() throws XMLStreamException{       
        writer.writeStartElement("Answer");
        writer.writeAttribute("number",number);
        writer.writeAttribute("text",text);
        writer.writeEndElement();
    }
    
    public void printInfo(){
        System.out.println("Ответ №" + number + ": " + text);
    }
}
