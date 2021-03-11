package gexabyte.test.java.util;

import org.apache.axis.encoding.Base64;

import java.nio.charset.StandardCharsets;

public class EncodeDecodeTool {

    public static String encodeString(String text){
        byte[] textBytes = text.getBytes(StandardCharsets.UTF_8);
        return Base64.encode(textBytes);
    }
    public static String decodeString(String text){
        byte[] textBytes = Base64.decode(text);
        return new String(textBytes, StandardCharsets.UTF_8);
    }
}
