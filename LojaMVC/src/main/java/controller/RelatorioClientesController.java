package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.Cliente;
import model.ClienteDAO;

public class RelatorioClientesController {

    private Stage stage;

    @FXML
    private TableView<Cliente> tbClientes;

    @FXML
    private TableColumn<Cliente, Integer> cID;

    @FXML
    private TableColumn<Cliente, String> cNome;

    @FXML
    private TableColumn<Cliente, String> cEndereco;

    @FXML
    private TableColumn<Cliente, String> cTelefone;

    @FXML
    private TableColumn<Cliente, Date> cNascimento;

    @FXML
    void btnEditar(ActionEvent event) throws IOException {
       
        Cliente clienteSelecionado = tbClientes.getSelectionModel().getSelectedItem();

        if (clienteSelecionado != null) {
            
            URL url = new File("src/main/java/view/EdicaoCliente.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();
            Stage telaEdicaoCliente = new Stage();
            EdicaoClienteController ec = loader.getController();

            ec.setOnClienteAtualizado(() -> carregarClientes());
            ec.setCliente(clienteSelecionado);
            ec.setStage(telaEdicaoCliente);

            Scene scene = new Scene(root);
            telaEdicaoCliente.setScene(scene);
            telaEdicaoCliente.show();
        } else {
            
            PrincipalController.mostrarAlerta("Erro", "Selecione um cliente para editar.");
        }
    }

    @FXML
    void btnExcluir(ActionEvent event) {
        
        Cliente clienteSelecionado = tbClientes.getSelectionModel().getSelectedItem();

        if (clienteSelecionado != null) {
            
            ClienteDAO dao = new ClienteDAO();
            boolean sucesso = dao.excluirCliente(clienteSelecionado.getId());

            if (sucesso) {
               
                tbClientes.getItems().remove(clienteSelecionado);
                PrincipalController.mostrarAlerta("Sucesso", "Cliente excluído com sucesso!");
            } else {
                PrincipalController.mostrarAlerta("Erro", "Erro ao excluir cliente.");
            }
        } else {
            
            PrincipalController.mostrarAlerta("Erro", "Selecione um cliente para excluir.");
        }
    }
    @FXML
    void btnSair(ActionEvent event) throws IOException {
        URL url = new File("src/main/java/view/Principal.fxml").toURI().toURL();
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();
        Stage telaPrincipal = new Stage();
        PrincipalController pc = loader.getController();
        pc.setStage(telaPrincipal);
        
        Scene scene = new Scene (root);
        telaPrincipal.setScene(scene);
        telaPrincipal.show();

    }

    // Método que carrega a lista de clientes na TableView
    public void carregarClientes() {
        ClienteDAO dao = new ClienteDAO();
        List<Cliente> listaClientes = dao.listarTodosClientes();  // Busca todos os clientes do banco
        ObservableList<Cliente> clientesObservable = FXCollections.observableArrayList(listaClientes);

        // Define as células da tabela
        cID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        cNome.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));
        cEndereco.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEndereco()));
        cTelefone.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefone()));
        cNascimento.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDataNascimento()));

       
        tbClientes.setItems(clientesObservable);
    }

    

    public void setStage(Stage telaRelatorioCliente) {
        this.stage = telaRelatorioCliente;
    }

    @FXML
    void initialize() {
        
        carregarClientes();
    }
}
