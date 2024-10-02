package com.odk.Controller;

import com.odk.Entity.Question;
import com.odk.Entity.Quiz;
import com.odk.Service.Interface.Service.QuizService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/quiz")
@AllArgsConstructor
public class QuizController {

    private QuizService quizService;

    @PostMapping("/ajout")
    @ResponseStatus(HttpStatus.CREATED)
    public Quiz ajouter(@RequestBody Quiz quiz) {
        try {
            return quizService.add(quiz);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur lors de l'ajout de quiz", e);
        }
    }

    @GetMapping("/listeQuiz")
    @ResponseStatus(HttpStatus.OK)
    public List<Quiz> listerQuestion() {
        try {
            return quizService.List();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur lors de la récupération des quiz", e);
        }
    }

    @GetMapping("/listeQuiz/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Quiz> getRoleParId(@PathVariable Long id) {
        try {
            return quizService.findById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur lors de la récupération de quiz par ID", e);
        }
    }

    @PutMapping("/modifier/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Quiz modifier(@PathVariable Long id, @RequestBody Quiz quiz) {
        try {
            return quizService.update(quiz, id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur lors de la modification de quiz", e);
        }
    }

    @DeleteMapping("/supprimer/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void supprimer(@PathVariable Long id) {
        try {
            quizService.delete(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur lors de la suppression de quiz", e);
        }
    }
}
