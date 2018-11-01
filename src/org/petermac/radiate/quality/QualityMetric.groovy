package org.petermac.radiate.quality

//import groovy.transform.TypeChecked

/**
 * Created by reid gareth on 7/01/2015.
 */
//@TypeChecked
public class QualityMetric
{
    public QualityMetric()
    {
        QScores = new LinkedList<Integer>()
        for (int i = 0; i < 50; i++){
            QScores.add(0)
        }
    }
    public String TileNumber
    public String LaneNumber
    public String CycleNumber
    public String Sequencer
    public int Records

    public List<BigDecimal> QScores

    public int ArrayPointer

    public Double PercentageOfScoresAbove(int numberAbove = 30)
    {
        try {
            int totalClusters = QScores.sum()
            int clustersAbove = QScores[numberAbove - 1..QScores.size() - 1].sum()
            return ((Double) ((clustersAbove / totalClusters)) * 100).round(1)
        } catch(Exception e){
            return 0
        }
    }



}
