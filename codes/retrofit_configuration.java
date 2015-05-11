/**
 * Dagger module for setting up provides statements.
 * Register all of your entry points below.
 */
@Module(
    complete = false,
    injects = {
      ...
    }
)
public class BootstrapModule {

  ...
  
  @Provides
  RestErrorHandler provideRestErrorHandler(Bus bus) {
    return new RestErrorHandler(bus);
  }

  @Provides
  RestAdapterRequestInterceptor provideRestAdapterRequestInterceptor(UserAgentProvider userAgentProvider) {
    return new RestAdapterRequestInterceptor(userAgentProvider);
  }

  @Provides
  RestAdapter provideRestAdapter(RestErrorHandler restErrorHandler,
                                 RestAdapterRequestInterceptor restRequestInterceptor, Gson gson) {
    return new RestAdapter.Builder()
        .setEndpoint(Constants.Http.URL_BASE)
        .setErrorHandler(restErrorHandler)
        .setRequestInterceptor(restRequestInterceptor)
        .setLogLevel(RestAdapter.LogLevel.FULL)
        .setConverter(new GsonConverter(gson))
        .build();
  }

  ...
}
