package exceptions;

@SuppressWarnings("serial")
public class RuleFileErrorException extends Exception{

	public RuleFileErrorException() {
        super("Se produjo un error al intentar cargar el archivo de reglas.");
    }

    public RuleFileErrorException(String msg) {
        super(msg);
    }
}
