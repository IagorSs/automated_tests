package util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaUtil {

  private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("tests");

  public static EntityManager getEntityManager() {
    return factory.createEntityManager();
  }
}
