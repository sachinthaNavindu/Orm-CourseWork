package edu.ijse.therapycenter.util;

import edu.ijse.therapycenter.config.FactoryConfiguration;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class PaymentReport {
    public static void generatePaymentSlip(String sessionId) {
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(
                    PaymentReport.class.getResourceAsStream("/reports/Blank_A4.jrxml")
            );

            Session session = FactoryConfiguration.getInstance().getSession();

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("sessionId", sessionId);

            session.doWork(new Work() {
                @Override
                public void execute(Connection connection) throws SQLException {
                    try {
                        JasperPrint print = JasperFillManager.fillReport(
                                jasperReport, parameters, connection);
                        JasperViewer.viewReport(print, false);
                    } catch (JRException e) {
                        throw new RuntimeException("Failed to generate report", e);
                    }
                }
            });

        } catch (JRException e) {
            throw new RuntimeException("Failed to compile report", e);
        }
    }
}