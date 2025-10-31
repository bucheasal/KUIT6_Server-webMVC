package jwp.dao;

public enum QuestionQueryEnum {
    FIND_ALL_QUESTIONS("SELECT * FROM QUESTIONS"),
    INSERT_QUESTION("INSERT INTO USERS VALUES (?, ?, ?, ?, ?, ?)"),
    UPDATE_QUESTION("UPDATE USERS SET writer =?, title =?, content =?, createdDate =?, countOfAnswer =? WHERE userId =?"),
    DELETE_QUESTION("DELETE FROM USERS WHERE questionId =?"),
    FIND_BY_QUESTIONID("SELECT * FROM USERS WHERE questionId=?");
    private final String sql;
    QuestionQueryEnum(String sql) {
        this.sql = sql;
    }

    public String sql() {
        return sql;
    }
}
