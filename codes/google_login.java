public class GoogleActivity extends BootstrapActivity implements
    GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks
    , View.OnClickListener {

  ...

  private final static String CALENDAR_API_SCOPE = "https://www.googleapis.com/auth/plus.login https://www.googleapis.com/auth/calendar"; //https://www.googleapis.com/auth/calendar

  private final static String GOOGLE_CALENDAR_API_SCOPE = "oauth2:" + CALENDAR_API_SCOPE;

  private static String SCOPE_TO_USE = GOOGLE_CALENDAR_API_SCOPE;

  ...

  /* Request code used to invoke sign in user interactions. */
  private static final int RC_SIGN_IN = 0;
  private static final int REQUEST_CODE_TOKEN_AUTH = 2;

  /* Client used to interact with Google APIs. */
  private GoogleApiClient mGoogleApiClient;

  /**
   * True if the sign-in button was clicked.  When true, we know to resolve all
   * issues preventing sign-in without waiting.
   */
  private boolean mSignInClicked;

  /**
   * True if we are in the process of resolving a ConnectionResult
   */
  private boolean mIntentInProgress;

  ...

  @Override
  protected void onCreate(final Bundle savedInstanceState) {
    ...
    mGoogleApiClient = new GoogleApiClient.Builder(this)
        .addConnectionCallbacks(this)
        .addOnConnectionFailedListener(this)
        .addApi(Plus.API)
        .addScope(Plus.SCOPE_PLUS_PROFILE)
        .build();

    // googleLoginButton is instance of com.google.android.gms.common.SignInButton
    googleLoginButton.setOnClickListener(this);
    ...
  }

  ...

  public void obtainToken() {
    AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
      @Override
      protected String doInBackground(Void... params) {
        ...        
          String accountName = Plus.AccountApi.getAccountName(mGoogleApiClient);
          token = GoogleAuthUtil.getToken(
              GoogleActivity.this,
              accountName,
              SCOPE_TO_USE);
        ...
        }
        return token;
      }

      @Override
      protected void onPostExecute(String s) {
        super.onPostExecute(s);
        processGoogleToken(s);
      }
    };
    task.execute();
  }

  private void processGoogleToken(String s) {
    ...
    ShrPrefUtils.saveGoogleToken(s);
    ...
  }

  ...

  @Override
  public void onConnected(Bundle connectionHint) {
    mSignInClicked = false;
    Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();
    obtainToken();
  }

  @Override
  protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
    if (requestCode == RC_SIGN_IN) {
      if (responseCode != RESULT_OK) {
        mSignInClicked = false;
      }
      mIntentInProgress = false;

      if (!mGoogleApiClient.isConnected()) {
        mGoogleApiClient.reconnect();
      }
    }
    if (requestCode == REQUEST_CODE_TOKEN_AUTH && responseCode == RESULT_OK) {
      Bundle extra = intent.getExtras();
      String oneTimeToken = extra.getString("authtoken");
    }
  }

  public void onConnectionSuspended(int cause) {
    mGoogleApiClient.connect();
  }
  
  ...
  
}