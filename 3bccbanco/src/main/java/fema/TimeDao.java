package fema;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TimeDao {

    private Connection cnn;

    public TimeDao(Connection cnn) {
        this.cnn = cnn;
    }

    public List<Time> recuperar() throws Exception {
        List<Time> resultado = new ArrayList<Time>();
        String sql = "select * from time order by id";
        PreparedStatement ps = cnn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Time t = new Time();
            t.setId(rs.getInt("id"));
            t.setNome(rs.getString("nome"));
            t.setCidade(rs.getString("cidade"));
            resultado.add(t);
        }
        rs.close();
        ps.close();
        return resultado;
    }

    public void inserir(Time t) throws Exception {
        String sql = "insert into time values (?,?,?)";
        PreparedStatement ps = cnn.prepareStatement(sql);
        ps.setInt(1, t.getId());
        ps.setString(2, t.getNome());
        ps.setString(3, t.getCidade());
        ps.execute();
        ps.close();
    }

    public void atualizar(Time t) throws Exception {
        String sql = "update time set nome = ?, cidade = ? where id = ?";
        PreparedStatement ps = cnn.prepareStatement(sql);
        ps.setString(1, t.getNome());
        ps.setString(2, t.getCidade());
        ps.setInt(3, t.getId());
        ps.execute();
        ps.close();
    }
    
    public void remover(Time t) throws Exception {
        String sql = "delete from time where id = ?";
        PreparedStatement ps = cnn.prepareStatement(sql);
        ps.setInt(1, t.getId());
        ps.execute();
        ps.close();
    }
}