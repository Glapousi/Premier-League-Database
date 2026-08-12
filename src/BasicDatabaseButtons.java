

import com.sun.jdi.connect.spi.Connection;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author giorgos
 */
public class BasicDatabaseButtons {
    private JFrame frame;
    private JPanel panel, panelButtons, buttonsContainer ;
    private JLabel idLabel, AgeLabel, GoalsLabel, NameLabel, carabaoLabel, leagueLabel, teamLabel,fieldLabel, premierLabel, numLabel, nameLabel, ageLabel, clubLabel, timeLabel, goalsLabel,scoreLabel, posLabel, winsLabel, dateLabel, drawsLabel, lossesLabel, pointsLabel, teamAwayLabel;
    private JTextField idField, AgeField, NameField, GoalsField, leagueField, premierField, carabaoField, teamField, fieldField, numField, ageField, timeField, nameField, scoreField, clubField, goalsField, posField, winsField, dateField, drawsField, lossesField, pointsField, teamAwayField;
    private RoundedButton submitBtn, closeBtn;
    private Font labelFont, labelFont1, labelFont2, labelFont3, labelFont4, labelFont5;
    private JComboBox<String> idComboBox;
    static String driverClassName = "oracle.jdbc.OracleDriver" ;
    static String url = "jdbc:oracle:thin:@192.168.6.21:1521:dblabs" ;
    static java.sql.Connection dbConnection = null;
    static String username = "iee2020076";
    static String passwd = "Giannislapousi7*";
    static Statement  statement = null;
    static ResultSet rs = null;
    private String selectString;
    private JScrollPane idScrollPane ;
    
    public void setFrame(String buttonAction, String tableName){
        switch(tableName){
            case("TEAMS"):
                ButtonsTeams(buttonAction);
                break;
            case("PLAYERS"):
                ButtonsPlayers(buttonAction);
                break;
            case("SCOREBOARD"):
                ButtonsScore(buttonAction);
                break;
            case("FOOTBALLMATCHES"):
                ButtonsMatch(buttonAction);
                break;
            case("TROPHIES"):
                ButtonsTrophies(buttonAction);
                break;
        }
       
       
    }
    
    private void ButtonsPlayers(String buttonAction){
        frame = new JFrame();
       
        if(buttonAction.equals("add")){
            frame.setTitle("Add player");
            
            panel=new JPanel(new GridLayout(7, 2, 40, 20));
            panel.setBackground(new Color(40, 100, 140));
            idLabel = new JLabel("ID:");
            idField = new JTextField();
            idField.setPreferredSize(new Dimension(10, 30));
            labelFont = idLabel.getFont();
            idLabel.setFont(new Font(labelFont.getFontName(), Font.PLAIN, 22));
            idLabel.setForeground(Color.white);
            
            NameLabel = new JLabel("Name:");
            NameField = new JTextField();
            NameField.setPreferredSize(new Dimension(30, 30));
            labelFont1 = NameLabel.getFont();
            NameLabel.setFont(new Font(labelFont1.getFontName(), Font.PLAIN, 22));
            NameLabel.setForeground(Color.white);
            
            AgeLabel = new JLabel("Age:");
            AgeField = new JTextField();
            AgeField.setPreferredSize(new Dimension(30, 30));
            labelFont2 = AgeLabel.getFont();
            AgeLabel.setFont(new Font(labelFont2.getFontName(), Font.PLAIN, 22));
            AgeLabel.setForeground(Color.white);
            
            clubLabel = new JLabel("Club:");
            clubField = new JTextField();
            clubField.setPreferredSize(new Dimension(30, 30));
            labelFont3 = clubLabel.getFont();
            clubLabel.setFont(new Font(labelFont3.getFontName(), Font.PLAIN, 22));
            clubLabel.setForeground(Color.white);
            
            GoalsLabel = new JLabel("Goals:");
            GoalsField = new JTextField();
            GoalsField.setPreferredSize(new Dimension(30, 30));
            labelFont4 = GoalsLabel.getFont();
            GoalsLabel.setFont(new Font(labelFont4.getFontName(), Font.PLAIN, 22));
            GoalsLabel.setForeground(Color.white);
            
            posLabel = new JLabel("Position:");
            posField = new JTextField();
            posField.setPreferredSize(new Dimension(30, 30));
            labelFont5 = posLabel.getFont();
            posLabel.setFont(new Font(labelFont5.getFontName(), Font.PLAIN, 22));
            posLabel.setForeground(Color.white);
            buttonsContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 70, 22)); 
            buttonsContainer.setBackground(new Color(40, 100, 140));
            submitBtn = new RoundedButton("Submit");
            closeBtn=new RoundedButton("close");

            submitBtn.setPreferredSize(new Dimension(90, 50));
            closeBtn.setPreferredSize(new Dimension(90, 50));
            buttonsContainer.add(submitBtn);
            buttonsContainer.add(closeBtn);
            
            
            Border margin = new EmptyBorder(40, 100, 20, 100);
            panel.setBorder(margin);
            
          
            
            
            panel.add(idLabel);
            panel.add(idField);
            panel.add(NameLabel);
            panel.add(NameField);
            panel.add(AgeLabel);
            panel.add(AgeField);
            panel.add(clubLabel);
            panel.add(clubField);
            panel.add(GoalsLabel);
            panel.add(GoalsField);
            panel.add(posLabel);
            panel.add(posField);
            frame.add(panel, BorderLayout.CENTER);
            
            
            
            
            
            
            submitBtn.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    int id = Integer.parseInt(idField.getText());
                    String name = NameField.getText();
                    int age =Integer.parseInt( AgeField.getText());
                    String club=clubField.getText();
                    int goals =Integer.parseInt( GoalsField.getText());
                    String pos = posField.getText();

                    try {
                        DatabaseDriver.insertplayer(id , name , age ,club, goals,pos);
                        //addtologfile()
                    } catch (Exception ex) {
                        Logger.getLogger(FootballLeagueGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                   frame.dispose();
                }
            });

            closeBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    frame.dispose(); 
                }
           
            });
    
        }else if(buttonAction.equals("delete")){
            frame.setTitle("Delete player");
            panel=new JPanel(new FlowLayout(FlowLayout.CENTER,50,30));
            panel.setBackground(new Color(40, 100, 140));
            idLabel = new JLabel("ID:");
            idField = new JTextField();
            idField.setPreferredSize(new Dimension(70, 30));
            labelFont = idLabel.getFont();
            idLabel.setFont(new Font(labelFont.getFontName(), Font.PLAIN, 22));
            idLabel.setForeground(Color.white);
            buttonsContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 70, 150)); 
            buttonsContainer.setBackground(new Color(40, 100, 140));
            submitBtn = new RoundedButton("Submit");
            closeBtn=new RoundedButton("Close");

            submitBtn.setPreferredSize(new Dimension(90, 50));
            closeBtn.setPreferredSize(new Dimension(90, 50));
            buttonsContainer.add(submitBtn);
            buttonsContainer.add(closeBtn);
            
            Border margin = new EmptyBorder(80, 80, 180, 100);
            panel.setBorder(margin);
            
            panel.add(idLabel);
            panel.add(idField);                 
               
            submitBtn.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    int id =Integer.parseInt( idField.getText());
                    
                    try {
                        DatabaseDriver.deleteplayer(id);
                    } catch (Exception ex) {
                        Logger.getLogger(FootballLeagueGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }    
                    frame.dispose();      
                }
            });

            closeBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    frame.dispose();
                }
            });  
            
        }else{
            frame.setTitle("Update player");
            panel=new JPanel(new GridLayout(7, 2, 40, 20));
            panel.setBackground(new Color(40, 100, 140));
            idLabel = new JLabel("ID:");
            idLabel.setForeground(Color.white);
            DatabaseDriver dbDriver = new DatabaseDriver();
            Vector<String> ids  =dbDriver.fetchIds("PLAYERS");

            idComboBox = new JComboBox<>(ids);
           
            
            labelFont = idLabel.getFont();
            idLabel.setFont(new Font(labelFont.getFontName(), Font.PLAIN, 22));

            NameLabel = new JLabel("Name:");
            NameField = new JTextField();
            NameField.setPreferredSize(new Dimension(30, 30));
            labelFont1 = NameLabel.getFont();
            NameLabel.setFont(new Font(labelFont1.getFontName(), Font.PLAIN, 22));
            NameLabel.setForeground(Color.white);
           
            AgeLabel = new JLabel("Age:");
            AgeField = new JTextField();
            AgeField.setPreferredSize(new Dimension(30, 30));
            labelFont2 = AgeLabel.getFont();
            AgeLabel.setFont(new Font(labelFont2.getFontName(), Font.PLAIN, 22));
            AgeLabel.setForeground(Color.white);
                
            clubLabel = new JLabel("Club:");
            clubField = new JTextField();
            clubField.setPreferredSize(new Dimension(30, 30));
            labelFont3 = clubLabel.getFont();
            clubLabel.setFont(new Font(labelFont3.getFontName(), Font.PLAIN, 22));
            clubLabel.setForeground(Color.white);
            
            GoalsLabel = new JLabel("Goals:");
            GoalsField = new JTextField();
            GoalsField.setPreferredSize(new Dimension(30, 30));
            labelFont4 = GoalsLabel.getFont();
            GoalsLabel.setFont(new Font(labelFont4.getFontName(), Font.PLAIN, 22));
            GoalsLabel.setForeground(Color.white);
            
            posLabel = new JLabel("Position:");
            posField = new JTextField();
            posField.setPreferredSize(new Dimension(30, 30));
            labelFont5 = posLabel.getFont();
            posLabel.setFont(new Font(labelFont5.getFontName(), Font.PLAIN, 22));
            posLabel.setForeground(Color.white);
            
            buttonsContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 70, 22)); 
            buttonsContainer.setBackground(new Color(40, 100, 140));
            submitBtn = new RoundedButton("Submit");
            closeBtn=new RoundedButton("close");

            submitBtn.setPreferredSize(new Dimension(90, 50));
            closeBtn.setPreferredSize(new Dimension(90, 50));
            buttonsContainer.add(submitBtn);
            buttonsContainer.add(closeBtn);
            
            Border margin = new EmptyBorder(40, 100, 20, 100);
            panel.setBorder(margin);
            
            panel.add(idLabel);
            panel.add(idComboBox);
            panel.add(NameLabel);
            panel.add(NameField);
            panel.add(AgeLabel);
            panel.add(AgeField);
            panel.add(clubLabel);
            panel.add(clubField);
            panel.add(GoalsLabel);
            panel.add(GoalsField);
            panel.add(posLabel);
            panel.add(posField);
            
            idComboBox.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    // When an ID is selected, populate JTextFields with data
                    populateTextFields((String) idComboBox.getSelectedItem(), "PLAYERS");
                }
            });

            submitBtn.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    int id =Integer.parseInt((String) idComboBox.getSelectedItem());
                    String name = NameField.getText();
                    int age =Integer.parseInt( AgeField.getText());
                    String club = clubField.getText();
                    int goals =Integer.parseInt( GoalsField.getText());
                    String pos = posField.getText();

                    try {
                        DatabaseDriver.updateplayer(id , name , age ,club, goals,pos);
                    } catch (Exception ex) {
                        Logger.getLogger(FootballLeagueGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    frame.dispose();
                }
            });
                     
            closeBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    frame.dispose(); 
                }
            });
        } 
        
        addToFrame(panel, buttonsContainer);
}    
    private void ButtonsTeams(String buttonAction){
        frame = new JFrame();
        
        if(buttonAction.equals("add")){
            frame.setTitle("Add team");
            panel=new JPanel(new GridLayout(5, 2, 10, 40));
            panel.setBackground(new Color(40, 100, 140));
            idLabel = new JLabel("ID:");
            idField = new JTextField();
            idField.setPreferredSize(new Dimension(30, 30));
            labelFont = idLabel.getFont();
            idLabel.setFont(new Font(labelFont.getFontName(), Font.PLAIN, 22));
            idLabel.setForeground(Color.white);
            
            teamLabel = new JLabel("Name:");
            teamField = new JTextField();
            teamField.setPreferredSize(new Dimension(30, 30));
            labelFont1 = teamLabel.getFont();
            teamLabel.setFont(new Font(labelFont1.getFontName(), Font.PLAIN, 22));
            teamLabel.setForeground(Color.white);
            
            fieldLabel = new JLabel("Field:");
            fieldField = new JTextField();
            fieldField.setPreferredSize(new Dimension(30, 30));
            labelFont2 = fieldLabel.getFont();
            fieldLabel.setFont(new Font(labelFont2.getFontName(), Font.PLAIN, 22));
            fieldLabel.setForeground(Color.white);
            
            numLabel = new JLabel("PlayersNumber:");
            numField = new JTextField();
            numField.setPreferredSize(new Dimension(30, 30));
            labelFont3 = numLabel.getFont();
            numLabel.setFont(new Font(labelFont3.getFontName(), Font.PLAIN, 22));
            numLabel.setForeground(Color.white);
            
            buttonsContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 70, 22)); 
            buttonsContainer.setBackground(new Color(40, 100, 140));
            submitBtn = new RoundedButton("Submit");
            closeBtn=new RoundedButton("close");

            submitBtn.setPreferredSize(new Dimension(90, 50));
            closeBtn.setPreferredSize(new Dimension(90, 50));
            buttonsContainer.add(submitBtn);
            buttonsContainer.add(closeBtn);

            Border margin = new EmptyBorder(40, 100, 20, 100);
            panel.setBorder(margin);

            panel.add(idLabel);
            panel.add(idField);
            panel.add(teamLabel);
            panel.add(teamField);
            panel.add(fieldLabel);
            panel.add(fieldField);
            panel.add(numLabel);
            panel.add(numField);


            submitBtn.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    int id =Integer.parseInt( idField.getText());
                    String name = teamField.getText();
                    String field = fieldField.getText();
                    int num =Integer.parseInt(numField.getText());
                    try {
                        DatabaseDriver.insertteam(id , name , field , num);
                    } catch (Exception ex) {
                        Logger.getLogger(FootballLeagueGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    frame.dispose();
                }
            });
            closeBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    frame.dispose(); 
                }
            });
            
        }else if(buttonAction.equals("delete")){
            frame.setTitle("Delete team");
            panel=new JPanel(new FlowLayout(FlowLayout.CENTER,50,30));
            panel.setBackground(new Color(40, 100, 140));
            idLabel = new JLabel("ID:");
            idField = new JTextField();
            idField.setPreferredSize(new Dimension(70, 30));
            labelFont = idLabel.getFont();
            idLabel.setFont(new Font(labelFont.getFontName(), Font.PLAIN, 22));
            idLabel.setForeground(Color.white);
            
            buttonsContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 70, 20)); 
            buttonsContainer.setBackground(new Color(40, 100, 140));
            submitBtn = new RoundedButton("Submit");
            closeBtn=new RoundedButton("close");

            submitBtn.setPreferredSize(new Dimension(90, 50));
            closeBtn.setPreferredSize(new Dimension(90, 50));
            buttonsContainer.add(submitBtn);
            buttonsContainer.add(closeBtn);
            
            Border margin = new EmptyBorder(80, 80, 180, 100);
            panel.setBorder(margin);
            
            panel.add(idLabel);
            panel.add(idField);
            

            submitBtn.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    int id =Integer.parseInt( idField.getText());

                    try {
                        DatabaseDriver.deleteteam(id);
                    } catch (Exception ex) {
                        Logger.getLogger(FootballLeagueGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    frame.dispose();
                }
            });

            closeBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    frame.dispose(); // Κλείσιμο του πλαισίου (frame)
                }
            });  
        }else{
            frame.setTitle("Update team");
            panel=new JPanel(new GridLayout(5, 2, 10, 50));
            panel.setBackground(new Color(40, 100, 140));
            idLabel = new JLabel("ID:");
            
            DatabaseDriver dbDriver = new DatabaseDriver();
            Vector<String> ids  =dbDriver.fetchIds("TEAMS");

            idComboBox = new JComboBox<>(ids);
            
            labelFont = idLabel.getFont();
            idLabel.setFont(new Font(labelFont.getFontName(), Font.PLAIN, 22));
            idLabel.setForeground(Color.white);
            
            teamLabel = new JLabel("Name:");
            teamField = new JTextField();
            teamField.setPreferredSize(new Dimension(30, 30));
            labelFont1 = teamLabel.getFont();
            teamLabel.setFont(new Font(labelFont1.getFontName(), Font.PLAIN, 22));
            teamLabel.setForeground(Color.white);
            
            fieldLabel = new JLabel("Field:");
            fieldField = new JTextField();
            fieldField.setPreferredSize(new Dimension(30, 30));
            labelFont2 = fieldLabel.getFont();
            fieldLabel.setFont(new Font(labelFont2.getFontName(), Font.PLAIN, 22));
            fieldLabel.setForeground(Color.white);
            
            numLabel = new JLabel("Players Number:");
            numField = new JTextField();
            numField.setPreferredSize(new Dimension(30, 30));
            labelFont3 = numLabel.getFont();
            numLabel.setFont(new Font(labelFont3.getFontName(), Font.PLAIN, 22));
            numLabel.setForeground(Color.white);
            
            buttonsContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 70, 22)); 
            buttonsContainer.setBackground(new Color(40, 100, 140));
            submitBtn = new RoundedButton("Submit");
            closeBtn=new RoundedButton("close");

            submitBtn.setPreferredSize(new Dimension(90, 50));
            closeBtn.setPreferredSize(new Dimension(90, 50));
            buttonsContainer.add(submitBtn);
            buttonsContainer.add(closeBtn);
            
            Border margin = new EmptyBorder(40, 100, 20, 100);
            panel.setBorder(margin);
           
            
            panel.add(idLabel);
            panel.add(idComboBox);
            panel.add(teamLabel);
            panel.add(teamField);
            panel.add(fieldLabel);
            panel.add(fieldField);
            panel.add(numLabel);
            panel.add(numField);
            
            
            idComboBox.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    // When an ID is selected, populate JTextFields with data
                    populateTextFields((String) idComboBox.getSelectedItem(), "TEAMS");
                }
            });
    
            submitBtn.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    int id =Integer.parseInt((String) idComboBox.getSelectedItem());
                    String name = teamField.getText();
                    String field = fieldField.getText();
                    int num =Integer.parseInt( numField.getText());

                    try {
                         DatabaseDriver.updateteam(id , name , field , num);
                    } catch (Exception ex) {
                        Logger.getLogger(FootballLeagueGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    frame.dispose();
                }
            });

            closeBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    frame.dispose(); 
                }
            });
        }
        
        addToFrame(panel, buttonsContainer);
    }
    private void ButtonsScore(String buttonAction){
        frame = new JFrame();
        
        if(buttonAction.equals("add")){
            frame.setTitle("Add score");
            panel=new JPanel(new GridLayout(7, 2, 15, 15));
            panel.setBackground(new Color(40, 100, 140));    
            idLabel = new JLabel("ID:");
            idField = new JTextField();
            idField.setPreferredSize(new Dimension(30, 30));
            labelFont = idLabel.getFont();
            idLabel.setFont(new Font(labelFont.getFontName(), Font.PLAIN, 22));
            idLabel.setForeground(Color.white);
            
            teamLabel = new JLabel("Team:");
            teamField = new JTextField();
            teamField.setPreferredSize(new Dimension(30, 30));
            labelFont1 = teamLabel.getFont();
            teamLabel.setFont(new Font(labelFont1.getFontName(), Font.PLAIN, 22));
            teamLabel.setForeground(Color.white);
            
            winsLabel = new JLabel("Wins:");
            winsField = new JTextField();
            winsField.setPreferredSize(new Dimension(30, 30));
            labelFont2 = winsLabel.getFont();
            winsLabel.setFont(new Font(labelFont2.getFontName(), Font.PLAIN, 22));
            winsLabel.setForeground(Color.white);
            
            drawsLabel = new JLabel("Draws:");
            drawsField = new JTextField();
            drawsField.setPreferredSize(new Dimension(30, 30));
            labelFont3 = drawsLabel.getFont();
            drawsLabel.setFont(new Font(labelFont3.getFontName(), Font.PLAIN, 22));
            drawsLabel.setForeground(Color.white);
            
            lossesLabel = new JLabel("Losses:");
            lossesField = new JTextField();
            lossesField.setPreferredSize(new Dimension(30, 30));
            labelFont4 = lossesLabel.getFont();
            lossesLabel.setFont(new Font(labelFont4.getFontName(), Font.PLAIN, 22));
            lossesLabel.setForeground(Color.white);
            
            pointsLabel = new JLabel("Points:");
            pointsField = new JTextField();
            pointsField.setPreferredSize(new Dimension(30, 30));
            labelFont5 = pointsLabel.getFont();
            pointsLabel.setFont(new Font(labelFont5.getFontName(), Font.PLAIN, 22));
            pointsLabel.setForeground(Color.white);

            buttonsContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 70, 22)); 
            buttonsContainer.setBackground(new Color(40, 100, 140));
            submitBtn = new RoundedButton("Submit");
            closeBtn=new RoundedButton("close");

            submitBtn.setPreferredSize(new Dimension(90, 50));
            closeBtn.setPreferredSize(new Dimension(90, 50));
            buttonsContainer.add(submitBtn);
            buttonsContainer.add(closeBtn);
            
            Border margin = new EmptyBorder(40, 100, 20, 100);
            panel.setBorder(margin);

            panel.add(idLabel);
            panel.add(idField);
            panel.add(teamLabel);
            panel.add(teamField);
            panel.add(winsLabel);
            panel.add(winsField);
            panel.add(drawsLabel);
            panel.add(drawsField);
            panel.add(lossesLabel);
            panel.add(lossesField);
            panel.add(pointsLabel);
            panel.add(pointsField);

            submitBtn.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    int id =Integer.parseInt( idField.getText());
                    String team = teamField.getText();
                    int wins =Integer.parseInt( winsField.getText());
                    int draws=Integer.parseInt(drawsField.getText());
                    int losses = Integer.parseInt(lossesField.getText());
                    int points = Integer.parseInt(pointsField.getText());

                    try {
                        DatabaseDriver.insertscore(id , team , wins ,draws, losses,points);
                    } catch (Exception ex) {
                        Logger.getLogger(FootballLeagueGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    frame.dispose();
                }
            });
            closeBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    frame.dispose(); 
                }
            });
            
        }else if(buttonAction.equals("delete")){
            frame.setTitle("Delete score");
            panel=new JPanel(new FlowLayout(FlowLayout.CENTER,50,30));
             panel.setBackground(new Color(40, 100, 140));    
            idLabel = new JLabel("ID:");
            idField = new JTextField();
            idField.setPreferredSize(new Dimension(50, 30));
            labelFont = idLabel.getFont();
            idLabel.setFont(new Font(labelFont.getFontName(), Font.PLAIN, 22));
            idLabel.setForeground(Color.white); 
           
            buttonsContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 70, 20)); 
            buttonsContainer.setBackground(new Color(40, 100, 140));
            submitBtn = new RoundedButton("Submit");
            closeBtn=new RoundedButton("close");

            submitBtn.setPreferredSize(new Dimension(90, 50));
            closeBtn.setPreferredSize(new Dimension(90, 50));
            buttonsContainer.add(submitBtn);
            buttonsContainer.add(closeBtn);
           
            Border margin = new EmptyBorder(80, 80, 180, 100);
            panel.setBorder(margin);
            
            panel.add(idLabel);
            panel.add(idField);
          
            submitBtn.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    int id=Integer.parseInt(idField.getText());
                    try {
                        DatabaseDriver.deletescore(id);
                    } catch (Exception ex) {
                        Logger.getLogger(FootballLeagueGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }

                   frame.dispose();


                }
            });
        
                
                
            closeBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    frame.dispose(); 
                }
            }); 
        }else{
            frame.setTitle("Update score");
            panel=new JPanel(new GridLayout(7, 2, 15, 15));
            panel.setBackground(new Color(40, 100, 140));    
            idLabel = new JLabel("ID:");
            DatabaseDriver dbDriver = new DatabaseDriver();
            Vector<String> ids  =dbDriver.fetchIds("SCOREBOARD");

            idComboBox = new JComboBox<>(ids);
            
            labelFont = idLabel.getFont();
            idLabel.setFont(new Font(labelFont.getFontName(), Font.PLAIN, 22));
            idLabel.setForeground(Color.white); 
            
            teamLabel = new JLabel("Team:");
            teamField = new JTextField();
            teamField.setPreferredSize(new Dimension(30, 30));
            labelFont1 = teamLabel.getFont();
            teamLabel.setFont(new Font(labelFont1.getFontName(), Font.PLAIN, 22));
            teamLabel.setForeground(Color.white); 
            
            winsLabel = new JLabel("Wins:");
            winsField = new JTextField();
            winsField.setPreferredSize(new Dimension(30, 30));
            labelFont2 = winsLabel.getFont();
            winsLabel.setFont(new Font(labelFont2.getFontName(), Font.PLAIN, 22));
            winsLabel.setForeground(Color.white); 
             
            drawsLabel = new JLabel("Draws:");
            drawsField = new JTextField();
            drawsField.setPreferredSize(new Dimension(30, 30));
            labelFont3 = drawsLabel.getFont();
            drawsLabel.setFont(new Font(labelFont3.getFontName(), Font.PLAIN, 22));
            drawsLabel.setForeground(Color.white);
            
            lossesLabel = new JLabel("Losses:");
            lossesField = new JTextField();
            lossesField.setPreferredSize(new Dimension(30, 30));
            labelFont4 = lossesLabel.getFont();
            lossesLabel.setFont(new Font(labelFont4.getFontName(), Font.PLAIN, 22));
            lossesLabel.setForeground(Color.white);
            
            pointsLabel = new JLabel("Points:");
            pointsField = new JTextField();
            pointsField.setPreferredSize(new Dimension(30, 30));
            labelFont5 = pointsLabel.getFont();
            pointsLabel.setFont(new Font(labelFont5.getFontName(), Font.PLAIN, 22));
            pointsLabel.setForeground(Color.white);

            buttonsContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 70, 22)); 
            buttonsContainer.setBackground(new Color(40, 100, 140));
            submitBtn = new RoundedButton("Submit");
            closeBtn=new RoundedButton("close");

            submitBtn.setPreferredSize(new Dimension(90, 50));
            closeBtn.setPreferredSize(new Dimension(90, 50));
            buttonsContainer.add(submitBtn);
            buttonsContainer.add(closeBtn);

            Border margin = new EmptyBorder(40, 100, 20, 100);
            panel.setBorder(margin);
            
            panel.setBorder(margin);
            panel.add(idLabel);
            panel.add(idComboBox);
            panel.add(teamLabel);
            panel.add(teamField);
            panel.add(winsLabel);
            panel.add(winsField);
            panel.add(drawsLabel);
            panel.add(drawsField);
            panel.add(lossesLabel);
            panel.add(lossesField);
            panel.add(pointsLabel);
            panel.add(pointsField);
            
            idComboBox.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    // When an ID is selected, populate JTextFields with data
                    populateTextFields((String) idComboBox.getSelectedItem(), "SCOREBOARD");
                }
            });
          
            submitBtn.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    int id =Integer.parseInt((String) idComboBox.getSelectedItem());
                    String team = teamField.getText();
                    int wins =Integer.parseInt( winsField.getText());
                    int draws=Integer.parseInt(drawsField.getText());
                    int losses = Integer.parseInt(lossesField.getText());
                    int points = Integer.parseInt(pointsField.getText());
                       try {
                        DatabaseDriver.updatescore(id , team , wins , draws,losses,points);
                    } catch (Exception ex) {
                        Logger.getLogger(FootballLeagueGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }           
                    frame.dispose();
                }
            });
          
            closeBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    frame.dispose(); 
                }
            });
        }

        addToFrame(panel, buttonsContainer);
    }
    private void ButtonsMatch(String buttonAction){
        frame = new JFrame();
        
        if(buttonAction.equals("add")){
            frame.setTitle("Add match");
            panel=new JPanel(new GridLayout(7, 2, 15, 15));
            panel.setBackground(new Color(40, 100, 140));      
            idLabel = new JLabel("ID:");
            idField = new JTextField();
            idField.setPreferredSize(new Dimension(30, 30));
            labelFont = idLabel.getFont();
            idLabel.setFont(new Font(labelFont.getFontName(), Font.PLAIN, 22));
            idLabel.setForeground(Color.white);
            
            teamLabel = new JLabel("HomeTeam:");
            teamField = new JTextField();
            teamField.setPreferredSize(new Dimension(30, 30));
            labelFont1 = teamLabel.getFont();
            teamLabel.setFont(new Font(labelFont1.getFontName(), Font.PLAIN, 22));
            teamLabel.setForeground(Color.white);
            
            teamAwayLabel = new JLabel("AwayTeam:");
            teamAwayField = new JTextField();
            teamAwayField.setPreferredSize(new Dimension(30, 30));
            labelFont2 = teamAwayLabel.getFont();
            teamAwayLabel.setFont(new Font(labelFont2.getFontName(), Font.PLAIN, 22));
             teamAwayLabel.setForeground(Color.white);
            
            dateLabel = new JLabel("Date:");
            dateField = new JTextField();
            dateField.setPreferredSize(new Dimension(30, 30));
            labelFont3 = dateLabel.getFont();
            dateLabel.setFont(new Font(labelFont3.getFontName(), Font.PLAIN, 22));
            dateLabel.setForeground(Color.white);
            
            timeLabel = new JLabel("Time:");
            timeField = new JTextField();
            timeField.setPreferredSize(new Dimension(30, 30));
            labelFont4 = timeLabel.getFont();
            timeLabel.setFont(new Font(labelFont4.getFontName(), Font.PLAIN, 22));
            timeLabel.setForeground(Color.white);
            
            scoreLabel = new JLabel("Score:");
            scoreField = new JTextField();
            scoreField.setPreferredSize(new Dimension(30, 30));
            labelFont5 = scoreLabel.getFont();
            scoreLabel.setFont(new Font(labelFont5.getFontName(), Font.PLAIN, 22));
            scoreLabel.setForeground(Color.white);
            

            buttonsContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 70, 22)); 
             buttonsContainer.setBackground(new Color(40, 100, 140)); 
            submitBtn = new RoundedButton("Submit");
            closeBtn=new RoundedButton("close");

            submitBtn.setPreferredSize(new Dimension(90, 50));
            closeBtn.setPreferredSize(new Dimension(90, 50));
            buttonsContainer.add(submitBtn);
            buttonsContainer.add(closeBtn);  

            Border margin = new EmptyBorder(40, 100, 20, 100);
            panel.setBorder(margin);
            
            panel.setBorder(margin);
            panel.add(idLabel);
            panel.add(idField);
            panel.add(teamLabel);
            panel.add(teamField);
            panel.add(teamAwayLabel);
            panel.add(teamAwayField);
            panel.add(dateLabel);
            panel.add(dateField);
            panel.add(timeLabel);
            panel.add(timeField);
            panel.add(scoreLabel);
            panel.add(scoreField);


            submitBtn.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    int id = Integer.parseInt(idField.getText());
                    String home = teamField.getText();
                    String away = teamAwayField.getText();
                    String date = dateField.getText();
                    String time = timeField.getText();
                    String score = scoreField.getText();
            
                    try {
                        DatabaseDriver.insertmatch(id , home , away ,date, time,score);
                    } catch (Exception ex) {
                        Logger.getLogger(FootballLeagueGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }

                   frame.dispose();
                   }
            });
            
            closeBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    frame.dispose(); 
                }
            });
            
        }else if(buttonAction.equals("delete")){
            frame.setTitle("Delete match");
            panel=new JPanel(new FlowLayout(FlowLayout.CENTER,50,30));
             panel.setBackground(new Color(40, 100, 140));      
            idLabel = new JLabel("ID:");
            idField = new JTextField();
            idField.setPreferredSize(new Dimension(50, 30));
            labelFont = idLabel.getFont();
            idLabel.setFont(new Font(labelFont.getFontName(), Font.PLAIN, 22));
            idLabel.setForeground(Color.white);
            
            buttonsContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 70, 20)); 
             buttonsContainer.setBackground(new Color(40, 100, 140)); 
            submitBtn = new RoundedButton("Submit");
            closeBtn=new RoundedButton("close");

            submitBtn.setPreferredSize(new Dimension(90, 50));
            closeBtn.setPreferredSize(new Dimension(90, 50));
            buttonsContainer.add(submitBtn);
            buttonsContainer.add(closeBtn); 
            
            Border margin = new EmptyBorder(80, 80, 180, 100);
            panel.setBorder(margin);
            
            panel.add(idLabel);
            panel.add(idField);

            submitBtn.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    int id = Integer.parseInt(idField.getText());
                    try {
                        DatabaseDriver.deletematch(id);
                    } catch (Exception ex) {
                        Logger.getLogger(FootballLeagueGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                   frame.dispose(); 
                }
            });

            closeBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    frame.dispose(); 
                }
            });
    
        }else{    
            frame.setTitle("Update match");
            panel=new JPanel(new GridLayout(7, 2, 15, 15));
             panel.setBackground(new Color(40, 100, 140));   
            idLabel = new JLabel("ID:");
            
            DatabaseDriver dbDriver = new DatabaseDriver();
            Vector<String> ids  =dbDriver.fetchIds("FOOTBALLMATCHES");

            idComboBox = new JComboBox<>(ids);
            
            labelFont = idLabel.getFont();
            idLabel.setFont(new Font(labelFont.getFontName(), Font.PLAIN, 22));
            idLabel.setForeground(Color.white);
            
            teamLabel = new JLabel("HomeTeam:");
            teamField = new JTextField();
            teamField.setPreferredSize(new Dimension(30, 30));
            labelFont1 = teamLabel.getFont();
            teamLabel.setFont(new Font(labelFont1.getFontName(), Font.PLAIN, 22));
            teamLabel.setForeground(Color.white);
            
            teamAwayLabel = new JLabel("AwayTeam:");
            teamAwayField = new JTextField();
            teamAwayField.setPreferredSize(new Dimension(30, 30));
            labelFont2 = teamAwayLabel.getFont();
            teamAwayLabel.setFont(new Font(labelFont2.getFontName(), Font.PLAIN, 22));
            teamAwayLabel.setForeground(Color.white);
            
            dateLabel = new JLabel("Date:");
            dateField = new JTextField();
            dateField.setPreferredSize(new Dimension(30, 30));
            labelFont3 = dateLabel.getFont();
            dateLabel.setFont(new Font(labelFont3.getFontName(), Font.PLAIN, 22));
            dateLabel.setForeground(Color.white);
            
            timeLabel = new JLabel("Time:");
            timeField = new JTextField();
            timeField.setPreferredSize(new Dimension(30, 30));
            labelFont4 = timeLabel.getFont();
            timeLabel.setFont(new Font(labelFont4.getFontName(), Font.PLAIN, 22));
             timeLabel.setForeground(Color.white);
            
            scoreLabel = new JLabel("Score:");
            scoreField = new JTextField();
            scoreField.setPreferredSize(new Dimension(30, 30));
            labelFont5 = scoreLabel.getFont();
            scoreLabel.setFont(new Font(labelFont5.getFontName(), Font.PLAIN, 22));
            scoreLabel.setForeground(Color.white);

            buttonsContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 70, 22)); 
             buttonsContainer.setBackground(new Color(40, 100, 140));   
            submitBtn = new RoundedButton("Submit");
            closeBtn=new RoundedButton("close");

            submitBtn.setPreferredSize(new Dimension(90, 50));
            closeBtn.setPreferredSize(new Dimension(90, 50));
            buttonsContainer.add(submitBtn);
            buttonsContainer.add(closeBtn);
            
            Border margin = new EmptyBorder(40, 100, 20, 100);
            panel.setBorder(margin);
            
            panel.setBorder(margin);
            panel.add(idLabel);
            panel.add(idComboBox);
            panel.add(teamLabel);
            panel.add(teamField);
            panel.add(teamAwayLabel);
            panel.add(teamAwayField);
            panel.add(dateLabel);
            panel.add(dateField);
            panel.add(timeLabel);
            panel.add(timeField);
            panel.add(scoreLabel);
            panel.add(scoreField);
            panel.add(new JLabel());
            
            idComboBox.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    // When an ID is selected, populate JTextFields with data
                    populateTextFields((String) idComboBox.getSelectedItem(), "FOOTBALLMATCHES");
                }
            });

            submitBtn.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    int id =Integer.parseInt((String) idComboBox.getSelectedItem());
                    String home = teamField.getText();
                    String away = teamAwayField.getText();
                    String date=dateField.getText();
                    String time = timeField.getText();
                    String score = scoreField.getText();

                    try {
                        DatabaseDriver.updatematch(id , home , away ,date, time,score);
                    } catch (Exception ex) {
                        Logger.getLogger(FootballLeagueGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    frame.dispose();
                }
            });

            closeBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    frame.dispose(); 
                }
            });
        }  
        
        addToFrame(panel, buttonsContainer);
    }
    private void ButtonsTrophies(String buttonAction){
        frame = new JFrame();
        
        
        if(buttonAction.equals("add")){
            frame.setTitle("Add trophy");
            panel=new JPanel(new GridLayout(6, 2, 15, 15));
             panel.setBackground(new Color(40, 100, 140));    
            idLabel = new JLabel("ID:");
            idField = new JTextField();
            idField.setPreferredSize(new Dimension(30, 30));
            labelFont = idLabel.getFont();
            idLabel.setFont(new Font(labelFont.getFontName(), Font.PLAIN, 22));
            idLabel.setForeground(Color.white);
            
            teamLabel = new JLabel("Team:");
            teamField = new JTextField();
            teamField.setPreferredSize(new Dimension(30, 30));
            labelFont1 = teamLabel.getFont();
            teamLabel.setFont(new Font(labelFont1.getFontName(), Font.PLAIN, 22));
            teamLabel.setForeground(Color.white);
            
            premierLabel = new JLabel("Premier:");
            premierField = new JTextField();
            premierField.setPreferredSize(new Dimension(30, 30));
            labelFont2 = premierLabel.getFont();
            premierLabel.setFont(new Font(labelFont2.getFontName(), Font.PLAIN, 22));
            premierLabel.setForeground(Color.white);
            
            carabaoLabel = new JLabel("Carabao:");
            carabaoField = new JTextField();
            carabaoField.setPreferredSize(new Dimension(30, 30));
            labelFont3 = carabaoLabel.getFont();
            carabaoLabel.setFont(new Font(labelFont3.getFontName(), Font.PLAIN, 22));
            carabaoLabel.setForeground(Color.white);
            
            leagueLabel = new JLabel("FA Cup:");
            leagueField = new JTextField();
            leagueField.setPreferredSize(new Dimension(30, 30));
            labelFont4 = leagueLabel.getFont();
            leagueLabel.setFont(new Font(labelFont4.getFontName(), Font.PLAIN, 22));
            leagueLabel.setForeground(Color.white);
            
            buttonsContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 70, 20)); 
             buttonsContainer.setBackground(new Color(40, 100, 140));
            submitBtn = new RoundedButton("Submit");
            closeBtn=new RoundedButton("close");

            submitBtn.setPreferredSize(new Dimension(90, 50));
            closeBtn.setPreferredSize(new Dimension(90, 50));
            buttonsContainer.add(submitBtn);
            buttonsContainer.add(closeBtn);

            Border margin = new EmptyBorder(40, 100, 20, 100);
            panel.setBorder(margin);
            
            panel.setBorder(margin);
            panel.add(idLabel);
            panel.add(idField);
            panel.add(teamLabel);
            panel.add(teamField);
            panel.add(premierLabel);
            panel.add(premierField);
            panel.add(carabaoLabel);
            panel.add(carabaoField);
            panel.add(leagueLabel);
            panel.add(leagueField);

            submitBtn.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    int id =Integer.parseInt( idField.getText());
                    String team = teamField.getText();
                    int premier =Integer.parseInt( premierField.getText());
                    int carabao=Integer.parseInt(carabaoField.getText());
                    int league = Integer.parseInt(leagueField.getText());

                    try {
                        DatabaseDriver.inserttrophies(id , team , premier ,carabao, league);
                    } catch (Exception ex) {
                        Logger.getLogger(FootballLeagueGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    frame.dispose();
                }
            });
            
            closeBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    frame.dispose();
                }
            });
        }else if(buttonAction.equals("delete")){
            frame.setTitle("Delete trophy");
            panel=new JPanel(new FlowLayout(FlowLayout.CENTER,50,30));
             panel.setBackground(new Color(40, 100, 140));    
            idLabel = new JLabel("ID:");
            idField = new JTextField();
            idField.setPreferredSize(new Dimension(50, 30));
            labelFont = idLabel.getFont();
            idLabel.setFont(new Font(labelFont.getFontName(), Font.PLAIN, 22));
            idLabel.setForeground(Color.white);
            
            buttonsContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 70, 20)); 
             buttonsContainer.setBackground(new Color(40, 100, 140));
            submitBtn = new RoundedButton("Submit");
            closeBtn=new RoundedButton("close");

            submitBtn.setPreferredSize(new Dimension(90, 50));
            closeBtn.setPreferredSize(new Dimension(90, 50));
            buttonsContainer.add(submitBtn);
            buttonsContainer.add(closeBtn);
            
            Border margin = new EmptyBorder(80, 80, 180, 100);
            panel.setBorder(margin);
            
            panel.add(idLabel);
            panel.add(idField);                 

            submitBtn.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    int id=Integer.parseInt(idField.getText());
                    try {
                        DatabaseDriver.deletetrophies(id);
                    } catch (Exception ex) {
                        Logger.getLogger(FootballLeagueGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                   frame.dispose();
                }
            });                   

            closeBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    frame.dispose(); 
                }
            });
        }else{
            
            frame.setTitle("Update trophy");
            
            panel=new JPanel(new GridLayout(7, 2, 15, 15));
             panel.setBackground(new Color(40, 100, 140));
            idLabel = new JLabel("ID:");
            
            DatabaseDriver dbDriver = new DatabaseDriver();
            Vector<String> ids  =dbDriver.fetchIds("TROPHIES");

            idComboBox = new JComboBox<>(ids);
            labelFont = idLabel.getFont();
            idLabel.setFont(new Font(labelFont.getFontName(), Font.PLAIN, 22));
            idLabel.setForeground(Color.white);
            
            teamLabel = new JLabel("Team:");
            teamField = new JTextField();
            teamField.setPreferredSize(new Dimension(30, 30));
            labelFont1 = teamLabel.getFont();
            teamLabel.setFont(new Font(labelFont1.getFontName(), Font.PLAIN, 22));
            teamLabel.setForeground(Color.white);
                
            premierLabel = new JLabel("Premier:");
            premierField = new JTextField();
            premierField.setPreferredSize(new Dimension(30, 30));
            labelFont2 = premierLabel.getFont();
            premierLabel.setFont(new Font(labelFont2.getFontName(), Font.PLAIN, 22));
            premierLabel.setForeground(Color.white);
            
            carabaoLabel = new JLabel("Carabao:");
            carabaoField = new JTextField();
            carabaoField.setPreferredSize(new Dimension(30, 30));
            labelFont3 = carabaoLabel.getFont();
            carabaoLabel.setFont(new Font(labelFont3.getFontName(), Font.PLAIN, 22));
            carabaoLabel.setForeground(Color.white);
            
            leagueLabel = new JLabel("FA Cup:");
            leagueField = new JTextField();
            leagueField.setPreferredSize(new Dimension(30, 30));
            labelFont4 = leagueLabel.getFont();
            leagueLabel.setFont(new Font(labelFont4.getFontName(), Font.PLAIN, 22));
            leagueLabel.setForeground(Color.white);
            
            buttonsContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 70, 20)); 
             buttonsContainer.setBackground(new Color(40, 100, 140));
            submitBtn = new RoundedButton("Submit");
            closeBtn=new RoundedButton("close");

            submitBtn.setPreferredSize(new Dimension(90, 50));
            closeBtn.setPreferredSize(new Dimension(90, 50));
            buttonsContainer.add(submitBtn);
            buttonsContainer.add(closeBtn);

            Border margin = new EmptyBorder(40, 100, 20, 100);
            panel.setBorder(margin);
            
            panel.setBorder(margin);
            panel.add(idLabel);
            panel.add(idComboBox);
            panel.add(teamLabel);
            panel.add(teamField);
            panel.add(premierLabel);
            panel.add(premierField);
            panel.add(carabaoLabel);
            panel.add(carabaoField);
            panel.add(leagueLabel);
            panel.add(leagueField);
            
            idComboBox.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    // When an ID is selected, populate JTextFields with data
                    populateTextFields((String) idComboBox.getSelectedItem(), "TROPHIES");
                }
            });

            submitBtn.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    int id =Integer.parseInt((String) idComboBox.getSelectedItem());
                    String team = teamField.getText();
                    int premier =Integer.parseInt( premierField.getText());
                    int carabao=Integer.parseInt(carabaoField.getText());
                    int league = Integer.parseInt(leagueField.getText());
                       try {
                        DatabaseDriver.updatetrophies(id , team, premier, carabao,league);
                    } catch (Exception ex) {
                        Logger.getLogger(FootballLeagueGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                   frame.dispose();
                }
            });

            closeBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    frame.dispose(); 
                }
            });
        }
   
        addToFrame(panel, buttonsContainer);
    }
    
    private void populateTextFields(String selectedID, String tableName) {
        try {
            dbConnection = DriverManager.getConnection(url, username,passwd);
            switch(tableName){
                case("TEAMS"):
                    selectString = "SELECT T_ID, T_NAME, T_FIELD, T_NUM FROM " + tableName + " WHERE T_ID = " + selectedID;
                    statement = dbConnection.createStatement();
                    rs = statement.executeQuery(selectString);
                    
                    if (rs.next()) {
                        teamField.setText(rs.getString(2)); // Assuming T_NAME is in the second column
                        fieldField.setText(rs.getString(3)); // Assuming T_FIELD is in the third column
                        numField.setText(String.valueOf(rs.getInt(4)));   // Assuming there is a fourth column for PlayersNumber
                    }
                    break;
                case("PLAYERS"):
                    selectString = "SELECT PLAYERID, NAME, AGE, CLUB, GOALSSCORED, POSITION FROM " + tableName + " WHERE PLAYERID = " + selectedID;
                    statement = dbConnection.createStatement();
                    rs = statement.executeQuery(selectString);
                    
                    if (rs.next()) {
                        NameField.setText(rs.getString(2)); 
                        AgeField.setText(String.valueOf(rs.getInt(3)));
                        clubField.setText(rs.getString(4));  
                        GoalsField.setText(String.valueOf(rs.getInt(5)));
                        posField.setText(rs.getString(6));
                    }
                    break;
                case("SCOREBOARD"):
                    selectString = "SELECT GAME_ID, S_TEAM, WINS, DRAWS, LOSSES, POINTS FROM " + tableName + " WHERE GAME_ID = " + selectedID;                  
                    statement = dbConnection.createStatement();
                    rs = statement.executeQuery(selectString);
                    
                     if (rs.next()) {
                        teamField.setText(rs.getString(2)); 
                        winsField.setText(String.valueOf(rs.getInt(3)));
                        drawsField.setText(String.valueOf(rs.getInt(4)));  
                        lossesField.setText(String.valueOf(rs.getInt(5)));
                        pointsField.setText(String.valueOf(rs.getInt(6)));
                    }
                    break;    
                case("FOOTBALLMATCHES"):
                    selectString = "SELECT MATCHID, HOMETEAM, AWAYTEAM, MATCHDATE, MATCHTIME, MATCHSCORE FROM " + tableName + " WHERE MATCHID = " + selectedID;
                    statement = dbConnection.createStatement();
                    rs = statement.executeQuery(selectString);
                    if (rs.next()) {
                        teamField.setText(rs.getString(2)); 
                        teamAwayField.setText(rs.getString(3));
                        dateField.setText(rs.getString(4));  
                        timeField.setText(rs.getString(5));
                        scoreField.setText(rs.getString(6));
                    }
                    break;
                case("TROPHIES"):
                    selectString = "SELECT TRID, TEAM, PREMIER, CARABAO, LEAGUECUP FROM " + tableName + " WHERE TRID = " + selectedID;
                    statement = dbConnection.createStatement();
                    rs = statement.executeQuery(selectString);
                    if (rs.next()) {
                        teamField.setText(rs.getString(2)); 
                        premierField.setText(String.valueOf(rs.getInt(3)));
                        carabaoField.setText(String.valueOf(rs.getInt(4)));  
                        leagueField.setText(String.valueOf(rs.getInt(5)));
                    }
                    break; 
            }
           

            
         
           

            rs.close();
            statement.close();
            dbConnection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void addToFrame(JPanel panel, JPanel buttonsContainer){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int screenWidth = (int) toolkit.getScreenSize().getWidth();
        int screenHeight = (int) toolkit.getScreenSize().getHeight();
        
        double widthFraction = 0.3;
        double heightFraction = 0.5;
        
        int windowWidth = (int)(screenWidth * widthFraction);
        int windowHeight = (int)(screenHeight * heightFraction);
         
        frame.setSize(windowWidth, windowHeight);
        frame.getContentPane().setBackground(Color.white);
        frame.add(panel, BorderLayout.CENTER);
        frame.add(buttonsContainer, BorderLayout.SOUTH); 
       
        
        
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
}
