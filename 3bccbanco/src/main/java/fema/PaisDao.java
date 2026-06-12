package fema;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PaisDao {

	private Connection cnn;

	public PaisDao(Connection cnn) {
		this.cnn = cnn;
	}

	public List<Pais> recuperar() throws Exception {
		List<Pais> resultado = new ArrayList<Pais>();
		String sql = "select * from pais order by id";
		PreparedStatement ps = cnn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Pais p = new Pais();
			p.setId(rs.getInt("id"));
			p.setNome(rs.getString("nome"));
			p.setSigla(rs.getString("sigla"));
			resultado.add(p);
		}
		rs.close();
		ps.close();
		return resultado;
	}

	public void inserir(Pais p) throws Exception {
		String sql = "insert into pais values (?,?,?)";
		PreparedStatement ps = cnn.prepareStatement(sql);
		ps.setInt(1, p.getId());
		ps.setString(2, p.getNome());
		ps.setString(3, p.getSigla());
		ps.execute();
		ps.close();
	}

	public void atualizar(Pais p) throws Exception {
		String sql = "update pais set nome = ?, sigla = ? where id = ?";
		PreparedStatement ps = cnn.prepareStatement(sql);
		ps.setString(1, p.getNome());
		ps.setString(2, p.getSigla());
		ps.setInt(3, p.getId());
		ps.execute();
		ps.close();
	}
	
	public void remover(Pais p) throws Exception {
		String sql = "delete from pais where id = ?";
		PreparedStatement ps = cnn.prepareStatement(sql);
		ps.setInt(1, p.getId());
		ps.execute();
		ps.close();
	}

}
