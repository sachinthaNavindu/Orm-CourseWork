package edu.ijse.therapycenter.controller;

import edu.ijse.therapycenter.bo.BOFactory;
import edu.ijse.therapycenter.bo.custom.PatientBO;
import edu.ijse.therapycenter.bo.custom.TherapySessionBO;
import edu.ijse.therapycenter.bo.custom.impl.PaymentBOImpl;
import edu.ijse.therapycenter.dto.PatientDTO;
import edu.ijse.therapycenter.dto.PaymentDTO;
import edu.ijse.therapycenter.dto.TherapySessionDTO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class PaymentController implements Initializable {

    @FXML private Button btnProcess;
    @FXML private Button btnReset;
    @FXML private Button btnUpdate;
    @FXML private TableColumn<PaymentDTO, String> colAmount;
    @FXML private TableColumn<PaymentDTO, String> colDate;
    @FXML private TableColumn<PaymentDTO, String> colPatient;
    @FXML private TableColumn<PaymentDTO, String> colPaymentId;
    @FXML private TableColumn<PaymentDTO, String> colSession;
    @FXML private DatePicker datePickerPayment;
    @FXML private Label errorMessage;
    @FXML private Label lblPaymentId;
    @FXML private ComboBox<PatientDTO> cmbPatient;
    @FXML private ComboBox<TherapySessionDTO> cmbSession;
    @FXML private TableView<PaymentDTO> tblPayments;
    @FXML private TextField txtAmount;

    private final PaymentBOImpl paymentBO = (PaymentBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.PAYMENT);
    private final PatientBO patientBO = (PatientBO) BOFactory.getInstance().getBO(BOFactory.BOType.PATIENT);
    private final TherapySessionBO therapySessionBO = (TherapySessionBO) BOFactory.getInstance().getBO(BOFactory.BOType.THERAPY_SESSION);

    private final ObservableList<PaymentDTO> paymentList = FXCollections.observableArrayList();
    private PaymentDTO selectedPayment = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeTable();
        loadPatients();
        loadSessions();
        loadAllPayments();
        lblPaymentId.setText(paymentBO.getLastPK().orElse("0"));
    }

    private void initializeTable() {
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        // For nested properties
        colPatient.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getPatient().getName()));

        colSession.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTherapySession().getId()));

        tblPayments.setItems(paymentList);
    }

    private void loadPatients() {
        try {
            List<PatientDTO> allPatients = patientBO.getAll();
            ObservableList<PatientDTO> patientObservableList = FXCollections.observableArrayList(allPatients);
            cmbPatient.setItems(patientObservableList);

            cmbPatient.setConverter(new StringConverter<PatientDTO>() {
                @Override
                public String toString(PatientDTO patient) {
                    return patient != null ? patient.getName() + " (" + patient.getId() + ")" : "";
                }

                @Override
                public PatientDTO fromString(String string) {
                    return null;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load patients!").show();
        }
    }

    private void loadSessions() {
        try {
            List<TherapySessionDTO> allSessions = therapySessionBO.getAll();
            ObservableList<TherapySessionDTO> sessionObservableList = FXCollections.observableArrayList(allSessions);
            cmbSession.setItems(sessionObservableList);

            cmbSession.setConverter(new StringConverter<TherapySessionDTO>() {
                @Override
                public String toString(TherapySessionDTO session) {
                    return session != null ? session.getId() : "";
                }

                @Override
                public TherapySessionDTO fromString(String string) {
                    return null;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load therapy sessions!").show();
        }
    }

    private void loadAllPayments() {
        try {
            paymentList.clear();
            paymentList.addAll(paymentBO.getAll());
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load payments!").show();
        }
    }

    @FXML
    void paymentSelectOnAction(MouseEvent event) {
        selectedPayment = tblPayments.getSelectionModel().getSelectedItem();
        if (selectedPayment != null) {
            lblPaymentId.setText(selectedPayment.getId());
            txtAmount.setText(String.valueOf(selectedPayment.getAmount()));
            datePickerPayment.setValue(LocalDate.parse(selectedPayment.getDate()));

            // Select patient in combobox
            for (PatientDTO patient : cmbPatient.getItems()) {
                if (patient.getId().equals(selectedPayment.getPatient().getId())) {
                    cmbPatient.getSelectionModel().select(patient);
                    break;
                }
            }

            // Select session in combobox
            for (TherapySessionDTO session : cmbSession.getItems()) {
                if (session.getId().equals(selectedPayment.getTherapySession().getId())) {
                    cmbSession.getSelectionModel().select(session);
                    break;
                }
            }
        }
    }

    @FXML
    void processPayment(ActionEvent event) {
        try {
            if (validateFields()) {
                PaymentDTO paymentDTO = new PaymentDTO(
                        lblPaymentId.getText(),
                        Double.parseDouble(txtAmount.getText()),
                        datePickerPayment.getValue().toString(),
                        cmbPatient.getValue(),
                        cmbSession.getValue()
                );

                if (paymentBO.save(paymentDTO)) {
                    new Alert(Alert.AlertType.INFORMATION, "Payment processed successfully!").show();
                    resetForm();
                    loadAllPayments();
                    lblPaymentId.setText(paymentBO.getLastPK().orElse("0"));
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to process payment!").show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "An error occurred: " + e.getMessage()).show();
        }
    }

    @FXML
    void updatePayment(ActionEvent event) {
        if (selectedPayment == null) {
            new Alert(Alert.AlertType.WARNING, "Please select a payment to update!").show();
            return;
        }

        try {
            if (validateFields()) {
                PaymentDTO paymentDTO = new PaymentDTO(
                        lblPaymentId.getText(),
                        Double.parseDouble(txtAmount.getText()),
                        datePickerPayment.getValue().toString(),
                        cmbPatient.getValue(),
                        cmbSession.getValue()
                );

                if (paymentBO.update(paymentDTO)) {
                    new Alert(Alert.AlertType.INFORMATION, "Payment updated successfully!").show();
                    resetForm();
                    loadAllPayments();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to update payment!").show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "An error occurred: " + e.getMessage()).show();
        }
    }

    @FXML
    void resetForm(ActionEvent event) {
        resetForm();
    }

    private boolean validateFields() {
        if (txtAmount.getText().isEmpty() || datePickerPayment.getValue() == null ||
                cmbPatient.getValue() == null || cmbSession.getValue() == null) {
            errorMessage.setText("Please fill all fields!");
            return false;
        }

        try {
            Double.parseDouble(txtAmount.getText());
        } catch (NumberFormatException e) {
            errorMessage.setText("Invalid amount format!");
            return false;
        }

        errorMessage.setText("");
        return true;
    }

    private void resetForm() {
        lblPaymentId.setText(paymentBO.getLastPK().orElse("0"));
        txtAmount.clear();
        datePickerPayment.setValue(null);
        cmbPatient.getSelectionModel().clearSelection();
        cmbSession.getSelectionModel().clearSelection();
        errorMessage.setText("");
        selectedPayment = null;
    }
}