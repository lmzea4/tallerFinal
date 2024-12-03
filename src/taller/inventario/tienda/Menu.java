package taller.inventario.tienda;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Menu {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Inventario inventario = new Inventario();

    public static void main(String[] args) {
        while (true) {
            mostrarMenu();
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1: // Agregar producto
                    agregarProducto();
                    break;
                case 2: // Actualizar producto
                    actualizarProducto();
                    break;
                case 3: // Eliminar producto
                    eliminarProducto();
                    break;
                case 4: // Buscar por categoría
                    buscarPorCategoria();
                    break;
                case 5: // Generar reporte
                    generarReporte();
                    break;
                case 6: // Cantidad de productos por categoría
                    cantidadDeProductosPorCategoria();
                    break;
                case 7: // Producto más caro
                    productoMasCaro();
                    break;
                case 8: // Salir
                    System.out.println("Saliendo del sistema...");
                    return;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    public static void mostrarMenu() {
        System.out.println("\n--- Menú de Inventario ---");
        System.out.println("1. Agregar producto");
        System.out.println("2. Actualizar producto");
        System.out.println("3. Eliminar producto");
        System.out.println("4. Buscar por categoría");
        System.out.println("5. Generar reporte");
        System.out.println("6. Cantidad de productos por categoría");
        System.out.println("7. Producto más caro");
        System.out.println("8. Salir");
        System.out.print("Elija una opción: ");
    }

    public static void agregarProducto() {
        System.out.print("Ingrese ID del producto: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir salto de línea
        System.out.print("Ingrese nombre del producto: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese categoría: ");
        String categoria = scanner.nextLine();
        System.out.print("Ingrese precio: ");
        double precio = scanner.nextDouble();
        System.out.print("Ingrese cantidad disponible: ");
        int cantidad = scanner.nextInt();

        Producto nuevoProducto = new Producto(id, nombre, categoria, precio, cantidad);
        inventario.agregarProducto(nuevoProducto);
        System.out.println("Producto agregado correctamente.");
    }

    public static void actualizarProducto() {
        System.out.print("Ingrese ID del producto a actualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir salto de línea
        System.out.print("Ingrese nuevo nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese nueva categoría: ");
        String categoria = scanner.nextLine();
        System.out.print("Ingrese nuevo precio: ");
        double precio = scanner.nextDouble();
        System.out.print("Ingrese nueva cantidad: ");
        int cantidad = scanner.nextInt();

        Producto productoActualizado = new Producto(id, nombre, categoria, precio, cantidad);
        if (inventario.actualizarProducto(id, productoActualizado)) {
            System.out.println("Producto actualizado correctamente.");
        } else {
            System.out.println("Producto no encontrado.");
        }
    }

    public static void eliminarProducto() {
        System.out.print("Ingrese ID del producto a eliminar: ");
        int id = scanner.nextInt();
        if (inventario.eliminarProducto(id)) {
            System.out.println("Producto eliminado correctamente.");
        } else {
            System.out.println("Producto no encontrado.");
        }
    }

    public static void buscarPorCategoria() {
        System.out.print("Ingrese categoría a buscar: ");
        String categoria = scanner.nextLine();
        List<Producto> productosEncontrados = inventario.buscarProductoPorCategoria(categoria);
        if (productosEncontrados.isEmpty()) {
            System.out.println("No se encontraron productos en esa categoría.");
        } else {
            for (Producto producto : productosEncontrados) {
                System.out.println("ID: " + producto.getIdProducto() +
                        ", Nombre: " + producto.getNombreProducto() +
                        ", Precio: " + String.format("%.2f", producto.getPrecio()) +
                        ", Cantidad Disponible: " + producto.getCantidadDisponible());
            }
        }
    }

    public static void generarReporte() {
        inventario.generarReporte();
        System.out.println("Reporte generado correctamente.");
    }

    public static void cantidadDeProductosPorCategoria() {
        Map<String, Integer> cantidadPorCategoria = inventario.cantidadDeProductosPorCategoria();
        for (Map.Entry<String, Integer> entry : cantidadPorCategoria.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public static void productoMasCaro() {
        Producto productoCaro = inventario.productoMasCaro();
        if (productoCaro != null) {
            System.out.println("El producto más caro es: " + productoCaro);
        } else {
            System.out.println("No hay productos en el inventario.");
        }
    }
}
