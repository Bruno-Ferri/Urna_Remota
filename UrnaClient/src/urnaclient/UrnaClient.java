package urnaclient;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.URL;
import javax.swing.JOptionPane;
import view.FrameApuracao;
import view.FrameCadastroEleitor;
import view.FrameConfirmar;
import view.FrameLiberar;

/**
 *
 * @author Admin
 */
public class UrnaClient {

    FrameConfirmar fc = new FrameConfirmar();
    FrameLiberar fl = new FrameLiberar();
    FrameCadastroEleitor fce = new FrameCadastroEleitor();
    FrameApuracao fa = new FrameApuracao();
    static String[] array;

    QName qnameElection = new QName("http://urnaserver/", "UrnaServerElectionImplService");
    QName portqnameElection = new QName("http://urnaserver/", "UrnaServerElectionImplPort");
    QName qnameSecurity = new QName("http://urnaserver/", "UrnaSecurityImplService");
    QName portqnameSecurity = new QName("http://urnaserver/", "UrnaSecurityImplPort");

    public void pesquisacandidato(int legenda) throws MalformedURLException {
        URL url = new URL("http://127.0.0.1:9876/urnaserver?wsdl");

        Service ws = Service.create(url, qnameElection);
        Election election = ws.getPort(portqnameElection, Election.class);
        if (election.verificar(legenda) == false) {
            legenda = 0;
        }
        String[] candidato = election.pesquisar(legenda);
        fc.setVisible(true);
        fc.lbl_candidato.setText(candidato[1]);
        fc.lbl_legenda.setText(candidato[0]);
    }

    public void votar(int legenda) throws MalformedURLException {
        URL url = new URL("http://127.0.0.1:9876/urnaserver?wsdl");
        Service ws = Service.create(url, qnameElection);
        Election election = ws.getPort(portqnameElection, Election.class);
        String[] candidato = election.pesquisar(legenda);
        int votos = 0;

        fc.lbl_legenda.getText();
        votos = Integer.parseInt(candidato[2]);
        votos++;
        election.votar(legenda, votos);
        election.certificar(array[1]);
        JOptionPane.showMessageDialog(null, "Votação concluída!");
    }

    public void pesquisaEleitor(String titulo) throws MalformedURLException {
        URL urlSecurity = new URL("http://127.0.0.2:9876/urnaserver?wsdl");
        Service wssecurity = Service.create(urlSecurity, qnameSecurity);
        Security security = wssecurity.getPort(portqnameSecurity, Security.class);
        

        
        if (security.verificar(titulo) == true) {
            array = security.autenticar(titulo);
            if (Boolean.parseBoolean(array[4])==false) {
                fl.setVisible(true);
                fl.lbl_eleitor.setText(array[0]);
                fl.lbl_titulo.setText(array[1]);
                fl.lbl_zona.setText(array[2]);
                fl.lbl_sessao.setText(array[3]);
            }
            else{
                JOptionPane.showMessageDialog(null, "O eleitor informado já votou.");
                
            }
        } else {
            JOptionPane.showMessageDialog(null, "Eleitor não cadastrado.");
        }
    }

    public void cadastrarEleitor(String nome, String titulo, String zona, String sessao) throws MalformedURLException, UnsupportedEncodingException {
        URL urlElection = new URL("http://127.0.0.1:9876/urnaserver?wsdl");
        URL urlSecurity = new URL("http://127.0.0.2:9876/urnaserver?wsdl");
        Service wselection = Service.create(urlElection, qnameElection);
        Service wssecurity = Service.create(urlSecurity, qnameSecurity);
        Election election = wselection.getPort(portqnameElection, Election.class);
        Security security = wssecurity.getPort(portqnameSecurity, Security.class);
        if (security.verificar(titulo) == false) {
            election.cadastrarEleitor(nome, titulo, zona, sessao);
            JOptionPane.showMessageDialog(null, "Eleitor registrado com sucesso!");
        } else {
            JOptionPane.showMessageDialog(null, "Título de eleitor já cadastrado!");
        }
    }

    public void cadastrarCandidato(String nome, int legenda) throws MalformedURLException {
        URL urlElection = new URL("http://127.0.0.1:9876/urnaserver?wsdl");
        Service wselection = Service.create(urlElection, qnameElection);
        Election election = wselection.getPort(portqnameElection, Election.class);
        if (election.verificar(legenda) == false) {
            election.cadastrarCandidato(nome, legenda);
            JOptionPane.showMessageDialog(null, "Candidato registrado com sucesso!");
        } else {
            JOptionPane.showMessageDialog(null, "Legenda já existente!");
        }
    }

    public String[][] apurarVotacao() throws MalformedURLException {
        URL urlElection = new URL("http://127.0.0.1:9876/urnaserver?wsdl");
        Service wselection = Service.create(urlElection, qnameElection);
        Election election = wselection.getPort(portqnameElection, Election.class);
        String[][] resultado = election.apurarVotacao();
        return resultado;
    }

    public int contarCandidatos() throws MalformedURLException {
        URL urlElection = new URL("http://127.0.0.1:9876/urnaserver?wsdl");
        Service wselection = Service.create(urlElection, qnameElection);
        Election election = wselection.getPort(portqnameElection, Election.class);
        int totalCandidatos = election.contaCandidatos();
        return totalCandidatos;
    }
    
    public void atualizarEleitor(String titulo, String nome, String zona, String sessao) throws MalformedURLException{
        URL urlElection = new URL("http://127.0.0.1:9876/urnaserver?wsdl");
        Service wselection = Service.create(urlElection, qnameElection);
        Election election = wselection.getPort(portqnameElection, Election.class);
        election.atualizarEleitor(titulo, nome, zona, sessao);
        JOptionPane.showMessageDialog(null, "Eleitor atualizado com sucesso!");
        
    }
    public boolean verificarEleitor(String titulo) throws MalformedURLException{
        URL urlSecurity = new URL("http://127.0.0.2:9876/urnaserver?wsdl");
        Service wssecurity = Service.create(urlSecurity, qnameSecurity);
        Security security = wssecurity.getPort(portqnameSecurity, Security.class);
        return security.verificar(titulo);
    }
    
    public String[] preencherCadastroEleitor(String titulo) throws MalformedURLException{
        URL urlSecurity = new URL("http://127.0.0.2:9876/urnaserver?wsdl");
        Service wssecurity = Service.create(urlSecurity, qnameSecurity);
        Security security = wssecurity.getPort(portqnameSecurity, Security.class);
        String eleitor[] = security.autenticar(titulo);
        return eleitor;
    }
    public void apagarEleitor(String titulo) throws MalformedURLException{
        URL urlElection = new URL("http://127.0.0.1:9876/urnaserver?wsdl");
        Service wselection = Service.create(urlElection, qnameElection);
        Election election = wselection.getPort(portqnameElection, Election.class);
        election.apagarEleitor(titulo);
        JOptionPane.showMessageDialog(null, "Eleitor excluído com sucesso!");
    }
    
    public void atualizarCandidato(String nome, int legenda) throws MalformedURLException{
        URL urlElection = new URL("http://127.0.0.1:9876/urnaserver?wsdl");
        Service wselection = Service.create(urlElection, qnameElection);
        Election election = wselection.getPort(portqnameElection, Election.class);
        election.atualizarCandidato(nome, legenda);
        JOptionPane.showMessageDialog(null, "Candidato atualizado com sucesso!");
        
    }
    
    public boolean verificarCandidato(int legenda) throws MalformedURLException{
        URL urlElection = new URL("http://127.0.0.1:9876/urnaserver?wsdl");
        Service wselection = Service.create(urlElection, qnameElection);
        Election election = wselection.getPort(portqnameElection, Election.class);
        return election.verificar(legenda);
    }
    
    public String[] preencherCadastroCandidato(int legenda) throws MalformedURLException{
        URL urlElection = new URL("http://127.0.0.1:9876/urnaserver?wsdl");
        Service wselection = Service.create(urlElection, qnameElection);
        Election election = wselection.getPort(portqnameElection, Election.class);
        String candidato[] = election.pesquisar(legenda);
        return candidato;
    }
    public void apagarCandidato(int legenda) throws MalformedURLException{
        URL urlElection = new URL("http://127.0.0.1:9876/urnaserver?wsdl");
        Service wselection = Service.create(urlElection, qnameElection);
        Election election = wselection.getPort(portqnameElection, Election.class);
        election.apagarCandidato(legenda);
        JOptionPane.showMessageDialog(null, "Candidato excluído com sucesso!");
    }
    public boolean verificaAdm(String usuario, String senha) throws MalformedURLException{
        URL urlSecurity = new URL("http://127.0.0.2:9876/urnaserver?wsdl");
        Service wssecurity = Service.create(urlSecurity, qnameSecurity);
        Security security = wssecurity.getPort(portqnameSecurity, Security.class);
        return security.validarAdm(usuario, senha);
    }
}
