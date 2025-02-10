module org.example.bibliotecafx {
    // JavaFX modules required for UI components
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    // Persistence modules (Hibernate and its dependencies)
    requires java.sql; // For database connections
    requires java.naming; // For JNDI (Java Naming and Directory Interface)
    requires jakarta.persistence; // JPA (Jakarta Persistence API)
    requires org.hibernate.orm.core; // Hibernate ORM for database management
    // requires mysql.connector.java; // Uncomment if using MySQL (JDBC driver)
    requires org.slf4j; // Logging framework used by Hibernate

    // Additional JavaFX UI dependencies
    requires org.controlsfx.controls; // Extra UI components for JavaFX
    requires com.dlsc.formsfx; // Forms handling in JavaFX
    requires net.synedra.validatorfx; // Validation framework for JavaFX forms
    requires org.kordamp.ikonli.javafx; // Icon support for JavaFX
    requires org.kordamp.bootstrapfx.core; // Bootstrap-like styling for JavaFX
    requires eu.hansolo.tilesfx; // Dashboard and tile-based UI components
    requires com.almasb.fxgl.all; // FXGL game development framework (check if needed)
    requires java.persistence; // JPA API (redundant, as Jakarta Persistence is already required)

    // Opens packages for reflection (required by Hibernate and JavaFX controllers)
    opens org.example.bibliotecafx.entities to org.hibernate.orm.core, javafx.fxml;
    opens org.example.bibliotecafx.view.controller to javafx.fxml;

    // Exports the main package so that other modules can access the application
    exports org.example.bibliotecafx;
}
