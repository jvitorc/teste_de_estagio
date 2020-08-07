package testes;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dao.AluguelDAO;
import dao.ClienteDAO;
import dao.FilmeDAO;
import dao.jdbc.AluguelDAOImpl;
import dao.jdbc.ClienteDAOImpl;
import dao.jdbc.FilmeDAOImpl;
import entidades.Aluguel;
import entidades.Cliente;
import entidades.Filme;

class AluguelDAOImplTest {
	FilmeDAO filmeDAO;
	ClienteDAO clienteDAO;
	AluguelDAO aluguelDAO;

	Connection conn;
	Filme filme;
	Cliente cliente;
	Aluguel aluguel;
	
	List<Filme> filmes;

	@BeforeEach
	void setup() {
		try {
			Class.forName("org.postgresql.Driver");
	        conn = DriverManager.getConnection("jdbc:postgresql://localhost/teste", "postgres", "1234");
	        conn.setAutoCommit(false);	
			
	    	filmeDAO = new FilmeDAOImpl();
	    	clienteDAO = new ClienteDAOImpl();
	    	aluguelDAO = new AluguelDAOImpl();
	    		    	
	    	// Criacao do filme TESTE
	    	filme = new Filme();
	    	filme.setDataLancamento(new Date());
	    	filme.setDescricao("TESTE");
	    	filme.setNome("FILME TESTE");
	    	
	    	// Criacao do cliente TESTE
	    	cliente = new Cliente("TESTE");
	    	clienteDAO.insert(conn, cliente);
	    	
	    	// Inserir cliente
	    	clienteDAO.insert(conn, cliente);
	    	// Inserir filme
	    	filmeDAO.insert(conn, filme);

	    	// Lista Filme
	    	filmes = new ArrayList<>();
	    	filmes.add(filme);

	    	// Criacao aluguel TESTE
	    	aluguel = new Aluguel();
	    	aluguel.setCliente(cliente);
	    	aluguel.setDataAluguel(new Date());
	    	aluguel.setFilmes(filmes);
	    	aluguel.setValor(45);

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
	void inserir() throws Exception {
    	// Inserir aluguel
    	aluguelDAO.insert(conn, aluguel);
    	
    	// Procurar aluguel
    	Aluguel aluguelTeste = aluguelDAO.find(conn, aluguel.getIdAluguel());
    	assertEquals(aluguel.getCliente().getIdCliente(), aluguelTeste.getCliente().getIdCliente());
	}

	@Test
	void edit1() throws Exception {
		// Inserir aluguel
    	aluguelDAO.insert(conn, aluguel);
    	
		// Editar - propriedade valor
    	aluguel.setValor(100);
    	aluguelDAO.edit(conn, aluguel);
    	Aluguel aluguelTeste = aluguelDAO.find(conn, aluguel.getIdAluguel());
    	assertEquals(aluguelTeste.getValor(), 100);
	}
	
	@Test
	void edit2() throws Exception {
		// Inserir aluguel
    	aluguelDAO.insert(conn, aluguel);

    	// Editar - mudar lista de filmes - 0
		filmes.clear();
    	aluguelDAO.edit(conn, aluguel);
    	Aluguel aluguelTeste = aluguelDAO.find(conn, aluguel.getIdAluguel());
    	assertEquals(aluguelTeste.getFilmes().size(), 0);
	}

	@Test
	void deleteFilme() throws Exception {
		// Inserir aluguel
    	aluguelDAO.insert(conn, aluguel);

		// Excluir filme (Alterar quantidade de filmes alugado - 0)
    	filmeDAO.delete(conn, filme.getIdFilme());
    	Aluguel aluguelTeste = aluguelDAO.find(conn, aluguel.getIdAluguel());
    	assertEquals(aluguelTeste.getFilmes().size(), 0);		
	}
	
	
	@Test
	void list() throws Exception {
    	// Lista antiga
		Collection<Aluguel> alugueisAntigos = aluguelDAO.list(conn);

		// Inserir filme
    	aluguelDAO.insert(conn, aluguel);

    	// Lista nova
		Collection<Aluguel> alugueisNovos = aluguelDAO.list(conn);
    	
    	assertEquals(alugueisAntigos.size()+1, alugueisNovos.size());	
	}
	
	@Test
	void delete() throws Exception {
		// Inserir aluguel
    	aluguelDAO.insert(conn, aluguel);

		aluguelDAO.delete(conn, aluguel);
    	Aluguel aluguelTeste = aluguelDAO.find(conn, aluguel.getIdAluguel());
    	assertNull(aluguelTeste);
	}
	
}
