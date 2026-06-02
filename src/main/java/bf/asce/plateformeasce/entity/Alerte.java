package bf.asce.plateformeasce.entity;

import bf.asce.plateformeasce.enums.NiveauAlerte;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "alerte")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Alerte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String message;

    private LocalDateTime dateAlerte;

    @Enumerated(EnumType.STRING)
    private NiveauAlerte niveau;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activite_id")
    private Activite activite;
}