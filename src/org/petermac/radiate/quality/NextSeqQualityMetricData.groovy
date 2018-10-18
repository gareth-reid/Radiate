package org.petermac.radiate.quality

import org.petermac.radiate.SeqMetrics
import org.petermac.radiate.ToolBox

import java.nio.Buffer

/**
 * Created by reid gareth on 17/12/2014.
 */

public class NextSeqQualityMetricData extends SeqMetrics<QualityMetric> {

    private BigDecimal readFourBytesToInt(BufferedReader br){
        def fourByteArray = new byte[4]
        for (int i = 0; i < 4; i++) {
            fourByteArray[i] = br.read()
        }
        return _toolBox.GetInt32(fourByteArray)
    }
    public QualityMetric Execute(path)
    {
        String binPath = path + "/InterOp/QMetricsOut.bin"
        BufferedReader reader = LoadBuffered(binPath)

        def version = reader.read()
        def ba = new byte[1]
        ba[0] = reader.read()
        def lengthOfEachRecord = _toolBox.GetInt8(ba)
        def binning = reader.read()

        if (binning == 1){
            def numOfBins = reader.read()
            ReadIntegersFromBuffers(numOfBins * 3, reader)
        }


        //TODO get nchars
        int records = 8192 / (200 * 8)//_toolBox.GetInt8(Arrays.copyOfRange(array, 1, 2) ) * 8
        def qualityMetric = new QualityMetric(Records: 0)
        //int records = 0
        def leftOverDataInEachRecord = lengthOfEachRecord - 200
        for (int j = 0; j < records; j++) {
            try {
                qualityMetric.LaneNumber = reader.read()
                qualityMetric.TileNumber = reader.read()
                qualityMetric.CycleNumber = reader.read()
                for (int i = 1; i < 51; i++) {
                    qualityMetric.QScores.set(i - 1, readFourBytesToInt(reader)
                            + getScore(i - 1))
                }
                //readInts(leftOverDataInEachRecord, reader)
               // records++
                //j += records
                int w = 0
            }
            catch (Exception e) {
                break
            }
        }

        qualityMetric.Records = records
        for (int i = 0; i < 50; i++){
            qualityMetric.QScores.set(i, (int)qualityMetric.QScores.get(i)  / qualityMetric.Records)
        }
        return qualityMetric
    }

    public QualityMetric Parse(int start, byte[] array)
    {
        return new QualityMetric()
    }


    public BigDecimal getScore(int i){
        return qualityMetric.QScores.get(i)
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



