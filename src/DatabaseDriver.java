import java.sql.*;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class DatabaseDriver {
   
    // Database connection parameters
    static String driverClassName = "org.sqlite.JDBC";
    static String url = "jdbc:sqlite:football.db"; 
    static String username = ""; 
    static String passwd = "";   



    static Statement  statement = null;
    static ResultSet rs = null;
    private static ResultSetMetaData metaData;
    private String selectString, tableName;;
    private String[] columnNames;
    
    public DatabaseDriver(){}
    
    //This method shows up every table when we click it
    // public void showTable(String tableName, DatabaseTable dbTable) throws ClassNotFoundException{
    //     try{
    //         Class.forName(driverClassName);
    //         dbConnection = DriverManager.getConnection(url, username,passwd);      
    //         statement = dbConnection.createStatement();
                    
    //         selectString = "SELECT * FROM " + tableName ;
    //         rs = statement.executeQuery(selectString);       
             
    //         metaData = rs.getMetaData();
    //         int columnCount = metaData.getColumnCount();
    //         columnNames = new String[columnCount];
            
    //         for(int i = 1; i<=columnCount; i++){
    //             columnNames[i - 1] = metaData.getColumnName(i);
    //         }
            
    //         dbTable.setColumns(columnCount, columnNames);
    //         while (rs.next()) {
    //             dbTable.addData(rs);
    //         }

    //         rs.close();
    //         statement.close();
    //         dbConnection.close();
    //     }catch(NullPointerException | SQLException e){
    //         e.printStackTrace();
    //     }
    // }
    public void showTable(String tableName, DatabaseTable dbTable) throws ClassNotFoundException {
        Class.forName(driverClassName);
    
        String selectString = "SELECT * FROM " + tableName;
    
        // Ανοίγουμε τη σύνδεση τοπικά μέσα στο try () ώστε να κλείσει αυτόματα στο τέλος
        try (Connection localConn = DriverManager.getConnection(url, username, passwd);
            Statement localStmt = localConn.createStatement();
            ResultSet localRs = localStmt.executeQuery(selectString)) {
                
            metaData = localRs.getMetaData();
            int columnCount = metaData.getColumnCount();
            columnNames = new String[columnCount];
        
            for (int i = 1; i <= columnCount; i++) {
                columnNames[i - 1] = metaData.getColumnName(i);
            }
        
            dbTable.setColumns(columnCount, columnNames);
            while (localRs.next()) {
                dbTable.addData(localRs); // Εδώ περνάς το localRs
            }

        }catch (SQLException | NullPointerException e) {
            System.err.println("Σφάλμα κατά τη φόρτωση του πίνακα " + tableName);
            e.printStackTrace(); // Κοίταξε το τερματικό σου αν τυπωθεί αυτό!
        }
    }
    
    public static void insertteam(int teamId, String teamName, String teamField, int teamNum) throws Exception {
    try {
        // Έλεγχος αν το ID υπάρχει ήδη στη βάση δεδομένων
        String findQuery = "SELECT COUNT(*) FROM teams WHERE T_ID = ?";
        dbConnection = DriverManager.getConnection(url, username, passwd);
        PreparedStatement findStatement = dbConnection.prepareStatement(findQuery);
        findStatement.setInt(1, teamId);
        ResultSet resultSet = findStatement.executeQuery();

        if (resultSet.next() && resultSet.getInt(1) > 0) {
            // Αν το ID υπάρχει ήδη στη βάση δεδομένων, εμφάνιση μηνύματος λάθους
            JFrame Frame = new JFrame("Error");
            JOptionPane.showMessageDialog(Frame, "ID already exists", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            // Εάν το ID δεν υπάρχει, εισαγωγή των δεδομένων στη βάση
            String insertQuery = "INSERT INTO teams (T_ID, T_NAME, T_FIELD, T_NUM) VALUES (?, ?, ?, ?)";
            PreparedStatement insertStatement = dbConnection.prepareStatement(insertQuery);

            insertStatement.setInt(1, teamId);
            insertStatement.setString(2, teamName);
            insertStatement.setString(3, teamField);
            insertStatement.setInt(4, teamNum);
            insertStatement.executeUpdate();
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } 
}
    public static void deleteteam(int Id){
        try{
             // Έλεγχος αν το ID υπάρχει ήδη στη βάση δεδομένων
            String findQuery = "SELECT COUNT(*) FROM teams WHERE T_ID = ?";
            dbConnection = DriverManager.getConnection(url, username, passwd);
            PreparedStatement findStatement = dbConnection.prepareStatement(findQuery);
            findStatement.setInt(1, Id);
            ResultSet resultSet = findStatement.executeQuery();

            if (resultSet.next() && resultSet.getInt(1) > 0) {
                String query1="DELETE FROM TEAMS WHERE T_ID=?";
                dbConnection = DriverManager.getConnection (url, username, passwd);
                PreparedStatement preparedstatement1 = dbConnection.prepareStatement(query1);
                preparedstatement1.setInt(1, Id);
                preparedstatement1.executeUpdate();
            }else{
               JFrame Frame = new JFrame("Error");
               JOptionPane.showMessageDialog(Frame, "ID does not exist", "Error", JOptionPane.ERROR_MESSAGE);  
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void updateteam(int teamId,String teamName,String teamField,int teamNum) throws Exception{
        
        
        try {
            // Έλεγχος αν το ID υπάρχει ήδη στη βάση δεδομένων
            String findQuery = "SELECT COUNT(*) FROM teams WHERE T_ID = ?";
            dbConnection = DriverManager.getConnection(url, username, passwd);
            PreparedStatement findStatement = dbConnection.prepareStatement(findQuery);
            findStatement.setInt(1, teamId);
            ResultSet resultSet = findStatement.executeQuery();

            if (resultSet.next() && resultSet.getInt(1) > 0) {
                // Εισαγωγή δεδομένων στη βάση δεδομένων
                String query = "UPDATE teams SET  T_NAME = ?, T_FIELD = ?, T_NUM = ? WHERE T_ID = ?";
                dbConnection = DriverManager.getConnection (url, username, passwd);
                PreparedStatement preparedstatement = dbConnection.prepareStatement(query);
           
                preparedstatement.setString(1, teamName);
                preparedstatement.setString(2, teamField);
                preparedstatement.setInt(3, teamNum);
                preparedstatement.setInt(4, teamId);
                preparedstatement.executeUpdate();
                
            } else {
                JFrame Frame = new JFrame("Error");
                JOptionPane.showMessageDialog(Frame, "ID does not exist", "Error", JOptionPane.ERROR_MESSAGE);
               
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
     
    public static void insertplayer(int playerId,String playerName,int playerage,String playerclub,int playergoals,String playerpos) throws Exception{
        
        
        try {
             // Έλεγχος αν το ID υπάρχει ήδη στη βάση δεδομένων
            String findQuery = "SELECT COUNT(*) FROM players WHERE PLAYERID = ?";
            dbConnection = DriverManager.getConnection(url, username, passwd);
            PreparedStatement findStatement = dbConnection.prepareStatement(findQuery);
            findStatement.setInt(1, playerId);
            ResultSet resultSet = findStatement.executeQuery();

        if (resultSet.next() && resultSet.getInt(1) > 0) {
            // Αν το ID υπάρχει ήδη στη βάση δεδομένων, εμφάνιση μηνύματος λάθους
            JFrame Frame = new JFrame("Error");
            JOptionPane.showMessageDialog(Frame, "ID already exists", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            // Εισαγωγή δεδομένων στη βάση δεδομένων
            String query1 = "INSERT INTO players (PLAYERID, NAME, AGE, CLUB, GOALSSCORED, POSITION) VALUES (?, ?, ?, ?, ?, ?)";
            dbConnection = DriverManager.getConnection (url, username, passwd);
            PreparedStatement preparedstatement1 = dbConnection.prepareStatement(query1);
           
            preparedstatement1.setInt(1, playerId);
            preparedstatement1.setString(2, playerName);
            preparedstatement1.setInt(3, playerage);
            preparedstatement1.setString(4, playerclub);
            preparedstatement1.setInt(5, playergoals);
            preparedstatement1.setString(6, playerpos);
            preparedstatement1.executeUpdate();
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void deleteplayer(int Id){
        try{
             // Έλεγχος αν το ID υπάρχει ήδη στη βάση δεδομένων
            String findQuery = "SELECT COUNT(*) FROM players WHERE PLAYERID = ?";
            dbConnection = DriverManager.getConnection(url, username, passwd);
            PreparedStatement findStatement = dbConnection.prepareStatement(findQuery);
            findStatement.setInt(1, Id);
            ResultSet resultSet = findStatement.executeQuery();

            if (resultSet.next() && resultSet.getInt(1) > 0) {
                String query1="DELETE FROM PLAYERS WHERE PLAYERID=?";
                dbConnection = DriverManager.getConnection (url, username, passwd);
                PreparedStatement preparedstatement1 = dbConnection.prepareStatement(query1);
                preparedstatement1.setInt(1, Id);
                preparedstatement1.executeUpdate();
            }else{
               JFrame Frame = new JFrame("Error");
               JOptionPane.showMessageDialog(Frame, "ID does not exist", "Error", JOptionPane.ERROR_MESSAGE);  
            }
            } catch (SQLException e) {
            e.printStackTrace();
        }
    } 
    public static void updateplayer(int playerId,String playerName,int playerage,String playerclub,int playergoals,String playerpos) throws Exception{
        
        
        try {
             // Έλεγχος αν το ID υπάρχει ήδη στη βάση δεδομένων
            String findQuery = "SELECT COUNT(*) FROM players WHERE PLAYERID = ?";
            dbConnection = DriverManager.getConnection(url, username, passwd);
            PreparedStatement findStatement = dbConnection.prepareStatement(findQuery);
            findStatement.setInt(1, playerId);
            ResultSet resultSet = findStatement.executeQuery();

            if (resultSet.next() && resultSet.getInt(1) > 0) {
                // Εισαγωγή δεδομένων στη βάση δεδομένων
                String query1 = "UPDATE PLAYERS SET NAME=?, AGE=?, CLUB=?, GOALSSCORED=?, POSITION=? WHERE PLAYERID=?";
                dbConnection = DriverManager.getConnection (url, username, passwd);
                PreparedStatement preparedstatement1 = dbConnection.prepareStatement(query1);
           
            
                preparedstatement1.setString(1, playerName);
                preparedstatement1.setInt(2, playerage);
                preparedstatement1.setString(3, playerclub);
                preparedstatement1.setInt(4, playergoals);
                preparedstatement1.setString(5, playerpos);
                preparedstatement1.setInt(6, playerId);
                preparedstatement1.executeUpdate();
            }else{
               JFrame Frame = new JFrame("Error");
               JOptionPane.showMessageDialog(Frame, "ID does not exist", "Error", JOptionPane.ERROR_MESSAGE);  
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
   
    public static void insertmatch(int Id,String home,String away,String date,String time,String score) throws Exception{
        
        
        try {
             // Έλεγχος αν το ID υπάρχει ήδη στη βάση δεδομένων
            String findQuery = "SELECT COUNT(*) FROM footballmatches WHERE MatchID = ?";
            dbConnection = DriverManager.getConnection(url, username, passwd);
            PreparedStatement findStatement = dbConnection.prepareStatement(findQuery);
            findStatement.setInt(1, Id);
            ResultSet resultSet = findStatement.executeQuery();

            if (resultSet.next() && resultSet.getInt(1) > 0) {
                // Αν το ID υπάρχει ήδη στη βάση δεδομένων, εμφάνιση μηνύματος λάθους
                JFrame Frame = new JFrame("Error");
                JOptionPane.showMessageDialog(Frame, "ID already exists", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
            // Εισαγωγή δεδομένων στη βάση δεδομένων
            String query1 = "INSERT INTO footballmatches (MatchID, HomeTeam, AwayTeam, MatchDate, MatchTime, MatchScore) VALUES (?, ?, ?, ?, ?, ?)";
            dbConnection = DriverManager.getConnection (url, username, passwd);
            PreparedStatement preparedstatement1 = dbConnection.prepareStatement(query1);
           
            preparedstatement1.setInt(1, Id);
            preparedstatement1.setString(2, home);
            preparedstatement1.setString(3, away);
            preparedstatement1.setString(4, date);
            preparedstatement1.setString(5, time);
            preparedstatement1.setString(6, score);
            preparedstatement1.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }   
    public static void deletematch(int Id){
        try{
             // Έλεγχος αν το ID υπάρχει ήδη στη βάση δεδομένων
            String findQuery = "SELECT COUNT(*) FROM footballmatches WHERE MatchID = ?";
            dbConnection = DriverManager.getConnection(url, username, passwd);
            PreparedStatement findStatement = dbConnection.prepareStatement(findQuery);
            findStatement.setInt(1, Id);
            ResultSet resultSet = findStatement.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                String query1="DELETE FROM FOOTBALLMATCHES WHERE MatchID=?";
                dbConnection = DriverManager.getConnection (url, username, passwd);
                PreparedStatement preparedstatement1 = dbConnection.prepareStatement(query1);
                preparedstatement1.setInt(1, Id);
                preparedstatement1.executeUpdate();
            }else{
                 JFrame Frame = new JFrame("Error");
                JOptionPane.showMessageDialog(Frame, "ID does not exist", "Error", JOptionPane.ERROR_MESSAGE); 
            }
            } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void updatematch(int Id,String home,String away,String date,String time,String score) throws Exception{
        
        
        try {
             // Έλεγχος αν το ID υπάρχει ήδη στη βάση δεδομένων
            String findQuery = "SELECT COUNT(*) FROM footballmatches WHERE MatchID = ?";
            dbConnection = DriverManager.getConnection(url, username, passwd);
            PreparedStatement findStatement = dbConnection.prepareStatement(findQuery);
            findStatement.setInt(1, Id);
            ResultSet resultSet = findStatement.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                // Εισαγωγή δεδομένων στη βάση δεδομένων
                String query1 = "UPDATE footballmatches SET   HomeTeam=?, AwayTeam=?, MatchDate=?, MatchTime=?, MatchScore=? WHERE MatchID=?";
                dbConnection = DriverManager.getConnection (url, username, passwd);
                PreparedStatement preparedstatement1 = dbConnection.prepareStatement(query1);
           
            
                preparedstatement1.setString(1, home);
                preparedstatement1.setString(2, away);
                preparedstatement1.setString(3, date);
                preparedstatement1.setString(4, time);
                preparedstatement1.setString(5, score);
                preparedstatement1.setInt(6, Id);
                preparedstatement1.executeUpdate();
            }else{
                 JFrame Frame = new JFrame("Error");
                JOptionPane.showMessageDialog(Frame, "ID does not exist", "Error", JOptionPane.ERROR_MESSAGE); 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
      
    public static void insertscore(int Id,String team,int wins,int draws,int losses,int points) throws Exception{
        
        
        try {
             // Έλεγχος αν το ID υπάρχει ήδη στη βάση δεδομένων
            String findQuery = "SELECT COUNT(*) FROM scoreboard WHERE Game_id = ?";
            dbConnection = DriverManager.getConnection(url, username, passwd);
            PreparedStatement findStatement = dbConnection.prepareStatement(findQuery);
            findStatement.setInt(1, Id);
            ResultSet resultSet = findStatement.executeQuery();

            if (resultSet.next() && resultSet.getInt(1) > 0) {
                // Αν το ID υπάρχει ήδη στη βάση δεδομένων, εμφάνιση μηνύματος λάθους
                JFrame Frame = new JFrame("Error");
                JOptionPane.showMessageDialog(Frame, "ID already exists", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
            // Εισαγωγή δεδομένων στη βάση δεδομένων
            String query1 = "INSERT INTO scoreboard (Game_id, S_team, Wins, Draws, Losses, Points) VALUES (?, ?, ?, ?, ?, ?)";
            dbConnection = DriverManager.getConnection (url, username, passwd);
            PreparedStatement preparedstatement1 = dbConnection.prepareStatement(query1);
           
            preparedstatement1.setInt(1, Id);
            preparedstatement1.setString(2, team);
            preparedstatement1.setInt(3, wins);
            preparedstatement1.setInt(4, draws);
            preparedstatement1.setInt(5, losses);
            preparedstatement1.setInt(6, points);
            preparedstatement1.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }  
    public static void deletescore(int Id){
        try{
             // Έλεγχος αν το ID υπάρχει ήδη στη βάση δεδομένων
            String findQuery = "SELECT COUNT(*) FROM scoreboard WHERE Game_id = ?";
            dbConnection = DriverManager.getConnection(url, username, passwd);
            PreparedStatement findStatement = dbConnection.prepareStatement(findQuery);
            findStatement.setInt(1, Id);
            ResultSet resultSet = findStatement.executeQuery();

            if (resultSet.next() && resultSet.getInt(1) > 0) {
                String query1="DELETE FROM SCOREBOARD WHERE GAME_ID=?";
                dbConnection = DriverManager.getConnection (url, username, passwd);
                PreparedStatement preparedstatement1 = dbConnection.prepareStatement(query1);
                preparedstatement1.setInt(1, Id);
                preparedstatement1.executeUpdate();
            }else{
                JFrame Frame = new JFrame("Error");
                JOptionPane.showMessageDialog(Frame, "ID does not exist", "Error", JOptionPane.ERROR_MESSAGE); 
                    }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    } 
    public static void updatescore(int Id,String team,int wins,int draws,int losses,int points) throws Exception{
        
        
        try {
             // Έλεγχος αν το ID υπάρχει ήδη στη βάση δεδομένων
            String findQuery = "SELECT COUNT(*) FROM scoreboard WHERE Game_id = ?";
            dbConnection = DriverManager.getConnection(url, username, passwd);
            PreparedStatement findStatement = dbConnection.prepareStatement(findQuery);
            findStatement.setInt(1, Id);
            ResultSet resultSet = findStatement.executeQuery();

            if (resultSet.next() && resultSet.getInt(1) > 0) {
                // Εισαγωγή δεδομένων στη βάση δεδομένων
                String query1 = "UPDATE scoreboard SET  S_team=?, Wins=?, Draws=?, Losses=?, Points=? WHERE Game_id=?";
                dbConnection = DriverManager.getConnection (url, username, passwd);
                PreparedStatement preparedstatement1 = dbConnection.prepareStatement(query1);
           
            
                preparedstatement1.setString(1, team);
                preparedstatement1.setInt(2, wins);
                preparedstatement1.setInt(3, draws);
                preparedstatement1.setInt(4, losses);
                preparedstatement1.setInt(5, points);
                preparedstatement1.setInt(6, Id);
                preparedstatement1.executeUpdate();
            }else{
                JFrame Frame = new JFrame("Error");
                JOptionPane.showMessageDialog(Frame, "ID does not exist", "Error", JOptionPane.ERROR_MESSAGE); 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    public static void inserttrophies(int id,String team,int premier,int carabao,int leaguecup){
        try {
             // Έλεγχος αν το ID υπάρχει ήδη στη βάση δεδομένων
            String findQuery = "SELECT COUNT(*) FROM trophies WHERE trid = ?";
            dbConnection = DriverManager.getConnection(url, username, passwd);
            PreparedStatement findStatement = dbConnection.prepareStatement(findQuery);
            findStatement.setInt(1, id);
            ResultSet resultSet = findStatement.executeQuery();

            if (resultSet.next() && resultSet.getInt(1) > 0) {
                // Αν το ID υπάρχει ήδη στη βάση δεδομένων, εμφάνιση μηνύματος λάθους
                JFrame Frame = new JFrame("Error");
                JOptionPane.showMessageDialog(Frame, "ID already exists", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
            // Εισαγωγή δεδομένων στη βάση δεδομένων
            String query1 = "INSERT INTO trophies (trid, team, premier, carabao, leaguecup) VALUES (?, ?, ?, ?, ?)";
            dbConnection = DriverManager.getConnection (url, username, passwd);
            PreparedStatement preparedstatement1 = dbConnection.prepareStatement(query1);
           
            preparedstatement1.setInt(1, id);
            preparedstatement1.setString(2, team);
            preparedstatement1.setInt(3, premier);
            preparedstatement1.setInt(4, carabao);
            preparedstatement1.setInt(5, leaguecup);
            
            preparedstatement1.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void deletetrophies(int id){
        try {
            // Έλεγχος αν το ID υπάρχει ήδη στη βάση δεδομένων
            String findQuery = "SELECT COUNT(*) FROM trophies WHERE trid = ?";
            dbConnection = DriverManager.getConnection(url, username, passwd);
            PreparedStatement findStatement = dbConnection.prepareStatement(findQuery);
            findStatement.setInt(1, id);
            ResultSet resultSet = findStatement.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                // Διαγραφή δεδομένων στη βάση δεδομένων
                String query1 = "DELETE  FROM trophies WHERE trid=? ";
                dbConnection = DriverManager.getConnection (url, username, passwd);
                PreparedStatement preparedstatement1 = dbConnection.prepareStatement(query1);
                preparedstatement1.setInt(1, id);
                preparedstatement1.executeUpdate();
             } else {
                JFrame Frame = new JFrame("Error");
                JOptionPane.showMessageDialog(Frame, " ID does not exist", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void updatetrophies(int id,String team,int premier,int carabao,int leaguecup){
        try {
            // Έλεγχος αν το ID υπάρχει ήδη στη βάση δεδομένων
            String findQuery = "SELECT COUNT(*) FROM trophies WHERE trid = ?";
            dbConnection = DriverManager.getConnection(url, username, passwd);
            PreparedStatement findStatement = dbConnection.prepareStatement(findQuery);
            findStatement.setInt(1, id);
            ResultSet resultSet = findStatement.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                // Εισαγωγή δεδομένων στη βάση δεδομένων
                String query1 = "UPDATE trophies SET  team=?, premier=?, carabao=?, leaguecup=? WHERE id=?";
                dbConnection = DriverManager.getConnection (url, username, passwd);
                PreparedStatement preparedstatement1 = dbConnection.prepareStatement(query1);
           
                preparedstatement1.setString(1, team);
                preparedstatement1.setInt(2, premier);
                preparedstatement1.setInt(3, carabao);
                preparedstatement1.setInt(4, leaguecup);
                preparedstatement1.setInt(5, id);
                preparedstatement1.executeUpdate();
            } else {
                JFrame Frame = new JFrame("Error");
                JOptionPane.showMessageDialog(Frame, "ID does not exist", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void addlogfie(int id, Date date, String action, int tid, String tableName){
        try{
            String query1 = "INSERT INTO logfile (lid, ldate, action, tid, tablename) VALUES (?, ?, ?, ?, ?)";
            dbConnection = DriverManager.getConnection (url, username, passwd);
            PreparedStatement preparedstatement1 = dbConnection.prepareStatement(query1);
            preparedstatement1.setInt(1, id);
            preparedstatement1.setDate(2, new java.sql.Date(date.getTime()));
            preparedstatement1.setString(3, action);
            preparedstatement1.setInt(4, tid);
            preparedstatement1.setString(5, tableName);
            preparedstatement1.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public  Vector<String> fetchIds(String tableName){
        Vector<String> ids = new Vector<>();
        try {
            
            dbConnection = DriverManager.getConnection(url, username, passwd);
            switch(tableName){
                case("TEAMS"):
                    selectString = "SELECT T_ID FROM " + tableName;
                    break;
                case("PLAYERS"):
                    selectString = "SELECT PLAYERID FROM " + tableName;
                    break;
                case("SCOREBOARD"):
                    selectString = "SELECT GAME_ID FROM " + tableName;
                    break;    
                case("FOOTBALLMATCHES"):
                    selectString = "SELECT MATCHID FROM " + tableName;
                    break;
                case("TROPHIES"):
                    selectString = "SELECT TRID FROM " + tableName;
                    break; 
            }
            
            statement = dbConnection.createStatement();
            rs = statement.executeQuery(selectString);

            while (rs.next()) {
                String id = rs.getString(1);
                 ids.add(id);
            }

            rs.close();
            statement.close();
            dbConnection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ids;
    }



        public static void backupToLocalSQLite() {
            String sqliteUrl = "jdbc:sqlite:football.db";
            
            System.out.println("=== ΕΝΑΡΞΗ ΜΕΤΑΦΟΡΑΣ ΔΕΔΟΜΕΝΩΝ ===");
            
            try {
                // Φορτώνουμε τον driver της SQLite ρητά
                Class.forName("org.sqlite.JDBC");
                
                try (Connection oracleConn = DriverManager.getConnection(url, username, passwd);
                    Connection sqliteConn = DriverManager.getConnection(sqliteUrl);
                    Statement oracleStmt = oracleConn.createStatement()) {
                    
                    System.out.println("Επιτυχής σύνδεση και στις δύο βάσεις!");
                    
                    // 1. Δημιουργία τοπικού πίνακα
                    try (Statement sqliteStmt = sqliteConn.createStatement()) {
                        sqliteStmt.execute("CREATE TABLE IF NOT EXISTS teams (" +
                                "T_ID INTEGER PRIMARY KEY, " +
                                "T_NAME TEXT, " +
                                "T_FIELD TEXT, " +
                                "T_NUM INTEGER)");
                    }
                    
                    // 2. Μεταφορά
                    try (ResultSet rs = oracleStmt.executeQuery("SELECT * FROM TEAMS");
                        PreparedStatement insertStmt = sqliteConn.prepareStatement(
                                "INSERT OR REPLACE INTO teams (T_ID, T_NAME, T_FIELD, T_NUM) VALUES (?, ?, ?, ?)")) {
                        
                        int count = 0;
                        while (rs.next()) {
                            insertStmt.setInt(1, rs.getInt("T_ID"));
                            insertStmt.setString(2, rs.getString("T_NAME"));
                            insertStmt.setString(3, rs.getString("T_FIELD"));
                            insertStmt.setInt(4, rs.getInt("T_NUM"));
                            insertStmt.executeUpdate();
                            count++;
                        }
                        System.out.println(">>> ΕΠΙΤΥΧΙΑ: Μεταφέρθηκαν " + count + " ομάδες στην τοπική βάση!");
                    }
                }
            } catch (Exception e) {
                System.err.println("=== ΚΑΤΙ ΠΗΓΕ ΛΑΘΟΣ ΣΤΗ ΜΕΤΑΦΟΡΑ ===");
                e.printStackTrace(); // Αυτό θα μας αναγκάσει να δούμε το λάθος στο PowerShell
            }
        }
}

