import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Vector;

/**
 * Created by Student on 23.04.2015.
 */
public class ChatServer{
    private JPanel Panel;
    private JButton Button;
    private JList UserList;
    private DefaultListModel listModel;
    private static List<Socket> socketList;
    private static int last = 0;

    public ChatServer() {
        listModel = new DefaultListModel();
        socketList = new Vector();
        UserList.setModel(listModel);
        UserList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        UserList.setVisibleRowCount(8);
        Button.setEnabled(false);
        Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = UserList.getSelectedIndex();
                listModel.remove(index);
                try {
                    socketList.get(index).close();
                }
                catch (IOException s)
                {System.out.println("Error: " + s);}
                socketList.remove(index);
                last--;

                int size = listModel.getSize();
                if (size == 0) {
                    Button.setEnabled(false);
                } else {
                    if (index == listModel.getSize()) {
                        index--;
                    }
                    UserList.setSelectedIndex(index);
                    UserList.ensureIndexIsVisible(index);
                }
            }
        });
        UserList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting() == false) {
                    if (UserList.getSelectedIndex() == -1) {
                        Button.setEnabled(false);
                    } else {
                        Button.setEnabled(true);
                    }
                }
            }
        });
    }

    public void AddNewElement(String msg)
    {
        listModel.addElement(msg);
    }

    public static void main(String[] args) throws Exception {
        ChatServer chatServer = new ChatServer();
        JFrame frame = new JFrame("ChatServer");
        frame.setContentPane(chatServer.Panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        boolean done = false;
        ServerSocket serverSocket = new ServerSocket(27015);
        while (!done) {
            try {
                socketList.add(serverSocket.accept());
                last = socketList.size() - 1;
                BufferedReader msg = new BufferedReader(new InputStreamReader(socketList.get(last).getInputStream()));
                chatServer.AddNewElement(socketList.get(last).getInetAddress().toString() + " " + msg.readLine());
            } catch (IOException e) {
                System.out.println("Error: " + e);
            }
        }
    }
}
