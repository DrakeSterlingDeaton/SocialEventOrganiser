package eventorganiser.dir.DBMethods;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DBQueries {

    protected JdbcTemplate jdbcTemplate;

    public DBQueries(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public JdbcTemplate jdbcTemplate() {
        return this.jdbcTemplate;
    }

}