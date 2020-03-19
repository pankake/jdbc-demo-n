package com.kaleyra.db.daniele;

import java.sql.*;

public class MainClass {

    public static void main(String[] args) throws SQLException {

        final String JDBC_URL = "jdbc:hsqldb:file:db/hsqldb-db;shutdown=true;hsqldb.write_delay=false";
        //    private static final String JDBC_URL = "jdbc:hsqldb:mem:inmemory;hsqldb.sqllog=3";
        final String USERNAME = "SA";
        final String PASSWORD = "";
        Connection conn;
        PreparedStatement insertUser;

        //connessione al db
        conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        conn.setAutoCommit(false);
        Statement stmt = conn.createStatement();

        //restituisce un ospedale libero nella provincia se ha posti disponibili
        String prov = "SR";
        ResultSet rs = stmt.executeQuery("SELECT * FROM Ospedale WHERE sigla_provincia ='" + prov +
                                            "' AND posti_letto_disponibili > 0 LIMIT 1");
        int codiceStruttura = 0;
        int postiDisponibili = 0;

        while (rs.next()) {
            codiceStruttura = rs.getInt("codice_struttura");
            postiDisponibili = rs.getInt("posti_letto_disponibili");
        }

        //System.out.println(codiceStruttura);
        //System.out.println(postiDisponibili);

        //inserisce un paziente nel db (con il codice della struttura che può prenderlo in carico)
        stmt.executeQuery("INSERT INTO Paziente(cf, nome, cognome, eta, comune_residenza, idOspedale) VALUES('DRNMTND2A29O801Y', 'Franco', 'Marconi', '69', 'ROMA', '" + codiceStruttura + "')");

        //update ospedale decrementando il numero di posti letto
        postiDisponibili -= 1;
        stmt.executeQuery("UPDATE Ospedale SET posti_letto_disponibili = '" +postiDisponibili+
                            "' WHERE codice_struttura = '"+codiceStruttura+"'");

        //update ospedale incrementando il numero di posti letto
        postiDisponibili += 1;
        stmt.executeQuery("UPDATE Ospedale SET posti_letto_disponibili = '" +postiDisponibili+
                            "' WHERE codice_struttura = '"+codiceStruttura+"'");

        //update del paziente con l'id della struttura presso la quale viene ricoverato
        String codiceFiscale = "FRNMTND2A29O801Y";
        stmt.executeQuery("UPDATE Paziente SET idOspedale = '" +codiceStruttura+
                            "' WHERE cf = '"+codiceFiscale+"'");

            //stampa la tabella Ospedale
            rs = stmt.executeQuery("SELECT * FROM Ospedale");
            while (rs.next()) {
                int x = rs.getInt("codice_regione");
                String d = rs.getString("descrizione");
                int cs = rs.getInt("codice_struttura");
                String i = rs.getString("indirizzo");
                int c = rs.getInt("codice_comune");
                String cmn = rs.getString("comune");
                String s = rs.getString("sigla_provincia");
                int p = rs.getInt("posti_letto_disponibili");
                System.out.print(x);
                System.out.print(" ");
                System.out.print(cs);
                System.out.print(" ");
                System.out.print(i);
                System.out.print(" ");
                System.out.print(c);
                System.out.print(" ");
                System.out.print(cmn);
                System.out.print(" ");
                System.out.print(s);
                System.out.print(" ");
                System.out.print(p);
                System.out.println(); }

           //stampa la tabella Paziente
           ResultSet rp = stmt.executeQuery("SELECT * FROM Paziente");
            while (rp.next()) {
                String cf = rp.getString("cf");
                String n = rp.getString("nome");
                String cn = rp.getString("cognome");
                int et = rp.getInt("eta");
                String cr = rp.getString("comune_residenza");
                int io = rp.getInt("idOspedale");
                System.out.print(cf);
                System.out.print(" ");
                System.out.print(n);
                System.out.print(" ");
                System.out.print(cn);
                System.out.print(" ");
                System.out.print(et);
                System.out.print(" ");
                System.out.print(cr);
                System.out.print(" ");
                System.out.print(io);
                System.out.println();
        }

        //recupero uno dei codici struttura
        //ResultSet rc = stmt.executeQuery("SELECT codice_struttura FROM Ospedale WHERE sigla_provincia ='" + prov  + "');


 /*     //estrae il comune di residenza di un paziente e lo stampa
        ResultSet comune = stmt.executeQuery("SELECT comune_residenza FROM Paziente WHERE cf = 'FRNMCN51A20H501O'");
        while (comune.next()) {
            String d = comune.getString("comune_residenza");
            System.out.print(d);
            System.out.println();
        }   */

        //PUNTO 3, se non ci sono posti nella provincia cerca nella propria regione,
        //         se non trova nulla cerca nelle regioni vicine
        //         nota: la tabella è ordinata per codice regione ascendente,
        //
        if(postiDisponibili == 0) {

            /*restituisce la 'sigla_provincia' dell'ospedale, accetta in input il codice_regione dell'ospedale
            int codiceRegione = 10;

            String siglaProvincia = "";
            ResultSet rr = stmt.executeQuery("SELECT sigla_provincia FROM Ospedale WHERE codice_regione ='" +
                                                codiceRegione + "' LIMIT 1");
            while (rr.next()) {
                String sp = rr.getString("sigla_provincia");
                siglaProvincia = sp;
                System.out.println();
            }
            //System.out.print(siglaProvincia); */
        }

/*
        //distrugge la tabella Ospedale
        stmt.executeQuery("DROP TABLE Ospedale");

        //distrugge la tabella Studente
        stmt.executeQuery("DROP TABLE Studente");

        //crea la tabella Ospedale
        stmt.executeQuery("CREATE TABLE Ospedale " +
                "(codice_regione INTEGER, " +
                "descrizione_regione VARCHAR(255), " +
                "codice_struttura INTEGER PRIMARY KEY, " +
                "denominazione_struttura VARCHAR(255), " +
                "indirizo VARCHAR(255), " +
                "codice_comune INTEGER, " +
                "comune VARCHAR(255), " +
                "sigla_provincia VARCHAR(2), " +
                "posti_letto_disponibili INTEGER)");

        //crea a tabella Studente
        stmt.executeQuery("CREATE TABLE Studente " +
                "(cf VARCHAR(16) PRIMARY KEY, " +
                "nome VARCHAR(100), " +
                "cognome VARCHAR(100), " +
                "eta INTEGER, " +
                "comune_residenza VARCHAR(100), " +
                "idOspedale INTEGER)");

*/
        //insertUser = conn.prepareStatement("INSERT INTO user (name, email) VALUES (?, ?)");

        conn.commit();

    }

}
