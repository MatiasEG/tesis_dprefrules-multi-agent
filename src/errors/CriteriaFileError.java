package errors;

@SuppressWarnings("serial")
public class CriteriaFileError extends Exception{

	public CriteriaFileError() {
        super("Se produjo un error al intentar cargar el archivo de criterios.");
    }

    public CriteriaFileError(String msg) {
        super(msg);
    }
}
