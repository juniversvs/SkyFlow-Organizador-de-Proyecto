
package com.cibertec.app.controller;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cibertec.app.entity.Producto;
import com.cibertec.app.entity.ProductoParaVender;
import com.cibertec.app.entity.Venta;
import com.cibertec.app.service.AsesorVentaService;
import com.cibertec.app.service.ClienteService;
import com.cibertec.app.service.DetalleVentaService;
import com.cibertec.app.service.ProductoService;
import com.cibertec.app.service.VentaService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/venta")
@SessionAttributes("venta")
public class VentaController {

    private final ProductoService productoService;
    private final VentaService ventaService;
    private final DetalleVentaService detalleVentaService;
    private final ClienteService clienteService;
    private final AsesorVentaService asesorVentaService;

    public VentaController(
            ProductoService productoService,
            VentaService ventaService,
            DetalleVentaService detalleVentaService,
            ClienteService clienteService,
            AsesorVentaService asesorVentaService) {

        this.productoService = productoService;
        this.ventaService = ventaService;
        this.detalleVentaService = detalleVentaService;
        this.clienteService = clienteService;
        this.asesorVentaService = asesorVentaService;
    }

    @ModelAttribute("venta")
    public Venta obtenerVentaSesion() {
        return new Venta();
    }

    @GetMapping
    public String listVentas(Model model) {
        model.addAttribute(
                "ventas",
                ventaService.ListarTodosVentas()
        );
        return "venta/index";
    }

    @SuppressWarnings("unchecked")
    private ArrayList<ProductoParaVender> obtenerCarrito(
            HttpServletRequest request) {

        ArrayList<ProductoParaVender> carrito =
                (ArrayList<ProductoParaVender>) request
                        .getSession()
                        .getAttribute("carrito");

        return carrito == null
                ? new ArrayList<>()
                : carrito;
    }

    private void guardarCarrito(
            ArrayList<ProductoParaVender> carrito,
            HttpServletRequest request) {

        request.getSession().setAttribute("carrito", carrito);
    }

    private void limpiarCarrito(HttpServletRequest request) {
        guardarCarrito(new ArrayList<>(), request);
    }

    @GetMapping("/new")
    public String interfazVender(
            Model model,
            @ModelAttribute("venta") Venta venta,
            HttpServletRequest request) {

        model.addAttribute(
                "clienteList",
                clienteService.listarTodosClientes()
        );

        model.addAttribute(
                "asesorVentaList",
                asesorVentaService.listarTodosAsesores()
        );

        model.addAttribute("producto", new Producto());

        BigDecimal total = BigDecimal.ZERO;

        for (ProductoParaVender item : obtenerCarrito(request)) {
            total = total.add(item.getTotal());
        }

        model.addAttribute("total", total);

        return "venta/create";
    }

    @PostMapping("/agregar")
    public String agregarAlCarrito(
            @ModelAttribute Producto producto,
            HttpServletRequest request,
            RedirectAttributes redirectAttrs) {

        if (producto.getCodigo() == null
                || producto.getCodigo().isBlank()) {

            redirectAttrs.addFlashAttribute(
                    "mensaje", "Ingresa el código del producto.");
            redirectAttrs.addFlashAttribute("clase", "warning");

            return "redirect:/venta/new";
        }

        ArrayList<ProductoParaVender> carrito =
                obtenerCarrito(request);

        Producto productoBD =
                productoService.buscarProductoByCodigo(
                        producto.getCodigo());

        if (productoBD == null) {
            redirectAttrs.addFlashAttribute(
                    "mensaje", "El producto no existe.");
            redirectAttrs.addFlashAttribute("clase", "danger");

            return "redirect:/venta/new";
        }

        if (productoBD.sinExistencia()) {
            redirectAttrs.addFlashAttribute(
                    "mensaje", "El producto no tiene stock.");
            redirectAttrs.addFlashAttribute("clase", "warning");

            return "redirect:/venta/new";
        }

        boolean encontrado = false;

        for (ProductoParaVender item : carrito) {
            if (item.getCodigo().equals(productoBD.getCodigo())) {

                if (item.getCantidad() < productoBD.getStock()) {
                    item.aumentarCantidad();
                } else {
                    redirectAttrs.addFlashAttribute(
                            "mensaje", "Stock máximo alcanzado.");
                    redirectAttrs.addFlashAttribute("clase", "warning");
                }

                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            carrito.add(new ProductoParaVender(
                    productoBD.getIdProd(),
                    productoBD.getCodigo(),
                    productoBD.getDescripcion(),
                    productoBD.getPrecioCompra(),
                    productoBD.getPrecioVenta(),
                    productoBD.getStock(),
                    1
            ));
        }

        guardarCarrito(carrito, request);

        return "redirect:/venta/new";
    }

    @PostMapping("/quitar/{indice}")
    public String quitarDelCarrito(
            @PathVariable int indice,
            HttpServletRequest request) {

        ArrayList<ProductoParaVender> carrito =
                obtenerCarrito(request);

        if (indice >= 0 && indice < carrito.size()) {
            carrito.remove(indice);
            guardarCarrito(carrito, request);
        }

        return "redirect:/venta/new";
    }

    @PostMapping("/terminar")
    public String terminarVenta(
            @ModelAttribute("venta") Venta venta,
            HttpServletRequest request,
            RedirectAttributes redirectAttrs,
            SessionStatus status) {

        ArrayList<ProductoParaVender> carrito =
                obtenerCarrito(request);

        try {
            if (venta.getIdCliente() == null) {
                throw new IllegalArgumentException(
                        "Debes seleccionar un cliente.");
            }

            if (venta.getIdAsesor() == null) {
                throw new IllegalArgumentException(
                        "Debes seleccionar un asesor.");
            }

            if (carrito.isEmpty()) {
                throw new IllegalArgumentException(
                        "Agrega al menos un producto al carrito.");
            }

            venta.setCliente(
                    clienteService.buscarClienteById(
                            venta.getIdCliente()));

            venta.setAsesorVenta(
                    asesorVentaService.buscarAsesorVentaById(
                            venta.getIdAsesor()));

            if (venta.getCliente() == null) {
                throw new IllegalArgumentException(
                        "No se encontró el cliente seleccionado.");
            }

            if (venta.getAsesorVenta() == null) {
                throw new IllegalArgumentException(
                        "No se encontró el asesor seleccionado.");
            }

            ventaService.registrarVenta(venta, carrito);

            limpiarCarrito(request);
            status.setComplete();

            redirectAttrs.addFlashAttribute(
                    "mensaje", "Venta realizada correctamente.");
            redirectAttrs.addFlashAttribute("clase", "success");

        } catch (RuntimeException e) {
            String mensaje = e.getMessage();

            if (mensaje == null || mensaje.isBlank()) {
                mensaje = "No se pudo registrar la venta. Revisa los datos.";
            }

            redirectAttrs.addFlashAttribute("mensaje", mensaje);
            redirectAttrs.addFlashAttribute("clase", "danger");
        }

        return "redirect:/venta/new";
    }

    @GetMapping("/limpiar")
    public String cancelarVenta(
            HttpServletRequest request,
            RedirectAttributes redirectAttrs) {

        limpiarCarrito(request);

        redirectAttrs.addFlashAttribute(
                "mensaje", "Venta cancelada.");
        redirectAttrs.addFlashAttribute("clase", "info");

        return "redirect:/venta/new";
    }

    @GetMapping("/verDetalle/{id}")
    public String verDetalleForm(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "listDetalleVenta",
                detalleVentaService.buscarDetalleVentaByNroVentas(id)
        );

        return "venta/detail";
    }
}
