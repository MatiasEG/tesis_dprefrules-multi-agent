package exceptions;

@SuppressWarnings("serial")
public class EvidenceFileException extends Exception{

	public EvidenceFileException() {
        super("Se produjo un error al intentar cargar el archivo de evidencia.");
    }

    public EvidenceFileException(String msg) {
        super(msg);
    }
}
