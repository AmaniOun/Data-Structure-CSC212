
package com.mycompany.inventoryandordersystem1;


public class PQ_Element<T> {

 
    public T data;
    float priority;
        
    public PQ_Element(T e, float pr){
                   data = e;
                   priority = pr;
   }
}
    