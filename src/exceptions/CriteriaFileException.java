package exceptions;

@SuppressWarnings("serial")
public class CriteriaFileException extends Exception{

	public CriteriaFileException() {
        super("Se produjo un error al intentar cargar el archivo de criterios.");
    }

    public CriteriaFileException(String msg) {
        super(msg);
    }
}
