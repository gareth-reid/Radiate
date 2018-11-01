package tests

import junit.framework.Assert
import org.junit.Test
import org.petermac.radiate.extraction.MiSeqExtractionMetricData
import org.petermac.radiate.index.MiSeqIndexMetricData
import org.petermac.radiate.quality.MiSeqQualityMetricData
import org.petermac.radiate.quality.NextSeqQualityMetricData
import org.petermac.radiate.quality.QualityMetric

/**
 * Created by reid gareth on 7/01/2015.
 */
public class qualityTests
{
    //simple tests to run for debugging and check code not broken
    @Test
    public void QuailtyMiSeqTest()
    {
        def quality = new MiSeqQualityMetricData()
        def data = quality.Execute("/Volumes/bioinf_pipeline/Runs/MiSeq/181011_M01053_0785_000000000-C4LP2/")
        Assert.assertNotNull(data)
    }

    @Test
    public void QuailtyNextSeqTest()
    {
        def quality = new NextSeqQualityMetricData()
        def data = quality.Execute("/Volumes/bioinf_pipeline/Runs/NextSeq/181015_NS500817_0404_AH3HNJBGX9/")
        Assert.assertNotNull(data)
    }

    @Test
    public void QuailtyMiSeq_ScoreAbove30Test()
    {
        def quality = new MiSeqQualityMetricData()
        def data = quality.Execute("/Volumes/bioinf_pipeline/Runs/MiSeq/180919_M01053_0774_000000000-C4MP5/")
        def result = data.PercentageOfScoresAbove(30)
        Assert.assertNotNull(result)
    }

}
