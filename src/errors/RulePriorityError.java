package errors;

@SuppressWarnings("serial")
public class RulePriorityError extends Exception{

	public RulePriorityError() {
        super("Se produjo un error al intentar cargar el archivo de preferencia entre reglas.");
    }

    public RulePriorityError(String msg) {
        super(msg);
    }
}
