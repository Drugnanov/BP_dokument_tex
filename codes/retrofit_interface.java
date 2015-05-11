/**
 * User service for connecting the the REST API and
 * getting the users.
 */
public interface UserService {

    @GET(Constants.Http.URL_AUTH_FRAG) 
    PersonAuth authenticate(@Path(Constants.Http.PARAM_USERNAME) String username, @Header(Constants.Http.PARAM_PASSWORD) String password);

    @POST(Constants.Http.URL_PERSONS_FRAG)
    Person createPerson(@Body PersonCreate personCreate);

    @GET(Constants.Http.URL_PERSON_BY_TOKEN_FRAG)
    Person getUsersByToken();

    @PUT(Constants.Http.URL_PERSON_FRAG)
    Person updatePerson(@Path(Constants.Http.PARAM_PERSON_ID) Integer personId, @Body Person person);
}
