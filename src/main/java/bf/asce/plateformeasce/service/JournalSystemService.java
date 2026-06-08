package bf.asce.plateformeasce.service;

import bf.asce.plateformeasce.entity.JournalSystem;
import bf.asce.plateformeasce.entity.Utilisateur;
import bf.asce.plateformeasce.repository.JournalSystemRepository;
import bf.asce.plateformeasce.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class JournalSystemService {

    private final JournalSystemRepository journalSystemRepository;
    private final UtilisateurRepository utilisateurRepository;

    public JournalSystemService(JournalSystemRepository journalSystemRepository,
                                UtilisateurRepository utilisateurRepository) {
        this.journalSystemRepository = journalSystemRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    // Enregistrer une action dans le journal
    public void enregistrer(String action, String adresseIp, String email) {
        JournalSystem journal = new JournalSystem();
        journal.setAction(action);
        journal.setDateAction(LocalDateTime.now());
        journal.setAdresseIp(adresseIp);

        // Lier l'utilisateur si trouvé en base
        if (email != null) {
            utilisateurRepository.findByEmail(email)
                    .ifPresent(journal::setUtilisateur);
        }

        journalSystemRepository.save(journal);
    }

    // Récupérer tous les journaux (du plus récent au plus ancien)
    public List<JournalSystem> findAll() {
        return journalSystemRepository.findAllByOrderByDateActionDesc();
    }

    // Récupérer les journaux d'un utilisateur
    public List<JournalSystem> findByUtilisateur(Long utilisateurId) {
        return journalSystemRepository.findByUtilisateurId(utilisateurId);
    }
}