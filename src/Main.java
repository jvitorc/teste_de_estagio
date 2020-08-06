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
            Filme f1 = new Filme();
            f1.setDataLancamento(new Date());
            f1.setDescricao("TESTE");
            f1.setNome("FILME TESTE");
            filmeDAO.insert(conn, f1);
            System.out.println(f1.getNome() + " " + f1.getIdFilme());
            
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