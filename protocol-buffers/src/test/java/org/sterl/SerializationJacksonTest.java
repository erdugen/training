package org.sterl;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;

import org.junit.Test;
import org.sterl.jackson.JacksonType;
import org.sterl.jackson.RequestMessage;
import org.sterl.jackson.RequestMessage.JumpData;
import org.sterl.jackson.RequestMessage.MoveData;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SerializationJacksonTest extends AbstractTest {

    ObjectMapper mapper = new ObjectMapper();
    
    @Test
    public void serlialization() throws Exception {
        
        RequestMessage read = null;
        for (int i = 0; i < CYCLES; i++) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            this.buildMessage.start();
            RequestMessage message = new RequestMessage();
            message.setType(JacksonType.JUMP);
            message.setJumpData(new JumpData(10, 650));
            message.setMoveData(new MoveData(500));
            this.buildMessage.stop();
            
            this.serialization.start();
            mapper.writeValue(out, message);
            this.serialization.stop();
            
            byte[] msg = out.toByteArray();
            this.deserialization.start();
            read = mapper.readValue(msg, RequestMessage.class);
            this.deserialization.stop();
            
            messageSize = msg.length;
            assertEquals(message, read);
        }

        writeStats("Jackson Statistics");
    }
}
