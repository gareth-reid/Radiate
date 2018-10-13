package org.petermac.radiate

import org.petermac.radiate.index.IndexMetric

/**
 * Created by reid gareth on 7/01/2015.
 */
public abstract class MiSeqMetrics<T>
{
    public abstract T Parse(int start, byte[] array)
    public byte[] Load(String path)
    {
        def file = new File(path)
        if(!file.exists())
        {
            return new byte[0] //file not arrived
        }
        def stream = file.newDataInputStream()
        return _toolBox.ReadFromStream(stream)

    }
}
