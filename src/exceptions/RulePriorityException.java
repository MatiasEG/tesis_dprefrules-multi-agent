package exceptions;

@SuppressWarnings("serial")
public class RulePriorityException extends Exception{

	public RulePriorityException() {
        super("Se produjo un error al intentar cargar el archivo de preferencia entre reglas.");
    }

    public RulePriorityException(String msg) {
        super(msg);
    }
}
