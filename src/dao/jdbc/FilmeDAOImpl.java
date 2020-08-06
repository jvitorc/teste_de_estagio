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
        PreparedStatement myStmt = conn.prepareStatement("insert into en_filme (id_filme, data_lancamento, nome, descricao) values (?, ?, ?, ?)");

        Integer idFilme = this.getNextId(conn);

        myStmt.setInt(1, idFilme);
        myStmt.setDate(2, new java.sql.Date(filme.getDataLancamento().getTime()));
        myStmt.setString(3, filme.getNome());
        myStmt.setString(4, filme.getDescricao());

        myStmt.execute();
        conn.commit();


        filme.setIdFilme(idFilme);		
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

        PreparedStatement myStmt = conn.prepareStatement("update en_filme set data_lancamento = (?), nome = (?), descricao = (?)  where id_filme = (?)");

        myStmt.setDate(1, new java.sql.Date(filme.getDataLancamento().getTime()));
        myStmt.setString(2, filme.getNome());
        myStmt.setString(3, filme.getDescricao());
        myStmt.setInt(4, filme.getIdFilme());
        
        myStmt.execute();
        conn.commit();
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
