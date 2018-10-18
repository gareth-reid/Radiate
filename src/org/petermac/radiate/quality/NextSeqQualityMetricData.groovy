package org.petermac.radiate.quality

import org.petermac.radiate.SeqMetrics
import org.petermac.radiate.ToolBox

import java.nio.Buffer

/**
 * Created by reid gareth on 17/12/2014.
 */

//Not fully accurate yet - nedd to read in byte array length but is is overflowing memory
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


        //TODO get nchars and refactor into parse method
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
//# v5 QualityMetrics format of NextSeq, HiSeq X, and HiSeq instruments running RTA v1.18.64 and newer
//# according to ILMN specs :
//# byte 0: file version number (5)
//#   byte 1: length of each record
//#   byte 2: quality score binning (byte flag representing if binning was on)
//#   if (byte 2 == 1) // quality score binning on
//#       byte 3: number of quality score bins, B
//#       bytes 4 – (4+B-1): lower boundary of quality score bins
//#       bytes (4+B) – (4+2*B-1): upper boundary of quality score bins
//#       bytes (4+2*B) – (4+3*B-1): remapped scores of quality score bins
//#   bytes (N * 206 + 2) - (N *206 + 207): record:
//#       2 bytes: lane number (uint16)
//#       2 bytes: tile number (uint16)
//#       2 bytes: cycle number (uint16)
//#       4 x 50 bytes: number of clusters assigned score (uint32) Q1 through Q50



