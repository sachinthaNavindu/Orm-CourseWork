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
    requires java.sql;

    // Use the automatic module name from the JAR file
    requires net.sf.jasperreports.core;

    // Jackson dependencies
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;

    opens edu.ijse.therapycenter to javafx.fxml;
    opens edu.ijse.therapycenter.controller to javafx.fxml;
    opens edu.ijse.therapycenter.entity to org.hibernate.orm.core;
    opens edu.ijse.therapycenter.dto to javafx.base;

    exports edu.ijse.therapycenter;
    exports edu.ijse.therapycenter.controller;
}