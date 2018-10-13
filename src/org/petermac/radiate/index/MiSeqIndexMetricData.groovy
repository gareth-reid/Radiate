package org.petermac.radiate.index

import org.petermac.radiate.MiSeqMetrics
import org.petermac.radiate.ToolBox

/**
 * Created by reid gareth on 17/12/2014.
 */

public class MiSeqIndexMetricData extends MiSeqMetrics<IndexMetric>
{
    private ToolBox _toolBox = new ToolBox()

    public List<IndexMetric> Execute(path)
    {
        String binPath = path + "/InterOp/IndexMetricsOut.bin"
        def indexMetrics = new LinkedList<IndexMetric>()
        int pointer = 1 //start (version)
        byte[] array = Load(binPath)
        if(array.length == 0){
            return new LinkedList<IndexMetric>()
        }

        while (true)
        {
            try {
                def i = Parse(pointer, array)
                pointer = i.ArrayPointer
                indexMetrics.add(i)
            }
            catch (Exception e)
            {
                //end
                break
            }
        }
        def uniqueSamples = indexMetrics.unique { it.SampleName }
        return uniqueSamples.sort { it.SampleName }
    }

    public IndexMetric Parse(int start, byte[] array)
    {
        def indexMetric = new IndexMetric()
        int arrayPointer = start

        //lane
        indexMetric.Lane = _toolBox.GetInt16(Arrays.copyOfRange(array, arrayPointer, arrayPointer + 2))
        arrayPointer += 2
        //tile
        indexMetric.Tile = _toolBox.GetInt32(Arrays.copyOfRange(array, arrayPointer, arrayPointer + 4))
        arrayPointer += 4
        //read
        indexMetric.Read = _toolBox.GetInt16(Arrays.copyOfRange(array, arrayPointer, arrayPointer + 2))
        arrayPointer += 2

        //index
        def lengthOfIndexSection = _toolBox.GetInt16(Arrays.copyOfRange(array, arrayPointer, arrayPointer + 2))
        arrayPointer += 2
        indexMetric.Index = _toolBox.GetString(array, arrayPointer, lengthOfIndexSection)
        //10 = index length (lengthOfIndexSection)
        arrayPointer += lengthOfIndexSection

        //clusters - 4 bytes
        indexMetric.Clusters = _toolBox.GetInt64(Arrays.copyOfRange(array, arrayPointer, arrayPointer + 8))
        arrayPointer += 8

        //sample name
        short lengthOfSampleNameSection = (short) (((array[arrayPointer] & 0xFF)) | (array[arrayPointer + 1] & 0xFF));
        arrayPointer += 2
        indexMetric.SampleName = _toolBox.GetString(array, arrayPointer, lengthOfSampleNameSection)
        arrayPointer += lengthOfSampleNameSection

        //project
        short lengthOfSampleProjectNameSection = (short) (((array[arrayPointer] & 0xFF)) | (array[arrayPointer + 1] & 0xFF));
        arrayPointer += 2
        indexMetric.SampleProject = _toolBox.GetString(array, arrayPointer, lengthOfSampleProjectNameSection)
        arrayPointer += lengthOfSampleProjectNameSection
        //update current pointer
        indexMetric.ArrayPointer = arrayPointer

        return indexMetric
    }


}

//Reports the indexes count. Format:
//   Byte 0: file version (1)
//   Bytes( variable length): record:
//   2 bytes: lane number(unint16)
//   2 bytes: tile number(unint16)
//   2 bytes: read number(unint16)
//   2 bytes: number of bytes Y for index name(unint16)
//   Y bytes: index name string (string in UTF8Encoding)
//   4 bytes: num of clusters identified as index (uint32)
//   2 bytes: number of bytes V for sample name(unint16)
//   V bytes: sample name string (string in UTF8Encoding)
//       2 bytes: number of bytes W for sample project(unint16)
//   W bytes: sample project string (string in UTF8Encoding)

