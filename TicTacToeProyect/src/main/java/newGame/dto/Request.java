package newGame.dto;

import java.io.Serializable;

// Clase base para solicitudes
public class Request implements Serializable {
    private String operation; // Tipo de operación (e.g., "LOGIN", "SIGN_UP")
    private Object payload;   // Datos asociados (e.g., un UserDTO)

    public Request(String operation, Object payload) {
        this.operation = operation;
        this.payload = payload;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }
}
