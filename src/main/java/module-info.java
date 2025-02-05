module org.example.bibliotecafx {
    // Módulos de JavaFX y otros necesarios para la parte gráfica
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    // Módulos para persistencia con Hibernate
    requires java.sql;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires mysql.connector.java;
    requires org.slf4j;

    // Otras dependencias de la interfaz
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.persistence;

    // Abrir paquetes para que sean accesibles vía reflexión
    opens org.example.bibliotecafx.entities to org.hibernate.orm.core, javafx.fxml;
    opens org.example.bibliotecafx.view to javafx.fxml;
    opens org.example.bibliotecafx.view.controllers to javafx.fxml;

    // Exportar los paquetes que realmente se usarán desde fuera, si es necesario
    exports org.example.bibliotecafx.view.controller;
    // Exporta otros paquetes solo si son necesarios
}
