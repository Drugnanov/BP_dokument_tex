public class FacebookPublisher {

  private final FacebookClient facebookClient;
  
  ...
  
  public FacebookPublisher(String accessToken) {
    this.facebookClient = new DefaultFacebookClient(accessToken, Version.VERSION_2_2);
  }

  ...

  public String publishSimpleMessage(String msgBody) {
    FacebookType publishMessageResponse = facebookClient
        .publish("me/feed", FacebookType.class, Parameter.with("message", msgBody));
    return publishMessageResponse.getId();
  }

  public String publishTask(Task task, String userMessage) throws GtdApiException {
    ...

    String id = null;
    try {
      id = publishSimpleMessage(msgText);
    }
    catch (Exception e) {
      ...
    }
    if (id == null) {
      throw new ...
    }
    return id;
  }
  ...
}
