import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DatabaseTable extends DefaultTableModel{
   private Vector<String> columnNames;

    public DatabaseTable(int columnCount, String[] columnNames) {
        super(columnNames, 0); // 0 indicates initial row count
        this.columnNames = new Vector<>();
        for (String columnName : columnNames) {
            this.columnNames.add(columnName);
        }
    }

    //This method adds name in columns. Every table has different number and names of columns
    public void setColumns(int columnCount, String[] columnNames) {
        this.columnNames.clear();
        for (int i = 0; i < columnCount; i++) {
            this.columnNames.add(columnNames[i]);
        }
        fireTableStructureChanged(); // Notify the table model that the structure has changed
    }
    
    //This method adds data in every column.The data is from oracle database
     public void addData(ResultSet rs) {
        try {
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            Vector<Object> rowData = new Vector<>();

            for (int i = 1; i <= columnCount; i++) {
                rowData.add(rs.getObject(i));
            }

            addRow(rowData); // Add the row to the table model
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
