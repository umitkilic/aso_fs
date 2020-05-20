/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asomain;

import java.util.Random;

/**
 *
 * @author Umit Kilic
 */
public class updateclass {
    
    public void updatefor_mpicurrent(members member){
        Random rnd=new Random();
        if (member.mov_based_on_current_pos>-0.5) {
            int i1=rnd.nextInt(member.pos.length);
            int i2=rnd.nextInt(member.pos.length);
            //System.out.println("r1:"+i1+" r2:"+i2);
            int v1=member.pos[i1];
            int v2=member.pos[i2];
            
            member.pos[i1]=v2;
            member.pos[i2]=v1;
        } else {
            
        }
    }
    
    public void updatefor_mpisociety(members member,members[] members,int gbm_id){
        if (member.mov_based_on_other_pos>-0.25) {
            Random rnd=new Random();
            int i1=rnd.nextInt(member.pos.length);
            int i2=rnd.nextInt(member.pos.length);
            int max=0;
            if(i1>i2) max=i1; else max=i2;
            int d=0;
            for (int i = 0; i < Math.abs((i2-i1)); i++) {
               member.pos[max]= members[gbm_id].pos[max];
               max-=1;
            }
            
        }else if (member.mov_based_on_other_pos>-0.5 && member.mov_based_on_other_pos<-0.25) {
            Random rnd=new Random();
            for (int i = 0; i < member.pos.length; i++) {
                int r=rnd.nextInt(2);
                if(r==0){
                    member.pos[i]=members[gbm_id].pos[i];
                }
            }
        }else if (member.mov_based_on_other_pos>-0.75 && member.mov_based_on_other_pos<-0.5) {
            Random rnd=new Random();
            int i1=rnd.nextInt(member.pos.length);
            int i2=rnd.nextInt(member.pos.length);
            int max=0;
            if(i1>i2) max=i1; else max=i2;
            int othermemid=rnd.nextInt(member.pos.length);
            while (othermemid==gbm_id){
                othermemid=rnd.nextInt(member.pos.length);
            }
            for (int i = 0; i < Math.abs((i2-i1)); i++) {
               member.pos[max]= members[othermemid].pos[max];
               max-=1;
            }
        }else{
            Random rnd=new Random();
            int othermemid=rnd.nextInt(member.pos.length);
            while (othermemid==gbm_id){
                othermemid=rnd.nextInt(member.pos.length);
            }
            
            for (int i = 0; i < member.pos.length; i++) {
                int r=rnd.nextInt(2);
                if(r==0){
                    member.pos[i]=members[othermemid].pos[i];
                }
            }
        }
    }
    
    public void updatefor_mpipast(members member){
        Random rnd=new Random();
           
            for (int i = 0; i < member.pos.length; i++) {
                int r=rnd.nextInt(2);
                if(r==0){
                    member.pos[i]=member.p_pos[i];
                }
            }
    }
    
    
}
