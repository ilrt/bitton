/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ilrt.wf.facets.freemarker;

import freemarker.template.ObjectWrapper;
import freemarker.template.SimpleCollection;

/**
 *
 * @author cmcpb
 */
public class ExtendedSimpleCollection {
    private int size = 0;
    private SimpleCollection collection;
            
    public ExtendedSimpleCollection(java.util.Collection collection, ObjectWrapper wrapper) 
    {
        this.collection = new SimpleCollection(collection, wrapper);
        this.size = collection.size();
    }
    
    public int getSize() { return size; }
    public SimpleCollection getCollection() { return collection; }
}
