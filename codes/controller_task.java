@RestController
@RequestMapping("/api/v1/tasks")
public class TaskRestController extends RestControllerAbs {

  protected Logger logger = LoggerFactory.getLogger(this.getClass());

  private TaskAdmin taskAdmin;
  private PersonAdmin personAdmin;

  @Autowired
  public void setTaskAdmin(TaskAdmin taskAdmin) {
    this.taskAdmin = taskAdmin;
  }

  @Autowired
  public void setPersonAdmin(PersonAdmin personAdmin) {
    this.personAdmin = personAdmin;
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  @ResponseBody
  public Task get(@PathVariable int id, ServletWebRequest wr, Principal auth) {
    logRequest(wr);
    String userLogin = ((UsernamePasswordAuthenticationToken) auth).getPrincipal().toString();
    Person user = personAdmin.getOsoba(userLogin);
    return taskAdmin.getUkol(id, user);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  @ResponseStatus(HttpStatus.OK)
  public void delete(@PathVariable int id, ServletWebRequest wr, Principal auth) {
    logRequest(wr);
    String userLogin = ((UsernamePasswordAuthenticationToken) auth).getPrincipal().toString();
    Person user = personAdmin.getOsoba(userLogin);
    Task t = taskAdmin.getUkol(id, user);
    taskAdmin.deleteUkol(t, user);
  }

  ...

  @RequestMapping(method = RequestMethod.POST)
  @ResponseStatus(HttpStatus.CREATED)
  public Task create(@RequestBody Task task, Principal auth, ServletWebRequest wr) {
    logRequest(wr);
    String userLogin = ((UsernamePasswordAuthenticationToken) auth).getPrincipal().toString();
    Person user = personAdmin.getOsoba(userLogin);
    taskAdmin.addUkol(task, user, null);
    return task;
  }

  ...
}