package taller.inventario.tienda;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

public class Inventario {
    private List<Producto> productos;

    public Inventario() {
        productos = new ArrayList<>();
        cargarInventarioDesdeArchivo();
    }

    // Cargar productos desde archivo
    public void cargarInventarioDesdeArchivo() {
        File file = new File("C:\\Users\\lmuno15\\Documents\\leidy\\Fundamentos de programcion\\Inventario_tienda.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                int idProducto = Integer.parseInt(datos[0].trim());
                String nombreProducto = datos[1].trim();
                String categoria = datos[2].trim();
                double precio = Double.parseDouble(datos[3].trim());
                int cantidadDisponible = Integer.parseInt(datos[4].trim());

                Producto producto = new Producto(idProducto, nombreProducto, categoria, precio, cantidadDisponible);
                productos.add(producto);
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error al cargar el inventario: " + e.getMessage());
        }
    }

    // Agregar producto
    public void agregarProducto(Producto producto) {
        productos.add(producto);
        guardarInventarioEnArchivo();
    }

    // Actualizar producto
    public boolean actualizarProducto(int idProducto, Producto productoActualizado) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getIdProducto() == idProducto) {
                productos.set(i, productoActualizado);
                guardarInventarioEnArchivo();
                return true;
            }
        }
        return false;
    }

    // Eliminar producto
    public boolean eliminarProducto(int idProducto) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getIdProducto() == idProducto) {
                productos.remove(i);
                guardarInventarioEnArchivo();
                return true;
            }
        }
        return false;
    }

    // Buscar producto por categoría
    public List<Producto> buscarProductoPorCategoria(String categoria) {
        List<Producto> resultado = new ArrayList<>();
        for (Producto producto : productos) {
            if (producto.getCategoria().equalsIgnoreCase(categoria)) {
                resultado.add(producto);
            }
        }
        return resultado;
    }

    // Producto más caro
    public Producto productoMasCaro() {
        Producto productoCaro = null;
        for (Producto producto : productos) {
            if (productoCaro == null || producto.getPrecio() > productoCaro.getPrecio()) {
                productoCaro = producto;
            }
        }
        return productoCaro;
    }

    // Cantidad de productos por categoría
    public Map<String, Integer> cantidadDeProductosPorCategoria() {
        Map<String, Integer> cantidadPorCategoria = new HashMap<>();
        for (Producto producto : productos) {
            cantidadPorCategoria.put(producto.getCategoria(),
                    cantidadPorCategoria.getOrDefault(producto.getCategoria(), 0) + producto.getCantidadDisponible());
        }
        return cantidadPorCategoria;
    }

    // Generar reporte de inventario
    public void generarReporte() {
        double valorTotal = 0;
        DecimalFormat formatoMoneda = new DecimalFormat("###,###.00");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("reporte_inventario.txt"))) {
            for (Producto producto : productos) {
                double valorProducto = producto.getPrecio() * producto.getCantidadDisponible();
                valorTotal += valorProducto;
                bw.write(producto.getIdProducto() + ", " +
                        producto.getNombreProducto() + ", " +
                        producto.getCategoria() + ", " +
                        producto.getPrecio() + ", " +
                        producto.getCantidadDisponible() + ", " +
                        valorProducto + "\n");
            }
            String valorTotalFormateado = formatoMoneda.format(valorTotal);
            bw.write("\nValor total del inventario: $" + valorTotalFormateado);
        } catch (IOException e) {
            System.out.println("Error al generar el reporte: " + e.getMessage());
        }
    }

    // Guardar inventario en archivo
    private void guardarInventarioEnArchivo() {
        File file = new File("C:\\Users\\lmuno15\\Documents\\leidy\\Fundamentos de programcion\\Inventario_tienda.txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (Producto producto : productos) {
                bw.write(producto.getIdProducto() + "," +
                        producto.getNombreProducto() + "," +
                        producto.getCategoria() + "," +
                        producto.getPrecio() + "," +
                        producto.getCantidadDisponible() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error al guardar el inventario: " + e.getMessage());
        }
    }

    public List<Producto> getProductos() {
        return productos;
    }
}