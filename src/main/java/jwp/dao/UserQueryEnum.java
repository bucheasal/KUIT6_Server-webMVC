package jwp.dao;

public enum UserQueryEnum {
    INSERT_USER("INSERT INTO USERS VALUES (?, ?, ?, ?)"),
    UPDATE_USER("UPDATE USERS SET password =?, name =?, email =? WHERE userId =?"),
    DELETE_USER("DELETE FROM USERS WHERE userId =?"),
    FIND_ALL_USER("SELECT * FROM USERS"),
    FIND_BY_USERID("SELECT * FROM USERS WHERE userId=?");

    private final String sql;
    UserQueryEnum(String sql) {
        this.sql = sql;
    }

    public String sql() {
        return sql;
    }

}
