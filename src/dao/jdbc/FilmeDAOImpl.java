package dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;

import dao.FilmeDAO;
import entidades.Filme;

public class FilmeDAOImpl implements FilmeDAO {

	@Override
	public void insert(Connection conn, Filme filme) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Integer getNextId(Connection conn) throws Exception {
        PreparedStatement myStmt = conn.prepareStatement("select nextval('seq_en_filme')");
        ResultSet rs = myStmt.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

	@Override
	public void edit(Connection conn, Filme filme) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Connection conn, Integer idFilme) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Filme find(Connection conn, Integer idFilme) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Filme> list(Connection conn) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
