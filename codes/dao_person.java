@Component
public class DAOPerson extends DAOGeneric<Person> implements IDAOPerson {

    ...

    @Override
    @SuppressWarnings("unchecked")
    public Person getOsoba(String login) {
        Session session = null;
        Transaction tx = null;
        List<Person> persons = null;
        try {
            session = this.openSession();
            tx = session.beginTransaction();
            Query query = session.createQuery(
                    "from " + Person.class.getName() + " p "
                    + "where p.username = :login"
            );
            query.setParameter("login", login);
            persons = (List<Person>) query.list();
            tx.commit();
        } catch (HibernateException e) {
            handleException(e, tx);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        if (persons == null || persons.isEmpty()) {
            throw new ItemNotFoundException("User '" + login
                    + "' not found");
        }
        return persons.get(0);
    }

    ...

    @Override
    public Person get(int id) {
        return (Person) this.get(Person.class, id);
    }
}
