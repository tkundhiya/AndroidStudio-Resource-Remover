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
import java.util.Iterator;
import java.util.SortedSet;

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
            
            while((line = br.readLine())!=null)
            {
                if(lineNumber == lineNumberToBeDeleted)
                {
                    if(iterator.hasNext())
                    {
                        lineNumberToBeDeleted = (int) iterator.next();
                    }
                }
                
                else
                {
                    sb.append(line+"\n");
                }
                
                
                lineNumber++;
            }
            
            
            

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

    
    
    
}
