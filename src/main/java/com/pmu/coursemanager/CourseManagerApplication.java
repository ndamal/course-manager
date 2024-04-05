package com.pmu.coursemanager;

import com.pmu.coursemanager.entity.Course;
import com.pmu.coursemanager.entity.Partant;
import com.pmu.coursemanager.repository.CourseRepository;
import com.pmu.coursemanager.repository.PartantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@SpringBootApplication
public class CourseManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CourseManagerApplication.class, args);
    }

    private Random random = new Random();

    @Bean
    CommandLineRunner commandLineRunner(CourseRepository courseRepository, PartantRepository partantRepository) {
        log.info("================= Initialization ================");
        return args -> {
            Set<Integer> generatedNumbers = new HashSet<>();
            List<String> nomCourses = List.of("Course de printemps", "Course d'été", "Course d'automne",
                    "Course d'hiver", "Grand Prix", "Course de Gala");
            List<String> nomPartants = new ArrayList<>(List.of("Cheval Noir", "Éclair Blanc", "Fusée Rouge", "Étalon Doré",
                    "Tempête Grise", "Comète Bleue", "Galopant Vert", "Aile d'Argent", "Pégase", "Licorne",
                    "Jument Noire", "Étoile Filante", "Brise Marine", "Tourbillon Jaune", "Cascade", "Arc-en-Ciel",
                    "Cavalier Astral", "Course Céleste"));
            // Mélanger la liste de noms de partants
            Collections.shuffle(nomPartants);

            for (String nomCourse : nomCourses) {
                int numberOfPartants = random.nextInt(16) + 3;

                int numberUnique;
                do {
                    numberUnique = random.nextInt(100);
                } while (!generatedNumbers.add(numberUnique)); // Vérifie si le nombre est déjà présent, sinon ajoute-le

                Course course = Course.builder()
                        .name(nomCourse)
                        .date(LocalDate.now().plusDays(random.nextInt(7)))
                        .numberUnique(numberUnique)
                        .build();
                course = courseRepository.save(course);

                for (int j = 0; j < Math.min(nomPartants.size(), numberOfPartants); j++) {
                    Partant partant = Partant.builder()
                            .nom(nomPartants.get(j))
                            .numero(j + 1)
                            .course(course)
                            .build();
                    partantRepository.save(partant);
                }
            }
        };

    }
}
