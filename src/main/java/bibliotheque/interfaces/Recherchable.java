package bibliotheque.interfaces;

import java.util.List;

/**
 * Interface permettant la recherche multicritère sur un document.
 */
public interface Recherchable {
    boolean correspondA(String critere);
    List<String> getTags();
}
