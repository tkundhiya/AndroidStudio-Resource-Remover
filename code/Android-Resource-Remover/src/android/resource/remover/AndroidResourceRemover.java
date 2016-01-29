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



 class Problem
{
      File file;
      int lineNo;
      

    public Problem(File file, int lineNo) {
        this.file = file;
        this.lineNo = lineNo;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
    
    public int getLineNo() {
        return lineNo;
    }

    public void setLineNo(int lineNo) {
        this.lineNo = lineNo;
    }
    
    public int resolve()
    {
        if(this.file.delete())
       {
    	      System.out.println(this.file.getName() + " deleted..");
              return 1;
       }
       else
       {
               System.out.println(this.file.getName() + " deletion failed..");
               return 0;
       }
    }
    
    
    public int resolveLayoutSafely()
    {
        if(this.lineNo ==1 )
        {
            this.resolve();
        }
        
        else
        {
            return 0;
        }
        
        return 0;
    }
    
}

public class AndroidResourceRemover {

    /**
     * @param args the command line arguments
     */
    
    HashSet validfileSet = new HashSet();
    
    
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        
//        for (String s: args) {
//            System.out.println(s);
//        }
        
        
        String inputXml = "inputLayout.xml";
        String moduleDirectory = "D:/iprof_android/trunk/code/" ;
        boolean askConfirm = false;
        
        if(!askConfirm)
        {
           parseAndResolveXml(inputXml, moduleDirectory);
        }
        else
        {
//           ArrayList<Problem> problems = parseXml(inputXml,moduleDirectory);
//           
//           ResolveProblem(problems);
           
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
                System.out.println(P.getFile().getPath()+" deleted..");
            }
            
            else if(resolveStatus == 0)
            {
                System.out.println(P.getFile().getPath()+" deletion failed");
            }
            
        }
        
      
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
