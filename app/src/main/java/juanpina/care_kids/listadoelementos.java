package juanpina.care_kids;

public class listadoelementos {
    public String color;
    public String nombre;
    public String numero;

    public listadoelementos(String color, String nombre, String numero) {
        this.color = color;
        this.nombre = nombre;
        this.numero = numero;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
