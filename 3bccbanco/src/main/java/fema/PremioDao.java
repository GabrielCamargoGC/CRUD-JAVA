package fema;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PremioDao {

    private Connection cnn;

    public PremioDao(Connection cnn) {
        this.cnn = cnn;
    }

    public List<Premio> recuperar() throws Exception {
        List<Premio> resultado = new ArrayList<Premio>();
        String sql = "select * from premio order by id";
        PreparedStatement ps = cnn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Premio p = new Premio();
            p.setId(rs.getInt("id"));
            p.setNome(rs.getString("nome"));
            p.setAno(rs.getInt("ano"));
            p.setJogadorId(rs.getInt("jogador_id"));
            resultado.add(p);
        }
        rs.close();
        ps.close();
        return resultado;
    }

    public void inserir(Premio p) throws Exception {
        String sql = "insert into premio values (?,?,?,?)";
        PreparedStatement ps = cnn.prepareStatement(sql);
        ps.setInt(1, p.getId());
        ps.setString(2, p.getNome());
        ps.setInt(3, p.getAno());
        ps.setInt(4, p.getJogadorId());
        ps.execute();
        ps.close();
    }

    public void atualizar(Premio p) throws Exception {
        String sql = "update premio set nome = ?, ano = ?, jogador_id = ? where id = ?";
        PreparedStatement ps = cnn.prepareStatement(sql);
        ps.setString(1, p.getNome());
        ps.setInt(2, p.getAno());
        ps.setInt(3, p.getJogadorId());
        ps.setInt(4, p.getId());
        ps.execute();
        ps.close();
    }
    
    public void remover(Premio p) throws Exception {
        String sql = "delete from premio where id = ?";
        PreparedStatement ps = cnn.prepareStatement(sql);
        ps.setInt(1, p.getId());
        ps.execute();
        ps.close();
    }
}