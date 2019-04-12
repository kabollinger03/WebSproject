package Controller;




import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;


public class ModuleNameExtracter implements ResultSetExtractor<ArrayList<String>> {

    @Override
    public ArrayList<String> extractData(ResultSet rs) throws SQLException, DataAccessException {
        ArrayList<String> moduleNames = new ArrayList<>();
        
        while(rs.next())
            moduleNames.add(rs.getString("module_name"));
        
        return moduleNames;
    }
}
