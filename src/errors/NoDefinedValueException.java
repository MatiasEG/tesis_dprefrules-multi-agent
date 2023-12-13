package errors;

@SuppressWarnings("serial")
public class NoDefinedValueException extends Exception{

	public NoDefinedValueException() {
        super("Se produjo un error al obtener un valor asociado a una premisa.");
    }

    public NoDefinedValueException(String msg) {
        super(msg);
    }
}
