package org.petermac.radiate.index

//import groovy.transform.TypeChecked

/**
 * Created by reid gareth on 7/01/2015.
 */
//@TypeChecked
public class IndexMetric
{
    public int ArrayPointer
    public int Clusters
    public String SampleName
    public String Index
    public String SampleProject
    public Date Date
    public String Lane
    public String Tile
    public String Read
    public String Sequencer
    public String SampleNameAndIndex
    {

        SampleName + " (${Index})"
    }
}
