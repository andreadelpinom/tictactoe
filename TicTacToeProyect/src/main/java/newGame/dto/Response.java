package newGame.dto;

import java.io.Serializable;

public class Response implements Serializable {
    private boolean success; // Indica si la operación fue exitosa
    private String message;  // Mensaje de resultado
    private Object data;     // Datos adicionales (opcional)

    public Response(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}