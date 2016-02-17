/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package android.resource.remover;

/**
 *
 * @author Tarun
 */


import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class RemoverUtilities {
    
    
    
    public static int removeUnusedLines(String filename,SortedSet<Integer> linesSet)
    {
               
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            //String buffer to store contents of the file
            StringBuffer sb = new StringBuffer("");
            
            int lineNumber = 1; 
            String line;
            Iterator iterator = linesSet.iterator();
            int lineNumberToBeDeleted = (int) iterator.next();
            
            int count = 0;
            
            while((line = br.readLine())!=null)
            {
                if(lineNumber == lineNumberToBeDeleted)
                {
                    if(iterator.hasNext())
                    {
                        lineNumberToBeDeleted = (int) iterator.next();
                        count++;
                    }
                }
                
                else
                {
                    sb.append(line+"\n");
                }
                
                
                lineNumber++;
            }
            
            	FileWriter fw=new FileWriter(new File(filename));
		//Write entire string buffer into the file
		fw.write(sb.toString());
		fw.close();
           
            System.err.println("deleted lines" + count);

        }
        catch(Exception e)
        {
            System.err.println("error:" +e.getMessage());
            e.printStackTrace();
            return -10;

        }
        
        
        return 10;
        
    }
    
    
    public static void delete(String filename, int startline, int numlines)
    {
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(filename));
 
			//String buffer to store contents of the file
			StringBuffer sb = new StringBuffer("");
 
			//Keep track of the line number
			int linenumber = 1;
			String line;
 
			while((line=br.readLine())!=null)
			{
				//Store each valid line in the string buffer
				if(linenumber<startline||linenumber>=startline+numlines)
					sb.append(line+"\n");
				linenumber++;
			
                        }
                        
			if(startline+numlines>linenumber)
				System.out.println("End of file reached.");
			br.close();
 
			FileWriter fw=new FileWriter(new File(filename));
			//Write entire string buffer into the file
			fw.write(sb.toString());
			fw.close();
		}
		catch (Exception e)
		{
			System.out.println("Something went horribly wrong: "+e.getMessage());
		}
    }

    
    public static  ArrayList<LintProblem> parseAndgenerateLintProblems(String xmlFilePath , String moduleDirectory) 
           throws FileNotFoundException, ParserConfigurationException, SAXException, IOException
    {
        
        
        ArrayList<LintProblem>  A = new ArrayList();
        
        DocumentBuilderFactory factory ;
        factory = DocumentBuilderFactory.newInstance();
        
        DocumentBuilder builder = factory.newDocumentBuilder();
        
        Document document =  builder.parse(xmlFilePath);
        
        document.getDocumentElement().normalize();
       
        NodeList nodelist = document.getElementsByTagName("problem");
        
//        System.out.println("length" + nodelist.getLength());

        Element E;
        String filePath;
        int index,line;
        String previousFilePath = "";
        SortedSet<Integer> currentSet = new TreeSet();
        
        
        for(int i = 0 ; i< nodelist.getLength() ;i++)
        {
             E = (Element)nodelist.item(i);
        
             filePath =  E.getElementsByTagName("file").item(0).getTextContent();
             line =  Integer.parseInt(E.getElementsByTagName("line").item(0).getTextContent());
            
             index = filePath.lastIndexOf("$");            
             filePath = filePath.substring(index+1);
             filePath = moduleDirectory+filePath;

             if(line == 0 )
             {
                 A.add(new LintProblem(new File(filePath)));
             }
             else if(line == 1 && !filePath.equals(previousFilePath))
             {
                if(currentSet.size()!=0)
                {
                   // System.out.println("File:" + previousFilePath);
                   // System.out.println("lines-a:" + currentSet.size());
                    A.add(new LintProblem(new File(previousFilePath), currentSet));
                }
                
                
                     
                //System.out.println("File:" + filePath);
                //System.out.println("singleFile");

                A.add(new LintProblem(new File(filePath)));   
                currentSet = new TreeSet();

                 
             }
             else
             {
                 
                 
                 if(previousFilePath.equals("") || filePath.equals(previousFilePath))
                 {
                     currentSet.add(line);
                     
                     if(i == nodelist.getLength()-1)
                     {
                           // System.out.println("File:" + previousFilePath);
                           // System.out.println("lines-b:" + currentSet.size());
                            A.add(new LintProblem(new File(previousFilePath), currentSet));
                     }
                     
                 }
                 
                 else
                 {
                     if(currentSet.size() !=0)
                     {
                         //System.out.println("File:" + previousFilePath);
                         //System.out.println("lines-c:" + currentSet.size());
                         A.add(new LintProblem(new File(previousFilePath), currentSet));
                     }
                     
                     currentSet = new TreeSet();
                     currentSet.add(line);
                     
                     if(i == nodelist.getLength()-1)
                     {
                        A.add(new LintProblem(new File(filePath), currentSet));
                     }
                     
                 }
         
                 
             }
             
             previousFilePath = filePath;
        }
        
        
        
      return A;

    }
    
    
    public static  LinkedHashMap<String,List<Integer>> parseAndgenerateFileSet(String xmlFilePath , String moduleDirectory) 
           throws FileNotFoundException, ParserConfigurationException, SAXException, IOException
    {
        
        LinkedHashMap<String,List<Integer>> H = new LinkedHashMap();
        
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
                  toBeAdded = new ArrayList();
                  toBeAdded.add(Integer.parseInt(line));
                  H.put(filePath, toBeAdded);
             }
            
        }
        
      return H;

    }

    
    
}
