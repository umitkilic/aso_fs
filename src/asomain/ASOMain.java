/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asomain;

import java.util.Arrays;
import java.util.Random;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;
import java.util.stream.*;

/**
 *
 * @author Umit Kilic
 */
public class ASOMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int foldnumber=10;
        String filepath="C:\\Users\\Umit Kilic\\Desktop\\Doktora\\esra hoca\\zoo.arff";
        Instances data;
        data=readData(filepath);
        System.out.println("Number of attributes of the data: "+ data.numAttributes());
        members[] members=new members[data.numAttributes()-1];
        for (int k=0;k<members.length;k++){ members[k]=new members(data.numAttributes()-1); }
        
        System.out.println(members.length+" members created with the lenght of "+members[0].pos.length+" for pos.");
        
        prep p=new prep();
        members=p.initmembers(data.numAttributes(),members);
        System.out.println("Created members' randomly prepared positions are:");
        for (int k=0;k<members.length;k++){ System.out.println(k+". member's pos: "+ Arrays.toString(members[k].pos)); }
        
        int a=0;
        int gbm_id=0; //global best mem id
        /*int[] aa=new int[5];
        aa[0]=1;
        aa[1]=0;
        aa[2]=1;
        aa[3]=1;
        aa[4]=0;
        
        System.out.println("toplam:"+IntStream.of(aa).sum());*/
        
        while(a<100){
            System.out.println("Iteration "+a);
            movementpolicies mp=new movementpolicies();
            gbm_id=mp.UpdateMembersAndFindGlobalBest(members, data, foldnumber);
            mp.based_on_current_pos(members, gbm_id);
            mp.based_on_other_mem_pos(members, gbm_id);
            for (int i = 0; i < members.length; i++) {
                if (i==gbm_id)
                    continue;
                Random r=new Random();
                int rnd=r.nextInt(3);
                
                updateclass upd=new updateclass();
                if (rnd==0)
                upd.updatefor_mpicurrent(members[i]);
                else if(rnd==1)
                upd.updatefor_mpisociety(members[i], members,gbm_id);
                else if(rnd==2)
                upd.updatefor_mpipast(members[i]);
            }
            
            a++;
            System.out.println("***END OF THE ITERATION "+a+" ***");
            System.out.println(gbm_id+".mem pos (Global BEST): "+Arrays.toString(members[gbm_id].pos)+ " mem fitness:"+ members[gbm_id].fitness);
            /*for (int i = 0; i < members.length; i++) {
                System.out.println(i+".mem pos: "+Arrays.toString(members[i].pos)+ " mem fitness:"+ members[i].fitness);
            }*/
        }
        
    }
    
    public static Instances readData(String name){
        ConverterUtils.DataSource datasource;
        Instances data=null;
        
        try{
            datasource=new ConverterUtils.DataSource(name);
            data=datasource.getDataSet();
            data.setClassIndex(data.numAttributes()-1); // class index belirlendi
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return data;
    }
    
    
}
