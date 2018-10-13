package tests

import junit.framework.Assert
import org.petermac.radiate.extraction.MiSeqExtractionMetricData
import org.junit.Test


/**
 * Created by reid gareth on 7/01/2015.
 */
public class extractionTests
{
    @Test
    public void extractionTest()
    {
        def extraction = new MiSeqExtractionMetricData()
        def data = extraction.Execute("/Users/File")
        Assert.assertNotSame(0, data.size())
    }
}
