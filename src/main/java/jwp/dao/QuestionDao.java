package jwp.dao;

import jwp.model.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class QuestionDao {

    private final EntityManager em;

    @Transactional
    public Question insert(Question q) {
        em.persist(q);
        return q;
    }

    @Transactional
    public void update(Question question) {
        em.merge(question);
    }

    @Transactional
    public void deleteById(Question question) {
        em.remove(question);
    }

    public List<Question> findAll() {
        return em.createQuery("select q from Question q", Question.class).getResultList();
    }

    public Question findByQuestionId(long questionId) {
        return em.find(Question.class, questionId);
    }
}
