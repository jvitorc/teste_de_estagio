package dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

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
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Connection conn, Aluguel aluguel) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Aluguel find(Connection conn, Integer idAluguel) throws Exception {
		// TODO Auto-generated method stub
		return null;
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
           
            items.add(new Aluguel(idAluguel, null, cliente, dataAluguel, valor));
        }

        return items;
	}
	
	public Collection<Filme> getFilmes(Connection conn, Integer idAluguel)  throws Exception {

		PreparedStatement myStmt = conn.prepareStatement("select * from re_aluguel_filme where id_aluguel = ?");
		myStmt.setInt(1, idAluguel);

		ResultSet myRs = myStmt.executeQuery();

        Collection<Filme> items = new ArrayList<>();

        FilmeDAO filmeDAO = new FilmeDAOImpl();
        
        while (myRs.next()) {
            Integer idFilme = myRs.getInt("id_filme");
            Filme filme = filmeDAO.find(conn, idFilme);
            
            items.add(filme);
        }

        return items;
	}

}
