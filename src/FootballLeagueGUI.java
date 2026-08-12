import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import java.sql.*;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import oracle.jdbc.OracleTypes;

public class FootballLeagueGUI extends javax.swing.JFrame {

    private JPanel northPanel, westPanel, centerPanel, eastPanel, southPanel;
    private JLabel description;
    private RoundedButton teamsBtn, playersBtn, scoreboardTableBtn, logfile, gameDayBtn, throphiesBtn, addBtn, deleteBtn, updateBtn, mostPlayersBtn, lessPlayersBtn, mostGoalsBtn, olderBtn, matchdayBtn, mostPLBtn, mostCarabapBtn, leagueTableBtn, moreLossesBtn, orderByDateBtn;
    private JScrollPane scrollPane;
    private JTable table;
    private JTextArea message, newTextArea;
    private GridBagConstraints c;
    private Component[] components;
    private DatabaseTable dbTable;
    private Connection connection;
    private CallableStatement cs;
    private String result, lastClickedButton;
    private final String[] teamsColumnNames = {"Id", "Name", "Field", "Number Of Players"};
    private final String[] playersColumnNames = {"Id", "Name", "Age","Club", "Goals", "Position"};
    private final String[] scoreboardColumnNames = {"Id", "Team", "Wins", "Draws", "Losses", "Points"};
    private final String[] footbllMatchesColumnNames = {"Id", "Home Team", "Away Team", "Date", "Time", "Score"};
    private final String[] trophiesColumnNames = {"Id", "Team", "Premier League", "Carabao Cup", "FA Cup"};
    private final String[] logfileColumnNames = {"Operation", "Date", "Table id", "Table Name"};
    public FootballLeagueGUI() {
        initComponentsNew();
    }
    
    private void initComponentsNew(){
        
        //Making the size of application's window same for every pc
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int screenWidth = (int) toolkit.getScreenSize().getWidth();
        int screenHeight = (int) toolkit.getScreenSize().getHeight();
        
        double widthFraction = 0.69;
        double heightFraction = 0.6;
        
        int windowWidth = (int)(screenWidth * widthFraction);
        int windowHeight = (int)(screenHeight * heightFraction);
        
       
        
        setSize(windowWidth,windowHeight);
        setTitle("Premier League Database");
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        //Making 5 panels in BorderLayout
        setLayout(new BorderLayout());
        
        northPanel = new JPanel();
        westPanel = new JPanel();
        centerPanel = new JPanel();
        eastPanel = new JPanel();
        southPanel = new JPanel();
       
        
        //Setting dimensions of every panel
        northPanel.setPreferredSize(new Dimension(windowWidth, (int)(windowHeight * 0.1)));
        westPanel.setPreferredSize(new Dimension((int)(windowWidth * 0.2), (int)(windowHeight * 0.8)));
        centerPanel.setPreferredSize(new Dimension((int)(windowWidth * 0.6), (int)(windowHeight * 0.8)));
        eastPanel.setPreferredSize(new Dimension((int)(windowWidth * 0.2), (int)(windowHeight * 0.8)));
        southPanel.setPreferredSize(new Dimension(windowWidth, (int)(windowHeight * 0.1)));
       
       
        
        
        
        northPanel.setBackground(Color.white);
        westPanel.setBackground(Color.white);
        eastPanel.setBackground(Color.white);
        southPanel.setBackground(Color.white);
        
        westPanel.setBackground(new Color(40, 100, 140));
        northPanel.setBackground(new Color(20, 80, 120));
        eastPanel.setBackground(new Color(40, 100, 140));
        southPanel.setBackground(new Color(20, 80, 120));
        
        
        add(northPanel,BorderLayout.NORTH);
        add(westPanel,BorderLayout.WEST);
        add(centerPanel,BorderLayout.CENTER);
        add(eastPanel,BorderLayout.EAST);
        add(southPanel,BorderLayout.SOUTH);
        
        northPanel.setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 0;
        description = new JLabel("This is an application which gives you access in Premiere League's Database");
        Font font1 = new Font(description.getFont().getName(), Font.PLAIN, 21); 
        description.setFont(font1);
        description.setForeground(Color.white);
        northPanel.add(description, c);
        
        
        
        //In west panel user can select every array of our database
        westPanel.setLayout(new GridBagLayout());
        
        c.insets = new Insets(15,17,15,17);
        
        teamsBtn = new RoundedButton("Teams"); 
        c.gridy = 0;
        westPanel.add(teamsBtn,c); 
        
        playersBtn = new RoundedButton("Players");   
        c.gridy = 1;
        westPanel.add(playersBtn,c);
        
        scoreboardTableBtn = new RoundedButton("Scoreboard");
        c.gridy = 2;
        westPanel.add(scoreboardTableBtn,c);
        
        gameDayBtn = new RoundedButton("Game Days");
        c.gridy = 3;
        westPanel.add(gameDayBtn,c);
        
        throphiesBtn = new RoundedButton("Throphies");
        c.gridy = 4;
        westPanel.add(throphiesBtn,c);
        
        teamsBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                clear(centerPanel);
                createTable(teamsColumnNames);
                teamsQueries();
                databaseConnect("TEAMS");   
                lastClickedButton = "TEAMS";
            }
        });
        
        playersBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                clear(centerPanel);
                createTable(playersColumnNames);
                playersQueries();
                databaseConnect("PLAYERS"); 
                lastClickedButton = "PLAYERS";
            }
        });
        
        scoreboardTableBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                clear(centerPanel);
                createTable(scoreboardColumnNames);
                scoreboardQueries();
                databaseConnect("SCOREBOARD");
                lastClickedButton = "SCOREBOARD";
            }
        });
        
        gameDayBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                clear(centerPanel);
                createTable(footbllMatchesColumnNames);
                gameDayQueries();
                databaseConnect("FOOTBALLMATCHES");
                lastClickedButton = "FOOTBALLMATCHES";
            }
        });
        
        throphiesBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                clear(centerPanel);
                createTable(trophiesColumnNames);
                throphiesQueries();
                databaseConnect("TROPHIES");
                lastClickedButton = "TROPHIES";
            }
        });
        
        
         
       
        //In center panel will be displayed info from selected array
        centerPanel.setLayout(new BorderLayout());
        message = new JTextArea("\n\n\n\n\n\n\n"
                + "\t\tSelect a table to see its info.");
        Font font = new Font(message.getFont().getName(), Font.PLAIN, 21); 
        message.setFont(font);
        
        message.setLineWrap(true);
        message.setWrapStyleWord(true);
        message.setEditable(false);
        scrollPane = new JScrollPane(message);
        centerPanel.add(scrollPane);
             //East panel has some queries differnent for each table
        eastPanel.setLayout(new GridBagLayout());
        //South panel has the three classic SQL queries add, delete, update
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 4;
        
        addBtn = new RoundedButton("Add");
        southPanel.add(addBtn,c);  
        addBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                BasicDatabaseButtons db = new BasicDatabaseButtons();
                db.setFrame("add", lastClickedButton);
            }
        });
        
        deleteBtn = new RoundedButton("Delete");
        southPanel.add(deleteBtn,c);
        deleteBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                BasicDatabaseButtons db = new BasicDatabaseButtons();
                db.setFrame("delete", lastClickedButton);
            }
        });
        updateBtn = new RoundedButton("Update");
        southPanel.add(updateBtn,c);
        updateBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                BasicDatabaseButtons db = new BasicDatabaseButtons();
                db.setFrame("update", lastClickedButton);
            }
        });
        
        
        
        logfile = new RoundedButton("Log file");
        southPanel.add(logfile, c);
        logfile.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                clear(centerPanel);
                clear(eastPanel);
                createTable(logfileColumnNames);
                databaseConnect("LOGFILE");
            }
        }); 
    }   
    private void teamsQueries(){
        clear(eastPanel);
        
        c.fill = GridBagConstraints.VERTICAL;
        c.gridwidth = 3;
        
        mostPlayersBtn = new RoundedButton("Most players");
        c.gridy = 0;
        eastPanel.add(mostPlayersBtn,c); 
        mostPlayersBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                clear(centerPanel);
               
                message=new JTextArea("\n\n\n\n\n\n\n\n"
                        + "\tThe team with most players in Premier League is " + executePLSQLFunction("most_players()") + ".");
                     
                Font font = new Font(message.getFont().getName(), Font.PLAIN, 20); 
                message.setFont(font);
        
                message.setLineWrap(true);
                message.setWrapStyleWord(true);
                message.setEditable(false);
                scrollPane = new JScrollPane(message);
                centerPanel.add(scrollPane);
            }
        });
        
        lessPlayersBtn = new RoundedButton("Less players");
        c.gridy = 1;
        eastPanel.add(lessPlayersBtn,c); 
        lessPlayersBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                clear(centerPanel);
              
                message=new JTextArea("\n\n\n\n\n\n\n\n"
                       + "\tThe team with less players in Premier League is " + executePLSQLFunction("less_players()") + ".");                   
                Font font = new Font(message.getFont().getName(), Font.PLAIN, 20); 
                message.setFont(font);
        
                message.setLineWrap(true);
                message.setWrapStyleWord(true);
                message.setEditable(false);
                scrollPane = new JScrollPane(message);
                centerPanel.add(scrollPane);
            }
        });
    }
    
    private void playersQueries(){
        clear(eastPanel);
        c.fill = GridBagConstraints.VERTICAL;
        c.gridwidth = 3;
        
        mostGoalsBtn = new RoundedButton("Most goals");
        c.gridy = 0;
        eastPanel.add(mostGoalsBtn,c);
        mostGoalsBtn.addActionListener(new ActionListener(){
            
            @Override
            public void actionPerformed(ActionEvent e){   
                clear(centerPanel);
                
                //removeAllComponetsAndSetTextArea(centerPanel,executePLSQLFunction("most_goals()", false, null)); 
                
                message=new JTextArea("\n\n\n\n\n\n\n\n"
                        + "\tThe player with most goals in Premier League is " + executePLSQLFunction("most_goals()") + ".");                   
                Font font = new Font(message.getFont().getName(), Font.PLAIN, 20); 
                message.setFont(font);
        
                message.setLineWrap(true);
                message.setWrapStyleWord(true);
                message.setEditable(false);
                scrollPane = new JScrollPane(message);
                centerPanel.add(scrollPane);
                
            }
        });
        
        olderBtn = new RoundedButton("Older player");
        c.gridy = 1;
        eastPanel.add(olderBtn,c);   
        olderBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                clear(centerPanel);
               
                message=new JTextArea("\n\n\n\n\n\n\n\n"
                        + "\tThe older player in Premier League is " + executePLSQLFunction("older_player()") + ".");                   
                Font font = new Font(message.getFont().getName(), Font.PLAIN, 20); 
                message.setFont(font);
        
                message.setLineWrap(true);
                message.setWrapStyleWord(true);
                message.setEditable(false);
                scrollPane = new JScrollPane(message);
                centerPanel.add(scrollPane);
            }
        });
    } 
    private void scoreboardQueries(){
        clear(eastPanel);
        
        c.fill = GridBagConstraints.VERTICAL;
        c.gridwidth = 3;
        
        leagueTableBtn = new RoundedButton("Sorted teams");
        c.gridy = 0;
        eastPanel.add(leagueTableBtn,c);
        leagueTableBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear(centerPanel);
                displayFunctionsInTable("getsortedteams()");
            }
        });
        
        moreLossesBtn = new RoundedButton("More losses");
        c.gridy = 1;
        eastPanel.add(moreLossesBtn,c);
        moreLossesBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear(centerPanel);
               displayFunctionsInTable("more_losses()");   
            }
        });
    }
    
    private void gameDayQueries(){
        clear(eastPanel);

        c.fill = GridBagConstraints.VERTICAL;
        c.gridwidth = 3;
        
        matchdayBtn = new RoundedButton("Match by time");
        c.gridy = 0;
        eastPanel.add(matchdayBtn,c);
        matchdayBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                clear(centerPanel);
                displayFunctionsInTable("order_by_time()");
            }
        });
        
        orderByDateBtn = new RoundedButton("Match by date");
        c.gridy = 1;
        eastPanel.add(orderByDateBtn,c);
        orderByDateBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                clear(centerPanel);
                displayFunctionsInTable("order_by_date()");
            }
        });
        
    }
    
    private void throphiesQueries(){
        clear(eastPanel);
              
        c.fill = GridBagConstraints.VERTICAL;
        c.gridwidth = 3;
        
        mostPLBtn = new RoundedButton("Most Premier Leagues");
        c.gridy = 0;
        mostPLBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                clear(centerPanel);
               
                message=new JTextArea("\n\n\n\n\n\n\n\n"
                        + "\tThe team with most Premier League trophies is " + executePLSQLFunction("most_premier()") + ".");                   
                Font font = new Font(message.getFont().getName(), Font.PLAIN, 20); 
                message.setFont(font);
        
                message.setLineWrap(true);
                message.setWrapStyleWord(true);
                message.setEditable(false);
                scrollPane = new JScrollPane(message);
                centerPanel.add(scrollPane);
            }
        });
        
        eastPanel.add(mostPLBtn,c);
        mostCarabapBtn = new RoundedButton("Most Carabao cups");
        c.gridy = 1;
        eastPanel.add(mostCarabapBtn,c);
        mostCarabapBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                clear(centerPanel);
                
                message=new JTextArea("\n\n\n\n\n\n\n\n"
                        + "\tThe team with most Carabao cups is " + executePLSQLFunction("most_carabao()") + ".");                   
                Font font = new Font(message.getFont().getName(), Font.PLAIN, 20); 
                message.setFont(font);
        
                message.setLineWrap(true);
                message.setWrapStyleWord(true);
                message.setEditable(false);
                scrollPane = new JScrollPane(message);
                centerPanel.add(scrollPane);
            }
        });
        
    }

    
    //Shows the table
    private void createTable(String [] tableColumnNames){
        dbTable = new DatabaseTable(tableColumnNames.length, tableColumnNames);
        table = new JTable(dbTable);
        scrollPane.setViewportView(table);
        centerPanel.add(scrollPane, BorderLayout.CENTER);                 
    }
    
    //Makes the connection with database
    private void databaseConnect(String tableName){
        DatabaseDriver db = new DatabaseDriver();
        try{
            db.showTable(tableName, dbTable);
        }catch(ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }
    
    //Clears panels
    private void clear(JPanel panel){
        panel.removeAll();
        panel.revalidate();
        panel.repaint();
    }
    
    //Settes text in massage text area
    private void setTextArea(String text){
        newTextArea = new JTextArea(text);
        newTextArea.setLineWrap(true);
        newTextArea.setWrapStyleWord(true);
        newTextArea.setEditable(false);
    
        scrollPane = new JScrollPane(newTextArea);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        centerPanel.revalidate();
        centerPanel.repaint();
    }
    
    
    private String executePLSQLFunction(String function){
        try{
            connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.6.21:1521:dblabs","iee2020076","Giannislapousi7*");
            cs = connection.prepareCall("{? = call "+ function +" }");
            cs.registerOutParameter(1, OracleTypes.VARCHAR);
            cs.execute();
            result = cs.getString(1);
            return result;
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (cs != null) {
                    cs.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return "error";
    }
    
    //Shows the results in table and not in text area
    private void displayFunctionsInTable(String functionName) {
        clear(centerPanel);
        createTable(scoreboardColumnNames);

        try {
            connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.6.21:1521:dblabs", "iee2020076", "Giannislapousi7*");
            switch(functionName){
                case ("getsortedteams()"):
                    createTable(scoreboardColumnNames);
                    cs = connection.prepareCall("{? = call " + functionName + "}");
                    cs.registerOutParameter(1, OracleTypes.CURSOR);
                    cs.execute();

                    ResultSet resultSet = (ResultSet) cs.getObject(1);
                    Object[] rowData = null;
                    while (resultSet.next()) {
                        rowData =new Object[] {
                                resultSet.getInt(1),
                                resultSet.getString(2),
                                resultSet.getInt(3),
                                resultSet.getInt(4),
                                resultSet.getInt(5),
                                resultSet.getInt(6)
                        };
                       
                        dbTable.addRow(rowData);
                    }
                    break;  
                case ("more_losses()"):
                    createTable(scoreboardColumnNames);
                    cs = connection.prepareCall("{? = call " + functionName + "}");
                    cs.registerOutParameter(1, OracleTypes.CURSOR);
                    cs.execute();
                    resultSet = (ResultSet) cs.getObject(1);
                    while (resultSet.next()) {
                        rowData = new Object[]{
                                resultSet.getInt(1),
                                resultSet.getString(2),
                                resultSet.getInt(3),
                                resultSet.getInt(4),
                                resultSet.getInt(5),
                                resultSet.getInt(6)
                        };
                        
                        dbTable.addRow(rowData);     
                    } 
                    break;
                case("order_by_time()"):
                    createTable(footbllMatchesColumnNames);
                    cs = connection.prepareCall("{? = call " + functionName + "}");
                    cs.registerOutParameter(1, OracleTypes.CURSOR);
                    cs.execute();
                    resultSet = (ResultSet) cs.getObject(1);
                    while (resultSet.next()) {
                        rowData = new Object[]{
                                resultSet.getInt(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(5),
                                resultSet.getString(6)
                        };
                        dbTable.addRow(rowData);     
                    } 
                    break;
                case("order_by_date()"):
                    createTable(footbllMatchesColumnNames);
                    cs = connection.prepareCall("{? = call " + functionName + "}");
                    cs.registerOutParameter(1, OracleTypes.CURSOR);
                    cs.execute();
                    resultSet = (ResultSet) cs.getObject(1);
                    while (resultSet.next()) {
                        rowData = new Object[]{
                                resultSet.getInt(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(5),
                                resultSet.getString(6)
                        };
                        dbTable.addRow(rowData);     
                    } 
                    break;
                    
            }
            table.setModel(dbTable);
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
    
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FootballLeagueGUI().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}