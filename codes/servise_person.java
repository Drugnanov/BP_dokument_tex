
@Component
public class PersonAdmin {

    @Autowired
    private IDAOPerson daoOsoba;
    @Autowired
    private IDAOState daoStav;
    @Autowired
    private IDAOPersonToken daoPersonToken;
    @Autowired
    private TaskAdmin taskAdmin;
    @Autowired
    private ProjectAdmin projectAdmin;
    @Autowired
    private ContextAdmin contextAdmin;
    @Autowired
    private TokenUtils tokenUtils;

    ...

    public void addPersonToken(PersonToken personToken) {
        if (!personToken.checkValidate()) {
            throw new InvalidEntityException("Invalid token data:" + personToken.toString());
        }
        daoPersonToken.create(personToken);
    }

    public PersonToken createPersonToken(Person osoba) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        PersonToken personToken = PersonToken.createPersonToken(osoba);
        addPersonToken(personToken);
        return personToken;
    }

    public Person addOsoba(Person person)
            throws UnsupportedEncodingException, NoSuchAlgorithmException, ResourceExistsException {
        if (!isValid(person)) {
            throw new InvalidEntityException("Invalid person data.");
        }
        if (daoOsoba.existPerson(person.getUsername())) {
            throw new ResourceExistsException("Cannot add person due to existing login:" + person.getUsername());
        }
        person.setPassword(HashConverter.md5(person.getPassword()));
        daoOsoba.create(person);
        person = daoOsoba.get(person.getId());
        tokenUtils.getToken(person);
        return daoOsoba.get(person.getId());
    }

    ...

}
