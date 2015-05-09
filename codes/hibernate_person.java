@Entity
@Table(name = "person")
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Person {

    public static final int MAX_LENGTH_FIRST_NAME = 20;
    public static final int MAX_LENGTH_LAST_NAME = 20;
    public static final int MAX_LENGTH_LOGIN = 20;
    public static final int MAX_LENGTH_PASSWORD = 50;

    @Id
    @GeneratedValue
    private int id;

    @Column(length = MAX_LENGTH_FIRST_NAME, nullable = false, name = "name")
    private String name;

    @Column(length = MAX_LENGTH_PASSWORD, nullable = false)
    private String password;

    @Transient
    private String passwordRaw;

    @Column(nullable = false, name = "right_generate_token")
    @JsonIgnore
    private Boolean rightGenerateToken;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PersonToken> tokens;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Contact> contacts;
 
    @Column(length = MAX_LENGTH_LOGIN, nullable = false, unique = true, name = "login")
    private String username;

    @Column(length = MAX_LENGTH_LAST_NAME, nullable = false, name = "surname")
    private String surname;

    @ManyToOne
    @JoinColumn(nullable = false, name = "state_id")
    @JsonProperty(value = ApiConstants.STATE)
    private PersonState state;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Project> projects;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Context> contexts;

    ....
}