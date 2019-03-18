package carrot.dto;

public class Result<T> {
    public int status;

    public T result;

    public static Result successRs(){
        Result rs=new Result();
        rs.status=0;
        return rs;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
