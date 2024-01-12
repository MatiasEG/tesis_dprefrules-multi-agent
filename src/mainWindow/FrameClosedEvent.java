package mainWindow;

import java.util.EventObject;

@SuppressWarnings("serial")
public class FrameClosedEvent extends EventObject {
    public FrameClosedEvent(Object source) {
        super(source);
    }
}
