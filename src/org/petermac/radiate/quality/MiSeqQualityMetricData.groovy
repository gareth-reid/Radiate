package org.petermac.radiate.quality

import org.petermac.radiate.MiSeqMetrics
import org.petermac.radiate.ToolBox
import org.petermac.radiate.extraction.BaseSet
import org.petermac.radiate.extraction.ExtractionMetric
import org.petermac.radiate.index.IndexMetric

/**
 * Created by reid gareth on 17/12/2014.
 */

public class MiSeqQualityMetricData extends MiSeqMetrics<QualityMetric>
{
    private ToolBox _toolBox = new ToolBox()

    public List<QualityMetric> Execute(path)
    {
        String binPath = path + "/InterOp/QMetricsOut.bin"
        def qualityMetrics = new LinkedList<QualityMetric>()
        int pointer = 2 //start
        byte[] array = Load(binPath)
        if(array.length == 0){
            return new LinkedList<QualityMetric>()
        }
        int records = array.size() / _toolBox.GetInt8(Arrays.copyOfRange(array, 1, 2) ) * 8
        for(int j = 0; j < records; j++)
        {
            try
            {
                def qm = Parse(pointer ,array)
                if(j > 0) {//first one}
                    qm.IncrementWithPrevious(qualityMetrics.get(j - 1)) //add to previous
                }
                pointer = qm.ArrayPointer
                qualityMetrics.add(qm)
            }
            catch (Exception e)
            {
                break
            } //end
        }
        return qualityMetrics
    }

    public QualityMetric Parse(int start, byte[] array)
    {
        def qualityMetrics = new QualityMetric()
        int arrayPointer = start

        //lane
        qualityMetrics.LaneNumber = _toolBox.GetInt16(Arrays.copyOfRange(array, arrayPointer, arrayPointer + 2))
        arrayPointer += 2
        //tile
        qualityMetrics.TileNumber = _toolBox.GetInt16(Arrays.copyOfRange(array, arrayPointer, arrayPointer + 2))
        arrayPointer += 2
        //cycle
        qualityMetrics.CycleNumber = _toolBox.GetInt16(Arrays.copyOfRange(array, arrayPointer, arrayPointer + 2))
        arrayPointer += 2

        for (int i = 0; i < 50; i++)
        {
            qualityMetrics.QScores.add(_toolBox.GetInt32(Arrays.copyOfRange(array, arrayPointer, arrayPointer + 4)))
            arrayPointer += 4
        }
        qualityMetrics.ArrayPointer = arrayPointer
        return qualityMetrics

    }
}

//# QualityMetrics format according to ILMN specs:
//#
//#   byte 0: file version number (4)
//#   byte 1: length of each record
//#   bytes (N * 206 + 2) - (N * 206 + 207): record:
//#       2 bytes: lane number (uint16)
//#       2 bytes: tile number (uint16)
//#       2 bytes: cycle number (uint16)
//#       4 x 50 bytes: number of clusters assigned score (uint32) Q1 through Q50



