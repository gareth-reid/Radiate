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
    }
    public String TileNumber
    public String LaneNumber
    public String CycleNumber

    public List<BigDecimal> QScores

    public int ArrayPointer

    public void IncrementWithPrevious(QualityMetric qm)
    {
        List<BigDecimal> tmpQscores = new LinkedList<Integer>()
        for (int i = 0; i < qm.QScores.size(); i++){
            def thisOne = QScores.get(i)
            def prevOne = qm.QScores.get(i)
            tmpQscores.add(thisOne + prevOne)
        }
        QScores = tmpQscores
    }



}
