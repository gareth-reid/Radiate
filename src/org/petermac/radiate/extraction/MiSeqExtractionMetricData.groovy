package org.petermac.radiate.extraction

import org.petermac.radiate.SeqMetrics
import org.petermac.radiate.ToolBox
import org.petermac.radiate.index.IndexMetric
import org.petermac.radiate.quality.QualityMetric

/**
 * Created by reid gareth on 17/12/2014.
 */

public class MiSeqExtractionMetricData extends SeqMetrics<ExtractionMetric> {

    public List<IndexMetric> Execute(path)
    {
        String binPath = path + "/InterOp/ExtractionMetricsOut.bin"
        def extractionMetrics = new LinkedList<ExtractionMetric>()
        int pointer = 2 //start
        byte[] array = Load(binPath)
        if(array.length == 0){
            return new LinkedList<ExtractionMetric>()
        }

        while (true)
        {
            try
            {
                def extractionMetric = Parse(pointer ,array)
                pointer = extractionMetric.ArrayPointer
                extractionMetrics.add(extractionMetric)
            }
            catch (Exception e)
            {
                break
            } //end

        }
        return extractionMetrics
    }

    public ExtractionMetric Parse(int start, byte[] arraym)
    {
        def extractionMetric = new ExtractionMetric()
        int arrayPointer = start

        //lane
        extractionMetric.LaneNumber = _toolBox.GetInt16(Arrays.copyOfRange(array, arrayPointer, arrayPointer + 2))
        arrayPointer += 2
        //tile
        extractionMetric.TileNumber = _toolBox.GetInt16(Arrays.copyOfRange(array, arrayPointer, arrayPointer + 2))
        arrayPointer += 2
        //cycle
        extractionMetric.CycleNumber = _toolBox.GetInt16(Arrays.copyOfRange(array, arrayPointer, arrayPointer + 2))
        arrayPointer += 2

        //fwhm scores
        def baseSetFwhm = new BaseSet()
        baseSetFwhm.A = _toolBox.GetInt16(Arrays.copyOfRange(array, arrayPointer, arrayPointer + 4))
        arrayPointer += 4
        baseSetFwhm.C = _toolBox.GetInt16(Arrays.copyOfRange(array, arrayPointer, arrayPointer + 4))
        arrayPointer += 4
        baseSetFwhm.G = _toolBox.GetInt16(Arrays.copyOfRange(array, arrayPointer, arrayPointer + 4))
        arrayPointer += 4
        baseSetFwhm.T = _toolBox.GetInt16(Arrays.copyOfRange(array, arrayPointer, arrayPointer + 4))
        arrayPointer += 4

        extractionMetric.FwhmScores = baseSetFwhm

        //intensities
        def baseSetIntensities = new BaseSet()
        baseSetIntensities.A = _toolBox.GetInt16(Arrays.copyOfRange(array, arrayPointer, arrayPointer + 2))
        arrayPointer += 2
        baseSetIntensities.C = _toolBox.GetInt16(Arrays.copyOfRange(array, arrayPointer, arrayPointer + 2))
        arrayPointer += 2
        baseSetIntensities.G = _toolBox.GetInt16(Arrays.copyOfRange(array, arrayPointer, arrayPointer + 2))
        arrayPointer += 2
        baseSetIntensities.T = _toolBox.GetInt16(Arrays.copyOfRange(array, arrayPointer, arrayPointer + 2))
        arrayPointer += 2

        extractionMetric.Intensities = baseSetIntensities

        extractionMetric.ArrayPointer = arrayPointer + 8//ignore date for now

        //timestamp
        def bit = Integer.toBinaryString(array[arrayPointer] & 0xFF)

        //SetBit(array[arrayPointer + 1], 0)

        return extractionMetric

    }
}

//Format:
//   byte 0: file version number (2)
//   byte 1: length of each record
//   bytes (N * 38 + 2) - (N *38 + 39): record:
//     2 bytes: lane number (uint16)
//     2 bytes: tile number (uint16)
//     2 bytes: cycle number (uint16)
//     4 x 4 bytes: fwhm scores (float) for channel [A, C, G, T] respectively
//     2 x 4 bytes: intensities (uint16) for channel [A, C, G, T] respectively
//     8 bytes: date/time of CIF creation --> serialized C# datetime object
//   ...Where N is the record index


