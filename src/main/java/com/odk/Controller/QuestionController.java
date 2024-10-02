package com.odk.Controller;

import com.odk.Entity.Cours;
import com.odk.Entity.Question;
import com.odk.Repository.QuestionRepository;
import com.odk.Service.Interface.Service.QuestionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/questions")
@AllArgsConstructor
public class QuestionController {

    private QuestionService questionService;

    @PostMapping("/ajout")
    @ResponseStatus(HttpStatus.CREATED)
        public Question ajouter(@RequestBody Question question) {
        try {
            return questionService.add(question);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur lors de l'ajout du question", e);
        }
    }

    @GetMapping("/listeQuestion")
    @ResponseStatus(HttpStatus.OK)
    public List<Question> listerQuestion() {
        try {
            return questionService.List();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur lors de la récupération des questions", e);
        }
    }

    @GetMapping("/listeQuestion/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Question> getRoleParId(@PathVariable Long id) {
        try {
            return questionService.findById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur lors de la récupération du question par ID", e);
        }
    }

    @PutMapping("/modifier/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Question modifier(@PathVariable Long id, @RequestBody Question question) {
        try {
            return questionService.update(question, id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur lors de la modification du question", e);
        }
    }

    @DeleteMapping("/supprimer/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void supprimer(@PathVariable Long id) {
        try {
            questionService.delete(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur lors de la suppression du question", e);
        }
    }

}
