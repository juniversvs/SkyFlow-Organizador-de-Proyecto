package com.greener.skyflow.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario {
	
	@Id
	@Column(name="id_usuario")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idUsuario;
	
	private String nombre;
	private String apellido;
	private String username;
	private String email;
	
	@Column(name="password_hash")
	private String clave;
	
	@Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

	public Usuario(String nombre, String apellido, String username, String email, String clave,
			LocalDateTime fechaRegistro) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.username = username;
		this.email = email;
		this.clave = clave;
		this.fechaRegistro = fechaRegistro;
	}
	
}

/*CREATE TABLE usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100),
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(150) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP
);*/