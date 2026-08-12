# Premier League Database Manager ⚽

A Desktop Database Management application for a football league, built using **Java Swing** and **SQLite**. 

Originally designed to connect to a remote Oracle SQL server, this version has been fully migrated to a standalone, local SQLite architecture for seamless offline execution.

## 🚀 Features
* **Table Views**: Easily browse through core data tables including **Teams**, **Players**, **Scoreboard**, **Game Days**, and **Trophies**.
* **Modern GUI**: Customized **Java Swing** interface utilizing components like `RoundedButton` and adaptive screen-resolution scaling.
* **Autonomous Execution**: Runs fully offline with an integrated local database (`football.db`), requiring no active VPN or server connections.

## 🛠️ Tech Stack
* **Language**: Java 21 (or newer)
* **GUI Framework**: Java Swing / AWT
* **Database**: SQLite (via SQLite JDBC Driver)

## 💻 How to Run
1. Clone this repository:
   ```bash
   git clone https://github.com
   ```
2. Ensure you have the `sqlite-jdbc.jar` in your project root or referenced libraries.
3. Compile the project files:
   ```bash
   javac -cp ".;sqlite-jdbc.jar" src/*.java -d bin
   ```
4. Execute the application:
   ```bash
   java -cp "bin;sqlite-jdbc.jar" FootballLeagueGUI
   ```
