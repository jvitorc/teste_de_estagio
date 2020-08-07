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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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
            
            testeFilme(conn);
            testeAluguel(conn);

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
    	
    	// Listar filmes
    	boolean filmeEncontrado = false;
    	for (Filme f: filmeDAO.list(conn)) {
    		if (f.getIdFilme() == filme.getIdFilme()) {
    			filmeEncontrado = true;
    			break;
    		}
    	}
    	System.out.println(filmeEncontrado);

    	// Excluir
    	filmeDAO.delete(conn, filme.getIdFilme());
    	testeFilme = filmeDAO.find(conn, filme.getIdFilme());
    	System.out.println(testeFilme == null);
    	
   }
    

    public static void testeAluguel(Connection conn) throws Exception {
    	FilmeDAO filmeDAO = new FilmeDAOImpl();
    	ClienteDAO clienteDAO = new ClienteDAOImpl();
    	AluguelDAO aluguelDAO = new AluguelDAOImpl();
    	
    	// Criacao do filme TESTE
    	Filme filme = new Filme();
    	filme.setDataLancamento(new Date());
    	filme.setDescricao("TESTE");
    	filme.setNome("FILME TESTE");
    	
    	// Criacao do cliente TESTE
    	Cliente cliente = new Cliente("TESTE");
    	clienteDAO.insert(conn, cliente);
    	
    	// Inserir cliente
    	clienteDAO.insert(conn, cliente);
    	// Inserir filme
    	filmeDAO.insert(conn, filme);

    	// Lista Filme
    	List<Filme> filmes = new ArrayList<>();
    	filmes.add(filme);

    	
    	// Criacao aluguel TESTE
    	Aluguel aluguel = new Aluguel();
    	aluguel.setCliente(cliente);
    	aluguel.setDataAluguel(new Date());
    	aluguel.setFilmes(filmes);
    	aluguel.setValor(45);
    	
    	// Inserir aluguel
    	aluguelDAO.insert(conn, aluguel);
    	
    	// Procurar aluguel
    	Aluguel aluguelTeste = aluguelDAO.find(conn, aluguel.getIdAluguel());
    	System.out.println(aluguel.getCliente().getIdCliente() == aluguelTeste.getCliente().getIdCliente());
    	
    	// Editar - propriedade valor
    	aluguel.setValor(100);
    	aluguelDAO.edit(conn, aluguel);
    	aluguelTeste = aluguelDAO.find(conn, aluguel.getIdAluguel());
    	System.out.println(aluguelTeste.getValor() == 100);
    	
    	// Editar - mudar lista de filmes - 0
    	filmes.clear();
    	aluguelDAO.edit(conn, aluguel);
    	aluguelTeste = aluguelDAO.find(conn, aluguel.getIdAluguel());
    	System.out.println(aluguelTeste.getFilmes().size() == 0);

    	// Editar - mudar lista de filmes - 1    	
    	filmes.add(filme);
    	aluguelDAO.edit(conn, aluguel);
    	aluguelTeste = aluguelDAO.find(conn, aluguel.getIdAluguel());
    	System.out.println(aluguelTeste.getFilmes().size() == 1);

    	// Excluir filme (Alterar quantidade de filmes alugado - 0)
    	filmeDAO.delete(conn, filme.getIdFilme());
    	aluguelTeste = aluguelDAO.find(conn, aluguel.getIdAluguel());
    	System.out.println(aluguelTeste.getFilmes().size() == 0);
    	
    	// Listar alugueis
    	boolean aluguelEncontrado = false;
    	for (Aluguel a: aluguelDAO.list(conn)) {
    		if (a.getIdAluguel() == aluguel.getIdAluguel()) {
    			aluguelEncontrado = true;
    			break;
    		}
    	}
    	System.out.println(aluguelEncontrado);
    	    	
    	// Excluir aluguel
    	aluguelDAO.delete(conn, aluguel);
    	aluguelTeste = aluguelDAO.find(conn, aluguel.getIdAluguel());
    	System.out.println(aluguelTeste == null);
    	    	
    }
    
}