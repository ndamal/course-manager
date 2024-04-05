package com.pmu.coursemanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @NotEmpty(message = "Le nom de la course ne peut pas être vide")
    private String name;

    @NotNull(message = "La date de la course ne peut pas être nulle")
    private LocalDate date;

    @Min(value = 1, message = "Le numéro unique de la course doit être supérieur à 0")
    @Column(unique = true)
    private int numberUnique;

    @JsonManagedReference
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Size(min = 3)
    private List<Partant> partants = new ArrayList<>();
}
