package bf.asce.plateformeasce.dto;

import bf.asce.plateformeasce.enums.StatutActivite;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActiviteDTO {

    private Long id;
    private String titre;
    private String description;
    private StatutActivite statut;
    private LocalDate dateDebutPrevue;
    private LocalDate dateFinPrevue;

    // Au lieu de l'objet Direction entier, juste les infos utiles :
    private Long directionId;
    private String directionNom;
}