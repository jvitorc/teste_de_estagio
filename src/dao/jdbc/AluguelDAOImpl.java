package dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import dao.jdbc.ClienteDAOImpl;
import dao.jdbc.FilmeDAOImpl;
import dao.ClienteDAO;
import dao.FilmeDAO;

import dao.AluguelDAO;
import entidades.Aluguel;
import entidades.Cliente;
import entidades.Filme;

public class AluguelDAOImpl implements AluguelDAO {

	@Override
	public void insert(Connection conn, Aluguel aluguel) throws Exception {
        PreparedStatement myStmt = conn.prepareStatement("insert into en_aluguel (id_aluguel, id_cliente, data_aluguel, valor) values (?, ?, ?, ?)");

        Integer idAluguel = this.getNextId(conn);

        myStmt.setInt(1, idAluguel);
        myStmt.setInt(2, aluguel.getCliente().getIdCliente());
        myStmt.setDate(3, new java.sql.Date(aluguel.getDataAluguel().getTime()));
        myStmt.setFloat(4, aluguel.getValor());

        myStmt.execute();

        aluguel.setIdAluguel(idAluguel);		
        
        for (Filme filme: aluguel.getFilmes()) {
        	this.insertFilme(conn, idAluguel, filme);
        }
        conn.commit();	
	}

	private void insertFilme(Connection conn, Integer idAluguel, Filme filme) throws Exception {
		PreparedStatement myStmt = conn.prepareStatement("insert into re_aluguel_filme (id_aluguel, id_filme) values (?, ?)");
		
		Integer idFilme = filme.getIdFilme();

		myStmt.setInt(1, idAluguel);
		myStmt.setInt(2, idFilme);
		
		myStmt.execute();
	}
	
	@Override
	public Integer getNextId(Connection conn) throws Exception {
        PreparedStatement myStmt = conn.prepareStatement("select nextval('seq_en_aluguel')");
        ResultSet rs = myStmt.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

	@Override
	public void edit(Connection conn, Aluguel aluguel) throws Exception {
        PreparedStatement myStmt = conn.prepareStatement("update en_aluguel set id_cliente = (?), data_aluguel = (?), valor = (?)  where id_aluguel = (?)");

        Integer idAluguel = aluguel.getIdAluguel();

        myStmt.setInt(1, aluguel.getCliente().getIdCliente());
        myStmt.setDate(2, new java.sql.Date(aluguel.getDataAluguel().getTime()));
        myStmt.setFloat(3, aluguel.getValor());
        myStmt.setInt(4, idAluguel);

        myStmt.execute();
        conn.commit();

        this.deleteAluguelFilmes(conn, idAluguel);
        
        for (Filme filme: aluguel.getFilmes()) {
        	this.insertFilme(conn, idAluguel, filme);
        }        
	}

	@Override
	public void delete(Connection conn, Aluguel aluguel) throws Exception {
		deleteAluguelFilmes(conn, aluguel.getIdAluguel());
		
		PreparedStatement myStmt = conn.prepareStatement("delete from en_aluguel where id_aluguel = ?");

        myStmt.setInt(1, aluguel.getIdAluguel());

        myStmt.execute();
        conn.commit();
		
	}
	
	private void deleteAluguelFilmes(Connection conn, Integer idAluguel) throws Exception {
		PreparedStatement myStmt = conn.prepareStatement("delete from re_aluguel_filme where id_aluguel = ?");

        myStmt.setInt(1, idAluguel);
        myStmt.execute();		
	}

	@Override
	public Aluguel find(Connection conn, Integer idAluguel) throws Exception {
        PreparedStatement myStmt = conn.prepareStatement("select * from en_aluguel where id_aluguel = ?");

        myStmt.setInt(1, idAluguel);

        ResultSet myRs = myStmt.executeQuery();

        if (!myRs.next()) {
            return null;
        }

        ClienteDAO clienteDAO = new ClienteDAOImpl();

        Integer idCliente = myRs.getInt("id_cliente");
        Cliente cliente = clienteDAO.find(conn, idCliente);
        java.sql.Date dataAluguel = myRs.getDate("data_aluguel");
        float valor = myRs.getFloat("valor"); 
        List<Filme> filmes = getAluguelFilmes(conn, idAluguel);

        return new Aluguel(idAluguel, filmes, cliente, dataAluguel, valor);
	}

	@Override
	public Collection<Aluguel> list(Connection conn) throws Exception {

		PreparedStatement myStmt = conn.prepareStatement("select * from en_aluguel");
        ResultSet myRs = myStmt.executeQuery();

        Collection<Aluguel> items = new ArrayList<>();

        ClienteDAO clienteDAO = new ClienteDAOImpl();
        
        while (myRs.next()) {
            Integer idAluguel = myRs.getInt("id_aluguel");
            Integer idCliente = myRs.getInt("id_cliente");
            Cliente cliente = clienteDAO.find(conn, idCliente);
            java.sql.Date dataAluguel = myRs.getDate("data_aluguel");
            float valor = myRs.getFloat("valor"); 
            List<Filme> filmes = getAluguelFilmes(conn, idAluguel);
            items.add(new Aluguel(idAluguel, filmes, cliente, dataAluguel, valor));
        }

        return items;
	}
	
	private List<Filme> getAluguelFilmes(Connection conn, Integer idAluguel)  throws Exception {

		PreparedStatement myStmt = conn.prepareStatement("select * from re_aluguel_filme where id_aluguel = ?");
		myStmt.setInt(1, idAluguel);

		ResultSet myRs = myStmt.executeQuery();

        List<Filme> items = new ArrayList<>();

        FilmeDAO filmeDAO = new FilmeDAOImpl();
        
        while (myRs.next()) {
            Integer idFilme = myRs.getInt("id_filme");
            Filme filme = filmeDAO.find(conn, idFilme);
            
            items.add(filme);
        }

        return items;
	}

}
