package com.pmu.coursemanager.controller;

import com.pmu.coursemanager.dto.PartantDTO;
import com.pmu.coursemanager.entity.Partant;
import com.pmu.coursemanager.service.PartantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses/{courseId}/partants")
@AllArgsConstructor
@Api(tags = "Partants")
public class PartantController {

    private PartantService partantService;

    @ApiOperation(value = "Créer un nouveau partant", response = Partant.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Partant créée avec succès"),
            @ApiResponse(code = 400, message = "Requête invalide"),
            @ApiResponse(code = 500, message = "Erreur interne du serveur")
    })
    @Transactional
    @PostMapping
    public ResponseEntity<Partant> createPartant(@PathVariable Long courseId, @RequestBody PartantDTO partantDTO) {
        if (partantDTO.getNom() == null || partantDTO.getNom().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Partant partant = new Partant();
        partant.setNom(partantDTO.getNom());
        Partant createdPartant = partantService.addPartant(courseId, partant);

        return ResponseEntity.ok(createdPartant);
    }

    @ApiOperation(value = "Récupérer tous les partants d'une course spécifique", response = Partant.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Liste de partants trouvée")
    })
    @GetMapping
    public ResponseEntity<List<Partant>> getAllPartants(@PathVariable Long courseId) {
        List<Partant> partants = partantService.getAllPartantsByCourseId(courseId);
        return ResponseEntity.ok(partants);
    }

}
