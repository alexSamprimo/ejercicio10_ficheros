public class Alumno implements Comparable{ //esto es para implementar la interfaz
    //atributos
    private String apellido;
    private String nombre;

    private float nota1;
    private float nota2;
    private float nota3;


    private float notaFinal;

    //constructores
    public Alumno() {
    }

    public Alumno(String apellido, String nombre, float nota1, float nota2, float nota3) {
        this.apellido = apellido;
        this.nombre = nombre;
        this.nota1 = comprobarNota(nota1);
        this.nota2 = comprobarNota(nota2);
        this.nota3 = comprobarNota(nota3);
        calcularNotaFinal();

    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getNota1() {
        return nota1;
    }

    public void setNota1(float nota1) {
        this.nota1 = comprobarNota(nota1);
    }


    public float getNota2() {
        return nota2;
    }

    public void setNota2(float nota2) {
        this.nota2 = comprobarNota(nota2);
    }

    public float getNota3() {
        return nota3;
    }

    public void setNota3(float nota3) {
        this.nota3 = comprobarNota(nota3);
    }

    public float getNotaFinal() {
        return notaFinal;
    }
    //metodos
    private float comprobarNota(float nota) {
        if (nota >= 0 || nota <= 10){
            return nota;
        }
        return 0;
    }

    public void calcularNotaFinal() {
        notaFinal = (nota1+nota2+nota3)/3;
    }

    //en vez del metodo imprimir haremos esto automaticamente
    @Override
    public String toString() {
        return apellido +
                ";"+nombre +
                ";"+ nota1 +
                ";"+ nota2 +
                ";"+ nota3 +
                ";"+ notaFinal +
                '}';
    }

    @Override
    public int compareTo(Object o) {//este metodo sirve para poder ordenar los objetos dentro de la lista
        //nosotros le damos los criterios de comparacion
        Alumno a = (Alumno)o;
        //este metodo diferencia entre maysuculas y minusculas
        //un nombre con mayuscula ira antes que uno en minuscula
        if (apellido.compareTo(a.apellido) > 0){
            return 1;
        }
        if (apellido.compareTo(a.apellido) < 0){
            return -1;
        }
        return 0;
    }
}
