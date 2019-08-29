package net.eternalconflict.www.interfaces;

import net.eternalconflict.www.ConfigFile;
import net.eternalconflict.www.handlers.SocketHandler;

public interface SocketInterface extends DefaultInterface {
    void inputFromSocket(SocketHandler socketHandler, ConfigFile data);
}
