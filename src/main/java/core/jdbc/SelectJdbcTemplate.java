package core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class SelectJdbcTemplate<T> {
    public List<T> query(RowMapper<T> rowMapper) {
        List<T> objects = new ArrayList<>();
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(createSelectQuery());
             ResultSet rs = pstmt.executeQuery();) {
            while (rs.next()) {
                T object = rowMapper.mapRow(rs);
                objects.add(object);
            }
            return objects;
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public T queryForObject(PreparedStatementSetter pstmtSetter, RowMapper<T> rowMapper){
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(createSelectQuery())){

            pstmtSetter.setParameters(pstmt);
            try(ResultSet rs = pstmt.executeQuery();){
                T object = null;// 순서 꼬일 수 있어서 안에 넣지 않는다 -> 수동으로 자원 해제
                if (rs.next()) {
                    object = rowMapper.mapRow(rs);
                }
                return object;
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public abstract String createSelectQuery();
}