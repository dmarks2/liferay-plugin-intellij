package de.dm.intellij.liferay.scripts;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class GogoShellClient implements AutoCloseable {

    private final Socket socket;
    private final DataInputStream inputStream;
    private final DataOutputStream outputStream;

    public GogoShellClient(String host, int port) throws IOException {
        this.socket = new Socket(host, port);

        this.inputStream = new DataInputStream(socket.getInputStream());
        this.outputStream = new DataOutputStream(socket.getOutputStream());

        initialHandshake();
    }

    public void close() {
        try {
            socket.close();
        }
        catch (IOException ioException) {
        }
    }

    public String send(String command) throws IOException {
        byte[] bytes = command.getBytes();

        int[] codes = new int[bytes.length + 2];

        for (int i = 0; i < bytes.length; i++) {
            codes[i] = bytes[i];
        }

        codes[bytes.length] = '\r';
        codes[bytes.length + 1] = '\n';

        sendCommand(codes);

        return readUntilNextGogoPrompt();
    }

    private void initialHandshake() throws IOException {

        readCommand();
        readCommand();
        readCommand();
        readCommand();

        sendCommand(255, 251, 24);

        readCommand();

        sendCommand(255, 250, 24, 0, 'V', 'T', '2', '2', '0', 255, 240);

        readUntilNextGogoPrompt();
    }

    private int[] readCommand() throws IOException {
        List<Integer> bytes = new ArrayList<>();

        int iac = inputStream.read();
        bytes.add(iac);

        int second = inputStream.read();
        bytes.add(second);

        if (second == 250) {
            int option = inputStream.read();

            bytes.add(option);
            int code = inputStream.read();

            bytes.add(code);

            if (code == 0) {
                throw new IllegalStateException();
            }
            else if (code == 1) {
                iac = inputStream.read();
                bytes.add(iac);

                int se = inputStream.read();
                bytes.add(se);
            }
        }
        else {
            bytes.add(inputStream.read());
        }

        return bytes.stream().mapToInt(i->i).toArray();
    }

    private String readUntilNextGogoPrompt() throws IOException {
        StringBuilder sb = new StringBuilder();

        int c = inputStream.read();

        while (c != -1) {
            sb.append((char)c);

            String str = sb.toString();

            if (str.endsWith("g! ")) {
                break;
            }

            c = inputStream.read();
        }

        String output = sb.substring(0, sb.length() - 3);
        return output.trim();
    }

    private void sendCommand(int... codes) throws IOException {
        for (int code : codes) {
            outputStream.write(code);
        }
    }
}
