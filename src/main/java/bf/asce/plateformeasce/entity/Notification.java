package bf.asce.plateformeasce.entity;

import bf.asce.plateformeasce.enums.TypeNotification;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "notification")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String message;

    private LocalDateTime dateEnvoi;

    private boolean lu = false;

    @Enumerated(EnumType.STRING)
    private TypeNotification type;

    @ManyToMany
    @JoinTable(
        name = "notification_destinataire",
        joinColumns = @JoinColumn(name = "notification_id"),
        inverseJoinColumns = @JoinColumn(name = "utilisateur_id")
    )
    private Set<Utilisateur> destinataires = new HashSet<>();
}