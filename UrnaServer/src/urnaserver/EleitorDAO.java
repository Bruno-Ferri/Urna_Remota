package urnaserver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class EleitorDAO {

    public void create(Eleitor e) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO tb_eleitor (nome, titulo, zona, sessao, votou) VALUES (?,?,?,?,?)");
            stmt.setString(1, e.getNome());
            stmt.setString(2, e.getTitulo());
            stmt.setString(3, e.getZona());
            stmt.setString(4, e.getSessao());
            stmt.setBoolean(5, e.isVotou());
            stmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(EleitorDAO.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao salvar." + ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public String[] readPesquisaEleitor(String titulo) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String[] eleitor = null;

        try {
            stmt = con.prepareStatement("SELECT * FROM tb_eleitor WHERE titulo LIKE ?");
            stmt.setString(1, titulo);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Eleitor e = new Eleitor();
                e.setNome(rs.getString("nome"));
                e.setTitulo(rs.getString("titulo"));
                e.setSessao(rs.getString("sessao"));
                e.setZona(rs.getString("zona"));
                e.setVotou(rs.getBoolean("votou"));
                String[] aux = {e.nome, e.titulo, e.zona, e.sessao, Boolean.toString(e.votou)};
                eleitor = aux;
            }

        } catch (SQLException ex) {
            Logger.getLogger(EleitorDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return eleitor;
    }

    public boolean verificaEleitor(String titulo) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean result = false;

        try {
            stmt = con.prepareStatement("SELECT titulo FROM tb_eleitor where titulo=?");
            stmt.setString(1, titulo);
            rs = stmt.executeQuery();
            if (rs.next()) {
                result = true;
            } else {
                result = false;
            }

        } catch (SQLException ex) {
            Logger.getLogger(EleitorDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return result;
    }

    public void updateVotou(String titulo) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE tb_eleitor SET votou=? WHERE titulo=?");
            stmt.setBoolean(1, true);
            stmt.setString(2, titulo);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(EleitorDAO.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao atualizar." + ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void update(Eleitor e) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE tb_eleitor SET nome=?, zona=?, sessao=? WHERE titulo=?");
            stmt.setString(1, e.getNome());
            stmt.setString(2, e.getZona());
            stmt.setString(3, e.getSessao());
            stmt.setString(4, e.getTitulo());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(EleitorDAO.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao atualizar." + ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void delete(String titulo) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("DELETE FROM tb_eleitor WHERE titulo=? ");
            stmt.setString(1, titulo);
            stmt.executeUpdate();
            //JOptionPane.showMessageDialog(null, "Registro excluído com sucesso!");
        } catch (SQLException ex) {
            Logger.getLogger(EleitorDAO.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao excluir." + ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
