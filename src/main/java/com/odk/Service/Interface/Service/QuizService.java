package com.odk.Service.Interface.Service;

import com.odk.Entity.Quiz;
import com.odk.Repository.QuestionRepository;
import com.odk.Repository.QuizRepository;
import com.odk.Service.Interface.CrudService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class QuizService implements CrudService<Quiz, Long> {

    private final QuestionService questionService;
    private final QuestionRepository questionRepository;
    private QuizRepository quizRepository;

    @Override
    public Quiz add(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    @Override
    public List<Quiz> List() {
        return quizRepository.findAll();
    }

    @Override
    public Optional<Quiz> findById(Long id) {
        return quizRepository.findById(id);
    }

    @Override
    public Quiz update(Quiz quiz, Long id) {
        Optional<Quiz> optionalQuiz = quizRepository.findById(id);
        if (optionalQuiz.isPresent()) {
            Quiz updatedQuiz = optionalQuiz.get();
            updatedQuiz.setTitre(quiz.getTitre());
            return quizRepository.save(updatedQuiz);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        Optional<Quiz> optionalQuiz = quizRepository.findById(id);
        optionalQuiz.ifPresent(quiz -> quizRepository.delete(quiz));
    }
}
