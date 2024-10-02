package com.odk.Service.Interface.Service;

import com.odk.Entity.Question;
import com.odk.Repository.QuestionRepository;
import com.odk.Service.Interface.CrudService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class QuestionService implements CrudService<Question, Long> {

    private QuestionRepository questionRepository;

    @Override
    public Question add(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public List<Question> List() {
        return questionRepository.findAll();
    }

    @Override
    public Optional<Question> findById(Long id) {
        return questionRepository.findById(id);
    }

    @Override
    public Question update(Question cours, Long id) {
         Optional<Question> questionOptional = questionRepository.findById(id);
         if (questionOptional.isPresent()) {
             Question existingQuestion = questionOptional.get();
             existingQuestion.setType(cours.getType());
             existingQuestion.setContenu(cours.getContenu());
             return questionRepository.save(existingQuestion);
         }
        return null;
    }

    @Override
    public void delete(Long id) {
        Optional<Question> questionOptional = questionRepository.findById(id);
        questionOptional.ifPresent(question -> questionRepository.delete(question));
    }
}
