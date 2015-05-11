public class GooglePublisher {

  ...

  public GooglePublisher(String accessToken) {
    credential = new GoogleCredential().setAccessToken(accessToken);
  }

  public Event publishEvent(Event event) throws IOException, GeneralSecurityException {
    com.google.api.services.calendar.Calendar service = getCalendarService(credential);
    return service.events().insert("primary", event).execute();
  }

  public String publishTask(Task task, String userMessage) throws GtdApiException,
      GtdGoogleTaskAlreadyReportedException, GtdPublishInvalidTokenException {
    ...

    //object from google api
    Event event = new Event();
    ...
    
    String id = null;
    try {
      // Insert the new event
      Event createdEvent = publishEvent(event);
      id = createdEvent.getId();
    }
    catch (Exception e) {
     ...
    }
    ...
    return id;
  }

  ...

  public static com.google.api.services.calendar.Calendar
  getCalendarService(GoogleCredential credential) throws IOException, GeneralSecurityException {
    //Credential credential = authorize();
    HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
    return new com.google.api.services.calendar.Calendar.Builder(
        HTTP_TRANSPORT, JSON_FACTORY, credential)
        .setApplicationName(APPLICATION_NAME)
        .build();
  }
}
