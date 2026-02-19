package Application;

import Db.DB;

import java.sql.Connection;

public class Main {
    public static void main (String[] args){

        Connection conection = DB.getConnection();

        if(conection != null){
            System.out.print("Banco de dados conectado com sucesso");
        }
    }
}
