package com.greener.skyflow.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "proyecto_usuario")
public class ProyectoUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proyecto_usuario")
    private int idProyectoUsuario;

    @ManyToOne
    @JoinColumn(name = "id_proyecto")
    private Proyecto proyecto;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol")
    private Rol rol;

    @Column(name = "fecha_union")
    private LocalDateTime fechaUnion;

    public ProyectoUsuario(Proyecto proyecto, Usuario usuario, Rol rol,
            LocalDateTime fechaUnion) {
        this.proyecto = proyecto;
        this.usuario = usuario;
        this.rol = rol;
        this.fechaUnion = fechaUnion;
    }
}

/*CREATE TABLE proyecto_usuario (
    id_proyecto_usuario INT AUTO_INCREMENT PRIMARY KEY,
    id_proyecto INT NOT NULL,
    id_usuario INT NOT NULL,
    rol ENUM('LIDER', 'SUBORDINADO')
        DEFAULT 'SUBORDINADO',
    fecha_union DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_proyecto)
        REFERENCES proyectos(id_proyecto)
        ON DELETE CASCADE,
    FOREIGN KEY (id_usuario)
        REFERENCES usuarios(id_usuario)
        ON DELETE CASCADE,
    UNIQUE (id_proyecto, id_usuario)
);*/
