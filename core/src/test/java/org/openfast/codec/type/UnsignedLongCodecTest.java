package org.openfast.codec.type;

import java.nio.ByteBuffer;
import org.openfast.Global;
import org.openfast.codec.LongCodec;
import org.openfast.error.ErrorHandler;
import org.openfast.error.FastConstants;
import org.openfast.error.FastException;
import org.openfast.test.OpenFastTestCase;

public class UnsignedLongCodecTest extends OpenFastTestCase {
    LongCodec codec = new UnsignedLongCodec();
    ByteBuffer buffer = ByteBuffer.allocate(32);
    
    public void testBoundaries() {
        assertEquals(Long.MAX_VALUE, decode("01111111 01111111 01111111 01111111 01111111 01111111 01111111 01111111 11111111"));
        assertEquals("01111111 01111111 01111111 01111111 01111111 01111111 01111111 01111111 11111111", encode(Long.MAX_VALUE));
    }
    
    public void testOverlong() {
        try {
            decode("00000000 11000000");
            fail();
        } catch (FastException e) {
            assertEquals(FastConstants.R6_OVERLONG_INT, e.getCode());
        }
    }
    
    public void testOverlongIgnored() {
        Global.setErrorHandler(ErrorHandler.NULL);
        assertEquals(64, decode("00000000 11000000"));
        Global.setErrorHandler(ErrorHandler.DEFAULT);
    }
    
    public void testDecode() {
        assertEquals(1, decode("10000001"));
        assertEquals(2, decode("10000010"));
    }

    public void testEncode() {
        assertEquals("10000001", encode(1));
        assertEquals("10000010", encode(2));
    }

    private long decode(String bits) {
        return codec.decode(buffer(bits));
    }

    private ByteBuffer encode(long value) {
        buffer.clear();
        codec.encode(buffer, value);
        return buffer;
    }
}
