package bibliotheque.interfaces;

/**
 * Interface Observable du pattern Observer.
 */
public interface Observable {
    void ajouterObserver(Observer o);
    void retirerObserver(Observer o);
    void notifierObservers(String message);
}
