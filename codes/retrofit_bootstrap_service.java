/**
 * Bootstrap API service
 */
public class BootstrapService {

  private RestAdapter restAdapter;

  ...

  //used
  
  private UserService getUserService() {
    return getRestAdapter().create(UserService.class);
  }

  ...
  
  //*****************************************
  //***************    PERSON
  //*****************************************
  public Person getPersonByToken() {
    return getUserService().getUsersByToken();
  }

  public Person updatePerson(int personId, Person person) {
    return getUserService().updatePerson(personId, person);
  }

  public PersonAuth authenticate(String email, String password) {
    return getUserService().authenticate(email, password);
  }

  public Person createPerson(PersonCreate personCreate) {
    return getUserService().createPerson(personCreate);
  }

}