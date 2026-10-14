
package com.cibertec.app.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cibertec.app.entity.DetalleVenta;
import com.cibertec.app.entity.DetalleVentaId;
import com.cibertec.app.entity.Producto;
import com.cibertec.app.entity.ProductoParaVender;
import com.cibertec.app.entity.Venta;
import com.cibertec.app.repository.VentaRepository;
import com.cibertec.app.service.DetalleVentaService;
import com.cibertec.app.service.ProductoService;
import com.cibertec.app.service.VentaService;

import jakarta.transaction.Transactional;

@Service
public class VentaserviceImpl implements VentaService {

    private final VentaRepository ventaRepository;
    private final ProductoService productoService;
    private final DetalleVentaService detalleVentaService;

    private static final BigDecimal IGV = new BigDecimal("0.18");

    public VentaserviceImpl(
            VentaRepository ventaRepository,
            ProductoService productoService,
            DetalleVentaService detalleVentaService) {
        this.ventaRepository = ventaRepository;
        this.productoService = productoService;
        this.detalleVentaService = detalleVentaService;
    }

    @Override
    public Venta guardarVenta(Venta venta) {
        return ventaRepository.save(venta);
    }

    @Override
    public List<Venta> ListarTodosVentas() {
        return ventaRepository.findAll();
    }

    @Override
    @Transactional
    public Venta registrarVenta(
            Venta venta,
            List<ProductoParaVender> carrito) {

        if (venta == null) {
            throw new IllegalArgumentException(
                    "No se recibieron los datos de la venta.");
        }

        if (carrito == null || carrito.isEmpty()) {
            throw new IllegalArgumentException(
                    "No hay productos en el carrito.");
        }

        if (venta.getCliente() == null) {
            throw new IllegalArgumentException(
                    "Debes seleccionar un cliente.");
        }

        if (venta.getAsesorVenta() == null) {
            throw new IllegalArgumentException(
                    "Debes seleccionar un asesor.");
        }

        venta.setFecha(java.sql.Date.valueOf(LocalDate.now()));

        BigDecimal subtotal = BigDecimal.ZERO;
        BigDecimal ganancia = BigDecimal.ZERO;

        // Validar el carrito antes de registrar los detalles.
        for (ProductoParaVender item : carrito) {
            if (item.getCantidad() <= 0) {
                throw new IllegalArgumentException(
                        "La cantidad del producto debe ser mayor que cero.");
            }

            Producto producto =
                    productoService.buscarProductoById(item.getIdProd());

            if (producto == null) {
                throw new IllegalArgumentException(
                        "No se encontró uno de los productos del carrito.");
            }

            if (producto.getStock() < item.getCantidad()) {
                throw new IllegalArgumentException(
                        "Stock insuficiente para el producto: "
                        + producto.getDescripcion());
            }

            if (item.getPrecioVenta() == null
                    || item.getPrecioCompra() == null) {
                throw new IllegalArgumentException(
                        "Falta el precio de uno de los productos.");
            }
        }

        // Guardar la venta para obtener su número.
        Venta ventaGuardada = ventaRepository.save(venta);

        for (ProductoParaVender item : carrito) {
            Producto producto =
                    productoService.buscarProductoById(item.getIdProd());

            producto.restarExistencia(item.getCantidad());
            productoService.guardarProducto(producto);

            BigDecimal cantidad =
                    BigDecimal.valueOf(item.getCantidad());

            BigDecimal importeVenta =
                    item.getPrecioVenta().multiply(cantidad);

            BigDecimal importeCompra =
                    item.getPrecioCompra().multiply(cantidad);

            DetalleVentaId id =
                    new DetalleVentaId(ventaGuardada, producto);

            DetalleVenta detalle = new DetalleVenta(
                    id,
                    item.getCantidad(),
                    item.getPrecioVenta(),
                    importeVenta,
                    item.getPrecioCompra()
            );

            detalleVentaService.guardarDetalleVenta(detalle);

            // Acumular correctamente los importes.
            subtotal = subtotal.add(importeVenta);
            ganancia = ganancia.add(
                    importeVenta.subtract(importeCompra));
        }

        BigDecimal igv = subtotal.multiply(IGV);
        BigDecimal total = subtotal.add(igv);

        ventaGuardada.setSubtotal(subtotal);
        ventaGuardada.setIgv(igv);
        ventaGuardada.setTotal(total);
        ventaGuardada.setGanancia(ganancia);

        return ventaRepository.save(ventaGuardada);
    }
}
