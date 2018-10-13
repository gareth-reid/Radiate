package org.petermac.radiate



import java.nio.ByteBuffer
import java.nio.ByteOrder




/**
 * Created by reid gareth on 7/01/2015.
 */
public class ToolBox
{
    public String GetString(byte[] array, int start, int length )
    {
        def str = ""
        //char[] charString = new char[amount]
        for(int i = start; i < start + length; i++)
        {
            str += (char)array[i]
        }
        return str
    }

    public int GetInt32(byte[] bytes) //4 bytes
    {
       //UInt32 unsignedInt = new UInt32(bytes)
        //return unsignedInt.uint32Value()

        return bytes[0] & 0xFF << 8 |
                (bytes[1] & 0xFF) << 16 |
                (bytes[2] & 0xFF) << 24 |
                (bytes[3] & 0xFF) << 32;
    }

    public int GetFloat32(byte[] bytes) //4 bytes
    {
        if (bytes.length == 4) {
            return ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).getFloat();
            //return Float.parseFloat(new String(bytes));
        }

        return 0;
    }



    public int GetInt8(byte[] bytes) //4 bytes
    {
        long value =
                ((bytes[0] & 0xFF) <<  8);
        return value;
    }

    public int GetInt64(byte[] bytes) //6 bytes
    {
        long value =
                ((bytes[0] & 0xFF) <<  8) |
                        ((bytes[1] & 0xFF) <<  16) |
                        ((bytes[2] & 0xFF) << 24) |
                        ((bytes[3] & 0xFF) << 32) |
                        ((bytes[4] & 0xFF) << 40) |
                        ((bytes[5] & 0xFF) << 48) |
                        ((bytes[6] & 0xFF) << 56) |
                        ((bytes[7] & 0xFF) << 64);
        return value;
    }

    public short GetInt16(byte[] bytes) //2 bytes
    {
        def int16 = (((bytes[0] & 0xFF)) | (bytes[1] & 0xFF));
        return int16;
    }

    public byte[] ReadFromStream(InputStream inputStream) throws Exception
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        byte[] data = new byte[4096];
        int count = inputStream.read(data);
        while(count != -1)
        {
            dos.write(data, 0, count);
            count = inputStream.read(data);
        }

        return baos.toByteArray();
    }

    public byte SetBit(byte value, int bit){
        return value|(1<<bit);
    }
}
