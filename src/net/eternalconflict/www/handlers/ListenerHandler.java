package net.eternalconflict.www.handlers;

import net.eternalconflict.www.ConfigFile;
import net.eternalconflict.www.enums.ListenerEnum;
import net.eternalconflict.www.interfaces.ConsoleInterface;
import net.eternalconflict.www.interfaces.DefaultInterface;
import net.eternalconflict.www.interfaces.SocketInterface;

import java.util.ArrayList;
import java.util.List;

public class ListenerHandler {

    public static ListenerHandler instance;
    public ListenerHandler()
    {
        instance = this;
    }
    private List<DefaultInterface> listeners = new ArrayList<DefaultInterface>();

    public void addListener(DefaultInterface toAdd) {
        listeners.add(toAdd);
    }

    public void fireEvent(ListenerEnum type, Object ... args) {
        for (DefaultInterface hl : listeners) {
            if (hl instanceof SocketInterface && type == ListenerEnum.SOCKET) {
                if (args.length == 2) {
                    SocketInterface sli = (SocketInterface) hl;
                    sli.inputFromSocket((SocketHandler) args[0], (ConfigFile) args[1]);
                }
            }
            if (hl instanceof ConsoleInterface && type == ListenerEnum.CONSOLE) {
                if (args.length == 1) {
                    ConsoleInterface sli = (ConsoleInterface) hl;
                    sli.inputFromConsole((String)args[0]);
                }
            }
        }

    }
}
