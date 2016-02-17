/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package android.resource.remover;


import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.xml.sax.SAXException;


/**
 *
 * @author Tarun
 */


//<problem>
//    <file>file://$PROJECT_DIR$/schoolEraGlobal/src/main/res/values/color.xml</file>
//    <line>25</line>
//    <module>schoolEraGlobal</module>
//    <entry_point TYPE="file" FQNAME="file://$PROJECT_DIR$/schoolEraGlobal/src/main/res/values/color.xml" />
//    <problem_class severity="WARNING" attribute_key="WARNING_ATTRIBUTES">Unused resources</problem_class>
//    <hints />
//    <description>&lt;html&gt;The resource &lt;code&gt;R.color.ButtonColorWhenPrimaryIsOrange&lt;/code&gt; appears to be unused&lt;/html&gt;</description>
//  </problem>




public class AndroidResourceRemover {

    /**
     * @param args the command line arguments
     */
    
    
    
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        
//        for (String s: args) {
//            System.out.println(s);
//        }
        
        
        String inputXml = "inputLayout.xml";
        String moduleDirectory = "D:/iprof_android/trunk/code/" ;
        boolean askConfirm = false;
        

        if(askConfirm)
        {
            //System.out.println("entered");
           
               
            //ArrayList<LintProblem> lintProblems = RemoverUtilities.parseAndgenerateLintProblems(inputXml, moduleDirectory);
            HashMap<String,List<Integer>> H = RemoverUtilities.parseAndgenerateFileSet(inputXml, moduleDirectory);
            
            
            for(String key: H.keySet())
            {
                System.out.println("File :" + key);
                System.out.println("Lines :" + H.get(key).size());
                
            }
            
            
            
            
        }
        
        
//        startResolvingTheData();
        
          solveProblems();
//        String filePath = "C:/Users/Tarun/Desktop/data/input"+1+".txt";
//        SortedSet<Integer> set = SampleProblem.generateRandomLines();
//        RemoverUtilities.removeUnusedLines(filePath,set);
//        
        
    }
    
    
    
    private static void startResolvingTheData() throws Exception
    {

         long init = System.currentTimeMillis();
         System.err.println("currentTime"+init);
        
        ExecutorService executorService = Executors.newFixedThreadPool(100);

        Set<Callable<String>> callables = new HashSet<Callable<String>>();

        callables.add(new Callable<String>() {
            public String call() throws Exception {
                System.out.println(Thread.currentThread().getName() + " started Task 1 at" + System.currentTimeMillis());
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName() + " solved Task 1 at" + System.currentTimeMillis());
                return "Task 1";
            }
        });
        callables.add(new Callable<String>() {
            public String call() throws Exception {
                System.out.println(Thread.currentThread().getName() + " started Task 2 at" + System.currentTimeMillis());
                Thread.sleep(3000);
                System.out.println(Thread.currentThread().getName() + " solved Task 2 at" + System.currentTimeMillis());
                return "Task 2";
            }
        });
        callables.add(new Callable<String>() {
            public String call() throws Exception {
                System.out.println(Thread.currentThread().getName() + " started Task 3 at" + System.currentTimeMillis());
                Thread.sleep(4000);
                System.out.println(Thread.currentThread().getName() + " solved Task 3 at" + System.currentTimeMillis());
                return "Task 3";
            }
        });
        callables.add(new Callable<String>() {
            public String call() throws Exception {
                System.out.println(Thread.currentThread().getName() + " started Task 4 at" + System.currentTimeMillis());
                Thread.sleep(5000);
                System.out.println(Thread.currentThread().getName() + " solved Task 4 at" + System.currentTimeMillis());
                return "Task 4";
            }
        });
        callables.add(new Callable<String>() {
            public String call() throws Exception {
                System.out.println(Thread.currentThread().getName() + " started Task 5 at" + System.currentTimeMillis());
                Thread.sleep(5000);
                System.out.println(Thread.currentThread().getName() + " solved Task 5 at" + System.currentTimeMillis());
                return "Task 5";
            }
        });
        callables.add(new Callable<String>() {
            public String call() throws Exception {
                System.out.println(Thread.currentThread().getName() + " started Task 6 at" + System.currentTimeMillis());
                Thread.sleep(5000);
                System.out.println(Thread.currentThread().getName() + " solved Task 6 at" + System.currentTimeMillis());
                return "Task 6";
            }
        });
        
        
        
        
        
        for(int i = 8 ; i<100 ; i++)
        {
                callables.add(new Callable<String>() {
                public String call() throws Exception {
                    System.out.println(Thread.currentThread().getName() + " started Task x at" + System.currentTimeMillis());
                    Thread.sleep(2000);
                    System.out.println(Thread.currentThread().getName() + " solved Task x at" + System.currentTimeMillis());
                    return "Task x";
                }
            });
        }
        
        
        List<Future<String>> futures = executorService.invokeAll(callables);

        for(Future<String> future : futures){
           // System.out.println("future.get = " + future.get());
        }
        
        long end = System.currentTimeMillis();
        System.err.println("endTime"+end);
        System.err.println("Total Time :" + (end-init));
            

        executorService.shutdown();
        
    }
    
    
    public static  HashMap<String,List<Integer>> parseAndgenerateFileSet(String xmlFilePath , String moduleDirectory) 
           throws FileNotFoundException, ParserConfigurationException, SAXException, IOException
    {
        
        HashMap<String,List<Integer>> H = new HashMap();
        
        DocumentBuilderFactory factory ;
        factory = DocumentBuilderFactory.newInstance();
        
        DocumentBuilder builder = factory.newDocumentBuilder();
        
        Document document =  builder.parse(xmlFilePath);
        
        document.getDocumentElement().normalize();
       
        NodeList nodelist = document.getElementsByTagName("problem");
        
        System.out.println("length" + nodelist.getLength());
        
        Problem P;
        List<Integer> toBeAdded = new ArrayList();
        Element E;
        String filePath,line;
        int index;
        
        
        for(int i = 0 ; i< nodelist.getLength() ;i++)
        {
             E = (Element)nodelist.item(i);
        
             filePath =  E.getElementsByTagName("file").item(0).getTextContent();
             line =  E.getElementsByTagName("line").item(0).getTextContent();
            
             index = filePath.lastIndexOf("$");
            
             filePath = filePath.substring(index+1);
            
             filePath = moduleDirectory+filePath;

             if(H.containsKey(filePath))
             {
                 H.get(filePath).add(Integer.parseInt(line));
             }

             else
             {
                toBeAdded.clear();
                toBeAdded.add(Integer.parseInt(line));
                H.put(line, toBeAdded);
             }
            
        }
        
      return H;

    }
    
    public static void clearFileMap(HashMap<String,List<Integer>> H)
    {
        for(String filepath :H.keySet())
        {
            if(H.get(filepath).size()>1)
            {
                H.remove(filepath);
            }
        }
               
    }
    
    
    public static void parseAndResolveXml(String xmlFilePath , String moduleDirectory) throws Exception
    {
        DocumentBuilderFactory factory ;
        factory = DocumentBuilderFactory.newInstance();
        
        DocumentBuilder builder = factory.newDocumentBuilder();
        
        Document document =  builder.parse(xmlFilePath);
        
        document.getDocumentElement().normalize();
        
        System.out.println("Root Element:" + document.getDocumentElement().getNodeName());
        
        
        NodeList nodelist = document.getElementsByTagName("problem");
        
        System.out.println("length" + nodelist.getLength());
        
        Problem P =null;
        File file ;
        int totalFilesDeleted = 0;
        
        for(int i = 0 ; i< nodelist.getLength() ;i++)
        {
            
            Element E = (Element)nodelist.item(i);
        
            String filePath =  E.getElementsByTagName("file").item(0).getTextContent();
            String line =  E.getElementsByTagName("line").item(0).getTextContent();
            
            int index = filePath.lastIndexOf("$");
            
            filePath = filePath.substring(index+1);            
            filePath = moduleDirectory+filePath;
    
            file = new File(filePath);

            if(P == null)
            {
              P = new Problem(file,Integer.parseInt(line));   
            }

            else
            {
                P.file = file;
                P.lineNo = Integer.parseInt(line);
            }

            int resolveStatus = P.resolveLayoutSafely();


            if(resolveStatus ==1)
            {
                totalFilesDeleted++;
                System.out.println(P.getFile().getPath()+" deleted..");
            }

            else if(resolveStatus == 0)
            {
                System.out.println(P.getFile().getPath()+" deletion failed");
            }
            
            
            
        }
        
      
    }
    
    
    private static void solveProblems() throws Exception
    {
        Set<Callable<String>> callables = new HashSet<Callable<String>>();
        ExecutorService service = Executors.newFixedThreadPool(4);
        
        for(int i = 1 ; i <=12 ; i++)
        {
            String filePath = "C:/Users/Tarun/Desktop/data/input"+i+".txt";
            
            SampleProblem S = new SampleProblem(filePath,i);
            
            callables.add(S);
            
        }
        
         List<Future<String>> futures;
         futures = service.invokeAll(callables);
         
         for(Future<String> future : futures){
            System.out.println("future.get = " + future.get());
        }
         
        service.shutdown();
    }
    
    
    public static ArrayList<Problem> parseXml(String xmlFilePath , String moduleDirectory) throws Exception
    {
        DocumentBuilderFactory factory ;
        factory = DocumentBuilderFactory.newInstance();
        
        DocumentBuilder builder = factory.newDocumentBuilder();
        
        Document document =  builder.parse(xmlFilePath);
        
        document.getDocumentElement().normalize();
        
     //   System.out.println("Root Element:" + document.getDocumentElement().getNodeName());
        
        ArrayList<Problem> problems = new ArrayList();
        
        NodeList nodelist = document.getElementsByTagName("problem");
        
        System.out.println("length" + nodelist.getLength());
        
        Problem P;
        
        for(int i = 0 ; i< nodelist.getLength() ;i++)
        {
            Element E = (Element)nodelist.item(i);
        
            String filePath =  E.getElementsByTagName("file").item(0).getTextContent();
            String line =  E.getElementsByTagName("line").item(0).getTextContent();
            
            int index = filePath.lastIndexOf("$");
            
            filePath = filePath.substring(index+1);
            
            filePath = moduleDirectory+filePath;
    
            File file = new File(filePath);
            
            P = new Problem(file,Integer.parseInt(line));
            
            System.out.println("filePath" + "-" + P.getFile().getPath());
            System.out.println("lineNo" + i + "-" + P.getLineNo());
            
            
            problems.add(P);
            
        }
        
      return problems;
    
    }
    
    
    public static void ResolveProblem(ArrayList<Problem> problems)
    {
        for(Problem p: problems)
        {
           if(p.lineNo == 0)
           {
               System.out.println("Single File... Resolving");
               p.resolve();
           }
           else if(p.lineNo ==1)
           {
               System.out.println("Take user Consent");
               p.resolve();
           }
           
           else
           {
               System.out.println("item resource unused");
           }
           
        }
    }
    
    
    
    
}
