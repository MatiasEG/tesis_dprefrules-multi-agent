package errors;

@SuppressWarnings("serial")
public class RuleFileError extends Exception{

	public RuleFileError() {
        super("Se produjo un error al intentar cargar el archivo de reglas.");
    }

    public RuleFileError(String msg) {
        super(msg);
    }
}
