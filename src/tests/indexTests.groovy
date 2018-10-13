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
        def data = index.Execute("/Volumes/bioinf_pipeline/Runs/MiSeq/180516_M01053_0713_000000000-BRB4C/")
        //def data = index.Execute("/Users/File/NextSeqInterOp/")
        Assert.assertNotSame(0, data.size())
    }
}
