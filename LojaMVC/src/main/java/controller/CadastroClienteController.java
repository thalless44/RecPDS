package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Cliente;
import java.sql.Date;
import java.time.LocalDate;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import model.ClienteDAO;

public class CadastroClienteController {

    private Stage stage;

    @FXML
    private DatePicker dataNascimento;  // Alteração aqui, para utilizar o DatePicker

    @FXML
    private TextField txtEndereco;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtTelefone;

    @FXML
    void btnSalvarOnActionPerformed(ActionEvent event) {
        try {
            String nome = txtNome.getText();
            String endereco = txtEndereco.getText();
            String telefone = txtTelefone.getText();

            // Verifica se todos os campos foram preenchidos
            if (nome.isEmpty() || endereco.isEmpty() || telefone.isEmpty() || dataNascimento.getValue() == null) {
                PrincipalController.mostrarAlerta("Erro", "Todos os campos devem ser preenchidos.");
                return;
            }

            // Convertendo o valor do DatePicker para java.sql.Date
            LocalDate localDate = dataNascimento.getValue();
            Date dataNascimentoSQL = Date.valueOf(localDate);

           
            Cliente cliente = new Cliente(nome, endereco, telefone, dataNascimentoSQL);

            // Inserindo no banco de dados
            ClienteDAO dao = new ClienteDAO();
            boolean sucesso = dao.inserirCliente(cliente);

            if (sucesso) {
                PrincipalController.mostrarAlerta("Sucesso", "Cliente cadastrado com sucesso!");
                URL url = new File("src/main/java/view/Principal.fxml").toURI().toURL();
                FXMLLoader loader = new FXMLLoader(url);
                Parent root = loader.load();
                Stage telaPrincipal = new Stage();
                PrincipalController pc = loader.getController();
                pc.setStage(telaPrincipal);
                Scene scene = new Scene(root);
                telaPrincipal.setScene(scene);
                telaPrincipal.show();
                stage.close();
            } else {
                PrincipalController.mostrarAlerta("Erro", "Erro ao cadastrar cliente.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            PrincipalController.mostrarAlerta("Erro", "Erro inesperado: " + e.getMessage());
        }
    }

    @FXML
    void onClickSair(ActionEvent event) throws IOException {
            URL url = new File("src/main/java/view/Principal.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();
            Stage telaPrincipal = new Stage();
            PrincipalController pc = loader.getController();
            pc.setStage(telaPrincipal);
            Scene scene = new Scene(root);
            telaPrincipal.setScene(scene);
            telaPrincipal.show();
            stage.close();
    }

    public void setStage(Stage telaCadastroCliente) {
        this.stage = telaCadastroCliente;
    }

    // Método para exibir a alerta
    
}
