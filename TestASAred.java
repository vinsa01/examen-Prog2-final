import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
        import java.time.LocalDate;

public class TestASAred {
    @Test
    public void testPointageRouge() {
        Prestataire p = new Prestataire(1, "Rakoto", "Jean", "rakoto@mail.hei.school", "0345572495", 100);
        p.ajouterPointage(new Pointage(LocalDate.of(2025,9,1), TypeTravail.ENSEIGNEMENT, 0.5, "Cours Java", "bleu"));
        p.ajouterPointage(new Pointage(LocalDate.of(2025,9,1), TypeTravail.ADMINISTRATION, 0.5, "Réunion", "jaune"));

        assertTrue(p.pointageRouge(LocalDate.of(2025,9,1)));
    }
}

