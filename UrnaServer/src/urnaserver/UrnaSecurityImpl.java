/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package urnaserver;


import javax.jws.WebService;


@WebService(endpointInterface = "urnaserver.Security")

/**
 *
 * @author Admin
 */
public class UrnaSecurityImpl implements Security {

    @Override
    public String[] autenticar(String titulo) {

        EleitorDAO edao = new EleitorDAO();
        return edao.readPesquisaEleitor(titulo);
        
    }

    @Override
    public boolean verificar(String titulo) {

        EleitorDAO edao = new EleitorDAO();
        return edao.verificaEleitor(titulo);
    }

    @Override
    public boolean validarAdm(String usuario, String senha) {
        AdministradorDAO adao = new AdministradorDAO();
        return adao.verificaAdm(usuario, senha);
    }
}
