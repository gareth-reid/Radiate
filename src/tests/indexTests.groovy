package tests

import junit.framework.Assert
import org.junit.Test
import org.petermac.radiate.index.MiSeqIndexMetricData

/**
 * Created by reid gareth on 7/01/2015.
 */
public class indexTests
{
    @Test
    public void IndexTest()
    {
        def index = new MiSeqIndexMetricData()
        def data = index.Execute("/Volumes/bioinf_pipeline/Runs/MiSeq/181026_M00139_0384_000000000-C5M2H/")
        //def data = index.Execute("/Users/File/NextSeqInterOp/")
        Assert.assertNotSame(0, data.size())
    }

    @Test
    public void IndexTest_GetValue()
    {
        def index = new MiSeqIndexMetricData()
        def result = index.GetValue(987, 3000)
        Assert.assertNotSame(0, result)
    }

}
