package br.com.alura.leilao.dao;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import br.com.alura.leilao.model.Usuario;
import util.JpaUtil;

public class UsuarioDaoTest {

  private UsuarioDao dao;

  private EntityManager em;

  @BeforeEach
  private void init() {
    this.em = JpaUtil.getEntityManager();

    this.dao = new UsuarioDao(em);
  }
  
  @Test
  void testeBuscaDeUsuarioPeloUsername() {
    Usuario usuario = new Usuario("fulano", "fulano@email.com", "1234");
    
    this.em.getTransaction().begin();
    this.em.persist(usuario);
    this.em.getTransaction().commit();

    Usuario encontrado = this.dao.buscarPorUsername("fulano");
    assertNotNull(encontrado);
  }
}
