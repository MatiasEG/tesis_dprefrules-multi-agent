package exceptions;

@SuppressWarnings("serial")
public class AgentPriorityException extends Exception{

	public AgentPriorityException() {
        super("Se produjo un error al intentar cargar el archivo de preferencia entre agentes.");
    }

    public AgentPriorityException(String msg) {
        super(msg);
    }
}
