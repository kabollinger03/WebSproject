
package POJO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Stream {
    private String ID;
    private String name;

    public Stream(ResultSet rs) {
        try {
            this.ID = rs.getString("stream_id");
            this.name = rs.getString("stream_name");
        } catch (SQLException ex) {
            Logger.getLogger(Stream.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
    @Override
    public String toString() {
        return "Stream{" + "ID=" + ID + ", name=" + name + '}';
    }
}
