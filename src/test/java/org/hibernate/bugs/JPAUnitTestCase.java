package org.hibernate.bugs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;


import org.hibernate.Session;
import org.hibernate.bugs.model.Child;
import org.hibernate.bugs.model.Parent;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM, using the Java Persistence API.
 */
public class JPAUnitTestCase {

	//private static Logger logger = LogManager.getLogger(JPAUnitTestCase.class);

	private EntityManagerFactory entityManagerFactory;

	@Before
	public void init() {

		entityManagerFactory = Persistence.createEntityManagerFactory( "templatePU" );
	}

	@After
	public void destroy() {
		entityManagerFactory.close();
	}

	// Entities are auto-discovered, so just add them anywhere on class-path
	// Add your tests, using standard JUnit.



	@Test
	public void queryEntityWithCircularReference() throws Exception {

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(createEntity());
		entityManager.getTransaction().commit();
		entityManager.close();


		entityManager = entityManagerFactory.createEntityManager();
		Child result = entityManager.createQuery("SELECT child from Child child WHERE child.objectId = 'c1'", Child.class).getSingleResult();


		/*
		logger.info("----- Result from query: -----");
		logger.info(result.toString());
		logger.info(result.getParent().getFavouriteChild().toString());
*/
		Assert.assertTrue(result == result.getParent().getFavouriteChild());
		entityManager.close();

	}
	@Test
	public void findEntityWithCircularReference() throws Exception {


		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(createEntity());
		entityManager.getTransaction().commit();
		entityManager.close();

		entityManager = entityManagerFactory.createEntityManager();
		Child result = entityManager.find(Child.class, "c1");
/*
		logger.info("----- Result from find: -----");
		logger.info(result.toString());
		logger.info(result.getParent().getFavouriteChild().toString());
*/
		Assert.assertTrue(result == result.getParent().getFavouriteChild());
		entityManager.close();

	}


	private Child createEntity() {
		Parent p1 = new Parent();
		p1.setObjectId("p1");

		Child c1 = new Child();
		c1.setObjectId("c1");

		c1.setParent(p1);

		p1.setFavouriteChild(c1);

		return c1;
	}
}
