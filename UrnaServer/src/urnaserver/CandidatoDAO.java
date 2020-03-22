/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package urnaserver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class CandidatoDAO {

    @SuppressWarnings("empty-statement")

    public void create(Candidato c) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO tb_candidato (nome, legenda, votos) VALUES (?,?,?)");
            stmt.setString(1, c.getNome());
            stmt.setInt(2, c.getLegenda());
            stmt.setInt(3, c.getVotos());
            stmt.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(CandidatoDAO.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao salvar." + ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public String[] readPesquisaCandidato(int legenda) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String[] candidato = null;

        try {
            stmt = con.prepareStatement("SELECT * FROM tb_candidato WHERE legenda LIKE ?");
            stmt.setInt(1, legenda);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Candidato c = new Candidato();
                c.setLegenda(rs.getInt("legenda"));
                c.setNome(rs.getString("nome"));
                c.setVotos(rs.getInt("votos"));
                String[] aux = {Integer.toString(c.legenda), c.nome, Integer.toString(c.votos)};
                candidato = aux;
            }

        } catch (SQLException ex) {
            Logger.getLogger(CandidatoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return candidato;
    }

    public void updateVotos(int legenda, int votos) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE tb_candidato SET votos=? WHERE legenda=?");
            stmt.setInt(1, votos);
            stmt.setInt(2, legenda);
            stmt.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(CandidatoDAO.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao atualizar." + ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public boolean verificaCandidato(int legenda) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean result = false;

        try {
            stmt = con.prepareStatement("SELECT legenda FROM tb_candidato where legenda=?");
            stmt.setInt(1, legenda);
            rs = stmt.executeQuery();
            if (rs.next()) {
                result = true;
            } else {
                result = false;
            }

        } catch (SQLException ex) {
            Logger.getLogger(CandidatoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return result;
    }

    public String[][] apuracao() {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int i = 0;
        String[][] candidato = null;
        String[][] aux = null;
        List<Candidato> lista = new ArrayList();
        StringBuilder sb = new StringBuilder();
        try {
            stmt = con.prepareStatement("SELECT * FROM tb_candidato order by votos desc");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Candidato c = new Candidato();
                c.setLegenda(rs.getInt("legenda"));
                c.setNome(rs.getString("nome"));
                c.setVotos(rs.getInt("votos"));
                lista.add(c);
            }
            String[][] dados = new String[lista.size()][3];
            for (Candidato can : lista) {
                dados[i][0] = can.nome;
                dados[i][1] = String.valueOf(can.legenda);
                dados[i][2] = String.valueOf(can.votos);
                i++;
            }
            candidato = dados;
        } catch (SQLException ex) {
            Logger.getLogger(CandidatoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return candidato;
    }

    public int contaCandidatos() {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int i = 0;
        try {
            stmt = con.prepareStatement("SELECT * FROM tb_candidato");
            rs = stmt.executeQuery();

            while (rs.next()) {
                i++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CandidatoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i;
    }
    
    public void update(Candidato c) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE tb_candidato SET nome=? WHERE legenda=?");
            stmt.setString(1, c.getNome());
            stmt.setInt(2, c.getLegenda());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CandidatoDAO.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao atualizar." + ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void delete(int legenda) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("DELETE FROM tb_candidato WHERE legenda=? ");
            stmt.setInt(1, legenda);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CandidatoDAO.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao excluir." + ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
