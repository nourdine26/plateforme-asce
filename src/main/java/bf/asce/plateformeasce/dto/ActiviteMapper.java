package bf.asce.plateformeasce.dto;

import bf.asce.plateformeasce.entity.Activite;
import org.springframework.stereotype.Component;

@Component
public class ActiviteMapper {

    // Transforme une entité Activite en ActiviteDTO (fiche résumé)
    public ActiviteDTO toDTO(Activite activite) {
        ActiviteDTO dto = new ActiviteDTO();

        dto.setId(activite.getId());
        dto.setTitre(activite.getTitre());
        dto.setDescription(activite.getDescription());
        dto.setStatut(activite.getStatut());
        dto.setDateDebutPrevue(activite.getDateDebutPrevue());
        dto.setDateFinPrevue(activite.getDateFinPrevue());

        // On va chercher les infos de la direction liée (si elle existe)
        if (activite.getDirection() != null) {
            dto.setDirectionId(activite.getDirection().getId());
            dto.setDirectionNom(activite.getDirection().getNom());
        }

        return dto;
    }
}