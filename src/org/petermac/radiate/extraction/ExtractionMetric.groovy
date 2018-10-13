package org.petermac.radiate.extraction

//import groovy.transform.TypeChecked

/**
 * Created by reid gareth on 7/01/2015.
 */
//@TypeChecked
public class ExtractionMetric
{
    public String TileNumber
    public String LaneNumber
    public String CycleNumber
    public BaseSet FwhmScores
    public BaseSet Intensities
    public int ArrayPointer


}

public class BaseSet
{
    public String A
    public String C
    public String G
    public String T
}
