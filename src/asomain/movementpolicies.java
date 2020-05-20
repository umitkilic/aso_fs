/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asomain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.lazy.IBk;
import weka.core.Instances;
import java.util.stream.*;
import weka.classifiers.bayes.BayesNet;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.RBFNetwork;
import weka.classifiers.meta.Bagging;
import weka.classifiers.rules.DecisionTable;
import weka.classifiers.rules.OneR;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomTree;

/**
 *
 * @author Umit Kilic
 */
public class movementpolicies {
    
    public void based_on_current_pos(members[] members,int global_best_mem/*Instances ins,int foldnumber*/){
        // compute fickleness index
        System.out.println("MPi Current is calculating...\nFitnesses:");
        Random r=new Random();
        
        for (int i = 0; i < members.length; i++) {
            double rnd=r.nextDouble();
            double fi=1-rnd*(members[global_best_mem].fitness/members[i].fitness)-(1-rnd)*(members[i].p_fitness/members[i].fitness);
            members[i].mov_based_on_current_pos=fi;
            //System.out.println("mov_mpicurrent:"+members[i].mov_based_on_current_pos);
        }    
    }
    
    public void based_on_other_mem_pos(members[] members,int global_best_mem){
        //compute external irregularity index
        System.out.println("MPi Society is calculating...");
        //double[] vals=this.standart_deviation_mean(members);
        //double CV=vals[1]/vals[0];
        
        for (int i = 0; i < members.length; i++) {
            //double ei=1-Math.exp(-10*CV);
            double ei=1-Math.exp(-1*(members[i].fitness-members[global_best_mem].fitness));
            members[i].mov_based_on_other_pos=ei;
            //System.out.println("mov_mpisociety:"+members[i].mov_based_on_other_pos);
        }
        
    }
    
    public void based_on_past_pos(members member){
        /*int[] new_pos=new int[member.pos.length];
        for (int i = 0; i < member.pos.length; i++) {
            
            if (i<member.pos.length/2) {
                new_pos[i]=member.pos[i];
            }else{
                new_pos[i]=member.p_pos[i];
            }
        }
        System.out.println("New Pos:"+Arrays.toString(new_pos));
        member.pos=new_pos;*/
        
    }
    
    public double[] standart_deviation_mean(members[] members){
        double t=0, std=0,m=0;
        double[] vals=new double[]{0,0};
        int len=members.length;
        for(int i=0;i<len;i++){
            t=t+members[i].fitness;
        }
        m=t/len;
        
        for(int i=0;i<len;i++){
            std+=Math.pow(members[i].fitness - m, 2);
        }
        
        vals[0]=m;
        vals[1]=std;
        
        return vals;
    
    }
    
    public int UpdateMembersAndFindGlobalBest(members[] members,Instances ins,int foldnumber){
        System.out.println("Updating and Finding Global Best...\n:");
        
        int global_best_mem=0;
        double global_best_fit=0;
        Random r=new Random();
        
        for (int i = 0; i < members.length; i++) {
            members[i].fitness=this.getFitness(ins, members[i].pos, foldnumber);
            if(members[i].fitness>members[i].p_fitness) {members[i].p_fitness=members[i].fitness; }
            if(members[i].fitness>global_best_fit){ global_best_mem=i; global_best_fit=members[i].fitness; }
            else if(members[i].fitness==global_best_fit){
                if (IntStream.of(members[i].pos).sum()<IntStream.of(members[global_best_mem].pos).sum()) {
                    global_best_mem=i; global_best_fit=members[i].fitness;
                }
            }
            //System.out.println(i+".member's fitness: "+members[i].fitness+ " member's p_fitness:"+members[i].p_fitness); 
            //System.out.println(" === %"+ (100*i)/members.length);
        }
        
        System.out.println("Global best is member :"+global_best_mem+" and its fitness:"+members[global_best_mem].fitness+" num of features:"+IntStream.of(members[global_best_mem].pos).sum());
        
        return global_best_mem;
    }
    
   
    public double getFitness(Instances ins,int[] memberpos,int foldnumber){
        Classifier classifier;
        Evaluation eval;
        Instances data;
        double fmeasure=0.0;
        
        try {
            //classifier=new RBFNetwork();
            //classifier=new BayesNet();
            classifier=new RandomTree();              // * +
            //classifier=new J48();                     // ** +
            //classifier=new Bagging();                 // *** +
            //classifier=new NaiveBayes();              // * +
            //classifier=new DecisionTable();           // ** + (ayarlanacak)
            //classifier=new SMO();                     // **** +
            //classifier=new RandomForest();              // **** -
            //classifier=new OneR();                      // ** +
            //classifier=new IBk(3); // sınıflandırıcı oluşturuldu // * +
            data=this.deleteZeros(memberpos, ins);
            eval=new Evaluation(data);
            eval.crossValidateModel(classifier, data, foldnumber, new Random(1));
            fmeasure=eval.weightedFMeasure();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return fmeasure;
    }
    
    public Instances deleteZeros(int[] memberpos,Instances ins){
        List<Integer> deleteIndexes=new ArrayList<>();
        
        for(int i=0;i<memberpos.length;i++){
            if(memberpos[i]==0){
                deleteIndexes.add(i);
            }
        }
        
        int girildi=0;
        
        Instances copy_ins=new Instances(ins);
        
        
        for (int i = 0; i < deleteIndexes.size(); i++) {
            //System.out.println("silinecek index:"+(deleteIndexes.get(i))+" data num of attr:"+copy_ins.numAttributes());
            copy_ins.deleteAttributeAt(deleteIndexes.get(i)-girildi);
            girildi++;
        }
        
        //System.out.println(" after delete, data num of attr:"+copy_ins.numAttributes());
        return copy_ins;
    }

}
