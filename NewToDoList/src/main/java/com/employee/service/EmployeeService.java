package com.employee.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.deltaspike.jpa.api.transaction.Transactional;
import org.primefaces.model.SortOrder;

import com.employee.model.EmployeeModel;

@Stateless
@Transactional
public class EmployeeService {

	@PersistenceContext(unitName = "applicationTodoPU")
	private EntityManager em;

	private EntityManager getEntityManager() {
		try {
			EntityManagerFactory factory = Persistence.createEntityManagerFactory("applicationTodoLocalPU");
			em = factory.createEntityManager();
			return em;
		} catch (Exception e) {
			System.out.println("Exception in getEntityManager() " + e);
			return null;
		}
	}

	// add
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void add(EmployeeModel employeeModel) {
		em.persist(employeeModel);
		em.flush();
	}

	// getAll
	public List<EmployeeModel> getAll() {
		String jpql = "SELECT e FROM EmployeeModel e";
		TypedQuery<EmployeeModel> query = em.createQuery(jpql, EmployeeModel.class);
		return query.getResultList();
	}

	// update
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void update(EmployeeModel updatedEmployee) {
		em.merge(updatedEmployee);
		em.flush();
	}

	// delete
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void delete(String id) {
		EmployeeModel employee = em.find(EmployeeModel.class, id);
		if (employee != null) {
			em.remove(employee);
			em.flush();
		}
	}

	// getAllId
	public List<String> getAllId() {
		String jpql = "SELECT e.id FROM EmployeeModel e";
		TypedQuery<String> query = em.createQuery(jpql, String.class);
		return query.getResultList();
	}

	// getEmployee for pagination,sorting,filtering
	public List<EmployeeModel> getEmployeeList(int start, int size, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {

		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<EmployeeModel> createQuery = criteriaBuilder.createQuery(EmployeeModel.class);
		Root<EmployeeModel> root = createQuery.from(EmployeeModel.class);

		if (sortField != null) {
			createQuery.orderBy(sortOrder == SortOrder.ASCENDING ? criteriaBuilder.asc(root.get(sortField))
					: criteriaBuilder.desc(root.get(sortField)));
		}

		CriteriaQuery<EmployeeModel> select = createQuery.select(root);

		if (filters != null && filters.size() > 0) {
			List<Predicate> predicates = new ArrayList<>();
			for (Map.Entry<String, Object> entry : filters.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				if (value == null) {
					continue;
				}

				Expression<String> expression = root.get(key).as(String.class);
				Predicate P = criteriaBuilder.like(criteriaBuilder.lower(expression),
						"%" + value.toString().toLowerCase() + "%");
				predicates.add(P);
				
			}
			if (predicates.size() > 0) { 
				createQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
			}
		}
		TypedQuery<EmployeeModel> query = em.createQuery(select);
		query.setFirstResult(start);
		query.setMaxResults(size);
		List<EmployeeModel> result = query.getResultList();
		return result;
	}

	public int getEmployeeTotalCount() {
		Query query = em.createQuery("SELECT COUNT(e.id) FROM EmployeeModel e");
		return ((Long) query.getSingleResult()).intValue();
	}

}
