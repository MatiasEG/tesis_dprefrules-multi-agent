package errors;

@SuppressWarnings("serial")
public class EvidenceFileError extends Exception{

	public EvidenceFileError() {
        super("Se produjo un error al intentar cargar el archivo de evidencia.");
    }

    public EvidenceFileError(String msg) {
        super(msg);
    }
}
