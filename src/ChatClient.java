import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Student on 23.04.2015.
 */
public class ChatClient {
    public static void main(String[] args) throws Exception {
        Socket socket = null;
        try {
            socket = new Socket("localhost",27015);
            PrintStream msg = new PrintStream(socket.getOutputStream());
            msg.println("Nick");
            msg.flush();
        }
        catch (IOException e)
        {
            System.out.println("Error: " + e);
        }
    }
}
