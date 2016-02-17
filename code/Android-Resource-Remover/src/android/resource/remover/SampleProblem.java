/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package android.resource.remover;

import java.util.ArrayList;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.Callable;

/**
 *
 * @author Tarun
 */
public class SampleProblem implements Callable{

    String file;
    int index;
    
    public SampleProblem(String inputFile , int fileNo)
    {
        file = inputFile;
        index = fileNo;
    }
    
    @Override
    public Object call() throws Exception {
        try
        {
            System.err.println(Thread.currentThread().getName()+"started processing File"+ index + "at" + System.currentTimeMillis());
            RemoverUtilities.removeUnusedLines(file, generateRandomLines());
            System.err.println(Thread.currentThread().getName()+"finished processing File"+ index + "at" + System.currentTimeMillis());
            return "done";
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return "failed";
        }
        
    }
    
    
    public static SortedSet<Integer> generateRandomLines()
    {
        SortedSet<Integer> lines = new TreeSet();
        
        Random random = new Random();
//        
        int prevRandom = 1;
        int rand = 0;
//      int random = (Integer)(10*Math.random());
        
        for(int i = 0 ; i<600 ; i++)
        {
            rand = random.nextInt(10);
            prevRandom += rand;
            //System.out.println("random" +prevRandom);
            lines.add(prevRandom);
        }
        
        return lines;
    }
}
