package jwp.dao;

import core.jdbc.PreparedStatementSetter;
import core.jdbc.RowMapper;
import core.jdbc.SelectJdbcTemplate;
import core.jdbc.UpdateJdbcTemplate;
import jwp.model.KeyHolder;
import jwp.model.Question;

import java.sql.Timestamp;
import java.util.List;

import static jwp.dao.QuestionQueryEnum.*;

public class QuestionDao {
    private static QuestionDao questionDao;
    private QuestionDao() {
    }

    public static QuestionDao getInstance() {
        if (questionDao == null) {
            questionDao = new QuestionDao();
            return questionDao;
        }
        return questionDao;
    }

    /*INSERT INTO QUESTIONS (writer, title, contents, createdDate, countOfAnswer) VALUES*/
    public List<Question> findAll() {
        RowMapper<Question> rowMapper = rs -> new Question(
                rs.getLong("questionId"),
                rs.getString("writer"),
                rs.getString("title"),
                rs.getString("contents"),
                rs.getTimestamp("createdDate").toLocalDateTime(),
                rs.getInt("countOfAnswer"));
        SelectJdbcTemplate<Question> selectJdbcTemplate = new SelectJdbcTemplate() {
            public String createSelectQuery() {
                return FIND_ALL_QUESTIONS.sql();
            }
        };
        return selectJdbcTemplate.query(rowMapper);
    }

    public Question insert(Question q) {
        PreparedStatementSetter pstmtSetter = pstmt ->{
            pstmt.setLong(1, q.getQuestionId());
            pstmt.setString(2, q.getWriter());
            pstmt.setString(3, q.getTitle());
            pstmt.setString(4, q.getContents());
            pstmt.setTimestamp(5, Timestamp.valueOf(q.getCreatedDate()));
            pstmt.setInt(6, q.getCountOfAnswer());};
        UpdateJdbcTemplate updateJdbcTemplate = new UpdateJdbcTemplate() {
            public String createQuery(){
                return INSERT_QUESTION.sql();
            }
        };
        KeyHolder keyHolder = new KeyHolder();
        updateJdbcTemplate.updateQuestion(pstmtSetter, keyHolder);
        return q;
    }

    public Question findByQuestionId(long questionId) {
        RowMapper<Question> rowMapper = rs -> new Question(
                rs.getLong("questionId"),
                rs.getString("writer"),
                rs.getString("title"),
                rs.getString("contents"),
                rs.getTimestamp("createdDate").toLocalDateTime(),
                rs.getInt("countOfAnswer"));
        SelectJdbcTemplate<Question> selectJdbcTemplate = new SelectJdbcTemplate() {
            public String createSelectQuery() { return FIND_BY_QUESTIONID.sql(); }
        };
        List<Question> result = selectJdbcTemplate.query(rowMapper);
        return result.isEmpty() ? null : result.get(0);
    }

    public void deleteById(long questionId) {
        PreparedStatementSetter pstmtSetter = pstmt -> pstmt.setLong(1, questionId);
        UpdateJdbcTemplate updateJdbcTemplate = new UpdateJdbcTemplate() {
            public String createQuery() { return DELETE_QUESTION.sql(); }
        };
        updateJdbcTemplate.update(pstmtSetter);
    }
}
