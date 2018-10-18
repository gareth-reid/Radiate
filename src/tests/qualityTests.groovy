package tests

import junit.framework.Assert
import org.junit.Test
import org.petermac.radiate.extraction.MiSeqExtractionMetricData
import org.petermac.radiate.index.MiSeqIndexMetricData
import org.petermac.radiate.quality.MiSeqQualityMetricData
import org.petermac.radiate.quality.NextSeqQualityMetricData

/**
 * Created by reid gareth on 7/01/2015.
 */
public class qualityTests
{
    @Test
    public void IndexMiSeqTest()
    {
        def quality = new MiSeqQualityMetricData()
        def data = quality.Execute("/Volumes/bioinf_pipeline/Runs/MiSeq/181011_M01053_0785_000000000-C4LP2/")
        //def data = index.Execute("/Users/File/NextSeqInterOp/")
        Assert.assertNotNull(data)
    }

    @Test
    public void IndexNextSeqTest()
    {
        def quality = new NextSeqQualityMetricData()
        def data = quality.Execute("/Volumes/bioinf_pipeline/Runs/NextSeq/181015_NS500817_0404_AH3HNJBGX9/")
        //def data = index.Execute("/Users/File/NextSeqInterOp/")
        Assert.assertNotNull(data)
    }


}
