package src;

import com.fazecast.jSerialComm.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class SerialJSC {
    SerialPort comPort;

    public void initialize() {
        this.comPort = SerialPort.getCommPorts()[0];
        this.comPort.openPort();
        this.comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
    }

    public synchronized void sendInitialMessage (int length) {
        comPort.writeBytes(new byte[]{116}, 1);
        comPort.writeBytes(new byte[]{(byte) length}, new byte[]{(byte) length}.length);

        byte[] response = new byte[1024];
        response[0] = 0;
        while (response[0] != 127) {
            int numRead = comPort.readBytes(response, response.length);
            System.out.println("Response: " + new String(response, StandardCharsets.US_ASCII));
        }
    }

    public synchronized void sendMessage (byte[] message) {
        comPort.writeBytes(message, message.length);

        byte[] response = new byte[1024];
        response[0] = 0;
        while (response[0] != 127) {
            int numRead = comPort.readBytes(response, response.length);
            System.out.println("Response: " + new String(response, StandardCharsets.US_ASCII));
        }
    }
}
