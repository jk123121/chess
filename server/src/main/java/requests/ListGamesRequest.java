package requests;

public class ListGamesRequest
{
    boolean success;

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public ListGamesRequest(boolean success) { setSuccess(success); }
}
