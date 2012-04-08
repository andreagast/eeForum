package it.gas.eeforum.beans;

import it.gas.eeforum.entities.Board;
import it.gas.eeforum.exceptions.InvalidIdException;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class ForumEJB {

	@PersistenceContext(unitName = "eeForumPU")
	private EntityManager em;
	
	/** BOARDS **/
	
	public List<Board> getBoards() {
		TypedQuery<Board> query = em.createNamedQuery("board.all", Board.class);
		return query.getResultList();
	}
	
	public Board getBoard(int id) throws InvalidIdException {
		Board b = em.find(Board.class, id);
		System.out.println("This is b: " + b);
		if (b == null)
			throw new InvalidIdException();
		return b;
	}
	
	public void addBoard(String name) {
		Board b = new Board();
		b.setName(name);
		em.persist(b);
	}
	
	public void deleteBoard(Board b) {
		b = em.merge(b);
		em.remove(b);
	}
	
}
