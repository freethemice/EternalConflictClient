package net.eternalconflict.www.handlers;

import net.eternalconflict.www.ConfigFile;
import net.eternalconflict.www.EternalConflict;
import net.eternalconflict.www.enums.ListenerEnum;
import net.eternalconflict.www.enums.ServerInfoEnum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class SocketHandler extends Thread {

    private InetAddress address;
    private Socket s1=null;
    private BufferedReader bufferedReader =null;
    private PrintWriter os=null;
    public static SocketHandler instance;
    public SocketHandler() throws Exception
    {
        InetAddress inetAddress = InetAddress.getByName(ServerInfoEnum.LOGIN.getAddress());

        this.address = inetAddress;
        s1=new Socket(address, 4445); // You can use static final constant PORT_NUM
        bufferedReader =new BufferedReader(new InputStreamReader(s1.getInputStream()));
        os= new PrintWriter(s1.getOutputStream());

        instance = this;
    }

    public void close()
    {
        try {
            s1.close();
            bufferedReader.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public InetAddress getAddress() {
        return address;
    }
    public void sendRawData(ConfigFile configFile)
    {
        String out = configFile.saveToString();
        sendRawData(out);
    }
    private void sendRawData(String text)
    {
        String encode = ConfigFile.encode(text);
        os.println(encode);
        os.flush();
    }
    public void run() {
        String response=null;
        try {
            while(!EternalConflict.quit) {
                if (bufferedReader.ready()) {
                    response = bufferedReader.readLine();
                    String text = ConfigFile.decode((String) response);
                    ConfigFile out = new ConfigFile();
                    out.loadFromString(text);
                    if (out != null && out.getKeys().size() > 0) {
                        ListenerHandler.instance.fireEvent(ListenerEnum.SOCKET, this, out);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
