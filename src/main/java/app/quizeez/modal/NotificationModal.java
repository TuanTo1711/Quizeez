package app.quizeez.modal;

public class NotificationModal {

    private boolean success;
    private String message;

    public NotificationModal() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NotificationModal(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
