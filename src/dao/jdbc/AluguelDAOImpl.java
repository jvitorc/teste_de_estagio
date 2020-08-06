package dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import dao.AluguelDAO;
import entidades.Aluguel;


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
		// TODO Auto-generated method stub
		return null;
	}

}
