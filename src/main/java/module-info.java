module org.example.bibliotecafx {
    // Módulos de JavaFX y otros para la parte gráfica
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    // Módulos para persistencia (Hibernate y dependencias)
    requires java.sql;
    requires java.naming;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    // requires mysql.connector.java;
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

    // Abrir paquetes para que sean accesibles vía reflexión (ajusta según la estructura real)
    opens org.example.bibliotecafx.entities to org.hibernate.orm.core, javafx.fxml;
    opens org.example.bibliotecafx.view.controller to javafx.fxml;

    // Exportar los paquetes que se necesiten desde fuera (exporta el paquete raíz si contiene tu clase principal)
    exports org.example.bibliotecafx;
}
