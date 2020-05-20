/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asomain;

/**
 *
 * @author Umit Kilic
 */
public class members {
    
    public int[] pos;
    public double mov_based_on_current_pos;
    public double mov_based_on_other_pos;
    public double mov_based_on_past_pos;
    public double fitness;
    public int[] p_pos;
    public double p_fitness=0;
    
    public members(int numofattr){
        this.pos=new int[numofattr];
        this.p_pos=this.pos;
    }
}
