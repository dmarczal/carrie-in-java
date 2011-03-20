package br.ufpr.c3sl.deepClone;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.Inflater;

import org.apache.commons.io.IOUtils;

public class Compressor{
   
	public static byte[] compress(byte[] content) throws IOException{
       
        // First Compressiong
        Deflater compressor = new Deflater();
        compressor.setLevel(Deflater.BEST_COMPRESSION);
        
        compressor.setInput(content);
        compressor.finish();
        
        ByteArrayOutputStream bos = new ByteArrayOutputStream(content.length);

        byte[] buf = new byte[1024];
        while (!compressor.finished()) {
          int count = compressor.deflate(buf);
          bos.write(buf, 0, count);
        }
        
        bos.close();
        
        byte[] compressedData = bos.toByteArray();	
		
		//Second Compression
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try{
            GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            gzipOutputStream.write(compressedData);
            gzipOutputStream.close();
        } catch(IOException e){
            throw new RuntimeException(e);
        }
        System.out.printf("Compression ratio %f\n", (1.0f * content.length/byteArrayOutputStream.size()));
        
        byte[] compressedAgain = byteArrayOutputStream.toByteArray();
        
        return compressedAgain;
    }

	
    public static byte[] decompress(byte[] contentBytes) throws DataFormatException, IOException{
    	//First decompression
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try{
            IOUtils.copy(new GZIPInputStream(new ByteArrayInputStream(contentBytes)), out);
        } catch(IOException e){
            throw new RuntimeException(e);
        }
        
        byte[] decompressedData = out.toByteArray();
    	
    	Inflater decompressor = new Inflater();
        decompressor.setInput(decompressedData);
        ByteArrayOutputStream bos = new ByteArrayOutputStream(decompressedData.length);
        byte[] buf = new byte[1024];
        while (!decompressor.finished()) {
          int count = decompressor.inflate(buf);
          bos.write(buf, 0, count);

        }
        bos.close();
        
        byte[] decompressedAgain = bos.toByteArray();
    	
        return decompressedAgain;
    }

    public static boolean notWorthCompressing(String contentType){
        return contentType.contains("jpeg")
                || contentType.contains("pdf")
                || contentType.contains("zip")
                || contentType.contains("mpeg")
                || contentType.contains("avi");
    }
}

