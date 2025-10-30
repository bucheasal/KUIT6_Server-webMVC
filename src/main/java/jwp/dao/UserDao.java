package jwp.dao;

import core.jdbc.*;
import jwp.model.User;

import java.util.List;

import static jwp.dao.QueryEnum.*;

public class UserDao {
    public void insert(User user) {
        PreparedStatementSetter pstmtSetter = pstmt ->{
        pstmt.setString(1, user.getUserId());
        pstmt.setString(2, user.getPassword());
        pstmt.setString(3, user.getName());
        pstmt.setString(4, user.getEmail());};
        UpdateJdbcTemplate updateJdbcTemplate = new UpdateJdbcTemplate() {
            public String createQuery(){
                return INSERT_USER.sql();
            }
        };
        updateJdbcTemplate.update(pstmtSetter);
    }

    public void update(User user) {
        PreparedStatementSetter pstmtSetter = pstmt -> {
            pstmt.setString(1, user.getPassword());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getUserId());
        };
        UpdateJdbcTemplate updateJdbcTemplate = new UpdateJdbcTemplate() {
            public String createQuery() {
                return UPDATE_USER.sql();
            }
        };
        updateJdbcTemplate.update(pstmtSetter);
    }

    public void delete(User user) {
        PreparedStatementSetter pstmtSetter = pstmt -> {
            pstmt.setString(1, user.getUserId());
        };
        UpdateJdbcTemplate updateJdbcTemplate = new UpdateJdbcTemplate() {
            public String createQuery() {
                return DELETE_USER.sql();
            }
        };
        updateJdbcTemplate.update(pstmtSetter);
    }

    public List<User> findAll() {
        RowMapper<User> rowMapper = rs -> new User(
                rs.getString("userId"),
                rs.getString("password"),
                rs.getString("name"),
                rs.getString("email"));
        SelectJdbcTemplate<User> selectJdbcTemplate = new SelectJdbcTemplate() {
            public String createSelectQuery() {
                return FIND_ALL_USER.sql();} };
        return selectJdbcTemplate.query(rowMapper);
    }

    public User findByUserId(String userId){
        PreparedStatementSetter pstmtSetter = pstmt->{ pstmt.setString(1, userId); };
        RowMapper<User> rowMapper = rs -> new User(
                rs.getString("userId"),
                rs.getString("password"),
                rs.getString("name"),
                rs.getString("email"));
        SelectJdbcTemplate<User> selectJdbcTemplate = new SelectJdbcTemplate() {
            public String createSelectQuery() {
                return FIND_BY_USERID.sql();
            }
        };
        return selectJdbcTemplate.queryForObject(pstmtSetter, rowMapper);

    }
}

