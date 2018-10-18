package org.petermac.radiate

import org.petermac.radiate.quality.QualityMetric

import java.nio.file.Files

/**
 * Created by reid gareth on 7/01/2015.
 */
public abstract class SeqMetrics<T>
{
    //Not completed need byte array length but gannot get without overflowing memory
    protected ToolBox _toolBox = new ToolBox()

    protected void ReadIntegersFromBuffers(int amount, BufferedReader br){
        for (int i = 0; i < amount; i++) {
            br.read()
        }
    }

    public abstract T Parse(int start, byte[] array)

    public BufferedReader LoadBuffered(String path)
    {
        //BufferedReader reader = new BufferedReader(new FileReader(path));
        //int count = 0
        //String line;
        //while (reader.read() != null) {
        //    count++
       // }
        //reader.close()
        def bufferedReader = new BufferedReader(new FileReader(path))
        return bufferedReader
    }

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
