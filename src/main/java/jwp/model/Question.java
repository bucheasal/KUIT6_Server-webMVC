package jwp.model;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;


@Entity
@Table(name = "QUESTIONS")
@NoArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /*INSERT INTO QUESTIONS (writer, title, contents, createdDate, countOfAnswer) VALUES*/
    private long questionId;
    private String writer;
    private String title;
    private String content;
    private Date createdDate;
    private int countOfAnswer;

    public Question(int questionId, String writer, String title, String content, Date createdDate, int countOfAnswer) {
        this.questionId = questionId;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.countOfAnswer = countOfAnswer;
    }

    public Question(String writer, String title, String content, int countOfAnswer) {
        this.questionId = 0;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.createdDate = Date.valueOf(LocalDate.now());
        this.countOfAnswer = countOfAnswer;
    }

    public long getQuestionId() {
        return questionId;
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public int getCountOfAnswer() {
        return countOfAnswer;
    }

    public void updateTitleAndContent(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void increaseCountOfAnswer() {
        countOfAnswer += 1;
    }

    public void decreaseCountOfAnswer() {
        countOfAnswer -= 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return getCountOfAnswer() == question.getCountOfAnswer() && Objects.equals(getWriter(), question.getWriter()) && Objects.equals(getTitle(), question.getTitle()) && Objects.equals(getContent(), question.getContent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getWriter(), getTitle(), getContent(), getCountOfAnswer());
    }

    public boolean isSameUser(User user) {
        if (user == null) return false;
        return writer.equals(user.getUserId());
    }
}