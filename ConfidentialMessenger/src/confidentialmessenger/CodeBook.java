package confidentialmessenger;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import java.io.Writer;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigInteger;
import org.w3c.dom.Element;

/**
 *
 * @author Shershnev
 */
public class CodeBook {      
    public CodeBook() throws ParserConfigurationException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        try {            
            doc = builder.parse("codeBook.xml");
        } catch (SAXException | IOException ex) {
            doc = builder.newDocument();
            Element root = doc.createElement("root");
            doc.appendChild(root);
            save();
        }
    }    
    
    public void addRecord(String name, BigInteger e, BigInteger n) throws IOException, RecordAlreadyExistException {        
        if (doc.getElementsByTagName(name).getLength() > 0)
            throw new RecordAlreadyExistException();
        Element node = doc.createElement(name);        
        node.setAttribute("e", e.toString());
        node.setAttribute("n", n.toString());
        doc.getLastChild().appendChild(node);
        save();
    }
    
    public BigInteger geteKey(String name) throws RecordDoNotExistException {   
         if (doc.getElementsByTagName(name).getLength() == 0)
            throw new RecordDoNotExistException();                  
        return new BigInteger(doc.getElementsByTagName(name).item(0).getAttributes().getNamedItem("e").getNodeValue());        
    }
    
    public BigInteger getnKey(String name) throws RecordDoNotExistException {  
        if (doc.getElementsByTagName(name).getLength() == 0)
            throw new RecordDoNotExistException();   
        return new BigInteger(doc.getElementsByTagName(name).item(0).getAttributes().getNamedItem("n").getNodeValue());        
    }
    
    private void save() throws IOException {
        try {
            PrintWriter out = new PrintWriter(new File("codeBook.xml").getAbsoluteFile());
            try {
                out.print(getString());
            } finally {
                out.close();
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    private String getString() throws IOException {
        OutputFormat format = new OutputFormat(doc);
        format.setLineWidth(65);
        format.setIndenting(true);
        format.setIndent(2);
        Writer out = new StringWriter();
        XMLSerializer serializer = new XMLSerializer(out, format);
        serializer.serialize(doc);        
        return out.toString();
    }
    
    private Document doc;
}
