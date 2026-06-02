package bf.asce.plateformeasce.entity;

import bf.asce.plateformeasce.enums.StatutActivite;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "activite")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Activite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titre;

    @Column(length = 1000)
    private String description;

    private String objectif;

    private String resultatAttendu;

    private Double budget;

    private LocalDate dateDebutPrevue;

    private LocalDate dateFinPrevue;

    private LocalDate dateDebutReelle;

    private LocalDate dateFinReelle;

    private Float tauxExecution;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutActivite statut = StatutActivite.NON_DEMARREE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "direction_id")
    private Direction direction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "createur_id")
    private Utilisateur createur;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "programme_id")
    private Programme programme;

    @ManyToMany
    @JoinTable(
        name = "activite_participant",
        joinColumns = @JoinColumn(name = "activite_id"),
        inverseJoinColumns = @JoinColumn(name = "participant_id")
    )
    private Set<Participant> participants = new HashSet<>();
}