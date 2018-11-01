package tests

import junit.framework.Assert
import org.junit.Test
import org.petermac.radiate.quality.MiSeqQualityMetricData
import org.petermac.radiate.quality.NextSeqQualityMetricData
import org.petermac.radiate.quality.QualityMetric

/**
 * Created by reid gareth on 7/01/2015.
 */
public class QualityScoresAboveTests
{
    @Test
    public void QuailtyMiSeq_ScoreAboveTest_OneMinIn_Expect100()
    {
        QualityMetric data = new QualityMetric()
        data.QScores = QScoresValueInSixPosition()
        def result = data.PercentageOfScoresAbove(1)
        Assert.assertEquals(100.0.toString(), result.toString())
    }

    @Test
    public void QuailtyMiSeq_ScoreAboveTest_FiftyMaxIn_Expect0()
    {
        QualityMetric data = new QualityMetric()
        data.QScores = QScoresValueInSixPosition()
        def result = data.PercentageOfScoresAbove(50)
        Assert.assertEquals(0.0.toString(), result.toString())
    }

    @Test
    public void QuailtyMiSeq_ScoreAboveTest_ExactValueSixIn_Expect100()
    {
        QualityMetric data = new QualityMetric()
        data.QScores = QScoresValueInSixPosition()
        def result = data.PercentageOfScoresAbove(6)
        Assert.assertEquals(100.0.toString(), result.toString())
    }

    @Test
    public void QuailtyMiSeq_ScoreAboveTest_OneLessExactValueFiveIn_Expect100()
    {
        QualityMetric data = new QualityMetric()
        data.QScores = QScoresValueInSixPosition()
        def result = data.PercentageOfScoresAbove(5)
        Assert.assertEquals(100.0.toString(), result.toString())
    }

    @Test
    public void QuailtyMiSeq_ScoreAboveTest_OneMoreExactValueSevenIn_ExpectZero()
    {
        QualityMetric data = new QualityMetric()
        data.QScores = QScoresValueInSixPosition()
        def result = data.PercentageOfScoresAbove(7)
        Assert.assertEquals(0.0.toString(), result.toString())
    }

    @Test
    public void QuailtyMiSeq_ScoreAboveTest_HigherValueTenIn_ExpectZero()
    {
        QualityMetric data = new QualityMetric()
        data.QScores = QScoresValueInSixSevenEightNinePosition()
        def result = data.PercentageOfScoresAbove(10)
        Assert.assertEquals(0.0.toString(), result.toString())
    }

    @Test
    public void QuailtyMiSeq_ScoreAboveTest_LowerValueOneIn_Expect100()
    {
        QualityMetric data = new QualityMetric()
        data.QScores = QScoresValueInSixSevenEightNinePosition()
        def result = data.PercentageOfScoresAbove(1)
        Assert.assertEquals(100.0.toString(), result.toString())
    }

    @Test
    public void QuailtyMiSeq_ScoreAboveTest_MidValueSevenIn_Expect100()
    {
        QualityMetric data = new QualityMetric()
        data.QScores = QScoresValueInSixSevenEightNinePosition()
        def result = data.PercentageOfScoresAbove(7)
        Assert.assertEquals(85.7.toString(), result.toString())
    }

    @Test
    public void QuailtyMiSeq_ScoreAboveTest_RoundUp_MidValueEightIn_Expect64_3()
    {
        QualityMetric data = new QualityMetric()
        data.QScores = QScoresValueInSixSevenEightNinePosition()
        def result = data.PercentageOfScoresAbove(8)
        Assert.assertEquals(64.3.toString(), result.toString())   //64.28 = 64.3
    }

    @Test
    public void QuailtyMiSeq_ScoreAboveTest_RoundUp_MidValueEightIn_Expect85_6()
    {
        QualityMetric data = new QualityMetric()
        data.QScores = QScoresForPointFiveRound()
        def result = data.PercentageOfScoresAbove(6)
        Assert.assertEquals(85.6.toString(), result.toString())   //85.555 = 85.6
    }

    //77/90 = 85.5555

    public Double[] QScoresValueInSixPosition() {
        return [ 0,0,0,0,0,20,0,0,0,0,
                 0,0,0,0,0,0,0,0,0,0,
                 0,0,0,0,0,0,0,0,0,0,
                 0,0,0,0,0,0,0,0,0,0,
                 0,0,0,0,0,0,0,0,0,0]

    }

    public Double[] QScoresValueInSixSevenEightNinePosition() {
        return [ 0,0,0,0,0,20,30,40,50,0,
                 0,0,0,0,0,0,0,0,0,0,
                 0,0,0,0,0,0,0,0,0,0,
                 0,0,0,0,0,0,0,0,0,0,
                 0,0,0,0,0,0,0,0,0,0]

    }

    public Double[] QScoresForPointFiveRound() {
        return [ 0,0,0,0,13,0,0,70,7,0,0,
                 0,0,0,0,0,0,0,0,0,0,
                 0,0,0,0,0,0,0,0,0,0,
                 0,0,0,0,0,0,0,0,0,0,
                 0,0,0,0,0,0,0,0,0,0]

    }
}
