package testes;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dao.FilmeDAO;
import dao.jdbc.FilmeDAOImpl;
import entidades.Filme;
import java.sql.Connection;

class FilmeDAOImplTest {
	
	FilmeDAO filmeDAO;
	Connection conn;
	Filme filme;
	
	@BeforeEach
	void setup() {
		try {
			Class.forName("org.postgresql.Driver");
	        this.conn = DriverManager.getConnection("jdbc:postgresql://localhost/teste", "postgres", "1234");
	        this.conn.setAutoCommit(false);	
			
	        this.filmeDAO = new FilmeDAOImpl();
	    	
	    	// Criacao do filme TESTE
	    	this.filme = new Filme();
	    	this.filme.setDataLancamento(new Date());
	    	this.filme.setDescricao("TESTE");
	    	this.filme.setNome("FILME TESTE");		

	    	
		} catch (Exception e) {
			e.printStackTrace();
            fail("Error");
        }
	}

	@AfterEach
	void tearDown() {
		 try {
             conn.close();
         } catch (SQLException e) {
             e.printStackTrace();
         }
	}

	@Test
	void insert() throws Exception {
	    	// Inserir filme
		filmeDAO.insert(conn, filme);    	
	    	// Procurar filme
    	Filme testeFilme = filmeDAO.find(conn, filme.getIdFilme());
	    	
    	assertEquals(filme.getNome(), testeFilme.getNome());			
	}

	@Test
	void edit() throws Exception {
    	// Inserir filme
    	this.filmeDAO.insert(conn, filme);    	

    	// Editar filme
    	filme.setNome("TESTE2");
    	filmeDAO.edit(conn, filme);
    	Filme testeFilme = filmeDAO.find(conn, filme.getIdFilme());
    	
    	assertEquals(filme.getNome(),testeFilme.getNome());			
	}

	@Test
	void list() throws Exception {
    	// Lista antiga
		Collection<Filme> filmesAntigos = filmeDAO.list(conn);

		// Inserir filme
    	filmeDAO.insert(conn, filme);

    	// Lista nova
		Collection<Filme> filmesNovos = filmeDAO.list(conn);
    	
    	assertEquals(filmesAntigos.size()+1, filmesNovos.size());	
	}
	
	@Test
	void delete() throws Exception {
		// Inserir filme
    	filmeDAO.insert(conn, filme);

		// Excluir
    	filmeDAO.delete(conn, filme.getIdFilme());
    	Filme testeFilme = filmeDAO.find(conn, filme.getIdFilme());
    	assertNull(testeFilme);		
	}
		
}
