package bibliotheque.interfaces;

/**
 * Interface Observer du pattern Observer.
 * Utilisée pour notifier les utilisateurs en file d'attente.
 */
public interface Observer {
    void notifier(String message);
}
