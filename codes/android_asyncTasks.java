public class TaskActivity extends BootstrapActivity implements AdapterView.OnItemSelectedListener {

  ...

  public void handleAction(final View view) {
    //cant run more than once at a time
    if (actionTask != null) {
      return;
    }
    final Task task = getTaskFromForms();
    if (task == null) {
      return;
    }
    showProgress();
    actionTask = new SafeAsyncTask<Boolean>() {
      public Boolean call() throws Exception {
        cz.slama.android.gtd.model.Task taskResponse;
        switch (actionType) {
          case TASK_CREATE:
            taskResponse = bootstrapService.createTask(task);
            break;
          case TASK_UPDATE:
            taskResponse = bootstrapService.updateTask(task.getId(), task);
            break;
          case TASK_DELETE:
            bootstrapService.deleteTask(task.getId());
            break;
        }
        return true;
      }

      @Override
      protected void onException(final Exception e) throws RuntimeException {
        // Retrofit Errors are handled inside of the {
        if (!(e instanceof RetrofitError)) {
          final Throwable cause = e.getCause() != null ? e.getCause() : e;
          if (cause != null) {
            Toaster.showLong(TaskActivity.this, getString(R.string.label_something_wrong));
          }
        }
        actionTask = null;
      }

      @Override
      public void onSuccess(final Boolean authSuccess) {
        ReloadStatus.setTaskToReload(true);
        actionTask = null;
        goHome();
      }

      @Override
      protected void onFinally() throws RuntimeException {
        hideProgress();
        actionTask = null;
      }
    };
    actionTask.execute();
  }

  ...


  @Subscribe
  public void onUnAuthorizedErrorEvent(UnAuthorizedErrorEvent unAuthorizedErrorEvent) {
    Toaster.showLong(TaskActivity.this, R.string.message_bad_credentials);
  }

  @Subscribe
  public void onNetworkErrorEvent(NetworkErrorEvent networkErrorEvent) {
    Toaster.showLong(TaskActivity.this, R.string.message_bad_network);
  }

  @Subscribe
  public void onRestAdapterErrorEvent(RestAdapterErrorEvent restAdapterErrorEvent) {
    Toaster.showLong(TaskActivity.this, R.string.message_bad_restRequest);
  }

  @Subscribe
  public void onBadRequestErrorEvent(BadRequestErrorEvent badRequestErrorEvent) {
    Toaster.showLong(TaskActivity.this, R.string.message_bad_restRequest);
  }

  @Subscribe
  public void onAlreadyReportedErrorEvent(AlreadyReportedErrorEvent alreadyReportedErrorEvent) {
    Toaster.showLong(TaskActivity.this, R.string.task_post_already_reported);
  }

}
