package xmlapplication;

import com.sun.xml.internal.txw2.output.IndentingXMLStreamWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

public class XMLApplication {

    public static void main(String[] args) throws IOException, XMLStreamException {
        
        final String IN_FILE = "Test.xml";
        final String OUT_FILE = "Result.xml";
        
        FileInputStream file;
        FileWriter filewriter;
        
        XMLInputFactory inFactory;
        XMLStreamReader parser;
        
        XMLOutputFactory outFactory;
        XMLStreamWriter writer;
        
        ArrayList<Answer> allAnswers;
        
        Scanner in;
        
        int event;
        int sumQuestions = 0;
        int sumRightAnswer = 0;
        Question question;
        
        try{
            file= new FileInputStream(IN_FILE);    
            filewriter = new FileWriter(OUT_FILE);
            
            inFactory = XMLInputFactory.newInstance();
            parser = inFactory.createXMLStreamReader(file);
            
            outFactory = XMLOutputFactory.newInstance();
            writer = outFactory.createXMLStreamWriter(filewriter);
            writer = new IndentingXMLStreamWriter(writer);
            
            allAnswers = new ArrayList<>();
            
            in = new Scanner(System.in);
            question = new Question();
            
            writer.writeStartDocument();
            writer.writeStartElement("Questions");
            
            while (parser.hasNext()) {
                event = parser.next();
                
                if (event == XMLStreamConstants.START_ELEMENT) {
                    if ("Question".equals(parser.getLocalName())) {
                       question = new Question(parser, writer);
                        question.printInfo();
                    }
                    if ("Answer".equals(parser.getLocalName())) {
                        Answer answer = new Answer(parser, writer);
                        answer.printInfo();
                        allAnswers.add(answer);
                        
                    }
                }
                if (event == XMLStreamConstants.END_ELEMENT) {
                    if ("Question".equals(parser.getLocalName())) {
                        System.out.println("Для ответа введите номера правильных ответов через пробел:");
                        
                        String inputArr[] = in.nextLine().replaceAll("[^ 0-9]", "").split(" ");
                        
                        
                        
                        question.checkOfSuccess(allAnswers, inputArr);
                        
                        question.writeStartElement();
                        question.writeAtributes();
                        
                        for(String num:inputArr){
                            for(Answer answer : allAnswers)
                               if (num.equals(answer.getNumber())) {
                                        answer.writeAnswer();
                                    }
                        }
                        
                        question.writeEndElement();
                        
                        if(question.hasSuccess()) {
                            sumRightAnswer++;
                        }
                        sumQuestions++;
                        allAnswers.clear();

                    }
                }
                if (event == XMLStreamConstants.END_DOCUMENT) {
                    writer.writeEndElement();
                    writer.writeEndDocument();
                    writer.flush();
                    writer.close();
                    System.out.println("Общее колличество вопросов: " + sumQuestions);
                    System.out.println("Колличество правильных ответов: " + sumRightAnswer);
                }
            }
      
        }catch(FileNotFoundException e) {
            System.out.println("Фаил с вопросами не был найден");
        }
    }
    
}
