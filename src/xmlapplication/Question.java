package xmlapplication;

import java.util.ArrayList;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

public class Question {
    
    XMLStreamReader parser;
    XMLStreamWriter writer;
    
    private String number;
    private String text;
    private boolean success;
    
    Question() {
        success = false;
        number = "0";
        text = "вопрос не инициализирован";
    }
    
    Question(XMLStreamReader parser, XMLStreamWriter writer) {
        this.parser = parser;
        this.writer = writer;
        this.number = parser.getAttributeValue(0);
        this.text = parser.getAttributeValue(1);
        success = false;

    }

    public String getText() {
        return text;
    }

    public String getNum() {
        return number;
    }
    
    public boolean hasSuccess() {
        return success;
    }

    public void checkOfSuccess(ArrayList<Answer> allAnswers, String[] inputArr){
       for (Answer answer : allAnswers) {
                            boolean foundRight = false;
                            boolean foundFalse = false;
                            if (answer.hasRight()) {
                                for (String num : inputArr) {
                                    if (num.equals(answer.getNumber())) {
                                        foundRight = true;
                                    }
                                }
                            } else if (!answer.hasRight()) {
                                foundRight = true;
                                for (String num : inputArr) {
                                    if (num.equals(answer.getNumber())) {
                                        foundFalse = true;
                                    }
                                }
                            }
                            if (foundRight && !foundFalse) {
                                success = true;  
                            } else {
                                success = false;
                                break;
                            }
                        }
   }

    public void writeStartElement() throws XMLStreamException {
        writer.writeStartElement("Question");

    }

    public void writeAtributes() throws XMLStreamException {
        writer.writeAttribute("number", number);
        writer.writeAttribute("text", text);
        writer.writeAttribute("success", String.valueOf(success));
    }

    public void writeEndElement() throws XMLStreamException {
        writer.writeEndElement();
    }
    
    public void printInfo(){
        System.out.println("Вопрос №" + number + ": " + text);
        System.out.println("Варианты ответов:");
    }
    
}
