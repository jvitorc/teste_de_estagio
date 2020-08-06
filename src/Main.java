import dao.ClienteDAO;
import dao.jdbc.ClienteDAOImpl;
import entidades.Cliente;

import dao.jdbc.FilmeDAOImpl;
import dao.FilmeDAO;
import entidades.Filme;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;


public class Main {

    public static void main(String[] args) {
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost/teste", "postgres", "1234");
            conn.setAutoCommit(false);

            //Demonstrar o funcionamento aqui
            ClienteDAO clienteDAO = new ClienteDAOImpl();
            
            FilmeDAO filmeDAO = new FilmeDAOImpl();
//            Filme f1 = new Filme();
//            f1.setIdFilme(9);
//            f1.setDataLancamento(new Date());
//            f1.setDescricao("TESTE2");
//            f1.setNome("FILME TESTE2");
//            filmeDAO.edit(conn, f1);
//            filmeDAO.delete(conn, f1.getIdFilme());
           
            Filme f1 = filmeDAO.find(conn, 1);

	       	System.out.println(f1.getIdFilme());
	        System.out.println(f1.getNome());
	        System.out.println(f1.getDescricao());
	        System.out.println(f1.getDataLancamento());
            
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Fim do teste.");
    }
}