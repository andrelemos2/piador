package br.tur.reservafacil.piador;

import br.tur.reservafacil.piador.domain.UsuarioDomain;
import br.tur.reservafacil.piador.domain.UsuarioDomainImpl;
import br.tur.reservafacil.piador.domain.exceptions.UsuarioJaExisteException;
import br.tur.reservafacil.piador.pio.Usuario;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by christian on 13/05/15.
 */
@WebServlet("/usuario")
public class UsuarioServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(UsuarioServlet.class);

    /**
     * Faz a criação do usuário
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override protected void doPost(HttpServletRequest request, HttpServletResponse response)
		    throws ServletException, IOException {

        try {
            final Usuario newUser = HttpServletRequestUtils.toUsuario(request);
            getUsuarioDomain().novoUsuario(newUser);
        }
        catch (UsuarioJaExisteException e){
            LOGGER.info("O usuario ja existe");
            HttpServletRequestUtils.adicionaErro("Usuario ja existe", request);
        }
        catch (Exception e){
            LOGGER.info("Tentativa de criacao de Usuario invalido: " + e.getMessage());
            HttpServletRequestUtils.adicionaErro("Usuario invalido (faltou login, email ou senha)", request);
        }
        request.setAttribute("sucesso", "Usuário criado. Por favor efetue o login.");
        new IndexServlet().doGet(request, response); //FEIO
        //FEIO
        //FEIOOOOO

        //FEEEEEEIIIIIIIIIIIIIOOOOOOOOOO

        //response.sendRedirect("index"); <-- não dá forward nos atrributos
    }

    UsuarioDomain getUsuarioDomain(){
        return new UsuarioDomainImpl();
    }
}
