import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutionException;

/**
 * Created by Student on 23.04.2015.
 */
public class ChatServer {
    private JPanel Panel;
    private JButton Button;
    private JList UserList;

    public ChatServer() {
        Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public void SetUserList(String[] userList)
    {
        UserList = new JList(userList);
    }

    public static void main(String[] args) throws Exception {
        ChatServer chatServer = new ChatServer();
        JFrame frame = new JFrame("ChatServer");
        frame.setContentPane(chatServer.Panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        Socket socket = null;
        try {
            ServerSocket serverSocket = new ServerSocket(27015);
            socket = serverSocket.accept();
            BufferedReader msg = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //chatServer.SetUserList(socket.getInetAddress().toString() + " " + msg.readLine());
            socket.close();
        }
        catch (IOException e)
        {
            System.out.println("Error: " + e);
        }
    }
}
