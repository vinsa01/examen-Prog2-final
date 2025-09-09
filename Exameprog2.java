import java.time.LocalDate;
import java.util.*;

enum TypeTravail {
    ENSEIGNEMENT, ADMINISTRATION, COMMUNICATION, DR, ABS_PAYEE, ABS_NON_PAYEE
}

class Promotion {
    LocalDate date;
    String raison;
    double valeur; 

    public Promotion(LocalDate date, String raison, double valeur) {
        this.date = date;
        this.raison = raison;
        this.valeur = valeur;
    }
}

class Pointage {
    LocalDate date;
    TypeTravail type;
    double quota; 
    String description;
    String couleur;

    public Pointage(LocalDate date, TypeTravail type, double quota, String description, String couleur) {
        if (quota <= 0 || quota > 1) {
            throw new IllegalArgumentException("Quota invalide: " + quota);
        }
        this.date = date;
        this.type = type;
        this.quota = quota;
        this.description = description;
        this.couleur = couleur;
    }
}

abstract class Travailleur {
    int id;
    String nom;
    String prenom;
    String email;
    String telephone;
    List<Promotion> promotions = new ArrayList<>();
    List<Pointage> pointages = new ArrayList<>();

    public Travailleur(int id, String nom, String prenom, String email, String telephone) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
    }

    public void ajouterPointage(Pointage p) {
        pointages.add(p);
    }

   
    }

    public boolean pointageRouge(LocalDate date) {
        
        List<Pointage> pourLaDate = pointages.stream()
                .filter(p -> p.date.equals(date))
                .collect(java.util.stream.Collectors.toList());

        if (pourLaDate.stream().anyMatch(p -> p.quota <= 0 || p.quota > 1)) {
            throw new IllegalArgumentException("Quota invalide trouvé pour la date " + date + " !");
        }

        double total = pourLaDate.stream()
                .mapToDouble(p -> p.quota)
                .sum();

        return total == 1.0;
    }

    
    public double getDaysRed(LocalDate debut, LocalDate fin) {
        Map<LocalDate, Double> parDate = pointages.stream()
                .filter(p -> !p.date.isBefore(debut) && !p.date.isAfter(fin))
                .filter(p -> p.type != TypeTravail.ABS_PAYEE && p.type != TypeTravail.ABS_NON_PAYEE)
                .collect(java.util.stream.Collectors.groupingBy(
                        p -> p.date,
                        java.util.stream.Collectors.summingDouble(p -> p.quota)
                ));

        return parDate.values().stream().mapToDouble(Double::doubleValue).sum();
    }
}

class Salarie extends Travailleur {
    double salaireMensuel;

    public Salarie(int id, String nom, String prenom, String email, String telephone, double salaireMensuel) {
        super(id, nom, prenom, email, telephone);
        this.salaireMensuel = salaireMensuel;
    }
}

class Prestataire extends Travailleur {
    double tjm; 

    public Prestataire(int id, String nom, String prenom, String email, String telephone, double tjm) {
        super(id, nom, prenom, email, telephone);
        this.tjm = tjm;
    }

    
    public double getTjmAu(LocalDate date) {
        return promotions.stream()
                .filter(pr -> !pr.date.isAfter(date))
                .max(Comparator.comparing(pr -> pr.date))
                .map(pr -> pr.valeur)
                .orElse(this.tjm);
    }

    
    public double getSalairePrestataire(LocalDate debut, LocalDate fin) {
        Map<LocalDate, Double> quotasParDate = pointages.stream()
                .filter(p -> !p.date.isBefore(debut) && !p.date.isAfter(fin))
                .filter(p -> p.type != TypeTravail.ABS_PAYEE && p.type != TypeTravail.ABS_NON_PAYEE)
                .collect(java.util.stream.Collectors.groupingBy(
                        p -> p.date,
                        java.util.stream.Collectors.summingDouble(p -> p.quota)
                ));

        double total = 0.0;
        
        for (Map.Entry<LocalDate, Double> e :);
        }
        return total;
    }
}