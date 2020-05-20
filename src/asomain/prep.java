/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asomain;

import java.util.Random;
import java.util.stream.StreamSupport;
import java.util.stream.*;
import weka.core.Instances;

/**
 *
 * @author Umit Kilic
 */
public class prep {
    
    public members[] initmembers(int numofattr,members[] members){
        //int[][] members=new int[numofattr-1][numofattr-1];
        Random r=new Random();
        int a=r.nextInt(2)-1;
        
        for(int k=0;k<numofattr-1;k++){
            a=r.nextInt(2)-1;
            for(int l=0;l<numofattr-1;l++){
                a=r.nextInt(2);
                members[k].pos[l]=a;
                //members[k][l]=a;
                //System.out.print(" degerler:"+k+"-"+l+":"+members[k][l]);
            }
            int toplam=IntStream.of(members[k].pos[0]).sum();
            //System.out.println("Toplam:"+toplam);
            if (toplam==0){
                //System.out.println("toplam sifir");
                Random rnd=new Random();
                members[k].pos[rnd.nextInt(numofattr-1)]=1;
            }
            
            for(int l=0;l<numofattr-1;l++){
                //System.out.print(" degerler2:"+k+"-"+l+":"+members[k][l]);
            }
        }
        
        return members;
        
    }
    
    
}
