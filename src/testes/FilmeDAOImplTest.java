package testes;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;

import org.junit.jupiter.api.Test;

import dao.FilmeDAO;
import dao.jdbc.FilmeDAOImpl;
import entidades.Filme;
import java.sql.Connection;

class FilmeDAOImplTest {

	@Test
	void Inserir() {
		Connection conn = null;
		try {
			Class.forName("org.postgresql.Driver");
	        conn = DriverManager.getConnection("jdbc:postgresql://localhost/teste", "postgres", "1234");
	        conn.setAutoCommit(false);
	    	
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
	    	
	    	assert(filme.getNome().equals(testeFilme.getNome()));			

        } catch (Exception e) {
            e.printStackTrace();
            fail("Error");
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
	}

}
