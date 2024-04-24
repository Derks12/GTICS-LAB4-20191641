package pe.sanmiguel.bienestar.proyecto_gtics.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pe.sanmiguel.bienestar.proyecto_gtics.Entity.*;
import pe.sanmiguel.bienestar.proyecto_gtics.Repository.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;



@Controller
@RequestMapping(value="/paciente", method= RequestMethod.GET)
public class PacienteController {




    /*----------------- Repositories -----------------*/
    final MedicamentoRepository medicamentoRepository;
    final OrdenRepository ordenRepository;
    final UsuarioRepository usuarioRepository;
    final TipoOrdenRepository tipoOrdenRepository;
    final EstadoOrdenRepository estadoOrdenRepository;
    final SedeRepository sedeRepository;
    final DoctorRepository doctorRepository;
    final EstadoPreOrdenRepository estadoPreOrdenRepository;
    final OrdenContenidoRepository ordenContenidoRepository;

    public PacienteController(OrdenContenidoRepository ordenContenidoRepository, EstadoPreOrdenRepository estadoPreOrdenRepository, DoctorRepository doctorRepository,EstadoOrdenRepository estadoOrdenRepository,TipoOrdenRepository tipoOrdenRepository,SedeRepository sedeRepository, MedicamentoRepository medicamentoRepository, UsuarioRepository usuarioRepository, OrdenRepository ordenRepository){
        this.medicamentoRepository = medicamentoRepository;
        this.usuarioRepository = usuarioRepository;
        this.ordenRepository = ordenRepository;
        this.sedeRepository = sedeRepository;
        this.tipoOrdenRepository = tipoOrdenRepository;
        this.estadoOrdenRepository = estadoOrdenRepository;
        this.doctorRepository = doctorRepository;
        this.estadoPreOrdenRepository = estadoPreOrdenRepository;
        this.ordenContenidoRepository = ordenContenidoRepository;
    }





    /*----------------- Method: GET -----------------*/

    @GetMapping(value="")
    public String preOrdenes(){return "/paciente/pre_ordenes";}

    @GetMapping(value="/ordenes")
    public String ordenes(){return "/paciente/ordenes";}

    @GetMapping(value="/pago_tarjeta")
    public String pago_tarjeta(){return "/paciente/pago_tarjeta";}

    @GetMapping(value="/tracking")
    public String tracking(){ return "/paciente/tracking";}

    @GetMapping(value="/tracking_wait")
    public String tracking_wait(){ return "/paciente/tracking_pendiente";}

    @GetMapping(value="/tracking_end")
    public String tracking_end(){ return "/paciente/tracking_finalizado";}

    @GetMapping(value="/new_orden")
    public String new_orden(Model model){

        List<Medicamento> listaMedicamentos = medicamentoRepository.findAll();
        model.addAttribute("listaMedicamentos", listaMedicamentos);

        return "/paciente/new_orden";}

    @GetMapping(value="/mensajeria")
    public String mensajeria(){return "/paciente/mensajeria";}

    @GetMapping(value = "/chatbot")
    public String chatbot(){return "/paciente/chatbot";}

    @GetMapping(value = "/orden_paciente")
    public String ordenPaciente(){
        return "/paciente/orden_paciente";
    }

    @GetMapping(value = "/orden_paciente_stock")
    public String ordenPacienteStock(){
        return "/paciente/orden_paciente_stock";
    }

    @GetMapping(value = "/reemplazar_medicamentos")
    public String ordenReemplazoMedicamentos(){
        return "/paciente/reemplazar_medicamentos";
    }

    @GetMapping(value = "/perfil")
    public String perfil(){
        return "/paciente/perfil";
    }

    @GetMapping(value = "/cambio_contrasena")
    public String cambioContrasena(){
        return "/paciente/cambioContraseña";
    }

    @GetMapping(value = "/confirmar_pago")
    public String confirmarPago(){
        return "/paciente/confirmar_pago";
    }




    /*----------------- Method: POST -----------------*/

    @PostMapping(value = "/guardarOrden")
    public String guardarOrden(Usuario usuario,
                               @RequestParam(value = "doctor", required = false) String doctor,
                               @RequestParam(value = "fecha", required = false) String fecha,
                               @RequestParam(value = "imagen", required = false) MultipartFile archivo,
                               @RequestParam(value = "listaIds", required = false) List<String> lista,
                               Model model, RedirectAttributes redirectAttributes){



        try{
            byte[] bytes = archivo.getBytes();
            String base64Encoded = Base64.getEncoder().encodeToString(bytes);
            model.addAttribute("imagen", base64Encoded);
        } catch (IOException e){
            e.printStackTrace();
            return "error";
        }


        String tracking = new String();
        LocalDate fechaIni = LocalDate.now();
        LocalDate fechaFin = LocalDate.now();
        Float precioTotal = new Float(3.14);
        Integer idFarmacista = new Integer(1);
        Usuario udb = usuarioRepository.getById(1);
        TipoOrden to = tipoOrdenRepository.getById(1);
        EstadoOrden eo = estadoOrdenRepository.getById(1);
        Sede s = sedeRepository.getById(1);
        Doctor doc = doctorRepository.getById(1);
        EstadoPreOrden esp = estadoPreOrdenRepository.getById(1);


        Orden orden = new Orden();
        orden.setIdOrden(ordenRepository.findLastOrdenId() + 1);
        orden.setTracking(tracking);
        orden.setFechaIni(fechaIni);
        orden.setFechaFin(fechaFin);
        orden.setPrecioTotal(precioTotal);
        orden.setIdFarmacista(idFarmacista);
        orden.setPaciente(udb);
        orden.setTipoOrden(to);
        orden.setEstadoOrden(eo);
        orden.setSede(s);
        orden.setDoctor(doc);
        orden.setEstadoPreOrden(esp);

        System.out.println(ordenRepository.findLastOrdenId() + 1);
        System.out.println(lista);

        //ordenRepository.save(orden);

        Integer i = new Integer(0);

        for(String n : lista){






        }


        redirectAttributes.addFlashAttribute("msg", "Orden Creada");
        return "redirect:/paciente/ordenes";


        //return "/paciente/prueba";
    }
}
