import dao.ClienteDAO;
import dao.jdbc.ClienteDAOImpl;
import entidades.Cliente;

import dao.jdbc.FilmeDAOImpl;
import dao.FilmeDAO;
import entidades.Filme;

import dao.jdbc.AluguelDAOImpl;
import dao.AluguelDAO;
import entidades.Aluguel;

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
            
            AluguelDAO aluguelDAO = new AluguelDAOImpl();

//            for (Aluguel aluguel: aluguelDAO.list(conn)) {
//            	System.out.println(aluguel.toString());
//            }
//            AluguelDAOImpl aluguelDAO = new AluguelDAOImpl();
//            for (Filme filme: aluguelDAO.getFilmes(conn, 3)) {
//            	System.out.println(filme);
//            }
            
//            System.out.println(aluguelDAO.find(conn, 3));
//            aluguelDAO.delete(conn, aluguelDAO.find(conn, 3));
//            System.out.println(aluguelDAO.find(conn, 3));

//            Aluguel aluguel = new Aluguel(0, null, clienteDAO.find(conn, 1), new Date(), 100);
//            aluguelDAO.insert(conn, aluguel);
//            System.out.println(aluguel);
//
//            
//            AluguelDAOImpl aluguelDAOImpl = new AluguelDAOImpl();
//            aluguelDAOImpl.insertFilme(conn, 11, filmeDAO.find(conn, 1));            

//            
//            Aluguel aluguel = aluguelDAO.find(conn, 1);
//            System.out.println(aluguel);
//            aluguel.setValor(30);
//            aluguelDAO.edit(conn, aluguel);
//            aluguel = aluguelDAO.find(conn, 1);            
//            System.out.println(aluguel);
            
            testeFilme(conn);
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

    public static void testeFilme(Connection conn) throws Exception {
    	FilmeDAO filmeDAO = new FilmeDAOImpl();
    	
    	// Criacao do filme TESTE
    	Filme filme = new Filme();
    	filme.setDataLancamento(new Date());
    	filme.setDescricao("TESTE");
    	filme.setNome("FILME TESTE");
    	

    	// Inserir filme
    	filmeDAO.insert(conn, filme);    	
    	// Procurar filme
    	Filme testeFilme = filmeDAO.find(conn, filme.getIdFilme());
    	System.out.println(filme.getNome().equals(testeFilme.getNome()));

    	// Editar filme
    	filme.setNome("TESTE2");
    	filmeDAO.edit(conn, filme);
    	testeFilme = filmeDAO.find(conn, filme.getIdFilme());
    	System.out.println(filme.getNome().equals(testeFilme.getNome()));
    	
    	// Listar filme
    	boolean filmeEncontrado = false;
    	for (Filme f: filmeDAO.list(conn)) {
    		if (f.getIdFilme() == filme.getIdFilme()) {
    			filmeEncontrado = true;
    		}
    	}
    	System.out.println(filmeEncontrado);

    	// Excluir
    	filmeDAO.delete(conn, filme.getIdFilme());
    	testeFilme = filmeDAO.find(conn, filme.getIdFilme());
    	System.out.println(testeFilme == null);
    	
   }
    
}