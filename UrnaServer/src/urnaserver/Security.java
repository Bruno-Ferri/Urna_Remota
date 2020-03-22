package urnaserver;


import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
 
@WebService
@SOAPBinding(style = Style.RPC)
/**
/**
 *
 * @author Admin
 */
public interface Security {
    @WebMethod String[] autenticar(String titulo);
    @WebMethod boolean verificar(String titulo);
    @WebMethod boolean validarAdm (String usuario, String senha);
}
