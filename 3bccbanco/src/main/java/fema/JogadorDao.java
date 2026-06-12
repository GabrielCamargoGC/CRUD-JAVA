package fema;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class JogadorDao {

    private Connection cnn;

    public JogadorDao(Connection cnn) {
        this.cnn = cnn;
    }

    public List<Jogador> recuperar() throws Exception {
        List<Jogador> resultado = new ArrayList<Jogador>();
        String sql = "select * from jogador order by id";
        PreparedStatement ps = cnn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Jogador j = new Jogador();
            j.setId(rs.getInt("id"));
            j.setNome(rs.getString("nome"));
            j.setPosicao(rs.getString("posicao"));
            j.setTimeId(rs.getInt("time_id"));
            resultado.add(j);
        }
        rs.close();
        ps.close();
        return resultado;
    }

    public void inserir(Jogador j) throws Exception {
        String sql = "insert into jogador values (?,?,?,?)";
        PreparedStatement ps = cnn.prepareStatement(sql);
        ps.setInt(1, j.getId());
        ps.setString(2, j.getNome());
        ps.setString(3, j.getPosicao());
        ps.setInt(4, j.getTimeId());
        ps.execute();
        ps.close();
    }

    public void atualizar(Jogador j) throws Exception {
        String sql = "update jogador set nome = ?, posicao = ?, time_id = ? where id = ?";
        PreparedStatement ps = cnn.prepareStatement(sql);
        ps.setString(1, j.getNome());
        ps.setString(2, j.getPosicao());
        ps.setInt(3, j.getTimeId());
        ps.setInt(4, j.getId());
        ps.execute();
        ps.close();
    }
    
    public void remover(Jogador j) throws Exception {
        String sql = "delete from jogador where id = ?";
        PreparedStatement ps = cnn.prepareStatement(sql);
        ps.setInt(1, j.getId());
        ps.execute();
        ps.close();
    }
}