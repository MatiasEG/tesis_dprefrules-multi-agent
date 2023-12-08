package errors;

@SuppressWarnings("serial")
public class AgentPriorityError extends Exception{

	public AgentPriorityError() {
        super("Se produjo un error al intentar cargar el archivo de preferencia entre agentes.");
    }

    public AgentPriorityError(String msg) {
        super(msg);
    }
}
