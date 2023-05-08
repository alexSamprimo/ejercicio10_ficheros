import java.io.*;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static final File ruta = new File("calificaciones.txt");
    static ArrayList<Alumno> lista = new ArrayList<>();
    static String nombre, apellidos;

    public static void main(String[] args) {
        String eleccion = "0";

        do {
            try {
                eleccion = menu();
                switch (eleccion) {
                    case "1":
                        insertarAlumno();
                        break;
                    case "2":
                        consultarAlumno();
                        break;

                    case "3":
                        modificarAlumno();
                        break;

                    case "4":
                        eliminarAlumno();
                        break;

                    case "5":
                        listaAlumnosApellido();
                        break;
                    case "6":
                        listaAlumnosAprobados();
                        break;
                    case "7":
                        listaAlumnosSuspendidos();
                        break;
                    case "8":
                        System.out.println("Adios!");
                        break;
                    default:
                        System.out.println("Opcion no valida");
                }
            } catch (Exception e) {
                System.out.println("Error inesperado");
            }
        } while (!eleccion.equals("8"));


    }

    private static void listaAlumnosSuspendidos() throws Exception {
        int contador = 0;
        guardarFicheroEnLista();
        for (Alumno a : lista) {
            if (a.getNotaFinal() < 5) {
                contador++;
                System.out.println(a.toString());
            }
        }
        System.out.println("Alumnos suspendidos: " + contador);
    }

    private static void listaAlumnosAprobados() throws Exception {
        guardarFicheroEnLista();
        for (Alumno a : lista) {
            if (a.getNotaFinal() >= 5) {
                System.out.println(a.toString());
            }
        }
    }

    private static void listaAlumnosApellido() throws Exception {
        //guardar el fichero en una lista
        guardarFicheroEnLista();

        //ordenar la lista por apellido
        Collections.sort(lista);
        //lista.sort(Alumno::compareTo); otra forma de hacer literalmente lo mismo

        //mostrar la informacion
        mostrarLista();
    }

    private static void eliminarAlumno() throws Exception {
        //pedir nombre y apellidos
        pedirAlumno();

        //guardar fichero en la lista
        guardarFicheroEnLista();

        //buscar el alumno en la lista
        Alumno a = buscarAlumno();// se nos devuelve el objeto que ha encontrado segun nombre y apellido

        if (a != null) {
            //si esta
            //lo elimino de la lista
            lista.remove(a);// lo borramos directamente porque sabemos el objeto ya que se nos ha devuelto con el metodo
            //guardo la lista en el fichero
            guardarListaEnFichero();
            System.out.println("Alumno eliminado con exito");
        } else {
            System.out.println("Ese alumno no existe");
        }

        //si no esta digo que no existe
    }

    private static void modificarAlumno() throws Exception {
        //pedir nombre y apellidos
        pedirAlumno();
        //guardar contenido del fichero en la lista
        guardarFicheroEnLista();

        //recorrer y buscar el alumno
        Alumno a = buscarAlumno();
        if (a != null) {
            //si lo encuentro pido las 3 notas nuevas
            //modificamos el alumno en la lista con las 4 nuevas notas
            System.out.println("Nueva nota 1: ");
            a.setNota1(sc.nextFloat());
            System.out.println("Nueva nota 2: ");
            a.setNota2(sc.nextFloat());
            System.out.println("Nueva nota 3: ");
            a.setNota3(sc.nextFloat());
            a.calcularNotaFinal();

            //escribir toda la lista en el fichero
            guardarListaEnFichero();

        } else {
            System.out.println("Ese alumno no existe");
        }


        //si no lo encuentro digo que no esta
    }

    private static void guardarListaEnFichero() throws IOException {
        FileWriter fw = new FileWriter(ruta);//no le ponemos nada despues de ruta porque queremos sobreescribir
        for (Alumno a : lista) {
            fw.write(a.toString() + "\n");//escribimos en el fichero
        }
        fw.close();
    }

    private static void consultarAlumno() throws Exception {
        boolean existe = false;
        //preguntar nombre y apellido


        pedirAlumno();

        //leer fichero calificaciones.txt y guardar contenido en una lista de alumnos
        guardarFicheroEnLista();
        //mostrarLista();

        //buscar en la lista el alumno
        Alumno a = buscarAlumno();
        if (a != null) {
            System.out.println("Nota 1: " + a.getNota1() + "Nota 2: " + a.getNota2() + " Nota 3: " + a.getNota3() + " Nota final: " + a.getNotaFinal());
        } else {
            System.out.println("No existe ese alumno");
        }
    }


    private static Alumno buscarAlumno() {
        for (Alumno a : lista) {
            if (a.getNombre().equalsIgnoreCase(nombre) && a.getApellido().equalsIgnoreCase(apellidos)) {
                return a;
            }
        }
        return null;

        //si no existe digo que no esta

        //si existe enseño sus notas
    }

    private static void pedirAlumno() {
        System.out.println("Nombre: ");
        nombre = sc.nextLine();
        System.out.println("Apellidos: ");
        apellidos = sc.nextLine();
    }

    private static void mostrarLista() {
        for (Alumno a : lista) {
            System.out.println(a.toString());
        }
    }

    private static void guardarFicheroEnLista() throws Exception {
        lista.clear();//limpiamos primero la lista para que no se duplique
        FileReader fr = new FileReader(ruta);


        BufferedReader br = new BufferedReader(fr); //esto lo lee todo a la vez, en vez de caracter a caracter
        String linea = br.readLine();

        while (linea != null) {
            //lo que hago con la linea que lea
            String[] trozos = linea.split(";");
            Alumno a = new Alumno(trozos[0], trozos[1], Float.parseFloat(trozos[2]), Float.parseFloat(trozos[3]), Float.parseFloat(trozos[4]));
            lista.add(a);
            linea = br.readLine();
        }

    }

    private static void insertarAlumno() {
        //pedir la informacion
        Alumno a = pedirInformacion();
        //guardar la informacion en el fichero calificaciones.txt
        guardarFichero(a);
    }

    private static void guardarFichero(Alumno a) {
        FileWriter fw = null; //el null es solo para que ya este inicializado

        try {
            fw = new FileWriter(ruta, true); //ESTO SIEMPRE DENTRO DE UN TRY CATCH, el true es para que en vez de sobreescribir, añada la informacion
            //escribir lo que toque
            fw.write(a.toString() + "\n"); //le ponemos un salto de linea
        } catch (Exception e) {
            System.out.println("Error al abrir el fichero para escribir");
        } finally {
            try {
                if (fw != null) { //esto es para que solo lo cierre si se llego a abrir
                    fw.close();
                }
            } catch (Exception e) {
                System.out.println("Error al cerrar el fichero");
            }
        }
    }

    private static Alumno pedirInformacion() {
        String nombre, apellidos;
        float nota1 = 0, nota2 = 0, nota3 = 0;
        System.out.println("Nombre: ");
        nombre = sc.nextLine();
        System.out.println("Apellidos: ");
        apellidos = sc.nextLine();
        try {
            System.out.println("Nota1: ");
            nota1 = sc.nextFloat();
            System.out.println("Nota2: ");
            nota2 = sc.nextFloat();
            System.out.println("Nota3: ");
            nota3 = sc.nextFloat();
            sc.nextLine();
        } catch (Exception e) {
            System.out.println("Error en la nota");
            sc.nextLine();
        }
        return new Alumno(apellidos, nombre, nota1, nota2, nota3);

    }

    private static String menu() {
        System.out.println("Elige una opcion");
        System.out.println("1- Insertar un alumno");
        System.out.println("2- Consultar las notas de un alumno");
        System.out.println("3- Modificar las notas de un alumno");
        System.out.println("4- Eliminar un alumno");
        System.out.println("5- Mostrar alumnos por apellido");
        System.out.println("6- Mostrar alumnos aprobados");
        System.out.println("7- Saber el numero de alumnos suspendidos");
        System.out.println("8- Salir");
        return sc.nextLine();
    }
}