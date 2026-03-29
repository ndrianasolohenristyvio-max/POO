package bibliotheque.model;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Registre centralisé des utilisateurs – Singleton.
 */
public class RegistreUtilisateurs {
    private static RegistreUtilisateurs instance;
    private Map<String, Utilisateur> utilisateurs = new LinkedHashMap<>();

    private RegistreUtilisateurs() {}

    public static RegistreUtilisateurs getInstance() {
        if (instance == null) instance = new RegistreUtilisateurs();
        return instance;
    }

    public void ajouter(Utilisateur u) { utilisateurs.put(u.getId(), u); }
    public Utilisateur getParId(String id) { return utilisateurs.get(id); }
    public Collection<Utilisateur> getTous() { return utilisateurs.values(); }

    public List<Utilisateur> getParType(String type) {
        return utilisateurs.values().stream()
            .filter(u -> u.getTypeUtilisateur().equals(type))
            .collect(Collectors.toList());
    }
}
