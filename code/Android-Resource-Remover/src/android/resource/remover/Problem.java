/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package android.resource.remover;

import java.io.File;

/**
 *
 * @author Tarun
 */
public class Problem
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
