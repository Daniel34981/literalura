package com.Daniel.literalura.principal;

import com.Daniel.literalura.model.Autor;
import com.Daniel.literalura.model.Datos;
import com.Daniel.literalura.model.DatosAutor;
import com.Daniel.literalura.model.DatosLibro;
import com.Daniel.literalura.model.Libro;
import com.Daniel.literalura.repository.AutorRepository;
import com.Daniel.literalura.repository.LibroRepository;
import com.Daniel.literalura.service.ConsumoAPI;
import com.Daniel.literalura.service.ConvierteDatos;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import java.util.Scanner;

public class Principal {

    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();

    private final String URL_BASE = "https://gutendex.com/books/?search=";

    private LibroRepository libroRepository;
    private AutorRepository autorRepository;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void muestraElMenu() {
        var opcion = -1;

        while (opcion != 0) {
            var menu = """
                    
                    ===== LITERALURA =====
                    1 - Buscar libro por título
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                    6 - Mostrar cantidad de libros por idioma
                    
                    0 - Salir
                    """;

            System.out.println(menu);

            try {
                opcion = Integer.parseInt(teclado.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opción inválida");
                continue;
            }

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosPorAnio();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 6:
                    mostrarCantidadLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private void buscarLibroPorTitulo() {
        System.out.println("Escribe el nombre del libro que deseas buscar:");
        var tituloLibro = teclado.nextLine();

        var json = consumoAPI.obtenerDatos(URL_BASE + tituloLibro.replace(" ", "%20"));
        var datos = conversor.obtenerDatos(json, Datos.class);

        if (datos.resultados() == null || datos.resultados().isEmpty()) {
            System.out.println("No se encontró ningún libro con ese título.");
            return;
        }

        DatosLibro datosLibro = datos.resultados().get(0);

        Optional<Libro> libroExistente = libroRepository.findByTituloIgnoreCase(datosLibro.titulo());
        if (libroExistente.isPresent()) {
            System.out.println("El libro ya está registrado en la base de datos:");
            System.out.println(libroExistente.get());
            return;
        }

        Libro libro = new Libro(datosLibro);

        if (datosLibro.autores() != null && !datosLibro.autores().isEmpty()) {
            DatosAutor datosAutor = datosLibro.autores().get(0);

            Autor autor = autorRepository.findByNombreIgnoreCase(datosAutor.nombre())
                    .orElseGet(() -> autorRepository.save(new Autor(datosAutor)));

            libro.setAutor(autor);
            libroRepository.save(libro);

            System.out.println("Libro guardado exitosamente:");
            System.out.println(libro);
        } else {
            libroRepository.save(libro);
            System.out.println("Libro guardado sin autor registrado:");
            System.out.println(libro);
        }
    }

    private void listarLibrosRegistrados() {
        var libros = libroRepository.findAll();

        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
            return;
        }

        libros.forEach(System.out::println);
    }

    private void listarAutoresRegistrados() {
        var autores = autorRepository.findAll();

        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados.");
            return;
        }

        autores.forEach(System.out::println);
    }

    private void listarAutoresVivosPorAnio() {
        System.out.println("Ingresa el año:");
        try {
            Integer anio = Integer.parseInt(teclado.nextLine());

            List<Autor> autoresConFechaFallecimiento = autorRepository
                    .findByFechaNacimientoLessThanEqualAndFechaFallecimientoGreaterThanEqual(anio, anio);

            List<Autor> autoresSinFechaFallecimiento = autorRepository
                    .findByFechaNacimientoLessThanEqualAndFechaFallecimientoIsNull(anio);

            if (autoresConFechaFallecimiento.isEmpty() && autoresSinFechaFallecimiento.isEmpty()) {
                System.out.println("No se encontraron autores vivos en ese año.");
                return;
            }

            autoresConFechaFallecimiento.forEach(System.out::println);
            autoresSinFechaFallecimiento.forEach(System.out::println);

        } catch (NumberFormatException e) {
            System.out.println("Debes ingresar un año válido.");
        }
    }

    private void listarLibrosPorIdioma() {
        System.out.println("""
            Ingresa el idioma para buscar los libros:
            es - español
            en - inglés
            fr - francés
            pt - portugués
            """);

        var idioma = teclado.nextLine().trim().toLowerCase();

        List<Libro> libros = libroRepository.findByIdiomaIgnoreCase(idioma);

        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros en ese idioma.");
            return;
        }

        libros.forEach(System.out::println);
    }

    private void mostrarCantidadLibrosPorIdioma() {
        System.out.println("""
            Ingresa el idioma para consultar cantidad:
            es - español
            en - inglés
            fr - francés
            pt - portugués
            """);

        var idioma = teclado.nextLine().trim().toLowerCase();

        Long cantidad = libroRepository.countByIdiomaIgnoreCase(idioma);

        System.out.println("Cantidad de libros en idioma '" + idioma + "': " + cantidad);
    }
}
