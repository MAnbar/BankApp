
package bank.threads;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class FileListenerRunnable implements Runnable{
    private String pathName;
    private DocumentBuilderFactory factory;
    private DocumentBuilder builder;
    private ArrayBlockingQueue<Document> documentQueue;
    
    public FileListenerRunnable(ArrayBlockingQueue<Document> documentQueue,String pathName){
        this.documentQueue=documentQueue;
        this.pathName=pathName;
        try {
            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
            //Build Document
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(FileListenerRunnable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void checkPath(){
        if(Thread.currentThread().isInterrupted()){
            return;
        }
        File folder = new File(pathName);
        File[] listOfFiles = folder.listFiles();
        String fileName;
        String AbsFileName;
        for (File file : listOfFiles) {
            if (file.isFile()) {
                fileName=file.getName();
                AbsFileName=pathName+"/"+fileName;
                //System.out.println("File " + fileName);
                if(fileName.matches("(.*)Transactions\\.(.*)")){
                    addDocument(AbsFileName);
                    if(Thread.currentThread().isInterrupted()){
                        return;
                    }
                    System.out.println("File Queued"); 
                    renameFile(file, fileName);
                    System.out.println("File Renamed"); 
                }
            } 
        }
    }
    
    public boolean addDocument(String fileName){
        try {
            Document currentDoc;
            currentDoc = builder.parse(new File( fileName ));
            System.out.println("Listener Putting..");
            System.out.println("Listener: "+currentDoc.getDocumentURI());
            documentQueue.put(currentDoc);
            System.out.println("Listener Running..");
            //System.out.println(documentQueue.peek().getDocumentURI());
            return true;
        } catch (SAXException ex) {
            Logger.getLogger(FileListenerRunnable.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileListenerRunnable.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
                Thread.currentThread().interrupt(); 
        }
        return false;

    }
    
    public boolean renameFile(File file,String fileName){
        fileName=fileName.replace("Transactions", "Transactions-Done");
        File DestFile=new File(pathName+"/"+fileName);
        file.renameTo(DestFile);
        return true;
    }

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()){
            checkPath();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) { 
                Thread.currentThread().interrupt(); 
            }
        }
        System.out.println("Path Listener Thread Terminating..");
    }
}
