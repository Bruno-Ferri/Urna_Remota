package urnaserver;


import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
 
@WebService
@SOAPBinding(style = Style.RPC)
/**
 *
 * @author Admin
 */
public interface Election {
  @WebMethod void votar(int legenda, int votos);
  @WebMethod String[] pesquisar(int legenda);
  @WebMethod boolean verificar(int legenda);
  @WebMethod void certificar (String titulo);
  @WebMethod void cadastrarEleitor (String nome, String titulo, String zona, String sessao);
  @WebMethod void cadastrarCandidato (String nome, int legenda);
  @WebMethod String[][] apurarVotacao();
  @WebMethod int contaCandidatos();
  @WebMethod void atualizarEleitor(String titulo, String nome, String zona, String sessao);
  @WebMethod void apagarEleitor(String titulo);
  @WebMethod void atualizarCandidato(String nome, int legenda);
  @WebMethod void apagarCandidato(int legenda);
}
