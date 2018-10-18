package org.petermac.radiate.quality

import org.petermac.radiate.SeqMetrics
import org.petermac.radiate.ToolBox
import org.petermac.radiate.extraction.BaseSet
import org.petermac.radiate.extraction.ExtractionMetric
import org.petermac.radiate.index.IndexMetric

/**
 * Created by reid gareth on 17/12/2014.
 */

public class MiSeqQualityMetricData extends SeqMetrics<QualityMetric> {
    private QualityMetric _qualityMetric
    public MiSeqQualityMetricData(){
        _qualityMetric = new QualityMetric()
    }
    public QualityMetric Execute(path) {
        String binPath = path + "/InterOp/QMetricsOut.bin"

        int pointer = 2 //start
        byte[] array = Load(binPath)
        if (array.length == 0) {
            return new QualityMetric()
        }
        int records = array.length / _toolBox.GetInt8(Arrays.copyOfRange(array, 1, 2)) * 8
        for (int j = 0; j < records; j++) {
            try {
                Parse(pointer, array)
                pointer = _qualityMetric.ArrayPointer
            }
            catch (Exception e) {
                break
            } //end
        }
        _qualityMetric.Records = records
        for (int i = 0; i < 50; i++){
            _qualityMetric.QScores.set(i, (int)_qualityMetric.QScores.get(i)  / _qualityMetric.Records)
        }
        return _qualityMetric
    }

    public QualityMetric Parse(int start, byte[] array) {
        int arrayPointer = start

        //lane
        _qualityMetric.LaneNumber = _toolBox.GetInt16(Arrays.copyOfRange(array, arrayPointer, arrayPointer + 2))
        arrayPointer += 2
        //tile
        _qualityMetric.TileNumber = _toolBox.GetInt16(Arrays.copyOfRange(array, arrayPointer, arrayPointer + 2))
        arrayPointer += 2
        //cycle
        _qualityMetric.CycleNumber = _toolBox.GetInt16(Arrays.copyOfRange(array, arrayPointer, arrayPointer + 2))
        arrayPointer += 2

        for (int i = 1; i < 51; i++) {
            _qualityMetric.QScores.set(i - 1, _toolBox.GetInt32(Arrays.copyOfRange(array, arrayPointer, arrayPointer + 4))
                    + getScore(i - 1))
            arrayPointer += 4
        }
        _qualityMetric.ArrayPointer = arrayPointer
        return _qualityMetric
    }

    public BigDecimal getScore(int i) {
        return _qualityMetric.QScores.get(i)
    }
}
//# V4 QualityMetrics format according to ILMN specs:
//#
//#   byte 0: file version number (4)
//#   byte 1: length of each record
//#   bytes (N * 206 + 2) - (N * 206 + 207): record:
//#       2 bytes: lane number (uint16)
//#       2 bytes: tile number (uint16)
//#       2 bytes: cycle number (uint16)
//#       4 x 50 bytes: number of clusters assigned score (uint32) Q1 through Q50



