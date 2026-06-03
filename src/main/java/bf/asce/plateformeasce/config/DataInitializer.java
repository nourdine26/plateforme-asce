package bf.asce.plateformeasce.config;

import bf.asce.plateformeasce.entity.Direction;
import bf.asce.plateformeasce.repository.DirectionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final DirectionRepository directionRepository;

    public DataInitializer(DirectionRepository directionRepository) {
        this.directionRepository = directionRepository;
    }

    @Override
    public void run(String... args) {
        // On insère seulement si la table est vide (pour éviter les doublons à chaque démarrage)
        if (directionRepository.count() == 0) {

            Direction d1 = new Direction();
            d1.setNom("Direction du Contrôle des Marchés Publics");
            d1.setCode("DCMP");

            Direction d2 = new Direction();
            d2.setNom("Direction des Investigations");
            d2.setCode("DI");

            Direction d3 = new Direction();
            d3.setNom("Direction des Affaires Juridiques");
            d3.setCode("DAJ");

            directionRepository.save(d1);
            directionRepository.save(d2);
            directionRepository.save(d3);

            System.out.println(">>> 3 directions de test ont été insérées !");
        }
    }
}