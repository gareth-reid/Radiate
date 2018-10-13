package tests

import junit.framework.Assert
import org.junit.Test
import org.petermac.radiate.extraction.MiSeqExtractionMetricData
import org.petermac.radiate.quality.MiSeqQualityMetricData

/**
 * Created by reid gareth on 7/01/2015.
 */
public class qualityTests
{
    @Test
    public void qualityTest()
    {
        def quality = new MiSeqQualityMetricData()
        def data = quality.Execute("/Volumes/Clarity/Runs/MiSeq/141124_M01053_0149_000000000-ACFRM/")
        Assert.assertNotSame(0, data.size())
    }
}
