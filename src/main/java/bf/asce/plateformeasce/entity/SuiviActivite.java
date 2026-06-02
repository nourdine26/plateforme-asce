package bf.asce.plateformeasce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "suivi_activite")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SuiviActivite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate periode;

    private LocalDate dateSaisie;

    private Float tauxExecution;

    @Column(length = 1000)
    private String commentaire;

    @Column(length = 1000)
    private String difficultes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activite_id")
    private Activite activite;
}