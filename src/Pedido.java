import java.util.Date;

public class Pedido {

    private String sabor;
    private String estado;
    private Cliente cliente;

    public Pedido(String sabor, String estado, Cliente cliente) {
        this.sabor = sabor;
        this.estado = estado;
        this.cliente = cliente;
    }

    public String getSabor() {
        return sabor;
    }

    public void setSabor(String sabor) {
        this.sabor = sabor;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
