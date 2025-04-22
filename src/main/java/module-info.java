module edu.ijse.therapycenter.therapycenter {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires static lombok;
    requires jakarta.persistence;
    requires java.naming;
    requires modelmapper;
    requires bcrypt;
    requires java.desktop;

    // Open packages for FXML and Hibernate
    opens edu.ijse.therapycenter to javafx.fxml;
    opens edu.ijse.therapycenter.controller to javafx.fxml;
    opens edu.ijse.therapycenter.entity to org.hibernate.orm.core;

    // NEW: Open DTO package to JavaFX for reflection access
    opens edu.ijse.therapycenter.dto to javafx.base;

    exports edu.ijse.therapycenter;
    exports edu.ijse.therapycenter.controller;
}