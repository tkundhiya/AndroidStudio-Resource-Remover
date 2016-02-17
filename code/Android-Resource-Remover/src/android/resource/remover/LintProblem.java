/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package android.resource.remover;

import java.io.File;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.Callable;

/**
 *
 * @author Tarun
 */
public class LintProblem implements Callable {
    
    private File file;
    private SortedSet lineSet;  
    private boolean isWholeFile;

    
    public LintProblem()
    {
        
    }
    
    public LintProblem(File file)
    {
        this.file = file;
        this.isWholeFile = true;
    }
    
    
    public LintProblem(File file, SortedSet set) {
        this.file = file;
        this.lineSet =  set;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
    
    public void setlineSet(SortedSet T)
    {
        this.lineSet = T;
    }
    
    public SortedSet getlineSet()
    {
        return this.lineSet;
    }

    public boolean isIsWholeFile() {
        return isWholeFile;
    }

    public void setIsWholeFile(boolean isWholeFile) {
        this.isWholeFile = isWholeFile;
    }
    
    
    
    public int resolveFile()
    {
        
        if(!this.file.exists())
        {
            System.err.println(this.file.getPath()+"does not exists..");
            return -1;
        }
     
     
        if(this.isWholeFile)
        {
            this.file.delete();
            return 1;    
        }
        
        else
        {
            
            if(!this.file.canWrite())
            {
                System.err.println(this.file.getPath()+"is not editable..");
                return -1;
            }
            
            if(this.lineSet == null || this.lineSet.size() ==0)
            {
                System.err.println(this.file.getPath());
                System.err.println("Internal Error - No lines are removable on this file");
                return -1;
            }
            
            
            return RemoverUtilities.removeUnusedLines(this.file.getPath(),this.lineSet);
            
        }
        
    }

    @Override
    public Object call() throws Exception {
        
        return this.resolveFile();
       
    }
    
    
    static enum ErrorType
    {
      FILE_NOT_FOUND,FILE_NOT_WRITABLE,FILE_NOT_READABLE,TECHNICAL_ERROR,NO_ERROR_FILE_DELETED;  
    }
    
    
}
