package bibliotheque.interfaces;

/**
 * Interface Observable du pattern Observers.
 */
public interface Observable {
    void ajouterObserver(Observer o);
    void retirerObserver(Observer o);
    void notifierObservers(String message);
}
