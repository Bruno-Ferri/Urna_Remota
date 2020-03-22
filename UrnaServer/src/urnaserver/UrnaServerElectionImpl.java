package urnaserver;



import javax.jws.WebService;
 
@WebService(endpointInterface = "urnaserver.Election")
/**
 *
 * @author Admin
 */
public class UrnaServerElectionImpl implements Election{
    
    @Override
    public String[] pesquisar(int legenda){
        CandidatoDAO cdao = new CandidatoDAO();
        return cdao.readPesquisaCandidato(legenda);
    }
    @Override
    public void votar(int legenda, int votos) {
        CandidatoDAO cdao = new CandidatoDAO();
        cdao.updateVotos(legenda, votos);
  }

    @Override
    public boolean verificar(int legenda) {
        CandidatoDAO cdao = new CandidatoDAO();
        return cdao.verificaCandidato(legenda);
    }

    @Override
    public void certificar(String titulo) {
        EleitorDAO edao = new EleitorDAO();
        edao.updateVotou(titulo);
   }

    @Override
    public void cadastrarEleitor(String nome, String titulo, String zona, String sessao) {
        Eleitor e = new Eleitor();
        e.setNome(nome);
        e.setTitulo(titulo);
        e.setZona(zona);
        e.setSessao(sessao);
        e.setVotou(false);
        EleitorDAO edao = new EleitorDAO();
        edao.create(e);
   }

    @Override
    public void cadastrarCandidato(String nome, int legenda) {
        Candidato c = new Candidato();
        c.setNome(nome);
        c.setLegenda(legenda);
        c.setVotos(0);
        CandidatoDAO cdao = new CandidatoDAO();
        cdao.create(c);
    }

    @Override
    public String[][] apurarVotacao() {
        CandidatoDAO cdao = new CandidatoDAO();
        return cdao.apuracao();
    }

    @Override
    public int contaCandidatos() {
        CandidatoDAO cdao = new CandidatoDAO();
        return cdao.contaCandidatos();
    }

    @Override
    public void atualizarEleitor(String titulo, String nome, String zona, String sessao) {
        EleitorDAO edao = new EleitorDAO();
        Eleitor e = new Eleitor();
        e.setNome(nome);
        e.setSessao(sessao);
        e.setTitulo(titulo);
        e.setZona(zona);
        edao.update(e);
    }

    @Override
    public void apagarEleitor(String titulo) {
        EleitorDAO edao = new EleitorDAO();
        edao.delete(titulo);
    }

    @Override
    public void atualizarCandidato(String nome, int legenda) {
        CandidatoDAO cdao = new CandidatoDAO();
        Candidato c = new Candidato();
        c.setLegenda(legenda);
        c.setNome(nome);
        cdao.update(c);
    }

    @Override
    public void apagarCandidato(int legenda) {
        CandidatoDAO cdao = new CandidatoDAO();
        cdao.delete(legenda);
    }
}
