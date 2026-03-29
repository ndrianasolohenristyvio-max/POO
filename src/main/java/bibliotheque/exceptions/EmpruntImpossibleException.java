package bibliotheque.exceptions;

/**
 * Exception levée lors d'un emprunt impossible (quota, disponibilité, etc.).
 */
public class EmpruntImpossibleException extends Exception {
    public EmpruntImpossibleException(String message) {
        super(message);
    }
    public EmpruntImpossibleException(String message, Throwable cause) {
        super(message, cause);
    }
}
